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

    public void getInfoPerfil(Context context, String metodo){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SessaoUsuario", Context.MODE_PRIVATE);

        String url = context.getResources().getString(R.string.web_service_path).concat("/usuarios/perfil/" + sharedPreferences.getInt("codigo", 0));
        DataGetter dt = new DataGetter(context, metodo);
        dt.execute(url);
    }

    public void getPagamentos(Context context, String metodo){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SessaoUsuario", Context.MODE_PRIVATE);

        String url = context.getResources().getString(R.string.web_service_path).concat("/usuarios/perfil/" + sharedPreferences.getInt("codigo", 0));
        DataGetter dt = new DataGetter(context, metodo);
        dt.execute(url);
    }

    public void getPedidosAndamento(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SessaoUsuario", Context.MODE_PRIVATE);

        String url = context.getResources().getString(R.string.web_service_path).concat("/venda/pedidos/andamento/" + sharedPreferences.getInt("codigo", 0));
        DataGetter dt = new DataGetter(context, "pedidos_andamento");
        dt.execute(url);
    }

    public void getPedidosFinalizados(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SessaoUsuario", Context.MODE_PRIVATE);

        String url = context.getResources().getString(R.string.web_service_path).concat("/venda/pedidos/finalizados/" + sharedPreferences.getInt("codigo", 0));
        DataGetter dt = new DataGetter(context, "pedidos_finalizados");
        dt.execute(url);
    }
}
