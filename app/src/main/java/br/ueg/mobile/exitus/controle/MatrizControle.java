package br.ueg.mobile.exitus.controle;

import br.ueg.mobile.exitus.dao.MatrizDAO;
import br.ueg.mobile.exitus.modelo.Matriz;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

/**
 * Created by jonas on 19/11/2018.
 */

public class MatrizControle extends Controle<Matriz, Long, MatrizDAO> {

    @Override
    protected Retorno persistirDependencias(Matriz modelo) {
        return new Retorno(true);
    }
}
