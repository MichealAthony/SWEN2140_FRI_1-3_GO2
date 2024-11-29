package cbs.swen.persistence;

import cbs.swen.domain.*;
import cbs.swen.presentation.*;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Delete {

    // Path to your JSON file containing the accused data
    private static final String ACCUSED_FILE_PATH = "/Users/rohanbrown/Downloads/swen/accused_list.json";
    // Path to your users.txt file
    private static final String USERS_FILE_PATH = "/Users/rohanbrown/Downloads/swen/src/users.txt";

    // Method to trigger the deletion process
    public void deleteAccused(String accusedID) {
        // Ask for role and clearance code
        String role = JOptionPane.showInputDialog(null, "Enter your role (e.g., supervisor):");
        String clearanceCode = JOptionPane.showInputDialog(null, "Enter your clearance code:");

        // Validate role and clearance code
        if (role != null && clearanceCode != null) {
            if (isValidRoleAndClearance(role, clearanceCode)) {
                // Confirm deletion
                int confirmation = JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to delete the entry with Accused ID: " + accusedID + "?", 
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                
                if (confirmation == JOptionPane.YES_OPTION) {
                    // Proceed with deletion
                    if (deleteFromFile(accusedID)) {
                        JOptionPane.showMessageDialog(null, "Accused entry deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Accused entry not found.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid role or clearance code.");
            }
        }
    }

    // Check if the provided role and clearance code match an entry in users.txt
    private boolean isValidRoleAndClearance(String role, String clearanceCode) {
        try {
            // Read users.txt and verify if the role and clearance code match
            BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(", ");
                String userRole = null;
                String userClearanceCode = null;

                // Parse the user information
                for (String info : userInfo) {
                    if (info.startsWith("Role: ")) {
                        userRole = info.split(": ")[1];
                    }
                    if (info.startsWith("ClearanceCode: ")) {
                        userClearanceCode = info.split(": ")[1];
                    }
                }

                // Check if role and clearance code match
                if (userRole != null && userClearanceCode != null) {
                    if (userRole.equalsIgnoreCase(role) && userClearanceCode.equals(clearanceCode)) {
                        return true;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete the entry from the accused JSON file
    private boolean deleteFromFile(String accusedID) {
        try {
            // Read the accused JSON file into a JSONArray
            BufferedReader reader = new BufferedReader(new FileReader(ACCUSED_FILE_PATH));
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
            reader.close();
            
            // Parse the JSON data into a JSONArray
            JSONArray accusedArray = new JSONArray(jsonData.toString());
            boolean deleted = false;

            // Loop through the array and search for the accused ID to delete
            for (int i = 0; i < accusedArray.length(); i++) {
                JSONObject accused = accusedArray.getJSONObject(i);
                if (accused.getString("accusedID").equals(accusedID)) {
                    accusedArray.remove(i);  // Remove the accused entry
                    deleted = true;
                    break;
                }
            }

            // If an entry was deleted, write the updated array back to the file
            if (deleted) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(ACCUSED_FILE_PATH));
                writer.write(accusedArray.toString(4)); // Pretty-print with 4 spaces
                writer.close();
            }

            return deleted;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
