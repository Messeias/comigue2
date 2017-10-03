package comigue.com.br.comigue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Usuario on 03/10/2017.
 */

public class LoginActivity extends Activity {

    private EditText usuario;
    private EditText senha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usuario = (EditText) findViewById(R.id.usuario);
        senha = (EditText) findViewById(R.id.senha);
    }

    public void entrarOnClick(View v){
        String usuarioInformado = usuario.getText().toString();
        String senhaInformada = senha.getText().toString();
        if("leitor".equals(usuarioInformado) && "123".equals(senhaInformada)){
            //startActivity(new Intent(this, DashboardActivity.class));
        }else {
            String msgErro = getString(R.string.erro_autenticacao);
            Toast toast = Toast.makeText(this, msgErro, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
