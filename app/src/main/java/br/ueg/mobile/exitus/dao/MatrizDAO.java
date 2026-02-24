package br.ueg.mobile.exitus.dao;

import android.content.Context;

import br.ueg.mobile.exitus.modelo.Matriz;

/**
 * Created by jonas on 19/11/2018.
 */

public class MatrizDAO extends DAO<Matriz, Long> {

    public MatrizDAO(Context context, Class<Matriz> classeModelo) throws Exception {
        super(context, classeModelo);
    }
}
