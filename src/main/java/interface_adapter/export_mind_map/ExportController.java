package interface_adapter.export_mind_map;

import javax.swing.JPanel;

import use_case.export_mind_map.ExportInputData;
import use_case.export_mind_map.ExportInteractor;

/**
 * Controller class for handling the export functionality of the mind map.
 * This class acts as the intermediary between the UI (JPanel) and the export interactor.
 */
public class ExportController {
    private final ExportInteractor exportInteractor;

    /**
     * Constructs an ExportController with the specified export interactor.
     *
     * @param exportInteractor the interactor that handles the export logic
     */
    public ExportController(ExportInteractor exportInteractor) {
        // Initialize with ExportInteractor
        this.exportInteractor = exportInteractor;
    }

    /**
     * Handles the user's export command and triggers the export process.
     * Prepares the required input data from the provided panel and delegates
     * the export logic to the interactor.
     *
     * @param panel the JPanel representing the mind map to be exported
     */
    public void handleExportCommand(JPanel panel) {
        // Prepare input data for export
        final ExportInputData inputData = new ExportInputData(panel);

        // Delegate the export process to the interactor
        exportInteractor.execute(inputData);
    }
}
