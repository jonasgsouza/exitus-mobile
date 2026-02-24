package br.ueg.mobile.exitus.sincronizador.tarefa;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ueg.exitus.sincronizador.tarefa.ITarefaAgendada;
import br.ueg.exitus.sincronizador.tarefa.ITarefaDAO;
import br.ueg.exitus.sincronizador.tarefa.Sincronizavel;
import br.ueg.exitus.sincronizador.tarefa.Status;
import br.ueg.exitus.sincronizador.tarefa.TarefaAbstract;
import br.ueg.exitus.sincronizador.utils.DateUtils;
import br.ueg.exitus.sincronizador.utils.SyncResult;
import br.ueg.mobile.exitus.controle.AnoSemestreControle;
import br.ueg.mobile.exitus.controle.Controle;
import br.ueg.mobile.exitus.controle.FrequenciaAlunoControle;
import br.ueg.mobile.exitus.controle.FrequenciaControle;
import br.ueg.mobile.exitus.controle.OfertaDisciplinaControle;
import br.ueg.mobile.exitus.controle.ProfessorControle;
import br.ueg.mobile.exitus.modelo.AgendarSincronizacaoFrequencia;
import br.ueg.mobile.exitus.modelo.AnoSemestre;
import br.ueg.mobile.exitus.modelo.Frequencia;
import br.ueg.mobile.exitus.modelo.FrequenciaAluno;
import br.ueg.mobile.exitus.modelo.OfertaDisciplina;
import br.ueg.mobile.exitus.modelo.Professor;
import br.ueg.mobile.exitus.servico_cliente.ExitusService;
import br.ueg.mobile.exitus.servico_cliente.Retorno;
import br.ueg.mobile.exitus.utils.AppStatus;
import br.ueg.mobile.exitus.utils.ConnectionUtils;
import br.ueg.mobile.exitus.utils.ObjectUtils;
import br.ueg.mobile.exitus.utils.Preferencias;

@DatabaseTable(tableName = "tarefa")
public class Tarefa<E extends Sincronizavel> extends TarefaAbstract<E> {

    @DatabaseField(persisted = false)
    private static final int TIME_OUT = 600000;

    @DatabaseField(canBeNull = false, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, width = 3)
    private String status;

    @DatabaseField(canBeNull = false, width = 45)
    private String tabela;

    @DatabaseField(canBeNull = false)
    private String parametros;

    @DatabaseField(columnName = "data_finalizacao")
    private Date dataFinalizacao;

    @ForeignCollectionField(eager=true, foreignFieldName = "tarefaPai")
    private Collection<TarefaAgendadaDepende> tarefasPai = new ArrayList<>();

    @DatabaseField(persisted = false)
    protected ITarefaDAO tarefaDAO;

    @DatabaseField(persisted = false)
    protected JSONObject parametrosJSON;

    @DatabaseField(persisted = false)
    protected Context context;

    @DatabaseField(persisted = false)
    protected Class<E> classeModelo;

    @DatabaseField(persisted = false)
    protected String pacote = "br.ueg.mobile.exitus.controle";

