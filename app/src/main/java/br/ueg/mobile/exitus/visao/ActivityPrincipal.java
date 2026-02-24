package br.ueg.mobile.exitus.visao;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ueg.exitus.sincronizador.utils.DateUtils;
import br.ueg.mobile.exitus.R;
import br.ueg.mobile.exitus.controle.AnoSemestreControle;
import br.ueg.mobile.exitus.controle.CampusControle;
import br.ueg.mobile.exitus.controle.FrequenciaControle;
import br.ueg.mobile.exitus.controle.OfertaCursoControle;
import br.ueg.mobile.exitus.controle.OfertaDisciplinaAlunoControle;
import br.ueg.mobile.exitus.controle.OfertaDisciplinaControle;
import br.ueg.mobile.exitus.controle.ProfessorControle;
import br.ueg.mobile.exitus.modelo.AnoSemestre;
import br.ueg.mobile.exitus.modelo.Campus;
import br.ueg.mobile.exitus.modelo.OfertaCurso;
import br.ueg.mobile.exitus.modelo.OfertaDisciplina;
import br.ueg.mobile.exitus.modelo.OfertaDisciplinaProfessor;
import br.ueg.mobile.exitus.modelo.Professor;
import br.ueg.mobile.exitus.servico_cliente.Retorno;
import br.ueg.mobile.exitus.sincronizador.GestorDeTarefasApp;
import br.ueg.mobile.exitus.sincronizador.tarefa.Tarefa;
import br.ueg.mobile.exitus.utils.MyApplication;
import br.ueg.mobile.exitus.utils.ObjectUtils;
import br.ueg.mobile.exitus.utils.Preferencias;
import br.ueg.mobile.exitus.visao.modelo.AnoSemestreVisao;
import br.ueg.mobile.exitus.visao.modelo.CampusVisao;
import br.ueg.mobile.exitus.visao.modelo.CursoVisao;
import br.ueg.mobile.exitus.visao.modelo.DisciplinaVisao;

