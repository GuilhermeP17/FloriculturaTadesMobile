package br.com.senac.projectsolutions.Controller;

import android.content.Context;

import br.com.senac.projectsolutions.R;
import br.com.senac.projectsolutions.Util.DataGetter;

public class LoginController {

    public void validaLogin(Context context, String email, String senha){
        String url = context.getResources().getString(R.string.web_service_path).concat("usuarios/validausuario/").concat(email + "/" + senha);
        DataGetter dt = new DataGetter(context, "login");
        dt.execute(url);
    }
}
