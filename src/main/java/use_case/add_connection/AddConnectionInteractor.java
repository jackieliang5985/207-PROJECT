package use_case.add_connection;

import entity.ConnectionEntity;
import data_access.ConnectionDAO;
import java.util.UUID;

public class AddConnectionInteractor implements AddConnectionInputBoundary {
    private final AddConnectionOutputBoundary outputBoundary;
    private final ConnectionDAO connectionDAO;

    public AddConnectionInteractor(AddConnectionOutputBoundary outputBoundary, ConnectionDAO connectionDAO) {
        this.outputBoundary = outputBoundary;
        this.connectionDAO = connectionDAO;
    }

    @Override
    public void addConnection(AddConnectionInputData inputData) {
        // Check that fromNoteId and toNoteId are not the same
        if (inputData.getFromNoteID().equals(inputData.getToNoteID())) {
            outputBoundary.present(new AddConnectionOutputData(false, "Cannot connect a note to itself."));
            return;
        }

        // Create a new ConnectionEntity
        String connectionId = UUID.randomUUID().toString();
        ConnectionEntity connection = new ConnectionEntity(
                connectionId,
                inputData.getFromNoteID(),
                inputData.getToNoteID()
        );

        // Store the connection using the DAO
        connectionDAO.saveConnection(connection);

        // Notify success
        outputBoundary.present(new AddConnectionOutputData(true, "Connection added successfully."));
    }
}
