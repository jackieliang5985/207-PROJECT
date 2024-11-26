package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryImageDataAccessObject {
    private final Map<String, SimpleImage> images = new HashMap<>();

    // Save a SimpleImage
    public void save(SimpleImage image) {
        images.put(image.getId(), image);
    }

    // Fetch a SimpleImage by ID
    public SimpleImage get(String id) {
        return images.get(id);
    }

    // Fetch all SimpleImages
    public List<SimpleImage> getAll() {
        return new ArrayList<>(images.values());
    }

    // Delete a SimpleImage by ID
    public void delete(String id) {
        images.remove(id);
    }

    // Check if a SimpleImage exists by ID
    public boolean existsById(String id) {
        return images.containsKey(id);
    }
}
