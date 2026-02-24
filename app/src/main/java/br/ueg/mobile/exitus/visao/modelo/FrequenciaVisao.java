package br.ueg.mobile.exitus.visao.modelo;

import java.util.List;

import br.ueg.mobile.exitus.modelo.Frequencia;

public class FrequenciaVisao {
    private Frequencia frequencia;
    private List<FrequenciaAlunoVisao> frequenciaAlunos;

    public FrequenciaVisao(){}

    public FrequenciaVisao(List<FrequenciaAlunoVisao> frequenciaAlunos) {
        this.frequenciaAlunos = frequenciaAlunos;
    }

    public FrequenciaVisao(Frequencia frequencia, List<FrequenciaAlunoVisao> frequenciaAlunos) {
        this.frequencia = frequencia;
        this.frequenciaAlunos = frequenciaAlunos;
    }

    public FrequenciaAlunoVisao getFrequenciaAlunoVisao(Long alunoId){
        for(FrequenciaAlunoVisao frequenciaAlunoVisao : frequenciaAlunos){
            if(frequenciaAlunoVisao.getFrequenciaAluno().getOfertaDisciplinaAluno().getAluno().getId().equals(alunoId)){
                return frequenciaAlunoVisao;
            }
        }
        return null;
    }

    public Frequencia getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Frequencia frequencia) {
        this.frequencia = frequencia;
    }

    public List<FrequenciaAlunoVisao> getFrequenciaAlunos() {
        return frequenciaAlunos;
    }

    public void setFrequenciaAlunos(List<FrequenciaAlunoVisao> frequenciaAlunos) {
        this.frequenciaAlunos = frequenciaAlunos;
    }
}
