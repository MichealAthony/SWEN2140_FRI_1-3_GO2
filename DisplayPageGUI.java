package cbs.swen.presentation;

import cbs.swen.application.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayPageGUI {

    private JFrame frame;

    public DisplayPageGUI() {
        initialize();
    }

    private void initialize() {
        // Create the frame
        frame = new JFrame("Display Page");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Add the logo at the top
        ImageIcon logoIcon = new ImageIcon("/Users/rohanbrown/Downloads/swen/src/resources/jcflogo.png");  // Change this path to your logo's location
        JLabel logoLabel = new JLabel(logoIcon);
        frame.getContentPane().add(logoLabel, BorderLayout.NORTH);  // Add logo to the top of the frame

        // Add the main content with text below the logo
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Hunts Bay Police Station Condition of Bail System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);
        frame.getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Add the buttons at the bottom (centered and slightly raised)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // Center-aligned buttons
        buttonPanel.setPreferredSize(new Dimension(600, 50));  // Height for the buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));  // Padding from the bottom

        // Load icons for the buttons
        ImageIcon settingsIcon = new ImageIcon("src/resources/settigs.png");
        ImageIcon listIcon = new ImageIcon("src/resources/lst.png");
        ImageIcon createIcon = new ImageIcon("src/resources/crate.png");
        ImageIcon notificationsIcon = new ImageIcon("src/resources/notifiations.png");

        // Create buttons with icons and text
        JButton settingsButton = new JButton("Settings", settingsIcon);
        settingsButton.setToolTipText("Settings");
        settingsButton.setHorizontalTextPosition(SwingConstants.LEFT);  // Icon on the left
        settingsButton.setVerticalTextPosition(SwingConstants.CENTER);  // Text centered vertically with icon
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsPage();  // Open the Settings Page
            }
        });

        JButton listButton = new JButton("List of Accused", listIcon);
        listButton.setToolTipText("List of Accused");
        listButton.setHorizontalTextPosition(SwingConstants.LEFT);
        listButton.setVerticalTextPosition(SwingConstants.CENTER);
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListPage();  // Open the List Page
            }
        });

        JButton createButton = new JButton("Create Accused", createIcon);
        createButton.setToolTipText("Create Accused");
        createButton.setHorizontalTextPosition(SwingConstants.LEFT);
        createButton.setVerticalTextPosition(SwingConstants.CENTER);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreatePage();  // Open the Create Accused Page
            }
        });

        JButton notificationsButton = new JButton("Notifications", notificationsIcon);
        notificationsButton.setToolTipText("Notifications");
        notificationsButton.setHorizontalTextPosition(SwingConstants.LEFT);
        notificationsButton.setVerticalTextPosition(SwingConstants.CENTER);
        notificationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NotificationsPage();  // Open the Notifications Page
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(notificationsButton);
        buttonPanel.add(createButton);
        buttonPanel.add(listButton);
        buttonPanel.add(settingsButton);

        // Add Logout button to the button panel
        JButton logoutButton = new JButton("Logout");
        logoutButton.setToolTipText("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform logout by clearing the session details
                UserSession.logout();  // Clear officer session

                // Close the current DisplayPageGUI window
                frame.setVisible(false);  // Hide the current page

                // Open the MainDisplayGUI again (login screen)
                MainDisplayGUI mainDisplay = new MainDisplayGUI();
                mainDisplay.getFrame().setVisible(true);
            }
        });

        // Add Logout button to the button panel
        buttonPanel.add(logoutButton);

        // Add button panel to the frame
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getter for the frame so it can be accessed in MainDisplayGUI
    public JFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DisplayPageGUI window = new DisplayPageGUI();
                window.getFrame().setVisible(true);
            }
        });
    }
}
