package comigue.com.br.comigue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comigue.com.br.comigue.consumer.MateriaConsumer;
import comigue.com.br.comigue.consumer.TarefaConsumer;
import comigue.com.br.comigue.consumer.UsuarioConsumer;
import comigue.com.br.comigue.pojo.Materia;
import comigue.com.br.comigue.pojo.Tarefa;
import comigue.com.br.comigue.pojo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 31/10/2017.
 */

public class NovaTarefaActivity extends Activity {

    private EditText nomeTarefa, pesoTarefa, descTarefa;
    private TextView mostraData;
    private List<Materia> materiasU;
    private Usuario usuario;
    private Materia materiaExist;
    private UsuarioConsumer uConsumer;
    private MateriaConsumer mConsumer;
    private TarefaConsumer tConsumer;
    private RadioGroup etiqueta;
    private char etiquetaTarefa;
    private Date dataTarefa;
    private Spinner spinner;
    private Tarefa tarefaEditar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_tarefa);
        inicializaDados();
        inicializaComponentes();
    }

    public void criarTarefa(View v){

        Tarefa t = new Tarefa();
        t.setDataEntrega(dataTarefa);
        t.setNome(nomeTarefa.getText().toString());
        t.setDescricao(descTarefa.getText().toString());

        if(!pesoTarefa.getText().toString().isEmpty())
        t.setPeso(Double.parseDouble(pesoTarefa.getText().toString()));

        Materia mat = (Materia) spinner.getSelectedItem();

        t.setEtiqueta(etiquetaTarefa);
        t.setMateria(mat);
        t.setUsuario(usuario);

        Log.i(dataTarefa.toString(), "quando ele vai criar: ");

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Carregando");
        progressDoalog.setTitle("Conectando com servidor");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // show it
        progressDoalog.show();

        if(tarefaEditar==null) {
            Call<Void> call = tConsumer.postCadastrar(t);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    Toast.makeText(NovaTarefaActivity.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    Bundle bd = new Bundle();
                    bd.putSerializable("usuario", usuario);
                    bd.putSerializable("materia", materiaExist);
                    bd.putLong("data", dataTarefa.getTime());
                    Intent i = new Intent(NovaTarefaActivity.this, DiaActivity.class);
                    i.putExtras(bd);
                    startActivity(i);
                    progressDoalog.dismiss();
                    finish();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(NovaTarefaActivity.this, "Não foi possível conectar ao servidor", Toast.LENGTH_SHORT).show();
                    Log.i(t.getMessage(), "onFailure: ");
                    Log.i(t.toString(), "onFailure: ");
                    Log.i(call.request().toString(), "onFailure: ");
                    Log.i(t.getCause().toString(), "onFailure: ");
                    progressDoalog.dismiss();
                }
            });
        } else {
            t.setCodTarefa(tarefaEditar.getCodTarefa());
            Call<Tarefa> call = tConsumer.putAtualizar(t);
            call.enqueue(new Callback<Tarefa>() {
                @Override
                public void onResponse(Call<Tarefa> call, Response<Tarefa> response) {
                    Toast.makeText(NovaTarefaActivity.this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
                    Bundle bd = new Bundle();
                    bd.putSerializable("usuario", usuario);
                    bd.putLong("data", dataTarefa.getTime());
                    Intent i = new Intent(NovaTarefaActivity.this, DiaActivity.class);
                    i.putExtras(bd);
                    startActivity(i);
                    progressDoalog.dismiss();
                    finish();
                }

                @Override
                public void onFailure(Call<Tarefa> call, Throwable t) {
                    Toast.makeText(NovaTarefaActivity.this, "Não foi possível conectar ao servidor", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    Log.i(t.toString(), "onFailure: ");
                    Log.i(call.request().toString(), "onFailure: ");
                    Log.i(t.getCause().toString(), "onFailure: ");
                    progressDoalog.dismiss();
                }
            });
        }
    }


    public void inicializaComponentes(){

        materiaExist = (Materia) getIntent().getExtras().getSerializable("materia");
        tarefaEditar = (Tarefa) getIntent().getExtras().getSerializable("tarefa");


        nomeTarefa = (EditText) findViewById(R.id.nova_tarefa_nome);
        pesoTarefa = (EditText) findViewById(R.id.nova_tarefa_peso);
        descTarefa = (EditText) findViewById(R.id.nova_tarefa_desc);
        mostraData = (TextView) findViewById(R.id.nova_tarefa_data);

        mostraData.setText(new SimpleDateFormat("dd/MM/yyyy").format(dataTarefa));

        etiqueta = (RadioGroup) findViewById(R.id.nova_tarefa_etiqueta);
        etiqueta.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton btn = (RadioButton) group.findViewById(checkedId);
                String t = btn.getText().toString();
                switch (t){
                    case "Difícil": etiquetaTarefa = 'd'; break;
                    case "Mediano": etiquetaTarefa = 'm'; break;
                    case "Fácil": etiquetaTarefa = 'f'; break;
                }
            }
        });

        if(tarefaEditar != null){
            this.nomeTarefa.setText(tarefaEditar.getNome());
            this.pesoTarefa.setText(tarefaEditar.getPeso()+"");
            this.descTarefa.setText(tarefaEditar.getDescricao());
            switch (tarefaEditar.getEtiqueta()){
                case 'f':
                    etiqueta.check(R.id.radioButtonFacil);
                    break;
                case 'm':
                    etiqueta.check(R.id.radioButtonMediano);
                    break;
                case 'd':
                    etiqueta.check(R.id.radioButtonDificil);
                    break;
            }
        }
    }

    public void inicializaDados(){
        mConsumer = new MateriaConsumer();
        tConsumer = new TarefaConsumer();
        usuario = (Usuario)getIntent().getExtras().getSerializable("usuario");
        materiaExist = (Materia) getIntent().getExtras().getSerializable("materia");
        tarefaEditar = (Tarefa) getIntent().getExtras().getSerializable("tarefa");

        dataTarefa = new Date((long)getIntent().getExtras().get("data"));

        etiquetaTarefa = 'm';



        if(materiaExist == null) {
            Call<List<Materia>> call = mConsumer.buscarPorUsuario(usuario.getCodUsuario());

            final ProgressDialog progressDoalog;
            progressDoalog = new ProgressDialog(this);
            progressDoalog.setMax(100);
            progressDoalog.setMessage("Carregando");
            progressDoalog.setTitle("Conectando com servidor");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // show it
            progressDoalog.show();

            call.enqueue(new Callback<List<Materia>>() {
                @Override
                public void onResponse(Call<List<Materia>> call, Response<List<Materia>> response) {
                    if (response.code() == 200) {
                        materiasU = response.body();
                        spinner = (Spinner) findViewById(R.id.nova_tarefa_materia);
                        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(NovaTarefaActivity.this, android.R.layout.simple_spinner_dropdown_item, materiasU);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                adapterView.getItemAtPosition(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    progressDoalog.dismiss();
                }

                @Override
                public void onFailure(Call<List<Materia>> call, Throwable t) {
                    Toast.makeText(NovaTarefaActivity.this, "Não foi possível carregar suas materias", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }
            });
        } else {

            materiasU = new ArrayList<>();
            materiasU.add(materiaExist);
            spinner = (Spinner) findViewById(R.id.nova_tarefa_materia);
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(NovaTarefaActivity.this, android.R.layout.simple_spinner_dropdown_item, materiasU);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    adapterView.getItemAtPosition(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    adapterView.getItemAtPosition(0);
                }
            });
        }

    }

    @Override
    public void onBackPressed(){
        Bundle bd = new Bundle();
        bd.putSerializable("usuario", usuario);
        bd.putSerializable("materia", materiaExist);
        bd.putLong("data", dataTarefa.getTime());
        Intent i = new Intent(NovaTarefaActivity.this, DiaActivity.class);
        i.putExtras(bd);
        startActivity(i);
        finish();
    }
}
