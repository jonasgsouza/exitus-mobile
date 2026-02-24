package br.ueg.mobile.exitus.controle;

import br.ueg.mobile.exitus.dao.OfertaCursoDAO;
import br.ueg.mobile.exitus.modelo.OfertaCurso;
import br.ueg.mobile.exitus.servico_cliente.Retorno;

public class OfertaCursoControle extends Controle<OfertaCurso, Long, OfertaCursoDAO> {

    @Override
    protected Retorno persistirDependencias(OfertaCurso modelo) {
        CampusControle campusControle = new CampusControle();
        MatrizControle matrizControle = new MatrizControle();
        CursoControle cursoControle = new CursoControle();
        Retorno retCampus = campusControle.salvarOuAtualizar(modelo.getCampus());
        if(!retCampus.isOk()) return retCampus;
        Retorno retMatriz = matrizControle.salvarOuAtualizar(modelo.getMatriz());
        if(!retMatriz.isOk()) return  retMatriz;
        return cursoControle.salvarOuAtualizar(modelo.getCurso());
    }
}
