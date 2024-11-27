package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import interface_adapter.add_Image_PostNote.ImagePostNoteController;
import interface_adapter.add_Image_PostNote.ImagePostNoteViewModel;
import interface_adapter.add_Text_PostNote.TextPostNoteController;
import interface_adapter.add_Text_PostNote.TextPostNoteViewModel;
import interface_adapter.export_mind_map.ExportController;
import interface_adapter.image.ImageController;
import interface_adapter.image.ImagePresenter;
import interface_adapter.image.ImageViewModel;

public class MindMapView extends JPanel {
    public static final String VIEW_NAME = "MINDMAP";

    // Constants for UI Dimensions and Padding
    private static final int PANEL_WIDTH = 1920;
    private static final int PANEL_HEIGHT = 1080;
    private static final int TITLE_LABEL_HEIGHT = 50;
    private static final int ICON_WIDTH = 100;
    private static final int ICON_HEIGHT = 100;
    private static final int TEXT_PADDING = 20;
    private static final int DEFAULT_TEXT_NOTE_WIDTH = 150;
    private static final int DEFAULT_TEXT_NOTE_HEIGHT = 100;
    private static final int IMAGE_POST_NOTE_RESIZE_OFFSET = 100;
    private static final int DIALOG_WIDTH = 600;
    private static final int DIALOG_HEIGHT = 400;
    private static final int GRID_LAYOUT_COLUMNS = 3;
    private static final int GRID_LAYOUT_HGAP = 10;
    private static final int GRID_LAYOUT_VGAP = 10;
    private static final int ZERO = 0;

    // Constants for Colors
    private static final Color TITLE_LABEL_BG_COLOR = Color.LIGHT_GRAY;
    private static final Color TITLE_LABEL_TEXT_COLOR = Color.BLACK;
    private static final Color DEFAULT_IMAGE_NOTE_COLOR = Color.ORANGE;
    private static final Color DEFAULT_TEXT_NOTE_COLOR = Color.YELLOW;
    private static final Color TEXT_COLOR = Color.BLACK;

    // Constants for Font
    private static final String FONT_NAME = "Arial";
    private static final int FONT_STYLE = Font.BOLD;
    private static final int FONT_SIZE = 15;
    private static final Font LABEL_FONT = new Font(FONT_NAME, FONT_STYLE, FONT_SIZE);

    private final CardLayout cardLayout;
    private final Container cardPanel;
    private final ImageViewModel imageViewModel;
    private final ExportController exportController;
    private final ImageController imageController;
    private final ImagePostNoteController imagePostNoteController;
    private final TextPostNoteController textPostNoteController;
    private final ImagePostNoteViewModel imagePostNoteViewModel;
    private final TextPostNoteViewModel textPostNoteViewModel;

    // List to store all image post-notes
    private List<ImagePostNoteViewModel> imagePostNotes;
    // List to store all text post-notes
    private List<TextPostNoteViewModel> textPostNotes;

    // Currently dragged image post note
    private ImagePostNoteViewModel draggedImagePostNote;
    // Currently dragged text post note
    private TextPostNoteViewModel draggedTextPostNote;
    // Offset for dragging position
    private Point dragOffset;
    private Point lastClickLocation;

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

