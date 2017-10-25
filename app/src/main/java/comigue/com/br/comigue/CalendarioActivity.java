package comigue.com.br.comigue;

import android.app.Activity;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Usuario on 23/10/2017.
 */

public class CalendarioActivity extends Activity {

    MaterialCalendarView calendario;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario);


        inicializaComponentes();
    }

    public void inicializaComponentes(){

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        this.calendario = (MaterialCalendarView) findViewById(R.id.calendarView);

        calendario.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            }
        });


    }
}
