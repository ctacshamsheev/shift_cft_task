package ru.cft.shift.task6.common.tcp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task6.common.json.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public class TCPConnection {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private Thread rxThread;
    private final TCPConnectionListener eventListener;

    private final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public TCPConnection(TCPConnectionListener eventListener, Socket socket) throws IOException {
        this.eventListener = eventListener;
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    }

    public void startConnection() {
        if (rxThread == null) {
            this.rxThread = new Thread(() -> {
                try {
                    eventListener.onConnectionReady(TCPConnection.this);
                    String value;
                    while (!rxThread.isInterrupted() && (value = in.readLine()) != null) {
                        log.trace("value = {}", value);
                        try {
                            Message message = objectMapper.readValue(value, Message.class);
                            eventListener.onReceiveString(TCPConnection.this, message);
                        } catch (JsonProcessingException e) {
                            log.error(e.getMessage());
                            throw new IOException(e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    eventListener.onException(TCPConnection.this, e);
                } finally {
                    eventListener.onDisconnect(TCPConnection.this);
                }
            });
            rxThread.start();
        } else {
            log.error("connection can be started only once");
        }
    }

    public synchronized void sendMessage(Message message) {
        try {
            String sendMessage = objectMapper.writeValueAsString(message) + "\n";
            out.write(sendMessage);
            log.info("sendToAllAuthorize: {}", sendMessage);
            out.flush();
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            eventListener.onException(TCPConnection.this, e);
        } catch (IOException e) {
            log.error(e.getMessage());
            eventListener.onException(TCPConnection.this, e);
            disconnect();
        }
    }

    public synchronized void disconnect() {
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            eventListener.onException(TCPConnection.this, e);
        }
    }

    @Override
    public String toString() {
        return "[TCP" + socket.getInetAddress() + ":" + socket.getPort() + "] ";
    }

}
