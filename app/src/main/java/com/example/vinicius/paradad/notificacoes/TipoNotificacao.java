package com.example.vinicius.paradad.notificacoes;

/**
 * Created by Vinicius on 12/03/2017.
 */

public enum TipoNotificacao {

    proximo(1), muitoProximo(2), chegou(3);

    private final int valor;

    TipoNotificacao(int i) {
        valor = i;

    }

    public int getValor(){
        return valor;
    }


}
