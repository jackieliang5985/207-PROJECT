package interface_adapter.add_Connection;

import use_case.add_connection.AddConnectionOutputData;


public class AddConnectionPresenter {
    private final ConnectionViewModel viewModel;

    public AddConnectionPresenter(ConnectionViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(AddConnectionOutputData outputData) {
        viewModel.setSuccess(outputData.isSuccess());
        viewModel.setMessage(outputData.getMessage());
        // Notify observers (e.g., the view) if necessary
    }
}
