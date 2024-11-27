package use_case.export_mind_map;

import java.io.File;
import java.awt.image.BufferedImage;

public class ExportInputData {
    private final BufferedImage image;  // Pre-rendered image
    private final File fileToSave;      // File selected by the user
    private final String fileFormat;    // Format (e.g., "png", "jpg", "pdf")

    public ExportInputData(BufferedImage image, File fileToSave, String fileFormat) {
        this.image = image;
        this.fileToSave = fileToSave;
        this.fileFormat = fileFormat;
    }

    public BufferedImage getImage() {
        return image;
    }

    public File getFileToSave() {
        return fileToSave;
    }

    public String getFileFormat() {
        return fileFormat;
    }
}