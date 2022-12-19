package ru.cft.shift.task6.client.view;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class ConnectWindow extends JDialog {
    @Setter
    private static ConnectListener connectListener;


    public ConnectWindow(JFrame frame) {
        super(frame, "Connect", true);

        JTextField ipAddressField = new JTextField();
        JTextField portField = new JTextField();

        GridLayout layout = new GridLayout(5, 1);
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(new JLabel("Enter IP Address:"));
        contentPane.add(ipAddressField);
        ipAddressField.setText("127.0.0.1");

        contentPane.add(new JLabel("Enter Port:"));
        contentPane.add(portField);
        portField.setText("8888");

        contentPane.add(createOkButton(ipAddressField, portField));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(210, 120));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private JButton createOkButton(JTextField ipAddressField, JTextField portField) {
        JButton button = new JButton("OK");
        button.addActionListener(e -> {
            dispose();
            if (connectListener != null) {

//                log.info(ipAddressField.getText()+":"+ portField.getText());
                connectListener.onConnectEntered(ipAddressField.getText(), portField.getText());
            }
        });
        return button;
    }
}
