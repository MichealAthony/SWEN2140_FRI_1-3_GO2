package cbs.swen.presentation;

import cbs.swen.domain.*;
import cbs.swen.persistence.*;
import cbs.swen.application.*;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ListPage {

    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField; // Search bar
        private JSONArray accusedList; // Declare accusedList as a class-level variable


    public ListPage() {
        // Create JFrame
        JFrame listFrame = new JFrame("List of Accused");
        listFrame.setSize(600, 500);
        listFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create JPanel and Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Title Label
        JLabel label = new JLabel("List of Accused", JLabel.CENTER);
        panel.add(label, BorderLayout.NORTH);

        // Create search panel with search bar and button
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center search bar and button horizontally
        searchPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT); // Optional: Fixes alignment issues for some cases
        


        // Search bar (slightly bigger)
        searchField = new JTextField(25); // Set to 25 columns wide for a bigger search bar
        searchPanel.add(searchField);

        // Add some space between search bar and button by adding padding/margins
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Horizontal space

        // Search Button with icon
        JButton searchButton = new JButton("Search", new ImageIcon("/path/to/your/icon.png"));
        searchButton.setHorizontalTextPosition(SwingConstants.RIGHT); // Position text to the right of the icon
        searchButton.setVerticalTextPosition(SwingConstants.CENTER); // Text centered vertically with the icon
        
        // Adjust button size to prevent truncation
searchButton.setPreferredSize(new Dimension(150, 20)); // Adjust width and height as needed

// Optional: Make the button text and icon adjust to available space
searchButton.setMaximumSize(new Dimension(150, 40)); // Ensure the button size doesn't grow beyond the set preferred size

        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Instantiate TableFilter class and call the filterTableData method
                TableFilter filter = new TableFilter(model);
                filter.filterTableData(searchField.getText());
            }
        });

        searchPanel.add(searchButton);

        // Adjusting spacing to bring the search bar down a bit
        searchPanel.setPreferredSize(new Dimension(600, 60)); // Setting preferred size to create space

        panel.add(searchPanel, BorderLayout.NORTH); // Add search panel to the top

        // Create table to display data
        String[] columnNames = {"Accused ID", "Name", "Age", "Date of Birth", "Address", "Contact Number", "Offence", "Reporting Days", "Reporting Time", "Created By", "Time Created"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // Read and parse the JSON data to load initial data into the table
        loadTableData();

        // Add table to JScrollPane for better display
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create buttons with icons and add ActionListeners
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Delete Button
        JButton deleteButton = new JButton("Delete", new ImageIcon("delete.png"));
        deleteButton.setToolTipText("Delete");
        deleteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        deleteButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Delete Button ActionListener
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the Accused ID from the selected row (assuming Accused ID is in the first column)
                    String accusedID = (String) model.getValueAt(selectedRow, 0);

                    // Confirm deletion with user
                    int confirmation = JOptionPane.showConfirmDialog(
                            null, 
                            "Are you sure you want to delete this accused?", 
                            "Delete Confirmation", 
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirmation == JOptionPane.YES_OPTION) {
                        // Perform deletion
                        Delete delete = new Delete();
                        delete.deleteAccused(accusedID);

                        // Remove the row from the table model
                        model.removeRow(selectedRow);

                        // Reload the table data from the JSON file to ensure it's in sync
                        loadTableData();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an accused to delete.");
                }
            }
        });

        // Update Button
        JButton updateButton = new JButton("Manage Accused", new ImageIcon("update.png"));
        updateButton.setToolTipText("Update");
        updateButton.setHorizontalTextPosition(SwingConstants.CENTER);
        updateButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Get the Accused ID from the selected row
                        String accusedID = (String) model.getValueAt(selectedRow, 0);
                        System.out.println("Accused ID: " + accusedID);  // Debugging line
        
                        // Open the AccusedDetailPage with the selected Accused ID
                        new AccusedDetailPage(accusedID);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select an accused to update.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();  // Print stack trace to check for any errors
                }
            }
        });
        
        // View Accused Button (added)
        JButton viewAccusedButton = new JButton("View Accused", new ImageIcon("view_icon.png"));
        viewAccusedButton.setToolTipText("View Accused Details");
        viewAccusedButton.setHorizontalTextPosition(SwingConstants.CENTER);
        viewAccusedButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        // View Accused Button ActionListener (currently does nothing)
        viewAccusedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the Accused ID from the selected row
                    String accusedID = (String) model.getValueAt(selectedRow, 0);

                    // Pass the accusedID and full accusedList to the AccusedView
                    new AccusedView(accusedID);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an accused to view.");
                }
            }
        });
         
        

        // Compliance Button
        JButton complianceButton = new JButton("Compliance", new ImageIcon("compliance.png"));
        complianceButton.setToolTipText("Compliance");
        complianceButton.setHorizontalTextPosition(SwingConstants.CENTER);
        complianceButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        complianceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markCompliance();
            }
        });

        // Archive Button
        JButton archiveButton = new JButton("Archive", new ImageIcon("archive.png"));
        archiveButton.setToolTipText("Archive");
        archiveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        archiveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        archiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                archiveAccused();
            }
        });

        //PDF button
        JButton pdfButton = new JButton("Generate PDF", new ImageIcon("pdf_icon.png")); // Replace with your actual icon path
