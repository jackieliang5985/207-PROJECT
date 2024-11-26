package view;

import entity.ImagePostNote;
import entity.PostNote;
import interface_adapter.create_MindMap.SquarePanel;
import interface_adapter.export_mind_map.ExportController;
import interface_adapter.image.ImageController;
import interface_adapter.image.ImagePresenter;
import interface_adapter.image.ImageViewModel;
import use_case.export_mind_map.ExportInputData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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

    private final ArrayList<PostNote> postNotes = new ArrayList<>();  // Declare postNotes

    private final ImageController imageController;  // Declare ImageController

    public MindMapView(CardLayout cardLayout, Container cardPanel, ImageController imageController,
                       ImageViewModel imageViewModel, ExportController exportController) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.imageViewModel = imageViewModel;
        this.exportController = exportController;
        this.imageController = imageController;  // Initialize ImageController

        // Initialize SquarePanel with postNotes
        this.boardPanel = new SquarePanel(postNotes);

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

        // Board Panel (Main area for the mind map)
        boardPanel.setBackground(Color.LIGHT_GRAY);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        boardPanel.setLayout(null);  // Layout for free placement
        add(boardPanel, BorderLayout.CENTER);

        // Bottom Panel for buttons (Save, Logout, etc.)
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0));
        bottomPanel.setBackground(new Color(230, 230, 250));

        final JButton addTextPostButton = createStyledButton("Add Text Post It");
        final JButton addImageButton = createStyledButton("Add Image Post It");
        final JButton attachStringButton = createStyledButton("Attach String");
        final JButton saveButton = createStyledButton("SAVE");
        final JButton logoutButton = createStyledButton("LOGOUT");

        // Add action listener for buttons
        addTextPostButton.addActionListener(evt -> boardPanel.createPostNote());
        addImageButton.addActionListener(evt -> fetchAndAddImage());
        saveButton.addActionListener(evt -> saveMindMap());
        logoutButton.addActionListener(evt -> logout());

        // Add buttons to the bottom panel
        bottomPanel.add(addTextPostButton);
        bottomPanel.add(addImageButton);
        bottomPanel.add(attachStringButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Listen for property changes from the ImageViewModel
        imageViewModel.addPropertyChangeListener(evt -> {
            if ("images".equals(evt.getPropertyName())) {
                final List<ImageViewModel.ImageDisplayData> images = (List<ImageViewModel.ImageDisplayData>) evt.getNewValue();
                showImageSelectionDialog(images); // Display the images in the dialog
            } else if ("errorMessage".equals(evt.getPropertyName())) {
                JOptionPane.showMessageDialog(this, imageViewModel.getErrorMessage());
            }
        });
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(230, 230, 250));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return button;
    }

    // Method for fetching and adding an image
    private void fetchAndAddImage() {
        final String query = JOptionPane.showInputDialog(this, "Enter a search term for images:");
        if (query == null || query.isEmpty()) {
            return; // User canceled
        }
        final ImagePresenter imagePresenter = new ImagePresenter(imageViewModel);
        imageController.fetchImages(query, imagePresenter);
    }

    // Method for showing the image selection dialog
    private void showImageSelectionDialog(List<ImageViewModel.ImageDisplayData> imageDisplayDataList) {
        final JDialog dialog = new JDialog((Frame) null, "Select an Image", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());

        final JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(0, 3, 10, 10)); // GridLayout with 3 columns

        final ImageViewModel.ImageDisplayData[] selectedImage = {null};
        for (ImageViewModel.ImageDisplayData imageData : imageDisplayDataList) {
            try {
                // Fetch the image
                BufferedImage bufferedImage = ImageIO.read(new URL(imageData.getUrl()));

                // Resize the image to a smaller size (e.g., 100x100)
                Image resizedImage = bufferedImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                // Create the ImageIcon from the resized image
                ImageIcon icon = new ImageIcon(resizedImage);

                // Add the image icon to a button
                final JButton imageButton = new JButton(icon);
                imageButton.addActionListener(evt -> {
                    selectedImage[0] = imageData;
                    dialog.dispose();
                    addImageToBoard(selectedImage[0]); // Add the selected image to the board
                });
                imagePanel.add(imageButton);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        final JScrollPane scrollPane = new JScrollPane(imagePanel);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Method for adding selected image to the board
    private void addImageToBoard(ImageViewModel.ImageDisplayData imageData) {
        try {
            ImageIcon imageIcon = new ImageIcon(new URL(imageData.getUrl()));
            ImagePostNote imagePostNote = new ImagePostNote(50, 50, Color.ORANGE, boardPanel);
            imagePostNote.setImage(imageIcon);

            // Add the ImagePostNote to the board (SquarePanel)
            boardPanel.createPostNote(imagePostNote);

            boardPanel.revalidate();
            boardPanel.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading the image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method for saving the MindMap
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

    // Method for handling the logout functionality
    private void logout() {
        JOptionPane.showMessageDialog(this, "You have been logged out. Returning to the Login page");
        cardLayout.show(cardPanel, "CreateNewMindMapView");
    }
}
