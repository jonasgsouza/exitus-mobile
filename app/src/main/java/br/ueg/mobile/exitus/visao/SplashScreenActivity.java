package br.ueg.mobile.exitus.visao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import br.ueg.mobile.exitus.R;
import br.ueg.mobile.exitus.controle.ProfessorControle;
import br.ueg.mobile.exitus.modelo.Professor;
import br.ueg.mobile.exitus.servico_cliente.ExitusService;
import br.ueg.mobile.exitus.servico_cliente.Retorno;
import br.ueg.mobile.exitus.utils.ConnectionUtils;
import br.ueg.mobile.exitus.utils.DatabaseUtils;
import br.ueg.mobile.exitus.utils.Preferencias;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends VisaoActivityAbstrata {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        Handler handle = new Handler();
        handle.postDelayed(() -> new Executa().execute(), 2000);
    }

    @Override
    public View getContentView() {
        return findViewById(R.id.activity_splash_screen);
    }

    public void exibirTela(Class<? extends Activity> Activity, Retorno ret){
        Intent intent = new Intent(this, Activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("retorno", ret);
        startActivity(intent);
    }

    class Executa extends AsyncTask<Void, Void, Retorno>{

        @Override
        protected void onPostExecute(Retorno ret) {
            super.onPostExecute(ret);
            if(ret.getMensagem() != null && ret.getMensagem().equals("login")){
                exibirTela(LoginActivity.class, null);
            }else if(ret.isOk()){
                exibirTela(ActivityPrincipal.class, ret);
            }else{
                exibirTela(LoginActivity.class, ret);
            }
        }

        @Override
        protected Retorno doInBackground(Void... params) {
            ProfessorControle professorControle = new ProfessorControle();
            Retorno ret = new Retorno();
            if(ConnectionUtils.isOnline()){
                if(professorControle.numeroDeLinhas() > 0){
                    //Validar token
                    String url = "";
                    if(!getSharedPreferences("exitus_prefs", MODE_PRIVATE).getString("endereco_servidor", "").isEmpty()) {
                        url = "http://" + Preferencias.getString(SplashScreenActivity.this, "endereco_servidor")
                                + "/exitus-webservice/services/AutenticadorServicePort";
                    }
                    try {
                        Retorno retToken = new ExitusService(url).validarToken(((Professor)professorControle.listar().getLista().get(0)).getToken());
                        if(!retToken.isOk()){
                            DatabaseUtils.getInstancia().limparBaseLocal();
                        }
                        return retToken;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Retorno retErro = new Retorno();
                        retErro.setOk(false);
                        retErro.setMensagem(e.getMessage());
                        retErro.setSeveridadeMensagem("erro");
                        return retErro;
                    }
                    //professores.get(0).getToken();
                }else{
                    ret.setMensagem("login");
                }
            }else{
                if(professorControle.numeroDeLinhas() > 0){
                    ret.setOk(true);
                    ret.setSeveridadeMensagem("sucesso");
                    ret.setMensagem("Trabalhando off-line");
                }else{
                    ret.setOk(false);
                    ret.setSeveridadeMensagem("alerta");
                    ret.setMensagem("Não é possível realizar o primeiro login sem internet");
                }
            }
            return ret;
        }


    }

}
