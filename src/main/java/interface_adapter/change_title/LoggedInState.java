package interface_adapter.change_title;

public class LoggedInState {
    private String name = "Default MindMap";  // Initial default name
    private String description = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

