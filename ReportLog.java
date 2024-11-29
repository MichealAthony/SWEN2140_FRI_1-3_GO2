package cbs.swen.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cbs.swen.application.UserSession;

public class ReportLog {

    private String accusedID;
    private String reportingStation;
    private LocalDateTime timestamp;
    private String createdByOfficer;

    // Constructor
    public ReportLog(String accusedID, String reportingStation, String createdByOfficer) {
        this.accusedID = accusedID;
        this.reportingStation = reportingStation;
        this.timestamp = LocalDateTime.now();  // Set timestamp to current time
        this.createdByOfficer = UserSession.getOfficerName();
    }

    // Getter and Setter methods
    public String getAccusedID() {
        return accusedID;
    }

    public String getCreatedByOfficer() {
        return createdByOfficer;
    }

    public void setAccusedID(String accusedID) {
        this.accusedID = accusedID;
    }

    public String getReportingStation() {
        return reportingStation;
    }

    public void setReportingStation(String reportingStation) {
        this.reportingStation = reportingStation;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Method to format timestamp for display, including the day of the week
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    @Override
    public String toString() {
        return "ReportLog [AccusedID=" + accusedID + ", Reporting Station=" + reportingStation + 
               ", Timestamp=" + getFormattedTimestamp() + "]";
    }
}
