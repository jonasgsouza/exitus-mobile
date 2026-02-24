package br.ueg.mobile.exitus.sincronizador.tarefa;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tarefa_agendada_depende")
public class TarefaAgendadaDepende {

    @DatabaseField(foreign=true, columnName = "tarefa_pai", foreignAutoRefresh = true, maxForeignAutoRefreshLevel=1)
    private Tarefa tarefaPai;

    @DatabaseField(foreign=true, columnName = "tarefa_filha", foreignAutoRefresh = true, maxForeignAutoRefreshLevel=1)
    private Tarefa tarefaFilha;

    public TarefaAgendadaDepende(){}

    public Tarefa getTarefaPai() {
        return tarefaPai;
    }

    public void setTarefaPai(Tarefa tarefaPai) {
        this.tarefaPai = tarefaPai;
    }

    public Tarefa getTarefaFilha() {
        return tarefaFilha;
    }

    public void setTarefaFilha(Tarefa tarefaFilha) {
        this.tarefaFilha = tarefaFilha;
    }
}
