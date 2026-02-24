package br.ueg.mobile.exitus.dao;

import android.content.Context;

import br.ueg.mobile.exitus.modelo.Curso;


/**
 * Created by jonas on 19/11/2018.
 */

public class CursoDAO extends DAO<Curso, Long> {

    public CursoDAO(Context context, Class<Curso> classeModelo) throws Exception {
        super(context, classeModelo);
    }
}
