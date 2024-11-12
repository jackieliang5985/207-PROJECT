package use_case.create_MindMap;

/**
 * The Input Data for the Signup Use Case.
 */
public class MindMapInputData {

    private final String name;
    private final String description;

    public MindMapInputData(String name, String description) {
        this.name = name;
        this.description = description;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

}
