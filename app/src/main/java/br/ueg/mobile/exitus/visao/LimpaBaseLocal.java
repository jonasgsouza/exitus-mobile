package br.ueg.mobile.exitus.visao;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import br.ueg.mobile.exitus.utils.DatabaseUtils;

/**
 * Created by jonas on 11/11/2018.
 */

public class LimpaBaseLocal extends AsyncTask<Void, Void, Void> {
    private DialogCarregando carregando;
    private Activity activity;

    public LimpaBaseLocal(Activity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        carregando = new DialogCarregando();
        Bundle bundle = new Bundle();
        bundle.putString("mensagem", "Saindo...");
        carregando.setArguments(bundle);
        carregando.show(activity.getFragmentManager(), "saindo_dialog");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        carregando.dismiss();
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected Void doInBackground(Void... params) {
        DatabaseUtils.getInstancia().limparBaseLocal();
        return null;
    }
}
