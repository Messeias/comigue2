package comigue.com.br.comigue.consumer;

import java.util.List;

import comigue.com.br.comigue.pojo.PlanoDeEnsino;
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

public interface IPlanoDeEnsinoService {

    @POST("planoDeEnsino/")
    Call<PlanoDeEnsino> postCadastrar(@Body PlanoDeEnsino planoDeEnsino);

    @PUT("planoDeEnsino/")
    Call<PlanoDeEnsino> putAtualizar(@Body PlanoDeEnsino planoDeEnsino);

    @GET("planoDeEnsino/")
    Call<List<PlanoDeEnsino>> buscarTodos();

    @GET("planoDeEnsino/materia/{cod}")
    Call<PlanoDeEnsino> buscarPorMateria(@Path("cod") long cod);

    @GET("planoDeEnsino/{id}")
    Call<PlanoDeEnsino> buscarPorId(@Path("id")long id);

    @DELETE("planoDeEnsino/{id}")
    Call<Void> deletePorId(@Path("id")long id);

}
