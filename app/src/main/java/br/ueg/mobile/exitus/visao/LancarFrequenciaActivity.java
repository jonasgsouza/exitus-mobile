package br.ueg.mobile.exitus.visao;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ueg.exitus.sincronizador.tarefa.Status;
import br.ueg.mobile.exitus.R;
import br.ueg.mobile.exitus.controle.FrequenciaControle;
import br.ueg.mobile.exitus.controle.OfertaDisciplinaAlunoControle;
import br.ueg.mobile.exitus.controle.ProfessorControle;
import br.ueg.mobile.exitus.modelo.AtualizarFrequencia;
import br.ueg.mobile.exitus.modelo.Frequencia;
import br.ueg.mobile.exitus.modelo.FrequenciaAluno;
import br.ueg.mobile.exitus.modelo.OfertaDisciplinaAluno;
import br.ueg.mobile.exitus.modelo.Professor;
import br.ueg.mobile.exitus.servico_cliente.ExitusService;
import br.ueg.mobile.exitus.servico_cliente.Retorno;
import br.ueg.mobile.exitus.sincronizador.GestorDeTarefasApp;
import br.ueg.mobile.exitus.sincronizador.tarefa.Tarefa;
import br.ueg.mobile.exitus.utils.AppStatus;
import br.ueg.mobile.exitus.utils.ObjectUtils;
import br.ueg.mobile.exitus.utils.Preferencias;
import br.ueg.mobile.exitus.visao.modelo.AlunoVisao;
import br.ueg.mobile.exitus.visao.modelo.DisciplinaVisao;
import br.ueg.mobile.exitus.visao.modelo.FrequenciaAlunoVisao;
import br.ueg.mobile.exitus.visao.modelo.FrequenciaVisao;

