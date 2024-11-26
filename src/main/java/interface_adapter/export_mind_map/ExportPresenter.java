package interface_adapter.export_mind_map;

import use_case.export_mind_map.ExportOutputBoundary;
import use_case.export_mind_map.ExportOutputData;

public class ExportPresenter implements ExportOutputBoundary {
    private final ExportViewModel viewModel;

    public ExportPresenter(ExportViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ExportOutputData outputData) {
        viewModel.setExportStatus("Success");
        viewModel.setFilePath(outputData.getFilePath());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setExportStatus("Failure");
        viewModel.setErrorMessage(errorMessage);
    }
}