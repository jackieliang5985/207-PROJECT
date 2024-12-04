package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import app.AppBuilder;
import interface_adapter.change_title.ChangeTitleController;
import interface_adapter.change_title.LoadedInState;
import interface_adapter.change_title.LoadedInViewModel;
import interface_adapter.logout.LogoutController;

/**
 * The Loaded In View for the Change Title Use Case.
 */
public class LoadedInView extends JPanel implements PropertyChangeListener {
    private static final String ARIAL = "Arial";
    private static final int BORDER = 20;
    private static final int INSET = 10;
    private static final int REGULARFONTSIZE = 14;
    private static final int HEADINGFONTSIZE = 20;
    private static final int DIMENSIONHEIGHT = 35;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FORTY = 40;
    private static final int FIFTY = 50;
    private static final int SEVENTY = 70;
    private static final int ONEHUNDRED = 100;
    private static final int ONEHUNDREDTEN = 110;
    private static final int ONEHUNDREDFOURTY = 140;
    private static final int ONEHUNDREDFIFTY = 150;
    private static final int TWOHUNDRED = 200;
    private static final int TWOHUNDREDFORTY = 240;
    private static final int DIMENSIONWIDTH = 250;
    private static final int TWOHUNDREDFIFTYFIVE = 255;

    private final String viewName = "logged in";
    private final LoadedInViewModel loadedInViewModel;
    private ChangeTitleController changeTitleController;
    private LogoutController logoutController;
    private final JLabel title;
    private final JButton returnToCreateMindmap;
    private final JButton changeMindMap;
    private final JButton continueToMindMap;

    public LoadedInView(LoadedInViewModel loadedInViewModel, AppBuilder appBuilder,
                        CardLayout cardLayout, Container cardPanel) {
        this.loadedInViewModel = loadedInViewModel;
        this.loadedInViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));

        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        // Light gray background
        centerPanel.setBackground(new Color(TWOHUNDREDFORTY, TWOHUNDREDFORTY, TWOHUNDREDFORTY));
        centerPanel.setBorder(BorderFactory.createLineBorder(
                new Color(ONEHUNDRED, ONEHUNDRED, TWOHUNDREDFIFTYFIVE), 2));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(INSET, INSET, INSET, INSET);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        final JLabel title = new JLabel("LOADED MIND MAP");
        title.setFont(new Font(ARIAL, Font.BOLD, HEADINGFONTSIZE));
        title.setHorizontalAlignment(JLabel.CENTER);
        // Blue text
        title.setForeground(new Color(FORTY, FORTY, TWOHUNDRED));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(title, gbc);

        final JLabel usernameInfo = new JLabel("Currently Loaded MindMap Name:");
        usernameInfo.setFont(new Font(ARIAL, Font.PLAIN, REGULARFONTSIZE));
        // Dark gray
        usernameInfo.setForeground(new Color(FIFTY, FIFTY, FIFTY));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(usernameInfo, gbc);

        this.title = new JLabel();
        this.title.setFont(new Font(ARIAL, Font.PLAIN, REGULARFONTSIZE));
        // Slightly lighter gray
        this.title.setForeground(new Color(SEVENTY, SEVENTY, SEVENTY));
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(this.title, gbc);

        returnToCreateMindmap = new JButton("Go back to home page");
        returnToCreateMindmap.setPreferredSize(new Dimension(DIMENSIONWIDTH, DIMENSIONHEIGHT));
        returnToCreateMindmap.setFont(new Font(ARIAL, Font.BOLD, REGULARFONTSIZE));
        // Red button
        returnToCreateMindmap.setBackground(new Color(TWOHUNDREDFIFTYFIVE, ONEHUNDRED, ONEHUNDRED));
        returnToCreateMindmap.setForeground(Color.WHITE);
        returnToCreateMindmap.setFocusPainted(false);
        returnToCreateMindmap.setBorder(BorderFactory.createLineBorder(
                new Color(FIFTY, ONEHUNDRED, ONEHUNDREDFIFTY), 2));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(returnToCreateMindmap, gbc);

        changeMindMap = new JButton("Change MindMap Title");
        changeMindMap.setPreferredSize(new Dimension(DIMENSIONWIDTH, DIMENSIONHEIGHT));
        changeMindMap.setFont(new Font(ARIAL, Font.BOLD, REGULARFONTSIZE));
        // Green button
        changeMindMap.setBackground(new Color(ONEHUNDRED, TWOHUNDRED, ONEHUNDRED));
        changeMindMap.setForeground(Color.WHITE);
        changeMindMap.setFocusPainted(false);
        changeMindMap.setBorder(BorderFactory.createLineBorder(new Color(SEVENTY, ONEHUNDREDFOURTY, SEVENTY), 2));
        gbc.gridx = 0;
        gbc.gridy = THREE;
        centerPanel.add(changeMindMap, gbc);

        continueToMindMap = new JButton("Continue to Mind Map");
        continueToMindMap.setPreferredSize(new Dimension(DIMENSIONWIDTH, DIMENSIONHEIGHT));
        continueToMindMap.setFont(new Font(ARIAL, Font.BOLD, REGULARFONTSIZE));
        // Light blue button
        continueToMindMap.setBackground(new Color(ONEHUNDRED, ONEHUNDREDFIFTY, TWOHUNDRED));
        continueToMindMap.setForeground(Color.WHITE);
        continueToMindMap.setFocusPainted(false);
        continueToMindMap.setBorder(BorderFactory.createLineBorder(
                new Color(SEVENTY, ONEHUNDREDTEN, ONEHUNDREDFOURTY), 2));
        gbc.gridx = 0;
        gbc.gridy = FOUR;
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
            final String newTitle = JOptionPane.showInputDialog(this, "Enter a new title for the mind map:");
            if (newTitle != null && !newTitle.isEmpty()) {
                // Call ChangeTitleController to change the title
                changeTitleController.execute(newTitle, title.getText());
            }
            else {
                JOptionPane.showMessageDialog(this, "Title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("name".equals(evt.getPropertyName())) {
            final LoadedInState state = (LoadedInState) evt.getNewValue();
            System.out.println("Property change event triggered in LoadedInView.");
            System.out.println("New Title from PropertyChangeEvent: " + state.getName());
            // Update the username label with the new title
            title.setText(state.getName());
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
