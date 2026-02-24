package br.ueg.mobile.exitus.dao;

import android.content.Context;

import br.ueg.mobile.exitus.modelo.Professor;

/**
 * Created by jonas on 09/06/2018.
 */

public class ProfessorDAO extends DAO<Professor, Long> {

    public ProfessorDAO(Context context, Class<Professor> classeModelo) throws Exception {
        super(context, classeModelo);
    }
}
