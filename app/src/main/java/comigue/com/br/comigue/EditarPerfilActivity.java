package comigue.com.br.comigue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import comigue.com.br.comigue.consumer.UsuarioConsumer;
import comigue.com.br.comigue.pojo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 10/11/2017.
 */

public class EditarPerfilActivity extends Activity {

    private EditText novo_email;
    private EditText novo_senha;
    private EditText novo_celular;
    private EditText novo_nome;
    private Usuario usuario;
    private Usuario novo_usuario;
    private UsuarioConsumer usuarioConsumer;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_perfil);
        inicializaComponentes();
    }



    public void editarCadastroOnClick(View v){
        novo_usuario = new Usuario();
        usuarioConsumer = new UsuarioConsumer();
        novo_usuario.setCelular(novo_celular.getText().toString());
        novo_usuario.setEmail(novo_email.getText().toString());
        novo_usuario.setSenha(novo_senha.getText().toString());
        novo_usuario.setNome(novo_nome.getText().toString());
        novo_usuario.setCodUsuario(usuario.getCodUsuario());

        Call<Usuario> call = usuarioConsumer.postCadastrar(novo_usuario);

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Carregando");
        progressDoalog.setTitle("Conectando com servidor");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                int responseCode = response.code();
                if(responseCode == 201){
                    Log.e("criado com sucesso ", "onResponse: ");
                    Toast.makeText(EditarPerfilActivity.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT);
                    Intent it = new Intent(EditarPerfilActivity.this, InicioActivity.class);

                    novo_usuario = response.body();
                    Bundle b = new Bundle();
                    b.putSerializable("usuario", novo_usuario);
                    it.putExtras(b);

                    startActivity(it);
                    progressDoalog.dismiss();
                    finish();
                } else {
                    Log.e("deu erro", "onResponse: ");
                    Toast.makeText(EditarPerfilActivity.this, "Erro ao salvar as modificações", Toast.LENGTH_SHORT);
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(EditarPerfilActivity.this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }


    public void removerCadastroOnClick(View v){

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Deletar Conta")
                .setMessage("Deseja realmente deletar sua conta?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Void> call = usuarioConsumer.deletePorId(usuario.getCodUsuario());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                int responseCode = response.code();
                                if(responseCode == 201){
                                    Log.e("Deletado com sucesso ", "onResponse: ");
                                    Toast.makeText(EditarPerfilActivity.this, "Conta deletada com sucesso", Toast.LENGTH_SHORT);
                                    Intent it = new Intent(EditarPerfilActivity.this, LoginActivity.class);

                                    startActivity(it);
                                    finish();

                                } else {
                                    Log.e("deu erro", "onResponse: ");
                                    Toast.makeText(EditarPerfilActivity.this, "Não foi possível deletar", Toast.LENGTH_SHORT);
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(EditarPerfilActivity.this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void inicializaComponentes(){
        this.usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");

        this.novo_email = (EditText) findViewById(R.id.editar_email);
        this.novo_nome = (EditText) findViewById(R.id.editar_nome);
        this.novo_celular = (EditText) findViewById(R.id.editar_celular);
        this.novo_senha = (EditText) findViewById(R.id.editar_senha);

        this.novo_nome.setText(usuario.getNome());
        this.novo_email.setText(usuario.getEmail());
        this.novo_celular.setText(usuario.getCelular());
        this.novo_senha.setText(usuario.getSenha());
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
