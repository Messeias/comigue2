package comigue.com.br.comigue.consumer;

import java.util.ArrayList;
import java.util.List;

import comigue.com.br.comigue.pojo.PlanoDeEnsino;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gabriel on 15/10/2017.
 */
public class PlanoDeEnsinoConsumer {
    // Polimorfismo
    List<PlanoDeEnsino> vetor = new ArrayList<PlanoDeEnsino>();

    private IPlanoDeEnsinoService planoDeEnsinoService;
    private Retrofit retrofit;

    public PlanoDeEnsinoConsumer() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(IService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.planoDeEnsinoService = retrofit.create(IPlanoDeEnsinoService.class);
    }

    public Call<PlanoDeEnsino> postCadastrar(PlanoDeEnsino planoDeEnsino) {
        return this.planoDeEnsinoService.postCadastrar(planoDeEnsino);
    }


    public Call<PlanoDeEnsino> putAtualizar(PlanoDeEnsino planoDeEnsino) {
        return this.planoDeEnsinoService.putAtualizar(planoDeEnsino);
    }

    public Call<List<PlanoDeEnsino>> buscarTodos() {
        return this.planoDeEnsinoService.buscarTodos();
    }

    public Call<PlanoDeEnsino> buscaPorMateria(long cod) {
        return this.planoDeEnsinoService.buscarPorMateria(cod);
    }

    public Call<PlanoDeEnsino> buscarPorId(long id){ return this.planoDeEnsinoService.buscarPorId(id);}

    public Call<Void> deletePorId(long id) {
        return this.planoDeEnsinoService.deletePorId(id);
    }
}