    public Tarefa(String params){
        super(params);
//        Class<E> classeModelo = (Class<E>) ((ParameterizedType)
//                getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        tabela = classeModelo.getSimpleName();
        if(parametros != null){
            try {
                parametrosJSON = new JSONObject(parametros);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Class<E> getClasseModelo() {
        return classeModelo;
    }

    public void setClasseModelo(Class<E> classeModelo) {
        tabela = classeModelo.getSimpleName();
        this.classeModelo = classeModelo;
    }

    public Tarefa(){
        super("{}");
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @DatabaseField(persisted = false)
    private List<E> result;

    @Override
    public boolean ehDependente(){
        return !ObjectUtils.isNullOrEmpty(tarefasPai);
    }

    protected Controle getControleBaseLocal() {
        String className = pacote + "." + tabela + "Controle";
        try {
            Class<Controle> classControle = (Class<Controle>) Class.forName(className);
            return classControle.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean podeIniciar() {
        return ConnectionUtils.isOnline();
    }

    @Override
    public void setTarefaDAO(ITarefaDAO tarefaDAO) {
        this.tarefaDAO = tarefaDAO;
    }

    @Override
    public boolean possuiControleDeAlteracaoNaBaseRemota() {
        return true;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    @Override
    public Date getDataFinalizacao() {
        return dataFinalizacao;
    }

    @Override
    public void setDataFinalizacao(Date dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    @Override
    public List<ITarefaAgendada<?>> getTarefasPai() {
        List<ITarefaAgendada<?>> list = new ArrayList<>();
        if(!ObjectUtils.isNullOrEmpty(tarefasPai)){
            for(TarefaAgendadaDepende tarefaAgendadaDepende : tarefasPai){
                list.add(tarefaAgendadaDepende.getTarefaPai());
            }
        }
        return list;
    }

    public void addTarefaPai(Tarefa<?> tarefa){
        TarefaAgendadaDepende tarefaAgendadaDepende = new TarefaAgendadaDepende();
        tarefaAgendadaDepende.setTarefaPai(tarefa);
        tarefaAgendadaDepende.setTarefaFilha(this);
        tarefasPai.add(tarefaAgendadaDepende);
    }

    public void addTarefaDependente(Tarefa<?> tarefa){
        TarefaAgendadaDepende tarefaAgendadaDepende = new TarefaAgendadaDepende();
        tarefaAgendadaDepende.setTarefaPai(this);
        tarefaAgendadaDepende.setTarefaFilha(tarefa);
        tarefasPai.add(tarefaAgendadaDepende);
    }

    public String getParametros() {
        return parametros;
    }

    @Override
    public String getIdentificador() {
        return tabela;
    }

    @Override
    public String getParametro(String key){
        if(parametrosJSON == null){
            try {
                parametrosJSON = new JSONObject(parametros);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return parametrosJSON.optString(key);
    }

    public void setParametros(String parametros) {
        try {
            parametrosJSON = new JSONObject(parametros);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.parametros = parametros;
    }

    public SyncResult<E> getSyncResult(Retorno ret){
        return new SyncResult<E>((List) ret.getLista(), ret.isOk());
    }

    @SuppressWarnings("unchecked")
    @Override
    public SyncResult<E> getEntidadesBaseLocal() {
        return getSyncResult(getControleBaseLocal().listar());
    }

    @Override
    public void executar() {
        if(tabela.equals("Frequencia")){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            FrequenciaControle frequenciaControle = new FrequenciaControle();
            Map<String, Object> map = new HashMap<>();
            try {
                map.put("data_frequencia", new SimpleDateFormat("yyyy-MM-dd").parse(getParametro("data_frequencia")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Retorno retLocal = frequenciaControle.filtrarPorCampo(map);
            Frequencia frequencia = (Frequencia) retLocal.getLista().get(0);
            String url = "http://" + Preferencias.getString("endereco_servidor") + "/exitus-webservice/services/"
                    + tabela + "ServicePort";
            ExitusService exitusService = new ExitusService(url, TIME_OUT);
            ProfessorControle professorControle = new ProfessorControle();
            Professor p = (Professor) professorControle.listar().getLista().get(0);
            if(frequencia.getStatus().equals(AppStatus.ENVIADO_AO_SERVIDOR)){
                try {
                    Retorno retStatusFreq = exitusService
                            .verificarStatusSincronizacao(p.getToken(), frequencia.getDataFrequencia());
                    switch (retStatusFreq.getMensagem()){
                        case Status.SINCRONIZADA:
                            atualizarStatusFrequencia(retLocal.getLista(), Status.SINCRONIZADA);
                            setStatus(Status.FINALIZADA);
                            break;
                        case Status.CONFLITO:
                            //substituir frequencia
                            frequenciaControle.substituirFrequencia(retLocal.getLista(new Frequencia()),
                                    retStatusFreq.getLista(new Frequencia()));
                            setStatus(Status.FINALIZADA);
                            break;
                        default:
                            setStatus(Status.PENDENTE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    setStatus(Status.PENDENTE);
                }
            }else{
                AgendarSincronizacaoFrequencia agendarSincronizacaoFrequencia = new AgendarSincronizacaoFrequencia();
                agendarSincronizacaoFrequencia.token = p.getToken();
                ArrayList<Frequencia> frequencias = new ArrayList<>();
                FrequenciaAlunoControle frequenciaAlunoControle = new FrequenciaAlunoControle();
                for(Object objFrequencia : retLocal.getLista()){
                    Frequencia frequencia1 = (Frequencia) objFrequencia;
                    frequencias.add(frequencia1);
                    Map<String, Object> mapAluno = new HashMap<>();
                    mapAluno.put("frequencia_id", frequencia1.getId());
                    Retorno retorno = frequenciaAlunoControle.filtrarPorCampo(mapAluno);
                    frequencia1.setFrequenciaAlunos(retorno.getLista(new FrequenciaAluno()));
                }
                agendarSincronizacaoFrequencia.frequencias = frequencias;
                try {
                    Retorno retorno = exitusService.agendarSincronizacaoFrequencia(agendarSincronizacaoFrequencia);
//                    if(retorno.isOk()) {
                    atualizarStatusFrequencia(frequencias, AppStatus.ENVIADO_AO_SERVIDOR);
                    setStatus(Status.PENDENTE);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    setStatus(Status.PENDENTE);
                }
            }
        }else {
            super.executar();
        }
    }

    private void atualizarStatusFrequencia(List frequencias, String status){
        FrequenciaControle frequenciaControle = new FrequenciaControle();
        for(Object objFrequenciaLocal : frequencias){
            Frequencia frequenciaLocal = (Frequencia) objFrequenciaLocal;
            frequenciaLocal.setStatus(status);
            frequenciaControle.atualizar(frequenciaLocal);
        }
    }

    @Override
    public SyncResult<E> getEntidadesBaseRemota(Date date) {
        String url = "http://" + Preferencias.getString("endereco_servidor") + "/exitus-webservice/services/" + tabela + "ServicePort";
        ExitusService exitusService = new ExitusService(url, TIME_OUT);
        ProfessorControle professorControle = new ProfessorControle();
        Professor p = (Professor) professorControle.listar().getLista().get(0);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        switch (tabela){
            case "OfertaDisciplinaProfessor":
                AnoSemestreControle anoSemestreControle = new AnoSemestreControle();
                Retorno retAnoSemestre = anoSemestreControle.buscar(Long.valueOf(getParametro("ano_semestre")));
                if(!retAnoSemestre.isOk()){
                    System.out.print(retAnoSemestre.getMensagem());
                    return new SyncResult<>(false);
                }
                try {
                    Date dataAtualizacao = new SimpleDateFormat("yyyy-MM-dd").parse(getParametro("data_atualizacao"));
                    return getSyncResult(exitusService.getOfertaDisciplinaProfessor(p.getToken(), (AnoSemestre) retAnoSemestre.valor, dataAtualizacao));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "OfertaDisciplinaAluno":
                OfertaDisciplinaControle ofertaDisciplinaControle = new OfertaDisciplinaControle();
                Retorno retOftDisc = ofertaDisciplinaControle.buscar(Long.valueOf(getParametro("oferta_disciplina")));
                if(!retOftDisc.isOk()){
                    System.out.print(retOftDisc.getMensagem());
                    return new SyncResult<>(false);
                }
                try {
                    Date dataAtualizacao = new SimpleDateFormat("yyyy-MM-dd").parse(getParametro("data_atualizacao"));
                    return getSyncResult(exitusService.getOfertaDisciplinaAluno(p.getToken(), (OfertaDisciplina) retOftDisc.valor, dataAtualizacao));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return new SyncResult<>(false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SyncResult<E> atualizarBaseLocalComChaveSecundaria(List<E> entidades) {
        for(E entidade : entidades){
            Retorno ret = getControleBaseLocal().atualizar(entidade);
            if(!ret.isOk()) return getSyncResult(ret);
        }
        return new SyncResult<>(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SyncResult<E> salvarOuatualizarBaseLocal(E entidade) {
        return getSyncResult(getControleBaseLocal().salvarOuAtualizar(entidade));
    }

    @SuppressWarnings("unchecked")
    @Override
    public SyncResult<E> salvarBaseLocal(E entidade) {
        return getSyncResult(getControleBaseLocal().persistir(entidade));
    }

    @SuppressWarnings("unchecked")
    @Override
    public SyncResult<E> salvarBaseLocal(List<E> entidades) {
        for(E entidade : entidades){
            Retorno ret = getControleBaseLocal().persistir(entidade);
            if(!ret.isOk()) return getSyncResult(ret);
        }
        return new SyncResult<>(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void atualizarTarefaNaBaseLocal() {
        tarefaDAO.atualizarTarefaAgendada(this);
    }

    @Override
    public void iniciarExecucao() {
        if(ObjectUtils.isNullOrEmpty(parametrosJSON.names())){
            try {
                parametrosJSON = new JSONObject(parametros);
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new Thread(this::executar).start();
    }

    @Override
    public List<E> getResult() {
        return result;
    }

    @Override
    public void setResult(List<E> result) {
        this.result = result;
    }
}
