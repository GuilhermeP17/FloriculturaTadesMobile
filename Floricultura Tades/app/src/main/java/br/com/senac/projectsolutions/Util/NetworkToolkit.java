package br.com.senac.projectsolutions.Util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NetworkToolkit {

    public static String doGet(String url){
        String retorno = "";
        try {
            URL apiEnd = new URL(url);
            int codigoResposta;
            HttpURLConnection conexao;
            InputStream inputStream;

            conexao = (HttpURLConnection) apiEnd.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.connect();

            codigoResposta = conexao.getResponseCode();
            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                inputStream = conexao.getInputStream();
            }else{
                inputStream = conexao.getErrorStream();
            }

            retorno = convertString(inputStream);
            inputStream.close();
            conexao.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public static String doPost(String url, JSONObject jsonSend){
        String retorno = "";
        try {
            URL apiEnd = new URL(url);
            int codigoResposta;
            HttpURLConnection conexao;
            InputStream inputStream;

            conexao = (HttpURLConnection) apiEnd.openConnection();
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setRequestProperty("Content-Type", "application/json");

            conexao.setRequestMethod("POST");
            conexao.setDoInput(true);
            conexao.setDoOutput(true);

            final OutputStream outputStream = conexao.getOutputStream();
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

            writer.write(jsonSend.toString());
            writer.flush();
            writer.close();
            outputStream.close();

            conexao.connect();

            codigoResposta = conexao.getResponseCode();
            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                inputStream = conexao.getInputStream();
            }else{
                inputStream = conexao.getErrorStream();
            }

            retorno = convertString(inputStream);
            inputStream.close();
            conexao.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }


    private static String convertString(InputStream response){
        StringBuffer buffer = new StringBuffer();
        try {
            String linha = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(response));
            linha = br.readLine();
            if (linha != null){
                buffer.append(linha);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
