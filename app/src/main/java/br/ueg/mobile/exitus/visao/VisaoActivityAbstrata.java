package br.ueg.mobile.exitus.visao;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.ueg.mobile.exitus.servico_cliente.Retorno;

/**
 * Created by jonas on 23/08/2018.
 */

public abstract class VisaoActivityAbstrata extends AppCompatActivity {

    protected boolean isVisible = false;
    protected List<Notificacao> notificacoesPendentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificacoesPendentes = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
        exibirNotificacoesPendentes();
        Retorno ret = (Retorno) getIntent().getSerializableExtra("retorno");
        if(ret != null && ret.getMensagem() != null){
            new Notificacao(this, ret.getMensagem(), ret.getSeveridadeMensagem()).exibir();
        }
        getIntent().removeExtra("retorno");
    }

    protected void exibirNotificacoesPendentes(){
        for(Notificacao notificao : notificacoesPendentes){
            notificao.exibir();
        }
        notificacoesPendentes.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
    }

    public boolean isVisible(){
        return isVisible;
    }

    public void addNotificacaoPendente(Notificacao notificacao){
        notificacoesPendentes.add(notificacao);
    }

    public abstract View getContentView();

    public Context getContext(){
        return getApplicationContext();
    }

}
