package use_case.export_mind_map;

import com.itextpdf.text.DocumentException;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ExportDataAccess {
    /**
     * Saves the image to the specified file.
     * @param image the image to save
     * @param filePath the file path where the image will be saved
     * @param fileExtension the file extension (png, jpg, pdf, etc.)
     * @throws IOException if an error occurs while writing the image
     * @throws DocumentException if an error occurs while saving the image as a PDF
     */
    void saveImage(BufferedImage image, String filePath, String fileExtension) throws IOException, DocumentException;
}