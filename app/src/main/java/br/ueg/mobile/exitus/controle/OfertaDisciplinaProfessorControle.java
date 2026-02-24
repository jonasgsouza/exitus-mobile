package br.ueg.mobile.exitus.controle;

import br.ueg.mobile.exitus.dao.OfertaDisciplinaProfessorDAO;
import br.ueg.mobile.exitus.modelo.OfertaDisciplinaProfessor;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

public class OfertaDisciplinaProfessorControle extends Controle<OfertaDisciplinaProfessor, Long, OfertaDisciplinaProfessorDAO> {

    @Override
    protected Retorno persistirDependencias(OfertaDisciplinaProfessor modelo) {
        return new OfertaDisciplinaControle().salvarOuAtualizar(modelo.getOfertaDisciplina());
    }
}
