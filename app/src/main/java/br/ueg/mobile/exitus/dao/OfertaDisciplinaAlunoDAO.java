package br.ueg.mobile.exitus.dao;

import android.content.Context;

import br.ueg.mobile.exitus.modelo.OfertaDisciplinaAluno;

public class OfertaDisciplinaAlunoDAO extends DAO<OfertaDisciplinaAluno, Long> {

    public OfertaDisciplinaAlunoDAO(Context context, Class<OfertaDisciplinaAluno> classeModelo) throws Exception {
        super(context, classeModelo);
    }

}
