package br.com.senac.projectsolutions.Controller;

import android.content.Context;

import br.com.senac.projectsolutions.Util.DataGetter;

public class LoginController {

    public void validaLogin(Context context, String email, String senha){
        String url = "http://192.168.0.13:8084/Api_Floricultura_Tads/webresources/usuarios/validausuario/"+email+"/"+senha;
        DataGetter dt = new DataGetter(context, "login");
        dt.execute(url);
    }
}
