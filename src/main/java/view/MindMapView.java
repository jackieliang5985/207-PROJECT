package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import data_access.ConnectionDAO;
import entity.ConnectionEntity;
import interface_adapter.add_Connection.AddConnectionController;
import interface_adapter.add_Connection.ConnectionViewModel;
import interface_adapter.add_Connection.PostItNoteViewModel;
import interface_adapter.add_Image_PostNote.ImagePostNoteController;
import interface_adapter.add_Image_PostNote.ImagePostNoteViewModel;
import interface_adapter.add_Text_PostNote.TextPostNoteController;
import interface_adapter.add_Text_PostNote.TextPostNoteViewModel;
import interface_adapter.change_color.ChangeColorController;
import interface_adapter.delete_note.DeletePostNoteController;
import interface_adapter.export_mind_map.ExportController;
import interface_adapter.image.ImageController;
import interface_adapter.image.ImageViewModel;

/**
 * The MindMapView class represents the main visual component of the Mind Map application.
 * It manages and displays post-notes, both image and text, and provides functionalities like
 * drag-and-drop, right-click menus, and interaction with external controllers.
 */
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
    private static final Color DEFAULT_TEXT_NOTE_COLOR = Color.ORANGE;
    private static final Color TEXT_COLOR = Color.BLACK;

    // Constants for Font
    private static final String FONT_NAME = "Arial";
    private static final int FONT_STYLE = Font.BOLD;
    private static final int FONT_SIZE = 15;
    private static final Font LABEL_FONT = new Font(FONT_NAME, FONT_STYLE, FONT_SIZE);
    private static final String SPACE = " ";

    private final CardLayout cardLayout;
    private final Container cardPanel;
    private final ImageViewModel imageViewModel;
    private final ExportController exportController;
    private final ImageController imageController;
    private final ImagePostNoteController imagePostNoteController;
    private final TextPostNoteController textPostNoteController;
    private final ImagePostNoteViewModel imagePostNoteViewModel;
    private final TextPostNoteViewModel textPostNoteViewModel;
    private final DeletePostNoteController deletePostNoteController;
    private final ChangeColorController changeColorController;

    private List<ImagePostNoteViewModel> imagePostNotes;
    private List<TextPostNoteViewModel> textPostNotes;
    private ImagePostNoteViewModel draggedImagePostNote;
    private TextPostNoteViewModel draggedTextPostNote;
    private Point dragOffset;
    private Point lastClickLocation;
    private Point rightClickLocation = null;

    private final AddConnectionController addConnectionController;
    private final ConnectionViewModel connectionViewModel;
    private final ConnectionDAO connectionDAO;
    private boolean isAddingConnection = false;
    private String firstSelectedNoteId = null;

    /**
     * Constructor for the MindMapView class.
     *
     * @param cardLayout                 The CardLayout for managing views.
     * @param cardPanel                  The parent container for switching views.
     * @param imageController            The controller for image-related actions.
     * @param imageViewModel             The view model for image data.
     * @param imagePostNoteViewModel     The view model for image post-notes.
     * @param textPostNoteViewModel      The view model for text post-notes.
     * @param exportController           The controller for exporting the mind map.
     * @param imagePostNoteController    The controller for managing image post-notes.
     * @param textPostNoteController     The controller for managing text post-notes.
     * @param deletePostNoteController   The controller for managing deleting post-notes.
     * @param changeColorController      The controller for customizing the color of post-notes.
     */
    public MindMapView(CardLayout cardLayout,
                       Container cardPanel,
                       ImageController imageController,
                       ImageViewModel imageViewModel,
                       ImagePostNoteViewModel imagePostNoteViewModel,
                       TextPostNoteViewModel textPostNoteViewModel,
                       ExportController exportController,
                       ImagePostNoteController imagePostNoteController,
                       TextPostNoteController textPostNoteController,
                       DeletePostNoteController deletePostNoteController,
                       ChangeColorController changeColorController,
                       AddConnectionController addConnectionController,
                       ConnectionViewModel connectionViewModel,
                       ConnectionDAO connectionDAO) {

        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.imageController = imageController;
        this.imageViewModel = imageViewModel;
        this.exportController = exportController;
        this.imagePostNoteController = imagePostNoteController;
        this.textPostNoteController = textPostNoteController;
        this.imagePostNoteViewModel = imagePostNoteViewModel;
        this.textPostNoteViewModel = textPostNoteViewModel;
        this.deletePostNoteController = deletePostNoteController;
        this.changeColorController = changeColorController;
        this.connectionDAO = connectionDAO;
        this.connectionViewModel = connectionViewModel;
        this.addConnectionController = addConnectionController;

        this.imagePostNotes = new ArrayList<>();
        this.textPostNotes = new ArrayList<>();

        setupUI();
        setupDragFunctionality();
    }


    /**
     * Sets up the UI components for the Mind Map View, including the title label
     * and the context menu for adding notes and managing the view.
     */
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

        final JPopupMenu popupMenu = new JPopupMenu();

        final JMenuItem addImageMenuItem = new JMenuItem("Add Image Post It");
        final JMenuItem addTextMenuItem = new JMenuItem("Add Text Post It");
        final JMenuItem changeColorMenuItem = new JMenuItem("Change Color");
        final JMenuItem deleteMenuItem = new JMenuItem("Delete Post It");
        final JMenuItem saveMenuItem = new JMenuItem("Save");
        final JMenuItem logoutMenuItem = new JMenuItem("Logout");
        final JMenuItem addConnectionMenuItem = new JMenuItem("Add Connection");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    rightClickLocation = e.getPoint(); // Store the point of the right-click
                    System.out.println("Right-click location stored: " + rightClickLocation);
                }
            }
        });

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

        changeColorMenuItem.addActionListener(e -> {
            final Point clickLocation = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(clickLocation, this);
            System.out.println("this is the click location" + clickLocation);
            changeColorAtLocation(rightClickLocation);

        });

        deleteMenuItem.addActionListener(e -> {
            final Point clickLocation = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(clickLocation, this);
            System.out.println("this is the click location" + clickLocation);
            deletePostNoteAtLocation(rightClickLocation);  // Call the deletion logic

        });

        addConnectionMenuItem.addActionListener(e -> {
            startAddingConnection(rightClickLocation);
        });

        // Add the menu items to the popup menu
        popupMenu.add(addImageMenuItem);
        popupMenu.add(addTextMenuItem);
        popupMenu.add(changeColorMenuItem);
        popupMenu.add(deleteMenuItem);
        popupMenu.add(saveMenuItem);
        popupMenu.add(logoutMenuItem);
        popupMenu.add(deleteMenuItem);
        popupMenu.add(addConnectionMenuItem);

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
                final List<ImageViewModel.ImageDisplayData> images =
                        (List<ImageViewModel.ImageDisplayData>) evt.getNewValue();
                showImageSelectionDialog(images, lastClickLocation);
            }
            else if ("errorMessage".equals(evt.getPropertyName())) {
                JOptionPane.showMessageDialog(this, imageViewModel.getErrorMessage());
            }
        });
    }

    /**
     * Configures the drag functionality for both image and text post-notes.
     * Handles mouse events for initiating and stopping the drag actions.
     */
    private void setupDragFunctionality() {
        // Add mouse listeners for dragging behavior for both image and text post-notes
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isAddingConnection) {
                    String secondNoteId = getNoteIdAtLocation(e.getPoint());
                    System.out.println("Second Selected Note ID: " + secondNoteId);
                    if (secondNoteId == null) {
                        JOptionPane.showMessageDialog(MindMapView.this, "No post-it found at the clicked location.");
                        isAddingConnection = false;
                        firstSelectedNoteId = null;
                    } else if (secondNoteId.equals(firstSelectedNoteId)) {
                        JOptionPane.showMessageDialog(MindMapView.this, "Cannot connect a note to itself.");
                        isAddingConnection = false;
                        firstSelectedNoteId = null;
                    } else {
                        addConnectionController.addConnection(firstSelectedNoteId, secondNoteId);
                        isAddingConnection = false;
                        firstSelectedNoteId = null;

                        // Check if the connection was successful
                        if (connectionViewModel.isSuccess()) {
                            repaint();
                        } else {
                            JOptionPane.showMessageDialog(MindMapView.this, connectionViewModel.getMessage());
                        }
                    }
                } else {
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
            }

            @Override
            public void mouseReleased(MouseEvent e) {
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

    /**
     * Checks if the point is within the bounds of the image post note.
     *
     * @param postNote The image post note to check against.
     * @param point The point to check if it lies within the post note's bounds.
     * @return True if the point is within the bounds of the image post note, otherwise false.
     */
    private boolean isWithinBounds(ImagePostNoteViewModel postNote, Point point) {
        return point.x >= postNote.getX()
                && point.x <= postNote.getX() + postNote.getWidth()
                && point.y >= postNote.getY()
                && point.y <= postNote.getY() + postNote.getHeight();
    }

    /**
     * Checks if the point is within the bounds of the text post note.
     *
     * @param postNote The text post note to check against.
     * @param point The point to check if it lies within the post note's bounds.
     * @return True if the point is within the bounds of the text post note, otherwise false.
     */
    private boolean isWithinBounds(TextPostNoteViewModel postNote, Point point) {
        return point.x >= postNote.getX()
                && point.x <= postNote.getX() + postNote.getWidth()
                && point.y >= postNote.getY()
                && point.y <= postNote.getY() + postNote.getHeight();
    }

    /**
     * Fetches and adds an image to the mind map based on a search query.
     * Displays a dialog for the user to choose an image.
     *
     * @param location The location where the image post note should be placed.
     */
    private void fetchAndAddImage(Point location) {
        final String query = JOptionPane.showInputDialog(this, "Enter a search term for images:");
        if (query == null || query.isEmpty()) {
            return;
        }

        lastClickLocation = location;
        imageController.fetchImages(query);
        System.out.println("Creating Image Note with ID: " + imagePostNoteViewModel.getId());

    }

    /**
     * Displays a dialog for the user to select an image from a list of available images.
     *
     * @param imageDisplayDataList List of images fetched from the search.
     * @param location The location where the image post note should be placed.
     */
    private void showImageSelectionDialog(List<ImageViewModel.ImageDisplayData> imageDisplayDataList, Point location) {
        final JDialog dialog = new JDialog((Frame) null, "Select an Image", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());

        final JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(ZERO, GRID_LAYOUT_COLUMNS, GRID_LAYOUT_HGAP, GRID_LAYOUT_VGAP));

        for (ImageViewModel.ImageDisplayData imageData : imageDisplayDataList) {
            try {
                final URL imageUrl = new URL(imageData.getUrl());
                final Image image = ImageIO.read(imageUrl);

                final Image resizedIcon = image.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
                final ImageIcon icon = new ImageIcon(resizedIcon);

                final JButton imageButton = new JButton(icon);
                imageButton.addActionListener(evt -> {
                    dialog.dispose();

                    imagePostNoteViewModel.setImageUrl(imageData.getUrl());
                    imagePostNoteViewModel.setX(location.x);
                    imagePostNoteViewModel.setY(location.y);
                    imagePostNoteViewModel.setWidth(image.getWidth(null) - IMAGE_POST_NOTE_RESIZE_OFFSET);
                    imagePostNoteViewModel.setHeight(image.getHeight(null) - IMAGE_POST_NOTE_RESIZE_OFFSET);
                    imagePostNoteViewModel.setColor(DEFAULT_IMAGE_NOTE_COLOR);

                    imagePostNoteController.addImagePostNote(
                            imagePostNoteViewModel.getImageUrl(),
                            imagePostNoteViewModel.getX(),
                            imagePostNoteViewModel.getY(),
                            imagePostNoteViewModel.getWidth(),
                            imagePostNoteViewModel.getHeight(),
                            imagePostNoteViewModel.getColor()
                    );

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

    /**
     * Fetches and adds a text post-it note to the mind map.
     *
     * @param location The location where the text post note should be placed.
     */
    private void fetchAndAddText(Point location) {
        final String text = JOptionPane.showInputDialog(this, "Enter text for the post-it:");
        if (text == null || text.isEmpty()) {
            return;
        }
        final String noteId = UUID.randomUUID().toString();
        textPostNoteViewModel.setId(noteId);

        textPostNoteViewModel.setText(text);
        textPostNoteViewModel.setX(location.x);
        textPostNoteViewModel.setY(location.y);
        textPostNoteViewModel.setWidth(DEFAULT_TEXT_NOTE_WIDTH);
        textPostNoteViewModel.setHeight(DEFAULT_TEXT_NOTE_HEIGHT);
        textPostNoteViewModel.setColor(DEFAULT_TEXT_NOTE_COLOR);

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

    /**
     * Updates the image post-notes in the mind map view by adding a new image post-note.
     *
     * @param postNoteViewModel The image post-note view model to add.
     */
    public void updateImagePostNotes(ImagePostNoteViewModel postNoteViewModel) {
        final ImagePostNoteViewModel newPostNote = new ImagePostNoteViewModel();
        newPostNote.setX(postNoteViewModel.getX());
        newPostNote.setY(postNoteViewModel.getY());
        newPostNote.setWidth(postNoteViewModel.getWidth());
        newPostNote.setHeight(postNoteViewModel.getHeight());
        newPostNote.setColor(postNoteViewModel.getColor());
        newPostNote.setImageUrl(postNoteViewModel.getImageUrl());
        newPostNote.setId(UUID.randomUUID().toString());

        this.imagePostNotes.add(newPostNote);

        repaint();
    }

    /**
     * Updates the text post-notes in the mind map view by adding a new text post-note.
     *
     * @param postNoteViewModel The text post-note view model to add.
     */
    public void updateTextPostNotes(TextPostNoteViewModel postNoteViewModel) {
        final TextPostNoteViewModel newPostNote = new TextPostNoteViewModel();
        newPostNote.setX(postNoteViewModel.getX());
        newPostNote.setY(postNoteViewModel.getY());
        newPostNote.setWidth(postNoteViewModel.getWidth());
        newPostNote.setHeight(postNoteViewModel.getHeight());
        newPostNote.setColor(postNoteViewModel.getColor());
        newPostNote.setText(postNoteViewModel.getText());
        newPostNote.setId(postNoteViewModel.getId());


        this.textPostNotes.add(newPostNote);
        repaint();
        System.out.println("Creating Text Note with ID: " + textPostNoteViewModel.getId());
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "You have been logged out. Returning to the Login page");
        cardLayout.show(cardPanel, "CreateNewMindMapView");
    }

    /**
     * Paints the components on the panel.
     *
     * @param g The Graphics object used for painting.
     */
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

        // Paint text post-notes
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
        List<ConnectionEntity> connections = connectionDAO.getAllConnections();
        System.out.println("Connections retrieved for drawing:");

        if (connections != null) {
            System.out.println("Number of connections: " + connections.size());

            if (connections.isEmpty()) {
                System.out.println("No connections found");
            } else {
                for (ConnectionEntity connection : connections) {
                    System.out.println(" - Connection between: " + connection.getFromNoteId() + " and " + connection.getToNoteId());
                }
            }
        } else {
            System.out.println("Connections list is null");
        }

        if (connections != null) {
            g.setColor(Color.BLACK); // Set line color
            for (ConnectionEntity connection : connections) {
                PostItNoteViewModel fromNote = getPostNoteById(connection.getFromNoteId());
                PostItNoteViewModel toNote = getPostNoteById(connection.getToNoteId());

                if (fromNote != null && toNote != null) {
                    int x1 = fromNote.getX() + fromNote.getWidth() / 2;
                    int y1 = fromNote.getY() + fromNote.getHeight() / 2;
                    int x2 = toNote.getX() + toNote.getWidth() / 2;
                    int y2 = toNote.getY() + toNote.getHeight() / 2;

                    g.drawLine(x1, y1, x2, y2);
                }
            }
        }
    }
        /**
         * Wraps text to fit within the specified width.
         *
         * @param text The text to wrap.
         * @param fm The FontMetrics used to measure the width.
         * @param maxWidth The maximum allowed width.
         * @return An array of lines that fit within the width.
         */
        private String[] wrapText (String text, FontMetrics fm,int maxWidth){
            final List<String> lines = new ArrayList<>();
            final String[] words = text.split(SPACE);

        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            // Check if adding the word exceeds the max width
            if (fm.stringWidth(currentLine.toString() + word) <= maxWidth) {
                currentLine.append(word).append(SPACE);
            }
            else {
                // If the line exceeds the width, add the current line and start a new one
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder(word + SPACE);
            }
        }

        // Add the last line if there's any remaining text
        if (currentLine.length() > ZERO) {
            lines.add(currentLine.toString().trim());
        }

        return lines.toArray(new String[ZERO]);
    }

    private void saveMindMap() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Mind Map");
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Image (*.png)", "png"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG Image (*.jpg)", "jpg"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Document (*.pdf)", "pdf"));

        // Show the file chooser and wait for user selection
        int userSelection = fileChooser.showSaveDialog(null);

        // If the user selects a file to save
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File fileToSave = fileChooser.getSelectedFile();

            // Get the selected file format (from the file filter)
            String fileFormat = getFileFormat(fileChooser);

            // Make sure the selected file has the appropriate extension
            if (!fileToSave.getName().toLowerCase().endsWith("." + fileFormat)) {
                fileToSave = new File(fileToSave.getAbsolutePath() + "." + fileFormat);
            }

            // Create a BufferedImage of the boardPanel (or any renderable content)
            BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            this.paint(g2d); // Render the boardPanel to the image
            g2d.dispose(); // Dispose of the graphics context

            // Pass the image, file, and format to the ExportController to handle the export
            exportController.execute(image, fileToSave, fileFormat);
        }
    }

    private String getFileFormat(JFileChooser fileChooser) {
        String description = fileChooser.getFileFilter().getDescription();
        if (description.contains("PNG")) return "png";
        if (description.contains("JPEG")) return "jpg";
        if (description.contains("PDF")) return "pdf";
        return "";
    }

    private void changeColorAtLocation(Point location) {

        // Check if the click is within the bounds of any text post note
        for (TextPostNoteViewModel postNote : textPostNotes) {
            System.out.println("it is going through the loop");
            if (isWithinBounds(postNote, location)) {
                // Call the delete controller with the post-note's coordinates and size
                Color newColor = JColorChooser.showDialog(null, "Choose a color", postNote.getColor());
                System.out.println("Color is " + newColor.toString());
                changeColorController.execute(postNote.getX(), postNote.getY(), postNote.getWidth(),
                        postNote.getHeight(), newColor);
                postNote.setColor(newColor);
                repaint();
                return;
            }
        }

        // If no post note is found at the location, notify the user
        JOptionPane.showMessageDialog(this, "No post-it found at the clicked location.");
    }

    private void deletePostNoteAtLocation(Point location) {
        // Check if the click is within the bounds of any image post note
        for (ImagePostNoteViewModel postNote : imagePostNotes) {
            if (isWithinBounds(postNote, location)) {
                // Call the delete controller with the post-note's coordinates and size
                deletePostNoteController.deletePostNote(postNote.getX(), postNote.getY(), postNote.getWidth(), postNote.getHeight());
                imagePostNotes.remove(postNote);  // Remove the post note from the list
                connectionDAO.deleteConnectionsByNoteId(postNote.getId());
                repaint();  // Repaint to reflect the deletion
                return;
            }
        }

            // Check if the click is within the bounds of any text post note
        for (TextPostNoteViewModel postNote : textPostNotes) {
            System.out.println("it is going through the loop");
            if (isWithinBounds(postNote, location)) {
                // Call the delete controller with the post-note's coordinates and size
                deletePostNoteController.deletePostNote(postNote.getX(), postNote.getY(), postNote.getWidth(), postNote.getHeight());
                textPostNotes.remove(postNote);  // Remove the post note from the list
                connectionDAO.deleteConnectionsByNoteId(postNote.getId());
                repaint();  // Repaint to reflect the deletion
                return;
                }
            }

        // If no post note is found at the location, notify the user
        JOptionPane.showMessageDialog(this, "No post-it found at the clicked location.");
    }

        private void startAddingConnection (Point location){
            isAddingConnection = true;
            firstSelectedNoteId = getNoteIdAtLocation(location);
            System.out.println("First Selected Note ID: " + firstSelectedNoteId);
            if (firstSelectedNoteId == null) {
                JOptionPane.showMessageDialog(this, "No post-it found at the selected location.");
                isAddingConnection = false;
            } else {
                JOptionPane.showMessageDialog(this, "Select the second post-it to connect.");
            }
        }

        private String getNoteIdAtLocation (Point location){
            for (ImagePostNoteViewModel postNote : imagePostNotes) {
                if (isWithinBounds(postNote, location)) {
                    return postNote.getId();
                }
            }
            for (TextPostNoteViewModel postNote : textPostNotes) {
                if (isWithinBounds(postNote, location)) {
                    return postNote.getId();
                }
            }
            return null;
        }
        private PostItNoteViewModel getPostNoteById (String noteId){
            for (ImagePostNoteViewModel note : imagePostNotes) {
                if (note.getId().equals(noteId)) {
                    return note;
                }
            }
            for (TextPostNoteViewModel note : textPostNotes) {
                if (note.getId().equals(noteId)) {
                    return note;
                }
            }
            return null;
        }
    }


