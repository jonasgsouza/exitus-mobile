package br.ueg.mobile.exitus.utils;

import android.os.StrictMode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class ConnectionUtils {

    public static boolean isOnline() {
//        ConnectivityManager cm = (ConnectivityManager) MyApplication.getInstancia().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            int timeoutMs = 5000;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);
            sock.connect(sockaddr, timeoutMs);
            sock.close();
            return true;
        } catch (IOException e) { return false; }
//        return netInfo != null && netInfo.isConnected();
    }
}
