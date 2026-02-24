package br.ueg.mobile.exitus.controle;

import br.ueg.mobile.exitus.dao.CampusDAO;
import br.ueg.mobile.exitus.modelo.Campus;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

/**
 * Created by jonas on 19/11/2018.
 */

public class CampusControle extends Controle<Campus, Long, CampusDAO> {

    @Override
    protected Retorno persistirDependencias(Campus modelo) {
        return new Retorno(true);
    }
}
