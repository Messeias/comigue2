package comigue.com.br.comigue;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import comigue.com.br.comigue.pojo.Horario;
import comigue.com.br.comigue.pojo.Materia;

/**
 * Created by Usuario on 07/10/2017.
 */

public class ListarMateriasAdapter extends ArrayAdapter<Materia> {

    private Context context;
    private List<Materia> materias;

    public ListarMateriasAdapter(Context context, List<Materia> materias){
        super(context, 0, materias);
        this.materias = materias;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        Materia materia = materias.get(position);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.materia_item_list, null);

        TextView nome = (TextView) view.findViewById(R.id.list_nome_materia);
        nome.setText(materia.getNome());

        TextView horario = (TextView) view.findViewById(R.id.list_horario_materia);
        String hrs = "";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE/hh:mm");
        for(Horario h: materia.getHorarios()){
            hrs = sdf.format(h.getHora()) +" "+ hrs;
        }
        horario.setText(hrs);


        TextView descricao =  (TextView) view.findViewById(R.id.list_descricao_materia);
        descricao.setText(materia.getDescricao());
        view.setId((int) materia.getCodMateria());
        Log.e("nome: "+materia.getNome(), "getView: id " +materia.getCodMateria() );
        return view;
    }
}
