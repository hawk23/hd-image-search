package hdimagesearch.gui.controller;

import hdimagesearch.gui.forms.DefaultForm;

import java.util.Observer;

/**
 * Created by mario on 05.07.15.
 */
public abstract class Controller implements Observer
{
    protected DefaultForm form;

    public Controller(DefaultForm form)
    {
        this.form = form;
        this.form.addObserver(this);
    }
}
