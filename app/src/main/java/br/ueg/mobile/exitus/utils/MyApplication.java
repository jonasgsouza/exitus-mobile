package br.ueg.mobile.exitus.utils;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication instancia;

    @Override
    public void onCreate() {
        super.onCreate();
        instancia = this;
    }

    public static MyApplication getInstancia(){
        return instancia;
    }

}
