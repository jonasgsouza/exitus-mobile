package br.ueg.mobile.exitus.visao.modelo;

import java.io.Serializable;
import java.util.List;

import br.ueg.mobile.exitus.utils.StringUtils;

/**
 * Created by jonas on 09/11/2018.
 */

public class AlunoVisao implements Serializable{
    private String nome;
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AlunoVisao(String nome, Long id){
        this.nome = nome;
        this.id = id;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString(){
        return nome;
    }
}
