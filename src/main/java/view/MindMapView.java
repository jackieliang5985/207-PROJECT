package view;

import entity.ImagePostNote;
import entity.PostNote;
import interface_adapter.create_MindMap.SquarePanel;
import com.itextpdf.text.DocumentException;
import entity.CommonImage;
import interface_adapter.export_mind_map.ExportController;
import interface_adapter.export_mind_map.ExportViewModel;
import interface_adapter.image.ImageController;
import interface_adapter.image.ImagePresenter;
import interface_adapter.image.ImageViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;
import use_case.export_mind_map.ExportInputData;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MindMapView extends JPanel {
    public static final String VIEW_NAME = "MINDMAP";

    private final CardLayout cardLayout;
    private final Container cardPanel;
    private final SquarePanel boardPanel;

    // Load the API key from the .env file
    private final Dotenv dotenv = Dotenv.configure()
            .directory(".")
            .load();
    private final String unsplashApiKey = dotenv.get("UNSPLASH_API_KEY");

    private final ImageController imageController;
    private final ImageViewModel imageViewModel;

    private ExportController exportController;

    // Constants for dimensions and spacing
    private final int zero = 0;
    private final int one = 1;
    private final int three = 3;
    private final int five = 5;
    private final int ten = 10;
    private final int tweleve = 12;
    private final int twentyFour = 24;
    private final int fifty = 50;
    private final int hundredFifty = 150;
    private final int twoHundredThirty = 230;
    private final int twoHundredFifty = 250;
    private final int fourHundred = 400;
    private final int sixHundred = 600;

    private final ArrayList<PostNote> postNotes = new ArrayList<>();

    public MindMapView(CardLayout cardLayout, Container cardPanel, ImageController imageController,
                       ImageViewModel imageViewModel, ExportController exportController) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.imageController = imageController;
        this.imageViewModel = imageViewModel;
        this.exportController = exportController;

        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(1920, 1080));
        final JPanel mainPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1920, 1080);
            }
        };
        final JLabel titleLabel = new JLabel("Mind Map", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, twentyFour));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.NORTH);

        // Main center panel for the board area
        boardPanel = new SquarePanel(postNotes);
        boardPanel.setBackground(Color.LIGHT_GRAY);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, three));
        boardPanel.setLayout(null);
        add(boardPanel, BorderLayout.CENTER);

        // Bottom panel for buttons
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(one, five, ten, zero));
        bottomPanel.setBackground(new Color(twoHundredThirty, twoHundredThirty, twoHundredFifty));

        final JButton addTextPostButton = createStyledButton("Add Text Post It");
        final JButton addImageButton = createStyledButton("Add Image Post It");
        final JButton attachStringButton = createStyledButton("Attach String");
        final JButton saveButton = createStyledButton("SAVE");
        final JButton logoutButton = createStyledButton("LOGOUT");

        // Add action listener for "Add Image Post It"
        addImageButton.addActionListener(evt -> fetchAndAddImage());

        addTextPostButton.addActionListener(evt -> boardPanel.createPostNote());

        // Adding action listener to the logout button
        logoutButton.addActionListener(evt -> {
            JOptionPane.showMessageDialog(
                    MindMapView.this,
                    "You have been logged out. Returning to the Login page"
            );
            cardLayout.show(cardPanel, "CreateNewMindMapView");
        });

        // Add buttons to the bottom panel
        bottomPanel.add(addTextPostButton);
        bottomPanel.add(addImageButton);
        bottomPanel.add(attachStringButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(evt -> {
            // Create a file chooser to let the user pick where to save the file
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
                BufferedImage image = new BufferedImage(boardPanel.getWidth(), boardPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = image.createGraphics();
                boardPanel.paint(g2d); // Render the boardPanel to the image
                g2d.dispose(); // Dispose of the graphics context

                // Pass the image, file, and format to the ExportController to handle the export
                exportController.execute(image, fileToSave, fileFormat);
            }
        });


        // Add property change listener to update the view with the fetched images
        imageViewModel.addPropertyChangeListener(evt -> {
            if ("images".equals(evt.getPropertyName())) {
                final List<CommonImage> images = (List<CommonImage>) evt.getNewValue();
                showImageSelectionDialog(images);
            }
            else if ("errorMessage".equals(evt.getPropertyName())) {
                JOptionPane.showMessageDialog(this, imageViewModel.getErrorMessage());
            }
        });

    }

    /**
     * Helper method to create styled buttons.
     */
    private JButton createStyledButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, tweleve));
        button.setBackground(new Color(twoHundredThirty, twoHundredThirty, twoHundredFifty));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, one));
        return button;
    }

    private String getFileFormat(JFileChooser fileChooser) {
        String description = fileChooser.getFileFilter().getDescription();
        if (description.contains("PNG")) return "png";
        if (description.contains("JPEG")) return "jpg";
        if (description.contains("PDF")) return "pdf";
        return "";
    }

    /**
     * Fetch images from Unsplash and add one to the board.
     */
    private void fetchAndAddImage() {
        final String query = JOptionPane.showInputDialog(this, "Enter a search term for images:");
        if (query == null || query.isEmpty()) {
            // User cancelled or something
            return;
        }

        final ImagePresenter imagePresenter = new ImagePresenter(imageViewModel);
        imageController.fetchImages(query, imagePresenter);
    }

    /**
     * Saves a BufferedImage as a PDF file.
     * This method converts the given BufferedImage into a PDF format and saves it
     * to the specified file location. The generated PDF will contain a single page
     * with the image scaled to fit.
     *
     * @param image the BufferedImage to be saved as a PDF
     * @param file  the file where the PDF will be saved
     * @throws IOException              if an error occurs while writing to the file
     * @throws com.itextpdf.text.DocumentException if an error occurs with the PDF generation
     */
    private void saveAsPdf(BufferedImage image, java.io.File file)
            throws IOException, com.itextpdf.text.DocumentException {
        final com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        com.itextpdf.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream(file));
        document.open();

        final com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(image, null);
        pdfImage.scaleToFit(document.getPageSize().getWidth() - fifty, document.getPageSize()
                .getHeight() - fifty);
        pdfImage.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);

        document.add(pdfImage);
        document.close();
    }

    /**
     * Display images in a dialog for selection.
     */
    private void showImageSelectionDialog(List<CommonImage> commonImages) {
        final JDialog dialog = new JDialog((Frame) null, "Select an Image", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());

        final JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(zero, three, ten, ten));

        final CommonImage[] selectedCommonImage = {null};
        for (CommonImage commonImage : commonImages) {
            try {
                final BufferedImage bufferedImage = ImageIO.read(new URL(commonImage.getUrl()));
                if (bufferedImage == null) {
                    System.err.println("Failed to fetch image: " + commonImage.getUrl());
                    continue;
                }
                final ImageIcon icon = new ImageIcon(bufferedImage.getScaledInstance(100, 100,
                        java.awt.Image.SCALE_SMOOTH));
                final JButton imageButton = new JButton(icon);
                imageButton.addActionListener(evt -> {
                    selectedCommonImage[zero] = commonImage;
                    dialog.dispose();

                    // Call the method to add the image to the board after selection
                    addImageToBoard(selectedCommonImage[zero]);
                });
                imagePanel.add(imageButton);
            }
            catch (IOException exception) {
                System.err.println("Error loading image: " + commonImage.getUrl());
                exception.printStackTrace();
            }
        }

        final JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setSize(sixHundred, fourHundred);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Add the selected image to the board as a draggable component.
     */
    /**
     * Add the selected image to the board as a PostNote.
     */
    private void addImageToBoard(CommonImage commonImage) {
        try {
            // Fetch the image from the URL
            final BufferedImage bufferedImage = ImageIO.read(new URL(commonImage.getUrl()));
            if (bufferedImage == null) {
                JOptionPane.showMessageDialog(this, "Failed to load the selected image.");
                return;
            }

            final ImageIcon imageIcon = new ImageIcon(bufferedImage);

            final ImagePostNote imagePostNote = new ImagePostNote(fifty, fifty, Color.ORANGE, boardPanel);
            imagePostNote.setImage(imageIcon);

            // Add the ImagePostNote to the board (SquarePanel)
            boardPanel.createPostNote(imagePostNote);

            boardPanel.revalidate();
            boardPanel.repaint();

        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this, "Error loading the image: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

}