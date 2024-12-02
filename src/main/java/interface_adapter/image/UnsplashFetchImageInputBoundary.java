package interface_adapter.image;

import entity.CommonImage;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UnsplashFetchImageInputBoundary implements FetchImageRepository {

    private final String apiKey;
    // Base URL to search for photos
    private final String baseUrl = "https://api.unsplash.com/search/photos";
    // Endpoint for fetching image details
    private final String imageDetailsUrl = "https://api.unsplash.com/photos/";

    /**
     * Constructor to initialize the UnsplashImageInputBoundary with the given API key.
     * @param apiKey The API key for authenticating requests to the Unsplash API.
     */
    public UnsplashFetchImageInputBoundary(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Fetches images based on a search query.
     * @param query The search query for fetching images.
     * @return A list of CommonImage objects containing the image URL, description, and ID.
     * @throws Exception If there is an error fetching images from the API.
     */
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

                commonImages.add(new CommonImage(imageUrl, description, id));

                // Note: The second API call fetchImageDetails(imageId) is not used here.
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

    /**
     * This method fetches additional details for a specific image by its ID.
     * Currently not being called within fetchImages, but kept for potential future use.
     * @param imageId The ID of the image to fetch details for.
     * @return A CommonImage object containing the full image URL, description, and ID.
     * @throws IOException If an error occurs while fetching image details.
     */
    private CommonImage fetchImageDetails(String imageId) throws IOException {
        HttpURLConnection connection = null;
        try {
            // Construct the API URL to fetch the details for a specific image using its ID
            final URL url = new URL(imageDetailsUrl + imageId + "?client_id=" + apiKey);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check the response code
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed to fetch image details: " + connection.getResponseMessage());
            }

            final String response = new String(connection.getInputStream().readAllBytes());
            final JSONObject imageDetails = new JSONObject(response);

            // Extract additional image details if needed (e.g., full size, additional metadata)
            final String fullImageUrl = imageDetails.getJSONObject("urls").getString("full");
            final String additionalDescription = imageDetails.optString("description", "No description").trim();

            return new CommonImage(fullImageUrl, additionalDescription, imageDetails.getString("id"));

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
