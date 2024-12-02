package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    public CreateNewMindMapView(MindMapViewModel mindMapViewModel) {
        this.mindMapViewModel = mindMapViewModel;
        mindMapViewModel.addPropertyChangeListener(this);

        // Main panel setup
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Center panel to mimic a box
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 240, 240)); // Light grey background
        centerPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        final JLabel titleLabel = new JLabel("CREATE NEW MIND MAP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(new Color(40, 40, 200)); // Set color to a subtle blue

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(titleLabel, gbc);

        // Label and input for title
        final JLabel titleInputLabel = new JLabel("Title");
        titleInputLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleInputLabel.setForeground(new Color(50, 50, 50)); // Dark gray text
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(titleInputLabel, gbc);

        titleInputField.setPreferredSize(new Dimension(250, 30)); // Resize input fields
        titleInputField.setFont(new Font("Arial", Font.PLAIN, 14));
        titleInputField.setBackground(new Color(255, 255, 255));
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(titleInputField, gbc);

        // Button for creating a new mindmap
        toCreate = new JButton("Create New Mindmap");
        toCreate.setPreferredSize(new Dimension(200, 40));
        toCreate.setFont(new Font("Arial", Font.BOLD, 14));
        toCreate.setBackground(new Color(100, 200, 100)); // Green button
        toCreate.setForeground(Color.WHITE);
        toCreate.setFocusPainted(false);
        toCreate.setBorder(BorderFactory.createLineBorder(new Color(70, 140, 70), 2));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(toCreate, gbc);

        // Cancel button
        cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(200, 40));
        cancel.setFont(new Font("Arial", Font.BOLD, 14));
        cancel.setBackground(new Color(255, 100, 100)); // Red button
        cancel.setForeground(Color.WHITE);
        cancel.setFocusPainted(false);
        cancel.setBorder(BorderFactory.createLineBorder(new Color(200, 70, 70), 2));

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        centerPanel.add(cancel, gbc);

        // Action listeners
        toCreate.addActionListener(evt -> {
            // Ensure the current state is updated before proceeding
            final MindMapState currentState = mindMapViewModel.getState();

            // Get the values directly from the text fields
            String name = titleInputField.getText().trim();
            String description = descriptionInputField.getText().trim();

            // Update the state with the current name and description
            currentState.setName(name);
            // currentState.setDescription(description);
            mindMapViewModel.setState(currentState);

            // if (name.isEmpty() || description.isEmpty()) //Only if we want description idk

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and description cannot be empty.");
            } else {
                if (currentState.isValidName()) {
                    mindMapController.execute(name, description);
                } else {
                    JOptionPane.showMessageDialog(this, currentState.getNameError());
                }
            }
        });

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
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addDescriptionListener() {
        descriptionInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final MindMapState currentState = mindMapViewModel.getState();
            // currentState.setDescription(descriptionInputField.getText());
                mindMapViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
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
