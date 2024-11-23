package app;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addMindMapLoadingView()
                .addSignupView()
                .addLoggedInView()
                .addMindMapView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addChangePasswordUseCase()
                .addLogoutUseCase()
                .build();

        // Set the application to maximized state
        application.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Make the application visible
        application.setVisible(true);
    }
}
