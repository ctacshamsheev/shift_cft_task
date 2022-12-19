package ru.cft.shift.task6.client.view;

import ru.cft.shift.task6.client.controller.ClientControllerListener;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientWindow extends JFrame {
    private static final int HEIGHT = 400;
    private static final int WIDTH = 600;
    private final JTextArea textArea = new JTextArea("");
    private final JTextField fieldInput = new JTextField("");
    private final DefaultListModel<String> listUserModel = new DefaultListModel<>();

    public String getMessage() {
        String message = fieldInput.getText();
        fieldInput.setText(null);
        return message;
    }

    public void updateListUser(List<String> stringList) {
        listUserModel.clear();
        listUserModel.addAll(stringList);
    }

    public void setActionListener(ClientControllerListener controllerListener) {
        fieldInput.addActionListener(controllerListener);
    }

    public ClientWindow() {
        setTitle("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        JList<String> listUser = new JList<>(listUserModel);
        listUser.setFixedCellWidth(100);
        listUser.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        add(listUser, BorderLayout.EAST);
        add(fieldInput, BorderLayout.SOUTH);
        setVisible(true);
    }

    public synchronized void printMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(message + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }
}
