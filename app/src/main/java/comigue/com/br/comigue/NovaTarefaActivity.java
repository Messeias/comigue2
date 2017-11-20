package comigue.com.br.comigue;

import android.app.Activity;
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


        Call<Void> call = tConsumer.postCadastrar(t);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Toast.makeText(NovaTarefaActivity.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                Bundle bd = new Bundle();
                bd.putSerializable("usuario", usuario);
                bd.putLong("data", dataTarefa.getTime());
                Intent i = new Intent(NovaTarefaActivity.this, DiaActivity.class);
                i.putExtras(bd);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(NovaTarefaActivity.this, "Não foi possível conectar ao servidor", Toast.LENGTH_SHORT).show();
                Log.i(t.getMessage(), "onFailure: ");
                Log.i(t.toString(), "onFailure: ");
                Log.i(call.request().toString(), "onFailure: ");
                Log.i(t.getCause().toString(), "onFailure: ");
            }
        });

    }


    public void inicializaComponentes(){

        materiaExist = (Materia) getIntent().getExtras().getSerializable("materia");

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
    }

    public void inicializaDados(){
        mConsumer = new MateriaConsumer();
        tConsumer = new TarefaConsumer();
        usuario = (Usuario)getIntent().getExtras().getSerializable("usuario");
        materiaExist = (Materia) getIntent().getExtras().getSerializable("materia");

        dataTarefa = new Date((long)getIntent().getExtras().get("data"));

        etiquetaTarefa = 'm';

        if(materiaExist == null) {
            Call<List<Materia>> call = mConsumer.buscarPorUsuario(usuario.getCodUsuario());
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
                }

                @Override
                public void onFailure(Call<List<Materia>> call, Throwable t) {
                    Toast.makeText(NovaTarefaActivity.this, "Não foi possível carregar suas materias", Toast.LENGTH_SHORT).show();
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
        bd.putLong("data", dataTarefa.getTime());
        Intent i = new Intent(NovaTarefaActivity.this, DiaActivity.class);
        i.putExtras(bd);
        startActivity(i);
        finish();
    }
}
