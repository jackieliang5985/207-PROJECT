package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.create_MindMap.MindMapController;
import interface_adapter.create_MindMap.MindMapState;
import interface_adapter.create_MindMap.MindMapViewModel;
import interface_adapter.loading.LoadingController;

/**
 * The View for the Loading Use Case.
 */
public class MindMapLoadingView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "loading";
    private final MindMapViewModel mindMapViewModel;

    private final JTextField idInputField = new JTextField(15);
    private final JLabel idErrorLabel = new JLabel();
    private final JButton loadMindMapButton;
    private final JButton cancelButton;
    private final ViewModel viewManagerModel;
    private final CardLayout cardLayout;
    private final Container cardPanel;
    private MindMapController mindMapController;
    private LoadingController loadingController;

    public MindMapLoadingView(MindMapViewModel mindMapViewModel,
                              ViewManagerModel viewManagerModel,
                              JPanel cardPanel,
                              CardLayout cardLayout) {
        this.mindMapViewModel = mindMapViewModel;
        this.mindMapViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        // Main layout setup
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Center panel setup with gray background around all elements
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.LIGHT_GRAY);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLUE, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Title label setup
        final JLabel title = new JLabel("Loading Screen");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(title);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacer below title

        // ID input panel setup
        final JPanel idInputPanel = new JPanel(new GridBagLayout());
        idInputPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        final JLabel idLabel = new JLabel("Enter Mind Map ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        idInputPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        idInputField.setPreferredSize(new Dimension(150, 25));
        idInputField.setMaximumSize(new Dimension(150, 25));
        idInputPanel.add(idInputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        idErrorLabel.setForeground(Color.RED);
        idInputPanel.add(idErrorLabel, gbc);

        centerPanel.add(idInputPanel);

        // Adding spacing below the ID input section
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Buttons panel setup
        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(Color.LIGHT_GRAY);

        final Dimension buttonSize = new Dimension(200, 30);

        // Load Mind Map button
        loadMindMapButton = new JButton("Load Mind Map");
        loadMindMapButton.setPreferredSize(buttonSize);
        loadMindMapButton.setMaximumSize(buttonSize);
        loadMindMapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.add(loadMindMapButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer between buttons

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(buttonSize);
        cancelButton.setMaximumSize(buttonSize);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.add(cancelButton);

        centerPanel.add(buttonsPanel); // Add buttons section to center panel

        // Add main center panel to the main layout
        this.add(centerPanel, BorderLayout.CENTER);

        loadMindMapButton.addActionListener(evt -> {
            JOptionPane.showMessageDialog(
                    MindMapLoadingView.this,
                    "Attempting to load Mind Map with ID: " + idInputField.getText()
            );
        });

        cancelButton.addActionListener(evt -> {
            JOptionPane.showMessageDialog(
                    MindMapLoadingView.this, "Loading canceled, returning to Mindmap Creation page"
            );
            mindMapViewModel.setState(new MindMapState(""));
            cardLayout.show(cardPanel, "CreateNewMindMapView");
        });

        // Document listener for ID input field
        idInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                final MindMapState currentState = mindMapViewModel.getState();
                currentState.setName(idInputField.getText());
                mindMapViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateState();
            }
        }
        );
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final MindMapState state = (MindMapState) evt.getNewValue();
        setFields(state);
        idErrorLabel.setText(state.getNameError());
    }

    private void setFields(MindMapState state) {
        idInputField.setText(state.getName());
    }

    public String getViewName() {
        return viewName;
    }

    public void setMindMapController(MindMapController mindMapController) {
        this.mindMapController = mindMapController;
    }

    public void setLoginController(LoadingController loadingController) {
        this.loadingController = loadingController;
    }
}
