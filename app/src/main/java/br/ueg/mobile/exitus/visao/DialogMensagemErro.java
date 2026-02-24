package br.ueg.mobile.exitus.visao;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.ueg.mobile.exitus.R;

/**
 * Created by jonas on 11/06/2018.
 */

public class DialogMensagemErro extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_mensagem_erro);
        TextView mensagem = dialog.findViewById(R.id.dialog_mensagem_erro_txt_mensagem);
        mensagem.setText(getArguments().getString("mensagem"));
        Button ok = dialog.findViewById(R.id.dialog_mensagem_erro_btn_ok);
        ok.setOnClickListener(l ->{
            dialog.dismiss();
        });
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
