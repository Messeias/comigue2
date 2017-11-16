package comigue.com.br.comigue;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import comigue.com.br.comigue.pojo.Materia;
import comigue.com.br.comigue.pojo.Usuario;

/**
 * Created by Usuario on 10/11/2017.
 */

public class AnotacaoActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Usuario usuario;
    private Materia materia;
    private TextView nomeAnotacao;
    private EditText textoAnotacao;
    private Spinner spinner;
    private static final String[]paths = {"", "Plano de Ensino", "Calendário", "Convidar colega"};
    private AlertDialog.Builder builder;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anotacao);

        inicializaComponentes();

    }

    public void inicializaComponentes(){
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        materia = (Materia) getIntent().getExtras().getSerializable("materia");

        if(materia==null){
            Toast.makeText(this, "Não tem materia", Toast.LENGTH_SHORT).show();
        }

        this.nomeAnotacao = (TextView) findViewById(R.id.nome_anotacao);
        this.textoAnotacao = (EditText) findViewById(R.id.texto_anotacao);

        spinner = (Spinner)findViewById(R.id.btn_meu_mat);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AnotacaoActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Digite o e-mail");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AnotacaoActivity.this, "Enviamos um convite para ele!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        this.textoAnotacao.setText(
                "Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos, " +
                        "e vem sendo utilizado desde o século XVI, quando um impressor desconhecido pegou uma" +
                        " bandeja de tipos e os embaralhou para fazer um livro de modelos de tipos. Lorem Ipsum " +
                        "sobreviveu não só a cinco séculos, como também ao salto para a editoração eletrônica, " +
                        "permanecendo essencialmente inalterado. Se popularizou na década de 60, quando a Letraset " +
                        "lançou decalques contendo passagens de Lorem Ipsum, e mais recentemente quando passou a " +
                        "ser integrado a softwares de editoração eletrônica como " +
                        "Aldus PageMaker.");

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        String opcao = (String) parent.getItemAtPosition(position);

        if("Plano de Ensino".equalsIgnoreCase(opcao)){
            Intent intent = new Intent(this, PlanoDeEnsinoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
          else if("Calendário".equalsIgnoreCase(opcao)) {

            // Calendário

        } else  if("Convidar colega".equalsIgnoreCase(opcao)) {
            builder.show();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }
}
