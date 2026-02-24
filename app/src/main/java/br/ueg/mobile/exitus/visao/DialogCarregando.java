package br.ueg.mobile.exitus.visao;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import br.ueg.mobile.exitus.R;

/**
 * Created by jonas on 11/11/2018.
 */

public class DialogCarregando extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog carregando = new Dialog(getActivity());
        carregando.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        carregando.setContentView(R.layout.dialog_carregando);
        TextView txtView = (TextView) carregando.findViewById(R.id.texto);
        if(getArguments() != null) {
            txtView.setText(getArguments().getString("mensagem"));
        }
        carregando.setCancelable(false);
        carregando.setCanceledOnTouchOutside(false);
        return carregando;
    }
}
