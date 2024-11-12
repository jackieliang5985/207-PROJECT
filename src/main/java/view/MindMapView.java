package view;

import javax.swing.*;
import java.awt.*;

/**
 * A view class for the Mind Map screen.
 */
public class MindMapView extends JPanel {
    // Define a constant for identifying this view in the CardLayout
    public static final String VIEW_NAME = "MindMapView";

    public MindMapView() {
        setLayout(new BorderLayout());

        // Add a simple label to indicate this is the MindMap view
        JLabel label = new JLabel("Mind Map View", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 24));

        // Add the label to the center of the panel
        add(label, BorderLayout.CENTER);
    }
    public String getViewName() {
        return "MindMapView";
    }
}
