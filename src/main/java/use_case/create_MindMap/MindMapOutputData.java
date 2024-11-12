package use_case.create_MindMap;

/**
 * Output Data for the Signup Use Case.
 */
public class MindMapOutputData {

    private final String name;

    private final boolean useCaseFailed;

    public MindMapOutputData(String name, boolean useCaseFailed) {
        this.name = name;
        this.useCaseFailed = useCaseFailed;
    }

    public String getName() {
        return name;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
