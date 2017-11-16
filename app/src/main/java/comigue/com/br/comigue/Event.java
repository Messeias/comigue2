package comigue.com.br.comigue;

import java.util.Date;

/**
 * Created by Usuario on 29/10/2017.
 */

public class Event implements Comparable<Event> {

    private Date date;
    private int color;

    public Event(Date date, int color) {
        this.date = date;
        this.color = color;
    }

    public Date getDate() {
        return date;
    }

    public int getColor() {
        return color;
    }

    @Override
    public int compareTo(Event o) {
        return new Integer(this.color).compareTo(new Integer(o.color));
    }
}