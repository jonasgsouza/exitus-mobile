package br.ueg.mobile.exitus.visao.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jonas on 09/11/2018.
 */

public class CursoVisao implements Serializable {

    private String nome;
    private List<DisciplinaVisao> disciplinas;

    public CursoVisao(String nome, List<DisciplinaVisao> disciplinas){
        this.nome = nome;
        this.disciplinas = disciplinas;
    }

    public CursoVisao(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<DisciplinaVisao> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<DisciplinaVisao> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public String toString(){
        return nome;
    }
}
