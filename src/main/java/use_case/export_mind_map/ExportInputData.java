package use_case.export_mind_map;

import javax.swing.*;

public class ExportInputData {
    private final JPanel panel;

    // Constructor
    public ExportInputData(JPanel panel) {
        this.panel = panel;
    }

    // Getters
    public JPanel getPanel() {
        return panel;
    }
}