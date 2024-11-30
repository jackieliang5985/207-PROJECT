package data_access;

public class SimpleImage {
    private String url;
    private String description;
    private String id;

    public SimpleImage(String url, String description, String id) {
        this.url = url;
        this.description = description;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
