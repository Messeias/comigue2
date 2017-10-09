package comigue.com.br.comigue;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Usuario on 08/10/2017.
 */

public class DrawerItemClickListener implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        selectItem(view.toString());
    }

    private void selectItem(String opcao){
        String msg = "Selecionou a opção "+opcao;
    }

}
