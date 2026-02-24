package br.ueg.mobile.exitus.visao;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.ueg.mobile.exitus.R;
import br.ueg.mobile.exitus.modelo.OfertaDisciplinaAluno;
import br.ueg.mobile.exitus.sincronizador.GestorDeTarefasApp;
import br.ueg.mobile.exitus.sincronizador.tarefa.Tarefa;
import br.ueg.mobile.exitus.utils.ObjectUtils;
import br.ueg.mobile.exitus.visao.modelo.CursoVisao;
import br.ueg.mobile.exitus.visao.modelo.DisciplinaVisao;

/**
 * Created by andre on 05/05/2018.
 */

public class CursoAdapter extends ArrayAdapter<CursoVisao> implements View.OnClickListener{

    private List<CursoVisao> dataSet;
    private Context mContext;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private DisciplinaVisao disciplinaSelecionada;

    public CursoAdapter(List<CursoVisao> data, Context context, SlidingUpPanelLayout slidingUpPanelLayout) {
        super(context, R.layout.layout_curso_card_view, data);
        this.dataSet = data;
        this.slidingUpPanelLayout = slidingUpPanelLayout;
    }

    @Override
    public void onClick(View v) {

    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtNome;
        ListView listView;
    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        CursoVisao dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_curso_card_view, parent, false);
            viewHolder.txtNome = convertView.findViewById(R.id.txtView_curso_nome);
            CustomListView customListView = new CustomListView(getContext());
//            ListView disciplinaListView = (ListView) convertView.findViewById(R.id.disciplina_list_view);

            ((ViewGroup)convertView.findViewById(R.id.root_disciplina_list_view)).addView(customListView);
            customListView.setOnItemClickListener((parent1, view, position1, id) -> {
                disciplinaSelecionada = (DisciplinaVisao) parent1.getItemAtPosition(position1);
                if(!disciplinaSelecionada.isAlunosSincronizados()){
                    DialogMensagem dialogMensagem = new DialogMensagem();
                    Bundle bundle = new Bundle();
                    bundle.putString("titulo", "Sincronização pendente");
                    bundle.putString("mensagem", "Sincronização dos alunos em andamento. Não será possível lançar " +
                            "frequência para essa disciplina no momento");
                    dialogMensagem.setArguments(bundle);
                    new AgendaTarefaTask().execute(disciplinaSelecionada);
                    dialogMensagem.show(((AppCompatActivity)getContext()).getSupportFragmentManager(), "dialog_msg");
                    return;
                }
                TextView nomeDisciplina = slidingUpPanelLayout.findViewById(R.id.txtView_disciplina);
                TextView txtAulasMinistradas = slidingUpPanelLayout.findViewById(R.id.textView_aulas_ministradas);
                TextView txtAulasPrevistas = slidingUpPanelLayout.findViewById(R.id.textView_aulas_previstas);
                String aulasMinistradas = ObjectUtils.isNullOrEmpty(disciplinaSelecionada.getAulasLancadas()) ? "-" :
                        disciplinaSelecionada.getAulasLancadas().toString();
                String aulasPrevistas = ObjectUtils.isNullOrEmpty(disciplinaSelecionada.getAulasPrevistas()) ? "-" :
                        disciplinaSelecionada.getAulasPrevistas().toString();
                txtAulasMinistradas.setText(aulasMinistradas);
                txtAulasPrevistas.setText(aulasPrevistas);
                nomeDisciplina.setText(disciplinaSelecionada.toString());
                reLayoutChildren(slidingUpPanelLayout);
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            });
            viewHolder.listView = customListView;
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.txtNome.setText(dataModel.toString());
        DisciplinaAdapter disciplinaArrayAdapter = new DisciplinaAdapter(dataModel.getDisciplinas(),getContext());
        viewHolder.listView.setAdapter(disciplinaArrayAdapter);

        // Return the completed view to render on screen
        return result;
    }

    public DisciplinaVisao getDisciplinaSelecionada(){
        return disciplinaSelecionada;
    }

    private void reLayoutChildren(View view) {
        view.measure(
                View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), View.MeasureSpec.EXACTLY));
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    static class AgendaTarefaTask extends AsyncTask<DisciplinaVisao, Void, Void>{

        @Override
        protected Void doInBackground(DisciplinaVisao... disciplinaVisaos) {
            String params = "{\"oferta_disciplina\":" + disciplinaVisaos[0].getId() + ", \"data_atualizacao\": \""
                    + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "\"}";
            Tarefa<OfertaDisciplinaAluno> tarefa = new Tarefa<>(params);
            tarefa.setClasseModelo(OfertaDisciplinaAluno.class);
            GestorDeTarefasApp.getInstancia().agendarTarefa(tarefa);
            return null;
        }
    }

    static class eventoTask extends AsyncTask<DisciplinaVisao, Void, Void>{

        @Override
        protected Void doInBackground(DisciplinaVisao... disciplinaVisaos) {
            return null;
        }
    }
}
