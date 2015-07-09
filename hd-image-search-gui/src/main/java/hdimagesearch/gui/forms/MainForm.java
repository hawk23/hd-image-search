package hdimagesearch.gui.forms;

import hdimagesearch.gui.events.Event;
import hdimagesearch.gui.events.EventTypes;

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
    private JButton btnExtractFeatures;
    private JList lstResult;
    private JTextArea txtLog;
    private JButton btnAdd;
    private JFileChooser fileChooser = new JFileChooser();


    public MainForm() {

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Event event = new Event(EventTypes.START_SEARCH);
                dispatchEvent(event);
            }
        });
        btnSelectInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Event event = new Event(EventTypes.SELECT_INPUT_IMAGE);
                dispatchEvent(event);
            }
        });
        btnExtractFeatures.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Event event = new Event(EventTypes.EXTRACT_FEATURES);
                dispatchEvent(event);
            }
        });
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Event event = new Event(EventTypes.ADD_CONFIG);
                dispatchEvent(event);
            }
        });
    }

    public JPanel getPnlMain() {
        return pnlMain;
    }

    public JSplitPane getPnlTop() {
        return pnlTop;
    }

    public JSplitPane getPnlSearchMain() {
        return pnlSearchMain;
    }

    public JTabbedPane getPnlTab() {
        return pnlTab;
    }

    public JLabel getLblCluster() {
        return lblCluster;
    }

    public JComboBox getCbCluster() {
        return cbCluster;
    }

    public JLabel getLblInputImage() {
        return lblInputImage;
    }

    public JTextField getTxtInputImage() {
        return txtInputImage;
    }

    public JButton getBtnSelectInput() {
        return btnSelectInput;
    }

    public JLabel getLblNumResults() {
        return lblNumResults;
    }

    public JSpinner getSpinner1() {
        return spinner1;
    }

    public JButton getBtnStart() {
        return btnStart;
    }

    public JLabel getImgSample() {
        return imgSample;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public JList getLstResult() {
        return lstResult;
    }

    public void setLstResult(JList lstResult) {
        this.lstResult = lstResult;
    }

    public JTextArea getTxtLog() {
        return txtLog;
    }

    public void setTxtLog(JTextArea txtLog) {
        this.txtLog = txtLog;
    }
}
