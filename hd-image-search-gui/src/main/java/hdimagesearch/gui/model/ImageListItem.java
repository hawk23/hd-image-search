package hdimagesearch.gui.model;

import java.util.Arrays;

/**
 * Created by mario on 07.07.15.
 */
public class ImageListItem {

    private String filename;
    private byte[] image;

    public ImageListItem(String filename, byte[] image) {
        this.filename = filename;
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return this.filename;
    }
}
