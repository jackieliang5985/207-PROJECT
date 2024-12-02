package interface_adapter.fetch_image;

import entity.CommonImage;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UnsplashImageInputBoundary implements ImageRepository {

    private final String apiKey;
    // To find all photos
    private final String baseUrl = "https://api.unsplash.com/search/photos";
    // Additional info (second endpoint for image details, but not used now)
    private final String imageDetailsUrl = "https://api.unsplash.com/photos/";

    public UnsplashImageInputBoundary(String apiKey) {
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

                // Don't call the second API here, just use the basic details
                commonImages.add(new CommonImage(imageUrl, description, id));

                // Note: The second API call fetchImageDetails(imageId) is not being used here.
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

    // Keep the fetchImageDetails method but do not call it in the fetchImages method
    private CommonImage fetchImageDetails(String imageId) throws IOException {
        HttpURLConnection connection = null;
        try {
            // Construct the API URL to fetch the details for a specific image using its ID
            URL url = new URL(imageDetailsUrl + imageId + "?client_id=" + apiKey);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check the response code
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed to fetch image details: " + connection.getResponseMessage());
            }

            String response = new String(connection.getInputStream().readAllBytes());
            JSONObject imageDetails = new JSONObject(response);

            // Extract additional image details if needed (e.g., full size, additional metadata)
            String fullImageUrl = imageDetails.getJSONObject("urls").getString("full");
            String additionalDescription = imageDetails.optString("description", "No description").trim();

            return new CommonImage(fullImageUrl, additionalDescription, imageDetails.getString("id"));

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
