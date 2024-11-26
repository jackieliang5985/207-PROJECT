package app;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

// Import all necessary components
import data_access.InMemoryImageDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import data_access.UnsplashImageRepository;
import entity.CommonUserFactory;
import entity.PostNote;
import entity.UserFactory;
import interface_adapter.UnsplashImageInputBoundary;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_Image_PostNote.ImagePostNoteController;
import interface_adapter.add_Image_PostNote.ImagePostNotePresenter;
import interface_adapter.add_Image_PostNote.ImagePostNoteViewModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.create_MindMap.MindMapController;
import interface_adapter.create_MindMap.MindMapPresenter;
import interface_adapter.create_MindMap.MindMapViewModel;
import interface_adapter.create_MindMap.SquarePanel;
import interface_adapter.export_mind_map.ExportController;
import interface_adapter.export_mind_map.ExportState;
import interface_adapter.export_mind_map.ExportViewModel;
import interface_adapter.image.*;
import interface_adapter.loading.LoadingController;
import interface_adapter.loading.LoadingPresenter;
import interface_adapter.loading.LoadingViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import io.github.cdimascio.dotenv.Dotenv;
import use_case.add_Image_PostNote.ImagePostNoteInteractor;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.create_MindMap.MindMapInputBoundary;
import use_case.create_MindMap.MindMapInteractor;
import use_case.create_MindMap.MindMapOutputBoundary;
import use_case.export_mind_map.ExportInteractor;
import use_case.image.ImageInteractor;
import use_case.loading.LoadingInputBoundary;
import use_case.loading.LoadingInteractor;
import use_case.loading.LoadingOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import view.CreateNewMindMapView;
import view.LoadedInView;
import view.MindMapLoadingView;
import view.MindMapView;
import view.ViewManager;

/**
 * The AppBuilder class is responsible for putting together the components
 * for the application's architecture.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();

    private CreateNewMindMapView createNewMindMapView;
    private MindMapViewModel mindMapViewModel;
    private LoadingViewModel loadingViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoadedInView loadedInView;
    private MindMapLoadingView mindMapLoadingView;
    Dotenv dotenv = Dotenv.configure()
            .directory(".") // Directory of the ..env file (default is root)
            .load();

    private final String unsplashApiKey = dotenv.get("UNSPLASH_API_KEY");

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     *
     * @return this builder
     */
    public AppBuilder addSignupView() {
        mindMapViewModel = new MindMapViewModel();
        createNewMindMapView = new CreateNewMindMapView(mindMapViewModel);
        cardPanel.add(createNewMindMapView, createNewMindMapView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     *
     * @return this builder
     */
    public AppBuilder addMindMapLoadingView() {
        loadingViewModel = new LoadingViewModel();
        final MindMapViewModel mindMapViewModel = new MindMapViewModel();
        mindMapLoadingView = new MindMapLoadingView(mindMapViewModel, viewManagerModel, cardPanel, cardLayout);
        cardPanel.add(mindMapLoadingView, mindMapLoadingView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     *
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loadedInView = new LoadedInView(loggedInViewModel, this, cardLayout, cardPanel);
        cardPanel.add(loadedInView, loadedInView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final MindMapOutputBoundary mindMapOutputBoundary =
                new MindMapPresenter(viewManagerModel, mindMapViewModel, loadingViewModel, loggedInViewModel);
        final MindMapInputBoundary userSignupInteractor =
                new MindMapInteractor(userDataAccessObject, mindMapOutputBoundary, userFactory);
        final MindMapController controller =
                new MindMapController(userSignupInteractor);
        createNewMindMapView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        loadingViewModel = new LoadingViewModel();
        final LoadingOutputBoundary loadingOutputBoundary =
                new LoadingPresenter(viewManagerModel, loggedInViewModel, loadingViewModel);
        final LoadingInputBoundary loginInteractor = new LoadingInteractor(userDataAccessObject, loadingOutputBoundary);
        final LoadingController loadingController = new LoadingController(loginInteractor);
        mindMapLoadingView.setLoginController(loadingController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);
        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);
        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        loadedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary =
                new LogoutPresenter(viewManagerModel, loggedInViewModel, loadingViewModel);
        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);
        final LogoutController logoutController =
                new LogoutController(logoutInteractor);
        loadedInView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Adds the MindMap View to the application.
     *
     * @return this builder
     */
    public AppBuilder addMindMapView() {
        // Initialize the ImageViewModel and ImagePostNoteViewModel
        ImageViewModel imageViewModel = new ImageViewModel();
        ImagePostNoteViewModel imagePostNoteViewModel = new ImagePostNoteViewModel();

        // Initialize ImagePresenter (uses ImageViewModel)
        ImagePresenter imagePresenter = new ImagePresenter(imageViewModel);

        // Use UnsplashImageInputBoundary as ImageRepository and create ImageInteractor
        ImageInteractor imageInteractor = new ImageInteractor(new UnsplashImageInputBoundary(unsplashApiKey), imagePresenter);
        ImageController imageController = new ImageController(imageInteractor);

        // Initialize ExportController and related components
        ExportState exportState = new ExportState();
        ExportViewModel exportViewModel = new ExportViewModel(exportState);
        ExportInteractor exportInteractor = new ExportInteractor(exportViewModel);
        ExportController exportController = new ExportController(exportInteractor);

        // Create an empty list of PostNotes
        ArrayList<PostNote> postNotes = new ArrayList<>();

        // Initialize SquarePanel with postNotes
        SquarePanel boardPanel = new SquarePanel(postNotes);

        // Initialize the ImagePostNotePresenter and Controller
        ImagePostNotePresenter imagePostNotePresenter = new ImagePostNotePresenter(imagePostNoteViewModel); // Presenter uses ImagePostNoteViewModel
        ImagePostNoteController imagePostNoteController = new ImagePostNoteController(new ImagePostNoteInteractor(imagePostNotePresenter), null);

        // Initialize MindMapView (mindMapView) first
        final MindMapView mindMapView = new MindMapView(cardLayout, cardPanel, imageController,
                imageViewModel, imagePostNoteViewModel,
                exportController, imagePostNoteController);

        // Now pass the mindMapView to ImagePostNoteController
        imagePostNoteController.setMindMapView(mindMapView);  // Set MindMapView in the controller

        // Add the MindMapView to the cardPanel
        cardPanel.add(mindMapView, MindMapView.VIEW_NAME);

        return this;
    }










    /**
     * Switches to display the MindMap view.
     */
    public void showMindMapView() {
        cardLayout.show(cardPanel, MindMapView.VIEW_NAME);
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     *
     * @return the application
     */

    public JFrame build() {
        final JFrame application = new JFrame("MindMap");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);
        viewManagerModel.setState(createNewMindMapView.getViewName());
        viewManagerModel.firePropertyChanged();
        return application;
    }
}