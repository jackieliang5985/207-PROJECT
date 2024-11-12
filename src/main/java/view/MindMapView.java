package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A view class for the Detective Board screen.
 */
public class MindMapView extends JPanel {
    // Define a constant for identifying this view in the CardLayout
    public static final String VIEW_NAME = "DetectiveBoardView";
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Constructor that accepts the main panel and CardLayout as parameters
    public MindMapView(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        setLayout(new BorderLayout());

        // Header label (Detective Board title)
        JLabel titleLabel = new JLabel("Detective Board", JLabel.CENTER);
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

        // Adding ActionListener to the logout button
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the login/signup screen
                cardLayout.show(mainPanel, "log in"); // Assume "LoginView" is the name of your login screen
            }
        });

        // Adding other button actions (currently placeholders)
        addTextPostButton.addActionListener(e -> System.out.println("Add Text Post clicked"));
        addImageButton.addActionListener(e -> System.out.println("Add Image Post clicked"));
        attachStringButton.addActionListener(e -> System.out.println("Attach String clicked"));
        saveButton.addActionListener(e -> System.out.println("Save clicked"));

        // Adding buttons to the bottom panel
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
        return "DetectiveBoardView";
    }
}
