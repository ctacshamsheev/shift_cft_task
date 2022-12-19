package ru.cft.shift.task3.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsWindow extends JDialog {
    private final Map<GameType, JRadioButton> radioButtonsMap = new HashMap<>(3);
    private final ButtonGroup radioGroup = new ButtonGroup();

    private GameTypeListener gameTypeListener;
    private GameType gameType;

    public SettingsWindow(JFrame owner, GameTypeListener gameTypeListener) {
        super(owner, "GameType", true);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        int gridY = 0;
        contentPane.add(createRadioButton("Novice (10 mines, 9х9)", ru.cft.shift.task3.view.GameType.NOVICE, layout, gridY++));
        contentPane.add(createRadioButton("Medium (40 mines, 16х16)", ru.cft.shift.task3.view.GameType.MEDIUM, layout, gridY++));
        contentPane.add(createRadioButton("Expert (99 mines, 16х30)", ru.cft.shift.task3.view.GameType.EXPERT, layout, gridY));

        contentPane.add(createOkButton(layout));
        contentPane.add(createCloseButton(layout));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 180));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        setGameType(ru.cft.shift.task3.view.GameType.NOVICE);
        setGameTypeListener(gameTypeListener);
    }

    public void setGameType(GameType gameType) {
        JRadioButton radioButton = radioButtonsMap.get(gameType);

        if (radioButton == null) {
            throw new UnsupportedOperationException("Unknown game type: " + gameType);
        }

        this.gameType = gameType;
        radioGroup.setSelected(radioButton.getModel(), true);
    }

    public void setGameTypeListener(GameTypeListener gameTypeListener) {
        this.gameTypeListener = gameTypeListener;
    }

    private JRadioButton createRadioButton(String radioButtonText, GameType gameType, GridBagLayout layout, int gridY) {
        JRadioButton radioButton = new JRadioButton(radioButtonText);
        radioButton.addActionListener(e -> this.gameType = gameType);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        layout.setConstraints(radioButton, gbc);

        radioButtonsMap.put(gameType, radioButton);
        radioGroup.add(radioButton);

        return radioButton;
    }

    private JButton createOkButton(GridBagLayout layout) {
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(80, 25));
        okButton.addActionListener(e -> {
            dispose();

            if (gameTypeListener != null) {
                gameTypeListener.onGameTypeChanged(gameType);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 0, 0, 0);
        layout.setConstraints(okButton, gbc);

        return okButton;
    }

    private JButton createCloseButton(GridBagLayout layout) {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(80, 25));
        cancelButton.addActionListener(e -> dispose());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 5, 0, 0);
        layout.setConstraints(cancelButton, gbc);

        return cancelButton;
    }
}
