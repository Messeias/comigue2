package comigue.com.br.comigue.consumer;

/**
 * Created by alunoinfo on 10/10/17.
 */

import comigue.com.br.comigue.pojo.Usuario;
import retrofit2.http.POST;

        import java.util.List;
        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.DELETE;
        import retrofit2.http.GET;
        import retrofit2.http.POST;
        import retrofit2.http.PUT;
        import retrofit2.http.Path;

/**
 * Created by REMOR on 31/08/2017.
 */

public interface IUsuarioService {
    static final String URL_BASE = "http://192.168.0.3:8081/";

    @POST("usuario/{login}/{senha}")
    Call<Usuario> postAutentica(@Path("login") String login, @Path("senha") String senha);

    @POST("usuario/")
    Call<Usuario> postCadastrar(@Body Usuario usuario);

    @PUT("usuario/")
    Call<Usuario> putAtualizar(@Body Usuario usuario);

    @GET("usuario/")
    Call<List<Usuario>> buscarTodos();

    @DELETE("usuario/{id}")
    Call<Void> deletePorId(@Path("id")long id);




}
