package interface_adapter.export_mind_map;

import use_case.export_mind_map.ExportInputBoundary;
import use_case.export_mind_map.ExportInputData;

import java.awt.image.BufferedImage;
import java.io.File;

public class ExportController {
    private final ExportInputBoundary interactor;

    public ExportController(ExportInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(BufferedImage image, File fileToSave, String fileFormat) {
        ExportInputData inputData = new ExportInputData(image, fileToSave, fileFormat);
        interactor.execute(inputData);
    }
}