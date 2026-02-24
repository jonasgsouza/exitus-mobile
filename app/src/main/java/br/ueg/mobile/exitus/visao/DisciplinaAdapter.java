package br.ueg.mobile.exitus.visao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.ueg.mobile.exitus.R;
import br.ueg.mobile.exitus.visao.modelo.DisciplinaVisao;

/**
 * Created by andre on 05/05/2018.
 */

public class DisciplinaAdapter extends ArrayAdapter<DisciplinaVisao> implements View.OnClickListener{

    private List<DisciplinaVisao> dataSet;
    private Context mContext;

    public DisciplinaAdapter(List<DisciplinaVisao> data, Context context) {
        super(context, R.layout.layout_generico_item_list_view, data);
        this.dataSet = data;
    }

    @Override
    public void onClick(View v) {

    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtNome;
    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DisciplinaVisao dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_generico_item_list_view, parent, false);
            viewHolder.txtNome = (TextView) convertView.findViewById(R.id.disciplina_nome_item);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.txtNome.setText(dataModel.getNome());
        // Return the completed view to render on screen
        return result;
    }
}
