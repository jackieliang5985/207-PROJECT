package view;

import entity.Image;
import interface_adapter.UnsplashImageRepository;
import use_case.image.FetchImagesUseCase;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import io.github.cdimascio.dotenv.Dotenv;


public class MindMapView extends JPanel {
    public static final String VIEW_NAME = "MINDMAP";

    private final CardLayout cardLayout;
    private final Container cardPanel;
    private final JPanel boardPanel;
    Dotenv dotenv = Dotenv.configure()
            .directory(".") // Directory of the .env file (default is root)
            .load();

    private final String unsplashApiKey = dotenv.get("UNSPLASH_API_KEY");

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
        boardPanel = new JPanel();
        boardPanel.setBackground(Color.LIGHT_GRAY);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        boardPanel.setLayout(null);
        // Allow absolute positioning for draggable components
        add(boardPanel, BorderLayout.CENTER);

        // Bottom panel for buttons
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0));
        bottomPanel.setBackground(new Color(230, 230, 250));

        final JButton addTextPostButton = createStyledButton("Add Text Post It");
        final JButton addImageButton = createStyledButton("Add Image Post It");
        final JButton attachStringButton = createStyledButton("Attach String");
        final JButton saveButton = createStyledButton("SAVE");
        final JButton logoutButton = createStyledButton("LOGOUT");

        // Add action listener for "Add Image Post It"
        addImageButton.addActionListener(evt -> fetchAndAddImage());

        // Adding action listener to the logout button
        logoutButton.addActionListener(evt -> {
            JOptionPane.showMessageDialog(
                    MindMapView.this,
                    "You have been logged out. Returning to the Login page"
            );
            cardLayout.show(cardPanel, "CreateNewMindMapView");
        });

        bottomPanel.add(addTextPostButton);
        bottomPanel.add(addImageButton);
        bottomPanel.add(attachStringButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Helper method to create styled buttons.
     */
    private JButton createStyledButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(230, 230, 250));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return button;
    }

    /**
     * Fetch images from Unsplash and add one to the board.
     */
    private void fetchAndAddImage() {
        System.out.println("Unsplash API Key: " + unsplashApiKey);
        String query = JOptionPane.showInputDialog(this, "Enter a search term for images:");
        if (query == null || query.isEmpty()) {
            // User canceled or provided empty input
            return;
        }

        try {
            // Use UnsplashImageRepository to fetch images
            UnsplashImageRepository repository = new UnsplashImageRepository(unsplashApiKey);
            FetchImagesUseCase fetchImagesUseCase = new FetchImagesUseCase(repository);

            List<Image> images = fetchImagesUseCase.fetchImages(query);
            if (images.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No images found for the query: " + query);
                return;
            }

            // Show images in a dialog for selection
            Image selectedImage = showImageSelectionDialog(images);
            if (selectedImage != null) {
                addImageToBoard(selectedImage);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching images: " + e.getMessage());
        }
    }

    /**
     * Display images in a dialog for selection.
     */
    private Image showImageSelectionDialog(List<Image> images) {
        final JDialog dialog = new JDialog((Frame) null, "Select an Image", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());

        // Create a panel to hold the image buttons
        final JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(0, 3, 10, 10)); // 3 images per row with spacing

        final Image[] selectedImage = {null};
        // Icons for buttons
        for (Image image : images) {
            try {
                System.out.println("Fetching image: " + image.getUrl());
                BufferedImage bufferedImage = ImageIO.read(new URL(image.getUrl()));
                if (bufferedImage == null) {
                    System.err.println("Failed to fetch image: " + image.getUrl());
                    continue;
                }
                final ImageIcon icon = new ImageIcon(bufferedImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
                final JButton imageButton = new JButton(icon);
                imageButton.addActionListener(evt -> {
                    selectedImage[0] = image;
                    dialog.dispose();
                });
                imagePanel.add(imageButton);
            }
            catch (IOException e) {
                System.err.println("Error loading image: " + image.getUrl());
                e.printStackTrace();
            }
        }

        // Scrolling
        final JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        dialog.add(scrollPane, BorderLayout.CENTER);
        // Set an appropriate size for the dialog
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        return selectedImage[0];
    }

    /**
     * Add the selected image to the board as a draggable component.
     */
    private void addImageToBoard(Image image) {
        System.out.println("Adding image: " + image.getUrl());  // Debug the image URL
        final ImageIcon icon = new ImageIcon(new ImageIcon(image.getUrl()).getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
        final JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(50, 50, 150, 150); // Example positioning
        boardPanel.add(imageLabel);
        boardPanel.revalidate();
        boardPanel.repaint();
    }
}
