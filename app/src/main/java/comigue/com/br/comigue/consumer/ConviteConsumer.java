package comigue.com.br.comigue.consumer;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comigue.com.br.comigue.pojo.Convite;
import comigue.com.br.comigue.pojo.Materia;
import comigue.com.br.comigue.pojo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    public static void convidarUsuario(Usuario usuario, Materia materia){
        Convite c = new Convite();
        c.setUsuario(usuario);
        c.setMateria(materia);
        c.setStatus(true);

        Log.i(usuario.getNome() + "  " +usuario.getCodUsuario(), "convidarUsuario: ");

        new ConviteConsumer().postCadastrar(c).enqueue(new Callback<Convite>() {
            @Override
            public void onResponse(Call<Convite> call, Response<Convite> response) {
                Log.i(response.code()+"", "onResponse: ");
            }

            @Override
            public void onFailure(Call<Convite> call, Throwable t) {
                t.printStackTrace();
                Log.i(t.getMessage().toString(), "onFailure: ");
            }
        });
    }
}
