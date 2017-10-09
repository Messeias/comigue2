package comigue.com.br.comigue;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 06/10/2017.
 */

public class InicioActivity extends Activity {


//    usar listview
    private EditText pesquisar;
    private ListView listaMaterias;
    private DrawerLayout menuLateral;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView drawerList;
    private String[] materia1 = {"Matemática", "9:30", "Matemática turma 4I na pétala, sala 9"};
    private String[] materia2 = {"Geografia", "10:45", "Geografia com professor Fernando"};
    private String[] materia3 = {"História", "11:30", "História bem louco com o Roger e mais uns loucos que nem são da turma mas estão fazendo por que repetiram de ano nesta matéria idiota"};
    private String[] menuItens = {"Minhas anotações", "Meus horários", "Configurações", "Editar perfil"};

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        pesquisar = (EditText) findViewById(R.id.pesquisar);
        listaMaterias = (ListView) findViewById(R.id.lista_materias);
        final ListarMateriasAdapter materiasAdapter = new ListarMateriasAdapter(this, listarMaterias());
        listaMaterias.setAdapter(materiasAdapter);


        menuLateral = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuItens));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

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

    public List<String[]> listarMaterias(){
        List<String[]> materias = new ArrayList<String[]>();
        materias.add(this.materia1);
        materias.add(this.materia2);
        materias.add(this.materia3);
        return materias;
    }

}
