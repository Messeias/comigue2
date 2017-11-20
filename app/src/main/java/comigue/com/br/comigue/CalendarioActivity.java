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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comigue.com.br.comigue.consumer.TarefaConsumer;
import comigue.com.br.comigue.pojo.Materia;
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
    private Materia materia;
    private List<Tarefa> tarefas;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario);
        this.usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        this.materia = (Materia) getIntent().getExtras().getSerializable("materia");
//        adjustFontScale(getResources().getConfiguration());
        inicializaComponentes();
    }

    public void inicializaComponentes(){

        this.calendario = (MaterialCalendarView) findViewById(R.id.calendarView);
        this.tarefaConsumer = new TarefaConsumer();

        if(materia == null){
            Call<List<Tarefa>> call = tarefaConsumer.buscarPorAluno(usuario.getCodUsuario());
            call.enqueue(new Callback<List<Tarefa>>() {

                @Override
                public void onResponse(Call<List<Tarefa>> call, Response<List<Tarefa>> response) {
                    tarefas = response.body();
                    if(tarefas != null && !tarefas.isEmpty()){

                        Log.i("deu certo", "onResponse: "+tarefas.toString());

                        List<Event> events = new ArrayList<>();

                        for(Tarefa t : tarefas){
                            int df = 2;
                            switch (t.getEtiqueta()){
                                case 'f': df = 0; break;
                                case 'm': df = 1; break;
                                case 'd': df = 2; break;
                            }
                            events.add(new Event(t.getDataEntrega(), df));
                            Collections.sort(events);
                        }
                        decorarEventos(events);
                    } else {
                        Log.i("não deu", "onResponse: ");
                    }
                }

                @Override
                public void onFailure(Call<List<Tarefa>> call, Throwable t) {
                    Toast.makeText(CalendarioActivity.this, "Não foi possivel carregar as tarefas", Toast.LENGTH_SHORT).show();
//                Log.i("onFailure: ", t.getMessage() + "\n "+ t.getCause().toString());
                }
            });
        } else {

            Call<List<Tarefa>> callMatTarefas = tarefaConsumer.buscarPorMateria(materia.getCodMateria());
            callMatTarefas.enqueue(new Callback<List<Tarefa>>() {
                @Override
                public void onResponse(Call<List<Tarefa>> call, Response<List<Tarefa>> response) {
                    tarefas = response.body();
                    if(tarefas != null && !tarefas.isEmpty()){

                        Log.i("deu certo", "onResponse: "+tarefas.toString());

                        List<Event> events = new ArrayList<>();

                        for(Tarefa t : tarefas){
                            int df = 2;
                            switch (t.getEtiqueta()){
                                case 'f': df = 0; break;
                                case 'm': df = 1; break;
                                case 'd': df = 2; break;
                            }
                            events.add(new Event(t.getDataEntrega(), df));
                            Collections.sort(events);
                        }
                        decorarEventos(events);
                    } else {
                        Log.i("não deu", "onResponse: ");
                    }
                }

                @Override
                public void onFailure(Call<List<Tarefa>> call, Throwable t) {
                    Toast.makeText(CalendarioActivity.this, "Não foi possivel carregar as tarefas da materia", Toast.LENGTH_SHORT).show();
                }
            });

        }



        calendario.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Bundle b = new Bundle();
                b.putSerializable("usuario", CalendarioActivity.this.usuario);
                b.putSerializable("materia", CalendarioActivity.this.materia);
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
        retornarInicio(new Button(this));
    }

    public void retornarInicio(View v){
        Intent intent = new Intent(this, InicioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
