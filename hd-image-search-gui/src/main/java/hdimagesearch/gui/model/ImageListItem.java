package hdimagesearch.gui.model;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by mario on 07.07.15.
 */
public class ImageListItem {

    private String filename;
    private BufferedImage image;

    public ImageListItem(String filename, BufferedImage image) {
        this.filename = filename;
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return this.filename;
    }
}
