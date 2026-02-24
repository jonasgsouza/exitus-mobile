package br.ueg.mobile.exitus.controle;

import br.ueg.mobile.exitus.dao.CursoDAO;
import br.ueg.mobile.exitus.modelo.Curso;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

/**
 * Created by jonas on 19/11/2018.
 */

public class CursoControle extends Controle<Curso, Long, CursoDAO> {

    @Override
    protected Retorno persistirDependencias(Curso modelo) {
        return new Retorno(true);
    }
}
