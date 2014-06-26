package com.mycards003.app;

import android.content.Intent;

/**
 * Created by Takeshi on 25/06/2014.
 */
public class Parametros {

    private static Parametros parametros = null;
    public static String nm_banco;

    private Parametros() {

    }

    public static Parametros getInstance() {
        if (parametros == null) {
            parametros = new Parametros();
        }
        return parametros;
    }

}
