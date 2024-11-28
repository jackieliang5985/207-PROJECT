package interface_adapter.create_MindMap;

/**
 * The state for the Signup View Model.
 */
public class MindMapState {
    private String name = "";
    private String nameError;
//    private String description = "";

    public MindMapState(String nameError) {
        this.nameError = nameError;
    }

    public String getName() {
        return name;
    }

    public String getNameError() {
        return nameError;
    }

//    public String getDescription() {
//        return description;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

//    public void setDescription(String description) {
//        this.description = description;
//    }

    public boolean isValidName() {
        // System.out.println("Validating name: '" + name + "'");
        if (name == null || name.trim().isEmpty()) {
            nameError = "Title cannot be empty.";
            return false;
        }
        // Other vaildations
        return true;
    }

    @Override
    public String toString() {
        return "MindMapState{"
                + "name='" + name + '\''
//                + ", description='" + description + '\''
                + '}';
    }
}
