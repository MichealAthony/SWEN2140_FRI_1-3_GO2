package cbs.swen.persistence;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Search {

    private String jsonFilePath; // Store the file path

    // Constructor that accepts the file path
    public Search(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath; // Initialize the file path
    }

    // Search method to filter the accused list
    public List<JSONObject> searchAccused(String searchTerm) {
        List<JSONObject> filteredAccusedList = new ArrayList<>();

        try {
            // Read and parse the JSON file
            String json = new String(Files.readAllBytes(Paths.get(jsonFilePath))); 
            JSONArray accusedList = new JSONArray(json);

            // Filter the list based on the search term
            for (int i = 0; i < accusedList.length(); i++) {
                JSONObject accused = accusedList.getJSONObject(i);
                String accusedID = accused.getString("accusedID");
                String name = accused.getString("name");
                String offence = accused.getString("offenceCommitted");

                // Check if the search term matches the ID, name, or offence (case-insensitive)
                if (accusedID.toLowerCase().contains(searchTerm.toLowerCase()) || 
                    name.toLowerCase().contains(searchTerm.toLowerCase()) || 
                    offence.toLowerCase().contains(searchTerm.toLowerCase())) {
                    filteredAccusedList.add(accused);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the filtered list
        return filteredAccusedList;
    }
}
