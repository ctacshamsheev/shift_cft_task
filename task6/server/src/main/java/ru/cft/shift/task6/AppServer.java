package ru.cft.shift.task6;

import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task6.server.Server;

@Slf4j
public class AppServer {
    public static void main(String[] args) {
        new Server(8888).startServer();
    }
}
