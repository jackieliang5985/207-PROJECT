package entity;

import java.util.UUID;  // For generating unique IDs

public class PostNoteEntity {
    private String id;  // Unique ID for each post-it note
    private int x, y, width, height;
    private MindMapEntity mindMap;  // Reference to the mind map

    // Constructor that initializes the fields and generates a unique ID
    public PostNoteEntity(int x, int y, int width, int height, MindMapEntity mindMap) {
        this.id = UUID.randomUUID().toString();  // Generate a unique ID for this post-it note
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mindMap = mindMap;
    }

    // Getter for the unique ID
    public String getId() {
        return id;  // Return the unique ID
    }

    // Getters and setters for other fields
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }

    public MindMapEntity getMindMap() { return mindMap; }

    // Other methods related to PostNoteEntity can be added here (like text manipulation or connections)
}
