package ru.cft.shift.task6.server;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task6.common.json.Message;
import ru.cft.shift.task6.common.json.MessageType;
import ru.cft.shift.task6.common.tcp.TCPConnection;
import ru.cft.shift.task6.common.tcp.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class Server implements TCPConnectionListener {
    final Map<TCPConnection, String> connections = new HashMap<>();
    private final int port;

    public void startServer() {
        log.info("Server starting on {}", port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                new TCPConnection(this, serverSocket.accept()).startConnection();
            }
        } catch (IOException e) {
            log.error("Error occurred while accepting server {} socket: {}", port, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private synchronized void sendToAllAuthorize(Message message) {
        for (TCPConnection connection : connections.keySet()) {
            if (isAuthorize(connection)) {
                connection.sendMessage(message);
            }
        }
    }

    private boolean isAuthorize(TCPConnection connection) {
        return connections.get(connection) != null;
    }

    private synchronized void sendUserList() {
        List<String> users = connections.values().stream().filter(Objects::nonNull).toList();
        Message message = Message.builder()
                .type(MessageType.LIST_USER)
                .users(users)
                .build();
        for (TCPConnection connection : connections.keySet()) {
            connection.sendMessage(message);
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpconnection) {
        log.info("onConnectionReady Client Connected: {}", tcpconnection);
        connections.put(tcpconnection, null);
        sendUserList();
        tcpconnection.sendMessage(
                Message.builder().
                        type(MessageType.AUTHORIZATION).
                        message("Authorize!")
                        .build());
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpconnection, Message message) {
        if (connections.get(tcpconnection) == null) {
            authorize(tcpconnection, message);
        } else {
            sendToAllAuthorize(message);
        }
    }

    private void authorize(TCPConnection tcpconnection, Message message) {
        sendUserList();
        if (message.getType() == MessageType.AUTHORIZATION
                && !connections.containsValue(message.getSender())) {
            connections.replace(tcpconnection, message.getSender());
            sendToAllAuthorize(Message.builder()
                    .type(MessageType.MESSAGE)
                    .sender(message.getSender())
                    .message("Hi, I'm in the chat")
                    .build());
            sendUserList();
        } else {
            tcpconnection.sendMessage(Message.builder().
                    type(MessageType.AUTHORIZATION).
                    message("This name: " + message.getSender() + " is already in use")
                    .build());
            tcpconnection.disconnect();
        }
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpconnection) {
        if (connections.get(tcpconnection) != null)
            sendToAllAuthorize(Message.builder().
                    type(MessageType.MESSAGE).
                    sender(connections.get(tcpconnection)).
                    message("Bye everyone, I'm out of the chat").
                    build());
        connections.remove(tcpconnection);
        sendUserList();
        log.info("Disconnect client: {}", tcpconnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpconnection, IOException e) {
        log.info("Exception: {} {}", tcpconnection, e.getMessage());
    }
}