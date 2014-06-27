package com.mycards.app;

import com.mycards.business.Model;

/**
 * Created by Takeshi on 25/06/2014.
 */
public class Parametros {

    private static Parametros parametros = null;
    public static Model model;
    public static Integer posicaoMenu;
    public static String IP;

    private Parametros() { }


    public static Parametros getInstance() {
        if (parametros == null) {
            parametros = new Parametros();
            parametros.IP = "10.199.38.61";
            //parametros.IP = "192.168.0.103";
        }
        return parametros;
    }
}
