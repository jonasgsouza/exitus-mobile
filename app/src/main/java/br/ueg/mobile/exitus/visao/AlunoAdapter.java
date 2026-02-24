package br.ueg.mobile.exitus.visao;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ueg.mobile.exitus.R;
import br.ueg.mobile.exitus.visao.modelo.AlunoVisao;
import br.ueg.mobile.exitus.visao.modelo.FrequenciaAlunoVisao;
import br.ueg.mobile.exitus.visao.modelo.FrequenciaVisao;

/**
 * Created by andre on 05/05/2018.
 */

public class AlunoAdapter extends ArrayAdapter<AlunoVisao> implements View.OnClickListener{

    private List<AlunoVisao> dataSet;
    private List<FrequenciaVisao> frequenciaVisaoList;
    private Context mContext;
    private Integer numeroAulas;

    public AlunoAdapter(List<AlunoVisao> data, List<FrequenciaVisao> frequenciaVisaoList, Context context, Integer numeroAulas) {
        super(context, R.layout.layout_aluno_card_view, data);
        this.dataSet = data;
        this.numeroAulas = numeroAulas;
        this.frequenciaVisaoList = frequenciaVisaoList;
    }

    @Override
    public void onClick(View v) {

    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtNome;
        List<CheckBox> checkBoxes;
        FrequenciaAlunoVisao frequenciaAlunoVisao;
        ViewGroup rootAlunoCheckbox;
        CheckBox checkBoxGeral;
        ViewGroup layoutHeader;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        AlunoVisao dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_aluno_card_view, parent, false);
            viewHolder.txtNome = convertView.findViewById(R.id.txtView_aluno_nome);
            viewHolder.checkBoxes = new ArrayList<>();
            viewHolder.rootAlunoCheckbox = convertView.findViewById(R.id.root_aluno_checkbox);
            for(int i = 0; i < numeroAulas; i++){
                FrequenciaVisao frequenciaVisao = frequenciaVisaoList.get(i);

                FrequenciaAlunoVisao frequenciaAlunoVisao = frequenciaVisao.getFrequenciaAlunoVisao(dataModel.getId());
                if(frequenciaAlunoVisao == null) continue;
                CheckBox checkBox = frequenciaAlunoVisao.getCheckBox();
                if (i > 0) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(25,0,0,0);
                    checkBox.setLayoutParams(layoutParams);
                }

                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    frequenciaAlunoVisao.setSituacao(isChecked);
                    boolean othersChecked = true;
                    for(CheckBox checkBox1 : viewHolder.checkBoxes){
                        if(checkBox != checkBox1) {
                            othersChecked = othersChecked && checkBox1.isChecked();
                        }
                    }
                    if(othersChecked || isChecked){
                        viewHolder.checkBoxGeral.setChecked(true);
                        viewHolder.layoutHeader.setBackgroundColor(Color.parseColor("#08AE9E"));
                    }else {
                        viewHolder.checkBoxGeral.setChecked(false);
                        viewHolder.layoutHeader.setBackgroundColor(Color.parseColor("#999999"));
                    }
                });
                viewHolder.checkBoxes.add(checkBox);
                viewHolder.rootAlunoCheckbox.addView(checkBox);
            }
            viewHolder.layoutHeader = convertView.findViewById(R.id.layout_header_aluno_card_view);
            viewHolder.checkBoxGeral = convertView.findViewById(R.id.checkBox_geral);
            viewHolder.checkBoxGeral.setOnClickListener(l -> {
                boolean isChecked = viewHolder.checkBoxGeral.isChecked();
                for(CheckBox checkBox : viewHolder.checkBoxes){
                    checkBox.setChecked(isChecked);
                }
                if(isChecked){
                    viewHolder.layoutHeader.setBackgroundColor(Color.parseColor("#08AE9E"));
                }else{
                    viewHolder.layoutHeader.setBackgroundColor(Color.parseColor("#999999"));
                }
            });

            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtNome.setText(dataModel.toString());

        // Return the completed view to render on screen
        return result;
    }
}
