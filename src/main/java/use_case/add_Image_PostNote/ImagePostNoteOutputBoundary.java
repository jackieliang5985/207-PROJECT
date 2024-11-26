package use_case.add_Image_PostNote;

import interface_adapter.add_Image_PostNote.ImagePostNoteData;

public interface ImagePostNoteOutputBoundary {
    void presentImagePostNotes(ImagePostNoteData data);

    void presentError(String errorMessage); // Add this method
}
