package data_access;

import java.util.List;

public interface ImageDataAccessInterface {
    void save(SimpleImage image);                    // Save an image
    SimpleImage get(String id);                      // Get an image by ID
    List<SimpleImage> getAll();                      // Get all images
    void delete(String id);                          // Delete an image by ID
    boolean existsById(String id);                   // Check if an image exists by ID
}
