package use_case.export_mind_map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportInteractor implements ExportInputBoundary {
    private final ExportOutputBoundary presenter;

    public ExportInteractor(ExportOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(ExportInputData inputData) {
        // Get the pre-rendered image
        BufferedImage image = inputData.getImage();

        if (validFile(inputData.getFileFormat())) {
            final boolean success = saveFile(image, inputData.getFileToSave(), inputData.getFileFormat());
            if (success) {
                presenter.prepareSuccessView(new ExportOutputData(inputData.getFileToSave().getAbsolutePath()));
                System.out.println("ran successfully");
            }
            else {
                presenter.prepareFailView("Failed to export file. An error occurred during the saving process.");
                System.out.println("ran unsuccessfully");
            }
        }
    }

    private boolean saveFile(BufferedImage image, File file, String format) {
        try {
            if (format.equals("png") || format.equals("jpg")) {
                ImageIO.write(image, format, file);
            } else if (format.equals("pdf")) {
                saveAsPdf(image, file);
            } else {
                return false; // Unsupported file format
            }
            return true; // File saved successfully
        }
        catch (IOException e) {
            // Handle IO exceptions during file saving
            return false;
        }
        catch (DocumentException e) {
            // Handle document-related exceptions for PDF
            return false;
        }
    }

    private boolean validFile(String fileName) {
        return fileName.equals("pdf") || fileName.equals("jpg") || fileName.equals("png");
    }

    private void saveAsPdf(BufferedImage image, File file) throws IOException, DocumentException {
        Document document = new Document(new com.itextpdf.text.Rectangle(image.getWidth(), image.getHeight()));
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(image, null);
        document.add(pdfImage);
        document.close();
    }
}
