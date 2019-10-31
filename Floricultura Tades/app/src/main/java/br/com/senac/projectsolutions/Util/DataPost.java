package br.com.senac.projectsolutions.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class DataPost extends AsyncTask<String, Void, String> {
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
        try {
            JSONObject json = new JSONObject(s);
            String mensagem = json.getString("msgStatus");
            Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
