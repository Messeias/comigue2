package comigue.com.br.comigue.consumer;

import java.util.List;

import comigue.com.br.comigue.pojo.Assunto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Gabriel on 15/10/2017.
 */
public interface IAssuntoService {
    static final String URL_BASE = "http://192.168.0.2:8081/";

    @POST("assunto/")
    Call<Assunto> postCadastrar(@Body Assunto assunto);

    @PUT("assunto/")
    Call<Assunto> putAtualizar(@Body Assunto assunto);

    @GET("assunto/")
    Call<List<Assunto>> buscarTodos();

    @GET("assunto/{id}")
    Call<Assunto> buscarPorId(@Path("id")long id);

    @DELETE("assunto/{id}")
    Call<Void> deletePorId(@Path("id")long id);

}
