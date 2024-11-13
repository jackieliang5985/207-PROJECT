package view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.AppBuilder;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.create_MindMap.MindMapState;
import interface_adapter.logout.LogoutController;

public class LoggedInView extends JPanel implements PropertyChangeListener {
    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private final JLabel passwordErrorField = new JLabel();
    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;
    private final JLabel username;
    private final JButton returnToCreateMindmap;
    private final JTextField passwordInputField = new JTextField(15);
    private final JButton changeMindMap;
    private final JButton continueToMindMap;

    public LoggedInView(LoggedInViewModel loggedInViewModel, AppBuilder appBuilder, CardLayout cardLayout, Container cardPanel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Logged In Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        final LabelTextPanel passwordInfo = new LabelTextPanel(new JLabel("ID"), passwordInputField);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();

        final JPanel buttons = new JPanel();
        returnToCreateMindmap = new JButton("Go back to home page");
        buttons.add(returnToCreateMindmap);

        changeMindMap = new JButton("Change ID to load existing MindMap");
        buttons.add(changeMindMap);

        continueToMindMap = new JButton("Continue to Mind Map");
        buttons.add(continueToMindMap);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(usernameInfo);
        this.add(username);
        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(buttons);

        continueToMindMap.addActionListener(evt -> appBuilder.showMindMapView());

        // Action listener for the returnToCreateMindmap button
        returnToCreateMindmap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JOptionPane.showMessageDialog(LoggedInView.this, "Loading canceled.");
                final String createNewMindMapViewName = "CreateNewMindMapView";
                JOptionPane.showMessageDialog(
                        LoggedInView.this, "Loading canceled,"
                                + " returning to Mindmap Creation page"
                );
                cardLayout.show(cardPanel, createNewMindMapViewName);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText(state.getName());
        } else if (evt.getPropertyName().equals("password")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "password updated for " + state.getName());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }
}
