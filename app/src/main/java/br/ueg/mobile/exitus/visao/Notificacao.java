package br.ueg.mobile.exitus.visao;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import br.ueg.mobile.exitus.R;

/**
 * Created by jonas on 11/06/2018.
 */

public class Notificacao {
    private View view;
    private String mensagem;
    private VisaoActivityAbstrata activity;
    private String severidade;
    private Snackbar snackbar;
    private DialogMensagemErro dialogMensagemErro;

    public Notificacao(VisaoActivityAbstrata activity, String mensagem, String severidade) {
        this.activity = activity;
        this.view = activity.getContentView();
        this.mensagem = mensagem;
        this.severidade = severidade;
        construirNotificacao();
    }

    private void construirNotificacao() {
        int duracao = Snackbar.LENGTH_LONG;
        int corDeFundo = -1;
        int corFonte = -1;
        switch (severidade){
            case "sucesso":
                duracao = Snackbar.LENGTH_LONG;
                corDeFundo = Color.parseColor("#08AE9E");
                corFonte = Color.WHITE;
                break;
            case "alerta":
                duracao = Snackbar.LENGTH_LONG;
                corDeFundo = Color.parseColor("#fff066");
                corFonte = Color.BLACK;
                break;
            case "erro":
                dialogMensagemErro = new DialogMensagemErro();
                Bundle bundle = new Bundle();
                bundle.putString("mensagem", mensagem);
                dialogMensagemErro.setArguments(bundle);
                return;
            default:
                duracao = Snackbar.LENGTH_LONG;
                corDeFundo = Color.parseColor("#08AE9E");
                corFonte = Color.WHITE;
                break;
        }
        Snackbar snackbar = Snackbar.make(view, mensagem, duracao);
        View snackView = snackbar.getView();
        TextView textView = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        snackView.setBackgroundColor(corDeFundo);
        textView.setTextColor(corFonte);
        if(mensagem != null && mensagem.length() > 93){
            snackbar.setAction("Detalhes", v -> {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(v.getContext(), android.support.design.R.style.Base_Theme_AppCompat_Light_Dialog_Alert);
                alertBuilder.setMessage(mensagem)
                        .setTitle("Mensagem completa")
                        .setNeutralButton("OK", (dialog, which) -> dialog.dismiss()).create().show();
            });
        }else {
            snackbar.setAction("OK", v -> {
                snackbar.dismiss();
            });
        }
        snackbar.setActionTextColor(corFonte);
        this.snackbar = snackbar;
    }

    public void exibir(){
        if(!activity.isVisible()){
            activity.addNotificacaoPendente(this);
            return;
        }
        if(snackbar == null){
            dialogMensagemErro.show(activity.getFragmentManager(), "dialog_erro");
        }else {
            snackbar.show();
        }
    }

}
