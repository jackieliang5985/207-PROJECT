package view;

import interface_adapter.add_Image_PostNote.ImagePostNoteController;
import interface_adapter.add_Image_PostNote.ImagePostNoteViewModel;
import interface_adapter.add_Text_PostNote.TextPostNoteController;
import interface_adapter.add_Text_PostNote.TextPostNoteViewModel;
import interface_adapter.image.ImageController;
import interface_adapter.image.ImagePresenter;
import interface_adapter.image.ImageViewModel;
import interface_adapter.export_mind_map.ExportController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private final TextPostNoteController textPostNoteController;
    private final ImagePostNoteViewModel imagePostNoteViewModel;
    private final TextPostNoteViewModel textPostNoteViewModel;

    private List<ImagePostNoteViewModel> imagePostNotes;  // List to store all image post-notes
    private List<TextPostNoteViewModel> textPostNotes;  // List to store all text post-notes

    private ImagePostNoteViewModel draggedImagePostNote; // Currently dragged image post note
    private TextPostNoteViewModel draggedTextPostNote;  // Currently dragged text post note
    private Point dragOffset; // Offset for dragging position

    public MindMapView(CardLayout cardLayout, Container cardPanel,
                       ImageController imageController,
                       ImageViewModel imageViewModel,
                       ImagePostNoteViewModel imagePostNoteViewModel,
                       TextPostNoteViewModel textPostNoteViewModel,
                       ExportController exportController,
                       ImagePostNoteController imagePostNoteController,
                       TextPostNoteController textPostNoteController) {

        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.imageController = imageController;
        this.imageViewModel = imageViewModel;
        this.exportController = exportController;
        this.imagePostNoteController = imagePostNoteController;
        this.textPostNoteController = textPostNoteController;
        this.imagePostNoteViewModel = imagePostNoteViewModel;
        this.textPostNoteViewModel = textPostNoteViewModel;

        // Initialize postNotes as an empty list
        this.imagePostNotes = new ArrayList<>();
        this.textPostNotes = new ArrayList<>();

        setupUI();
        setupDragFunctionality(); // Add drag functionality
    }

    private void setupUI() {
        setLayout(null); // Use absolute positioning for free placement
        setPreferredSize(new Dimension(1920, 1080));

        // Title Label for Mind Map
        final JLabel titleLabel = new JLabel("Mind Map", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, 0, getWidth(), 50); // Ensure the title label is visible
        add(titleLabel);

        // Set up right-click context menu
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem addImageMenuItem = new JMenuItem("Add Image Post It");
        JMenuItem addTextMenuItem = new JMenuItem("Add Text Post It");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem logoutMenuItem = new JMenuItem("Logout");

        // Add action listeners for each item
        addImageMenuItem.addActionListener(e -> fetchAndAddImage());
        addTextMenuItem.addActionListener(e -> fetchAndAddText());
        saveMenuItem.addActionListener(e -> saveMindMap());
        logoutMenuItem.addActionListener(e -> logout());

        // Add the menu items to the popup menu
        popupMenu.add(addImageMenuItem);
        popupMenu.add(addTextMenuItem);
        popupMenu.add(saveMenuItem);
        popupMenu.add(logoutMenuItem);

        // Add mouse listener to show the popup menu on right-click
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        revalidate();
        repaint();

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

    private void setupDragFunctionality() {
        // Add mouse listeners for dragging behavior for both image and text post-notes
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (ImagePostNoteViewModel postNote : imagePostNotes) {
                    if (isWithinBounds(postNote, e.getPoint())) {
                        draggedImagePostNote = postNote;
                        dragOffset = new Point(
                                e.getX() - postNote.getX(),
                                e.getY() - postNote.getY()
                        );
                        break;
                    }
                }

                for (TextPostNoteViewModel postNote : textPostNotes) {
                    if (isWithinBounds(postNote, e.getPoint())) {
                        draggedTextPostNote = postNote;
                        dragOffset = new Point(
                                e.getX() - postNote.getX(),
                                e.getY() - postNote.getY()
                        );
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggedImagePostNote = null;  // Reset dragging state
                draggedTextPostNote = null;   // Reset dragging state
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedImagePostNote != null) {
                    draggedImagePostNote.setX(e.getX() - dragOffset.x);
                    draggedImagePostNote.setY(e.getY() - dragOffset.y);
                    repaint();
                }

                if (draggedTextPostNote != null) {
                    draggedTextPostNote.setX(e.getX() - dragOffset.x);
                    draggedTextPostNote.setY(e.getY() - dragOffset.y);
                    repaint();
                }
            }
        });
    }

    private boolean isWithinBounds(ImagePostNoteViewModel postNote, Point point) {
        return point.x >= postNote.getX()
                && point.x <= postNote.getX() + postNote.getWidth()
                && point.y >= postNote.getY()
                && point.y <= postNote.getY() + postNote.getHeight();
    }

    private boolean isWithinBounds(TextPostNoteViewModel postNote, Point point) {
        return point.x >= postNote.getX()
                && point.x <= postNote.getX() + postNote.getWidth()
                && point.y >= postNote.getY()
                && point.y <= postNote.getY() + postNote.getHeight();
    }

    private void fetchAndAddImage() {
        final String query = JOptionPane.showInputDialog(this, "Enter a search term for images:");
        if (query == null || query.isEmpty()) {
            return; // User canceled
        }

        ImagePresenter imagePresenter = new ImagePresenter(imageViewModel);
        imageController.fetchImages(query, imagePresenter);
    }

    private void showImageSelectionDialog(List<ImageViewModel.ImageDisplayData> imageDisplayDataList) {
        final JDialog dialog = new JDialog((Frame) null, "Select an Image", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());

        final JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(0, 3, 10, 10)); // Layout with 3 columns

        for (ImageViewModel.ImageDisplayData imageData : imageDisplayDataList) {
            try {
                // Fetch the image from the URL
                URL imageUrl = new URL(imageData.getUrl());
                Image image = ImageIO.read(imageUrl);

                // Resize the icon for the button
                int iconWidth = 100;  // desired width for icon
                int iconHeight = 100; // desired height for icon
                Image resizedIcon = image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);

                // Create an ImageIcon with the resized image for the button
                ImageIcon icon = new ImageIcon(resizedIcon);

                final JButton imageButton = new JButton(icon);
                imageButton.addActionListener(evt -> {
                    dialog.dispose(); // Close the dialog

                    // Set the original image size for the post note
                    imagePostNoteViewModel.setImageUrl(imageData.getUrl());
                    imagePostNoteViewModel.setX(50); // Default X position
                    imagePostNoteViewModel.setY(50); // Default Y position
                    imagePostNoteViewModel.setWidth(image.getWidth(null)-100); // Use original image width
                    imagePostNoteViewModel.setHeight(image.getHeight(null)-100); // Use original image height
                    imagePostNoteViewModel.setColor(Color.ORANGE);

                    // Add image post note via the controller
                    imagePostNoteController.addImagePostNote(imagePostNoteViewModel.getImageUrl(), imagePostNoteViewModel.getX(),
                            imagePostNoteViewModel.getY(), imagePostNoteViewModel.getWidth(), imagePostNoteViewModel.getHeight(),
                            imagePostNoteViewModel.getColor());

                    // Once the post note is added, update the MindMapView
                    updateImagePostNotes(imagePostNoteViewModel);  // This will repaint the board with the new image
                });
                imagePanel.add(imageButton);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        dialog.add(new JScrollPane(imagePanel), BorderLayout.CENTER);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void fetchAndAddText() {
        final String text = JOptionPane.showInputDialog(this, "Enter text for the post-it:");
        if (text == null || text.isEmpty()) {
            return; // User canceled or left empty
        }

        // Set default values for text post-it note
        textPostNoteViewModel.setText(text);
        textPostNoteViewModel.setX(50); // Default X position
        textPostNoteViewModel.setY(50); // Default Y position
        textPostNoteViewModel.setWidth(150); // Default width
        textPostNoteViewModel.setHeight(100); // Default height
        textPostNoteViewModel.setColor(Color.YELLOW); // Default color

        // Add text post note via the controller
        textPostNoteController.addTextPostNote(
                textPostNoteViewModel.getText(),
                textPostNoteViewModel.getX(),
                textPostNoteViewModel.getY(),
                textPostNoteViewModel.getWidth(),
                textPostNoteViewModel.getHeight(),
                textPostNoteViewModel.getColor()
        );

        // Once the post note is added, update the MindMapView
        updateTextPostNotes(textPostNoteViewModel);
    }

    public void updateImagePostNotes(ImagePostNoteViewModel postNoteViewModel) {
        ImagePostNoteViewModel newPostNote = new ImagePostNoteViewModel();
        newPostNote.setX(postNoteViewModel.getX());
        newPostNote.setY(postNoteViewModel.getY());
        newPostNote.setWidth(postNoteViewModel.getWidth());
        newPostNote.setHeight(postNoteViewModel.getHeight());
        newPostNote.setColor(postNoteViewModel.getColor());
        newPostNote.setImageUrl(postNoteViewModel.getImageUrl());

        this.imagePostNotes.add(newPostNote);

        repaint();
    }

    public void updateTextPostNotes(TextPostNoteViewModel postNoteViewModel) {
        TextPostNoteViewModel newPostNote = new TextPostNoteViewModel();
        newPostNote.setX(postNoteViewModel.getX());
        newPostNote.setY(postNoteViewModel.getY());
        newPostNote.setWidth(postNoteViewModel.getWidth());
        newPostNote.setHeight(postNoteViewModel.getHeight());
        newPostNote.setColor(postNoteViewModel.getColor());
        newPostNote.setText(postNoteViewModel.getText());

        this.textPostNotes.add(newPostNote);

        repaint();
    }

    private void saveMindMap() {
        try {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set the font to Arial Bold
        Font font = new Font("Arial", Font.BOLD, 12);  // Arial, Bold, Font size 12
        g.setFont(font);

        // Paint image post-notes
        if (imagePostNotes != null && !imagePostNotes.isEmpty()) {
            for (ImagePostNoteViewModel postNote : imagePostNotes) {
                g.setColor(postNote.getColor());
                g.fillRect(postNote.getX(), postNote.getY(), postNote.getWidth(), postNote.getHeight());

                if (postNote.getImageUrl() != null && !postNote.getImageUrl().isEmpty()) {
                    try {
                        Image image = ImageIO.read(new URL(postNote.getImageUrl()));
                        g.drawImage(image, postNote.getX(), postNote.getY(), postNote.getWidth(), postNote.getHeight(), this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Paint text post-notes
        if (textPostNotes != null && !textPostNotes.isEmpty()) {
            for (TextPostNoteViewModel postNote : textPostNotes) {
                g.setColor(postNote.getColor());
                g.fillRect(postNote.getX(), postNote.getY(), postNote.getWidth(), postNote.getHeight());

                // Setup the font and color for the text
                g.setColor(Color.BLACK);
                String text = postNote.getText();
                FontMetrics fm = g.getFontMetrics();
                int lineHeight = fm.getHeight();

                // Split the text into lines to make sure it fits within the note's width
                String[] lines = wrapText(text, fm, postNote.getWidth() - 20); // 20 is padding
                int totalHeight = lines.length * lineHeight;

                // Calculate the vertical starting position for centering the text
                int yPos = postNote.getY() + (postNote.getHeight() - totalHeight) / 2;

                // Draw each line of text centered horizontally
                for (String line : lines) {
                    int lineWidth = fm.stringWidth(line);
                    int xPos = postNote.getX() + (postNote.getWidth() - lineWidth) / 2; // Center the text horizontally
                    g.drawString(line, xPos, yPos);
                    yPos += lineHeight;
                }
            }
        }
    }

    /**
     * Wraps text to fit within the specified width.
     */
    private String[] wrapText(String text, FontMetrics fm, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");

        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            // Check if adding the word exceeds the max width
            if (fm.stringWidth(currentLine.toString() + word) <= maxWidth) {
                currentLine.append(word).append(" ");
            } else {
                // If the line exceeds the width, add the current line and start a new one
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder(word + " ");
            }
        }

        // Add the last line if there's any remaining text
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }

        return lines.toArray(new String[0]);
    }

}
