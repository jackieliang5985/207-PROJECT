package interface_adapter.create_MindMap;

/**
 * The state for the Signup View Model.
 */
public class MindMapState {
    private String name = "";
    private String nameError;
    private String description = "";

    public MindMapState(String nameError) {
        this.nameError = nameError;
    }

    public String getName() {
        return name;
    }

    public String getNameError() {
        return nameError;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String username) {
        this.name = name;
    }

    public void setNameError(String usernameError) {
        this.nameError = nameError;
    }

    public void setDescription(String password) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SignupState{"
                + "username='" + name + '\''
                + ", password='" + description + '\''
                + '}';
    }
}
