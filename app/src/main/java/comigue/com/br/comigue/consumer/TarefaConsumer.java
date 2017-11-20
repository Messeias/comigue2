package comigue.com.br.comigue.consumer;

/**
 * Created by alunoinfo on 10/10/17.
 */

import android.util.Log;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
//                Log.i(json.getAsLong()+"", "deserialize: ");

                String str = json.getAsString();
                Date dataJ = new Date();

                if (str.contains("-")) {
                    try {
                        return new SimpleDateFormat("yyyy-MM-dd").parse(str);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    dataJ = new Date(json.getAsLong());
                }


//                dataJ = new Date(json.getAsLong());

                return json == null ? null : dataJ;
            }
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, ser)
                .registerTypeAdapter(Date.class, deser).create();

//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd").create();

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
        return this.tarefaService.putAtualizar(tarefa.getCodTarefa(), tarefa);
    }

    public Call<List<Tarefa>> buscarTodos() {
        return this.tarefaService.buscarTodos();
    }

    public Call<List<Tarefa>> buscarPorAluno(Long codAluno) {
        return this.tarefaService.buscarPorAluno(codAluno);
    }

    public Call<List<Tarefa>> buscarPorMateria(Long codAluno) {
        return this.tarefaService.buscarPorMateria(codAluno);
    }

    public Call<List<Tarefa>> buscarPorData(String dia, long codUsuario) {
        return this.tarefaService.buscarPorData(dia, codUsuario);
    }

    public Call<List<Tarefa>> buscarPorDataMateria(String dia, long codUsuario) {
        return this.tarefaService.buscarPorDataMateria(dia, codUsuario);
    }

    public Call<Tarefa> buscarPorId(long id){
        return this.tarefaService.buscarPorId(id);
    }

    public Call<Void> deletePorId(long id) {
        return this.tarefaService.deletePorId(id);
    }








}
