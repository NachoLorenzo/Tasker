package com.example.tasker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComunicaProyectoConActivity} interface
 * to handle interaction events.
 * Use the {@link ProyectoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProyectoFragment extends Fragment {

    private Spinner spinnerProyecto;
    private ComunicaProyectoConActivity mListener;
    private DatabaseReference referenceProyecto;
    private FloatingActionButton añadirProyecto;
    private Proyecto proyecto;

    public ProyectoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProyectoFragment newInstance() {
        ProyectoFragment fragment = new ProyectoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_proyecto, container, false);

        añadirProyecto = v.findViewById(R.id.añadir_proyecto);

        referenceProyecto = FirebaseDatabase.getInstance().getReference();
        /*añadirProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Usuario> listaUsuarios = proyecto.getListaUsuarios();
                Proyecto objetoProyecto = new Proyecto(listaUsuarios);
                referenceProyecto.child("Proyecto").setValue(objetoProyecto);
            }
        });*/

        spinnerProyecto = (Spinner) v.findViewById(R.id.spinnerProyecto);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComunicaProyectoConActivity) {
            mListener = (ComunicaProyectoConActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " tienes que implementar la interfaz ComunicaProyectoConActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface ComunicaProyectoConActivity {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
