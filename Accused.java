package cbs.swen.domain;

import cbs.swen.application.UserSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;  // Import the module
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Accused {

    // Static variable to track the Accused ID for incrementing
    private static int accusedCount = 0;

    private static List<Accused> accusedList = new ArrayList<>();

    // Instance variables for Accused information
    private String accusedID;
    private String name;
    private int age;
    private LocalDate dob;
    private String address;
    private String contactNumber;
    private String offenceCommitted;
    private String[] reportingDays; // Two days of the week
    private String reportingTimeRange; // Time range such as "6am-6pm"
    private List<ReportLog> reportLogs;
    private String photoPath;

    // Additional fields for logging officer and creation date
    private String createdByOfficer; // Name of the officer who created the entry
    private LocalDateTime creationDate; // Date and time when the entry was created
    //private String rank;
    //private String regulationNumber;

    public Accused() {
        // Initialize fields, if needed
    }

    // Constructor to initialize Accused object
    public Accused(String name, LocalDate dob, String address, String contactNumber, 
                   String offenceCommitted, String[] reportingDays, String reportingTimeRange, String photoPath, String officerName) {
        // Increment the accusedCount and generate the Accused ID
        accusedCount++;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String currentYear = LocalDate.now().format(formatter);
        this.accusedID = currentYear + "-HBS-" + String.format("%04d", accusedCount);
        
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.contactNumber = contactNumber;
        this.offenceCommitted = offenceCommitted;
        this.reportingDays = reportingDays;
        this.reportingTimeRange = reportingTimeRange;
        this.reportLogs = new ArrayList<>();
        this.photoPath = photoPath;
        this.age = Period.between(dob, LocalDate.now()).getYears();

        // Track the officer who created the entry and the creation date
        this.createdByOfficer = UserSession.getOfficerName() + " " + UserSession.getOfficerRank() + " " + UserSession.getOfficerRegulationNumber();  // Assuming the officer's name is stored in the session
        this.creationDate = LocalDateTime.now();  // Set the creation date to current time
    }

    // Getter and Setter methods for each attribute

        // Method to get formatted creationDate as a string
        public String getFormattedCreationDate() {
        if (creationDate != null) {
            // Custom DateTimeFormatter to exclude seconds
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            return creationDate.format(formatter);  // Format without seconds
        }
        return "";
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void logReport(String reportingStation) {
        ReportLog newReport = new ReportLog(this.accusedID, reportingStation, createdByOfficer);
        reportLogs.add(newReport);
    }

    public List<ReportLog> getReportLogs() {
        return reportLogs;
    }

    public static void saveAllToJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Register the JavaTimeModule to support LocalDate, LocalDateTime, etc.
        objectMapper.registerModule(new JavaTimeModule());

        try {
            // Specify the file path where the accused info will be saved
            File file = new File("accused_list.json");

            // Write the list of Accused objects to the JSON file
            objectMapper.writeValue(file, accusedList);
            System.out.println("All accused data saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Static method to load all accused from a JSON file
    public static void loadAllFromJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Register the JavaTimeModule to support LocalDate, LocalDateTime, etc.
        objectMapper.registerModule(new JavaTimeModule());

        try {
            // Specify the file path where the accused info is stored
            File file = new File("accused_list.json");

            // Read the list of Accused objects from the JSON file
            if (file.exists()) {
                accusedList = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Accused.class));
                System.out.println("All accused data loaded from " + file.getAbsolutePath());
            } else {
                System.out.println("No existing accused data found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAccusedID() {
        return accusedID;
    }

    public static List<Accused> getAccusedList() {
        return accusedList;
    }

    public void setAccusedID(String accusedID) {
        this.accusedID = accusedID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getOffenceCommitted() {
        return offenceCommitted;
    }

    public void setOffenceCommitted(String offenceCommitted) {
        this.offenceCommitted = offenceCommitted;
    }

    public String[] getReportingDays() {
        return reportingDays;
    }

    public void setReportingDays(String[] reportingDays) {
        this.reportingDays = reportingDays;
    }

    public String getReportingTimeRange() {
        return reportingTimeRange;
    }

    public void setReportingTimeRange(String reportingTimeRange) {
        this.reportingTimeRange = reportingTimeRange;
    }

    public static void addAccused(Accused accused) {
        // Check for duplication before adding
        if (!accusedList.contains(accused)) {
            accusedList.add(accused);
        }
    }
/// set name
    public void setCreatedByOfficer(String officerName) {
        this.createdByOfficer = officerName;
    }

    public String getCreatedByOfficer(
    ){return createdByOfficer;}
    
    public void setCreationDate(LocalDateTime creationdate)
    {this.creationDate = creationdate;}

    public LocalDateTime getCreationDate()
    {return creationDate;}
    
    /* public void setRank(String rank)
    {this.rank = rank;}

    public String getRank()
    {return rank;}

    public void setRegulationNumber(String regulationNumber)
    {this.regulationNumber = regulationNumber;}

    public String getRegulationNumber()
    {return regulationNumber;} */

    // Method to display Accused information
    @Override
    public String toString() {
        return "Accused [AccusedID=" + accusedID + ", Name=" + name + ", Age=" + age + 
               ", Date of Birth=" + dob + ", Address=" + address + ", Contact Number=" + 
               contactNumber + ", Offence Committed=" + offenceCommitted + 
               ", Reporting Days=" + String.join(", ", reportingDays) + 
               ", Reporting Time Range=" + reportingTimeRange + 
               ", Photo Path=" + photoPath + 
               ", Created By=" + createdByOfficer + 
               ", Creation Date=" + creationDate + "]";
    }
}
