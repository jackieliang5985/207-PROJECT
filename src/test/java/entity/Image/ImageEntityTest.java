package entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

class ImageEntityTest {

    // Test valid creation of ImagePostNoteEntity
    @Test
    void testValidImagePostNoteEntity() {
        MindMapEntity mindMapEntity = new MindMapEntity("Test Mind Map", new ArrayList<>());
        String imageUrl = "https://example.com/image.jpg";
        int x = 50, y = 100, width = 250, height = 150;
        Color color = Color.YELLOW;

        ImagePostNoteEntity imagePostNoteEntity = new ImagePostNoteEntity(x, y, width, height, mindMapEntity, imageUrl);

        // Assert that the object was created successfully and fields are initialized correctly
        assertNotNull(imagePostNoteEntity);
        assertEquals(imageUrl, imagePostNoteEntity.getImageUrl(), "Image URL should match");
        assertEquals(x, imagePostNoteEntity.getX(), "X coordinate should match");
        assertEquals(y, imagePostNoteEntity.getY(), "Y coordinate should match");
        assertEquals(width, imagePostNoteEntity.getWidth(), "Width should match");
        assertEquals(height, imagePostNoteEntity.getHeight(), "Height should match");
    }

    // Test invalid coordinates (negative values)
    @Test
    void testInvalidCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ImagePostNoteEntity(-10, -20, 250, 150, new MindMapEntity("Test", new ArrayList<>()), "https://example.com/image.jpg");
        });
    }

    // Test invalid (empty) image URL
    @Test
    void testEmptyImageUrl() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ImagePostNoteEntity(50, 100, 250, 150, new MindMapEntity("Test", new ArrayList<>()), "");
        });
    }

    // Test invalid (zero or negative) width/height
    @Test
    void testInvalidWidthHeight() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ImagePostNoteEntity(50, 100, -250, 150, new MindMapEntity("Test", new ArrayList<>()), "https://example.com/image.jpg");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new ImagePostNoteEntity(50, 100, 250, 0, new MindMapEntity("Test", new ArrayList<>()), "https://example.com/image.jpg");
        });
    }

    // Test setter and getter for imageUrl
    @Test
    void testImageUrlSetterGetter() {
        MindMapEntity mindMapEntity = new MindMapEntity("Test Mind Map", new ArrayList<>());
        ImagePostNoteEntity imagePostNoteEntity = new ImagePostNoteEntity(50, 100, 250, 150, mindMapEntity, "https://example.com/image.jpg");

        // Assert that the initial image URL is correct
        assertEquals("https://example.com/image.jpg", imagePostNoteEntity.getImageUrl());

        // Change the image URL using the setter and verify it was updated
        String newImageUrl = "https://example.com/another-image.jpg";
        imagePostNoteEntity.setImageUrl(newImageUrl);
        assertEquals(newImageUrl, imagePostNoteEntity.getImageUrl());
    }
}
