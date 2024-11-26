package view;

import interface_adapter.add_Image_PostNote.ImagePostNoteController;
import interface_adapter.add_Image_PostNote.ImagePostNoteData;
import interface_adapter.add_Image_PostNote.ImagePostNoteViewModel;
import interface_adapter.create_MindMap.SquarePanel;
import interface_adapter.export_mind_map.ExportController;
import interface_adapter.image.ImageController;
import interface_adapter.image.ImagePresenter;
import interface_adapter.image.ImageViewModel;
import use_case.export_mind_map.ExportInputData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MindMapView extends JPanel {
    public static final String VIEW_NAME = "MINDMAP";
    private final CardLayout cardLayout;
    private final Container cardPanel;
    private final SquarePanel boardPanel;
    private final ImageViewModel imageViewModel;
    private final ExportController exportController;
    private final ImageController imageController;
    private final ImagePostNoteController imagePostNoteController;
    private final ImagePostNoteViewModel imagePostNoteViewModel;

    public MindMapView(CardLayout cardLayout, Container cardPanel,
                       ImageController imageController,
                       ImageViewModel imageViewModel,
                       ImagePostNoteViewModel imagePostNoteViewModel,
                       ExportController exportController,
                       ImagePostNoteController imagePostNoteController) {

        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.imageController = imageController;
        this.imageViewModel = imageViewModel;
        this.imagePostNoteViewModel = imagePostNoteViewModel;  // Use both view models
        this.exportController = exportController;
        this.imagePostNoteController = imagePostNoteController;

        // Initialize SquarePanel with an empty list of post notes
        this.boardPanel = new SquarePanel(new ArrayList<>());
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1920, 1080));

        // Title Label for Mind Map
        final JLabel titleLabel = new JLabel("Mind Map", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.NORTH);

        // Board Panel
        boardPanel.setBackground(Color.LIGHT_GRAY);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        boardPanel.setLayout(null); // Free placement layout
        add(boardPanel, BorderLayout.CENTER);

        // Bottom Panel with buttons
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0));
        bottomPanel.setBackground(new Color(230, 230, 250));

        // Create buttons
        final JButton addTextPostButton = createStyledButton("Add Text Post It");
        final JButton addImageButton = createStyledButton("Add Image Post It");
        final JButton saveButton = createStyledButton("SAVE");
        final JButton logoutButton = createStyledButton("LOGOUT");

        // Add button listeners
        addTextPostButton.addActionListener(evt -> boardPanel.createPostNote());
        addImageButton.addActionListener(evt -> fetchAndAddImage());
        saveButton.addActionListener(evt -> saveMindMap());
        logoutButton.addActionListener(evt -> logout());

        // Add buttons to the panel
        bottomPanel.add(addTextPostButton);
        bottomPanel.add(addImageButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Listen for property changes from the ImageViewModel
        imageViewModel.addPropertyChangeListener(evt -> {
            if ("images".equals(evt.getPropertyName())) {
                List<ImageViewModel.ImageDisplayData> images = (List<ImageViewModel.ImageDisplayData>) evt.getNewValue();
                showImageSelectionDialog(images);
            } else if ("errorMessage".equals(evt.getPropertyName())) {
                JOptionPane.showMessageDialog(this, imageViewModel.getErrorMessage());
            }
        });
    }

    private JButton createStyledButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(230, 230, 250));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return button;
    }

    private void fetchAndAddImage() {
        final String query = JOptionPane.showInputDialog(this, "Enter a search term for images:");
        if (query == null || query.isEmpty()) {
            return; // User canceled
        }

        // Fetch images using the controller
        final ImagePresenter imagePresenter = new ImagePresenter(imageViewModel);
        imageController.fetchImages(query, imagePresenter);
    }

    private void showImageSelectionDialog(List<ImageViewModel.ImageDisplayData> imageDisplayDataList) {
        final JDialog dialog = new JDialog((Frame) null, "Select an Image", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());

        final JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(0, 3, 10, 10)); // Layout with 3 columns

        for (ImageViewModel.ImageDisplayData imageData : imageDisplayDataList) {
            try {
                // Fetch the image
                URL imageUrl = new URL(imageData.getUrl());
                Image image = ImageIO.read(imageUrl);  // Read the image using ImageIO

                // Resize the image to a smaller size (e.g., 100x100)
                Image resizedImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                // Create an ImageIcon from the resized image
                ImageIcon icon = new ImageIcon(resizedImage);

                // Create a button with the resized image icon
                final JButton imageButton = new JButton(icon);
                imageButton.addActionListener(evt -> {
                    dialog.dispose();  // Close the dialog
                    // Create ImagePostNoteViewModel and pass it to the controller
                    imagePostNoteViewModel.setImageUrl(imageData.getUrl());
                    imagePostNoteViewModel.setX(50);  // Default X position
                    imagePostNoteViewModel.setY(50);  // Default Y position
                    imagePostNoteViewModel.setColor(Color.ORANGE);
                    imagePostNoteController.addImagePostNote(imagePostNoteViewModel);
                });
                imagePanel.add(imageButton);
            } catch (Exception e) {
                e.printStackTrace();  // Handle any exceptions
            }
        }

        // Add the image panel to a scrollable pane
        final JScrollPane scrollPane = new JScrollPane(imagePanel);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setSize(600, 400);  // Set the dialog size
        dialog.setLocationRelativeTo(this);  // Center the dialog
        dialog.setVisible(true);
    }

    public void updatePostNotes(ImagePostNoteViewModel viewModel) {
        try {
            // Load the image from the URL using ImageIO
            URL imageUrl = new URL(viewModel.getImageUrl());
            Image image = ImageIO.read(imageUrl);  // Use ImageIO to read the image

            // Check if image is loaded successfully
            if (image != null) {
                viewModel.setWidth(image.getWidth(null));  // Set width
                viewModel.setHeight(image.getHeight(null));  // Set height
                System.out.println("Image loaded successfully: " + viewModel.getImageUrl());
            } else {
                System.out.println("Error: Image failed to load.");
                return;  // Exit if image loading fails
            }

            // Create the post-it note panel (use the calculated width and height)
            JPanel postItNotePanel = new JPanel();
            postItNotePanel.setLayout(new BorderLayout());
            postItNotePanel.setBounds(viewModel.getX(), viewModel.getY(), viewModel.getWidth()+10, viewModel.getHeight()+10);
            postItNotePanel.setBackground(viewModel.getColor());
            postItNotePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Create an ImageIcon from the Image and add it to the post-it note panel
            ImageIcon imageIcon = new ImageIcon(image);  // Use Image loaded by ImageIO
            JLabel imageLabel = new JLabel(imageIcon);
            postItNotePanel.add(imageLabel, BorderLayout.CENTER);

            // Add the post-it note panel to the board
            boardPanel.add(postItNotePanel);

            // Revalidate and repaint to ensure the board updates
            boardPanel.revalidate();
            boardPanel.repaint();

            System.out.println("Image added to post-it note at position: (" + viewModel.getX() + ", " + viewModel.getY() + ")");
        } catch (Exception e) {
            System.out.println("Error: Failed to load image from URL: " + viewModel.getImageUrl());
            e.printStackTrace();
        }
    }

    private void saveMindMap() {
        try {
            List<String> supportedFormats = Arrays.asList("png", "jpg", "pdf");
            String dialogTitle = "Save Mind Map";
            ExportInputData inputData = new ExportInputData(boardPanel, dialogTitle, supportedFormats);
            exportController.handleExportCommand(boardPanel, dialogTitle);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving Mind Map: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "You have been logged out. Returning to the Login page");
        cardLayout.show(cardPanel, "CreateNewMindMapView");
    }
}
