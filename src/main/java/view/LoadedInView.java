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

        // Center panel setup
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        centerPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        final JLabel title = new JLabel("LOADED MIND MAP");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(new Color(40, 40, 200)); // Blue text

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(title, gbc);

        // Label and input for current MindMap name
        final JLabel usernameInfo = new JLabel("Currently Loaded MindMap Name:");
        usernameInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameInfo.setForeground(new Color(50, 50, 50)); // Dark gray
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(usernameInfo, gbc);

        username = new JLabel();
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setForeground(new Color(70, 70, 70)); // Slightly lighter gray
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(username, gbc);

        // Label and input for password (ID)
        final JLabel passwordLabel = new JLabel("ID:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(50, 50, 50)); // Dark gray
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(passwordLabel, gbc);

        passwordInputField.setPreferredSize(new Dimension(200, 30));
        passwordInputField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordInputField.setBackground(new Color(255, 255, 255)); // White background for input field
        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(passwordInputField, gbc);

        // Button for returning to the MindMap creation page
        returnToCreateMindmap = new JButton("Go back to home page");
        returnToCreateMindmap.setPreferredSize(new Dimension(250, 35));
        returnToCreateMindmap.setFont(new Font("Arial", Font.BOLD, 14));
        returnToCreateMindmap.setBackground(new Color(255, 100, 100)); // red button
        returnToCreateMindmap.setForeground(Color.WHITE);
        returnToCreateMindmap.setFocusPainted(false);
        returnToCreateMindmap.setBorder(BorderFactory.createLineBorder(new Color(50, 100, 150), 2));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(returnToCreateMindmap, gbc);

        // Button for changing ID to load existing MindMap
        changeMindMap = new JButton("Change ID to load existing MindMap");
        changeMindMap.setPreferredSize(new Dimension(250, 35));
        changeMindMap.setFont(new Font("Arial", Font.BOLD, 14));
        changeMindMap.setBackground(new Color(100, 200, 100)); // Green button
        changeMindMap.setForeground(Color.WHITE);
        changeMindMap.setFocusPainted(false);
        changeMindMap.setBorder(BorderFactory.createLineBorder(new Color(70, 140, 70), 2));
        gbc.gridx = 0;
        gbc.gridy = 4;
        centerPanel.add(changeMindMap, gbc);

        // Button for continuing to the MindMap
        continueToMindMap = new JButton("Continue to Mind Map");
        continueToMindMap.setPreferredSize(new Dimension(250, 35));
        continueToMindMap.setFont(new Font("Arial", Font.BOLD, 14));
        continueToMindMap.setBackground(new Color(100, 150, 200)); // Light blue button
        continueToMindMap.setForeground(Color.WHITE);
        continueToMindMap.setFocusPainted(false);
        continueToMindMap.setBorder(BorderFactory.createLineBorder(new Color(70, 110, 140), 2));
        gbc.gridx = 0;
        gbc.gridy = 5;
        centerPanel.add(continueToMindMap, gbc);

        // Add center panel to the main panel
        this.add(centerPanel, BorderLayout.CENTER);

        // Action listener for the continueToMindMap button
        continueToMindMap.addActionListener(evt -> appBuilder.showMindMapView());

        // Action listener for the returnToCreateMindmap button
        returnToCreateMindmap.addActionListener(evt -> {
            final String createNewMindMapViewName = "CreateNewMindMapView";
            JOptionPane.showMessageDialog(LoadedInView.this, "Loading canceled, returning to Mindmap Creation page");
            cardLayout.show(cardPanel, createNewMindMapViewName);
        });

        // Action listener for the changeMindMap button
        changeMindMap.addActionListener(evt -> {
            // Handle the logic for changing the MindMap ID
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText(state.getName());
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
