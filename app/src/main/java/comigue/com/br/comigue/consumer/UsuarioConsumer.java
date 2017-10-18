package comigue.com.br.comigue.consumer;

/**
 * Created by alunoinfo on 10/10/17.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import comigue.com.br.comigue.pojo.Usuario;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by REMOR on 31/08/2017.
 */

public class UsuarioConsumer {
    // Polimorfismo
    List<Usuario> vetor = new ArrayList<Usuario>();

    private IUsuarioService usuarioService;
    private Retrofit retrofit;

    public UsuarioConsumer() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(IUsuarioService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.usuarioService = retrofit.create(IUsuarioService.class);
    }

    public Call<Usuario> postAutentica(String login, String senha) {
        return this.usuarioService.postAutentica(login, senha);

    }

    public Call<Usuario> postCadastrar(Usuario usuario) {
        return this.usuarioService.postCadastrar(usuario);
    }


    public Call<Usuario> putAtualizar(Usuario usuario) {
        return this.usuarioService.putAtualizar(usuario);
    }

    public Call<List<Usuario>> buscarTodos() {
        return this.usuarioService.buscarTodos();
    }

    public Call<Usuario> buscarPorId(long id){ return this.usuarioService.buscarPorId(id);}

    public Call<Usuario> postLogar(Usuario usuario){ return this.usuarioService.postLogar(usuario);}

    public Call<Void> deletePorId(long id) {
        return this.usuarioService.deletePorId(id);
    }








}
