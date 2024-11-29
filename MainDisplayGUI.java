package cbs.swen.presentation;

import cbs.swen.application.UserSession;
import cbs.swen.persistence.LoginCheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDisplayGUI {
    
    private JFrame frame;
    private JTextField emailField;
    private JPasswordField passwordField;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                MainDisplayGUI window = new MainDisplayGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainDisplayGUI() {
        initialize();
    }

    private void initialize() {
        // Create the frame
        frame = new JFrame("Hunts Bay Condition of Bail System");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(5, 1));

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome to the Hunts Bay Condition of Bail System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.getContentPane().add(welcomeLabel);

        // Email field
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new FlowLayout());
        emailPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        emailPanel.add(emailField);
        frame.getContentPane().add(emailPanel);

        // Password field
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout());
        passwordPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordField);
        frame.getContentPane().add(passwordPanel);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        frame.getContentPane().add(loginButton);
    }

    public JFrame getFrame(){return frame;};

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the email and password from the form
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
    
            // Validate login using LoginCheck and fetch officer details
            String[] officerDetails = LoginCheck.validateLogin(email, password);
    
            if (officerDetails != null) {
                // Login is successful, set officer details in UserSession
                String officerName = officerDetails[0];
                String officerRegulationNumber = officerDetails[1];
                String officerRank = officerDetails[2];
                UserSession.login(officerName, officerRegulationNumber, officerRank);

                // Show next page
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.setVisible(false);

                // Instantiate and show the next screen (passing UserSession details)
                DisplayPageGUI displayPage = new DisplayPageGUI();
                displayPage.getFrame().setVisible(true);
            } else {
                // Show error message if login fails
                JOptionPane.showMessageDialog(frame, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
