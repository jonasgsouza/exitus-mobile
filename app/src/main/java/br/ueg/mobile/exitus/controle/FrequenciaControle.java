package br.ueg.mobile.exitus.controle;

import java.util.ArrayList;
import java.util.List;

import br.ueg.mobile.exitus.dao.FrequenciaDAO;
import br.ueg.mobile.exitus.modelo.Frequencia;
import br.ueg.mobile.exitus.modelo.FrequenciaAluno;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

public class FrequenciaControle extends Controle<Frequencia, Long, FrequenciaDAO> {

    @Override
    protected Retorno persistirDependencias(Frequencia modelo) {
        return new Retorno(true);
    }

    @Override
    public Retorno salvarOuAtualizar(Frequencia modelo) {
        Retorno retorno = super.salvarOuAtualizar(modelo);
        if(!retorno.isOk()) return retorno;
        FrequenciaAlunoControle frequenciaAlunoControle = new FrequenciaAlunoControle();
        for(FrequenciaAluno frequenciaAluno : modelo.getFrequenciaAlunos()){
            Retorno retFreqAluno = frequenciaAlunoControle.salvarOuAtualizar(frequenciaAluno);
            if(!retFreqAluno.isOk()) return retFreqAluno;
        }
        return new Retorno(true);
    }

    public Retorno substituirFrequencia(List<Frequencia> substituir, List<Frequencia> nova){
        FrequenciaAlunoControle frequenciaAlunoControle = new FrequenciaAlunoControle();
        List<Long> frequenciaIds = new ArrayList<>();
        for(Frequencia frequencia : substituir){
            if(frequencia.getId() == null) continue;
            frequenciaIds.add(frequencia.getId());
        }
        if(!frequenciaIds.isEmpty()) {
            Retorno retDeleteFreqAluno =
                    frequenciaAlunoControle.deletarFrequenciaAluno(frequenciaIds.toArray(new Long[0]));
            if (!retDeleteFreqAluno.isOk()) {
                return new Retorno(false);
            }
            Retorno retDeletarFrequencia = dao.deletarFrequencia(frequenciaIds.toArray(new Long[0]));
            if (!retDeletarFrequencia.isOk()) {
                return new Retorno(false, retDeletarFrequencia.getMensagem(), retDeletarFrequencia.getSeveridadeMensagem());
            }
        }
        for(Frequencia frequencia : nova){
            Retorno retInserirFreq = persistir(frequencia);
            if(!retInserirFreq.isOk()){
                return retInserirFreq;
            }
        }
        return new Retorno(true, "", "sucesso", null, nova);
    }

    @Override
    public Retorno persistir (Frequencia modelo){
        Retorno persistir = dao.persistir(modelo);
        if(!persistir.isOk()) return persistir;
        FrequenciaAlunoControle frequenciaAlunoControle = new FrequenciaAlunoControle();
        for(FrequenciaAluno frequenciaAluno : modelo.getFrequenciaAlunos()){
            frequenciaAluno.setFrequencia(modelo);
            Retorno retFreqAluno = frequenciaAlunoControle.persistir(frequenciaAluno);
            if(!retFreqAluno.isOk()) return retFreqAluno;
        }
        return new Retorno(true);
    }
}
