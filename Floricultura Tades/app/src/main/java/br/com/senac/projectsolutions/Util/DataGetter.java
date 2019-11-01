package br.com.senac.projectsolutions.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.Model.Usuario;
import br.com.senac.projectsolutions.View.LoginActivity;
import br.com.senac.projectsolutions.View.MainActivity;

public class DataGetter extends AsyncTask<String, Void, String> {
    private Context context;
    private String metodo;

    public DataGetter(Context context, String metodo) {
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
            if (metodo.equalsIgnoreCase("login")) {
                //Caso Request seja de Login
                JSONObject json = new JSONObject(s);
                if (!json.getBoolean("statusLogin")) {
                    status = false;
                    msg = json.getString("msgErro");
                } else {
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
                ((LoginActivity) context).onResponse(status, msg, user);
            } else {
                //Caso Request seja de Produto
                JSONObject json = new JSONObject(s);
                ArrayList<Produto> produtos = null;
                if (!json.getBoolean("statusRequest")) {
                    status = false;
                    msg = json.getString("msgErro");
                } else {
                    produtos = new ArrayList<>();
                    JSONArray dataResponse = json.getJSONArray("produtosAtivos");
                    int i = 0;
                    do {
                        JSONObject auxProduto = (JSONObject) dataResponse.get(i);
                        Produto produto = new Produto(
                                auxProduto.getInt("id"),
                                auxProduto.getString("nome"),
                                auxProduto.getString("descricao"),
                                auxProduto.getString("tipo"),
                                auxProduto.getInt("quantidade"),
                                auxProduto.getDouble("valor")
                        );
                        produtos.add(produto);
                        i++;
                    }while (i < dataResponse.length());
                }
                ((MainActivity) context).onServidorResponse(status, msg, produtos);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Falha ao comunicar-se com o servidor, tente novamnete", Toast.LENGTH_LONG).show();
        }
    }
}
