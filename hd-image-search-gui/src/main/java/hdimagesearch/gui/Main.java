package hdimagesearch.gui;

import hdimagesearch.gui.controller.MainController;
import hdimagesearch.gui.forms.MainDialog;

import javax.swing.*;

/**
 * Created by mario on 05.07.15.
 */
public class Main
{
    public static void main(String[] args) throws Exception
    {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        MainDialog dialog = new MainDialog();
        dialog.pack();

        // Create hdimagesearch.gui.controller
        MainController mainController = new MainController(dialog.getFrmMain());

        dialog.setVisible(true);
        System.exit(0);
    }
}
