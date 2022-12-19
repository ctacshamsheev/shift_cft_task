package ru.cft.shift.task6.common.tcp;

import ru.cft.shift.task6.common.json.Message;

import java.io.IOException;

public interface TCPConnectionListener {
    void onConnectionReady(TCPConnection tcpconnection);

    void onReceiveString(TCPConnection tcpconnection, Message message);

    void onDisconnect(TCPConnection tcpconnection);

    void onException(TCPConnection tcpconnection, IOException e);
}
