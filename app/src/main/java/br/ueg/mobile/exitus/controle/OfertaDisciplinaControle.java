package br.ueg.mobile.exitus.controle;

import br.ueg.mobile.exitus.dao.OfertaDisciplinaDAO;
import br.ueg.mobile.exitus.modelo.OfertaDisciplina;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

public class OfertaDisciplinaControle extends Controle<OfertaDisciplina, Long, OfertaDisciplinaDAO> {

    @Override
    protected Retorno persistirDependencias(OfertaDisciplina modelo) {
        DisciplinaControle disciplinaControle = new DisciplinaControle();
        OfertaCursoControle ofertaCursoControle = new OfertaCursoControle();
        Retorno retDisciplina = disciplinaControle.salvarOuAtualizar(modelo.getDisciplina());
        if(!retDisciplina.isOk()) return retDisciplina;
        return ofertaCursoControle.salvarOuAtualizar(modelo.getOfertaCurso());
    }
}
