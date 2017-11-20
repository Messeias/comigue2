package comigue.com.br.comigue.consumer;

import java.util.List;

import comigue.com.br.comigue.pojo.Materia;
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

public interface IMateriaService {

    @POST("materia/")
    Call<Materia> postCadastrar(@Body Materia materia);

    @PUT("materia/")
    Call<Materia> putAtualizar(@Body Materia materia);

    @GET("materia/")
    Call<List<Materia>> buscarTodos();

    @GET("materia/pesquisar")
    Call<List<Materia>> buscarPublicos();

    @GET("materia/buscar/{nome}")
    Call<List<Materia>> buscarPorNome(@Path("nome") String nome);

    @GET("materia/usuario/{idUsuario}")
    Call<List<Materia>> buscarPorUsuario(@Path("idUsuario") Long id);

    @GET("materia/{id}")
    Call<Materia> buscarPorId(@Path("id")long id);

    @DELETE("materia/{id}")
    Call<Void> deletePorId(@Path("id")long id);

}
