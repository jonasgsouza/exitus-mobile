package br.ueg.mobile.exitus.sincronizador;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ueg.exitus.sincronizador.tarefa.ITarefaAgendada;
import br.ueg.exitus.sincronizador.tarefa.ITarefaDAO;
import br.ueg.exitus.sincronizador.utils.SyncResult;
import br.ueg.mobile.exitus.dao.DatabaseHelper;
import br.ueg.mobile.exitus.sincronizador.tarefa.Tarefa;
import br.ueg.mobile.exitus.sincronizador.tarefa.TarefaAgendadaDepende;

/**
 * Created by jonas on 18/11/2018.
 */

public class TarefaDAO implements ITarefaDAO {
    protected Context context;
    protected Dao<Tarefa, Integer> tarefaDAO;
    protected Dao<TarefaAgendadaDepende, Integer> tarefaDependeDAO;

    public TarefaDAO(Context context) {
        try {
            this.context = context;
            tarefaDAO = DaoManager.createDao(DatabaseHelper.getInstance(context).getConnectionSource(), Tarefa.class);
            tarefaDependeDAO = DaoManager.createDao(DatabaseHelper.getInstance(context).getConnectionSource(), TarefaAgendadaDepende.class);
            if(!tarefaDependeDAO.isTableExists()){
                TableUtils.createTable(DatabaseHelper.getInstance(context).getConnectionSource(), TarefaAgendadaDepende.class);
            }
            if (!tarefaDAO.isTableExists()) {
                TableUtils.createTable(DatabaseHelper.getInstance(context).getConnectionSource(), Tarefa.class);
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Não foi possível instanciar o DAO");
        }
    }

    @Override
    public SyncResult getTarefasPendentes() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "P");
        try {
            List<Tarefa> lista = tarefaDAO.queryForFieldValues(map);
            return new SyncResult<>(lista, true);
        } catch (SQLException e) {
            e.printStackTrace();
            return new SyncResult<>(false);
        }
    }

    @Override
    public SyncResult atualizarTarefaAgendada(ITarefaAgendada iTarefaAgendada) {
        Tarefa tarefa = (Tarefa) iTarefaAgendada;
        try {
            boolean ok = tarefaDAO.update(tarefa) > 0;
            return new SyncResult(ok);
        } catch (SQLException e) {
            e.printStackTrace();
            return new SyncResult(false);
        }
    }

    @Override
    public SyncResult persistirTarefaAgendada(ITarefaAgendada iTarefaAgendada) {
        Tarefa tarefa = (Tarefa) iTarefaAgendada;
        try {
            boolean ok = tarefaDAO.create(tarefa) == 1;
            return new SyncResult(ok);
        } catch (SQLException e) {
            e.printStackTrace();
            return new SyncResult(false);
        }
    }

    public void limparTabela(){
        try {
            tarefaDependeDAO.delete(tarefaDependeDAO.deleteBuilder().prepare());
            tarefaDAO.delete(tarefaDAO.deleteBuilder().prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
