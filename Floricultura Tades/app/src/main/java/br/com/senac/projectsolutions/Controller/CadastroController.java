package br.com.senac.projectsolutions.Controller;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.senac.projectsolutions.R;
import br.com.senac.projectsolutions.Util.DataPost;

public class CadastroController {

    public void validaLogin(Context context){
        String url = context.getResources().getString(R.string.web_service_path).concat("usuarios/cadastrar");
        DataPost dt = new DataPost(context);

        JSONObject json = new JSONObject();
        try{
            json.put("nome", "guilherme");
        }catch (JSONException e){
            e.printStackTrace();
        }
        dt.execute(url, json.toString());
    }
}
