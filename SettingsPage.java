package cbs.swen.presentation;

import javax.swing.*;
import java.awt.*;

public class SettingsPage {

    public SettingsPage() {
        JFrame settingsFrame = new JFrame("Settings");
        settingsFrame.setSize(400, 300);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Settings Page", JLabel.CENTER);
        panel.add(label, BorderLayout.CENTER);

        // Add specific settings components here, e.g., text fields, buttons
        settingsFrame.add(panel);
        settingsFrame.setVisible(true);
    }
}