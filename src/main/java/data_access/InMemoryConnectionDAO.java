package data_access;

import entity.ConnectionEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryConnectionDAO implements ConnectionDAO {
    private final List<ConnectionEntity> connections = new ArrayList<>();

    @Override
    public void saveConnection(ConnectionEntity connection) {
        connections.add(connection);
    }

    @Override
    public void deleteConnectionsByNoteId(String noteId) {
        connections.removeIf(c -> c.getFromNoteId().equals(noteId) || c.getToNoteId().equals(noteId));
    }

    @Override
    public List<ConnectionEntity> getAllConnections() {
        return new ArrayList<>(connections);
    }
}


