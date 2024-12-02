package use_case.FetchImage;

import data_access.SimpleImage;
import entity.CommonImage;
import data_access.InMemoryFetchImageDataAccessObject;
import interface_adapter.fetch_image.FetchImagePresenter;
import interface_adapter.fetch_image.FetchImageViewModel;
import org.junit.jupiter.api.Test;
import use_case.fetch_image.FetchImageInputData;
import use_case.fetch_image.FetchImageInteractor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class FetchImageInteractorTest {

    // Test for successful image search
    @Test
    void testExecute_Success() throws Exception {
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImageViewModel viewModel = new FetchImageViewModel();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(viewModel);

        // Add some images to the repository
        CommonImage image1 = new CommonImage("https://images.unsplash.com/photo-1591729651527-4d09a51b1e2c", "dad", "2");
        CommonImage image2 = new CommonImage("https://images.unsplash.com/photo-1549068294-04a001ee0638", "dad", "1");

        imageRepository.save(new SimpleImage(image1.getUrl(), image1.getDescription(), image1.getId()));
        imageRepository.save(new SimpleImage(image2.getUrl(), image2.getDescription(), image2.getId()));

        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);

        // Create ImageInputData with the query "dad"
        FetchImageInputData inputData = new FetchImageInputData("dad");

        // Call the method to test
        interactor.execute(inputData);

        // Verify the presenter handled the images correctly
        assertEquals(2, viewModel.getImages().size(), "Expected two images to be found.");
        assertEquals("dad", viewModel.getImages().get(0).getDescription());
        assertEquals("dad", viewModel.getImages().get(1).getDescription());
    }

    // Test for failure when no images are found
    @Test
    void testExecute_NoResults() throws Exception {
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImageViewModel viewModel = new FetchImageViewModel();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(viewModel);

        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);

        // Create FetchImageInputData with the query "nonexistent"
        FetchImageInputData inputData = new FetchImageInputData("nonexistent");

        interactor.execute(inputData);

        // Verify that the presenter handled the error
        assertEquals("Invalid Search: No results found.", viewModel.getErrorMessage());
    }

    // Test for failure when an exception is thrown by the repository
    @Test
    void testExecute_Failure() {
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImageViewModel viewModel = new FetchImageViewModel();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(viewModel);

        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);

        FetchImageInputData inputData = new FetchImageInputData("error");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            interactor.execute(inputData);
        });

        assertEquals("Error fetching images", exception.getMessage());
    }

    // Test when repository is empty
    @Test
    void testExecute_EmptyRepository() throws Exception {
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImageViewModel viewModel = new FetchImageViewModel();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(viewModel);

        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);
        FetchImageInputData inputData = new FetchImageInputData("sunset");

        interactor.execute(inputData);

        assertTrue(viewModel.getImages().isEmpty(), "Expected no results when repository is empty.");
    }

    // Test for case insensitivity
    @Test
    void testExecute_CaseInsensitive() throws Exception {
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImageViewModel viewModel = new FetchImageViewModel();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(viewModel);

        CommonImage image1 = new CommonImage("https://images.unsplash.com/photo-1591729651527-4d09a51b1e2c", "Sunset", "2");
        CommonImage image2 = new CommonImage("https://images.unsplash.com/photo-1549068294-04a001ee0638", "sunset", "1");

        imageRepository.save(new SimpleImage(image1.getUrl(), image1.getDescription(), image1.getId()));
        imageRepository.save(new SimpleImage(image2.getUrl(), image2.getDescription(), image2.getId()));

        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);
        FetchImageInputData inputData = new FetchImageInputData("Sunset");

        interactor.execute(inputData);

        assertEquals(2, viewModel.getImages().size(), "Expected 2 images to be found, ignoring case.");
    }

    // Test special character search
    @Test
    void testExecute_SpecialCharacters() throws Exception {
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImageViewModel viewModel = new FetchImageViewModel();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(viewModel);

        CommonImage image1 = new CommonImage("https://images.unsplash.com/photo-1591729651527-4d09a51b1e2c", "Beach Sunset", "2");
        imageRepository.save(new SimpleImage(image1.getUrl(), image1.getDescription(), image1.getId()));

        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);
        FetchImageInputData inputData = new FetchImageInputData("Beach Sunset");

        interactor.execute(inputData);

        assertEquals(1, viewModel.getImages().size(), "Expected 1 image to be found with special character search.");
    }

}
