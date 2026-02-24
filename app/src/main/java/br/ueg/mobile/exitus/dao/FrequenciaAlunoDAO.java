package br.ueg.mobile.exitus.dao;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import br.ueg.mobile.exitus.modelo.FrequenciaAluno;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

public class FrequenciaAlunoDAO extends DAO<FrequenciaAluno, Long> {

    public FrequenciaAlunoDAO(Context context, Class<FrequenciaAluno> classeModelo) throws Exception {
        super(context, classeModelo);
//        TableUtils.dropTable(modeloDAO, true);
    }

    public Retorno deletarFrequenciaAluno(Long... frequenciaIds){
        try {
            DeleteBuilder<FrequenciaAluno, Long> deleteBuilder = modeloDAO.deleteBuilder();
            deleteBuilder.where().in("frequencia_id", (Object[]) frequenciaIds);
            boolean ok = modeloDAO.delete(deleteBuilder.prepare()) >= 0;
            return new Retorno(ok);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Retorno(false, e.getMessage());
        }
    }
}
