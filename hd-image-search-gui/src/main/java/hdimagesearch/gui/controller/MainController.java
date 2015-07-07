package hdimagesearch.gui.controller;

import hdimagesearch.core.FeatureExtract;
import hdimagesearch.gui.events.Event;
import hdimagesearch.gui.forms.MainForm;

import java.util.Observable;

/**
 * Created by mario on 05.07.15.
 */
public class MainController extends Controller
{
    public MainController(MainForm mainForm)
    {
        super(mainForm);
    }

    private void startSearch ()
    {
        // HACK
        try {
            System.out.println("Start extracting features!");
            FeatureExtract.main(new String[] {"/images/png2", "/index-gui"});
            System.out.println("Features extracted!");
        }
        catch (Exception ex) {
            System.err.print(ex);
        }


    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof Event) {
            Event event = (Event) o;

            if (event.getType().equals("start"))
            {
                this.startSearch();
            }
        }
    }
}
