package br.com.senac.projectsolutions.Controller;

import android.content.Context;

import br.com.senac.projectsolutions.R;
import br.com.senac.projectsolutions.Util.DataGetter;

public class MainController {

    public void getProdutosDisponiveis(Context context){
        String url = context.getResources().getString(R.string.web_service_path).concat("produtos/allprodutos");
        DataGetter dt = new DataGetter(context, "produto");
        dt.execute(url);
    }
}
