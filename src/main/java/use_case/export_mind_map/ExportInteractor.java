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
        try {
            // Get the pre-rendered image
            BufferedImage image = inputData.getImage();

            // Save the file
            saveFile(image, inputData.getFileToSave(), inputData.getFileFormat());

            // Notify the presenter of success
            presenter.prepareSuccessView(new ExportOutputData(inputData.getFileToSave().getAbsolutePath()));
        } catch (IOException | DocumentException e) {
            // Notify the presenter of failure
            presenter.prepareFailView("Failed to export: " + e.getMessage());
        }
    }

    private void saveFile(BufferedImage image, File file, String format) throws IOException, DocumentException {
        if (format.equals("png") || format.equals("jpg")) {
            ImageIO.write(image, format, file);
        } else if (format.equals("pdf")) {
            saveAsPdf(image, file);
        } else {
            throw new IOException("Unsupported file format: " + format);
        }
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