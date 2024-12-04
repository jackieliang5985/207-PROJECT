package interface_adapter.create_MindMap;

/**
 * The state for the Mind Map View Model.
 */
public class MindMapState {
    // Initial name for the mind map
    private String name = "Default MindMap";
    private String nameError;

    public MindMapState(String nameError) {
        this.nameError = nameError;
    }

    public String getName() {
        return name;
    }

    public String getNameError() {
        return nameError;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    /**
     * Returns whether the Mind Map name is valid (is not null and does not consist of only spaces).
     * @return true if name is valid, false otherwise
     */
    public boolean isValidName() {
        boolean valid = true;
        if (name == null || name.trim().isEmpty()) {
            nameError = "Title cannot be empty.";
            valid = false;
        }
        return valid;
    }

    @Override
    public String toString() {
        return "MindMapState{" + "name='" + name + '\'' + '}';
    }
}
