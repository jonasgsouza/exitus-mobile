package br.ueg.mobile.exitus.controle;

import br.ueg.mobile.exitus.dao.AlunoDAO;
import br.ueg.mobile.exitus.modelo.Aluno;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

public class AlunoControle extends Controle<Aluno, Long, AlunoDAO> {

    @Override
    protected Retorno persistirDependencias(Aluno modelo) {
        return new Retorno(true);
    }
}
