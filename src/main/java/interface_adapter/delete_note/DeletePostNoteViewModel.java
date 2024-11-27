//package interface_adapter.delete_note;
////
////public class DeletePostNoteViewModel {
//    private DeletePostNoteController controller;
//    private boolean isDeletedSuccessfully;
//    private String errorMessage;
//
//    public DeletePostNoteViewModel(DeletePostNoteController controller) {
//        this.controller = controller;
//    }
//
//    // Getter for the delete success state
//    public boolean isDeletedSuccessfully() {
//        return isDeletedSuccessfully;
//    }
//
//    // Getter for error messages
//    public String getErrorMessage() {
//        return errorMessage;
//    }
//
//    // Update state when the deletion succeeds
//    public void onPostNoteDeleted() {
//        this.isDeletedSuccessfully = true;
//        this.errorMessage = null;
//    }
//
//    // Update state when the deletion fails
//    public void onDeleteFailed(String errorMessage) {
//        this.isDeletedSuccessfully = false;
//        this.errorMessage = errorMessage;
//    }
//}
