package interface_adapter.loading;

/**
 * The state for the Login View Model.
 */
public class LoadingState {
    private String name = "";
    private String loginError;
    private String description = "";

    public String getName() {
        return name;
    }

    public String getLoginError() {
        return loginError;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoginError(String usernameError) {
        this.loginError = usernameError;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
