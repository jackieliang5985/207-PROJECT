package use_case.fetch_image;

/**
 * A data transfer object (DTO) used to encapsulate the input data for the image search use case.
 * This class holds the query string that specifies the search criteria for fetching images.
 */
public class FetchImageInputData {

    /**
     * The query string representing the search term for image fetching.
     */
    private final String query;

    /**
     * Constructs a new {@code ImageInputData} object with the specified query.
     *
     * @param query the search term for the image search operation.
     */
    public FetchImageInputData(String query) {
        this.query = query;
    }

    /**
     * Returns the search query associated with this input data.
     *
     * @return the search query string.
     */
    public String getQuery() {
        return query;
    }
}
