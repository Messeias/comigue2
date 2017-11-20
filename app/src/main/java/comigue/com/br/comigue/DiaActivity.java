package comigue.com.br.comigue;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import comigue.com.br.comigue.consumer.TarefaConsumer;
import comigue.com.br.comigue.pojo.Materia;
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
    private Materia materia;
    SimpleDateFormat formatter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarefas_dia);
        this.usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        this.materia = (Materia) getIntent().getExtras().getSerializable("materia");
        this.dia = new Date(getIntent().getExtras().getLong("data"));


        this.formatter = new SimpleDateFormat("yyyy-MM-dd");
        Log.i(formatter.format(dia), "dia quando entra no dia acticity: ");
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

        if(materia == null) {

            tarefaConsumer.buscarPorData(formatter.format(dia), idUsuario).enqueue(new Callback<List<Tarefa>>() {
                @Override
                public void onResponse(Call<List<Tarefa>> call, Response<List<Tarefa>> response) {
                    Log.i(call.request().toString(), "onResponse: ");
                    DiaActivity.this.tarefas = response.body();
                    if (DiaActivity.this.tarefas != null) {
                        for (Tarefa m : DiaActivity.this.tarefas) {
                            Log.e("" + m.getNome(), "onResponse: ");
                        }

                        DiaActivity.this.listaTarefasAdapter = new ListaTarefasAdapter(DiaActivity.this, DiaActivity.this.tarefas);
                        DiaActivity.this.listaTarefas.setAdapter(listaTarefasAdapter);

                        listaTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Tarefa tar = (Tarefa) adapterView.getItemAtPosition(i);
                                Bundle b = new Bundle();
                                b.putSerializable("tarefa", tar);
                                b.putSerializable("usuario", usuario);
                                b.putSerializable("materia", materia);
                                b.putLong("data", dia.getTime());
                                Intent iEditar = new Intent(DiaActivity.this, NovaTarefaActivity.class);
                                iEditar.putExtras(b);
                                startActivity(iEditar);
                                finish();
                            }
                        });

                        listaTarefasAdapter.notifyDataSetChanged();

                        listaTarefas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                                new AlertDialog.Builder(DiaActivity.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Excluir tarefa")
                                        .setMessage("Você deseja excluir esta tarefa?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Excluir
                                                Tarefa tarExc = (Tarefa) parent.getItemAtPosition(position);
                                                Call<Void> callExcl = tarefaConsumer.deletePorId(tarExc.getCodTarefa());
                                                callExcl.enqueue(new Callback<Void>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                        Toast.makeText(DiaActivity.this, "Tarefa excluida com sucesso", Toast.LENGTH_SHORT).show();
                                                        buscarTarefas();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Void> call, Throwable t) {
                                                        Toast.makeText(DiaActivity.this, "Erro ao excluir a tarefa", Toast.LENGTH_SHORT).show();
                                                        t.printStackTrace();
                                                        Log.i(t.toString(), "onFailure: ");
                                                    }
                                                });
                                            }

                                        })
                                        .setNegativeButton("No", null)
                                        .show();

                                listaTarefasAdapter.notifyDataSetChanged();
                                return false;
                            }
                        });
                    } else {
                        Log.i("deu ruim ", "onResponse: ");
                    }

                    listaTarefasAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Tarefa>> call, Throwable t) {
                    Log.e("não deu ", "onFailure: ");
                }
            });

        } else {

            tarefaConsumer.buscarPorDataMateria(formatter.format(dia), materia.getCodMateria()).enqueue(new Callback<List<Tarefa>>() {
                @Override
                public void onResponse(Call<List<Tarefa>> call, Response<List<Tarefa>> response) {
                    Log.i(call.request().toString(), "onResponse: ");
                    DiaActivity.this.tarefas = response.body();
                    if (DiaActivity.this.tarefas != null) {
                        for (Tarefa m : DiaActivity.this.tarefas) {
                            Log.e("" + m.getNome(), "onResponse: ");
                        }

                        DiaActivity.this.listaTarefasAdapter = new ListaTarefasAdapter(DiaActivity.this, DiaActivity.this.tarefas);
                        DiaActivity.this.listaTarefas.setAdapter(listaTarefasAdapter);
                        listaTarefasAdapter.notifyDataSetChanged();
                    } else {
                        Log.i("deu ruim ", "onResponse: ");
                    }
                }

                @Override
                public void onFailure(Call<List<Tarefa>> call, Throwable t) {
                    Log.e("não deu ", "onFailure: ");
                }
            });

        }
    }

    public void criarTarefa(View v){
        Bundle b = new Bundle();
        b.putSerializable("usuario", usuario);
        b.putSerializable("materia", materia);
        b.putLong("data", dia.getTime());
        Intent i = new Intent(this, NovaTarefaActivity.class);
        i.putExtras(b);
        startActivity(i);
        finish();
    }

    public void retornarCalendario(View v){
        Bundle b = new Bundle();
        b.putSerializable("usuario", usuario);
        b.putSerializable("materia", materia);
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
