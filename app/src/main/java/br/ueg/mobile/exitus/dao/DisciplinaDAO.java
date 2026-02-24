package br.ueg.mobile.exitus.dao;

import android.content.Context;

import br.ueg.mobile.exitus.modelo.Disciplina;

public class DisciplinaDAO extends DAO<Disciplina, Long> {

    public DisciplinaDAO(Context context, Class<Disciplina> classeModelo) throws Exception {
        super(context, classeModelo);
    }
}
