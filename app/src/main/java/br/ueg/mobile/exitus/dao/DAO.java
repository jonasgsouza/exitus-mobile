package br.ueg.mobile.exitus.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.ueg.exitus.sincronizador.tarefa.Sincronizavel;
import br.ueg.exitus.sincronizador.utils.SyncResult;
import br.ueg.mobile.exitus.servico_cliente.Retorno;
import br.ueg.mobile.exitus.sincronizador.tarefa.Tarefa;
import br.ueg.mobile.exitus.utils.ObjectUtils;

/**
 * Created by jonas on 09/06/2018.
 */

public abstract class DAO<M extends Sincronizavel, ID> {
    protected Context context;
    protected Class<M> classeModelo;
    protected Dao<M, ID> modeloDAO;

    private DAO(){}

    public DAO(Context context, Class<M> classeModelo) throws Exception {
        this.context = context;
        this.classeModelo = classeModelo;
        modeloDAO = DaoManager.createDao(DatabaseHelper.getInstance(context).getConnectionSource(), classeModelo);
        if(!modeloDAO.isTableExists()){
            TableUtils.createTable(DatabaseHelper.getInstance(context).getConnectionSource(), classeModelo);
        }
    }

    public Retorno persistir(M modelo){
        Retorno ret = new Retorno();
        try {
            ret.setOk(modeloDAO.create(modelo) == 1);
        } catch (SQLException e) {
            ret.setMensagem(e.getMessage());
            ret.setOk(false);
        }
        return ret;
    }

    public Retorno atualizar(M modelo){
        Retorno ret = new Retorno();
        try {
            ret.setOk(modeloDAO.update(modelo) > 1);
        } catch (SQLException e) {
            ret.setMensagem(e.getMessage());
            ret.setOk(false);
        }
        return ret;
    }

    public Retorno salvarOuAtualizar(M modelo){
        Retorno ret = new Retorno();
        try {
            ret.setOk(modeloDAO.createOrUpdate(modelo).getNumLinesChanged() > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            ret.setMensagem(e.getMessage());
            ret.setOk(false);
        }
        return ret;
    }

    public Retorno buscar(ID id){
        Retorno ret = new Retorno();
        try {
            ret.setValor(modeloDAO.queryForId(id));
            ret.setOk(true);
        } catch (SQLException e) {
            e.printStackTrace();
            ret.setMensagem(e.getMessage());
            ret.setOk(false);
        }
        return ret;
    }

    public Retorno listar(){
        Retorno ret = new Retorno();
        try {
            ret.setLista((List) modeloDAO.queryForAll());
            ret.setOk(true);
        } catch (SQLException e) {
            e.printStackTrace();
            ret.setMensagem(e.getMessage());
            ret.setOk(false);
        }
        return ret;
    }

    public Retorno filtrarPorCampo(Map<String, Object> map){
        Retorno ret = new Retorno();
        try {
//            QueryBuilder<M, ID> midQueryBuilder = modeloDAO.queryBuilder();
//            Where<M, ID> where = midQueryBuilder.where();
//            int i = 0;
//            for (String key : map.keySet()) {
//                if(i == 0) {
//                    where.eq(key, map.get(key));
//                }else {
//                    where.and().eq(key, map.get(key));
//                }
//                i++;
//            }
            ret.setLista((List) modeloDAO.queryForFieldValues(map));
//            if(!ObjectUtils.isNullOrEmpty(getOrderByColumn())) {
//                midQueryBuilder.orderByRaw(getOrderByColumn() + " DESC");
//            }
//            ret.setLista((List) midQueryBuilder.query());
            ret.setOk(true);
        } catch (SQLException e) {
            e.printStackTrace();
            ret.setMensagem(e.getMessage());
            ret.setOk(false);
        }
        return ret;
    }

    public String getOrderByColumn(){
        return null;
    }

    public boolean limparTabela(){
        try {
            return modeloDAO.delete(modeloDAO.deleteBuilder().prepare()) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void dropTable(){
        try {
            TableUtils.dropTable(modeloDAO, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long numeroDeLinhas() {
        try {
            return modeloDAO.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
