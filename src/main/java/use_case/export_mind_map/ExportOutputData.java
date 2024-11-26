package use_case.export_mind_map;

public class ExportOutputData {
    private final String filePath; // Path of the successfully exported file

    public ExportOutputData(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}