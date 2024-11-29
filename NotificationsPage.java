package cbs.swen.presentation;

import javax.swing.*;
import java.awt.*;

public class NotificationsPage {

    public NotificationsPage() {
        JFrame notificationsFrame = new JFrame("Notifications");
        notificationsFrame.setSize(400, 300);
        notificationsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Notifications Page", JLabel.CENTER);
        panel.add(label, BorderLayout.CENTER);

        // Add notifications-related components here
        notificationsFrame.add(panel);
        notificationsFrame.setVisible(true);
    }
}
