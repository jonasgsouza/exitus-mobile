package br.ueg.mobile.exitus.dao;

import android.content.Context;

import br.ueg.mobile.exitus.modelo.OfertaDisciplinaProfessor;

public class OfertaDisciplinaProfessorDAO extends DAO<OfertaDisciplinaProfessor, Long> {
    public OfertaDisciplinaProfessorDAO(Context context, Class<OfertaDisciplinaProfessor> classeModelo) throws Exception {
        super(context, classeModelo);
    }
}
