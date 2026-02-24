package br.ueg.mobile.exitus.utils;

import android.app.Activity;

/**
 * Created by jonas on 23/08/2018.
 */

public class Preferencias {

    @Deprecated
    public static String getString(Activity activity, String pref) {
        return activity.getSharedPreferences("exitus_prefs", Activity.MODE_PRIVATE).getString(pref, "");
    }

    @Deprecated
    public static void putString(Activity activity, String pref, String valor) {
        activity.getSharedPreferences("exitus_prefs", Activity.MODE_PRIVATE).edit().putString(pref, valor).apply();
    }

    public static String getString(String pref) {
        return MyApplication.getInstancia().getApplicationContext().getSharedPreferences("exitus_prefs", Activity.MODE_PRIVATE).getString(pref, "");
    }

    public static void putString(String pref, String valor) {
        MyApplication.getInstancia().getApplicationContext().getSharedPreferences("exitus_prefs", Activity.MODE_PRIVATE).edit().putString(pref, valor).apply();
    }

    public static Long getLong(String pref) {
        return MyApplication.getInstancia().getApplicationContext().getSharedPreferences("exitus_prefs", Activity.MODE_PRIVATE).getLong(pref, -1);
    }

    public static void putLong(String pref, Long valor) {
        MyApplication.getInstancia().getApplicationContext().getSharedPreferences("exitus_prefs", Activity.MODE_PRIVATE).edit().putLong(pref, valor).apply();
    }


}