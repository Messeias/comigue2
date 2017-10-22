package comigue.com.br.comigue.consumer;

import java.util.List;

import comigue.com.br.comigue.pojo.Horario;
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
public interface IHorarioService{

    @POST("horario/")
    Call<Horario> postCadastrar(@Body Horario horario);

    @PUT("horario/")
    Call<Horario> putAtualizar(@Body Horario horario);

    @GET("horario/")
    Call<List<Horario>> buscarTodos();

    @GET("horario/{id}")
    Call<Horario> buscarPorId(@Path("id")long id);

    @DELETE("horario/{id}")
    Call<Void> deletePorId(@Path("id")long id);

}
