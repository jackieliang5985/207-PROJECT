package interface_adapter.add_Connection;

import use_case.add_connection.AddConnectionInputBoundary;
import use_case.add_connection.AddConnectionInputData;

public class AddConnectionController {
    private final AddConnectionInputBoundary inputBoundary;

    public AddConnectionController(AddConnectionInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void addConnection(String fromNoteId, String toNoteId) {
        AddConnectionInputData inputData = new AddConnectionInputData(fromNoteId, toNoteId);
        inputBoundary.addConnection(inputData);
    }
}
