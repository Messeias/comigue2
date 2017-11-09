package comigue.com.br.comigue.consumer;

/**
 * Created by alunoinfo on 10/10/17.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comigue.com.br.comigue.pojo.Tarefa;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by REMOR on 31/08/2017.
 */

public class TarefaConsumer {
    // Polimorfismo
    List<Tarefa> vetor = new ArrayList<Tarefa>();

    private ITarefaService tarefaService;
    private Retrofit retrofit;

    public TarefaConsumer() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd").create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(IService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.tarefaService = retrofit.create(ITarefaService.class);
    }

    public Call<Void> postCadastrar(Tarefa tarefa) {
        return this.tarefaService.postCadastrar(tarefa);
    }

    public Call<Tarefa> putAtualizar(Tarefa tarefa) {
        return this.tarefaService.putAtualizar(tarefa);
    }

    public Call<List<Tarefa>> buscarTodos() {
        return this.tarefaService.buscarTodos();
    }

    public Call<List<Tarefa>> buscarPorAluno(Long codAluno) {
        return this.tarefaService.buscarPorAluno(codAluno);
    }

    public Call<List<Tarefa>> buscarPorData(String dia, long codUsuario) {
        return this.tarefaService.buscarPorData(dia, codUsuario);
    }

    public Call<Tarefa> buscarPorId(long id){
        return this.tarefaService.buscarPorId(id);
    }

    public Call<Void> deletePorId(long id) {
        return this.tarefaService.deletePorId(id);
    }








}
