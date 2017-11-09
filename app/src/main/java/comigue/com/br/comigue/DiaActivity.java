package comigue.com.br.comigue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import comigue.com.br.comigue.consumer.TarefaConsumer;
import comigue.com.br.comigue.pojo.Tarefa;
import comigue.com.br.comigue.pojo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 30/10/2017.
 */

public class DiaActivity extends Activity {

    private TarefaConsumer tarefaConsumer;
    private Usuario usuario;
    private TextView diaTarefas;
    private List<Tarefa> tarefas;
    private ListView listaTarefas;
    private ListaTarefasAdapter listaTarefasAdapter;
    private Date dia;
    SimpleDateFormat formatter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarefas_dia);
        this.usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        this.dia = new Date(getIntent().getExtras().getLong("data"));


        this.formatter = new SimpleDateFormat("yyyy-MM-dd");
        Log.i(formatter.format(dia), "onCreate: ");
        Log.i(usuario.getCodUsuario() + " cod ", "onCreate: ");

        inicializaComponentes();
        buscarTarefas();
    }

    public void inicializaComponentes(){
        diaTarefas = (TextView) findViewById(R.id.dia_tarefas);
        this.diaTarefas.setText("Dia " + new SimpleDateFormat("dd/MM/yyyy").format(dia));

        listaTarefas = (ListView) findViewById(R.id.lista_tarefas);
        this.tarefaConsumer = new TarefaConsumer();
    }

    public void buscarTarefas(){
        Bundle bundle = getIntent().getExtras();
        usuario = (Usuario) bundle.getSerializable("usuario");
        Long idUsuario = usuario.getCodUsuario();

        tarefaConsumer.buscarPorData(formatter.format(dia), idUsuario).enqueue(new Callback<List<Tarefa>>() {
            @Override
            public void onResponse(Call<List<Tarefa>> call, Response<List<Tarefa>> response) {
                Log.i(call.request().toString(), "onResponse: ");
                DiaActivity.this.tarefas = response.body();
                if(DiaActivity.this.tarefas != null) {
                    for (Tarefa m : DiaActivity.this.tarefas) {
                        Log.e("" + m.getNome(), "onResponse: ");
                    }

                    DiaActivity.this.listaTarefasAdapter = new ListaTarefasAdapter(DiaActivity.this, DiaActivity.this.tarefas);
                    DiaActivity.this.listaTarefas.setAdapter(listaTarefasAdapter);
                } else {
                    Log.i("deu ruim ", "onResponse: ");
                }
            }

            @Override
            public void onFailure(Call<List<Tarefa>> call, Throwable t) {
                Log.e("não deu ", "onFailure: " );
            }
        });
    }

    public void criarTarefa(View v){
        Bundle b = new Bundle();
        b.putSerializable("usuario", usuario);
        b.putLong("data", dia.getTime());
        Intent i = new Intent(this, NovaTarefaActivity.class);
        i.putExtras(b);
        startActivity(i);
        finish();
    }

    public void retornarCalendario(View v){
        Bundle b = new Bundle();
        b.putSerializable("usuario", usuario);
        Intent i = new Intent(this, CalendarioActivity.class);
        i.putExtras(b);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed(){
        retornarCalendario(findViewById(R.id.btn_voltar));
    }
}
