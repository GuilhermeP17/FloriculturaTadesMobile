package br.com.senac.projectsolutions.Controller;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.senac.projectsolutions.R;
import br.com.senac.projectsolutions.Util.DataPost;

public class CadastroController {

    public void validaLogin(Context context, String[] camposFormulario) {
        String url = context.getResources().getString(R.string.web_service_path).concat("usuarios/cadastrar/usuario");
        DataPost dt = new DataPost(context, "cadastro_usuario");

        JSONObject json = new JSONObject();
        try {
            json.put("nome", camposFormulario[0] + " " + camposFormulario[1]);
            json.put("email",camposFormulario[2]);
            json.put("CPF", camposFormulario[3]);
            json.put("senha", camposFormulario[4]);
            json.put("logradouro", camposFormulario[5]);
            json.put("numero", camposFormulario[6]);
            json.put("cep", camposFormulario[7]);
            json.put("complemento", camposFormulario[8]);
            json.put("estado", camposFormulario[9]);
            json.put("cidade", camposFormulario[10]);
            json.put("bairro", camposFormulario[11]);
            json.put("tipoEndereco", camposFormulario[12]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dt.execute(url, json.toString());
    }
}
