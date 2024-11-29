package cbs.swen.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BailCondition extends ReportLog {

    private String entryDetails;

    public BailCondition(String accusedID, String reportingStation, String entryDetails) {
        super(accusedID, reportingStation);  // Call the parent constructor
        this.entryDetails = entryDetails;
    }

    // Format the entry details with date and time, including the day of the week
    public String getFormattedEntry() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm:ss");
        return entryDetails + " (Logged on: " + getReportTime().format(formatter) + ")";
    }

    public String getEntryDetails() {
        return entryDetails;
    }

    public void setEntryDetails(String entryDetails) {
        this.entryDetails = entryDetails;
    }

    
}
