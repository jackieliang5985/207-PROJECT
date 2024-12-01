package use_case.change_color;

/**
 * The Change Color Use Case.
 */
public interface ChangeColorInputBoundary {
    /**
     * Execute the Change Color Use Case.
     * @param changeColorInputData the input data for this use case
     */
    void execute(ChangeColorInputData changeColorInputData);

}