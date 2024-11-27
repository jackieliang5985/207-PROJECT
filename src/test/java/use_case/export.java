
package use_case.export;

import org.junit.jupiter.api.Test;
import use_case.export_mind_map.*;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ExportInteractorTest {

    @Test
    void successTest() {
        // Arrange input data
        ExportInputData inputData = new ExportInputData(createTestImage(), new File("output.png"), "png");

        // Mock Output Boundary
        ExportOutputBoundary successPresenter = new ExportOutputBoundary() {
            @Override
            public void prepareSuccessView(ExportOutputData outputData) {
                // Verify that the success result contains the correct file path
                String filePath = outputData.getFilePath(); // Full file path
                String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
                assertEquals("output.png", fileName);
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        // Create the interactor
        ExportInputBoundary interactor = new ExportInteractor(successPresenter);

        // Act
        interactor.execute(inputData);
    }

    @Test
    void failureInvalidFileFormatTest() {
        // Arrange input data with invalid format
        ExportInputData inputData = new ExportInputData(createTestImage(), new File("output.invalid"), "invalid");

        // Mock Output Boundary
        ExportOutputBoundary failurePresenter = new ExportOutputBoundary() {
            @Override
            public void prepareSuccessView(ExportOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                // Verify the correct error message
                assertEquals("Failed to export: Unsupported file format: invalid", errorMessage);
            }
        };

        // Create the interactor
        ExportInputBoundary interactor = new ExportInteractor(failurePresenter);

        // Act
        interactor.execute(inputData);
    }

    // Utility method to create a test image
    private BufferedImage createTestImage() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().fillRect(0, 0, 100, 100); // Fill with a solid color
        return image;
    }
}