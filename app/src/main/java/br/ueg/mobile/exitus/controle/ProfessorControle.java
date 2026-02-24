package br.ueg.mobile.exitus.controle;

import br.ueg.mobile.exitus.dao.ProfessorDAO;
import br.ueg.mobile.exitus.modelo.Professor;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

/**
 * Created by jonas on 09/06/2018.
 */

public class ProfessorControle extends Controle<Professor, Long, ProfessorDAO>{

    @Override
    protected Retorno persistirDependencias(Professor modelo) {
        return new Retorno(true);
    }
}
