package br.ueg.mobile.exitus.dao;

import android.content.Context;

import br.ueg.mobile.exitus.modelo.Campus;


/**
 * Created by jonas on 19/11/2018.
 */

public class CampusDAO extends DAO<Campus, Long> {

    public CampusDAO(Context context, Class<Campus> classeModelo) throws Exception {
        super(context, classeModelo);
    }
}
