package comigue.com.br.comigue.consumer;

import java.util.List;

import comigue.com.br.comigue.pojo.Convite;
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
public interface IConviteService {

    @POST("convite/")
    Call<Convite> postCadastrar(@Body Convite convite);

    @PUT("convite/")
    Call<Convite> putAtualizar(@Body Convite convite);

    @GET("convite/")
    Call<List<Convite>> buscarTodos();

    @GET("convite/{id}")
    Call<Convite> buscarPorId(@Path("id")long id);

    @DELETE("convite/{id}")
    Call<Void> deletePorId(@Path("id")long id);

}
