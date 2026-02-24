package br.ueg.mobile.exitus.visao;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;

import br.ueg.mobile.exitus.R;

public class DialogAjudaStatusFrequencia extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_ajuda_status_frequencia);
        Button fechar = dialog.findViewById(R.id.dialog_ajuda_status_freq_button_ok);
        fechar.setOnClickListener(v -> {
            dialog.dismiss();
        });
        return dialog;
    }
}
