package br.com.senac.projectsolutions.Controller;

import android.content.Context;

import br.com.senac.projectsolutions.Util.DataGetter;

public class MainController {

    public void getProdutosDisponiveis(Context context){
        String url = "http://192.168.0.44:8084/Api_Floricultura_Tads/webresources/produtos/allprodutos";
        DataGetter dt = new DataGetter(context, "produto");
        dt.execute(url);
    }
}
