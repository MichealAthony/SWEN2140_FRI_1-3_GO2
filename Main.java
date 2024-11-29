/*package cbs.swen.presentation;

import java.time.LocalDate;

import cbs.swen.domain.Accused;
import cbs.swen.domain.ReportLog;

public class Main {
    public static void main(String[] args) {
        // Create reporting days and time range for the accused
        String[] reportingDays = {"Monday", "Thursday"};
        String reportingTimeRange = "6am-6pm";
        
        // Create a new Accused object
        Accused accused1 = new Accused("John Doe", 35, LocalDate.of(1989, 5, 10), 
                                       "1234 Elm St.", "555-1234", 
                                       "Theft", reportingDays, reportingTimeRange);

        // Print Accused information
        System.out.println(accused1);

        // Save the accused data to a JSON file
        accused1.saveAllToJsonFile();

        // Log a report for the accused
        accused1.logReport("Central Police Station");

        // Print the report logs for the accused
        System.out.println("Report Logs:");
        for (ReportLog log : accused1.getReportLogs()) {
            System.out.println(log);
        }
    }
    
} */
