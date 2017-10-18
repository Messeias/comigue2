package comigue.com.br.comigue.consumer;

import java.util.ArrayList;
import java.util.List;

import comigue.com.br.comigue.pojo.Assunto;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gabriel on 15/10/2017.
 */
public class AssuntoConsumer {
    // Polimorfismo
    List<Assunto> vetor = new ArrayList<Assunto>();

    private IAssuntoService assuntoService;
    private Retrofit retrofit;

    public AssuntoConsumer() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(IAssuntoService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.assuntoService = retrofit.create(IAssuntoService.class);
    }

    public Call<Assunto> postCadastrar(Assunto assunto) {
        return this.assuntoService.postCadastrar(assunto);
    }


    public Call<Assunto> putAtualizar(Assunto assunto) {
        return this.assuntoService.putAtualizar(assunto);
    }

    public Call<List<Assunto>> buscarTodos() {
        return this.assuntoService.buscarTodos();
    }

    public Call<Assunto> buscarPorId(long id){ return this.assuntoService.buscarPorId(id);}

    public Call<Void> deletePorId(long id) {
        return this.assuntoService.deletePorId(id);
    }
}
