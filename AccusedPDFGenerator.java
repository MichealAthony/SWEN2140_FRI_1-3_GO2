package cbs.swen.persistence;

import cbs.swen.domain.*;
import java.time.LocalDate;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;




import java.io.IOException;

public class AccusedPDFGenerator {

    public static void main(String[] args) {
        try {
            // Test with the accused ID: 2024-HBS-0001
            AccusedPDFGenerator generator = new AccusedPDFGenerator();
            generator.generateAccusedPDF("2024-HBS-0001");  // Pass the accusedID you want to search for
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateAccusedPDF(String accusedID) throws Exception {
        // Fetch accused details by ID using the existing JSONReader class
        Accused accused = JSONReader.getAccusedDetailsById(accusedID);

        if (accused != null) {
            // Generate the PDF with the accused details
            String pdfPath = "accused_" + accusedID + ".pdf";  // Save the PDF in the current directory
            createPDF(accused, pdfPath);
            System.out.println("PDF saved to: " + pdfPath);
        } else {
            System.out.println("Accused with ID " + accusedID + " not found.");
        }
    }

    private void createPDF(Accused accused, String pdfPath) throws IOException {
        // Initialize PDF document and page
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Set up content stream for writing
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Set font and size
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
        contentStream.beginText();
        contentStream.newLineAtOffset(200, 750);  // Start position

        // Add title
        contentStream.showText("Condition of Bail Report");
        contentStream.endText();

        // Set font and size for body text
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 700);

        // Accused Information
        contentStream.showText("Accused ID: " + accused.getAccusedID());
        contentStream.newLineAtOffset(0, -15); // Move down for next line
        contentStream.showText("Name: " + accused.getName());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Age: " + accused.getAge());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Date of Birth: " + accused.getDob().getMonthValue() + "/" + accused.getDob().getDayOfMonth() + "/" + accused.getDob().getYear());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Address: " + accused.getAddress());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Contact Number: " + accused.getContactNumber());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Offence Committed: " + accused.getOffenceCommitted());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Reporting Days: " + String.join(", ", accused.getReportingDays()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Reporting Time Range: " + accused.getReportingTimeRange());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Record Created BY: " + accused.getCreatedByOfficer());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Record Created On: " + accused.getFormattedCreationDate());



        contentStream.endText();

        // Close the content stream
        contentStream.close();

        // Save the document to file
        document.save(pdfPath);
        document.close();
    }
}