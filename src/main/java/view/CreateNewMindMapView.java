package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.create_MindMap.MindMapController;
import interface_adapter.create_MindMap.MindMapState;
import interface_adapter.create_MindMap.MindMapViewModel;

/**
 * The View for the Create New Mindmap Use Case.
 */
public class CreateNewMindMapView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "create new mindmap";

    private final MindMapViewModel mindMapViewModel;
    private final JTextField titleInputField = new JTextField(15);
    private final JTextField descriptionInputField = new JTextField(15);
    private MindMapController mindMapController;

    private final JButton toCreate;
    private final JButton cancel;
    private final JButton toLoad;

    public CreateNewMindMapView(MindMapViewModel mindMapViewModel) {
        this.mindMapViewModel = mindMapViewModel;
        mindMapViewModel.addPropertyChangeListener(this);

        // Main panel setup
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Center panel to mimic a box
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        final JLabel titleLabel = new JLabel("DETECTIVE CLUE BOARD SIMULATOR");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(titleLabel, gbc);

        // Label and input for title
        final JLabel titleInputLabel = new JLabel("Title");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(titleInputLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(titleInputField, gbc);

        // Label and input for description
        final JLabel descriptionInputLabel = new JLabel("Description");
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(descriptionInputLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(descriptionInputField, gbc);

        // Button for creating a new mindmap
        toCreate = new JButton("Create New Mindmap");
        toCreate.setPreferredSize(new Dimension(200, 30));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(toCreate, gbc);

        // Button for loading an existing mindmap
        toLoad = new JButton("Load Existing Mindmap");
        toLoad.setPreferredSize(new Dimension(200, 30));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        centerPanel.add(toLoad, gbc);

        // Cancel button
        cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(200, 30));

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        centerPanel.add(cancel, gbc);

        toCreate.addActionListener(evt -> {
            // Ensure the current state is updated before proceeding
            final MindMapState currentState = mindMapViewModel.getState();

            // Get the values directly from the text fields
            String name = titleInputField.getText().trim();
            String description = descriptionInputField.getText().trim();

            // Update the state with the current name and description
            currentState.setName(name);
            currentState.setDescription(description);
            mindMapViewModel.setState(currentState);

            if (name.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and description cannot be empty.");
            }
            else {
                if (currentState.isValidName()) {
                    mindMapController.execute(name, description);
                }
                else {
                    JOptionPane.showMessageDialog(this, currentState.getNameError());
                }
            }
        });
        toLoad.addActionListener(evt -> mindMapController.switchToLoginView());

        cancel.addActionListener(evt -> {
            JOptionPane.showMessageDialog(CreateNewMindMapView.this, "Creation canceled. Closing program...");
            System.exit(0);
        });

        // Add listeners for input fields
        addTitleListener();
        addDescriptionListener();

        // Add the center panel to the main panel
        this.add(centerPanel, BorderLayout.CENTER);
    }

    private void addTitleListener() {
        titleInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final MindMapState currentState = mindMapViewModel.getState();
                currentState.setName(titleInputField.getText());
                mindMapViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    private void addDescriptionListener() {
        descriptionInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final MindMapState currentState = mindMapViewModel.getState();
                currentState.setDescription(descriptionInputField.getText());
                mindMapViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final MindMapState state = (MindMapState) evt.getNewValue();
        if (state.getNameError() != null) {
            JOptionPane.showMessageDialog(this, state.getNameError());
        }
    }

    public String getViewName() {
        return "CreateNewMindMapView";
    }

    public void setSignupController(MindMapController controller) {
        this.mindMapController = controller;
    }
}
