package comigue.com.br.comigue.consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        JsonSerializer<Date> ser = new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        };

        JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
                return json == null ? null : new Date(/*json.getAsLong()*/);
            }
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, ser)
                .registerTypeAdapter(Date.class, deser).create();


//        Gson gson = new GsonBuilder()
//                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz"), DateFormat.FULL).create();


        this.retrofit = new Retrofit.Builder()
                .baseUrl(IService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
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
