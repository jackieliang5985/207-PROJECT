package interface_adapter.image;

import data_access.SimpleImage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of ImageRepository using the Unsplash API.
 */
public class UnsplashImageRepository implements ImageRepository {
    private final String apiKey;
    private final String baseUrl = "https://api.unsplash.com/search/photos";

    public UnsplashImageRepository(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<SimpleImage> fetchImages(String query) throws Exception {
        HttpURLConnection connection = null;
        try {
            final URL url = new URL(baseUrl + "?query=" + query + "&client_id=" + apiKey);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed to fetch images: " + connection.getResponseMessage());
            }

            String response = new String(connection.getInputStream().readAllBytes());
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray results = jsonResponse.getJSONArray("results");

            List<SimpleImage> simpleImages = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);

                // Extract data from the JSON object
                String imageUrl = result.getJSONObject("urls").getString("small");
                String description = result.optString("description", "No description").trim();
                String id = result.getString("id"); // Extract the ID field from JSON

                // Create SimpleImage with all three arguments
                simpleImages.add(new SimpleImage(imageUrl, description, id));
            }

            return simpleImages;

        } catch (IOException | RuntimeException e) {
            throw new RuntimeException("Error fetching images", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