pdfButton.setToolTipText("Generate PDF");
pdfButton.setHorizontalTextPosition(SwingConstants.CENTER);
pdfButton.setVerticalTextPosition(SwingConstants.BOTTOM);

// PDF Button ActionListener
pdfButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Get the Accused ID from the selected row
            String accusedID = (String) model.getValueAt(selectedRow, 0);
            
            // Generate the PDF for the selected accused
            AccusedPDFGenerator pdfGenerator = new AccusedPDFGenerator();
            try {
                pdfGenerator.generateAccusedPDF(accusedID);
                JOptionPane.showMessageDialog(null, "PDF created successfully! Saved as: accused_" + accusedID + ".pdf");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error generating PDF: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an accused to generate a PDF.");
        }
    }
});

        // Add buttons to the panel
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(viewAccusedButton);
        buttonPanel.add(complianceButton);
        buttonPanel.add(archiveButton);
        buttonPanel.add(pdfButton);

        // Add button panel to the bottom of the JFrame
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panel to frame and make it visible
        listFrame.add(panel);
        listFrame.setVisible(true);
    }

    // Method to reload the table data from the JSON file
    private void loadTableData() {
        // Clear current table data
        model.setRowCount(0);

        // Read and parse the JSON data again
        try {
            String json = new String(Files.readAllBytes(Paths.get("/Users/rohanbrown/Downloads/swen/accused_list.json"))); // Update path as needed
            JSONArray accusedList = new JSONArray(json);

            // Loop through the list and add rows to the table
            for (int i = 0; i < accusedList.length(); i++) {
                JSONObject accused = accusedList.getJSONObject(i);
                String accusedID = accused.getString("accusedID");
                String name = accused.getString("name");
                int age = accused.getInt("age");
                String dob = accused.getJSONArray("dob").join("-");
                String address = accused.getString("address");
                String contactNumber = accused.getString("contactNumber");
                String offence = accused.getString("offenceCommitted");
                String reportingDays = accused.getJSONArray("reportingDays").join(", ");
                String reportingTime = accused.getString("reportingTimeRange");
                String createdBy = accused.getString("createdByOfficer"); // New field
                String creationDateStr = accused.optString("formattedCreationDate");

                // Add row to table
                model.addRow(new Object[]{accusedID, name, age, dob, address, contactNumber, offence, reportingDays, reportingTime,createdBy, creationDateStr != null ? creationDateStr : "" });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to update an accused (this is a placeholder for your logic)
    private void updateAccused() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Logic to update the selected accused
            System.out.println("Accused updated.");
        } else {
            JOptionPane.showMessageDialog(null, "Please select an accused to update.");
        }
    }

    // Method to mark an accused as compliant (this is a placeholder for your logic)
    private void markCompliance() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Logic to mark the selected accused as compliant
            System.out.println("Compliance marked.");
        } else {
            JOptionPane.showMessageDialog(null, "Please select an accused to mark compliance.");
        }
    }

    // Method to archive an accused (this is a placeholder for your logic)
    private void archiveAccused() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Get the Accused ID from the selected row (assuming Accused ID is in the first column)
            String accusedID = (String) model.getValueAt(selectedRow, 0);
    
            // Create an instance of Archive class and call the method to archive the selected accused
            Archive archive = new Archive();
            archive.archiveAccusedRecord(accusedID);
    
            // Remove the row from the table model to reflect the archival
            model.removeRow(selectedRow);
    
            // Reload the table data to ensure it is in sync
            loadTableData();
    
            JOptionPane.showMessageDialog(null, "Accused archived successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Please select an accused to archive.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListPage();
            }
        });
    }
}
