package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import app.AppBuilder;
import interface_adapter.change_title.ChangeTitleController;
import interface_adapter.change_title.LoadedInState;
import interface_adapter.change_title.LoadedInViewModel;
import interface_adapter.logout.LogoutController;

public class LoadedInView extends JPanel implements PropertyChangeListener {
    private final String viewName = "logged in";
    private final LoadedInViewModel loadedInViewModel;
    private ChangeTitleController changeTitleController;
    private LogoutController logoutController;
    private final JLabel username;
    private final JButton returnToCreateMindmap;
    private final JButton changeMindMap;
    private final JButton continueToMindMap;

    public LoadedInView(LoadedInViewModel loadedInViewModel, AppBuilder appBuilder, CardLayout cardLayout, Container cardPanel) {
        this.loadedInViewModel = loadedInViewModel;
        this.loadedInViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        centerPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        final JLabel title = new JLabel("LOADED MIND MAP");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(new Color(40, 40, 200)); // Blue text

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(title, gbc);

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

        returnToCreateMindmap = new JButton("Go back to home page");
        returnToCreateMindmap.setPreferredSize(new Dimension(250, 35));
        returnToCreateMindmap.setFont(new Font("Arial", Font.BOLD, 14));
        returnToCreateMindmap.setBackground(new Color(255, 100, 100)); // red button
        returnToCreateMindmap.setForeground(Color.WHITE);
        returnToCreateMindmap.setFocusPainted(false);
        returnToCreateMindmap.setBorder(BorderFactory.createLineBorder(new Color(50, 100, 150), 2));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(returnToCreateMindmap, gbc);

        changeMindMap = new JButton("Change MindMap Title");
        changeMindMap.setPreferredSize(new Dimension(250, 35));
        changeMindMap.setFont(new Font("Arial", Font.BOLD, 14));
        changeMindMap.setBackground(new Color(100, 200, 100)); // Green button
        changeMindMap.setForeground(Color.WHITE);
        changeMindMap.setFocusPainted(false);
        changeMindMap.setBorder(BorderFactory.createLineBorder(new Color(70, 140, 70), 2));
        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPanel.add(changeMindMap, gbc);

        continueToMindMap = new JButton("Continue to Mind Map");
        continueToMindMap.setPreferredSize(new Dimension(250, 35));
        continueToMindMap.setFont(new Font("Arial", Font.BOLD, 14));
        continueToMindMap.setBackground(new Color(100, 150, 200)); // Light blue button
        continueToMindMap.setForeground(Color.WHITE);
        continueToMindMap.setFocusPainted(false);
        continueToMindMap.setBorder(BorderFactory.createLineBorder(new Color(70, 110, 140), 2));
        gbc.gridx = 0;
        gbc.gridy = 4;
        centerPanel.add(continueToMindMap, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        continueToMindMap.addActionListener(evt -> appBuilder.showMindMapView());

        returnToCreateMindmap.addActionListener(evt -> {
            final String createNewMindMapViewName = "CreateNewMindMapView";
            JOptionPane.showMessageDialog(LoadedInView.this, "Loading canceled, returning to Mindmap Creation page");
            cardLayout.show(cardPanel, createNewMindMapViewName);
        });

        // Action listener for changing the mind map title
        changeMindMap.addActionListener(evt -> {
            String newTitle = JOptionPane.showInputDialog(this, "Enter a new title for the mind map:");
            if (newTitle != null && !newTitle.isEmpty()) {
                // Call ChangeTitleController to change the title
                changeTitleController.execute(newTitle, username.getText());
            } else {
                JOptionPane.showMessageDialog(this, "Title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("name".equals(evt.getPropertyName())) {
            LoadedInState state = (LoadedInState) evt.getNewValue();
            System.out.println("Property change event triggered in LoadedInView.");
            System.out.println("New Title from PropertyChangeEvent: " + state.getName());  // Debugging output
            username.setText(state.getName());  // Update the username label with the new title
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setChangeTitleController(ChangeTitleController changeTitleController) {
        this.changeTitleController = changeTitleController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }
}
