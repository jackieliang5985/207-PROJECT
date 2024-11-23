package view;

import entity.CommonImage;

import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import interface_adapter.image.ImageController;
import interface_adapter.image.ImagePresenter;
import interface_adapter.image.ImageViewModel;
import io.github.cdimascio.dotenv.Dotenv;

public class MindMapView extends JPanel {
    public static final String VIEW_NAME = "MINDMAP";

    private final CardLayout cardLayout;
    private final Container cardPanel;
    private final JPanel boardPanel;

    // Load the API key from .env file
    private Dotenv dotenv = Dotenv.configure()
            .directory(".")
            .load();

    private final String unsplashApiKey = dotenv.get("UNSPLASH_API_KEY");

    private final ImageController imageController;
    private final ImageViewModel imageViewModel;
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

    public MindMapView(CardLayout cardLayout, Container cardPanel, ImageController imageController,
                       ImageViewModel imageViewModel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.imageController = imageController;
        this.imageViewModel = imageViewModel;

        setLayout(new BorderLayout());

        // Header label
        final JLabel titleLabel = new JLabel("Mind Map", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, twentyFour));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.NORTH);

        // Main center panel for the board area
        boardPanel = new JPanel();
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

        saveButton.addActionListener(evt -> {
            try {
                // Capture the JPanel as a BufferedImage
                BufferedImage screenshot = new BufferedImage(
                        boardPanel.getWidth(),
                        boardPanel.getHeight(),
                        BufferedImage.TYPE_INT_RGB
                );
                Graphics2D g2d = screenshot.createGraphics();
                boardPanel.paint(g2d);
                g2d.dispose();

                // Open a file chooser to save the screenshot
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Mind Map");

                // Add a file filter for PNG, JPEG, and PDF
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Image (*.png)", "png"));
                fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JPEG Image (*.jpg)", "jpg"));
                fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Document (*.pdf)", "pdf"));

                int userSelection = fileChooser.showSaveDialog(MindMapView.this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    java.io.File fileToSave = fileChooser.getSelectedFile();
                    String selectedExtension = "";

                    // Determine the selected file format
                    String description = fileChooser.getFileFilter().getDescription();
                    if (description.contains("PNG")) {
                        selectedExtension = "png";
                    } else if (description.contains("JPEG")) {
                        selectedExtension = "jpg";
                    } else if (description.contains("PDF")) {
                        selectedExtension = "pdf";
                    }

                    // Ensure the file has the correct extension
                    if (!fileToSave.getName().toLowerCase().endsWith("." + selectedExtension)) {
                        fileToSave = new java.io.File(fileToSave.getAbsolutePath() + "." + selectedExtension);
                    }

                    // Save the file in the selected format
                    if (selectedExtension.equals("png") || selectedExtension.equals("jpg")) {
                        // Write image as PNG or JPEG
                        ImageIO.write(screenshot, selectedExtension, fileToSave);
                    } else if (selectedExtension.equals("pdf")) {
                        // Save as PDF
                        saveAsPdf(screenshot, fileToSave);
                    }

                    JOptionPane.showMessageDialog(MindMapView.this, "Mind Map saved successfully as " + selectedExtension.toUpperCase() + "!");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(MindMapView.this, "Error saving Mind Map: " + e.getMessage());
                e.printStackTrace();
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

    /**
     * Fetch images from Unsplash and add one to the board.
     */
    private void fetchAndAddImage() {
        final String query = JOptionPane.showInputDialog(this, "Enter a search term for images:");
        if (query == null || query.isEmpty()) {
            // User cancelled or something
            return;
        }

        // Create an instance of ImagePresenter (which implements ImageOutputBoundary)
        final ImagePresenter imagePresenter = new ImagePresenter(imageViewModel);

        // Call the ImageController with the query and the ImagePresenter as the output boundary
        imageController.fetchImages(query, imagePresenter);
    }

    /**
     * Helper method to download the JFrame mindMap as a PDF
     */
    private void saveAsPdf(BufferedImage image, java.io.File file) {
        try {
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream(file));
            document.open();

            com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(image, null);
            pdfImage.scaleToFit(document.getPageSize().getWidth() - 50, document.getPageSize().getHeight() - 50);
            pdfImage.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);

            document.add(pdfImage);
            document.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving PDF: " + e.getMessage());
            e.printStackTrace();
        }
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
        // Icons for buttons
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
                });
                imagePanel.add(imageButton);
            }
            catch (IOException e) {
                System.err.println("Error loading image: " + commonImage.getUrl());
                e.printStackTrace();
            }
        }

        // Scrolling
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
    private void addImageToBoard(CommonImage commonImage) {
        final ImageIcon icon = new ImageIcon(new ImageIcon(commonImage.getUrl()).getImage()
                .getScaledInstance(hundredFifty, hundredFifty, java.awt.Image.SCALE_SMOOTH));
        final JLabel imageLabel = new JLabel(icon);
        // Example positioning
        imageLabel.setBounds(fifty, fifty, hundredFifty, hundredFifty);
        boardPanel.add(imageLabel);
        boardPanel.revalidate();
        boardPanel.repaint();
    }
}
