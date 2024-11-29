package cbs.swen.persistence;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Archive {

    // Method to archive an accused record
    public void archiveAccusedRecord(String accusedID) {
        try {
            // Load the current list of accused from the JSON file
            String jsonString = new String(Files.readAllBytes(Paths.get("/Users/rohanbrown/Downloads/swen/accused_list.json")));
            
            // Debugging: Print out the content of the JSON file
            System.out.println("accused_list.json content: " + jsonString);  // Debugging line
    
            // Handle case where the file is empty
            if (jsonString.trim().isEmpty()) {
                System.out.println("The accused_list.json file is empty.");
                return;  // Exit early if the file is empty
            }
    
            // Parse the JSON content based on its structure
            JSONArray accusedList;
            try {
                if (jsonString.trim().startsWith("[")) {
                    // It's a JSON array, so parse it as a JSONArray
                    accusedList = new JSONArray(jsonString);
                } else {
                    // It's a JSON object, so wrap it in a JSONArray
                    System.out.println("The JSON is not an array, checking if it is an object...");
                    JSONObject accusedObject = new JSONObject(jsonString);
                    accusedList = new JSONArray();
                    accusedList.put(accusedObject);  // Convert the object into an array
                }
            } catch (org.json.JSONException e) {
                System.out.println("Error parsing JSON: " + e.getMessage());
                e.printStackTrace();
                return;  // Exit if parsing fails
            }
    
            // Find the accused record with the matching accusedID
            JSONObject accusedToArchive = null;
            for (int i = 0; i < accusedList.length(); i++) {
                JSONObject accused = accusedList.getJSONObject(i);
                if (accused.getString("accusedID").equals(accusedID)) {
                    accusedToArchive = accused;
                    break;
                }
            }
    
            if (accusedToArchive != null) {
                // Load the archive JSON file
                String archiveJsonString = new String(Files.readAllBytes(Paths.get("/Users/rohanbrown/Downloads/swen/accused_archive.json")));
                JSONArray archiveList = new JSONArray(archiveJsonString);
    
                // Add the accused record to the archive
                archiveList.put(accusedToArchive);
    
                // Write the updated archive back to the file
                Files.write(Paths.get("/path/to/your/archive.json"), archiveList.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    
                // Remove the archived accused from the original list
                accusedList = removeAccusedByID(accusedList, accusedID);
    
                // Write the updated accused list back to the main JSON file
                Files.write(Paths.get("/path/to/your/accused_list.json"), accusedList.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    
                System.out.println("Accused record archived successfully.");
            } else {
                System.out.println("Accused record not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    // Helper method to remove accused by ID from the JSON array
    private JSONArray removeAccusedByID(JSONArray accusedList, String accusedID) {
        for (int i = 0; i < accusedList.length(); i++) {
            JSONObject accused = accusedList.getJSONObject(i);
            if (accused.getString("accusedID").equals(accusedID)) {
                accusedList.remove(i);
                break;
            }
        }
        return accusedList;
    }
}

