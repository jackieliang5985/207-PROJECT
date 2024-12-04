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
    private static final String ARIAL = "Arial";
    private static final int BORDER = 20;
    private static final int INSET = 10;
    private static final int REGULARFONTSIZE = 14;
    private static final int HEADINGFONTSIZE = 20;
    private static final int INPUTFIELDDIMENSIONWIDTH = 250;
    private static final int INPUTFIELDDIMENSIONHEIGHT = 30;
    private static final int BUTTONDIMENSIONWIDTH = 200;
    private static final int BUTTONDIMENSIONHEIGHT = 40;
    private static final int THREE = 3;
    private static final int FIVE = 5;
    private static final int FORTY = 40;
    private static final int FIFTY = 50;
    private static final int SEVENTY = 70;
    private static final int ONEHUNDRED = 100;
    private static final int ONEHUNDREDFOURTY = 140;
    private static final int TWOHUNDRED = 200;
    private static final int TWOHUNDREDFORTY = 240;
    private static final int TWOHUNDREDFIFTYFIVE = 255;

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
        this.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));

        // Center panel to mimic a box
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        // Light grey background
        centerPanel.setBackground(new Color(TWOHUNDREDFORTY, TWOHUNDREDFORTY, TWOHUNDREDFORTY));
        centerPanel.setBorder(BorderFactory.createLineBorder(
                new Color(ONEHUNDRED, ONEHUNDRED, TWOHUNDREDFIFTYFIVE), 2));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(INSET, INSET, INSET, INSET);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        final JLabel titleLabel = new JLabel("CREATE NEW MIND MAP");
        titleLabel.setFont(new Font(ARIAL, Font.BOLD, HEADINGFONTSIZE));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        // Set color to a subtle blue
        titleLabel.setForeground(new Color(FORTY, FORTY, TWOHUNDRED));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(titleLabel, gbc);

        // Label and input for title
        final JLabel titleInputLabel = new JLabel("Title");
        titleInputLabel.setFont(new Font(ARIAL, Font.PLAIN, REGULARFONTSIZE));
        // Dark gray text
        titleInputLabel.setForeground(new Color(FIFTY, FIFTY, FIFTY));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(titleInputLabel, gbc);

        // Resize input fields
        titleInputField.setPreferredSize(new Dimension(INPUTFIELDDIMENSIONWIDTH, INPUTFIELDDIMENSIONHEIGHT));
        titleInputField.setFont(new Font(ARIAL, Font.PLAIN, REGULARFONTSIZE));
        titleInputField.setBackground(new Color(TWOHUNDREDFIFTYFIVE, TWOHUNDREDFIFTYFIVE, TWOHUNDREDFIFTYFIVE));
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(titleInputField, gbc);

        // Button for creating a new mindmap
        toCreate = new JButton("Create New Mindmap");
        toCreate.setPreferredSize(new Dimension(BUTTONDIMENSIONWIDTH, BUTTONDIMENSIONHEIGHT));
        toCreate.setFont(new Font(ARIAL, Font.BOLD, REGULARFONTSIZE));
        // Green button
        toCreate.setBackground(new Color(ONEHUNDRED, TWOHUNDRED, ONEHUNDRED));
        toCreate.setForeground(Color.WHITE);
        toCreate.setFocusPainted(false);
        toCreate.setBorder(BorderFactory.createLineBorder(new Color(SEVENTY, ONEHUNDREDFOURTY, SEVENTY), 2));

        gbc.gridx = 0;
        gbc.gridy = THREE;
        gbc.gridwidth = 2;
        centerPanel.add(toCreate, gbc);

        // Cancel button
        cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(BUTTONDIMENSIONWIDTH, BUTTONDIMENSIONHEIGHT));
        cancel.setFont(new Font(ARIAL, Font.BOLD, REGULARFONTSIZE));
        // Red button
        cancel.setBackground(new Color(TWOHUNDREDFIFTYFIVE, ONEHUNDRED, ONEHUNDRED));
        cancel.setForeground(Color.WHITE);
        cancel.setFocusPainted(false);
        cancel.setBorder(BorderFactory.createLineBorder(new Color(TWOHUNDRED, SEVENTY, SEVENTY), 2));

        gbc.gridx = 0;
        gbc.gridy = FIVE;
        gbc.gridwidth = 2;
        centerPanel.add(cancel, gbc);

        // Action listeners
        toCreate.addActionListener(evt -> {
            createMindMap(mindMapViewModel);
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

    private void createMindMap(MindMapViewModel mindMapViewModel) {
        // Ensure the current state is updated before proceeding
        final MindMapState currentState = mindMapViewModel.getState();

        // Get the values directly from the text fields
        final String name = titleInputField.getText().trim();
        final String description = descriptionInputField.getText().trim();

        // Update the state with the current name and description
        currentState.setName(name);
        // currentState.setDescription(description);
        mindMapViewModel.setState(currentState);

        // if (name.isEmpty() || description.isEmpty()) //Only if we want description idk

        if (name.isEmpty()) {
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
