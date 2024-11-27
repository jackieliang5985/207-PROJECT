package entity;

public class CommonImage {
    private final String url;
    private final String description;
    private final String id;

    public CommonImage(String url, String description, String id) {
        this.url = url;
        this.id = id;
        this.description = description;
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
