package use_case.export_mind_map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import use_case.export_mind_map.ExportInputBoundary;
import use_case.export_mind_map.ExportInputData;
import use_case.export_mind_map.ExportOutputBoundary;
import use_case.export_mind_map.ExportOutputData;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
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
            // Capture the JPanel as a BufferedImage
            BufferedImage screenshot = new BufferedImage(
                    inputData.getPanel().getWidth(),
                    inputData.getPanel().getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g2d = screenshot.createGraphics();
            inputData.getPanel().paint(g2d);
            g2d.dispose();

            // Handle file saving logic
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Mind Map");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Image (*.png)", "png"));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG Image (*.jpg)", "jpg"));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Document (*.pdf)", "pdf"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                String selectedExtension = getFileExtension(fileChooser);

                if (!fileToSave.getName().toLowerCase().endsWith("." + selectedExtension)) {
                    fileToSave = new java.io.File(fileToSave.getAbsolutePath() + "." + selectedExtension);
                }

                if (selectedExtension.equals("png") || selectedExtension.equals("jpg")) {
                    ImageIO.write(screenshot, selectedExtension, fileToSave);
                } else if (selectedExtension.equals("pdf")) {
                    saveAsPdf(screenshot, fileToSave);
                }

                presenter.prepareSuccessView(new ExportOutputData(fileToSave.getAbsolutePath()));
            }
        } catch (IOException | DocumentException e) {
            presenter.prepareFailView(e.getMessage());
        }
    }

    private static String getFileExtension(JFileChooser fileChooser) {
        String description = fileChooser.getFileFilter().getDescription();
        if (description.contains("PNG")) {
            return "png";
        } else if (description.contains("JPEG")) {
            return "jpg";
        } else if (description.contains("PDF")) {
            return "pdf";
        }
        return "";
    }

    private void saveAsPdf(BufferedImage image, java.io.File file) throws DocumentException, IOException {
        // Convert dimensions to float
        float width = (float) image.getWidth();
        float height = (float) image.getHeight();

        // Create a rectangle with the dimensions of the image
        Document document = new Document(new com.itextpdf.text.Rectangle(width, height));
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        // Convert the BufferedImage to an iText Image
        com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(image, null);

        // Add the image to the PDF
        document.add(pdfImage);
        document.close();
    }
}