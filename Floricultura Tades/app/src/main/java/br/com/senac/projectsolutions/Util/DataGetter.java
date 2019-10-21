package br.com.senac.projectsolutions.Util;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.senac.projectsolutions.Model.Usuario;
import br.com.senac.projectsolutions.Util.NetworkToolkit;
import br.com.senac.projectsolutions.View.LoginActivity;

public class DataGetter extends AsyncTask<String, Void, String> {
    private Context context;
    private String metodo;

    public DataGetter(Context context, String metodo){
        this.context = context;
        this.metodo = metodo;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        return NetworkToolkit.doGet(url);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        boolean status = true;
        String msg = "";
        Usuario user = null;

        try {
            if (metodo.equalsIgnoreCase("login")){
                JSONObject json = new JSONObject(s);
                if (!json.getBoolean("statusLogin")){
                    status = false;
                    msg = json.getString("msgErro");
                }else{
                    JSONArray dataResponse = json.getJSONArray("userInfo");
                    JSONObject usuario = (JSONObject) dataResponse.get(0);
                    user = new Usuario(
                            usuario.getInt("id"),
                            usuario.getString("nome"),
                            usuario.getString("email"),
                            usuario.getString("cpf")
                    );
                    msg = "Usuario encontrado com sucesso";
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ((LoginActivity)context).onResponse(status, msg, user);
    }
}
