package br.ueg.mobile.exitus.visao;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import br.ueg.mobile.exitus.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LancarFrequenciaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LancarFrequenciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LancarFrequenciaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public LancarFrequenciaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LancarFrequenciaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LancarFrequenciaFragment newInstance(String param1, String param2) {
        LancarFrequenciaFragment fragment = new LancarFrequenciaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_disciplina, container, false);
        Button fecharSlidePanel = (Button) view.findViewById(R.id.fechar_slide_panel);
        fecharSlidePanel.setOnClickListener(l ->{
            ((SlidingUpPanelLayout)getActivity().findViewById(R.id.sliding_up_panel)).setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
