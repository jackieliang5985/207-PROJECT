package use_case.change_title;

public class ChangeTitleOutputData {
    private String newTitle;

    public ChangeTitleOutputData(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewTitle() {
        return newTitle;
    }
}
