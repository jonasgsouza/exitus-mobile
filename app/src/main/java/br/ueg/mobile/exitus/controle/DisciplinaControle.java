package br.ueg.mobile.exitus.controle;

import br.ueg.mobile.exitus.dao.DisciplinaDAO;
import br.ueg.mobile.exitus.modelo.Disciplina;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

public class DisciplinaControle extends Controle<Disciplina, Long, DisciplinaDAO> {

    @Override
    protected Retorno persistirDependencias(Disciplina modelo) {
        return new Retorno(true);
    }
}
