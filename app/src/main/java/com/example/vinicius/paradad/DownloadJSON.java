package com.example.vinicius.paradad;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Vinicius on 03/02/2017.
 */

public class DownloadJSON extends AsyncTask<String, Void, JSONObject> {

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        String urlString = params[0];

        final int SEGUNDOS = 1000;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10 * SEGUNDOS);
            conexao.setConnectTimeout(15 * SEGUNDOS);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();

            int resposta = conexao.getResponseCode();

            JSONObject json = null;

            if (resposta == HttpURLConnection.HTTP_OK) {
                InputStream is = conexao.getInputStream();
                json = new JSONObject(bytesParaString(is));
            }

            return json;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(JSONObject json){

        try {
            JSONArray jsonParadas = json.getJSONArray("results");
            String id = "Sem id";

            if(jsonParadas.length() > 0){
                JSONObject jsonResult = jsonParadas.getJSONObject(0);
                id = jsonResult.getString("id");
            }

            MapsFragment.imprimeId(id);
        }catch(Exception e){
            e.printStackTrace();
        }



    }

    private static String bytesParaString(InputStream is) throws  IOException{
        byte[] buffer = new byte[1024];

        ByteArrayOutputStream bufferzao= new ByteArrayOutputStream();

        int bytesLidos;

        while((bytesLidos = is.read(buffer)) != -1){
            bufferzao.write(buffer, 0, bytesLidos);
        }

        return  new String(bufferzao.toByteArray(),"UTF-8");
    }


}
