package cbs.swen.persistence;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import cbs.swen.domain.Accused;

public class UploadPhotoButtonListener implements ActionListener {

    private Accused accused;  // Store the Accused object

    // Constructor to accept the Accused object
    public UploadPhotoButtonListener(Accused accused) {
        this.accused = accused;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Open file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a Photo");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();
            String accusedName = accused.getName().replace(" ", "_");  // Replace spaces with underscores
            Path targetDirectory = Paths.get("src", "main", "resources");

            // Ensure the target directory exists
            if (!Files.exists(targetDirectory)) {
                try {
                    Files.createDirectories(targetDirectory);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error creating directories: " + ioException.getMessage());
                    return;
                }
            }

            // Prepare the target file path (e.g., resources/Rohan_Brown.jpg)
            Path targetPath = targetDirectory.resolve(accusedName + ".jpg");

            try {
                // Copy the selected file to the resources folder
                Files.copy(selectedFile.toPath(), targetPath);
                
                // Save the file path in the accused object
                accused.setPhotoPath(targetPath.toString()); // Assuming you added a setPhotoPath method in the Accused class
                
                // Optionally, display the path to confirm
                System.out.println("Photo saved to: " + targetPath.toString());
                JOptionPane.showMessageDialog(null, "Photo uploaded successfully!");
                
            } catch (IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error uploading photo: " + ioException.getMessage());
            }
        }
    }
}
