package interface_adapter.create_MindMap;

public class MindMapState {
    private String name = "Default MindMap"; // Initial name for the mind map
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

    public boolean isValidName() {
        if (name == null || name.trim().isEmpty()) {
            nameError = "Title cannot be empty.";
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MindMapState{" +
                "name='" + name + '\'' +
                '}';
    }
}
