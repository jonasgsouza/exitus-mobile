package br.ueg.mobile.exitus.dao;

import android.content.Context;

import br.ueg.mobile.exitus.modelo.OfertaCurso;

public class OfertaCursoDAO extends DAO<OfertaCurso, Long> {
    public OfertaCursoDAO(Context context, Class<OfertaCurso> classeModelo) throws Exception {
        super(context, classeModelo);
    }
}
