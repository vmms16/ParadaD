package com.example.vinicius.paradad.notificacoes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.R;

import com.example.vinicius.paradad.Parada;
import com.example.vinicius.paradad.Sessao;
import com.example.vinicius.paradad.main.MapsFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

public class ConfirmacaoDialogFragment extends DialogFragment{

    private Sessao sessao= Sessao.getInstancia();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        LayoutInflater li = getActivity().getLayoutInflater();

        View title= li.inflate(com.example.vinicius.paradad.R.layout.activity_title,null);
        View view = li.inflate(com.example.vinicius.paradad.R.layout.activity_ativacao, null);

        final Switch statusAlarme = (Switch) view.findViewById(com.example.vinicius.paradad.R.id.switchAlarme);

        statusAlarme.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    MapsFragment.ativaAlarme();
                    statusAlarme.setText("Alarme Ativado");
                }else{
                    MapsFragment.desativaAlarme();
                    statusAlarme.setText("Alarme Desativado");
                }

            }
        });

        MarkerOptions alarme = MapsFragment.alarme;
        Parada paradaSelecionada = sessao.getParada();

        if(alarme != null){

            LatLng posicaoAlarme = alarme.getPosition();
            LatLng posicaoParada = paradaSelecionada.getLocation();

            if (posicaoAlarme.equals(posicaoParada)){
                statusAlarme.setChecked(true);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCustomTitle(title);
        builder.setView(view);

        return builder.create();
    }
}
