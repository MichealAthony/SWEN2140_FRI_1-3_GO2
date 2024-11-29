package cbs.swen.presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;

import cbs.swen.domain.*;

import java.util.Date;

public class AccusedGUI {

    private JFrame frame;
    private JTextField nameField, addressField, contactField, offenceField;
    private JSpinner dobSpinner;
    private JComboBox<String> dayComboBox1, dayComboBox2, timeRangeComboBox;
    private JButton saveButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                AccusedGUI window = new AccusedGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AccusedGUI() {
        // Load existing Accused data when the GUI is initialized
        Accused.loadAllFromJsonFile();
        initialize();
    }

    private void initialize() {
        // Create frame
        frame = new JFrame("Accused Information");
        frame.setBounds(100, 100, 450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(9, 2));

        // Add form fields
        frame.getContentPane().add(new JLabel("Name:"));
        nameField = new JTextField();
        frame.getContentPane().add(nameField);

        frame.getContentPane().add(new JLabel("Address:"));
        addressField = new JTextField();
        frame.getContentPane().add(addressField);

        frame.getContentPane().add(new JLabel("Contact Number:"));
        contactField = new JTextField();
        frame.getContentPane().add(contactField);

        frame.getContentPane().add(new JLabel("Offence Committed:"));
        offenceField = new JTextField();
        frame.getContentPane().add(offenceField);

        frame.getContentPane().add(new JLabel("Date of Birth:"));
        dobSpinner = createDateSpinner();
        frame.getContentPane().add(dobSpinner);

        frame.getContentPane().add(new JLabel("Reporting Day 1:"));
        dayComboBox1 = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
        frame.getContentPane().add(dayComboBox1);

        frame.getContentPane().add(new JLabel("Reporting Day 2:"));
        dayComboBox2 = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
        frame.getContentPane().add(dayComboBox2);

        frame.getContentPane().add(new JLabel("Reporting Time Range:"));
        timeRangeComboBox = new JComboBox<>(new String[]{
            "08:00 AM - 12:00 PM",
            "12:00 PM - 04:00 PM",
            "04:00 PM - 08:00 PM",
            "08:00 PM - 12:00 AM",
            "06:00 AM - 6:00 PM",
        });
        frame.getContentPane().add(timeRangeComboBox);

        // Add Save Button
        saveButton = new JButton("Save Accused");
        saveButton.addActionListener(new SaveButtonListener());
        frame.getContentPane().add(saveButton);

        // Add spacing for layout
        frame.getContentPane().add(new JLabel("")); // Empty space
    }

    private JSpinner createDateSpinner() {
        // Create a date model for the JSpinner
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy-MM-dd"));
        return spinner;
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Collect data from form fields
            String name = nameField.getText();
            String address = addressField.getText();
            String contactNumber = contactField.getText();
            String offenceCommitted = offenceField.getText();
            Date dob = (Date) dobSpinner.getValue();  // Retrieve the selected date
            String[] reportingDays = { (String) dayComboBox1.getSelectedItem(), (String) dayComboBox2.getSelectedItem() };
            String reportingTimeRange = (String) timeRangeComboBox.getSelectedItem(); // Get selected time range

            // Create an Accused object
          //  Accused accused = new Accused(name, 0, dob.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(), address, contactNumber, offenceCommitted, reportingDays, reportingTimeRange);

            // Add this accused to the list
           // Accused.addAccused(accused);

            // Save the entire list of accused to the JSON file
            Accused.saveAllToJsonFile();

            // Show confirmation message
            JOptionPane.showMessageDialog(frame, "Accused information saved successfully!");

            // Clear form fields after saving
            nameField.setText("");
            addressField.setText("");
            contactField.setText("");
            offenceField.setText("");
            timeRangeComboBox.setSelectedIndex(0); // Reset the time range selection
            dobSpinner.setValue(new Date());  // Reset date spinner
            dayComboBox1.setSelectedIndex(0);
            dayComboBox2.setSelectedIndex(0);
        }
    }
}
