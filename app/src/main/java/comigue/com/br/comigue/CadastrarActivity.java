package comigue.com.br.comigue;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Usuario on 06/10/2017.
 */

public class CadastrarActivity extends Activity {

    private EditText email;
    private EditText senha;
    private EditText celular;
    private EditText nome;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar);
        this.email = (EditText) findViewById(R.id.email);
        this.nome = (EditText) findViewById(R.id.nome);
        this.celular = (EditText) findViewById(R.id.celular);
        this.senha = (EditText) findViewById(R.id.senha);
    }

    public void finalizarCadastroOnClick(View v){
        String msg = "Cadastro realizado com sucesso, "+this.nome.getText().toString();
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
