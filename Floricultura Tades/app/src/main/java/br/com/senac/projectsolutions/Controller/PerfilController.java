package br.com.senac.projectsolutions.Controller;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.senac.projectsolutions.R;
import br.com.senac.projectsolutions.Util.DataGetter;

public class PerfilController {

    public void getInfoUsuario(Context context){
        String url = context.getResources().getString(R.string.web_service_path).concat("");
        DataGetter dt = new DataGetter(context, "usuario");
        dt.execute(url);
    }

    public void getInfoEndereco(Context context, String metodo){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SessaoUsuario", Context.MODE_PRIVATE);

        String url = context.getResources().getString(R.string.web_service_path).concat("/usuarios/enderecos/" + sharedPreferences.getInt("codigo", 0));
        DataGetter dt = new DataGetter(context, metodo);
        dt.execute(url);
    }

    public void getPedidosAndamento(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SessaoUsuario", Context.MODE_PRIVATE);

        String url = context.getResources().getString(R.string.web_service_path).concat("/venda/pedidos/andamento/" + sharedPreferences.getInt("codigo", 0));
        DataGetter dt = new DataGetter(context, "pedidos_andamento");
        dt.execute(url);
    }
}