public class LancarFrequenciaActivity extends VisaoActivityAbstrata {
    private DisciplinaVisao disciplinaVisao;
    private Date dataDiaFreq;
    private Integer numeroAulas;
    private ListView alunoListView;
    private ViewGroup rootAlunoListView;
    private ViewGroup rootNumeroAulas;
    private Button btnSalvarFrequencia;
    private List<FrequenciaVisao> frequenciaVisaoList;
    private ImageView status;
    private List<AlunoVisao> alunoVisaoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancar_frequencia);
        disciplinaVisao = (DisciplinaVisao) getIntent().getSerializableExtra("disciplina");
        dataDiaFreq = (Date) getIntent().getSerializableExtra("diaFreq");
        try {
            numeroAulas = Integer.valueOf(getIntent().getStringExtra("numeroAulas"));
        }catch(NumberFormatException e){
            e.printStackTrace();
            numeroAulas = 0;
//            numeroAulas = 2;
        }
        Toolbar toolbar = findViewById(R.id.lancar_frequencia_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lançar Frequência: " + new SimpleDateFormat("dd/MM/yyyy").format(dataDiaFreq));
        TextView cursoNome = findViewById(R.id.txtView_curso_nome);
        cursoNome.setText(disciplinaVisao.getCurso().toString());
        TextView disciplinaNome = findViewById(R.id.txtView_disciplina_nome);
        disciplinaNome.setText(disciplinaVisao.toString());
        btnSalvarFrequencia = findViewById(R.id.salvar_frequencia);
        btnSalvarFrequencia.setOnClickListener(l ->{
            new SalvaFrequenciaTask().execute();
        });
        rootAlunoListView = findViewById(R.id.root_aluno_list_view);
        rootAlunoListView.removeAllViews();
        alunoListView = new CustomListView(this);
        alunoListView.setDivider(new ColorDrawable(getResources().getColor(R.color.cardview_light_background)));
        alunoListView.setDividerHeight(30);
        alunoListView.setPadding(0,0,0,20);
        alunoListView.setClipToPadding(false);
        rootAlunoListView.addView(alunoListView);
        alunoListView.requestLayout();
        rootNumeroAulas = findViewById(R.id.root_numero_aulas);
        status = findViewById(R.id.activity_lancar_frequencia_imageView_status);
        status.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.sucesso, null));

        frequenciaVisaoList = new ArrayList<>();
        carregarFrequencia();
    }

    private void carregarFrequencia() {
        FrequenciaControle frequenciaControle = new FrequenciaControle();
        Map<String, Object> map  = new HashMap<>();
        map.put("data_frequencia", dataDiaFreq);
        map.put("oferta_disciplina_id", disciplinaVisao.getId());
        Retorno retorno = frequenciaControle.filtrarPorCampo(map);
        Map<String, Object> mapAluno = new HashMap<>();
        mapAluno.put("oferta_disciplina_id", disciplinaVisao.getId());
        OfertaDisciplinaAlunoControle ofertaDisciplinaAlunoControle = new OfertaDisciplinaAlunoControle();
        Retorno retOfertaDisciplinaAluno = ofertaDisciplinaAlunoControle.filtrarPorCampo(mapAluno);
        alunoVisaoList = new ArrayList<>();
        for (Object objOfertaDisciplinaAluno : retOfertaDisciplinaAluno.getLista()) {
            OfertaDisciplinaAluno ofertaDisciplinaAluno = (OfertaDisciplinaAluno) objOfertaDisciplinaAluno;
            alunoVisaoList.add(ofertaDisciplinaAluno.getAlunoVisao());
        }
        if(ObjectUtils.isNullOrEmpty(retorno.getLista())) {
            criarFrequencias(retOfertaDisciplinaAluno, numeroAulas, 1);
            status.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pendente, null));
            AlunoAdapter alunoAdapter = new AlunoAdapter(alunoVisaoList, frequenciaVisaoList, this, numeroAulas);
            alunoListView.setAdapter(alunoAdapter);
        }else{
            Frequencia frequencia = retorno.getLista(new Frequencia()).get(0);
            switch (frequencia.getStatus()) {
                case Status.CONFLITO:
                    DialogMensagemErro dialogMensagem = new DialogMensagemErro();
                    Bundle bundle = new Bundle();
                    bundle.putString("mensagem", "Foi encontrado um conflito no lançamento da frequência. O número de aulas" +
                            " lançado no aplicativo difere com o sistema acadêmico. A frequência local foi substituída");
                    dialogMensagem.setArguments(bundle);
                    dialogMensagem.show(getFragmentManager(), "dialog_erro");
                    status.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.erro, null));
                    new AtualizaStatusFrequencia().execute();
                    break;
                case AppStatus.SOMENTE_APP:
                    status.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pendente, null));
                    break;
                case AppStatus.ENVIADO_AO_SERVIDOR:
                    status.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.no_servidor, null));
                    break;
                case Status.SINCRONIZADA:
                    status.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.sucesso, null));
                    break;
            }
            List<Frequencia> frequencias = retorno.getLista(new Frequencia());
            frequenciaVisaoList = new ArrayList<>();
            for(Frequencia frequencia1 : frequencias){
                frequenciaVisaoList.add(frequencia1.getFrequenciaVisao(this));
            }
            if(numeroAulas != 0){
                criarFrequencias(retOfertaDisciplinaAluno, numeroAulas + retorno.getLista().size(), retorno.getLista().size()+1);
            }
            numeroAulas += retorno.getLista().size();
            AlunoAdapter alunoAdapter = new AlunoAdapter(alunoVisaoList, frequenciaVisaoList, this, numeroAulas);
            alunoListView.setAdapter(alunoAdapter);
        }
        carregarBarraNumeroAulas();
    }

    private void carregarBarraNumeroAulas() {
        rootNumeroAulas.removeAllViews();
        for(int i = 0; i < numeroAulas; i++){
            int leftMargin = i == 0 ? 32 : 85;
            TextView textView = new TextView(this);
            textView.setText((i+1)+"º");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(leftMargin,0,0,0);
            textView.setLayoutParams(layoutParams);
            rootNumeroAulas.addView(textView);
            rootNumeroAulas.requestLayout();
        }
    }

    private void criarFrequencias(Retorno retOfertaDisciplinaAluno, int numeroAulas, int inicio) {
        for(int i = inicio; i <= numeroAulas; i++) {
            FrequenciaVisao frequenciaVisao = new FrequenciaVisao();
            Frequencia frequencia = new Frequencia();
            frequencia.setAula(i);
            frequencia.setStatus(AppStatus.SOMENTE_APP);
            frequencia.setDataFrequencia(dataDiaFreq);
            frequencia.setOfertaDisciplina(disciplinaVisao.getOfertaDisciplina());
            frequenciaVisao.setFrequencia(frequencia);
            List<FrequenciaAlunoVisao> frequenciaAlunoVisaoList = new ArrayList<>();
            for (Object objOfertaDisciplinaAluno : retOfertaDisciplinaAluno.getLista()) {
                OfertaDisciplinaAluno ofertaDisciplinaAluno = (OfertaDisciplinaAluno) objOfertaDisciplinaAluno;
                FrequenciaAluno frequenciaAluno = new FrequenciaAluno();
                frequenciaVisao.getFrequencia().addFrequenciaAluno(frequenciaAluno);
                frequenciaAluno.setFrequencia(frequenciaVisao.getFrequencia());
                frequenciaAluno.setOfertaDisciplinaAluno(ofertaDisciplinaAluno);
                FrequenciaAlunoVisao frequenciaAlunoVisao = new FrequenciaAlunoVisao(this, frequenciaAluno);
                frequenciaAlunoVisaoList.add(frequenciaAlunoVisao);
            }
            frequenciaVisao.setFrequenciaAlunos(frequenciaAlunoVisaoList);
            frequenciaVisaoList.add(frequenciaVisao);
        }
    }

    public void atualizarFrequencia(){
        new AtualizaFrequenciaTask().execute();
    }

    class SalvaFrequenciaTask extends AsyncTask<Void, Void, Retorno>{
        DialogCarregando carregando;

        @Override
        protected void onPreExecute() {
            carregando = new DialogCarregando();
            Bundle bundle = new Bundle();
            bundle.putString("mensagem", "Salvando...");
            carregando.setArguments(bundle);
            carregando.show(getFragmentManager(), "dialog_carregando");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Retorno ret) {
            carregando.dismiss();
            if(!ret.isOk()){
                new Notificacao(LancarFrequenciaActivity.this, ret.getMensagem(), ret.getSeveridadeMensagem()).exibir();
            }else{
                new AgendaTarefaTask().execute();
                setResult(RESULT_OK);
                finish();
            }
            super.onPostExecute(ret);
        }

        @Override
        protected Retorno doInBackground(Void... voids) {
            FrequenciaControle frequenciaControle = new FrequenciaControle();
            for(FrequenciaVisao frequenciaVisao : frequenciaVisaoList) {
                frequenciaVisao.getFrequencia().setStatus(AppStatus.SOMENTE_APP);
                Retorno retorno = frequenciaControle.salvarOuAtualizar(frequenciaVisao.getFrequencia());
                if(!retorno.isOk()) return retorno;
            }
            return new Retorno(true);
        }
    }

    class AgendaTarefaTask extends AsyncTask<Void, Void, Retorno>{

        @Override
        protected Retorno doInBackground(Void... voids) {
            String params = "{\"data_frequencia\": \"" + new SimpleDateFormat("yyyy-MM-dd").format(dataDiaFreq) + "\"}";
            Tarefa<Frequencia> tarefa = new Tarefa<>(params);
            tarefa.setClasseModelo(Frequencia.class);
            GestorDeTarefasApp.getInstancia().agendarTarefa(tarefa);
            return null;
        }
    }

    class AtualizaFrequenciaTask extends AsyncTask<Void, Void, Retorno> {
        private DialogCarregando carregando;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            carregando = new DialogCarregando();
            Bundle bundle = new Bundle();
            bundle.putString("mensagem", "Sincronizado...");
            carregando.setArguments(bundle);
            carregando.show(getFragmentManager(), "carregando_dialog");
        }

        @Override
        protected void onPostExecute(Retorno retorno) {
            super.onPostExecute(retorno);
            if (carregando != null) carregando.dismiss();
            if (retorno.isOk()) {
                frequenciaVisaoList = new ArrayList<>();
                for (Frequencia frequencia1 : retorno.getLista(new Frequencia())) {
                    frequenciaVisaoList.add(frequencia1.getFrequenciaVisao(LancarFrequenciaActivity.this));
                }
                numeroAulas = retorno.getLista().size();
                AlunoAdapter alunoAdapter = new AlunoAdapter(alunoVisaoList, frequenciaVisaoList, LancarFrequenciaActivity.this, numeroAulas);
                alunoListView.setAdapter(alunoAdapter);
                carregarBarraNumeroAulas();
                new Notificacao(LancarFrequenciaActivity.this, "Frequência sincronizada", "sucesso").exibir();
                status.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.sucesso, null));
            } else {
                new Notificacao(LancarFrequenciaActivity.this, retorno.getMensagem(), retorno.getSeveridadeMensagem()).exibir();
            }
        }

        @Override
        protected Retorno doInBackground(Void... voids) {
            AtualizarFrequencia atualizarFrequencia = new AtualizarFrequencia();
            atualizarFrequencia.token = new ProfessorControle().listar().getLista(new Professor()).get(0).getToken();
            ArrayList<Frequencia> frequencias = new ArrayList<>();
            for (FrequenciaVisao frequenciaVisao : frequenciaVisaoList) {
                frequencias.add(frequenciaVisao.getFrequencia());
            }
            atualizarFrequencia.frequencias = frequencias;
            String url = "http://" + Preferencias.getString("endereco_servidor") + "/exitus-webservice/services/"
                    + "FrequenciaServicePort";
                try {
                    Retorno retorno = new ExitusService(url, 480000).atualizarFrequencia(atualizarFrequencia);
                    if (retorno.isOk()) {
                        FrequenciaControle frequenciaControle = new FrequenciaControle();
                        return frequenciaControle.substituirFrequencia(frequencias, retorno.getLista(new Frequencia()));
                    }
                    return retorno;
                } catch (Exception e) {
                        e.printStackTrace();
                        return new Retorno(false, e.getMessage(), "erro");
                }
//            }
        }
    }

    class AtualizaStatusFrequencia extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            FrequenciaControle frequenciaControle = new FrequenciaControle();
            for(FrequenciaVisao frequenciaVisao : frequenciaVisaoList){
                Frequencia frequencia = frequenciaVisao.getFrequencia();
                frequencia.setStatus(br.ueg.exitus.sincronizador.tarefa.Status.SINCRONIZADA);
                frequenciaControle.atualizar(frequencia);
            }
            return null;
        }
    }

    @Override
    public View getContentView() {
        return findViewById(R.id.activity_lancar_frequencia);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_lancar_frequencia_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_lateral_btn_ajuda:
                new DialogAjudaStatusFrequencia().show(getSupportFragmentManager(), "ajuda");
                break;
            case R.id.menu_lateral_btn_sincronizar:
                new DialogMensagemForcarSincronizacaoFrequencia().show(getSupportFragmentManager(), "forcar_sync");
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return true;
    }
}
