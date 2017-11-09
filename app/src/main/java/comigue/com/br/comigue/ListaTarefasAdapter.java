package comigue.com.br.comigue;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import comigue.com.br.comigue.pojo.Tarefa;

/**
 * Created by Usuario on 30/10/2017.
 */

public class ListaTarefasAdapter extends ArrayAdapter<Tarefa> {

    private Context context;
    private List<Tarefa> tarefas;

    public ListaTarefasAdapter(Context context, List<Tarefa> tarefas){
        super(context, 0, tarefas);
        this.tarefas = tarefas;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        Tarefa tarefa = tarefas.get(position);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.tarefa_item_list, null);

        TextView nome = (TextView) view.findViewById(R.id.list_nome_tarefa);
        nome.setText(tarefa.getNome());
        TextView peso = (TextView) view.findViewById(R.id.list_peso_tarefa);
        peso.setText("" + tarefa.getPeso());
        TextView descricao =  (TextView) view.findViewById(R.id.list_descricao_tarefa);
        View etiqueta = (View) view.findViewById(R.id.list_etiqueta_tarefa);
        switch (tarefa.getEtiqueta()){
            case 'f':
                etiqueta.setBackgroundColor(Color.BLUE);
                break;
            case 'm':
                etiqueta.setBackgroundColor(Color.rgb(242, 198, 91));
                break;
            case 'd':
                etiqueta.setBackgroundColor(Color.RED);
                break;
        }
        descricao.setText(tarefa.getDescricao());
        view.setId((int) tarefa.getCodTarefa());
        TextView materia =  (TextView) view.findViewById(R.id.list_materia_tarefa);
        materia.setText(tarefa.getMateria().getNome());
        Log.e("nome: "+tarefa.getNome(), "getView: id " +tarefa.getCodTarefa() );
        return view;
    }
}
