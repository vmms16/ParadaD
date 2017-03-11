package com.example.vinicius.paradad.json;

import com.example.vinicius.paradad.Parada;
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
 * Created by Vinicius on 19/02/2017.
 */

public class HttpRequest {

    public static JSONObject jsonDownload(LatLng clickedPoint) {

        double latitude = clickedPoint.latitude;
        double longitude = clickedPoint.longitude;

        String latitudeString = String.valueOf(latitude);
        String longitudeString = String.valueOf(longitude);

        String url_parada_json =
                "https://maps.googleapis.com/maps/api/place/radarsearch/json?location="
                        + latitudeString + "," + longitudeString +
                        "&radius=50&types=bus_station&key=AIzaSyBNaYNpbJWoW5CmK4jYhUDHGdWfAlwLf2o";

        HttpURLConnection connection = connect(url_parada_json);

        JSONObject json = obterJSON(connection);

        return json;

    }

    private static HttpURLConnection connect(String urlString) {

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

            return conexao;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static JSONObject obterJSON(HttpURLConnection connection){

        try {
            int answer = connection.getResponseCode();

            if (answer == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(is));

                return json;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private static String bytesParaString(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];

        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();

        int bytesLidos;

        while ((bytesLidos = is.read(buffer)) != -1) {
            bufferzao.write(buffer, 0, bytesLidos);
        }

        return new String(bufferzao.toByteArray(), "UTF-8");
    }

    public static List<Parada> lerJson(JSONObject json) throws JSONException{
        List<Parada> listaDeParadas= new ArrayList<Parada>();

        String result="result";

        JSONArray jsonParadas= json.getJSONArray("results");
        for (int i=0; i<jsonParadas.length(); i++){
            JSONObject json_parada= jsonParadas.getJSONObject(i);

            Parada parada= new Parada();
            parada.setId(json_parada.getString("id"));
            parada.setPlace_id(json_parada.getString("place_id"));
            parada.setReference((json_parada.getString("reference")));

            JSONObject geometry= json_parada.getJSONObject("geometry");
            JSONObject location= geometry.getJSONObject("location");

            double latitude= location.getDouble("lat");
            double longitude= location.getDouble("lng");

            parada.setLocation( new LatLng(latitude,longitude));

            listaDeParadas.add(parada);

        }

        return  listaDeParadas;
    }






    public static int obterDistancia(LatLng origin) {

        double latitudeDestino = -8.01593;
        double longitudeDestino = -34.9455997 ;

        double latitudeOrigem = origin.latitude;
        double longitudeOrigem = origin.longitude;


        String latDestinoString = String.valueOf(latitudeDestino);
        String longDestinoString = String.valueOf(longitudeDestino);

        String latOriString = String.valueOf(latitudeOrigem);
        String longOriString = String.valueOf(longitudeOrigem);

        String url_parada_json =
                "https://maps.googleapis.com/maps/api/distancematrix/json?" +
                        "origins="+latOriString+","+longOriString +
                        "&destinations="+latDestinoString+","+longDestinoString+
                        "&mode=bus&key=AIzaSyBNaYNpbJWoW5CmK4jYhUDHGdWfAlwLf2o";

        HttpURLConnection connection = connect(url_parada_json);

        JSONObject json = obterJSON(connection);


        try {

            return lerJsonDistancia(json);

        }catch (Exception e){
            e.printStackTrace();
        }

        return Integer.parseInt(null);

    }


    public  static int lerJsonDistancia (JSONObject json) throws  JSONException{



        JSONArray jsonDistancias= json.getJSONArray("rows");


        JSONObject json_distancia= jsonDistancias.getJSONObject(0);

        JSONArray elements = json_distancia.getJSONArray("elements");
        JSONObject valores= elements.getJSONObject(0);
        JSONObject distance = valores.getJSONObject("distance");

        int distancia= distance.getInt("value");

        return distancia;




    }
}
