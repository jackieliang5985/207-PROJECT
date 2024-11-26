package view;

import interface_adapter.add_Image_PostNote.ImagePostNoteController;
import entity.CommonImage;
import entity.PostNote;
import interface_adapter.create_MindMap.SquarePanel;
import interface_adapter.export_mind_map.ExportController;
import interface_adapter.image.ImageController;
import interface_adapter.image.ImagePresenter;
import interface_adapter.image.ImageViewModel;
import interface_adapter.add_Image_PostNote.ImagePostNotePresenter;
import io.github.cdimascio.dotenv.Dotenv;
import use_case.add_Image_PostNote.ImagePostNoteInteractor;
import use_case.export_mind_map.ExportInputData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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

    // Controller for ImagePostNotes
    private final ImagePostNoteController imagePostNoteController;

    // Load the API key from the .env file
    private final Dotenv dotenv = Dotenv.configure().directory(".").load();
    private final String unsplashApiKey = dotenv.get("UNSPLASH_API_KEY");

    private final ImageController imageController;
    private final ImageViewModel imageViewModel;

    private final ExportController exportController;

    private final ArrayList<PostNote> postNotes = new ArrayList<>();

    public MindMapView(CardLayout cardLayout, Container cardPanel, ImageController imageController,
                       ImageViewModel imageViewModel, ExportController exportController) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.imageController = imageController;
        this.imageViewModel = imageViewModel;
        this.exportController = exportController;

        setLayout(new BorderLayout());

        // Create the board panel
        boardPanel = new SquarePanel(postNotes);
        boardPanel.setBackground(Color.LIGHT_GRAY);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        boardPanel.setLayout(null);
        add(boardPanel, BorderLayout.CENTER);

        // Initialize CA Components
        ImagePostNotePresenter presenter = new ImagePostNotePresenter(boardPanel);
        ImagePostNoteInteractor interactor = new ImagePostNoteInteractor(presenter);
        imagePostNoteController = new ImagePostNoteController(interactor);

        // Set up UI
        setupUI();
    }

    private void setupUI() {
        final JLabel titleLabel = new JLabel("Mind Map", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.NORTH);

        // Bottom panel for buttons
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0));
        bottomPanel.setBackground(new Color(230, 230, 250));

        final JButton addTextPostButton = createStyledButton("Add Text Post It");
        final JButton addImageButton = createStyledButton("Add Image Post It");
        final JButton attachStringButton = createStyledButton("Attach String");
        final JButton saveButton = createStyledButton("SAVE");
        final JButton logoutButton = createStyledButton("LOGOUT");

        // Add listeners
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

        // Listen for image updates
        imageViewModel.addPropertyChangeListener(evt -> {
            if ("images".equals(evt.getPropertyName())) {
                final List<CommonImage> images = (List<CommonImage>) evt.getNewValue();
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
        final ImagePresenter imagePresenter = new ImagePresenter(imageViewModel);
        imageController.fetchImages(query, imagePresenter);
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

    private void showImageSelectionDialog(List<CommonImage> commonImages) {
        final JDialog dialog = new JDialog((Frame) null, "Select an Image", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());

        final JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(0, 3, 10, 10));

        final CommonImage[] selectedCommonImage = {null};
        for (CommonImage commonImage : commonImages) {
            try {
                BufferedImage bufferedImage = ImageIO.read(new URL(commonImage.getUrl()));
                if (bufferedImage == null) {
                    System.err.println("Failed to fetch image: " + commonImage.getUrl());
                    continue;
                }
                final ImageIcon icon = new ImageIcon(bufferedImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                final JButton imageButton = new JButton(icon);
                imageButton.addActionListener(evt -> {
                    selectedCommonImage[0] = commonImage;
                    dialog.dispose();
                    addImageToBoard(selectedCommonImage[0]); // Pass to controller
                });
                imagePanel.add(imageButton);
            } catch (IOException exception) {
                System.err.println("Error loading image: " + commonImage.getUrl());
                exception.printStackTrace();
            }
        }

        final JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void addImageToBoard(CommonImage commonImage) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new URL(commonImage.getUrl()));
            if (bufferedImage == null) {
                JOptionPane.showMessageDialog(this, "Failed to load the selected image.");
                return;
            }
            ImageIcon imageIcon = new ImageIcon(bufferedImage);
            imagePostNoteController.addImagePostNote(imageIcon); // Delegate to controller
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this, "Error loading the image: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
}
