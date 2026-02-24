package br.ueg.mobile.exitus.dao;

import android.content.Context;

import br.ueg.mobile.exitus.modelo.AnoSemestre;


/**
 * Created by jonas on 19/11/2018.
 */

public class AnoSemestreDAO extends DAO<AnoSemestre, Long> {

    public AnoSemestreDAO(Context context, Class<AnoSemestre> classeModelo) throws Exception {
        super(context, classeModelo);
    }
}
