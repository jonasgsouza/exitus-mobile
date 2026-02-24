package br.ueg.mobile.exitus.visao.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jonas on 09/11/2018.
 */

public class CampusVisao implements Serializable {

    private String nome;
    private List<CursoVisao> cursos;

    public CampusVisao(String nome, List<CursoVisao> cursos){
        this.nome = nome;
        this.cursos = cursos;
    }

    public CampusVisao(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<CursoVisao> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoVisao> cursos) {
        this.cursos = cursos;
    }

    @Override
    public String toString(){
        return nome;
    }
}
