package hdimagesearch.gui.events;

/**
 * Created by mario on 05.07.15.
 */
public class Event
{
    private EventTypes type;
    private Object data;

    public Event(EventTypes type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Event(EventTypes type) {
        this(type, null);
    }

    public EventTypes getType() {
        return type;
    }

    public void setType(EventTypes type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
