package comigue.com.br.comigue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Usuario on 07/10/2017.
 */

public class ListarMateriasAdapter extends ArrayAdapter<String[]> {

    private Context context;
    private List<String[]> materias = null;

    public ListarMateriasAdapter(Context context, List<String[]> materias){
        super(context, 0, materias);
        this.materias = materias;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        String[] materia = materias.get(position);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.materia_item_list, null);

        TextView nome = (TextView) view.findViewById(R.id.list_nome_materia);
        nome.setText(materia[0]);
        TextView horario = (TextView) view.findViewById(R.id.list_horario_materia);
        horario.setText(materia[1]);
        TextView descricao =  (TextView) view.findViewById(R.id.list_descricao_materia);
        descricao.setText(materia[2]);

        return view;
    }
}
