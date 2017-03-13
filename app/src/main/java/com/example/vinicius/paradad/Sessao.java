package com.example.vinicius.paradad;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Vinicius on 19/02/2017.
 */

public class Sessao {
    private static Sessao instancia= new Sessao();
    private Parada parada = null;

    private Sessao(){

    }

    public static Sessao getInstancia(){
        return instancia;
    }


    public Parada getParada() {
        return parada;
    }

    public void setParada(Parada parada) {
        this.parada = parada;
    }

}
