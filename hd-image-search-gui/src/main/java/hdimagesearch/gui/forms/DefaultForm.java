package hdimagesearch.gui.forms;

import hdimagesearch.gui.events.Event;

import java.util.Observable;

/**
 * Created by mario on 05.07.15.
 */
public abstract class DefaultForm extends Observable
{
    protected void dispatchEvent (Event event) {
        setChanged();
        notifyObservers(event);
    }
}
