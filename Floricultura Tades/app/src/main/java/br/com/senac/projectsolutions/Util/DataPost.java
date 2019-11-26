package br.com.senac.projectsolutions.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Model.Endereco;
import br.com.senac.projectsolutions.Model.Pagamento;
import br.com.senac.projectsolutions.Model.Usuario;
import br.com.senac.projectsolutions.View.CadastroActivity;
import br.com.senac.projectsolutions.View.PagamentoCarrinhoActivity;
import br.com.senac.projectsolutions.View.PerfilActivity;

public class DataPost extends AsyncTask<String, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private String metodo;

    public DataPost(Context context, String metodo) {
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

            if (!json.getBoolean("status")) {
                status = false;
            } else if (metodo.equals("cadastro_usuario")) {
                JSONArray dataResponse = json.getJSONArray("userInfo");
                JSONObject usuario = (JSONObject) dataResponse.get(0);
                user = new Usuario(
                        usuario.getInt("id"),
                        usuario.getString("nome"),
                        usuario.getString("email"),
                        usuario.getString("cpf")
                );
                ((CadastroActivity) context).onServidorResponse(msg, status, user);
            } else if (metodo.equals("cadastro_venda")) {
                ((PagamentoCarrinhoActivity) context).onServidorResponse(status, null, "cadastro_venda");
            } else if (metodo.equals("cadastro_endereco") || metodo.equals("atualizar_cadastro") || metodo.equals("cadastro_pagamento")) {
                ArrayList<Endereco> enderecos = new ArrayList<>();
                ArrayList<Pagamento> pagamentos = new ArrayList<>();
                Usuario userPerfil;

                JSONArray dataResponseUser = json.getJSONArray("infoUser");
                int k = 0;
                do {
                    JSONObject usuarioAux = (JSONObject) dataResponseUser.get(k);
                    userPerfil = new Usuario(
                            usuarioAux.getInt("codigo"),
                            usuarioAux.getString("nome"),
                            usuarioAux.getString("email"),
                            usuarioAux.getString("cpf")
                    );
                    k++;
                } while (k < dataResponseUser.length());

                JSONArray dataResponse = json.getJSONArray("enderecosUser");
                int i = 0;
                do {
                    JSONObject enderecoAux = (JSONObject) dataResponse.get(i);
                    Endereco endereco = new Endereco(
                            enderecoAux.getInt("codigo"),
                            enderecoAux.getString("logradouro"),
                            enderecoAux.getInt("numero"),
                            enderecoAux.getString("complemento"),
                            enderecoAux.getString("cep"),
                            enderecoAux.getString("estado"),
                            enderecoAux.getString("cidade"),
                            enderecoAux.getString("bairro"),
                            enderecoAux.getString("tipo")
                    );
                    enderecos.add(endereco);
                    i++;
                } while (i < dataResponse.length());

                JSONArray dataResponse2 = json.getJSONArray("pagamentosUser");
                int j = 0;
                do {
                    JSONObject pagamentosAux = (JSONObject) dataResponse2.get(j);
                    Pagamento pagamento = new Pagamento();
                    pagamento.setNumeroPagamento(pagamentosAux.getString("numPagamento"));
                    pagamento.setNomeTitular(pagamentosAux.getString("nomeTitular"));
                    pagamento.setDataVencimento(pagamentosAux.getString("dtVencimento"));
                    pagamento.setTipoPagamento(pagamentosAux.getString("tipoPagamento"));

                    pagamentos.add(pagamento);
                    j++;
                } while (j < dataResponse2.length());

                ((PerfilActivity) context).onServidorResponse(status, userPerfil, enderecos, pagamentos, metodo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