        // Add drag functionality
        setupDragFunctionality();
    }

    private void setupUI() {
        setLayout(null);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        final JLabel titleLabel = new JLabel("Mind Map", JLabel.CENTER);
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(TITLE_LABEL_BG_COLOR);
        titleLabel.setForeground(TITLE_LABEL_TEXT_COLOR);
        titleLabel.setBounds(ZERO, ZERO, getWidth(), TITLE_LABEL_HEIGHT);
        add(titleLabel);

        // Set up right-click context menu
        final JPopupMenu popupMenu = new JPopupMenu();

        final JMenuItem addImageMenuItem = new JMenuItem("Add Image Post It");
        final JMenuItem addTextMenuItem = new JMenuItem("Add Text Post It");
        final JMenuItem saveMenuItem = new JMenuItem("Save");
        final JMenuItem logoutMenuItem = new JMenuItem("Logout");

        addTextMenuItem.addActionListener(e -> {
            final Point clickLocation = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(clickLocation, this);
            fetchAndAddText(clickLocation);
        });

        addImageMenuItem.addActionListener(e -> {
            final Point clickLocation = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(clickLocation, this);
            fetchAndAddImage(clickLocation);
        });

        saveMenuItem.addActionListener(e -> saveMindMap());
        logoutMenuItem.addActionListener(e -> logout());

        popupMenu.add(addImageMenuItem);
        popupMenu.add(addTextMenuItem);
        popupMenu.add(saveMenuItem);
        popupMenu.add(logoutMenuItem);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    lastClickLocation = e.getPoint();
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        revalidate();
        repaint();

        imageViewModel.addPropertyChangeListener(evt -> {
            if ("images".equals(evt.getPropertyName())) {
                final List<ImageViewModel.ImageDisplayData> images = (List<ImageViewModel.ImageDisplayData>) evt.getNewValue();
                showImageSelectionDialog(images, lastClickLocation);
            }
            else if ("errorMessage".equals(evt.getPropertyName())) {
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
                // Reset dragging state
                draggedImagePostNote = null;
                draggedTextPostNote = null;
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

    private void fetchAndAddImage(Point location) {
        final String query = JOptionPane.showInputDialog(this, "Enter a search term for images:");
        if (query == null || query.isEmpty()) {
            return;
        }

        // Save the click location to use it later
        lastClickLocation = location;

        final ImagePresenter imagePresenter = new ImagePresenter(imageViewModel);
        // Trigger fetching images
        imageController.fetchImages(query, imagePresenter);
    }

    private void showImageSelectionDialog(List<ImageViewModel.ImageDisplayData> imageDisplayDataList, Point location) {
        final JDialog dialog = new JDialog((Frame) null, "Select an Image", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());

        final JPanel imagePanel = new JPanel();
        // Layout with 3 columns
        imagePanel.setLayout(new GridLayout(ZERO, GRID_LAYOUT_COLUMNS, GRID_LAYOUT_HGAP, GRID_LAYOUT_VGAP));

        for (ImageViewModel.ImageDisplayData imageData : imageDisplayDataList) {
            try {
                // Fetch the image from the URL
                final URL imageUrl = new URL(imageData.getUrl());
                final Image image = ImageIO.read(imageUrl);

                // Resize the icon for the button
                final int iconWidth = ICON_WIDTH;
                final int iconHeight = ICON_HEIGHT;
                final Image resizedIcon = image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);

                // Create an ImageIcon with the resized image for the button
                final ImageIcon icon = new ImageIcon(resizedIcon);

                final JButton imageButton = new JButton(icon);
                imageButton.addActionListener(evt -> {
                    dialog.dispose();

                    // Set the original image size for the post note at the clicked location
                    imagePostNoteViewModel.setImageUrl(imageData.getUrl());

                    // Click Positions
                    imagePostNoteViewModel.setX(location.x);
                    imagePostNoteViewModel.setY(location.y);

                    // Reszing OG image
                    imagePostNoteViewModel.setWidth(image.getWidth(null) - IMAGE_POST_NOTE_RESIZE_OFFSET);
                    imagePostNoteViewModel.setHeight(image.getHeight(null) - IMAGE_POST_NOTE_RESIZE_OFFSET);
                    imagePostNoteViewModel.setColor(Color.ORANGE);

                    // Add image post note via the controller
                    imagePostNoteController.addImagePostNote(
                            imagePostNoteViewModel.getImageUrl(),
                            imagePostNoteViewModel.getX(),
                            imagePostNoteViewModel.getY(),
                            imagePostNoteViewModel.getWidth(),
                            imagePostNoteViewModel.getHeight(),
                            imagePostNoteViewModel.getColor()
                    );

                    // Once the post note is added, update the MindMapView
                    updateImagePostNotes(imagePostNoteViewModel);
                });
                imagePanel.add(imageButton);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        dialog.add(new JScrollPane(imagePanel), BorderLayout.CENTER);
        dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void fetchAndAddText(Point location) {
        final String text = JOptionPane.showInputDialog(this, "Enter text for the post-it:");
        if (text == null || text.isEmpty()) {
            return;
        }

        // Set default values for the text post-it note at the clicked location
        textPostNoteViewModel.setText(text);

        // Using Click location
        textPostNoteViewModel.setX(location.x);
        textPostNoteViewModel.setY(location.y);

        // Default parameters.
        textPostNoteViewModel.setWidth(DEFAULT_TEXT_NOTE_WIDTH);
        textPostNoteViewModel.setHeight(DEFAULT_TEXT_NOTE_HEIGHT);
        textPostNoteViewModel.setColor(Color.YELLOW);

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
        final ImagePostNoteViewModel newPostNote = new ImagePostNoteViewModel();
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
        final TextPostNoteViewModel newPostNote = new TextPostNoteViewModel();
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
        }
        catch (Exception e) {
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

        g.setFont(LABEL_FONT);

        if (imagePostNotes != null && !imagePostNotes.isEmpty()) {
            for (ImagePostNoteViewModel postNote : imagePostNotes) {
                g.setColor(postNote.getColor());
                g.fillRect(postNote.getX(), postNote.getY(), postNote.getWidth(), postNote.getHeight());

                if (postNote.getImageUrl() != null && !postNote.getImageUrl().isEmpty()) {
                    try {
                        final Image image = ImageIO.read(new URL(postNote.getImageUrl()));
                        g.drawImage(image, postNote.getX(), postNote.getY(), postNote.getWidth(), postNote.getHeight(), this);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (textPostNotes != null && !textPostNotes.isEmpty()) {
            for (TextPostNoteViewModel postNote : textPostNotes) {
                g.setColor(postNote.getColor());
                g.fillRect(postNote.getX(), postNote.getY(), postNote.getWidth(), postNote.getHeight());

                g.setColor(TEXT_COLOR);
                final String text = postNote.getText();
                final FontMetrics fm = g.getFontMetrics();
                final int lineHeight = fm.getHeight();
                final String[] lines = wrapText(text, fm, postNote.getWidth() - TEXT_PADDING);
                final int totalHeight = lines.length * lineHeight;

                int yPos = postNote.getY() + (postNote.getHeight() - totalHeight) / 2;

                for (String line : lines) {
                    final int lineWidth = fm.stringWidth(line);
                    final int xPos = postNote.getX() + (postNote.getWidth() - lineWidth) / 2;
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
        final List<String> lines = new ArrayList<>();
        final String[] words = text.split(" ");

        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            // Check if adding the word exceeds the max width
            if (fm.stringWidth(currentLine.toString() + word) <= maxWidth) {
                currentLine.append(word).append(" ");
            }
            else {
                // If the line exceeds the width, add the current line and start a new one
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder(word + " ");
            }
        }

        // Add the last line if there's any remaining text
        if (currentLine.length() > ZERO) {
            lines.add(currentLine.toString().trim());
        }

        return lines.toArray(new String[ZERO]);
    }

}
