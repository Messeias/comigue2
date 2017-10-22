package comigue.com.br.comigue.consumer;

import java.util.ArrayList;
import java.util.List;

import comigue.com.br.comigue.pojo.Convite;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gabriel on 15/10/2017.
 */
public class ConviteConsumer {
    // Polimorfismo
    List<Convite> vetor = new ArrayList<Convite>();

    private IConviteService conviteService;
    private Retrofit retrofit;

    public ConviteConsumer() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(IService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.conviteService = retrofit.create(IConviteService.class);
    }

    public Call<Convite> postCadastrar(Convite convite) {
        return this.conviteService.postCadastrar(convite);
    }


    public Call<Convite> putAtualizar(Convite convite) {
        return this.conviteService.putAtualizar(convite);
    }

    public Call<List<Convite>> buscarTodos() {
        return this.conviteService.buscarTodos();
    }

    public Call<Convite> buscarPorId(long id){ return this.conviteService.buscarPorId(id);}

    public Call<Void> deletePorId(long id) {
        return this.conviteService.deletePorId(id);
    }
}
