package comigue.com.br.comigue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comigue.com.br.comigue.consumer.PlanoDeEnsinoConsumer;
import comigue.com.br.comigue.pojo.Assunto;
import comigue.com.br.comigue.pojo.Materia;
import comigue.com.br.comigue.pojo.PlanoDeEnsino;
import comigue.com.br.comigue.pojo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 10/11/2017.
 */

public class PlanoDeEnsinoActivity extends Activity{

    private List<Assunto> assuntos;
    private Materia materia;
    private Usuario usuario;
    private PlanoDeEnsino planoDeEnsino;
    private ListView listaAssuntos;
    private ListaAssuntosAdapter listaAssuntosAdapter;
    private TextView nomeM, nomeP;
    private PlanoDeEnsinoConsumer planoDeEnsinoConsumer;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plano_de_ensino);
        inicializaComponentes();
    }

    public void inicializaComponentes(){
        usuario = (Usuario)getIntent().getExtras().getSerializable("usuario");
        materia = (Materia)getIntent().getExtras().getSerializable("materia");

        listaAssuntos = (ListView) findViewById(R.id.plano_lista_assuntos);
        nomeM = (TextView) findViewById(R.id.plano_nome_mat);
        nomeP= (TextView) findViewById(R.id.plano_nome_prof);

        assuntos = new ArrayList<>();

        planoDeEnsinoConsumer = new PlanoDeEnsinoConsumer();

        Call<PlanoDeEnsino> call = planoDeEnsinoConsumer.buscaPorMateria(materia.getCodMateria());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Carregando");
        progressDoalog.setTitle("Conectando com servidor");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<PlanoDeEnsino>() {
            @Override
            public void onResponse(Call<PlanoDeEnsino> call, Response<PlanoDeEnsino> response) {
                planoDeEnsino = response.body();
                if(planoDeEnsino == null){
                    Toast.makeText(PlanoDeEnsinoActivity.this, "Não foi possível carregar o plano", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                    return;
                } else {
                    assuntos = planoDeEnsino.getAssuntos();

                    for(Assunto a: assuntos){
                        Log.i(a.getNome(), "onResponse: ");
                    }

                    nomeM.setText(materia.getNome());
                    nomeP.setText(planoDeEnsino.getProfessor());

                    listaAssuntosAdapter = new ListaAssuntosAdapter(PlanoDeEnsinoActivity.this, assuntos, false);
                    listaAssuntos.setAdapter(listaAssuntosAdapter);

                    progressDoalog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<PlanoDeEnsino> call, Throwable t) {
                Toast.makeText(PlanoDeEnsinoActivity.this, "Plano de Ensino não foi encontrado", Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });


    }

    @Override
    public void onBackPressed(){
        retornarInicio(new Button(this));
    }

    public void retornarInicio(View v){
        Intent intent = new Intent(this, InicioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);
        bundle.putSerializable("materia", materia);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
