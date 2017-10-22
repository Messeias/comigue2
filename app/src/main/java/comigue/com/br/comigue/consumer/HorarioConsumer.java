package comigue.com.br.comigue.consumer;

import java.util.ArrayList;
import java.util.List;

import comigue.com.br.comigue.pojo.Horario;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gabriel on 15/10/2017.
 */
public class HorarioConsumer {
    // Polimorfismo
    List<Horario> vetor = new ArrayList<Horario>();

    private IHorarioService horarioService;
    private Retrofit retrofit;

    public HorarioConsumer() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(IService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.horarioService = retrofit.create(IHorarioService.class);
    }

    public Call<Horario> postCadastrar(Horario horario) {
        return this.horarioService.postCadastrar(horario);
    }


    public Call<Horario> putAtualizar(Horario horario) {
        return this.horarioService.putAtualizar(horario);
    }

    public Call<List<Horario>> buscarTodos() {
        return this.horarioService.buscarTodos();
    }

    public Call<Horario> buscarPorId(long id){ return this.horarioService.buscarPorId(id);}

    public Call<Void> deletePorId(long id) {
        return this.horarioService.deletePorId(id);
    }
}
