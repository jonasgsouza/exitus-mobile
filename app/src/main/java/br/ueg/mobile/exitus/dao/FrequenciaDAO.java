package br.ueg.mobile.exitus.dao;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.ueg.mobile.exitus.modelo.Frequencia;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

public class FrequenciaDAO extends DAO<Frequencia, Long> {
    
    public FrequenciaDAO(Context context, Class<Frequencia> classeModelo) throws Exception {
        super(context, classeModelo);
//        TableUtils.dropTable(modeloDAO, true);
    }

    public Retorno substituirFrequencia(List<Frequencia> substituir, List<Frequencia> nova){

        return null;
    }

    public Retorno deletarFrequencia(Long... ids){
        try {
            DeleteBuilder<Frequencia, Long> deleteBuilder = modeloDAO.deleteBuilder();
            deleteBuilder.where().in("id", ids);
            boolean ok = modeloDAO.delete(deleteBuilder.prepare()) >= 0;
            return new Retorno(ok);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Retorno(false, e.getMessage());
        }
    }
}
