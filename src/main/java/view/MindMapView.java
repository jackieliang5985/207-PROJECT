package view;

import javax.swing.*;
import java.awt.*;

/**
 * A view class for the Detective Board screen.
 */
public class MindMapView extends JPanel {
    public static final String VIEW_NAME = "MINDMAP";

    public MindMapView() {
        setLayout(new BorderLayout());

        // Header label
        JLabel titleLabel = new JLabel("Mind Map", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.NORTH);

        // Main center panel for the board area
        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.LIGHT_GRAY);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        boardPanel.setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0));
        bottomPanel.setBackground(new Color(230, 230, 250)); // Light purple background to match the photo

        // Creating buttons with light purple background
        JButton addTextPostButton = createStyledButton("Add Text Post It");
        JButton addImageButton = createStyledButton("Add Image Post It");
        JButton attachStringButton = createStyledButton("Attach String");
        JButton saveButton = createStyledButton("SAVE");
        JButton logoutButton = createStyledButton("LOGOUT");

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
        JButton button = new JButton(text);
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
