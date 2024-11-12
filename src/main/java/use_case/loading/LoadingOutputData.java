package use_case.loading;

/**
 * Output Data for the Login Use Case.
 */
public class LoadingOutputData {

    private final String username;
    private final boolean useCaseFailed;

    public LoadingOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

}
