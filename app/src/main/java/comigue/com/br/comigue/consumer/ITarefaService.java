package comigue.com.br.comigue.consumer;

import java.util.List;

import comigue.com.br.comigue.pojo.Tarefa;
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
public interface ITarefaService {
    static final String URL_BASE = "http://192.168.0.2:8081/";

    @POST("tarefa/")
    Call<Tarefa> postCadastrar(@Body Tarefa tarefa);

    @PUT("tarefa/")
    Call<Tarefa> putAtualizar(@Body Tarefa tarefa);

    @GET("tarefa/")
    Call<List<Tarefa>> buscarTodos();

    @GET("tarefa/{id}")
    Call<Tarefa> buscarPorId(@Path("id")long id);

    @DELETE("tarefa/{id}")
    Call<Void> deletePorId(@Path("id")long id);

}
