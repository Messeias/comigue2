package comigue.com.br.comigue.consumer;

import java.util.List;

import comigue.com.br.comigue.pojo.Anotacao;
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
public interface IAnotacaoService {
    static final String URL_BASE = "http://192.168.0.2:8081/";

    @POST("anotacao/")
    Call<Anotacao> postCadastrar(@Body Anotacao anotacao);

    @PUT("anotacao/")
    Call<Anotacao> putAtualizar(@Body Anotacao anotacao);

    @GET("anotacao/")
    Call<List<Anotacao>> buscarTodos();

    @GET("anotacao/{id}")
    Call<Anotacao> buscarPorId(@Path("id") long id);

    @DELETE("anotacao/{id}")
    Call<Void> deletePorId(@Path("id")long id);

}
