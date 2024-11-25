package use_case.get_images;

public class ImageInputData {
    private final String query;

    public ImageInputData(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
