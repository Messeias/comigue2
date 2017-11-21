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
import comigue.com.br.comigue.pojo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 03/10/2017.
 */

public class LoginActivity extends Activity {

    private EditText email;
    private EditText senha;
    private Usuario usuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email = (EditText) findViewById(R.id.usuario);
        senha = (EditText) findViewById(R.id.senha);
    }

    public void entrarOnClick(View v){

        String emailInformado = this.email.getText().toString();
        String senhaInformada = this.senha.getText().toString();


        Usuario usuarioLogar = new Usuario();
        usuarioLogar.setEmail(emailInformado);
        usuarioLogar.setSenha(senhaInformada);


        UsuarioConsumer usuarioConsummer = new UsuarioConsumer();

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Carregando");
        progressDoalog.setTitle("Conectando com servidor");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // show it
        progressDoalog.show();

        Call<Usuario> call = usuarioConsummer.postLogar(usuarioLogar);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                int responseCode = response.code();
                if(responseCode == 200){
                    Usuario user = response.body();
                    Log.e("deu" , "onResponse: " + user.getNome() );
                    Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("usuario", user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    progressDoalog.dismiss();
                    finish();
                } else {
                    Log.e("Falha: " + responseCode, "onResponse: ");
                    Toast.makeText(LoginActivity.this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("não deu ",  t.getMessage());
                t.printStackTrace();
                Toast.makeText(LoginActivity.this, "Não foi possível conectar-se ao servidor", Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }

    public void cadastrarOnClick(View v){
        startActivity(new Intent(this, CadastrarActivity.class));
    }
}
