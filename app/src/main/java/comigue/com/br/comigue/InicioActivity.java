package comigue.com.br.comigue;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import comigue.com.br.comigue.consumer.MateriaConsumer;
import comigue.com.br.comigue.pojo.Materia;
import comigue.com.br.comigue.pojo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 06/10/2017.
 */

public class InicioActivity extends Activity {


    private EditText pesquisar;
    private DrawerLayout menuLateral;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView drawerList;
    private ListView listaMaterias;
    private List<Materia> materias;
    private MateriaConsumer materiaConsumer;
    private ListarMateriasAdapter materiasAdapter;
    private Usuario usuario;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        this.materiaConsumer = new MateriaConsumer();

        inicializaComponentes();

        buscarMaterias();

    }

    public void buscarMaterias(){
        Bundle bundle = getIntent().getExtras();
        usuario = (Usuario) bundle.getSerializable("usuario");
        Long idUsuario = usuario.getCodUsuario();
        materiaConsumer.buscarPorUsuario(idUsuario).enqueue(new Callback<List<Materia>>() {
            @Override
            public void onResponse(Call<List<Materia>> call, Response<List<Materia>> response) {
                InicioActivity.this.materias = response.body();
                for (Materia m: InicioActivity.this.materias) {
                    Log.e("" + m.getNome(), "onResponse: ");
                }

                InicioActivity.this.materiasAdapter = new ListarMateriasAdapter(InicioActivity.this, materias);
                InicioActivity.this.listaMaterias.setAdapter(materiasAdapter);
                InicioActivity.this.listaMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Materia materiaPassar = (Materia) adapterView.getItemAtPosition(i);
                        Intent it1 = new Intent(InicioActivity.this, AnotacaoActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("usuario", usuario);
                        bundle1.putSerializable("materia", materiaPassar);
                        it1.putExtras(bundle1);
                        startActivity(it1);
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Materia>> call, Throwable t) {
                Log.e("não deu ", "onFailure: " );
                t.printStackTrace();
                Log.i(t.getMessage(), "onFailure: ");
            }
        });
    }

    public void inicializaComponentes(){
        pesquisar = (EditText) findViewById(R.id.pesquisar);
        listaMaterias = (ListView) findViewById(R.id.lista_materias);

        menuLateral = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Arrays.asList("Editar Perfil", "Anotações Pessoais", "Meu Calendário", "Criar Nova Matéria", "Sair")));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent it0 = new Intent(InicioActivity.this, EditarPerfilActivity.class);
                        Bundle bundle0 = new Bundle();
                        bundle0.putSerializable("usuario", usuario);
                        it0.putExtras(bundle0);
                        startActivity(it0);
                        finish();
                        break;
                    case 1:
                        Intent it1 = new Intent(InicioActivity.this, AnotacaoActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("usuario", usuario);
                        it1.putExtras(bundle1);
                        startActivity(it1);
                        finish();
                        break;
                    case 2:
                        Intent it = new Intent(InicioActivity.this, CalendarioActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        it.putExtras(bundle);
                        startActivity(it);
                        finish();
                        break;
                    case 3:
                        Intent it2 = new Intent(InicioActivity.this, NovaMateriaActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("usuario", usuario);
                        it2.putExtras(bundle2);
                        startActivity(it2);
                        finish();
                        break;
                    case 4:
                        startActivity(new Intent(InicioActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, menuLateral,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        menuLateral.addDrawerListener(mDrawerToggle);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = menuLateral.isDrawerOpen(drawerList);
        menu.findItem(R.id.web).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public void mostrarMenuLateral(View v){
        String msg = "Fingindo que o menu apareceu";
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
        menuLateral.openDrawer(Gravity.LEFT);
    }

    public  void pesquisarMateria(View v){
        String msg = "pesquisou pela matéria: " +this.pesquisar.getText().toString();
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Fechar aplicativo")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
