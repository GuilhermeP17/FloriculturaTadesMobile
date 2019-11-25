package br.com.senac.projectsolutions.Controller;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import br.com.senac.projectsolutions.R;
import br.com.senac.projectsolutions.Util.DataGetter;
import br.com.senac.projectsolutions.Util.DataPost;

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

    public void postCadatrarVenda(Context context, JSONObject json){
        String url = context.getResources().getString(R.string.web_service_path).concat("/venda/cadastrar");
        DataPost dt = new DataPost(context, "cadastro_venda");

        dt.execute(url, json.toString());
    }

    public void postAtualizarCadastro(Context context, JSONObject json){
        String url = context.getResources().getString(R.string.web_service_path).concat("/usuarios/alterar/usuario");
        DataPost dt = new DataPost(context, "atualizar_cadastro");

        dt.execute(url, json.toString());
    }

    public void postAtualizarEndereco(Context context, JSONObject json){
        String url = context.getResources().getString(R.string.web_service_path).concat("/usuarios/alterar/endereco");
        DataPost dt = new DataPost(context, "atualizar_endereco");

        dt.execute(url, json.toString());
    }

    public void postCadatrarEndereco(Context context, JSONObject json){
        String url = context.getResources().getString(R.string.web_service_path).concat("/usuarios/cadastrar/endereco");
        DataPost dt = new DataPost(context, "cadastro_endereco");

        dt.execute(url, json.toString());
    }

    public void postAtualizarPagamento(Context context, JSONObject json){
        String url = context.getResources().getString(R.string.web_service_path).concat("/usuarios/alterar/pagamento");
        DataPost dt = new DataPost(context, "atualizar_pagamento");

        dt.execute(url, json.toString());
    }

    public void postCadatrarPagamento(Context context, JSONObject json){
        String url = context.getResources().getString(R.string.web_service_path).concat("/usuarios/cadastrar/pagamento");
        DataPost dt = new DataPost(context, "cadastro_pagamento");

        dt.execute(url, json.toString());
    }
}
