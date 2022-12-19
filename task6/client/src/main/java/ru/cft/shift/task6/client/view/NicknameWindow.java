package ru.cft.shift.task6.client.view;

import javax.swing.*;
import java.awt.*;

public class NicknameWindow extends JDialog {
    private static NicknameListener nameListener;

    public NicknameWindow(JFrame frame) {
        super(frame, "Name", true);

        JTextField nameField = new JTextField();

        GridLayout layout = new GridLayout(3, 1);
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(new JLabel("Enter your name:"));
        contentPane.add(nameField);
        contentPane.add(createOkButton(nameField));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(210, 120));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void setNameListener(NicknameListener nameListenerS) {
        nameListener = nameListenerS;
    }

    private JButton createOkButton(JTextField nameField) {
        JButton button = new JButton("OK");
        button.addActionListener(e -> {
            dispose();

            if (nameListener != null) {
                nameListener.onNicknameEntered(nameField.getText());
            }
        });
        return button;
    }
}
