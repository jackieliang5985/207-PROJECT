package use_case.image;

import interface_adapter.image.ImageRepository;
import interface_adapter.image.ImagePresenter;
import entity.CommonImage;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ImageInteractorTest {

    // Test for successful image search
    @Test
    void testSearchImages_Success() throws Exception {
        // Create mock dependencies
        ImageRepository mockImageRepository = mock(ImageRepository.class);
        ImagePresenter mockImagePresenter = mock(ImagePresenter.class);

        // Set up mock behavior for image repository
        String query = "sunset";
        CommonImage mockImage = new CommonImage("image1", "https://image1.url");
        List<CommonImage> mockImages = List.of(mockImage);
        when(mockImageRepository.fetchImages(query)).thenReturn(mockImages);

        // Create the interactor instance
        ImageInteractor interactor = new ImageInteractor(mockImageRepository, mockImagePresenter);

        // Call the method to test
        List<CommonImage> result = interactor.searchImages(query);

        // Verify the repository method was called with the correct parameters
        verify(mockImageRepository).fetchImages(query);

        // Verify the presenter method was called with the correct result
        verify(mockImagePresenter).presentImages(mockImages);

        // Assert the result
        assertEquals(1, result.size());  // Check that 1 image was returned
        assertEquals("image1", result.get(0).getImageName());  // Check image name
        assertEquals("https://image1.url", result.get(0).getImageUrl());  // Check image URL
    }

    // Test for failure when no images are found (empty list returned)
    @Test
    void testSearchImages_NoResults() throws Exception {
        // Create mock dependencies
        ImageRepository mockImageRepository = mock(ImageRepository.class);
        ImagePresenter mockImagePresenter = mock(ImagePresenter.class);

        // Set up mock behavior for image repository to return an empty list
        String query = "nonexistent";
        when(mockImageRepository.fetchImages(query)).thenReturn(List.of());  // No images found

        // Create the interactor instance
        ImageInteractor interactor = new ImageInteractor(mockImageRepository, mockImagePresenter);

        // Call the method to test
        List<CommonImage> result = interactor.searchImages(query);

        // Verify the repository method was called with the correct parameters
        verify(mockImageRepository).fetchImages(query);

        // Verify that no images were presented
        verify(mockImagePresenter).presentImages(List.of());

        // Assert the result is empty
        assertTrue(result.isEmpty(), "Expected an empty result list.");
    }

    // Test for failure when an exception is thrown by the repository
    @Test
    void testSearchImages_Failure() throws Exception {
        // Create mock dependencies
        ImageRepository mockImageRepository = mock(ImageRepository.class);
        ImagePresenter mockImagePresenter = mock(ImagePresenter.class);

        // Set up mock behavior to throw an exception when fetching images
        String query = "error";
        when(mockImageRepository.fetchImages(query)).thenThrow(new Exception("Repository error"));

        // Create the interactor instance
        ImageInteractor interactor = new ImageInteractor(mockImageRepository, mockImagePresenter);

        // Call the method to test and assert that it throws the expected exception
        Exception exception = assertThrows(Exception.class, () -> {
            interactor.searchImages(query);
        });

        // Verify that the exception message is correct
        assertEquals("Repository error", exception.getMessage());

        // Verify no images were presented
        verify(mockImagePresenter, never()).presentImages(anyList());
    }
}
