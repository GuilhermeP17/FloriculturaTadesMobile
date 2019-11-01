package br.com.senac.projectsolutions.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.senac.projectsolutions.Model.Usuario;
import br.com.senac.projectsolutions.View.CadastroActivity;

public class DataPost extends AsyncTask<String, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public DataPost(Context context) {
        this.context = context;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        JSONObject usuarioJson = null;
        try {
            usuarioJson = new JSONObject(strings[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return NetworkToolkit.doPost(url, usuarioJson);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        boolean status = true;
        String msg = "";
        Usuario user = null;
        try {
            JSONObject json = new JSONObject(s);
            msg = json.getString("msgStatus");
            if (!json.getBoolean("status")){
                status = false;
            }else{
                JSONArray dataResponse = json.getJSONArray("userInfo");
                JSONObject usuario = (JSONObject) dataResponse.get(0);
                user = new Usuario(
                        usuario.getInt("id"),
                        usuario.getString("nome"),
                        usuario.getString("email"),
                        usuario.getString("cpf")
                );
            }
            ((CadastroActivity)context).onServidorResponse(msg, status, user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
