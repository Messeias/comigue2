package comigue.com.br.comigue;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
//import android.widget.CalendarView;
import android.view.WindowManager;
import android.widget.Toast;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.List;

import comigue.com.br.comigue.consumer.TarefaConsumer;
import comigue.com.br.comigue.pojo.Tarefa;
import comigue.com.br.comigue.pojo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 23/10/2017.
 */

public class CalendarioActivity extends Activity {

    private MaterialCalendarView calendario;
    private TarefaConsumer tarefaConsumer;
    private Usuario usuario;
    private List<Tarefa> tarefas;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario);
        this.usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
//        adjustFontScale(getResources().getConfiguration());
        inicializaComponentes();
    }

    public void inicializaComponentes(){

        this.calendario = (MaterialCalendarView) findViewById(R.id.calendarView);
        this.tarefaConsumer = new TarefaConsumer();

        Call<List<Tarefa>> call = tarefaConsumer.buscarPorAluno(usuario.getCodUsuario());
        call.enqueue(new Callback<List<Tarefa>>() {

            @Override
            public void onResponse(Call<List<Tarefa>> call, Response<List<Tarefa>> response) {
                tarefas = response.body();
                if(tarefas != null && !tarefas.isEmpty()){

                    Log.i("deu certo", "onResponse: "+tarefas.toString());

                    List<Event> events = new ArrayList<>();

                    for(Tarefa t : tarefas){
                        events.add(new Event(t.getDataEntrega(), Color.RED));
                    }
                    decorarEventos(events);
                } else {
                    Log.i("não deu", "onResponse: ");
                }
            }

            @Override
            public void onFailure(Call<List<Tarefa>> call, Throwable t) {
                Toast.makeText(CalendarioActivity.this, "Não foi possivel carregar as tarefas", Toast.LENGTH_SHORT).show();
                Log.i("onFailure: ", t.getMessage() + "\n "+ t.getCause().toString());
            }
        });

        calendario.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Bundle b = new Bundle();
                b.putSerializable("usuario", CalendarioActivity.this.usuario);
                b.putLong("data", date.getDate().getTime());
                Intent i = new Intent(CalendarioActivity.this, DiaActivity.class);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
        });

    }

    public void decorarEventos(List<Event> events){
        for (Event event : events) {
            EventDecorator eventDecorator = new EventDecorator(calendario, event.getDate(), event.getColor());
            calendario.addDecorator(eventDecorator);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(CalendarioActivity.this, InicioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

//    public void adjustFontScale(Configuration configuration) {
//        configuration.fontScale = (float) 2;
//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//        wm.getDefaultDisplay().getMetrics(metrics);
//        metrics.scaledDensity = configuration.fontScale * metrics.density;
//        getBaseContext().getResources().updateConfiguration(configuration, metrics);
//    }
}
