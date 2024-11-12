package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A view class for the Detective Board screen.
 */
public class MindMapView extends JPanel {
    public static final String VIEW_NAME = "MINDMAP";

    private final CardLayout cardLayout;
    private final Container cardPanel;

    public MindMapView(CardLayout cardLayout, Container cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        // Header label
        final JLabel titleLabel = new JLabel("Mind Map", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.NORTH);

        // Main center panel for the board area
        final JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.LIGHT_GRAY);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        boardPanel.setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);

        // Bottom panel for buttons
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0));
        bottomPanel.setBackground(new Color(230, 230, 250)); // Light purple background to match the photo

        // Creating buttons with light purple background
        final JButton addTextPostButton = createStyledButton("Add Text Post It");
        final JButton addImageButton = createStyledButton("Add Image Post It");
        final JButton attachStringButton = createStyledButton("Attach String");
        final JButton saveButton = createStyledButton("SAVE");
        final JButton logoutButton = createStyledButton("LOGOUT");

        // Adding action listener to the logout button
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Show a message indicating the logout action
                JOptionPane.showMessageDialog(
                        MindMapView.this,
                        "You have been logged out."
                );

                // Define the target view name for redirection
                final String loginViewName = "create new mindmap";

                // Show another message indicating navigation to the login screen
                JOptionPane.showMessageDialog(
                        MindMapView.this,
                        "Returning to the Login page"
                );

                // Navigate to the "login" view
                cardLayout.show(cardPanel, loginViewName);
            }
        });

        // Adding buttons
        bottomPanel.add(addTextPostButton);
        bottomPanel.add(addImageButton);
        bottomPanel.add(attachStringButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(logoutButton);

        // Adding bottom panel to the main panel
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Helper method to create styled buttons.
     */
    private JButton createStyledButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(230, 230, 250)); // Light purple color
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return button;
    }

    public String getViewName() {
        return "MindMapView";
    }
}
