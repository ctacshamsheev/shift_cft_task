package ru.cft.shift.task3.view;

import javax.swing.*;
import java.awt.*;

public class HighScoresWindow extends JDialog {
    public static final String DEFAULT_RECORD_TEXT = "Unknown - 999";

    private final JLabel noviceRecordLabel;
    private final JLabel mediumRecordLabel;
    private final JLabel expertRecordLabel;

    public HighScoresWindow(JFrame owner) {
        super(owner, "High Scores", true);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        int gridY = 0;

        contentPane.add(createLabel("Novice:", layout, gridY++, 0));
        contentPane.add(noviceRecordLabel = createLabel(DEFAULT_RECORD_TEXT, layout, gridY++, 0));

        contentPane.add(createLabel("Medium:", layout, gridY++, 10));
        contentPane.add(mediumRecordLabel = createLabel(DEFAULT_RECORD_TEXT, layout, gridY++, 0));

        contentPane.add(createLabel("Expert:", layout, gridY++, 10));
        contentPane.add(expertRecordLabel = createLabel(DEFAULT_RECORD_TEXT, layout, gridY, 0));

        contentPane.add(createCloseButton(layout));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(200, 200));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public void setNoviceRecord(String winnerName, int timeValue) {
        noviceRecordLabel.setText(createRecordText(winnerName, timeValue));
    }

    public void setMediumRecord(String winnerName, int timeValue) {
        mediumRecordLabel.setText(createRecordText(winnerName, timeValue));
    }

    public void setExpertRecord(String winnerName, int timeValue) {
        expertRecordLabel.setText(createRecordText(winnerName, timeValue));
    }

    public String createRecordText(String winnerName, int timeValue) {
        return winnerName + " - " + timeValue;
    }

    private JLabel createLabel(String labelText, GridBagLayout layout, int gridY, int margin) {
        JLabel label = new JLabel(labelText);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(margin, 0, 0, 0);
        layout.setConstraints(label, gbc);

        return label;
    }

    private JButton createCloseButton(GridBagLayout layout) {
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        okButton.setPreferredSize(new Dimension(60, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        layout.setConstraints(okButton, gbc);

        return okButton;
    }
}
