package ru.cft.shift.task6.client.controller;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task6.client.model.ClientModel;
import ru.cft.shift.task6.client.view.ClientWindow;

import java.awt.event.ActionEvent;

@Slf4j
@Builder
public class ClientController implements ClientControllerListener {
    ClientModel clientModel;
    ClientWindow clientWindow;

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = clientWindow.getMessage();
        if (str != null && (!str.equals(""))) {
            clientModel.sendMessage(str);
        }
    }

    @Override
    public void onNicknameEntered(String name) {
        clientModel.setNickname(name);
    }

    @Override
    public void onConnectEntered(String ipAddress, String port) {
        clientModel.connectTo(ipAddress, port);
    }
}
