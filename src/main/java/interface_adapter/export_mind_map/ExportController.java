package interface_adapter.export_mind_map;

import use_case.export_mind_map.ExportInputBoundary;
import use_case.export_mind_map.ExportInputData;

import java.awt.image.BufferedImage;

import use_case.export_mind_map.ExportInputData;
import use_case.export_mind_map.ExportInteractor;

import javax.swing.*;
import java.util.Arrays;

public class ExportController {
    private final ExportInteractor exportInteractor; // Use ExportInteractor directly

    public ExportController(ExportInteractor exportInteractor) {
        this.exportInteractor = exportInteractor; // Initialize with ExportInteractor
    }

    /**
     * Handles the user's export command and invokes the interactor.
     * @param panel the JPanel to export
     * @param dialogTitle the title for the save dialog
     */
    public void handleExportCommand(JPanel panel, String dialogTitle) {
        // Supported formats
        java.util.List<String> supportedFormats = Arrays.asList("png", "jpg", "pdf");

        // Create input data for the interactor
        ExportInputData inputData = new ExportInputData(panel, dialogTitle, supportedFormats);

        // Call the interactor's execute method
        exportInteractor.execute(inputData); // Use exportInteractor directly
    }
}
