package app;

import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

// Import all necessary components
import data_access.InMemoryPostNoteDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.*;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_Image_PostNote.ImagePostNoteController;
import interface_adapter.add_Image_PostNote.ImagePostNotePresenter;
import interface_adapter.add_Image_PostNote.ImagePostNoteViewModel;
import interface_adapter.add_Text_PostNote.TextPostNoteController;
import interface_adapter.add_Text_PostNote.TextPostNotePresenter;
import interface_adapter.add_Text_PostNote.TextPostNoteViewModel;
import interface_adapter.change_color.ChangeColorController;
import interface_adapter.change_color.ChangeColorPresenter;
import interface_adapter.change_title.ChangeTitleController;
import interface_adapter.change_title.ChangeTitlePresenter;
import interface_adapter.change_title.LoggedInViewModel;
import interface_adapter.create_MindMap.MindMapController;
import interface_adapter.create_MindMap.MindMapPresenter;
import interface_adapter.create_MindMap.MindMapViewModel;
import interface_adapter.delete_note.DeletePostNoteController;
import interface_adapter.delete_note.DeletePostNotePresenter;
import interface_adapter.delete_note.DeletePostNoteViewModel;
import interface_adapter.export_mind_map.ExportController;
import interface_adapter.export_mind_map.ExportState;
import interface_adapter.export_mind_map.ExportViewModel;
import interface_adapter.fetch_image.*;
import interface_adapter.fetch_image.UnsplashFetchImageInputBoundary;
import interface_adapter.loading.LoadingViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import io.github.cdimascio.dotenv.Dotenv;
import use_case.add_Image_PostNote.ImagePostNoteInteractor;
import use_case.add_Text_PostNote.TextPostNoteInteractor;
import use_case.change_color.ChangeColorInputBoundary;
import use_case.change_color.ChangeColorInteractor;
import use_case.change_color.ChangeColorOutputBoundary;
import use_case.change_title.ChangeTitleInputBoundary;
import use_case.change_title.ChangeTitleInteractor;
import use_case.change_title.ChangeTitleOutputBoundary;
import use_case.create_MindMap.MindMapInputBoundary;
import use_case.create_MindMap.MindMapInteractor;
import use_case.create_MindMap.MindMapOutputBoundary;
import use_case.delete_note.DeletePostNoteInteractor;
import use_case.export_mind_map.ExportInteractor;
import use_case.fetch_image.FetchImageInteractor;
import use_case.fetch_image.FetchImageOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import view.CreateNewMindMapView;
import view.LoadedInView;
import view.MindMapView;
import view.ViewManager;
import data_access.ConnectionDAO;
import data_access.InMemoryConnectionDAO;
import interface_adapter.add_Connection.ConnectionViewModel;
import interface_adapter.add_Connection.AddConnectionController;
import interface_adapter.add_Connection.AddConnectionPresenter;
import use_case.add_connection.AddConnectionInputBoundary;
import use_case.add_connection.AddConnectionOutputBoundary;
import use_case.add_connection.AddConnectionInteractor;


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
    private final ConnectionDAO connectionDAO = new InMemoryConnectionDAO();
    private final ConnectionViewModel connectionViewModel = new ConnectionViewModel();
    private final AddConnectionOutputBoundary addConnectionOutputBoundary = new AddConnectionPresenter(connectionViewModel);
    private final AddConnectionInputBoundary addConnectionInteractor = new AddConnectionInteractor(addConnectionOutputBoundary, connectionDAO);
    private final AddConnectionController addConnectionController = new AddConnectionController(addConnectionInteractor);

    private CreateNewMindMapView createNewMindMapView;
    private MindMapViewModel mindMapViewModel;
    private LoadingViewModel loadingViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoadedInView loadedInView;

    private final Dotenv dotenv = Dotenv.configure()
            // Directory of the ..env file (default is root)
            .directory(".")
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
     * Adds the Change Password Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addChangeTitleUseCase() {
        // Create the ChangeTitleOutputBoundary to handle the success/failure of the title change
        final ChangeTitleOutputBoundary changeTitleOutputBoundary =
                new ChangeTitlePresenter(loggedInViewModel);

        // Create the ChangeTitleInteractor which contains the business logic for title change
        final ChangeTitleInputBoundary changeTitleInteractor =
                new ChangeTitleInteractor(changeTitleOutputBoundary);

        // Instantiate the ChangeTitleController to handle the request
        final ChangeTitleController changeTitleController =
                new ChangeTitleController(changeTitleInteractor, loggedInViewModel);  // Pass loggedInViewModel here

        // Inject the ChangeTitleController into the LoadedInView
        loadedInView.setChangeTitleController(changeTitleController);

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
        // Initialize the ViewModels
        final FetchImageViewModel fetchImageViewModel = new FetchImageViewModel();
        final ImagePostNoteViewModel imagePostNoteViewModel = new ImagePostNoteViewModel();
        final TextPostNoteViewModel textPostNoteViewModel = new TextPostNoteViewModel();

        // Initialize the Presenter with its associated ViewModel
        final FetchImagePresenter fetchImagePresenter = new FetchImagePresenter(fetchImageViewModel);

        // Set up the repository (UnsplashImageInputBoundary implementation)
        FetchImageRepository fetchImageRepository = new UnsplashFetchImageInputBoundary(unsplashApiKey);

        // Initialize the Output Boundary (typically the presenter)
        FetchImageOutputBoundary fetchImageOutputBoundary = fetchImagePresenter;

        // Initialize the Interactor (uses Repository and Output Boundary)
        final FetchImageInteractor fetchImageInteractor = new FetchImageInteractor(fetchImageRepository, fetchImageOutputBoundary);

        // Initialize the Controller (which uses the Interactor)
        final FetchImageController fetchImageController = new FetchImageController(fetchImageInteractor);


        // Initialize ExportController and related components
        final ExportState exportState = new ExportState();
        final ExportViewModel exportViewModel = new ExportViewModel(exportState);
        final ExportInteractor exportInteractor = new ExportInteractor(exportViewModel);
        final ExportController exportController = new ExportController(exportInteractor);

        // Create an empty list of PostNotes
        final ArrayList<PostNoteEntity> postNotes = new ArrayList<>();

        // Initialize ImagePostNotePresenter (this is the output boundary)
        final ImagePostNotePresenter imagePostNotePresenter = new ImagePostNotePresenter(imagePostNoteViewModel);

        // Initialize TextPostNotePresenter (this is the output boundary for TextPostNotes)
        final TextPostNotePresenter textPostNotePresenter = new TextPostNotePresenter(textPostNoteViewModel);

        // Initialize InMemoryPostNoteDAO (or use a different PostNoteDAO implementation)
        final InMemoryPostNoteDataAccessObject postNoteDAO = new InMemoryPostNoteDataAccessObject();

        // Initialize MindMapEntity
        final MindMapEntity mindMapEntity = new MindMapEntity("My Mind Map", postNotes);

        // Initialize the Interactors
        final ImagePostNoteInteractor imagePostNoteInteractor =
                new ImagePostNoteInteractor(imagePostNotePresenter, postNoteDAO, mindMapEntity);
        final TextPostNoteInteractor textPostNoteInteractor =
                new TextPostNoteInteractor(textPostNotePresenter, postNoteDAO, mindMapEntity);

        // Initialize the Controllers
        final ImagePostNoteController imagePostNoteController =
                new ImagePostNoteController(imagePostNoteInteractor, imagePostNoteViewModel);
        final TextPostNoteController textPostNoteController =
                new TextPostNoteController(textPostNoteInteractor, textPostNoteViewModel);

        // Initialize DeletePostNote components
        DeletePostNoteController deletePostNoteController = null;
        final DeletePostNoteViewModel deletePostNoteViewModel = new DeletePostNoteViewModel(deletePostNoteController);
        final DeletePostNotePresenter deletePostNotePresenter = new DeletePostNotePresenter(deletePostNoteViewModel);
        final DeletePostNoteInteractor deletePostNoteInteractor =
                new DeletePostNoteInteractor(deletePostNotePresenter, postNoteDAO, mindMapEntity);
        // Initialize the DeletePostNoteController
        deletePostNoteController = new DeletePostNoteController(deletePostNoteInteractor, deletePostNotePresenter);

        // Initialize ChangeColor components
        final ChangeColorOutputBoundary changeColorOutputBoundary = new ChangeColorPresenter(textPostNoteViewModel);
        final ChangeColorInputBoundary changeColorInteractor =
                new ChangeColorInteractor(postNoteDAO, changeColorOutputBoundary, postNoteDAO);
        final ChangeColorController changeColorController = new ChangeColorController(changeColorInteractor);

        // Initialize Connection components
        final ConnectionDAO connectionDAO = new InMemoryConnectionDAO();
        final ConnectionViewModel connectionViewModel = new ConnectionViewModel();
        final AddConnectionOutputBoundary addConnectionOutputBoundary = new AddConnectionPresenter(connectionViewModel);
        final AddConnectionInputBoundary addConnectionInteractor = new AddConnectionInteractor(addConnectionOutputBoundary, connectionDAO);
        final AddConnectionController connectionController = new AddConnectionController(addConnectionInteractor);

        // Initialize MindMapView
        final MindMapView mindMapView = new MindMapView(
                cardLayout, cardPanel,
                fetchImageController, fetchImageViewModel,
                imagePostNoteViewModel, textPostNoteViewModel,
                exportController, imagePostNoteController, textPostNoteController,
                deletePostNoteController,  // Correctly placed
                changeColorController,     // Correctly placed
                connectionController, connectionViewModel, connectionDAO
        );

        cardPanel.add(mindMapView, MindMapView.VIEW_NAME);

        return this;  // Return the builder for method chaining
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
