package cbs.swen.presentation;

import cbs.swen.domain.*;
import cbs.swen.persistence.JSONReader;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;


public class AccusedView {

    private String accusedID;

    public AccusedView(String accusedID) {
        this.accusedID = accusedID;
        initialize();
    }

    private void initialize() {
    // Create a new JFrame for viewing the accused details
    JFrame viewFrame = new JFrame("Accused Details");
    viewFrame.setSize(800, 600);
    viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Create a JPanel with BorderLayout for overall structure
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(20, 20));  // 20px padding between components
    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

    // Get the accused data from the JSON reader
    Accused accused = JSONReader.getAccusedDetailsById(accusedID);

    if (accused != null) {
        // Panel for the photo (top-left section)
        String photoPath = accused.getPhotoPath();
        ImageIcon photoIcon = resizeImage(photoPath, 100, 100);  // Resize image to 100x100 pixels
        JLabel photoImage = new JLabel(photoIcon);
        photoImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Panel for the accused information (top-right section)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(6, 2, 10, 10));  // Adding padding between labels and fields

        // Add Labels for accused details
        infoPanel.add(new JLabel("Name: "));
        infoPanel.add(new JLabel(accused.getName()));
        infoPanel.add(new JLabel("Age: "));
        infoPanel.add(new JLabel(String.valueOf(accused.getAge())));
        infoPanel.add(new JLabel("Date of Birth: "));
        infoPanel.add(new JLabel(accused.getDob().toString())); // Use the LocalDate's toString method
        infoPanel.add(new JLabel("Address: "));
        infoPanel.add(new JLabel(accused.getAddress()));
        infoPanel.add(new JLabel("Contact Number: "));
        infoPanel.add(new JLabel(accused.getContactNumber()));
        infoPanel.add(new JLabel("Offence: "));
        infoPanel.add(new JLabel(accused.getOffenceCommitted()));

        // Create a container for the photo and info panel (top-left and top-right)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(20, 20));
        topPanel.add(photoImage, BorderLayout.WEST);  // Photo to the left
        topPanel.add(infoPanel, BorderLayout.CENTER);  // Info to the right

        // Add the topPanel to the mainPanel
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panel for reporting logs (below the accused information)
        String[] columns = {"Log Date", "Report", "Created By"};  // Added "Created By" column

        // Initialize the table data model with the current report logs
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Populate the data model with existing logs
        for (ReportLog reportLog : accused.getReportLogs()) {
            model.addRow(new Object[]{
                reportLog.getFormattedTimestamp(),
                reportLog.getReportingStation(),
                reportLog.getCreatedByOfficer()
            });
        }

        JTable reportTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(reportTable);

        // Add the table to the main panel below the accused details
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a button to add a new log
        JButton addLogButton = new JButton("Add Log");
        addLogButton.addActionListener(e -> {
            String station = JOptionPane.showInputDialog(viewFrame, "Enter reporting station:");
            String createdBy = accused.getCreatedByOfficer(); // New field
            if (station != null && !station.isEmpty()) {
                // Add the new ReportLog
                ReportLog newLog = new ReportLog(accused.getAccusedID(), station,createdBy );
                accused.logReport(station);  // Add the new report to the accused's list

                // Update the table by adding the new log to the data model
                model.addRow(new Object[]{
                    newLog.getFormattedTimestamp(),
                    newLog.getReportingStation(),
                    newLog.getCreatedByOfficer()
                });

                // Save the updated list of accused back to the JSON file
                Accused.saveAllToJsonFile();  // This will persist the changes to the JSON file
            }
        });

        // Create a panel for the button at the bottom and center it
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addLogButton);

        // Add the button panel at the bottom of the main panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame and make it visible
        viewFrame.add(mainPanel);
        viewFrame.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(null, "Accused not found.");
    }
}

    
    // Method to resize the image to the specified width and height
    private ImageIcon resizeImage(String imagePath, int width, int height) {
        try {
            // Load the image from the file path
            BufferedImage img = ImageIO.read(new File(imagePath));
            // Resize the image
            Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImg);  // Return the resized image as an ImageIcon
        } catch (IOException e) {
            e.printStackTrace();
            return null;  // Return null if the image loading fails
        }
    }
    private void updateLogTable(JTable table, Accused accused) {
        // Convert ReportLogs to an array for JTable
        Object[][] data = new Object[accused.getReportLogs().size()][2];
        for (int i = 0; i < accused.getReportLogs().size(); i++) {
            ReportLog reportLog = accused.getReportLogs().get(i);
            data[i][0] = reportLog.getFormattedTimestamp();
            data[i][1] = reportLog.getReportingStation();
        }
        // Update the table model with new data
        table.setModel(new javax.swing.table.DefaultTableModel(
            data,
            new String[]{"Log Date", "Report"}
        ));


}
}