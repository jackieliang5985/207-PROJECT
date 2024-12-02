package data_access;

import interface_adapter.fetch_image.FetchImageRepository;
import entity.CommonImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryFetchImageDataAccessObject implements FetchImageRepository {
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

    // Implementing the method from ImageRepository to fetch images
    @Override
    public List<CommonImage> fetchImages(String query) throws Exception {
        // Assuming we are just searching by description in this mockup
        List<CommonImage> result = new ArrayList<>();
        for (SimpleImage simpleImage : images.values()) {
            if (simpleImage.getDescription().toLowerCase().contains(query.toLowerCase())) {
                result.add(new CommonImage(simpleImage.getUrl(), simpleImage.getDescription(), simpleImage.getId()));
            }
        }
        return result;
    }
}
