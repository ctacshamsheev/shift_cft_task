package ru.cft.shift.task6.client.controller;

import ru.cft.shift.task6.client.view.ConnectListener;
import ru.cft.shift.task6.client.view.NicknameListener;

import java.awt.event.ActionListener;

public interface ClientControllerListener extends ActionListener, NicknameListener, ConnectListener {
}
