package br.ueg.mobile.exitus.visao;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

import br.ueg.mobile.exitus.R;
import br.ueg.mobile.exitus.controle.ProfessorControle;
import br.ueg.mobile.exitus.modelo.Professor;
import br.ueg.mobile.exitus.servico_cliente.ExitusService;
import br.ueg.mobile.exitus.servico_cliente.Retorno;
import br.ueg.mobile.exitus.utils.ConnectionUtils;
import br.ueg.mobile.exitus.utils.Preferencias;

public class LoginActivity extends VisaoActivityAbstrata {
    private Button btnAlterarEnderecoServidor;
    private Button btnEntrar;
    private TextInputLayout txtLayoutUsuario;
    private EditText edtSenha;

    @Override
    protected void onResume() {
        super.onResume();
        new ExecutaAutenticadorService("getIdentificadorUsuario").execute();
    }

    @Override
    public View getContentView() {
        return findViewById(R.id.activity_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        if(Preferencias.getString(this, "endereco_servidor").isEmpty()){
//            Preferencias.putString(this, "endereco_servidor", "192.168.1.7:8080");
//        }
        txtLayoutUsuario = (TextInputLayout) findViewById(R.id.activity_login_txtlayout_edt_usuario);
        edtSenha = (EditText) findViewById(R.id.activity_login_edt_senha);
        btnAlterarEnderecoServidor = (Button) findViewById(R.id.login_btn_alterar_endereco);
        btnAlterarEnderecoServidor.setOnClickListener(l ->{
            DialogAlterarEnderecoServidor dialog = new DialogAlterarEnderecoServidor();
            Bundle bundle = new Bundle();
            bundle.putString("endereco_servidor", Preferencias.getString(this, "endereco_servidor"));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "tag");
        });
        btnEntrar = (Button) findViewById(R.id.activity_login_btn_entrar);
        btnEntrar.setOnClickListener(v -> {
//            new ProfessorControle(this).limparTabela();
            if (txtLayoutUsuario.getEditText().getText().toString().isEmpty() || edtSenha.getText().toString().isEmpty()) {
                new Notificacao(LoginActivity.this, "Preencha todos os campos!", "alerta").exibir();
            } else {
                new ExecutaAutenticadorService("autenticar").execute(txtLayoutUsuario.getEditText().getText().toString(), edtSenha.getText().toString());
            }
        });
    }

    public void carregarTelaPrincipal(){

    }

    class ExecutaAutenticadorService extends AsyncTask<String, Void, Object>{
        private String acao;
        private DialogCarregando carregando;

        public ExecutaAutenticadorService(String acao){
            this.acao = acao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(acao.equals("autenticar")) {
                carregando = new DialogCarregando();
                Bundle bundle = new Bundle();
                bundle.putString("mensagem", "Entrando...");
                carregando.setArguments(bundle);
                carregando.show(getFragmentManager(), "carregando_dialog");
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(o instanceof Retorno && ((Retorno) o).getMensagem() != null && (((Retorno) o).getMensagem().equals("No route to host")
                    || ((Retorno) o).getMensagem().equals("connect timed out") || ((Retorno) o).getMensagem().contains("failed to connect"))){
                if(carregando != null)carregando.dismiss();
                new Notificacao(LoginActivity.this, LoginActivity.this.getString(R.string.mensagem_sem_comunicacao_servidor), "erro").exibir();
                return;
            }
            switch (acao) {
                case "getIdentificadorUsuario" :
                    if(o instanceof String){
                        txtLayoutUsuario.setHint((String)o);
                    }else{
                        Retorno ret = (Retorno) o;
                        new Notificacao(LoginActivity.this, ret.getMensagem()
                                , ret.getSeveridadeMensagem()).exibir();
                    }
                    break;
                case "autenticar":
                    carregando.dismiss();
                    Retorno ret = (Retorno) o;
                    if(ret.isOk()){
                        ProfessorControle professorControle = new ProfessorControle();
                        professorControle.persistir((Professor) ret.getValor());
                        Intent intent = new Intent(LoginActivity.this, AnoSemestreActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("professor", (Serializable) ret.getValor());
                        startActivity(intent);
                    }else{
                        new Notificacao(LoginActivity.this, ret.getMensagem()
                                , ret.getSeveridadeMensagem()).exibir();
                    }
                    break;
            }

        }

        @Override
        protected Object doInBackground(String... params) {
            String url = "";
            if(!getSharedPreferences("exitus_prefs", MODE_PRIVATE).getString("endereco_servidor", "").isEmpty()) {
                url = "http://" + Preferencias.getString(LoginActivity.this, "endereco_servidor")
                        + "/exitus-webservice/services/AutenticadorServicePort";
            }
            switch (acao) {
                case "getIdentificadorUsuario":
                    if(ConnectionUtils.isOnline()) {
                        try {
                            return new ExitusService(url, 30000).getIdentificadorUsuario();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Retorno ret = new Retorno();
                            ret.setOk(false);
                            ret.setMensagem(e.getMessage());
                            ret.setSeveridadeMensagem("erro");
                            return ret;
                        }
                    }else{
                        return "Usuário";
                    }
                    // ------------------------------------------------------
                case "autenticar":
                    try {
                        return new ExitusService(url, 180000).autenticar(params[0], params[1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Retorno ret = new Retorno();
                        ret.setOk(false);
                        ret.setMensagem(e.getMessage());
                        ret.setSeveridadeMensagem("erro");
                        return ret;
                    }
            }
            return new Retorno();
        }
    }
}
