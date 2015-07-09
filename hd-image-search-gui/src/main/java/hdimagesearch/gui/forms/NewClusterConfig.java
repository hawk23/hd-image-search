package hdimagesearch.gui.forms;

import hdimagesearch.gui.model.ClusterConfiguration;

import javax.swing.*;
import java.awt.event.*;

public class NewClusterConfig extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtFsDefaultName;
    private JTextField txtJobTracker;
    private JTextField txtImagesFolder;
    private JTextField txtIndexFolder;
    private JTextField txtSearchResultFolder;

    private ClusterConfiguration clusterConfiguration = null;

    public NewClusterConfig() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {

        String fsDefaultName = this.txtFsDefaultName.getText();
        String mapredJobTracker = this.txtJobTracker.getText();
        String imagesFolder = this.txtImagesFolder.getText();
        String indexFolder = this.txtIndexFolder.getText();
        String searchResultFolder = this.txtSearchResultFolder.getText();

        this.clusterConfiguration = new ClusterConfiguration(
                fsDefaultName,
                mapredJobTracker,
                imagesFolder,
                indexFolder,
                searchResultFolder
        );

        dispose();
    }

    private void onCancel() {
        this.clusterConfiguration = null;

        dispose();
    }

    public static void main(String[] args) {
        NewClusterConfig dialog = new NewClusterConfig();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public ClusterConfiguration getClusterConfiguration() {
        return clusterConfiguration;
    }
}
