package interface_adapter;

import use_case.get_images.ImageInputBoundary;  // Import the ImageRepository interface
import entity.CommonImage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A concrete implementation of the ImageRepository interface that interacts with the Unsplash API
 * to fetch images based on a query. It handles the HTTP request, processes the response,
 * and returns a list of images.
 */
public class UnsplashImageInputBoundary implements ImageInputBoundary {
    private final String apiKey;
    private final String baseUrl = "https://api.unsplash.com/search/photos";

    /**
     * Constructor that accepts an API key for accessing the Unsplash API.
     * @param apiKey the API key to authenticate with Unsplash
     */
    public UnsplashImageInputBoundary(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Searches for images from the Unsplash API based on the provided query string.
     *
     * This method makes an HTTP GET request to the Unsplash API with the query parameter,
     * processes the JSON response, and returns a list of CommonImage objects representing the fetched images.
     *
     * @param query the search term to search for images
     * @return a list of CommonImage objects representing the fetched images
     * @throws Exception if there is an issue with the API request or response
     */
    @Override
    public List<CommonImage> searchImages(String query) throws Exception {
        HttpURLConnection connection = null;
        try {
            // Construct the API URL with the query and API key
            final URL url = new URL(baseUrl + "?query=" + query + "&client_id=" + apiKey);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check the response code (to see if it worked or not)
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed to fetch images: " + connection.getResponseMessage());
            }

            final String response = new String(connection.getInputStream().readAllBytes());
            final JSONObject jsonResponse = new JSONObject(response);
            // Extract image results from the response
            final JSONArray results = jsonResponse.getJSONArray("results");

            final List<CommonImage> commonImages = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                final JSONObject result = results.getJSONObject(i);
                final String id = result.getString("id");
                // Image URL (small size)
                final String imageUrl = result.getJSONObject("urls").getString("small");
                // Optional description (idk if we need)
                final String description = result.optString("description", "No description").trim();
                commonImages.add(new CommonImage(id, imageUrl, description));
            }
            return commonImages;

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
