package comigue.com.br.comigue.consumer;

/**
 * Created by alunoinfo on 10/10/17.
 */

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
import java.util.concurrent.TimeUnit;

import comigue.com.br.comigue.pojo.Usuario;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by REMOR on 31/08/2017.
 */

public class UsuarioConsumer {
    // Polimorfismo
    List<Usuario> vetor = new ArrayList<Usuario>();

    private IUsuarioService usuarioService;
    private Retrofit retrofit;

    public UsuarioConsumer() {


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

                if(str.contains("-")){
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
        this.usuarioService = retrofit.create(IUsuarioService.class);
    }

    public Call<Usuario> postAutentica(String login, String senha) {
        return this.usuarioService.postAutentica(login, senha);

    }

    public Call<Usuario> postCadastrar(Usuario usuario) {
        return this.usuarioService.postCadastrar(usuario);
    }


    public Call<Usuario> putAtualizar(Usuario usuario) {
        return this.usuarioService.putAtualizar(usuario);
    }

    public Call<List<Usuario>> buscarTodos() {
        return this.usuarioService.buscarTodos();
    }

    public Call<Usuario> buscarPorEmail(Usuario email) {
        return this.usuarioService.buscarPorEmail(email);
    }

    public Call<Usuario> buscarPorId(long id){ return this.usuarioService.buscarPorId(id);}

    public Call<Usuario> postLogar(Usuario usuario){ return this.usuarioService.postLogar(usuario);}

    public Call<Void> deletePorId(long id) {
        return this.usuarioService.deletePorId(id);
    }








}
