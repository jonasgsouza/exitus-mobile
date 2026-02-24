package br.ueg.mobile.exitus.visao.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ueg.mobile.exitus.controle.AnoSemestreControle;
import br.ueg.mobile.exitus.controle.CampusControle;
import br.ueg.mobile.exitus.controle.OfertaCursoControle;
import br.ueg.mobile.exitus.controle.OfertaDisciplinaControle;
import br.ueg.mobile.exitus.modelo.AnoSemestre;
import br.ueg.mobile.exitus.modelo.Campus;
import br.ueg.mobile.exitus.modelo.OfertaCurso;
import br.ueg.mobile.exitus.modelo.OfertaDisciplina;
import br.ueg.mobile.exitus.servico_cliente.Retorno;
import br.ueg.mobile.exitus.utils.Preferencias;

/**
 * Created by jonas on 09/11/2018.
 */

public class AnoSemestreVisao implements Serializable {
    private Long id;
    private String descricao;
    private List<CampusVisao> campus;

    public AnoSemestreVisao(String descricao, List<CampusVisao> campus){
        this.descricao = descricao;
        this.campus = campus;
    }

    public AnoSemestreVisao(Long id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String nome) {
        this.descricao = nome;
    }

    public List<CampusVisao> getCampus() {
        return campus;
    }

    public void setCampus(List<CampusVisao> campus) {
        this.campus = campus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return descricao;
    }
}
