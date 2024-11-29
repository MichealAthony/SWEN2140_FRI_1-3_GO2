package cbs.swen.presentation;

import cbs.swen.domain.Accused;
import cbs.swen.domain.ReportLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AccusedDetailPage {

    private JFrame detailFrame;
    private JTextField nameField, ageField, addressField, contactNumberField, offenceField, reportingDaysField, reportingTimeField;
    private JLabel accusedIDLabel;

    public AccusedDetailPage(String accusedID) {
        // Initialize the JFrame for the detail view
        detailFrame = new JFrame("Accused Details");
        detailFrame.setSize(400, 400);
        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailFrame.setLayout(new GridLayout(9, 2));
    
        // Load the list of accused from the JSON file
        Accused.loadAllFromJsonFile();
    
        // Fetch the accused details using the ID
        Accused accused = getAccusedDetailsById(accusedID);
    
        if (accused == null) {
            JOptionPane.showMessageDialog(null, "Accused not found.");
            return;
        }

        // Create labels and text fields for displaying and editing accused details
        accusedIDLabel = new JLabel(accused.getAccusedID());
        nameField = new JTextField(accused.getName());
        ageField = new JTextField(String.valueOf(accused.getAge()));
        addressField = new JTextField(accused.getAddress());
        contactNumberField = new JTextField(accused.getContactNumber());
        offenceField = new JTextField(accused.getOffenceCommitted());

        // Join the reporting days into a comma-separated string
        String reportingDays = String.join(", ", accused.getReportingDays());
        reportingDaysField = new JTextField(reportingDays);

        reportingTimeField = new JTextField(accused.getReportingTimeRange());

        // Add labels and text fields to the frame
        detailFrame.add(new JLabel("Accused ID:"));
        detailFrame.add(accusedIDLabel);
        detailFrame.add(new JLabel("Name:"));
        detailFrame.add(nameField);
        detailFrame.add(new JLabel("Age:"));
        detailFrame.add(ageField);
        detailFrame.add(new JLabel("Address:"));
        detailFrame.add(addressField);
        detailFrame.add(new JLabel("Contact Number:"));
        detailFrame.add(contactNumberField);
        detailFrame.add(new JLabel("Offence:"));
        detailFrame.add(offenceField);
        detailFrame.add(new JLabel("Reporting Days:"));
        detailFrame.add(reportingDaysField);
        detailFrame.add(new JLabel("Reporting Time:"));
        detailFrame.add(reportingTimeField);

        // Create and add buttons for editing, deleting, and updating
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton updateReportingLogButton = new JButton("Update Reporting Log");

        // Action for editing the details
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableFields(true); // Enable text fields for editing
            }
        });

        // Action for deleting the accused
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this accused?",
                        "Delete Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirmation == JOptionPane.YES_OPTION) {
                    // Call delete logic (replace with actual method)
                    deleteAccused(accused.getAccusedID());

                    // Close the detail frame
                    detailFrame.dispose();
                }
            }
        });

        // Action for updating the reporting log
        updateReportingLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to update reporting log (replace with actual method)
                JOptionPane.showMessageDialog(null, "Reporting log updated.");
            }
        });

        // Add buttons to the panel
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateReportingLogButton);

        // Add button panel to the frame
        detailFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Display the frame
        detailFrame.setVisible(true);
    }

    // Fetch accused details based on accused ID
    private Accused getAccusedDetailsById(String accusedID) {
        for (Accused accused : Accused.getAccusedList()) {
            if (accused.getAccusedID().equals(accusedID)) {
                return accused;
            }
        }
        return null;
    }

    public static void openDetailPage(String accusedID) {
        System.out.println("Opening details for accused: " + accusedID);  // Debugging line
        // Your existing code to display details
    }

    // Enable or disable fields for editing
    private void enableFields(boolean enable) {
        nameField.setEditable(enable);
        ageField.setEditable(enable);
        addressField.setEditable(enable);
        contactNumberField.setEditable(enable);
        offenceField.setEditable(enable);
        reportingDaysField.setEditable(enable);
        reportingTimeField.setEditable(enable);
    }

    // Delete the accused from the list and save the changes back to the file
    private void deleteAccused(String accusedID) {
        Accused accusedToDelete = null;
        for (Accused accused : Accused.getAccusedList()) {
            if (accused.getAccusedID().equals(accusedID)) {
                accusedToDelete = accused;
                break;
            }
        }

        if (accusedToDelete != null) {
            Accused.getAccusedList().remove(accusedToDelete);
            Accused.saveAllToJsonFile(); // Save updated list back to the file
            JOptionPane.showMessageDialog(null, "Accused deleted successfully.");
        }
    }
}
