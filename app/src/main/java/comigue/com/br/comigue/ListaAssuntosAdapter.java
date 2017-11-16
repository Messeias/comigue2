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

import comigue.com.br.comigue.pojo.Assunto;

/**
 * Created by Usuario on 08/11/2017.
 */

public class ListaAssuntosAdapter extends ArrayAdapter<Assunto> {

    private Context context;
    private List<Assunto> assuntos;
    private boolean excluir;

    public ListaAssuntosAdapter(Context context, List<Assunto> assuntos, boolean ecluir){
        super(context, 0, assuntos);
        this.assuntos = assuntos;
        this.context = context;
        this.excluir = ecluir;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        Assunto assunto = assuntos.get(position);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.assunto_item_list, null);

        TextView nome = (TextView) view.findViewById(R.id.list_nome_assunto);
        nome.setText(assunto.getNome());


        TextView dataI = (TextView) view.findViewById(R.id.list_data_inicio_assunto);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dataI.setText("De "+sdf.format(assunto.getDataInicio())+ " a " +sdf.format(assunto.getDataFim()));

//        TextView dataF =  (TextView) view.findViewById(R.id.list_data_fim_assunto);
//        dataF.setText(sdf.format(assunto.getDataFim()));

        TextView desc =  (TextView) view.findViewById(R.id.list_descricao_assunto);
        desc.setText(assunto.getDescricao());

        if(!excluir){
            TextView exc =  (TextView) view.findViewById(R.id.btn_excluir_ass);
            exc.setText("");
        }

        Log.e("nome: "+assunto.getNome(), "getView: id " +assunto.getCodAssunto() );
        return view;
    }
}
