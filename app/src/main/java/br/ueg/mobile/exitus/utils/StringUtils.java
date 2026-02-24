package br.ueg.mobile.exitus.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by jonas on 09/11/2018.
 */

public class StringUtils {
    private static StringUtils instancia;

    private StringUtils(){}

    public static StringUtils getInstancia(){
        if(instancia == null){
            instancia = new StringUtils();
        }
        return instancia;
    }

    public String fromISO8859ToUTF8(String string){
        try {
            return new String(string.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return string;
        }
    }
}
