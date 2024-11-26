package entity;

import java.awt.*;

import javax.swing.ImageIcon;

public class ImagePostNoteEntity extends PostNoteEntity {
    private ImageIcon imageIcon;

    public ImagePostNoteEntity(int x, int y, int width, int height, MindMapEntity mindMap) {
        super(x, y, width, height, mindMap);
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        if (imageIcon != null) {
            setWidth(imageIcon.getIconWidth());   // Use the protected setter
            setHeight(imageIcon.getIconHeight()); // Use the protected setter
        }
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

}
