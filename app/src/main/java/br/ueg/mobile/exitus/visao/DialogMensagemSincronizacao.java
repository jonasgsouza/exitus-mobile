package br.ueg.mobile.exitus.visao;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.ueg.mobile.exitus.R;
import br.ueg.mobile.exitus.modelo.OfertaDisciplinaProfessor;
import br.ueg.mobile.exitus.sincronizador.GestorDeTarefasApp;
import br.ueg.mobile.exitus.sincronizador.tarefa.Tarefa;
import br.ueg.mobile.exitus.utils.Preferencias;

/**
 * Created by jonas on 11/06/2018.
 */

public class DialogMensagemSincronizacao extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_mensagem_sincronizacao);
        Button ok = dialog.findViewById(R.id.dialog_mensagem_sincronizacao_btn_ok);
        ok.setOnClickListener(l ->{
            String params = "{\"ano_semestre\":" + Preferencias.getLong("ano_semestre_id") + ", \"data_atualizacao\": \""
                    + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "\"}";
            Tarefa<OfertaDisciplinaProfessor> tarefa = new Tarefa<>(params);
            tarefa.setClasseModelo(OfertaDisciplinaProfessor.class);
            GestorDeTarefasApp.getInstancia().agendarTarefa(tarefa);
        });
        Button cancelar = dialog.findViewById(R.id.dialog_mensagem_sincronizacao_btn_cancelar);
        cancelar.setOnClickListener(l -> dialog.dismiss());
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
