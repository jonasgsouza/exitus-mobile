package br.ueg.mobile.exitus.visao;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import br.ueg.mobile.exitus.R;
import br.ueg.mobile.exitus.controle.AnoSemestreControle;
import br.ueg.mobile.exitus.modelo.AnoSemestre;
import br.ueg.mobile.exitus.modelo.Professor;
import br.ueg.mobile.exitus.servico_cliente.ExitusService;
import br.ueg.mobile.exitus.servico_cliente.Retorno;
import br.ueg.mobile.exitus.utils.DatabaseUtils;
import br.ueg.mobile.exitus.utils.Preferencias;

public class AnoSemestreActivity extends VisaoActivityAbstrata {
    private Spinner anoSemestreSpinner;
    private Button continuar;
    private Professor p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ano_semestre);
        anoSemestreSpinner = findViewById(R.id.activity_ano_semestre_spinner);
        continuar = findViewById(R.id.activity_ano_semestre_btn_continuar);
        p = ((Professor)getIntent().getSerializableExtra("professor"));
        continuar.setOnClickListener(v -> {
            Preferencias.putLong("ano_semestre_id", ((AnoSemestre)anoSemestreSpinner.getSelectedItem()).getId());
            Intent intent = new Intent(AnoSemestreActivity.this, ActivityPrincipal.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("professor", p);
            startActivity(intent);
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        new CarregaAnoSemestre().execute(p.getToken());
    }

    @Override
    public View getContentView() {
        return findViewById(R.id.activity_ano_semestre);
    }

    class CarregaAnoSemestre extends AsyncTask<String, Void, Retorno>{
        private DialogCarregando carregando;

        @Override
        protected void onPreExecute() {
            carregando = new DialogCarregando();
            Bundle bundle = new Bundle();
            bundle.putString("mensagem", "Sincronizado...");

            carregando.setArguments(bundle);
            carregando.show(getFragmentManager(), "carregando_dialog");
        }

        @Override
        protected void onPostExecute(Retorno ret) {
            carregando.dismiss();
            if(ret.isOk()){
                ArrayAdapter arrayAdapter = new ArrayAdapter(AnoSemestreActivity.this, R.layout.layout_generico_item_list_view, ret.getLista());
                anoSemestreSpinner.setAdapter(arrayAdapter);
            }else{
                new Notificacao(AnoSemestreActivity.this, ret.getMensagem()
                        , ret.getSeveridadeMensagem()).exibir();
            }
        }

        @Override
        protected Retorno doInBackground(String... strings) {
            String url = "http://" + Preferencias.getString("endereco_servidor")
                    + "/exitus-webservice/services/AnoSemestreServicePort";
            Retorno ret = new Retorno();
            try {
                ret = new ExitusService(url).getAnoSemestre(strings[0]);
                if(ret.isOk()){
                    AnoSemestreControle anoSemestreControle = new AnoSemestreControle();
                    for(Object o : ret.getLista()){
                        AnoSemestre anoSemestre = (AnoSemestre) o;
                        anoSemestreControle.persistir(anoSemestre);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                DatabaseUtils.getInstancia().limparBaseLocal();
                ret.setOk(false);
                ret.setMensagem(e.getMessage());
                ret.setSeveridadeMensagem("erro");
            }
            return ret;
        }
    }
}
