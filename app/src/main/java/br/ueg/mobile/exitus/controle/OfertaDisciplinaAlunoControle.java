package br.ueg.mobile.exitus.controle;

import br.ueg.mobile.exitus.dao.OfertaDisciplinaAlunoDAO;
import br.ueg.mobile.exitus.modelo.OfertaDisciplinaAluno;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

public class OfertaDisciplinaAlunoControle extends Controle<OfertaDisciplinaAluno, Long, OfertaDisciplinaAlunoDAO> {

    @Override
    protected Retorno persistirDependencias(OfertaDisciplinaAluno modelo) {
        AlunoControle alunoControle = new AlunoControle();
        return alunoControle.salvarOuAtualizar(modelo.getAluno());
    }
}
