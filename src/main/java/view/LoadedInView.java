package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import app.AppBuilder;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.logout.LogoutController;

public class LoadedInView extends JPanel implements PropertyChangeListener {
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

    public LoadedInView(LoggedInViewModel loggedInViewModel, AppBuilder appBuilder, CardLayout cardLayout, Container cardPanel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Loading In Screen");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(title, gbc);

        JLabel usernameInfo = new JLabel("Currently Loaded MindMap Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(usernameInfo, gbc);

        username = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(username, gbc);

        JLabel passwordLabel = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(passwordLabel, gbc);

        passwordInputField.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(passwordInputField, gbc);

        returnToCreateMindmap = new JButton("Go back to home page");
        returnToCreateMindmap.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(returnToCreateMindmap, gbc);

        changeMindMap = new JButton("Change ID to load existing MindMap");
        changeMindMap.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 0;
        gbc.gridy = 4;
        centerPanel.add(changeMindMap, gbc);

        continueToMindMap = new JButton("Continue to Mind Map");
        continueToMindMap.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 0;
        gbc.gridy = 5;
        centerPanel.add(continueToMindMap, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Action listener for the continueToMindMap button
        continueToMindMap.addActionListener(evt -> appBuilder.showMindMapView());

        // Action listener for the changeMindMap button

        // Action listener for the returnToCreateMindmap button
        returnToCreateMindmap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JOptionPane.showMessageDialog(LoadedInView.this, "Loading canceled.");
                final String createNewMindMapViewName = "CreateNewMindMapView";
                JOptionPane.showMessageDialog(
                        LoadedInView.this, "Loading canceled,"
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
