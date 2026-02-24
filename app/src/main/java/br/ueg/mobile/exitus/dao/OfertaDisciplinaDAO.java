package br.ueg.mobile.exitus.dao;

import android.content.Context;

import br.ueg.mobile.exitus.modelo.OfertaDisciplina;

public class OfertaDisciplinaDAO extends DAO<OfertaDisciplina, Long> {
    public OfertaDisciplinaDAO(Context context, Class<OfertaDisciplina> classeModelo) throws Exception {
        super(context, classeModelo);
    }
}
