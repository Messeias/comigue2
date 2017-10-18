package comigue.com.br.comigue;

import android.app.Activity;
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
    private UsuarioConsumer usuarioConsummer = new UsuarioConsumer();
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

        usuarioConsummer.postAutentica(emailInformado, senhaInformada).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    usuario = response.body();
                    Intent it = new Intent(LoginActivity.this, InicioActivity.class);
                    Bundle data = new Bundle();
                    data.putSerializable("usuario", usuario);
                    it.putExtras(data);
                    startActivity(it);
                    finish();
                    startActivity(it);
//                    Toast.makeText(LoginActivity.this, "Deveria ter cadastrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Falha na Comunicação", Toast.LENGTH_SHORT).show();
                Log.e("YOUR_APP_LOG_TAG", t.getMessage() + "\t" + t.toString(), t.getCause());
            }
        });

        if(usuario != null){
            startActivity(new Intent(this, InicioActivity.class));
        }else {
            String msgErro = getString(R.string.erro_autenticacao);
            Toast toast = Toast.makeText(this, msgErro, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void cadastrarOnClick(View v){
        startActivity(new Intent(this, CadastrarActivity.class));
    }
}
