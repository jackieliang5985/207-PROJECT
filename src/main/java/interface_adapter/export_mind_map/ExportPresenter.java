package interface_adapter.export_mind_map;

import use_case.export_mind_map.ExportOutputBoundary;
import use_case.export_mind_map.ExportOutputData;

public class ExportPresenter implements ExportOutputBoundary {
    private final ExportViewModel exportViewModel;

    public ExportPresenter(ExportViewModel exportViewModel) {
        this.exportViewModel = exportViewModel;
    }

    @Override
    public void prepareSuccessView(ExportOutputData outputData) {
        exportViewModel.setFilePath(outputData.getFilePath());
        exportViewModel.setExportStatus("Success");
        exportViewModel.setErrorMessage(null);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        exportViewModel.setErrorMessage(errorMessage);
        exportViewModel.setExportStatus("Failure");
    }
}
