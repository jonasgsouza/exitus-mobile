package br.ueg.mobile.exitus.controle;

import br.ueg.mobile.exitus.dao.FrequenciaAlunoDAO;
import br.ueg.mobile.exitus.modelo.FrequenciaAluno;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

public class FrequenciaAlunoControle extends Controle<FrequenciaAluno, Long, FrequenciaAlunoDAO> {

    @Override
    protected Retorno persistirDependencias(FrequenciaAluno modelo) {
        return new Retorno(true);
    }

    public Retorno deletarFrequenciaAluno(Long... frequenciaIds){
        return dao.deletarFrequenciaAluno(frequenciaIds);
    }
}
