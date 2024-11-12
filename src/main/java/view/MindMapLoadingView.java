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
        final MindMapView createNewMindMapView = new MindMapView();
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        final JLabel title = new JLabel("Loading Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel idInfo = new LabelTextPanel(
                new JLabel("Enter Mind Map ID:"), idInputField);

        final JPanel buttons = new JPanel();
        loadMindMapButton = new JButton("Load Mind Map");
        buttons.add(loadMindMapButton);
        cancelButton = new JButton("Cancel");
        buttons.add(cancelButton);

        loadMindMapButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(loadMindMapButton)) {
                            // Placeholder action, actual loading logic to be implemented later
                            JOptionPane.showMessageDialog(
                                    MindMapLoadingView.this,
                                    "Attempting to load Mind Map with ID: " + idInputField.getText()
                            );
                        }
                    }
                }
        );

        cancelButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        JOptionPane.showMessageDialog(MindMapLoadingView.this, "Loading canceled.");
                        final String createNewMindMapViewName = "create new mindmap";
                        JOptionPane.showMessageDialog(
                                MindMapLoadingView.this, "Loading canceled,"
                                        + " returning to Mindmap Creation page"
                        );
                        cardLayout.show(cardPanel, createNewMindMapViewName);
                    }
                }
        );

        idInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final MindMapState currentState = mindMapViewModel.getState();
                currentState.setName(idInputField.getText());
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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(idInfo);
        this.add(idErrorLabel);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
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
