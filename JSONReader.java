package cbs.swen.persistence;

import cbs.swen.domain.Accused;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONReader {
    private static final String FILE_PATH = "/Users/rohanbrown/Downloads/swen/accused_list.json"; // Update with the actual path

    // Method to fetch accused details by ID
    public static Accused getAccusedDetailsById(String accusedID) {
        try {
            // Read JSON file as a string
            String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));

            // Parse the string as a JSON array
            JSONArray accusedList = new JSONArray(json);

            // Loop through the list of accused and find the one with the matching accusedID
            for (int i = 0; i < accusedList.length(); i++) {
                JSONObject accusedJson = accusedList.getJSONObject(i);

                // Check if the accused ID matches
                if (accusedJson.getString("accusedID").equals(accusedID)) {
                    // Extract data from the JSON object and create an Accused object
                    String name = accusedJson.getString("name");

                    // Parse the date of birth as LocalDate
                    JSONArray dobArray = accusedJson.getJSONArray("dob");
                    LocalDate dob = LocalDate.of(dobArray.getInt(0), dobArray.getInt(1), dobArray.getInt(2));

                    String address = accusedJson.getString("address");
                    String contactNumber = accusedJson.getString("contactNumber");
                    String offenceCommitted = accusedJson.getString("offenceCommitted");
                    String reportingTimeRange = accusedJson.getString("reportingTimeRange");
                    String photoPath = accusedJson.getString("photoPath");
                    String officerName = accusedJson.getString("createdByOfficer");

                    // Get reportingDays as a String array
                    JSONArray reportingDaysArray = accusedJson.getJSONArray("reportingDays");
                    String[] reportingDays = new String[reportingDaysArray.length()];
                    for (int j = 0; j < reportingDaysArray.length(); j++) {
                        reportingDays[j] = reportingDaysArray.getString(j);
                    }

                    // Create and return the Accused object
                    return new Accused(name, dob, address, contactNumber, offenceCommitted, reportingDays, reportingTimeRange, photoPath, officerName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Return null if accused not found
    }
}
