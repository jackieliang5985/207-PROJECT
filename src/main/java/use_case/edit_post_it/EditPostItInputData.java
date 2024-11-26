package use_case.edit_post_it;

public class EditPostItInputData {
    private final String postNoteId;
    private final String newText;

    public EditPostItInputData(String postNoteId, String newText) {
        this.postNoteId = postNoteId;
        this.newText = newText;
    }

    public String getPostNoteId() { return postNoteId; }
    public String getNewText() { return newText; }
}
