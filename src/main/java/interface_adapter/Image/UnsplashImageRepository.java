package interface_adapter;

import entity.Image;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.image.ImageRepository;

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
    public List<Image> searchImages(String query) {
        HttpURLConnection connection = null;
        try {
            final URL url = new URL(baseUrl + "?query=" + query + "&client_id=" + apiKey);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed to fetch images: " + connection.getResponseMessage());
            }

            // Parse the response
            final String response = new String(connection.getInputStream().readAllBytes());
            final JSONObject jsonResponse = new JSONObject(response);
            final JSONArray results = jsonResponse.getJSONArray("results");

            final List<Image> images = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                final JSONObject result = results.getJSONObject(i);
                final String id = result.getString("id");
                final String imageUrl = result.getJSONObject("urls").getString("small");
                final String description = result.optString("description", "No description").trim();

                images.add(new Image(id, imageUrl, description));
            }
            System.out.println("API Response: " + response);
            return images;

        }
        catch (IOException | RuntimeException e) {
            throw new RuntimeException("Error fetching images", e);
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
