package ru.cft.shift.task6;

import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task6.client.controller.ClientController;
import ru.cft.shift.task6.client.controller.UpdateView;
import ru.cft.shift.task6.client.model.ClientModel;
import ru.cft.shift.task6.client.view.ClientWindow;
import ru.cft.shift.task6.client.view.ConnectWindow;
import ru.cft.shift.task6.client.view.NicknameWindow;

@Slf4j
public class AppClient {
    public static void main(String[] args) {
        ClientWindow clientWindow = new ClientWindow();
        UpdateView updateView = new UpdateView(clientWindow);
        ClientModel clientModel = new ClientModel();
        clientModel.setUpdateView(updateView);
        ClientController controller = ClientController.builder().clientWindow(clientWindow).clientModel(clientModel).build();
        ConnectWindow.setConnectListener(controller);
        clientWindow.setActionListener(controller);
        NicknameWindow.setNameListener(controller);
        updateView.getConnect();
    }
}
