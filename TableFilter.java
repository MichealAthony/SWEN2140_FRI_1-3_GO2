package cbs.swen.persistence;

import cbs.swen.presentation.*;
import cbs.swen.domain.*;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TableFilter {

    private DefaultTableModel model;

    public TableFilter(DefaultTableModel model) {
        this.model = model;
    }

    public void loadTableData() {
        // Clear current table data
        model.setRowCount(0);
    
        try {
            String filePath = "/Users/rohanbrown/Downloads/swen/accused_list.json"; // Path can be configurable
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray accusedList = new JSONArray(json);
    
            // Loop through the list and add all rows to the table
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
                String createdByOfficer = accused.getString("createdByOfficer");
                String creationdate = accused.getString("formattedCreationDate");

    
                // Add row to table
                model.addRow(new Object[]{accusedID, name, age, dob, address, contactNumber, offence, reportingDays, reportingTime,createdByOfficer, creationdate  });
            }
    
        } catch (IOException e) {
            e.printStackTrace(); // Handle as needed
        }
    }
    

    // Method to filter the table data based on the query
    public void filterTableData(String query) {
        // Clear current table data
        model.setRowCount(0);
    
        // Check if the query is empty or contains only whitespace
        if (query.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search term.", "Empty Search", JOptionPane.WARNING_MESSAGE);
            loadTableData(); // Reload all data when the query is empty
            return;
        }
    
        boolean found = false; // Flag to track if any results were found
    
        try {
            String filePath = "/Users/rohanbrown/Downloads/swen/accused_list.json"; // Path can be configurable
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray accusedList = new JSONArray(json);
    
            // Loop through the list and add rows that match the search query
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
                String createdByOfficer = accused.getString("createdByOfficer");
                String creationDate = accused.getString("formattedCreationDate");

    
                // Check if any field matches the query (partial match)
                if (accusedID.toLowerCase().contains(query.toLowerCase()) ||
                    name.toLowerCase().contains(query.toLowerCase()) ||
                    offence.toLowerCase().contains(query.toLowerCase())) {
    
                    // Add matching row to table
                    model.addRow(new Object[]{accusedID, name, age, dob, address, contactNumber, offence, reportingDays, reportingTime, createdByOfficer, creationDate});
                    found = true; // Set the flag to true when a result is found
                }
            }
    
            // If no results were found, show a message
            if (!found) {
                JOptionPane.showMessageDialog(null, "Search not found.", "No Results", JOptionPane.INFORMATION_MESSAGE);
                loadTableData();  
            }
    
        } catch (IOException e) {
            e.printStackTrace(); // Handle as needed
        }
    }
    
}

