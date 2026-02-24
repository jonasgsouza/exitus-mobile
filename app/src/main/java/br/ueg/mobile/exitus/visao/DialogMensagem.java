package br.ueg.mobile.exitus.visao;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.TextView;

import br.ueg.mobile.exitus.R;

public class DialogMensagem extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_mensagem);
        Button ok = dialog.findViewById(R.id.dialog_mensagem_btn_ok);
        ok.setOnClickListener(l -> dialog.dismiss());
        if(getArguments() != null) {
            TextView txtViewMensagem = dialog.findViewById(R.id.dialog_mensagem_txt_mensagem);
            TextView textViewTitulo = dialog.findViewById(R.id.dialog_mensagem_titulo);
            textViewTitulo.setText(getArguments().getString("titulo"));
            txtViewMensagem.setText(getArguments().getString("mensagem"));
        }
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

}
