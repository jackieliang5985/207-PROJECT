package data_access;

import entity.ConnectionEntity;
import java.util.List;

public interface ConnectionDAO {
    void saveConnection(ConnectionEntity connection);

    void deleteConnectionsByNoteId(String noteId);

    List<ConnectionEntity> getAllConnections();
}
