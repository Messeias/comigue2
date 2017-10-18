package comigue.com.br.comigue.consumer;

import java.util.ArrayList;
import java.util.List;

import comigue.com.br.comigue.pojo.Anotacao;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gabriel on 15/10/2017.
 */
public class AnotacaoConsumer {
    // Polimorfismo
    List<Anotacao> vetor = new ArrayList<Anotacao>();

    private IAnotacaoService anotacaoService;
    private Retrofit retrofit;

    public AnotacaoConsumer() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(IAnotacaoService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.anotacaoService = retrofit.create(IAnotacaoService.class);
    }

    public Call<Anotacao> postCadastrar(Anotacao anotacao) {
        return this.anotacaoService.postCadastrar(anotacao);
    }


    public Call<Anotacao> putAtualizar(Anotacao anotacao) {
        return this.anotacaoService.putAtualizar(anotacao);
    }

    public Call<List<Anotacao>> buscarTodos() {
        return this.anotacaoService.buscarTodos();
    }

    public Call<Anotacao> buscarPorId(long id){ return this.anotacaoService.buscarPorId(id);}

    public Call<Void> deletePorId(long id) {
        return this.anotacaoService.deletePorId(id);
    }
}
