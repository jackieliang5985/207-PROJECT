package use_case.export_mind_map;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ExportInputData {
    private final JPanel panel;
    private final String dialogTitle;
    private final List<String> supportedFormats;

    // Constructor
    public ExportInputData(JPanel panel, String dialogTitle, List<String> supportedFormats) {
        this.panel = panel;
        this.dialogTitle = dialogTitle;
        this.supportedFormats = supportedFormats;
    }

    // Getters
    public JPanel getPanel() {
        return panel;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public List<String> getSupportedFormats() {
        return supportedFormats;
    }
}

