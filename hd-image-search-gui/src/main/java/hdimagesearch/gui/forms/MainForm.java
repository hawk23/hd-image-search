package hdimagesearch.gui.forms;

import hdimagesearch.gui.events.Event;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mario on 05.07.15.
 */
public class MainForm extends DefaultForm
{
    private JPanel pnlMain;
    private JSplitPane pnlTop;
    private JSplitPane pnlSearchMain;
    private JTabbedPane pnlTab;
    private JLabel lblCluster;
    private JComboBox cbCluster;
    private JLabel lblInputImage;
    private JTextField txtInputImage;
    private JButton btnSelectInput;
    private JLabel lblNumResults;
    private JSpinner spinner1;
    private JButton btnStart;
    private JLabel imgSample;

    public MainForm() {
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Event event = new Event("start", null);
                setChanged();
                notifyObservers(event);
            }
        });
    }
}
