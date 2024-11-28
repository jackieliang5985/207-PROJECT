package use_case.change_title;

public class ChangeTitleInputData {
    private String newTitle;
    private String currentTitle;

    public ChangeTitleInputData(String currentTitle, String newTitle) {
        this.currentTitle = currentTitle;
        this.newTitle = newTitle;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public String getCurrentTitle() {
        return currentTitle;
    }
}