public class ActivityPrincipal extends VisaoActivityAbstrata implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private EditText edtDiaFreq;
    private Date dataDiaFreq;
    private TextView textViewDisciplinaNome;
    private Spinner anoSemestreSpinner;
    private Spinner campusSpinner;
    private ViewGroup rootCardView;
    private Button continuar;
    private CursoAdapter cursoAdapter;
    private EditText edtNumeroAulas;
    private boolean flag;
    private List<AnoSemestreVisao> anoSemestreVisaoList;
    private boolean dadosCarregados;
    private boolean exibirMensagem;
    private AnoSemestreVisao anoSemestreVisaoSelecionado;

    @Override
    protected void onResume() {
        super.onResume();
//        new FrequenciaAlunoControle();
//        new FrequenciaControle();
        new CarregaDados().execute();
    }

    @Override
    public View getContentView() {
        return findViewById(R.id.activity_principal);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preferencias.putString("endereco_servidor", "192.168.1.7:8080");
        setContentView(R.layout.activity_principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,
                R.string.navigation_drawer_open,  /* "open drawer" description */
                R.string.navigation_drawer_close  /* "close drawer" description */
        );
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle("Exitus");
        Professor professor = (Professor) getIntent().getSerializableExtra("professor");
        if(professor != null){
            new DialogAjudaStatusFrequencia().show(getSupportFragmentManager(), "ajuda");
            String params = "{\"ano_semestre\":" + Preferencias.getLong("ano_semestre_id") + ", \"data_atualizacao\": \""
                    + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "\"}";
            Tarefa<OfertaDisciplinaProfessor> tarefa = new Tarefa<>(params);
            tarefa.setClasseModelo(OfertaDisciplinaProfessor.class);
            GestorDeTarefasApp.getInstancia().agendarTarefa(tarefa);
        }
        try {
            TextView textView = navigationView.getHeaderView(0).findViewById(R.id.txtView_professor_nome);
            if (professor == null) {
                professor = (Professor) new ProfessorControle().listar().getLista().get(0);
            }
            textView.setText(professor.getNome());
        }catch (Exception e){
            e.printStackTrace();
        }

        slidingUpPanelLayout = findViewById(R.id.sliding_up_panel);
        slidingUpPanelLayout.setTouchEnabled(false);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        flag = false;
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if(!flag && slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.DRAGGING){
                    setDiaFreq(new Date());
                }
            }
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                //set the expanded flag true to mark the expanded panel state.
                if(flag && newState == SlidingUpPanelLayout.PanelState.HIDDEN){
                    iniciarLancamento();
                }
            }
        });
        edtNumeroAulas = findViewById(R.id.edit_text_numero_aulas);
        edtDiaFreq = findViewById(R.id.edit_text_dia_freq);
        edtDiaFreq.setOnClickListener(l->{
            Bundle bundle = new Bundle();
            bundle.putString("metodo", "setDiaFreq");
            bundle.putSerializable("data", dataDiaFreq);
            DatePicker datePicker = new DatePicker();
            datePicker.setArguments(bundle);
            datePicker.show(getFragmentManager(), "datepicker");
        });
        edtDiaFreq.setKeyListener(null);
        anoSemestreSpinner = findViewById(R.id.ano_semestre_spinner);
        anoSemestreVisaoList = new ArrayList<>();
        AnoSemestreControle anoSemestreControle = new AnoSemestreControle();
        Retorno retAnoSemestre = anoSemestreControle.listar();
        int i = 0;
        int selectPosition = 0;
        for(Object objAnoSemestre : retAnoSemestre.getLista()) {
            AnoSemestre anoSemestre = (AnoSemestre) objAnoSemestre;
            anoSemestreVisaoList.add(anoSemestre.getAnoSemestreVisao());
            if(anoSemestre.getId().equals(Preferencias.getLong("ano_semestre_id"))){
                selectPosition = i;
            }
            i++;
        }
        campusSpinner = findViewById(R.id.campus_spinner);
        anoSemestreSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.layout_generico_item_list_view, anoSemestreVisaoList));
        anoSemestreSpinner.setSelection(selectPosition);
        dadosCarregados = false;
        exibirMensagem = false;
        anoSemestreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                anoSemestreVisaoSelecionado = (AnoSemestreVisao) parent.getItemAtPosition(position);
                Preferencias.putLong("ano_semestre_id", anoSemestreVisaoSelecionado.getId());
                if(dadosCarregados){
                    campusSpinner.setAdapter(new ArrayAdapter<>(ActivityPrincipal.this, R.layout.layout_generico_item_list_view, anoSemestreVisaoSelecionado.getCampus()));
                }
                campusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        CampusVisao campusVisaoSelecionado = (CampusVisao) parent.getItemAtPosition(position);
                        if(ObjectUtils.isNullOrEmpty(campusVisaoSelecionado.getCursos())) {
                            new DialogMensagemSincronizacao().show(getFragmentManager(), "");
                            campusSpinner.setAdapter(null);
                            ProgressBar progressBar = new ProgressBar(ActivityPrincipal.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(0, 370, 0, 0);
                            progressBar.setLayoutParams(layoutParams);
                            setConteudoRootCardView(progressBar);
                        }else {
                            cursoAdapter = new CursoAdapter(campusVisaoSelecionado.getCursos(), ActivityPrincipal.this, slidingUpPanelLayout);
                            ListView cursoListView = new ListView(ActivityPrincipal.this);
                            cursoListView.setDivider(new ColorDrawable(getResources().getColor(R.color.cardview_light_background)));
                            cursoListView.setDividerHeight(30);
                            cursoListView.setPadding(0, 0, 0, 20);
                            cursoListView.setClipToPadding(false);
                            setConteudoRootCardView(cursoListView);
                            cursoListView.setAdapter(cursoAdapter);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button fecharSlidePanel = findViewById(R.id.fechar_slide_panel);
        textViewDisciplinaNome = findViewById(R.id.txtView_disciplina);
        fecharSlidePanel.setOnClickListener(l ->{
            textViewDisciplinaNome.setText("");
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        });
        continuar = findViewById(R.id.btn_continuar);
        continuar.setOnClickListener(l -> continuar());
//        MyApplication.getInstancia().startService(getIntent());
    }

    public void continuar(){
        FrequenciaControle frequenciaControle = new FrequenciaControle();
        Map<String, Object> map  = new HashMap<>();
        map.put("data_frequencia", dataDiaFreq);
        map.put("oferta_disciplina_id", cursoAdapter.getDisciplinaSelecionada().getId());
        Retorno retorno = frequenciaControle.filtrarPorCampo(map);
        if(ObjectUtils.isNullOrEmpty(retorno.getLista())){
            if(ObjectUtils.isNullOrEmpty(edtNumeroAulas.getText().toString())
                    || Integer.valueOf(edtNumeroAulas.getText().toString()) <= 0){
                new Notificacao(this, "Informe o número de aulas", "alerta").exibir();
                return;
            }
        }
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        flag = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            new Notificacao(this, "Sincronização em andamento", "sucesso").exibir();
        }
    }

    public void iniciarLancamento(){
        Intent intent = new Intent(this, LancarFrequenciaActivity.class);
        intent.putExtra("disciplina", cursoAdapter.getDisciplinaSelecionada());
        intent.putExtra("diaFreq", dataDiaFreq);
        intent.putExtra("numeroAulas", edtNumeroAulas.getText().toString());
        edtNumeroAulas.setText("");
        flag = false;
        startActivityForResult(intent, 1);
    }

    public void setConteudoRootCardView(View v){
        if(rootCardView == null) {
            rootCardView = findViewById(R.id.root_card_view);
        }
        rootCardView.removeAllViews();
        rootCardView.addView(v);
    }

    private void criarListaDeAnoSemestre() {
        for(AnoSemestreVisao anoSemestreVisao : anoSemestreVisaoList) {
            CampusControle campusControle = new CampusControle();
            Retorno retCampus = campusControle.listar();
            Map<String, Object> map = new HashMap<>();
            map.put("ano_semestre_id", anoSemestreVisao.getId());
            List<CampusVisao> listacampusVisao = new ArrayList<>();
            for (Object objCampus : retCampus.getLista()) {
                Campus campus = (Campus) objCampus;
                map.put("campus_id", campus.getId());
                OfertaCursoControle ofertaCursoControle = new OfertaCursoControle();
                Retorno retOfertaCurso = ofertaCursoControle.filtrarPorCampo(map);
                List<CursoVisao> cursosVisao = new ArrayList<>();
                for (Object objOfertaCurso : retOfertaCurso.getLista()) {
                    OfertaCurso ofertaCurso = (OfertaCurso) objOfertaCurso;
                    CursoVisao cursoVisao = ofertaCurso.getCursoVisao();
                    cursosVisao.add(cursoVisao);
                    Map<String, Object> mapDisciplina = new HashMap<>();
                    mapDisciplina.put("oferta_curso_id", ofertaCurso.getId());
                    OfertaDisciplinaControle ofertaDisciplinaControle = new OfertaDisciplinaControle();
                    Retorno retOfertaDisciplina = ofertaDisciplinaControle.filtrarPorCampo(mapDisciplina);
                    List<DisciplinaVisao> disciplinasVisao = new ArrayList<>();
                    for (Object objOfertaDisciplina : retOfertaDisciplina.getLista()) {
                        OfertaDisciplina ofertaDisciplina = (OfertaDisciplina) objOfertaDisciplina;
                        DisciplinaVisao disciplinaVisao = ofertaDisciplina.getDisciplinaVisao();
                        disciplinasVisao.add(disciplinaVisao);
                        Map<String, Object> mapAluno = new HashMap<>();
                        mapAluno.put("oferta_disciplina_id", ofertaDisciplina.getId());
                        OfertaDisciplinaAlunoControle ofertaDisciplinaAlunoControle = new OfertaDisciplinaAlunoControle();
                        Retorno retOfertaDisciplinaAluno = ofertaDisciplinaAlunoControle.filtrarPorCampo(mapAluno);
                        disciplinaVisao.setAlunosSincronizados(!ObjectUtils.isNullOrEmpty(retOfertaDisciplinaAluno.getLista()));
//                        List<AlunoVisao> alunosVisao = new ArrayList<>();
//                        for(Object objOfertaDisciplinaAluno : retOfertaDisciplinaAluno.getLista()){
//                            OfertaDisciplinaAluno ofertaDisciplinaAluno = (OfertaDisciplinaAluno) objOfertaDisciplinaAluno;
//                            alunosVisao.add(ofertaDisciplinaAluno.getAlunoVisao());
//                        }
//                        disciplinaVisao.setAlunos(alunosVisao);
                    }
                    cursoVisao.setDisciplinas(disciplinasVisao);
                }
                CampusVisao campusVisao = campus.getCampusVisao();
                campusVisao.setCursos(cursosVisao);
                listacampusVisao.add(campusVisao);
                map.remove("campus_id");
            }
            anoSemestreVisao.setCampus(listacampusVisao);
        }
//        ArrayList<AlunoVisao> alunos = new ArrayList<>();
//        alunos.add(new AlunoVisao("Enzo Diego Calebe Barbosa"));
//        alunos.add(new AlunoVisao("Geraldo Alexandre Yago Corte Real"));
//        alunos.add(new AlunoVisao("Joaquim Marcos Souza"));
//        alunos.add(new AlunoVisao("Martin Nathan Diego Novaes"));
//        alunos.add(new AlunoVisao("Paulo Cauê Rafael da Cruz"));
//        CursoVisao curso1 = new CursoVisao("Sistemas de Informação - 2015/1");
//        ArrayList<DisciplinaVisao> disciplinas1 = new ArrayList<>();
//        disciplinas1.add(new DisciplinaVisao("Lógica de Programação", alunos, curso1));
//        disciplinas1.add(new DisciplinaVisao("Banco de dados II", new ArrayList<>(), curso1));
//        disciplinas1.add(new DisciplinaVisao("Programação IV", new ArrayList<>(), curso1));
//        curso1.setDisciplinas(disciplinas1);
//        ArrayList<CursoVisao> cursos1 = new ArrayList<>();
//        cursos1.add(curso1);
////        cursos1.add(curso1);
////        cursos1.add(curso1);
//        ArrayList<CampusVisao> campus = new ArrayList<>();
//        campus.add(new CampusVisao("Anápolis CET", cursos1));
//        //================================================================================================
//        CursoVisao curso2 = new CursoVisao("Sistemas de Informação - 2009/1");
//        ArrayList<DisciplinaVisao> disciplinas2 = new ArrayList<>();
//        disciplinas2.add(new DisciplinaVisao("Fundamentos da matemática para a computação", new ArrayList<>(), curso2));
//        disciplinas2.add(new DisciplinaVisao("Banco de dados I", new ArrayList<>(), curso2));
//        curso2.setDisciplinas(disciplinas2);
//        ArrayList<CursoVisao> cursos2 = new ArrayList<>();
//        cursos2.add(curso2);
//        campus.add(new CampusVisao("Pólo CEAR - Formosa", cursos2));
//        ArrayList<AnoSemestreVisao> anoSemestreVisaos = new ArrayList<>();
//        anoSemestreVisaos.add(new AnoSemestreVisao("2018/1", campus));
//        anoSemestreVisaos.add(new AnoSemestreVisao("2018/2", new ArrayList<CampusVisao>()));
//
//        return anoSemestreVisaos;
    }

    public void setDiaFreq(Date date){
        if(DateUtils.comparar(date, new Date()) > 0){
            new Notificacao(this, "Não pode ser escolhido um dia maior que o atual.", "alerta").exibir();
        }else {
            this.dataDiaFreq = date;
            edtDiaFreq.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_lateral_btn_sair:
                new LimpaBaseLocal(this).execute();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        }else {
            super.onBackPressed();
        }
    }

    class CarregaDados extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            dadosCarregados = true;
            if(anoSemestreVisaoSelecionado != null) {
                campusSpinner.setAdapter(new ArrayAdapter<>(ActivityPrincipal.this, R.layout.layout_generico_item_list_view, anoSemestreVisaoSelecionado.getCampus()));
            }
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            new TarefaDAO(MyApplication.getInstancia()).limparTabela();
            GestorDeTarefasApp.getInstancia();
//            new FrequenciaAlunoControle().limparTabela();
//            new FrequenciaControle().limparTabela();
//            new FrequenciaAlunoControle().dropTable();
//            new FrequenciaControle().dropTable();
            criarListaDeAnoSemestre();
            return null;
        }
    }

}
