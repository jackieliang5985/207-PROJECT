package data_access;

import interface_adapter.image.ImageRepository;
import entity.CommonImage;  // Use CommonImage
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UnsplashImageRepository implements ImageRepository {
    private final String apiKey;
    private final String baseUrl = "https://api.unsplash.com/search/photos";

    public UnsplashImageRepository(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<CommonImage> fetchImages(String query) throws Exception {
        HttpURLConnection connection = null;
        try {
            // Construct the API URL with query and API key
            final URL url = new URL(baseUrl + "?query=" + query + "&client_id=" + apiKey);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check the response code
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed to fetch images: " + connection.getResponseMessage());
            }

            // Read the response and parse JSON
            String response = new String(connection.getInputStream().readAllBytes());
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray results = jsonResponse.getJSONArray("results");

            List<CommonImage> commonImages = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String imageUrl = result.getJSONObject("urls").getString("small");
                String description = result.optString("description", "No description").trim();
                String id = result.getString("id");
                // Return CommonImage instead of SimpleImage
                commonImages.add(new CommonImage(imageUrl, description, id));
            }

            return commonImages;

        } catch (IOException | RuntimeException e) {
            throw new RuntimeException("Error fetching images", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
