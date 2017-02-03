package com.example.vinicius.paradad;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinicius on 01/02/2017.
 */

public class HttpRequest extends AsyncTask<String, Void, HttpURLConnection>{


    @Override
    protected HttpURLConnection doInBackground(String... params) {
        final int SEGUNDOS = 1000;
        try {

            URL url = new URL(params[0]);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10 * SEGUNDOS);
            conexao.setConnectTimeout(15 * SEGUNDOS);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();
            return conexao;
        }

        catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

    private static HttpURLConnection connectar(String urlArquivo) throws IOException{

        final int SEGUNDOS = 1000;

        URL url= new URL(urlArquivo);
        HttpURLConnection conexao = (HttpURLConnection)url.openConnection();
        conexao.setReadTimeout(10*SEGUNDOS);
        conexao.setConnectTimeout(15*SEGUNDOS);
        conexao.setRequestMethod("GET");
        conexao.setDoInput(true);
        conexao.setDoOutput(false);
        conexao.connect();
        return conexao;

    }

    protected void onPostExecute(HttpURLConnection conexao) {
        int resposta= 0;
        try {
            resposta = conexao.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean temConexao(Context ctx){
        ConnectivityManager cm= (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return  (info != null && info.isConnected());
    }

    /*public static List<ParadaPlace> carregarParada(LatLng latLng){

        String latitude = String.valueOf(latLng.latitude);
        String longitude = String.valueOf(latLng.longitude);

        String url_parada_json=
                "https://maps.googleapis.com/maps/api/place/radarsearch/json?location="
                        +latitude+","+longitude+
                        "&radius=50&types=bus_station&key=AIzaSyBNaYNpbJWoW5CmK4jYhUDHGdWfAlwLf2o";

        new HttpRequest().execute(url_parada_json);

        try{


            int resposta= conexao.getResponseCode();
            if( resposta == HttpURLConnection.HTTP_OK){
                InputStream is = conexao.getInputStream();
                JSONObject json= new JSONObject(bytesParaString(is));
                return lerJsonParadas(json);

            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
*/
    public static  List<ParadaPlace> lerJsonParadas(JSONObject json) throws JSONException{
        List<ParadaPlace> listaDeParadas= new ArrayList<ParadaPlace>();

        String results;

        JSONArray jsonParadas = json.getJSONArray("results");
        for (int i=0 ; i < jsonParadas.length();i++){
            JSONObject jsonResult= jsonParadas.getJSONObject(i);

            String id= jsonResult.getString("id");
            String name= jsonResult.getString("name");
            String place_id=jsonResult.getString("place_id");

            ParadaPlace parada = new ParadaPlace();

            parada.setId(id);
            parada.setName(name);
            parada.setPlace_id(place_id);

            listaDeParadas.add(parada);

        }

        return listaDeParadas;
    }

    private static String  bytesParaString(InputStream is) throws  IOException{
        byte[] buffer = new byte[1024];

        ByteArrayOutputStream bufferzao= new ByteArrayOutputStream();

        int bytesLidos;

        while((bytesLidos = is.read(buffer)) != -1){
            bufferzao.write(buffer, 0, bytesLidos);
        }

        return  new String(bufferzao.toByteArray(),"UTF-8");
    }
}
