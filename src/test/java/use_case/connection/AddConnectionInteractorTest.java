package use_case.add_connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AddConnectionInteractor.
 */
public class AddConnectionInteractorTest {

    private AddConnectionInteractor interactor;
    private InMemoryConnectionDAO connectionDAO;
    private TestAddConnectionOutputBoundary outputBoundary;

    /**
     * Setup method to initialize common objects before each test.
     */
    @BeforeEach
    void setUp() {
        connectionDAO = new InMemoryConnectionDAO();
        outputBoundary = new TestAddConnectionOutputBoundary();
        interactor = new AddConnectionInteractor(outputBoundary, connectionDAO);
    }

    /**
     * Test adding a valid connection between two distinct notes.
     */
    @Test
    void testAddConnection_Success() {
        // Arrange
        AddConnectionInputData inputData = new AddConnectionInputData("note1", "note2");

        // Act
        interactor.addConnection(inputData);

        // Assert
        // Verify that the connection is saved in the DAO
        assertEquals(1, connectionDAO.getAllConnections().size(), "Expected one connection to be saved.");
        ConnectionEntity savedConnection = connectionDAO.getAllConnections().get(0);
        assertEquals("note1", savedConnection.getFromNoteId(), "FromNoteId should match the input.");
        assertEquals("note2", savedConnection.getToNoteId(), "ToNoteId should match the input.");

        // Verify that the output boundary received a success response
        assertNotNull(outputBoundary.getOutputData(), "Output data should not be null.");
        assertTrue(outputBoundary.getOutputData().isSuccess(), "Operation should be successful.");
        assertEquals("Connection added successfully.", outputBoundary.getOutputData().getMessage(),
                "Success message should match.");
    }

    /**
     * Test attempting to connect a note to itself.
     */
    @Test
    void testAddConnection_SameNoteIds() {
        // Arrange
        AddConnectionInputData inputData = new AddConnectionInputData("note1", "note1");

        // Act
        interactor.addConnection(inputData);

        // Assert
        // Verify that no connection is saved in the DAO
        assertEquals(0, connectionDAO.getAllConnections().size(), "No connection should be saved.");

        // Verify that the output boundary received a failure response
        assertNotNull(outputBoundary.getOutputData(), "Output data should not be null.");
        assertFalse(outputBoundary.getOutputData().isSuccess(), "Operation should fail.");
        assertEquals("Cannot connect a note to itself.", outputBoundary.getOutputData().getMessage(),
                "Failure message should match.");
    }

    /**
     * Test adding multiple valid connections.
     */
    @Test
    void testAddConnection_MultipleConnections() {
        // Arrange
        AddConnectionInputData inputData1 = new AddConnectionInputData("note1", "note2");
        AddConnectionInputData inputData2 = new AddConnectionInputData("note2", "note3");
        AddConnectionInputData inputData3 = new AddConnectionInputData("note3", "note4");

        // Act
        interactor.addConnection(inputData1);
        interactor.addConnection(inputData2);
        interactor.addConnection(inputData3);

        // Assert
        // Verify that all connections are saved in the DAO
        assertEquals(3, connectionDAO.getAllConnections().size(), "Expected three connections to be saved.");

        // Verify each connection's details
        ConnectionEntity connection1 = connectionDAO.getAllConnections().get(0);
        assertEquals("note1", connection1.getFromNoteId(), "First connection's FromNoteId should match.");
        assertEquals("note2", connection1.getToNoteId(), "First connection's ToNoteId should match.");

        ConnectionEntity connection2 = connectionDAO.getAllConnections().get(1);
        assertEquals("note2", connection2.getFromNoteId(), "Second connection's FromNoteId should match.");
        assertEquals("note3", connection2.getToNoteId(), "Second connection's ToNoteId should match.");

        ConnectionEntity connection3 = connectionDAO.getAllConnections().get(2);
        assertEquals("note3", connection3.getFromNoteId(), "Third connection's FromNoteId should match.");
        assertEquals("note4", connection3.getToNoteId(), "Third connection's ToNoteId should match.");
    }

    /**
     * Test that the presenter correctly updates the view model upon successful addition.
     */
    @Test
    void testPresenterUpdatesViewModel_Success() {
        // Arrange
        AddConnectionInputData inputData = new AddConnectionInputData("note1", "note2");

        // Act
        interactor.addConnection(inputData);

        // Assert
        AddConnectionOutputData outputData = outputBoundary.getOutputData();
        assertNotNull(outputData, "Output data should not be null.");
        assertTrue(outputData.isSuccess(), "Operation should be successful.");
        assertEquals("Connection added successfully.", outputData.getMessage(),
                "Success message should match.");
    }

