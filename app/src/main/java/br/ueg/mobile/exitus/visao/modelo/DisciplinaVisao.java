package br.ueg.mobile.exitus.visao.modelo;

import java.io.Serializable;
import java.util.List;

import br.ueg.mobile.exitus.modelo.OfertaDisciplina;

/**
 * Created by jonas on 08/11/2018.
 */

public class DisciplinaVisao implements Serializable {
    private Long id;
    private String nome;
    private OfertaDisciplina ofertaDisciplina;
    private CursoVisao cursoVisao;
    private Integer aulasPrevistas;
    private Integer aulasLancadas;
    private boolean alunosSincronizados = false;

    private List<AlunoVisao> alunos;

    public DisciplinaVisao(String nome, List<AlunoVisao> alunos, CursoVisao cursoVisao){
        this.nome = nome;
        this.alunos = alunos;
        this.cursoVisao = cursoVisao;
    }

    public DisciplinaVisao(Long id, String nome, List<AlunoVisao> alunos, CursoVisao cursoVisao){
        this.id = id;
        this.nome = nome;
        this.alunos = alunos;
        this.cursoVisao = cursoVisao;
    }

    public DisciplinaVisao(Long id, String nome, CursoVisao cursoVisao){
        this.id = id;
        this.nome = nome;
        this.cursoVisao = cursoVisao;
    }

    public DisciplinaVisao(Long id, String nome, CursoVisao cursoVisao, OfertaDisciplina ofertaDisciplina, Integer aulasPrevistas, Integer aulasLancadas) {
        this.id = id;
        this.nome = nome;
        this.cursoVisao = cursoVisao;
        this.ofertaDisciplina = ofertaDisciplina;
        this.aulasPrevistas = aulasPrevistas;
        this.aulasLancadas = aulasLancadas;
    }

    public boolean isAlunosSincronizados() {
        return alunosSincronizados;
    }

    public void setAlunosSincronizados(boolean alunosSincronizados) {
        this.alunosSincronizados = alunosSincronizados;
    }

    public OfertaDisciplina getOfertaDisciplina() {
        return ofertaDisciplina;
    }

    public void setOfertaDisciplina(OfertaDisciplina ofertaDisciplina) {
        this.ofertaDisciplina = ofertaDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<AlunoVisao> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<AlunoVisao> alunos) {
        this.alunos = alunos;
    }

    public CursoVisao getCurso() {
        return cursoVisao;
    }

    public void setCurso(CursoVisao cursoVisao) {
        this.cursoVisao = cursoVisao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CursoVisao getCursoVisao() {
        return cursoVisao;
    }

    public void setCursoVisao(CursoVisao cursoVisao) {
        this.cursoVisao = cursoVisao;
    }

    public Integer getAulasPrevistas() {
        return aulasPrevistas;
    }

    public void setAulasPrevistas(Integer aulasPrevistas) {
        this.aulasPrevistas = aulasPrevistas;
    }

    public Integer getAulasLancadas() {
        return aulasLancadas;
    }

    public void setAulasLancadas(Integer aulasLancadas) {
        this.aulasLancadas = aulasLancadas;
    }

    @Override
    public String toString(){
        return nome;
    }
}
