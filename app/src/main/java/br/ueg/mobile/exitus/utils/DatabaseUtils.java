package br.ueg.mobile.exitus.utils;

import java.util.ArrayList;
import java.util.List;

import br.ueg.mobile.exitus.controle.AlunoControle;
import br.ueg.mobile.exitus.controle.AnoSemestreControle;
import br.ueg.mobile.exitus.controle.CampusControle;
import br.ueg.mobile.exitus.controle.Controle;
import br.ueg.mobile.exitus.controle.CursoControle;
import br.ueg.mobile.exitus.controle.DisciplinaControle;
import br.ueg.mobile.exitus.controle.FrequenciaAlunoControle;
import br.ueg.mobile.exitus.controle.FrequenciaControle;
import br.ueg.mobile.exitus.controle.MatrizControle;
import br.ueg.mobile.exitus.controle.OfertaCursoControle;
import br.ueg.mobile.exitus.controle.OfertaDisciplinaAlunoControle;
import br.ueg.mobile.exitus.controle.OfertaDisciplinaControle;
import br.ueg.mobile.exitus.controle.OfertaDisciplinaProfessorControle;
import br.ueg.mobile.exitus.controle.ProfessorControle;
import br.ueg.mobile.exitus.sincronizador.TarefaDAO;

/**
 * Created by jonas on 16/09/2018.
 */

public class DatabaseUtils {

    private static DatabaseUtils instancia;

    private DatabaseUtils(){}

    public static DatabaseUtils getInstancia(){
        if(instancia == null) instancia = new DatabaseUtils();
        return instancia;
    }

    public void limparBaseLocal(){
        List<Controle> controladores = new ArrayList<>();
        controladores.add(new FrequenciaAlunoControle());
        controladores.add(new FrequenciaControle());
        controladores.add(new OfertaDisciplinaAlunoControle());
        controladores.add(new AlunoControle());
        controladores.add(new OfertaDisciplinaProfessorControle());
        controladores.add(new OfertaDisciplinaControle());
        controladores.add(new OfertaCursoControle());
        controladores.add(new DisciplinaControle());
        controladores.add(new AnoSemestreControle());
        controladores.add(new CampusControle());
        controladores.add(new CursoControle());
        controladores.add(new MatrizControle());
        controladores.add(new ProfessorControle());
        for(Controle c : controladores){
            c.limparTabela();
        }
        new TarefaDAO(MyApplication.getInstancia()).limparTabela();
    }
}
