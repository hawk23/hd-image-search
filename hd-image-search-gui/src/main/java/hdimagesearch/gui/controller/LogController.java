package hdimagesearch.gui.controller;

import javax.swing.*;
import java.util.Observable;

/**
 * Created by mario on 07.07.15.
 */
public class LogController
{
    private JTextArea txtLog;

    public LogController(JTextArea txtLog)
    {
        this.txtLog = txtLog;
    }

    public void log (String message)
    {
        this.txtLog.append("[Log] "+ message +"\n");
    }

    public void error (String error)
    {
        this.txtLog.append("[Err] "+ error+"\n");
    }
}
