package br.ueg.mobile.exitus.dao;

import android.content.Context;

import br.ueg.mobile.exitus.modelo.Aluno;

public class AlunoDAO extends DAO<Aluno, Long> {

    public AlunoDAO(Context context, Class<Aluno> classeModelo) throws Exception {
        super(context, classeModelo);
    }

    @Override
    public String getOrderByColumn() {
        return "nome";
    }
}
