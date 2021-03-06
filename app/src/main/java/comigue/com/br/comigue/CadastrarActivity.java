package comigue.com.br.comigue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import comigue.com.br.comigue.consumer.UsuarioConsumer;
import comigue.com.br.comigue.pojo.Anotacao;
import comigue.com.br.comigue.pojo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 06/10/2017.
 */

public class CadastrarActivity extends Activity {

    private EditText email;
    private EditText senha;
    private EditText celular;
    private EditText nome;
    private Usuario usuario;
    private UsuarioConsumer usuarioConsumer;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar);
        inicializaComponentes();
    }



    public void finalizarCadastroOnClick(View v){
        usuario = new Usuario();
        usuarioConsumer = new UsuarioConsumer();
        usuario.setCelular(celular.getText().toString());
        usuario.setEmail(email.getText().toString());
        usuario.setSenha(senha.getText().toString());
        usuario.setNome(nome.getText().toString());

        Anotacao a = new Anotacao();
        a.setTitulo("Anotações de "+usuario.getNome());
        a.setTexto("Faça suas anotações aqui...");

        usuario.setAnotacoes(a);

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Carregando");
        progressDoalog.setTitle("Conectando com servidor");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // show it
        progressDoalog.show();

        Call<Usuario> call = usuarioConsumer.postCadastrar(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                int responseCode = response.code();
                if(responseCode == 201){
                    Log.e("criado com sucesso ", "onResponse: ");
                    Toast.makeText(CadastrarActivity.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(CadastrarActivity.this, LoginActivity.class);
                    startActivity(it);
                    progressDoalog.dismiss();
                    finish();
                } else {
                    Log.e("deu erro", "onResponse: ");
                    Toast.makeText(CadastrarActivity.this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CadastrarActivity.this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }

    public void inicializaComponentes(){
        this.email = (EditText) findViewById(R.id.email);
        this.nome = (EditText) findViewById(R.id.nome);
        this.celular = (EditText) findViewById(R.id.celular);
        this.senha = (EditText) findViewById(R.id.senha);
    }


}
