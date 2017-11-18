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

import com.google.api.services.drive.Drive;

import comigue.com.br.comigue.consumer.AnotacaoConsumer;
import comigue.com.br.comigue.pojo.Anotacao;
import comigue.com.br.comigue.pojo.Materia;
import comigue.com.br.comigue.pojo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 10/11/2017.
 */

public class AnotacaoActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Usuario usuario;
    private Materia materia;
    private TextView nomeAnotacao;
    private EditText textoAnotacao;
    private TextView tipoAnotacao;
    private Spinner spinner;
    private String[] paths;
    private AlertDialog.Builder builder;
    private AnotacaoConsumer anotacaoConsumer;
    private Anotacao anotacao;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anotacao);

        inicializaComponentes();

    }

    public void inicializaComponentes(){
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        materia = (Materia) getIntent().getExtras().getSerializable("materia");
        this.nomeAnotacao = (TextView) findViewById(R.id.nome_anotacao);
        this.textoAnotacao = (EditText) findViewById(R.id.texto_anotacao);
        this.tipoAnotacao = (TextView) findViewById(R.id.navTitleAnotacao);

        if(materia==null){
            Toast.makeText(this, "Não tem materia", Toast.LENGTH_SHORT).show();

            this.nomeAnotacao.setText("Anotações Pessoais");
            this.tipoAnotacao.setText(usuario.getNome());
            spinner = (Spinner)findViewById(R.id.btn_meu_mat);
            spinner.setVisibility(View.INVISIBLE);

            anotacao = usuario.getAnotacoes();

            nomeAnotacao.setText(anotacao.getTitulo());
            textoAnotacao.setText(anotacao.getTexto());

        } else {

            this.tipoAnotacao.setText(materia.getNome());
            this.anotacao = materia.getAnotacoes();

            this.nomeAnotacao.setText(anotacao.getTitulo());
            this.textoAnotacao.setText(anotacao.getTexto());


            paths = new String[] {"←", "Plano de Ensino", "Calendário", "Convidar colega"};

            spinner = (Spinner)findViewById(R.id.btn_meu_mat);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AnotacaoActivity.this,
                    android.R.layout.simple_spinner_item,paths);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
        }

        this.textoAnotacao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.hasFocus()) {
                    anotacaoConsumer = new AnotacaoConsumer();
                    anotacao.setTexto(textoAnotacao.getText().toString());
                    Call<Anotacao> call = anotacaoConsumer.putAtualizar(anotacao);
                    call.enqueue(new Callback<Anotacao>() {
                        @Override
                        public void onResponse(Call<Anotacao> call, Response<Anotacao> response) {
                            Toast.makeText(AnotacaoActivity.this, "Salvou esta anotação", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<Anotacao> call, Throwable t) {
                            Toast.makeText(AnotacaoActivity.this, "Deu algum erro", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
            bundle.putSerializable("materia", materia);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
          else if("Calendário".equalsIgnoreCase(opcao)) {

            // Calendário

        } else  if("Convidar colega".equalsIgnoreCase(opcao)) {
            criarDialog();
            builder.show();
            spinner.setSelection(0);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }

    public void criarDialog(){
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
    }
}
