package br.ueg.mobile.exitus.sincronizador;

import android.content.Intent;

import java.io.Serializable;
import java.util.List;

import br.ueg.exitus.sincronizador.MonitoradorAbstract;
import br.ueg.exitus.sincronizador.tarefa.ITarefaAgendada;
import br.ueg.exitus.sincronizador.tarefa.ITarefaDAO;
import br.ueg.mobile.exitus.sincronizador.tarefa.Tarefa;
import br.ueg.mobile.exitus.utils.MonitoradorAndroidService;
import br.ueg.mobile.exitus.utils.MyApplication;

/**
 * Created by jonas on 18/11/2018.
 */

public class Monitorador extends MonitoradorAbstract implements Serializable {

    @Override
    public void iniciarMonitoramento(List<ITarefaAgendada<?>> list) {
        MonitoradorAndroidService.monitorador = this;
        MonitoradorAndroidService.tarefaAgendadas = list;
        MyApplication.getInstancia().startService(getIntent());
    }

    @Override
    protected void esperar() {
        MonitoradorAndroidService.podeExecutar = false;
//        MyApplication.getInstancia().stopService(getIntent());
    }

    @Override
    public void notificar() {}

    private Intent getIntent(){
        return new Intent(MyApplication.getInstancia(), MonitoradorAndroidService.class);
    }

    @Override
    public boolean isWaiting() {
        return false;
    }

    @Override
    public boolean isAlive() {
        return MonitoradorAndroidService.executando;
    }

//    @Override
//    public void iniciarTarefa(ITarefaAgendada<?> tarefa, ITarefaDAO<ITarefaAgendada<?>> tarefaDAO) {
//        Tarefa tarefaCasted = (Tarefa) tarefa;
//        System.out.println("Colocando em execucao: " + tarefa);
//        tarefa.setStatus("E");
//        tarefa.setTarefaDAO(tarefaDAO);
//        tarefaCasted.iniciarExecucaoLooper(MonitoradorAndroidService.looper);
//    }
}
