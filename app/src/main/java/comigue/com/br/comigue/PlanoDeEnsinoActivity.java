package comigue.com.br.comigue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comigue.com.br.comigue.pojo.Assunto;
import comigue.com.br.comigue.pojo.Materia;
import comigue.com.br.comigue.pojo.PlanoDeEnsino;
import comigue.com.br.comigue.pojo.Usuario;

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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plano_de_ensino);
        inicializaComponentes();
    }

    public void inicializaComponentes(){
        usuario = (Usuario)getIntent().getExtras().getSerializable("usuario");

        listaAssuntos = (ListView) findViewById(R.id.plano_lista_assuntos);
        nomeM = (TextView) findViewById(R.id.plano_nome_mat);
        nomeP= (TextView) findViewById(R.id.plano_nome_prof);

        assuntos = new ArrayList<>();

        Assunto a = new Assunto();
        a.setNome("Assunto teste 1");
        a.setDescricao("Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos, " +
                "e vem sendo utilizado desde o século XVI, quando um impressor desconhecido pegou uma");
        a.setDataInicio(new Date());
        a.setDataFim(new Date());

        Assunto a2 = new Assunto();
        a2.setNome("Assunto teste 2");
        a2.setDescricao("Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos, " +
                "e vem sendo utilizado desde o século XVI, quando um impressor desconhecido pegou uma");
        a2.setDataInicio(new Date());
        a2.setDataFim(new Date());

        assuntos.add(a);
        assuntos.add(a2);

        listaAssuntosAdapter = new ListaAssuntosAdapter(this, assuntos, false);
        listaAssuntos.setAdapter(listaAssuntosAdapter);

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
