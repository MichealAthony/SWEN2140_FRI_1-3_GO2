package cbs.swen.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginCheck {

    private static final String USERS_FILE = "/Users/rohanbrown/Downloads/swen/src/users.txt";

    // Method to validate login and return officer details (name and regulation number)
    public static String[] validateLogin(String email, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by commas
                String[] userData = line.split(", ");
                String userEmail = null;
                String userPassword = null;
                String officerName = null;
                String officerRegulationNumber = null;
                String officerRank = null;

                // Extract relevant details from the line
                for (String data : userData) {
                    if (data.startsWith("Email:")) {
                        userEmail = data.split(":")[1].trim();
                    } else if (data.startsWith("Password:")) {
                        userPassword = data.split(":")[1].trim();
                    } else if (data.startsWith("Name:")) {
                        officerName = data.split(":")[1].trim();
                    } else if (data.startsWith("Regulation #:")) {
                        officerRegulationNumber = data.split(":")[1].trim();
                    }else if (data.startsWith("Rank:")) {
                        officerRank= data.split(":")[1].trim();
                    }

                }

                // Check if email and password match
                if (userEmail != null && userPassword != null && officerName != null && officerRegulationNumber != null) {
                    if (userEmail.equals(email) && userPassword.equals(password)) {
                        // Return the officer's name and regulation number
                        return new String[]{officerName, officerRegulationNumber, officerRank};
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // If no match found
    }
}
