package br.ueg.mobile.exitus.visao;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;

import br.ueg.mobile.exitus.R;

public class DialogMensagemForcarSincronizacaoFrequencia extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_mensagem_forcar_sincronizacao_frequencia);
        Button ok = dialog.findViewById(R.id.dialog_mensagem_sincronizacao_btn_ok);
        ok.setOnClickListener(l ->{
            if(getContext() instanceof LancarFrequenciaActivity){
                ((LancarFrequenciaActivity) getContext()).atualizarFrequencia();
            }
            dialog.dismiss();
        });
        Button cancelar = dialog.findViewById(R.id.dialog_mensagem_sincronizacao_btn_cancelar);
        cancelar.setOnClickListener(l -> dialog.dismiss());
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
