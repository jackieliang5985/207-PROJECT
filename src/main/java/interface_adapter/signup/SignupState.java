package interface_adapter.signup;

/**
 * The state for the Signup View Model.
 */
public class SignupState {
    private String name = "";
    private String nameError;
    private String description = "";

    public SignupState(String nameError) {
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
