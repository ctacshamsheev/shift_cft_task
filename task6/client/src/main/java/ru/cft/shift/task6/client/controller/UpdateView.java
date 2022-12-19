package ru.cft.shift.task6.client.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task6.client.view.ClientWindow;
import ru.cft.shift.task6.client.view.ConnectWindow;
import ru.cft.shift.task6.client.view.NicknameWindow;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class UpdateView {
    ClientWindow clientWindow;

    public void printMessage(String message) {
        clientWindow.printMessage(message);
    }

    public void enterNickname() {
        new NicknameWindow(clientWindow);
    }

    public void updateListUser(List<String> stringList) {
        clientWindow.updateListUser(stringList);
    }

    public void getConnect() {
        new ConnectWindow(clientWindow);
    }
}
