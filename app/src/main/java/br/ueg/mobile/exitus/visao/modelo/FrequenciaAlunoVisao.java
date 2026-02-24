package br.ueg.mobile.exitus.visao.modelo;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import br.ueg.mobile.exitus.modelo.FrequenciaAluno;

public class FrequenciaAlunoVisao {
    private CheckBox checkBox;
    private FrequenciaAluno frequenciaAluno;

    public FrequenciaAlunoVisao(Context context, FrequenciaAluno frequenciaAluno) {
        checkBox = new CheckBox(context);
        checkBox.setText("");
        checkBox.setChecked(frequenciaAluno.getSituacao());
        this.frequenciaAluno = frequenciaAluno;
    }

    public void setLayoutParamsCheckbox(LinearLayout.LayoutParams layoutParams){
        checkBox.setLayoutParams(layoutParams);
    }

    public void setSituacao(boolean situacao){
        frequenciaAluno.setSituacao(situacao);
//        checkBox.setChecked(situacao);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public FrequenciaAluno getFrequenciaAluno() {
        return frequenciaAluno;
    }

    public void setFrequenciaAluno(FrequenciaAluno frequenciaAluno) {
        this.frequenciaAluno = frequenciaAluno;
    }
}
