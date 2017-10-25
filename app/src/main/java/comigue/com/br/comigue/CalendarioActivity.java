package comigue.com.br.comigue;

import android.app.Activity;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

//import com.prolificinteractive.materialcalendarview.CalendarDay;
//import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
//import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import com.applandeo.materialcalendarview.CalendarView;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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

    private CalendarView calendario;
    private TarefaConsumer tarefaConsumer;
    private Usuario usuario;
    private List<Tarefa> tarefas;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario);
        this.usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");

        inicializaComponentes();
    }

    public void inicializaComponentes(){

        this.calendario = (CalendarView) findViewById(R.id.calendarView);
        this.tarefaConsumer = new TarefaConsumer();

        Call<List<Tarefa>> call = tarefaConsumer.buscarPorAluno(usuario.getCodUsuario());
        call.enqueue(new Callback<List<Tarefa>>() {
            @Override
            public void onResponse(Call<List<Tarefa>> call, Response<List<Tarefa>> response) {
                tarefas = response.body();
                if(tarefas != null && !tarefas.isEmpty()){
                    calendario.setEvents();
                }
            }

            @Override
            public void onFailure(Call<List<Tarefa>> call, Throwable t) {
                Toast.makeText(CalendarioActivity.this, "Não foi possivel carregar as tarefas", Toast.LENGTH_SHORT).show();
            }
        });

//        calendario.setOnDateChangedListener(new OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//
//            }
//        });
    }
}
