package ru.cft.shift.task6.client.model;

import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task6.client.controller.UpdateView;
import ru.cft.shift.task6.common.json.Message;
import ru.cft.shift.task6.common.json.MessageType;
import ru.cft.shift.task6.common.tcp.TCPConnection;
import ru.cft.shift.task6.common.tcp.TCPConnectionListener;

import java.io.IOException;
import java.net.Socket;

@Slf4j
public class ClientModel implements TCPConnectionListener {
    private TCPConnection connection;

    private String nickname = null;


    UpdateView updateView;

    public ClientModel() {

    }

    public void setUpdateView(UpdateView updateView) {
        this.updateView = updateView;
        updateView.printMessage("Enter ipAddress:port");
    }

    private void tryConnected(String ipAddress, int port) {
        log.info("tryConnected {} {}", ipAddress, port);
        try {
            connection = new TCPConnection(this, new Socket(ipAddress, port));
            connection.startConnection();
        } catch (IOException e) {
            log.error(e.getMessage());
            updateView.getConnect();
        }
    }

    public void sendMessage(String str) {
        Message message = Message.builder()
                .type(MessageType.MESSAGE)
                .sender(nickname)
                .message(str)
                .build();
        connection.sendMessage(message);

    }

    @Override
    public void onConnectionReady(TCPConnection tcpconnection) {
    }

    @Override
    public void onReceiveString(TCPConnection tcpconnection, Message message) {
        switch (message.getType()) {
            case MESSAGE -> updateView.printMessage(message.getSender() + ": " + message.getMessage());
            case LIST_USER -> updateView.updateListUser(message.getUsers());
            case AUTHORIZATION -> {
                updateView.printMessage(message.getMessage());
                nickname = null;
                updateView.enterNickname();
            }
        }
    }

    @Override
    public void onDisconnect(TCPConnection tcpconnection) {
        updateView.getConnect();
    }

    @Override
    public void onException(TCPConnection tcpconnection, IOException e) {
        updateView.printMessage(e.getMessage());
    }

    public void setNickname(String nickname) {
        Message message = Message.builder()
                .type(MessageType.AUTHORIZATION)
                .sender(nickname)
                .build();
        this.nickname = nickname;
        connection.sendMessage(message);
    }

    public void connectTo(String ipAddress, String port) {
        updateView.printMessage("Error connect to" + ipAddress + ":" + port);
        try {
            tryConnected(ipAddress, Integer.parseInt(port));
        } catch (Exception e) {
            updateView.printMessage("Error:" + e.getMessage());
            tryConnected(ipAddress, 0);
        }
    }
}
