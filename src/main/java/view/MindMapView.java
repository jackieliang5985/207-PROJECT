package view;

import interface_adapter.add_Image_PostNote.ImagePostNoteController;
import interface_adapter.add_Image_PostNote.ImagePostNoteViewModel;
import interface_adapter.image.ImageController;
import interface_adapter.image.ImagePresenter;
import interface_adapter.image.ImageViewModel;
import interface_adapter.export_mind_map.ExportController;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MindMapView extends JPanel {
    public static final String VIEW_NAME = "MINDMAP";

    private final CardLayout cardLayout;
    private final Container cardPanel;
    private final ImageViewModel imageViewModel;
    private final ExportController exportController;
    private final ImageController imageController;
    private final ImagePostNoteController imagePostNoteController;
    private final ImagePostNoteViewModel imagePostNoteViewModel;
    private List<ImagePostNoteViewModel> postNotes;  // List of post-notes (image post notes)

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
        this.exportController = exportController;
        this.imagePostNoteController = imagePostNoteController;
        this.imagePostNoteViewModel = imagePostNoteViewModel;

        // Initialize postNotes as an empty list
        this.postNotes = new ArrayList<>();

        setupUI();
    }

    private void setupUI() {
        setLayout(null);  // Use absolute positioning for free placement
        setPreferredSize(new Dimension(1920, 1080));

        // Title Label for Mind Map
        final JLabel titleLabel = new JLabel("Mind Map", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, 0, getWidth(), 50);  // Ensure the title label is visible
        add(titleLabel);

        // Set up right-click context menu
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem addImageMenuItem = new JMenuItem("Add Image Post It");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem logoutMenuItem = new JMenuItem("Logout");

        // Add action listeners for each item
        addImageMenuItem.addActionListener(e -> fetchAndAddImage());
        saveMenuItem.addActionListener(e -> saveMindMap());
        logoutMenuItem.addActionListener(e -> logout());

        // Add the menu items to the popup menu
        popupMenu.add(addImageMenuItem);
        popupMenu.add(saveMenuItem);
        popupMenu.add(logoutMenuItem);

        // Add mouse listener to show the popup menu on right-click
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    System.out.println("Right-click detected, showing popup menu...");
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    System.out.println("Right-click detected, showing popup menu...");
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        // Revalidate and repaint the panel after adding components
        revalidate();
        repaint();

        System.out.println("UI setup completed.");

        // Listen for property changes from the ImageViewModel
        imageViewModel.addPropertyChangeListener(evt -> {
            if ("images".equals(evt.getPropertyName())) {
                List<ImageViewModel.ImageDisplayData> images = (List<ImageViewModel.ImageDisplayData>) evt.getNewValue();
                System.out.println("Images received: " + images.size());
                showImageSelectionDialog(images);
            } else if ("errorMessage".equals(evt.getPropertyName())) {
                System.out.println("Error message: " + imageViewModel.getErrorMessage());
                JOptionPane.showMessageDialog(this, imageViewModel.getErrorMessage());
            }
        });
    }

    // Method to fetch images based on a query
    private void fetchAndAddImage() {
        System.out.println("Fetching images based on query...");
        final String query = JOptionPane.showInputDialog(this, "Enter a search term for images:");
        if (query == null || query.isEmpty()) {
            System.out.println("User canceled the query.");
            return; // User canceled
        }

        System.out.println("Searching for images with query: " + query);

        // Create an instance of ImagePresenter
        ImagePresenter imagePresenter = new ImagePresenter(imageViewModel);

        // Fetch images using the controller and pass both the query and the imagePresenter
        imageController.fetchImages(query, imagePresenter);
    }

    // Method to show the image selection dialog with the fetched images
    private void showImageSelectionDialog(List<ImageViewModel.ImageDisplayData> imageDisplayDataList) {
        final JDialog dialog = new JDialog((Frame) null, "Select an Image", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());

        final JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(0, 3, 10, 10)); // Layout with 3 columns

        for (ImageViewModel.ImageDisplayData imageData : imageDisplayDataList) {
            try {
                // Fetch the image from the URL
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

                    // Set the dimensions of the image post note view model
                    imagePostNoteViewModel.setImageUrl(imageData.getUrl());
                    imagePostNoteViewModel.setX(50);  // Default X position
                    imagePostNoteViewModel.setY(50);  // Default Y position
                    imagePostNoteViewModel.setColor(Color.ORANGE);
                    imagePostNoteViewModel.setWidth(resizedImage.getWidth(null));  // Set width based on resized image
                    imagePostNoteViewModel.setHeight(resizedImage.getHeight(null));  // Set height based on resized image

                    // Add image post note via the controller
                    imagePostNoteController.addImagePostNote(imagePostNoteViewModel.getImageUrl(), imagePostNoteViewModel.getX(),
                            imagePostNoteViewModel.getY(), imagePostNoteViewModel.getWidth(), imagePostNoteViewModel.getHeight(),
                            imagePostNoteViewModel.getColor());

                    // Once the post note is added, update the MindMapView
                    updatePostNotes(imagePostNoteViewModel);  // This will repaint the board with the new image
                });
                imagePanel.add(imageButton);
            } catch (Exception e) {
                e.printStackTrace();  // Handle any exceptions
            }
        }

        final JScrollPane scrollPane = new JScrollPane(imagePanel);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setSize(600, 400);  // Set the dialog size
        dialog.setLocationRelativeTo(this);  // Center the dialog
        dialog.setVisible(true);
    }


    // Method to update and display post notes on the board
    public void updatePostNotes(ImagePostNoteViewModel postNoteViewModel) {
        System.out.println("Updating post notes with new note: " + postNoteViewModel.getImageUrl());
        this.postNotes.add(postNoteViewModel);  // Add the new post note to the list
        System.out.println("Total post notes: " + postNotes.size());  // Debugging line to print number of post notes
        revalidate();  // Revalidate the component to ensure the layout is updated
        repaint();  // Repaint the panel to render the new post notes
    }

    // Method to save the MindMap
    private void saveMindMap() {
        try {
            // Export logic using exportController
            exportController.handleExportCommand(this, "MindMap");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving Mind Map: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "You have been logged out. Returning to the Login page");
        cardLayout.show(cardPanel, "CreateNewMindMapView");
    }

    // Overridden paintComponent to render the post-notes on the MindMap
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Ensures the panel is cleared before painting

        if (postNotes != null && !postNotes.isEmpty()) {
            for (ImagePostNoteViewModel postNote : postNotes) {
                // If postNote has an image, adjust the post-it note size to match the image's size + padding
                if (postNote.getImageUrl() != null && !postNote.getImageUrl().isEmpty()) {
                    try {
                        // Load the image from the URL
                        Image image = ImageIO.read(new URL(postNote.getImageUrl()));

                        // Get the original image dimensions
                        int imageWidth = image.getWidth(null);
                        int imageHeight = image.getHeight(null);

                        // Increase the post-it note size by 10 pixels on each side of the image
                        int postNoteWidth = imageWidth + 20;  // +10 on each side
                        int postNoteHeight = imageHeight + 20;  // +10 on each side

                        // Calculate the x and y offsets to center the image within the post-it note
                        int xOffset = (postNoteWidth - imageWidth) / 2;
                        int yOffset = (postNoteHeight - imageHeight) / 2;

                        // Resize the image
                        Image resizedImage = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);

                        // Draw the post-it note (a rectangle with border)
                        g.setColor(postNote.getColor());  // Set the post-it note color
                        g.fillRect(postNote.getX(), postNote.getY(), postNoteWidth, postNoteHeight);  // Adjusted width and height

                        // Draw the border around the post-it note
                        g.setColor(Color.BLACK);  // Border color
                        g.drawRect(postNote.getX(), postNote.getY(), postNoteWidth, postNoteHeight);  // Draw border

                        // Draw the resized image in the center of the post-it note
                        g.drawImage(resizedImage, postNote.getX() + xOffset, postNote.getY() + yOffset, this);
                    } catch (Exception e) {
                        e.printStackTrace();  // Handle image loading errors
                    }
                }
            }
        }
    }

}

