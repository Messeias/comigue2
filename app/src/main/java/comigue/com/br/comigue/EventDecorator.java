package comigue.com.br.comigue;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;

/**
 * Created by Usuario on 29/10/2017.
 */

public class EventDecorator implements DayViewDecorator {

    private final Drawable drawable;
    private final CalendarDay day;

    public EventDecorator(MaterialCalendarView view, Date date, int img) {
        this.day = CalendarDay.from(date);
        this.drawable = createBaseDrawable(view.getContext(), img);
//        view.setTileSize(100);

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (this.day.equals(day)) {
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }


    private static Drawable createBaseDrawable(Context context, int wich) {
        switch (wich){
            case 0:
                return ContextCompat.getDrawable(context, R.drawable.easy2);
            case 1:
                return ContextCompat.getDrawable(context, R.drawable.medium);
            case 2:
                return ContextCompat.getDrawable(context, R.drawable.hard);
        }
        return ContextCompat.getDrawable(context, R.drawable.hard);
    }
}