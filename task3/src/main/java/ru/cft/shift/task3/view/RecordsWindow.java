package ru.cft.shift.task3.view;

import javax.swing.*;
import java.awt.*;

public class RecordsWindow extends JDialog {
    private static RecordNameListener nameListener;

    public RecordsWindow(JFrame frame) {
        super(frame, "New Record", true);

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

    public static void setNameListener(RecordNameListener nameListenerS) {
        nameListener = nameListenerS;
    }

    private JButton createOkButton(JTextField nameField) {
        JButton button = new JButton("OK");
        button.addActionListener(e -> {
            dispose();

            if (nameListener != null) {
                nameListener.onRecordNameEntered(nameField.getText());
            }
        });
        return button;
    }
}
