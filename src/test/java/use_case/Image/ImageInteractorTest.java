package use_case.Image;

import data_access.SimpleImage;
import entity.CommonImage;
import data_access.InMemoryFetchImageDataAccessObject;
import interface_adapter.image.FetchImagePresenter;
import interface_adapter.image.FetchImageViewModel;
import org.junit.jupiter.api.Test;
import use_case.fetch_image.FetchImageInteractor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

// Basic Search and Validation: Test if images can be fetched based on various search terms.
// No Results: Ensure no results are correctly handled and error messages are displayed.
// Failure Scenarios: Test repository failures and ensure the interactor handles errors gracefully.
// Edge Cases: Check case insensitivity, special characters, empty queries, and null handling.
// ViewModel Updates: Confirm that the presenter correctly updates the view model with the appropriate image data.

class ImageInteractorTest {

    // Test for successful image search
    @Test
    void testSearchImages_Success() throws Exception {
        // Create the in-memory image repository and presenter
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(new FetchImageViewModel());

        // Add some images to the repository
        CommonImage image1 = new CommonImage("https://images.unsplash.com/photo-1591729651527-4d09a51b1e2c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2Nzg0NTF8MHwxfHNlYXJjaHw1fHxkYWR8ZW58MHx8fHwxNzMyNjU2OTY3fDA&ixlib=rb-4.0.3&q=80&w=400", "dad", "2");
        CommonImage image2 = new CommonImage("https://images.unsplash.com/photo-1549068294-04a001ee0638?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2Nzg0NTF8MHwxfHNlYXJjaHwxfHxkYWR8ZW58MHx8fHwxNzMyNjU2OTY3fDA&ixlib=rb-4.0.3&q=80&w=400", "dad", "1");

        // Save the images to the repository
        imageRepository.save(new SimpleImage(image1.getUrl(), image1.getDescription(), image1.getId()));
        imageRepository.save(new SimpleImage(image2.getUrl(), image2.getDescription(), image2.getId()));

        // Create the interactor instance
        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);

        // Call the method to test with the query that matches the descriptions
        List<CommonImage> result = interactor.searchImages("dad");

        // Verify the repository method was called
        assertEquals(2, result.size(), "Expected two images to be found.");
        assertEquals("dad", result.get(0).getDescription());
        assertEquals("dad", result.get(1).getDescription());
    }

    // Test for failure when no images are found (empty list returned)
    @Test
    void testSearchImages_NoResults() throws Exception {
        // Create the in-memory image repository and presenter
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(new FetchImageViewModel());

        // Create the interactor instance
        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);

        // Call the method to test
        List<CommonImage> result = interactor.searchImages("nonexistent");

        // Verify no images were found
        assertTrue(result.isEmpty(), "Expected no results for the query 'nonexistent'.");

        // Verify that the presenter handled the error (in your actual presenter implementation,
        // it would display this error to the view, so we simulate that handling in this test)
        assertEquals("Invalid Search: No results found.", fetchImagePresenter.getErrorMessage());
    }

    // Test for failure when an exception is thrown by the repository (e.g., invalid query or internal error)
    @Test
    void testSearchImages_Failure() throws Exception {
        // Create the in-memory image repository and presenter
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(new FetchImageViewModel());

        // Simulate an error by making the repository return an exception
        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);

        // We simulate an error scenario where repository fails to fetch images
        Exception exception = assertThrows(RuntimeException.class, () -> {
            // We simulate an error by calling with a query that will trigger an exception
            interactor.searchImages("error");
        });

        // Verify that the exception is correctly thrown and handled
        assertEquals("Error fetching images", exception.getMessage());

        // Verify that the presenter handles the error message (in real-world scenarios, this could show a dialog to the user)
        assertEquals("Error fetching images", fetchImagePresenter.getErrorMessage());
    }

    @Test
    void testSearchImages_EmptyRepository() throws Exception {
        // Create the in-memory image repository and presenter
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(new FetchImageViewModel());

        // Create the interactor instance
        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);

        // Call the method to test (search in an empty repository)
        List<CommonImage> result = interactor.searchImages("sunset");

        // Assert: Should return an empty list as no images are present
        assertTrue(result.isEmpty(), "Expected no results when the repository is empty.");
    }

    @Test
    void testSearchImages_CaseInsensitive() throws Exception {
        // Create the in-memory image repository and presenter
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(new FetchImageViewModel());

        // Add some images to the repository
        CommonImage image1 = new CommonImage("https://images.unsplash.com/photo-1591729651527-4d09a51b1e2c", "Sunset", "2");
        CommonImage image2 = new CommonImage("https://images.unsplash.com/photo-1549068294-04a001ee0638", "sunset", "1");
        imageRepository.save(new SimpleImage(image1.getUrl(), image1.getDescription(), image1.getId()));
        imageRepository.save(new SimpleImage(image2.getUrl(), image2.getDescription(), image2.getId()));

        // Create the interactor instance
        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);

        // Test case insensitivity by searching for "sunset"
        List<CommonImage> result = interactor.searchImages("Sunset");

        // Assert: Should find both images regardless of case
        assertEquals(2, result.size(), "Expected 2 images to be found, ignoring case.");
    }

    @Test
    void testSearchImages_SpecialCharacters() throws Exception {
        // Create the in-memory image repository and presenter
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(new FetchImageViewModel());

        // Add some images to the repository
        CommonImage image1 = new CommonImage("https://images.unsplash.com/photo-1591729651527-4d09a51b1e2c", "Beach Sunset", "2");
        CommonImage image2 = new CommonImage("https://images.unsplash.com/photo-1549068294-04a001ee0638", "Mountain View", "1");
        imageRepository.save(new SimpleImage(image1.getUrl(), image1.getDescription(), image1.getId()));
        imageRepository.save(new SimpleImage(image2.getUrl(), image2.getDescription(), image2.getId()));

        // Create the interactor instance
        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);

        // Test searching with special characters (e.g., space)
        List<CommonImage> result = interactor.searchImages("Beach Sunset");

        // Assert: Should find the "Beach Sunset" image
        assertEquals(1, result.size(), "Expected 1 image to be found with special character search.");
    }

    @Test
    void testImageURLInPresenter() throws Exception {
        // Arrange
        InMemoryFetchImageDataAccessObject imageRepository = new InMemoryFetchImageDataAccessObject();
        FetchImageViewModel viewModel = new FetchImageViewModel();
        FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(viewModel);

        // Add an image to the repository
        CommonImage image = new CommonImage("https://example.com/image.jpg", "Sunset", "123");
        imageRepository.save(new SimpleImage(image.getUrl(), image.getDescription(), image.getId()));

        // Create the interactor instance
        FetchImageInteractor interactor = new FetchImageInteractor(imageRepository, fetchImagePresenter);

        // Act: Search for images
        interactor.searchImages("sunset");

        // Assert: Check if the presenter updates the view model with correct data
        assertEquals("https://example.com/image.jpg", viewModel.getImages().get(0).getUrl());
        assertEquals("Sunset", viewModel.getImages().get(0).getDescription());
    }


}
