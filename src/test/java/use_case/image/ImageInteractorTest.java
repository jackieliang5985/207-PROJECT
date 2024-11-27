package use_case.image;

import data_access.SimpleImage;
import entity.CommonImage;
import data_access.InMemoryImageDataAccessObject;
import interface_adapter.image.ImagePresenter;
import interface_adapter.image.ImageViewModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ImageInteractorTest {

    // Test for successful image search
    @Test
    void testSearchImages_Success() throws Exception {
        // Create the in-memory image repository and presenter
        InMemoryImageDataAccessObject imageRepository = new InMemoryImageDataAccessObject();
        ImagePresenter imagePresenter = new ImagePresenter(new ImageViewModel());

        // Add some images to the repository
        CommonImage image1 = new CommonImage("https://images.unsplash.com/photo-1591729651527-4d09a51b1e2c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2Nzg0NTF8MHwxfHNlYXJjaHw1fHxkYWR8ZW58MHx8fHwxNzMyNjU2OTY3fDA&ixlib=rb-4.0.3&q=80&w=400", "dad", "2");
        CommonImage image2 = new CommonImage("https://images.unsplash.com/photo-1549068294-04a001ee0638?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2Nzg0NTF8MHwxfHNlYXJjaHwxfHxkYWR8ZW58MHx8fHwxNzMyNjU2OTY3fDA&ixlib=rb-4.0.3&q=80&w=400", "dad", "1");

        // Save the images to the repository
        imageRepository.save(new SimpleImage(image1.getUrl(), image1.getDescription(), image1.getId()));
        imageRepository.save(new SimpleImage(image2.getUrl(), image2.getDescription(), image2.getId()));

        // Create the interactor instance
        ImageInteractor interactor = new ImageInteractor(imageRepository, imagePresenter);

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
        InMemoryImageDataAccessObject imageRepository = new InMemoryImageDataAccessObject();
        ImagePresenter imagePresenter = new ImagePresenter(new ImageViewModel());

        // Create the interactor instance
        ImageInteractor interactor = new ImageInteractor(imageRepository, imagePresenter);

        // Call the method to test
        List<CommonImage> result = interactor.searchImages("nonexistent");

        // Verify no images were found
        assertTrue(result.isEmpty(), "Expected no results for the query 'nonexistent'.");

        // Verify that the presenter handled the error (in your actual presenter implementation,
        // it would display this error to the view, so we simulate that handling in this test)
        assertEquals("Invalid Search: No results found.", imagePresenter.getErrorMessage());
    }

    // Test for failure when an exception is thrown by the repository (e.g., invalid query or internal error)
    @Test
    void testSearchImages_Failure() throws Exception {
        // Create the in-memory image repository and presenter
        InMemoryImageDataAccessObject imageRepository = new InMemoryImageDataAccessObject();
        ImagePresenter imagePresenter = new ImagePresenter(new ImageViewModel());

        // Simulate an error by making the repository return an exception
        ImageInteractor interactor = new ImageInteractor(imageRepository, imagePresenter);

        // We simulate an error scenario where repository fails to fetch images
        Exception exception = assertThrows(RuntimeException.class, () -> {
            // We simulate an error by calling with a query that will trigger an exception
            interactor.searchImages("error");
        });

        // Verify that the exception is correctly thrown and handled
        assertEquals("Error fetching images", exception.getMessage());

        // Verify that the presenter handles the error message (in real-world scenarios, this could show a dialog to the user)
        assertEquals("Error fetching images", imagePresenter.getErrorMessage());
    }

}
