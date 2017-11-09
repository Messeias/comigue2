package comigue.com.br.comigue.consumer;

import java.util.Date;
import java.util.List;

import comigue.com.br.comigue.pojo.Tarefa;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Gabriel on 15/10/2017.
 */
public interface ITarefaService {

    @POST("tarefa/")
    Call<Void> postCadastrar(@Body Tarefa tarefa);

    @PUT("tarefa/")
    Call<Tarefa> putAtualizar(@Body Tarefa tarefa);

    @GET("tarefa/")
    Call<List<Tarefa>> buscarTodos();

    @GET("tarefa/aluno/{codAluno}")
    Call<List<Tarefa>> buscarPorAluno(@Path("codAluno") long id);

    @GET("tarefa/calendario")
    Call<List<Tarefa>> buscarPorData(@Query("dia") String dia, @Query("codUsuario") long codUsuario);

    @GET("tarefa/{id}")
    Call<Tarefa> buscarPorId(@Path("id")long id);

    @DELETE("tarefa/{id}")
    Call<Void> deletePorId(@Path("id")long id);

}