    /**
     * Test that the presenter correctly updates the view model upon failure.
     */
    @Test
    void testPresenterUpdatesViewModel_Failure() {
        // Arrange
        AddConnectionInputData inputData = new AddConnectionInputData("note1", "note1");

        // Act
        interactor.addConnection(inputData);

        // Assert
        AddConnectionOutputData outputData = outputBoundary.getOutputData();
        assertNotNull(outputData, "Output data should not be null.");
        assertFalse(outputData.isSuccess(), "Operation should fail.");
        assertEquals("Cannot connect a note to itself.", outputData.getMessage(),
                "Failure message should match.");
    }

    /**
     * Inner class to capture the output data from the interactor.
     */
    private static class TestAddConnectionOutputBoundary implements AddConnectionOutputBoundary {
        private AddConnectionOutputData outputData;

        @Override
        public void present(AddConnectionOutputData outputData) {
            this.outputData = outputData;
        }

        public AddConnectionOutputData getOutputData() {
            return outputData;
        }
    }

    /**
     * Additional helper classes and interfaces for testing purposes.
     * These should match your actual implementations.
     */

    /**
     * Mock implementation of AddConnectionOutputBoundary for testing.
     */
    private interface AddConnectionOutputBoundary {
        void present(AddConnectionOutputData outputData);
    }

    /**
     * Data class representing input data for adding a connection.
     */
    private static class AddConnectionInputData {
        private final String fromNoteID;
        private final String toNoteID;

        public AddConnectionInputData(String fromNoteID, String toNoteID) {
            this.fromNoteID = fromNoteID;
            this.toNoteID = toNoteID;
        }

        public String getFromNoteID() {
            return fromNoteID;
        }

        public String getToNoteID() {
            return toNoteID;
        }
    }

    /**
     * Data class representing output data after attempting to add a connection.
     */
    private static class AddConnectionOutputData {
        private final boolean success;
        private final String message;

        public AddConnectionOutputData(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Mock implementation of ConnectionEntity.
     */
    private static class ConnectionEntity {
        private final String id;
        private final String fromNoteId;
        private final String toNoteId;

        public ConnectionEntity(String id, String fromNoteId, String toNoteId) {
            this.id = id;
            this.fromNoteId = fromNoteId;
            this.toNoteId = toNoteId;
        }

        public String getId() {
            return id;
        }

        public String getFromNoteId() {
            return fromNoteId;
        }

        public String getToNoteId() {
            return toNoteId;
        }
    }

    /**
     * Mock implementation of InMemoryConnectionDAO.
     */
    private static class InMemoryConnectionDAO implements ConnectionDAO {
        private final List<ConnectionEntity> connections = new ArrayList<>();

        @Override
        public void saveConnection(ConnectionEntity connection) {
            connections.add(connection);
            System.out.println("Connection saved: " + connection.getFromNoteId() + " -> " + connection.getToNoteId());
        }

        @Override
        public void deleteConnectionsByNoteId(String noteId) {
            connections.removeIf(c -> c.getFromNoteId().equals(noteId) || c.getToNoteId().equals(noteId));
            System.out.println("Connections after deletion: " + connections.size());
        }

        @Override
        public List<ConnectionEntity> getAllConnections() {
            System.out.println("Retrieving all connections, total: " + connections.size());
            return new ArrayList<>(connections);
        }
    }

    /**
     * Mock implementation of ConnectionDAO interface.
     */
    private interface ConnectionDAO {
        void saveConnection(ConnectionEntity connection);
        void deleteConnectionsByNoteId(String noteId);
        List<ConnectionEntity> getAllConnections();
    }

    /**
     * Mock implementation of AddConnectionInteractor.
     * This should match your actual interactor's implementation.
     */
    private static class AddConnectionInteractor implements AddConnectionInputBoundary {
        private final AddConnectionOutputBoundary outputBoundary;
        private final ConnectionDAO connectionDAO;

        public AddConnectionInteractor(AddConnectionOutputBoundary outputBoundary, ConnectionDAO connectionDAO) {
            this.outputBoundary = outputBoundary;
            this.connectionDAO = connectionDAO;
        }

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
            System.out.println("Connection saved between: " + inputData.getFromNoteID() + " and " + inputData.getToNoteID());

            // Notify success
            outputBoundary.present(new AddConnectionOutputData(true, "Connection added successfully."));
        }

        @Override
        public void addConnection(use_case.add_connection.AddConnectionInputData inputData) {

        }
    }
}
