package br.ueg.mobile.exitus.sincronizador;

import java.sql.SQLException;

import br.ueg.exitus.sincronizador.GestorDeTarefas;
import br.ueg.exitus.sincronizador.MonitoradorAbstract;
import br.ueg.exitus.sincronizador.tarefa.ITarefaDAO;
import br.ueg.mobile.exitus.utils.MyApplication;

/**
 * Created by jonas on 18/11/2018.
 */

public class GestorDeTarefasApp extends GestorDeTarefas {

    private static GestorDeTarefasApp instancia;

    private GestorDeTarefasApp() {
    }

    public static GestorDeTarefasApp getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeTarefasApp();
        }
        return instancia;
    }

    @Override
    protected ITarefaDAO getTarefaDAO() {
        return new TarefaDAO(MyApplication.getInstancia().getApplicationContext());
    }

    @Override
    protected MonitoradorAbstract getMonitorador() {
        return new Monitorador();
    }
}
