package hdimagesearch.gui.util;

import hdimagesearch.gui.model.ImageListItem;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mario on 07.07.15.
 */
public class ImageListRenderer extends DefaultListCellRenderer
{
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        ImageListItem imageListItem = (ImageListItem) value;

        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setIcon(new ImageIcon(imageListItem.getImage()));
        label.setHorizontalTextPosition(JLabel.RIGHT);
        return label;
    }
}
