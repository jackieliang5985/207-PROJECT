package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.create_MindMap.MindMapController;
import interface_adapter.create_MindMap.MindMapState;
import interface_adapter.create_MindMap.MindMapViewModel;

/**
 * The View for the Signup Use Case.
 */
public class CreateNewMindMapView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "create new mindmap";

    private final MindMapViewModel mindMapViewModel;
    private final JTextField nameInputField = new JTextField(15);
    private final JPasswordField descriptionInputField = new JPasswordField(15);
    private MindMapController mindMapController;

    private final JButton toCreate;
    private final JButton cancel;
    private final JButton toLoad;

    public CreateNewMindMapView(MindMapViewModel mindMapViewModel) {
        this.mindMapViewModel = mindMapViewModel;
        mindMapViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(MindMapViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(MindMapViewModel.NAME_LABEL), nameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(MindMapViewModel.DESCRIPTION_LABEL), descriptionInputField);

        final JPanel buttons = new JPanel();
        toLoad = new JButton(MindMapViewModel.TO_LOAD_BUTTON_LABEL);
        buttons.add(toLoad);
        toCreate = new JButton(MindMapViewModel.CREATE_BUTTON_LABEL);
        buttons.add(toCreate);
        cancel = new JButton(MindMapViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        toCreate.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(toCreate)) {
                            final MindMapState currentState = mindMapViewModel.getState();

                            mindMapController.execute(
                                    currentState.getName(),
                                    currentState.getDescription()
                            );
                        }
                    }
                }
        );

        toLoad.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        mindMapController.switchToLoginView();
                    }
                }
        );

        cancel.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        JOptionPane.showMessageDialog(CreateNewMindMapView.this,
                                "Creation canceled. Closing program...");
                        System.exit(0);
                    }
                }
        );

        addNameListener();
        addDescriptionListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
        // this.add(repeatPasswordInfo);
        this.add(buttons);
    }

    private void addNameListener() {
        nameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final MindMapState currentState = mindMapViewModel.getState();
                currentState.setName(nameInputField.getText());
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
                currentState.setDescription(new String(descriptionInputField.getPassword()));
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
//
//    private void addRepeatPasswordListener() {
//        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {
//
//            private void documentListenerHelper() {
//                final SignupState currentState = signupViewModel.getState();
//                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
//                signupViewModel.setState(currentState);
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//        });
//    }

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
        return viewName;
    }

    public void setSignupController(MindMapController controller) {
        this.mindMapController = controller;
    }
}
