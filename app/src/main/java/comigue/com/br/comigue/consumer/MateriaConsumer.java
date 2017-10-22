package comigue.com.br.comigue.consumer;

import java.util.ArrayList;
import java.util.List;

import comigue.com.br.comigue.pojo.Materia;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gabriel on 15/10/2017.
 */
public class MateriaConsumer {
    // Polimorfismo
    List<Materia> vetor = new ArrayList<Materia>();

    private IMateriaService materiaService;
    private Retrofit retrofit;

    public MateriaConsumer() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(IService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.materiaService = retrofit.create(IMateriaService.class);
    }

    public Call<Materia> postCadastrar(Materia materia) {
        return this.materiaService.postCadastrar(materia);
    }


    public Call<Materia> putAtualizar(Materia materia) {
        return this.materiaService.putAtualizar(materia);
    }

    public Call<List<Materia>> buscarPorUsuario(Long idUsuario) {
        return this.materiaService.buscarPorUsuario(idUsuario);
    }

    public Call<List<Materia>> buscarPorNome(String nome) {
        return this.materiaService.buscarPorNome(nome);
    }

    public Call<List<Materia>> buscarTodos() {
        return this.materiaService.buscarTodos();
    }

    public Call<Materia> buscarPorId(long id){ return this.materiaService.buscarPorId(id);}

    public Call<Void> deletePorId(long id) {
        return this.materiaService.deletePorId(id);
    }
}
