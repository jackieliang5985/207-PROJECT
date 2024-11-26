package interface_adapter.add_Image_PostNote;

import use_case.add_Image_PostNote.ImagePostNoteInputBoundary;
import use_case.add_Image_PostNote.ImagePostNoteInputData;
import view.MindMapView;

public class ImagePostNoteController {
    private final ImagePostNoteInputBoundary inputBoundary;
    private MindMapView mindMapView;  // Add reference for MindMapView

    // Constructor
    public ImagePostNoteController(ImagePostNoteInputBoundary inputBoundary, MindMapView mindMapView) {
        this.inputBoundary = inputBoundary;
        this.mindMapView = mindMapView;  // Initialize the view
    }

    // Method to set MindMapView (after initialization)
    public void setMindMapView(MindMapView mindMapView) {
        this.mindMapView = mindMapView;
    }

    public void addImagePostNote(ImagePostNoteViewModel viewModel) {
        // Load the image to set width and height
        viewModel.loadImage();

        // Call updatePostNotes to add the image with its size
        mindMapView.updatePostNotes(viewModel);
    }
}
