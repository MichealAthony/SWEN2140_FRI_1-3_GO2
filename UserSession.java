package cbs.swen.application;

public class UserSession {

    private static String officerName;
    private static String officerRegulationNumber;
    private static boolean isLoggedIn = false;
    private static String officerRank;

    // Private constructor to prevent instantiation
    private UserSession() {}

    // Method to set the officer's details after a successful login
    public static void login(String officerName, String officerRegulationNumber, String officerRank) {
        UserSession.officerName = officerName;
        UserSession.officerRegulationNumber = officerRegulationNumber;
        UserSession.officerRank =officerRank;
        UserSession.isLoggedIn = true;
    }

    // Method to clear the officer's details (for logout)
    public static void logout() {
        officerName = null;
        officerRegulationNumber = null;
        isLoggedIn = false;
    }

    public static void setOfficerRank(String rank)
    {officerRank = rank;}


    public static String getOfficerRank()
    { return officerRank;}

    
    public static void setOfficerName(String name) {
        officerName = name;
    }
    

    // Getters for officer details
    public static String getOfficerName() {
        return officerName;
    }

    public static String getOfficerRegulationNumber() {
        return officerRegulationNumber;
    }

    // Check if the user is logged in
    public static boolean isLoggedIn() {
        return isLoggedIn;
    }
}
