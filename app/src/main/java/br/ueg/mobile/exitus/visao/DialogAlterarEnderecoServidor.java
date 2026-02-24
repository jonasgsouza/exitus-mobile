package br.ueg.mobile.exitus.visao;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import br.ueg.mobile.exitus.R;
import br.ueg.mobile.exitus.utils.Preferencias;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jonas on 11/06/2018.
 */

public class DialogAlterarEnderecoServidor extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Alterar endereço do servidor");
        dialog.setContentView(R.layout.dialog_alterar_endereco_servidor);
        String enderecoServidor = getArguments().getString("endereco_servidor");
        EditText edtEnderecoServidor = (EditText) dialog.findViewById(R.id.dialog_alterar_endereco_servidor_txt_endereco);
        edtEnderecoServidor.setText(enderecoServidor);
        Button fechar = (Button) dialog.findViewById(R.id.dialog_alterar_endereco_servidor_btn_fechar);
        fechar.setOnClickListener(v -> {
            dialog.dismiss();
        });
        Button salvar = (Button) dialog.findViewById(R.id.dialog_alterar_endereco_servidor_btn_salvar);
        salvar.setOnClickListener(v -> {
            Preferencias.putString(dialog.getOwnerActivity(),"endereco_servidor", edtEnderecoServidor.getText().toString());
            dialog.dismiss();
        });
        return dialog;
    }
}
