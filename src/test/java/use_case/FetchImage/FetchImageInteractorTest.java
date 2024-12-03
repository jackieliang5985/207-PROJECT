package use_case.FetchImage;

import data_access.SimpleImage;
import entity.CommonImage;
import data_access.InMemoryFetchImageDataAccessObject;
import interface_adapter.fetch_image.FetchImagePresenter;
import interface_adapter.fetch_image.FetchImageViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.fetch_image.FetchImageInputData;
import use_case.fetch_image.FetchImageInteractor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FetchImageInteractorTest {

    private InMemoryFetchImageDataAccessObject imageRepository;
    private FetchImageViewModel viewModel;
    private FetchImagePresenter fetchImagePresenter;
    private FetchImageInteractor interactor;

    @BeforeEach
    void setUp() {
        // Initialize the repository and presenter
        imageRepository = new InMemoryFetchImageDataAccessObject();
        viewModel = new FetchImageViewModel();
        fetchImagePresenter = new FetchImagePresenter(viewModel);
        interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);
    }

    @Test
    void testExecute_Success() throws Exception {
        // Arrange: Add images to the repository
        CommonImage image1 = new CommonImage("https://images.unsplash.com/photo-1591729651527-4d09a51b1e2c", "dad", "2");
        CommonImage image2 = new CommonImage("https://images.unsplash.com/photo-1549068294-04a001ee0638", "dad", "1");
        imageRepository.save(new SimpleImage(image1.getUrl(), image1.getDescription(), image1.getId()));
        imageRepository.save(new SimpleImage(image2.getUrl(), image2.getDescription(), image2.getId()));

        // Create FetchImageInputData for the query "dad"
        FetchImageInputData inputData = new FetchImageInputData("dad");

        // Act: Execute the interactor
        interactor.execute(inputData);

        // Assert: Verify that the images are returned
        assertEquals(2, viewModel.getImages().size(), "Expected two images to be found.");
        assertEquals("dad", viewModel.getImages().get(0).getDescription());
        assertEquals("dad", viewModel.getImages().get(1).getDescription());
    }

    @Test
    void testExecute_NoResults() throws Exception {
        // Create FetchImageInputData for a query that won't match any images
        FetchImageInputData inputData = new FetchImageInputData("nonexistent");

        // Act: Execute the interactor
        interactor.execute(inputData);

        // Assert: Verify that the error message is set in the view model
        assertEquals("Invalid Search: No results found.", viewModel.getErrorMessage());
    }

    @Test
    void testExecute_Failure() {
        // Simulate a failure by throwing an exception from the repository
        InMemoryFetchImageDataAccessObject faultyRepository = new InMemoryFetchImageDataAccessObject() {
            public List<SimpleImage> findByQuery(String query) {
                throw new RuntimeException("Error fetching images");
            }
        };

        // Create the interactor with the faulty repository
        FetchImageInteractor faultyInteractor = new FetchImageInteractor(faultyRepository, fetchImagePresenter);
        FetchImageInputData inputData = new FetchImageInputData("error");

        // Act & Assert: Verify that an exception is thrown
        Exception exception = assertThrows(RuntimeException.class, () -> {
            faultyInteractor.execute(inputData);
        });

        assertEquals("Error fetching images", exception.getMessage());
    }

    @Test
    void testExecute_EmptyRepository() throws Exception {
        // Create FetchImageInputData for a query
        FetchImageInputData inputData = new FetchImageInputData("sunset");

        // Act: Execute the interactor with an empty repository
        interactor.execute(inputData);

        // Assert: Verify that no images are returned
        assertTrue(viewModel.getImages().isEmpty(), "Expected no results when repository is empty.");
    }

    @Test
    void testExecute_CaseInsensitive() throws Exception {
        // Arrange: Add images with descriptions in different cases
        CommonImage image1 = new CommonImage("https://images.unsplash.com/photo-1591729651527-4d09a51b1e2c", "Sunset", "2");
        CommonImage image2 = new CommonImage("https://images.unsplash.com/photo-1549068294-04a001ee0638", "sunset", "1");
        imageRepository.save(new SimpleImage(image1.getUrl(), image1.getDescription(), image1.getId()));
        imageRepository.save(new SimpleImage(image2.getUrl(), image2.getDescription(), image2.getId()));

        // Create FetchImageInputData for a case-insensitive query
        FetchImageInputData inputData = new FetchImageInputData("Sunset");

        // Act: Execute the interactor
        interactor.execute(inputData);

        // Assert: Verify that both images are returned (case-insensitive match)
        assertEquals(2, viewModel.getImages().size(), "Expected 2 images to be found, ignoring case.");
    }

    @Test
    void testExecute_SpecialCharacters() throws Exception {
        // Arrange: Add an image with a description containing special characters
        CommonImage image1 = new CommonImage("https://images.unsplash.com/photo-1591729651527-4d09a51b1e2c", "Beach Sunset", "2");
        imageRepository.save(new SimpleImage(image1.getUrl(), image1.getDescription(), image1.getId()));

        // Create FetchImageInputData for a query with special characters
        FetchImageInputData inputData = new FetchImageInputData("Beach Sunset");

        // Act: Execute the interactor
        interactor.execute(inputData);

        // Assert: Verify that the image is returned correctly
        assertEquals(1, viewModel.getImages().size(), "Expected 1 image to be found with special character search.");
    }

    @Test
    void testExecute_NullInputData() {
        // Act & Assert: Verify that passing null inputData throws an IllegalArgumentException
        FetchImageInputData inputData = null;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            interactor.execute(inputData);
        });

        assertEquals("inputData must not be null", exception.getMessage());
    }
}
