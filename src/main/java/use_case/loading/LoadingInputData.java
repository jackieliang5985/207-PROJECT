package use_case.loading;

/**
 * The Input Data for the Login Use Case.
 */
public class LoadingInputData {

    private final String username;
    private final String password;

    public LoadingInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

}
