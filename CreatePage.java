package cbs.swen.presentation;

import cbs.swen.domain.Accused;
import cbs.swen.application.UserSession;  // Import UserSession for officer details

import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.Date;
import java.awt.GridLayout;
import java.time.LocalDate; // Import LocalDate for creation date

public class CreatePage {

    private JFrame frame;
    private JTextField nameField, addressField, contactField, offenceField;
    private JSpinner dobSpinner;
    private JComboBox<String> dayComboBox1, dayComboBox2, timeRangeComboBox;
    private JButton saveButton, uploadPhotoButton;
    private String photoPath;

    public CreatePage() {
        initialize();
        frame.setVisible(true); 
    }

    private void initialize() {
        // Create frame
        frame = new JFrame("Create Accused");
        frame.setBounds(100, 100, 450, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(10, 2));

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

        // Add upload photo button
        uploadPhotoButton = new JButton("Upload Photo");
        uploadPhotoButton.addActionListener(new UploadPhotoButtonListener());
        frame.getContentPane().add(uploadPhotoButton);

        // Add Save Button
        saveButton = new JButton("Save Accused");
        saveButton.addActionListener(new SaveButtonListener());
        frame.getContentPane().add(saveButton);

        // Add spacing for layout
        frame.getContentPane().add(new JLabel("")); // Empty space
    }

    private JSpinner createDateSpinner() {
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy-MM-dd"));
        return spinner;
    }

    // Listener for the upload photo button
    private class UploadPhotoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Photo");
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String photoFileName = nameField.getText().replaceAll("\\s+", "_") + ".jpg";
                Path targetPath = Paths.get("/Users/rohanbrown/Downloads/swen/src/resources" + photoFileName);
                try {
                    Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                    photoPath = targetPath.toString();
                    JOptionPane.showMessageDialog(frame, "Photo uploaded successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error uploading photo.");
                }
            }
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Collect data from form fields
            String name = nameField.getText();
            String address = addressField.getText();
            String contactNumber = contactField.getText();
            String offenceCommitted = offenceField.getText();
            Date dob = (Date) dobSpinner.getValue();
            String[] reportingDays = { (String) dayComboBox1.getSelectedItem(), (String) dayComboBox2.getSelectedItem() };
            String reportingTimeRange = (String) timeRangeComboBox.getSelectedItem();

            if (name.isEmpty() || address.isEmpty() || contactNumber.isEmpty() || offenceCommitted.isEmpty() || dob == null || photoPath == null || photoPath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all the fields before saving.");
                return;  // Do not proceed if any field is empty
            }

            // Get officer's name from UserSession (make sure officer is logged in)
            String officerName = UserSession.getOfficerName() +" "+ UserSession.getOfficerRank() + " " + UserSession.getOfficerRegulationNumber();  // Retrieve the officer who is logged in
            System.out.println("Officer Name: " + officerName);  // Debug print
String officerRank = UserSession.getOfficerRank();
String officerRegulationNumber = UserSession.getOfficerRegulationNumber();
System.out.println("Officer Name: " + officerName);  // Should show the officer's name
System.out.println("Officer Rank: " + officerRank);  // Should show the officer's rank
System.out.println("Officer Regulation #: " + officerRegulationNumber);  // Should show the regulation number

            // Create an Accused object with creation date and officer's name
            Accused accused = new Accused(
                name, 
                dob.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(), 
                address, 
                contactNumber, 
                offenceCommitted, 
                reportingDays, 
                reportingTimeRange, 
                photoPath,
                officerName
            );
            
            // Add the created Accused to the list
            Accused.addAccused(accused);
            System.out.println("Accused List: " + Accused.getAccusedList());  // Print the accused list to verify if the object was added
            System.out.println("Accused to be saved: " + accused);

            // Save all accused data to the JSON file
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

    // Getter for the frame so it can be accessed by the main application
    public JFrame getFrame() {
        return frame;
    }
}
