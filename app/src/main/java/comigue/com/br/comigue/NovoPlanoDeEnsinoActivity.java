package comigue.com.br.comigue;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comigue.com.br.comigue.consumer.ConviteConsumer;
import comigue.com.br.comigue.consumer.MateriaConsumer;
import comigue.com.br.comigue.pojo.Anotacao;
import comigue.com.br.comigue.pojo.Assunto;
import comigue.com.br.comigue.pojo.Materia;
import comigue.com.br.comigue.pojo.PlanoDeEnsino;
import comigue.com.br.comigue.pojo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 08/11/2017.
 */

public class NovoPlanoDeEnsinoActivity extends Activity implements DatePickerDialog.OnDateSetListener{

    private EditText nomeProf;
    private List<Assunto> assuntos;
    private Materia materia;
    private Usuario usuario;
    private PlanoDeEnsino planoDeEnsino;
    private ListView listaAssuntos;
    private ListaAssuntosAdapter listaAssuntosAdapter;
    private boolean ativoDI;
    private TextView dataFim, dataInicio;
    private View mView;
    private AlertDialog dialogAssunto;
    private MateriaConsumer materiaConsumer;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_plano);
        inicializaComponentes();
    }

    public void inicializaComponentes(){
        usuario = (Usuario)getIntent().getExtras().getSerializable("usuario");
        mView = getLayoutInflater().inflate(R.layout.assunto_dialog, null);

        nomeProf = (EditText) findViewById(R.id.novo_plano_prof);
        listaAssuntos = (ListView) findViewById(R.id.lista_assuntos);
        dataFim = (TextView) mView.findViewById(R.id.nova_data_fim_ass);
        dataInicio= (TextView) mView.findViewById(R.id.nova_data_inicio_ass);

        assuntos = new ArrayList<>();

        listaAssuntosAdapter = new ListaAssuntosAdapter(this, assuntos, true);
        listaAssuntos.setAdapter(listaAssuntosAdapter);

        listaAssuntos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Assunto assunto = (Assunto) parent.getItemAtPosition(position);
                assuntos.remove(assunto);
                listaAssuntosAdapter.notifyDataSetChanged();
                return true;
            }
        });


    }

    public void addAssunto(View v){

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mView = getLayoutInflater().inflate(R.layout.assunto_dialog, null);
        dataFim = (TextView) mView.findViewById(R.id.nova_data_fim_ass);
        dataInicio= (TextView) mView.findViewById(R.id.nova_data_inicio_ass);

        final EditText nomeAss = (EditText) mView.findViewById(R.id.novo_assunto_nome);
        final EditText descAss = (EditText) mView.findViewById(R.id.novo_assunto_desc);
        Button btCad = (Button) mView.findViewById(R.id.bnt_assunto_cad);

        btCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nomeAss.getText().toString().isEmpty() && !descAss.getText().toString().isEmpty() &&
                        !dataInicio.getText().toString().equals("Data Início") && !dataFim.getText().toString().equals("Data Fim")){

                    Assunto assunto = new Assunto();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try{
                        Date di = (Date) sdf.parse(dataInicio.getText().toString());
                        Date df = (Date) sdf.parse(dataFim.getText().toString());

                        assunto.setDataFim(df);
                        assunto.setDataInicio(di);

                    } catch (Exception e){
                        Toast.makeText(NovoPlanoDeEnsinoActivity.this, "Ocorreu um erro ao criar o assunto", Toast.LENGTH_SHORT).show();
                    }

                    assunto.setNome(nomeAss.getText().toString());
                    assunto.setDescricao(descAss.getText().toString());

                    assuntos.add(assunto);
                    listaAssuntosAdapter.notifyDataSetChanged();

//                    listaAssuntosAdapter = new ListaAssuntosAdapter(NovoPlanoDeEnsinoActivity.this, assuntos);
//                    listaAssuntos.setAdapter(listaAssuntosAdapter);

                    Toast.makeText(NovoPlanoDeEnsinoActivity.this, "Era para atualizar a lista", Toast.LENGTH_SHORT).show();

                    dialogAssunto.dismiss();
                } else {
                    Toast.makeText(NovoPlanoDeEnsinoActivity.this, "Preencha todos os campos do assunto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBuilder.setView(mView);
        dialogAssunto = mBuilder.create();
        dialogAssunto.show();

    }

    public void finalizarCadastroMateria(View v){
        if(camposPreenchidos()){
//            Toast.makeText(this, "Cadastraia a materia agora", Toast.LENGTH_SHORT).show();

            planoDeEnsino = new PlanoDeEnsino();
            planoDeEnsino.setProfessor(nomeProf.getText().toString());
            planoDeEnsino.setAssuntos(assuntos);

            materia = (Materia) getIntent().getExtras().getSerializable("materia");

            materia.setPlanoDeEnsino(planoDeEnsino);

            Anotacao anotacaoBase = new Anotacao();
            anotacaoBase.setTexto("Resuma a materia aqui...");
            anotacaoBase.setTitulo("Resumo de "+materia.getNome());

            materia.setAnotacoes(anotacaoBase);

            materiaConsumer = new MateriaConsumer();
            Call<Materia> call = materiaConsumer.postCadastrar(materia);

            call.enqueue(new Callback<Materia>() {
                @Override
                public void onResponse(Call<Materia> call, Response<Materia> response) {
                    Toast.makeText(NovoPlanoDeEnsinoActivity.this, "Cadstrou tudo eu acho", Toast.LENGTH_SHORT).show();
                    Log.i(response.code()+" "+ call.request().toString(), "onResponse: ");
                    materia = response.body();
                    ConviteConsumer.convidarUsuarioAceito(usuario, materia);

                    Bundle b = new Bundle();
                    b.putSerializable("materia", materia);
                    b.putSerializable("usuario", usuario);
                    Intent intent = new Intent(NovoPlanoDeEnsinoActivity.this, InicioActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<Materia> call, Throwable t) {
                    Toast.makeText(NovoPlanoDeEnsinoActivity.this, "Não deu", Toast.LENGTH_SHORT).show();
                    Log.e(t.getMessage(), "onFailure: " );
                    t.printStackTrace();
                }
            });

        } else {
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean camposPreenchidos(){
        boolean nomeP = !nomeProf.getText().toString().isEmpty();
        boolean assuntP = !assuntos.isEmpty();

        return nomeP && assuntP;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        Date d = c.getTime();

        if(ativoDI){
            dataInicio.setText(sdf.format(d));
        } else {
            dataFim.setText(sdf.format(d));
        }
    }

    public void escolherData(View v){
        Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(NovoPlanoDeEnsinoActivity.this, NovoPlanoDeEnsinoActivity.this, ano, mes, dia);
        datePickerDialog.show();

        if(v.getId() == R.id.nova_data_inicio_ass){
            ativoDI = true;
        } else {
            if(v.getId() == R.id.nova_data_fim_ass){
                ativoDI = false;
            }
        }
    }

    public void removerAssunto(View v){
        Toast.makeText(this, "Toque e segure para excluir", Toast.LENGTH_SHORT).show();
    }
}
