package br.ueg.mobile.exitus.utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Looper;

import java.util.List;

import br.ueg.exitus.sincronizador.tarefa.ITarefaAgendada;
import br.ueg.mobile.exitus.sincronizador.Monitorador;

public class MonitoradorAndroidService extends IntentService {
    public static boolean executando;
    public static boolean podeExecutar;
    public static Monitorador monitorador;
    public static List<ITarefaAgendada<?>> tarefaAgendadas;
    public static Looper looper;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MonitoradorAndroidService(String name) {
        super(name);
    }

    public MonitoradorAndroidService() {
        super("");
    }

    @Override
    public void onCreate() {
        executando = true;
        System.out.println("Criando serviço...");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        executando = false;
        looper = null;
        System.out.println("Destruindo serviço...");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        looper = Looper.myLooper();
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            executando = true;
            podeExecutar = true;
            System.out.println("Iniciando loop de monitoramento");
            while(podeExecutar) {
                if(tarefaAgendadas != null){
                    monitorador.executar(tarefaAgendadas);
                }
                System.out.println("Esperando 15 segundos...");
                Thread.sleep(15000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executando = false;
            System.out.println("Finalizando loop de monitoramento");
            Thread.currentThread().interrupt();
        }
        System.out.println("Finalizando loop de monitoramento");
        executando = false;
    }
}
