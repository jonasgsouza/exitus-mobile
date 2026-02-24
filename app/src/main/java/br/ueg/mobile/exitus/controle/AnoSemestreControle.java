package br.ueg.mobile.exitus.controle;

import br.ueg.mobile.exitus.dao.AnoSemestreDAO;
import br.ueg.mobile.exitus.modelo.AnoSemestre;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

/**
 * Created by jonas on 19/11/2018.
 */

public class AnoSemestreControle extends Controle<AnoSemestre, Long, AnoSemestreDAO> {

    @Override
    protected Retorno persistirDependencias(AnoSemestre modelo) {
        return new Retorno(true);
    }
}
