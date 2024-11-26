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
        imagePanel.setLayout(new GridLayout(0, 3, 10, 10));

        for (ImageViewModel.ImageDisplayData imageData : imageDisplayDataList) {
            try {
                // Create ImagePostNoteViewModel from the image data
                imagePostNoteViewModel.setImageUrl(imageData.getUrl());
                imagePostNoteViewModel.setX(50); // Default X position
                imagePostNoteViewModel.setY(50); // Default Y position
                imagePostNoteViewModel.setColor(Color.ORANGE);

                // Add a button with the image
                ImageIcon icon = new ImageIcon(new URL(imageData.getUrl()));
                final JButton imageButton = new JButton(icon);
                imageButton.addActionListener(evt -> {
                    dialog.dispose();
                    System.out.println("Passing image URL to controller: " + imagePostNoteViewModel.getImageUrl()); // Debugging URL
                    imagePostNoteController.addImagePostNote(imagePostNoteViewModel);  // Controller should update MindMap
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

    public void updatePostNotes(ImagePostNoteViewModel viewModel) {
        // Load the image from the URL
        ImageIcon imageIcon = new ImageIcon(viewModel.getImageUrl());

        // Create a panel to represent the post-it note
        JPanel postItNotePanel = new JPanel();
        postItNotePanel.setLayout(new BorderLayout());

        // Ensure the panel size matches the image size
        postItNotePanel.setBounds(viewModel.getX(), viewModel.getY(), viewModel.getWidth(), viewModel.getHeight());
        postItNotePanel.setBackground(viewModel.getColor());

        // Create a label for the image and add it to the post-it note panel
        JLabel imageLabel = new JLabel(imageIcon);

        // Add the image to the post-it note panel
        postItNotePanel.add(imageLabel, BorderLayout.CENTER);

        // Add the post-it note panel to the board
        boardPanel.add(postItNotePanel);

        // Revalidate and repaint to update the board
        boardPanel.revalidate();
        boardPanel.repaint();

        System.out.println("Adding image with URL: " + viewModel.getImageUrl());
        System.out.println("Image position: (" + viewModel.getX() + ", " + viewModel.getY() + ")");
        System.out.println("Image size: " + viewModel.getWidth() + "x" + viewModel.getHeight());
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
