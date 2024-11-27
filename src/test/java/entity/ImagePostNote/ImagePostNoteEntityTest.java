package entity;

import entity.ImagePostNoteEntity;
import entity.MindMapEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ImagePostNoteEntityTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String imageUrl = "https://example.com/image.jpg";
        int x = 50;
        int y = 100;
        int width = 250;
        int height = 150;
        MindMapEntity mindMap = new MindMapEntity("Test Mind Map", new ArrayList<>());

        // Act
        ImagePostNoteEntity postNote = new ImagePostNoteEntity(x, y, width, height, mindMap, imageUrl);

        // Assert: Ensure that the object was initialized correctly with the expected values.
        assertEquals(imageUrl, postNote.getImageUrl(), "Image URL should match.");
        assertEquals(x, postNote.getX(), "X position should match.");
        assertEquals(y, postNote.getY(), "Y position should match.");
        assertEquals(width, postNote.getWidth(), "Width should match.");
        assertEquals(height, postNote.getHeight(), "Height should match.");
        assertEquals(mindMap, postNote.getMindMap(), "MindMapEntity should match.");
    }
    @Test
    void testGetters() {
        // Arrange
        String imageUrl = "https://example.com/image.jpg";
        int x = 50;
        int y = 100;
        int width = 250;
        int height = 150;
        MindMapEntity mindMap = new MindMapEntity("Test Mind Map", new ArrayList<>());
        ImagePostNoteEntity postNote = new ImagePostNoteEntity(x, y, width, height, mindMap, imageUrl);

        // Act & Assert
        assertEquals(imageUrl, postNote.getImageUrl());
        assertEquals(x, postNote.getX());
        assertEquals(y, postNote.getY());
        assertEquals(width, postNote.getWidth());
        assertEquals(height, postNote.getHeight());
        assertEquals(mindMap, postNote.getMindMap());
    }

    @Test
    void testInvalidImagePostNote() {
        // Test invalid coordinates (negative values)
        assertThrows(IllegalArgumentException.class, () -> {
            new ImagePostNoteEntity(-10, -20, 250, 150, new MindMapEntity("Test", new ArrayList<>()), "https://example.com/image.jpg");
        });

        // Test invalid (empty) image URL
        assertThrows(IllegalArgumentException.class, () -> {
            new ImagePostNoteEntity(50, 100, 250, 150, new MindMapEntity("Test", new ArrayList<>()), "");
        });

        // Test invalid (zero or negative) width/height
        assertThrows(IllegalArgumentException.class, () -> {
            new ImagePostNoteEntity(50, 100, -250, 150, new MindMapEntity("Test", new ArrayList<>()), "https://example.com/image.jpg");
        });
    }


}
