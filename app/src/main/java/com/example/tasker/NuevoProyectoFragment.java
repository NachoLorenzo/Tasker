package com.example.tasker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NuevoProyectoFragment extends Fragment {

    private ComunicaNuevoProyectoFragmentConActivity mListener;
    private String nombreProyecto, idProyecto;
    private EditText editTextNombreProyecto;
    private Spinner spinnerUsuarios;
    private Button guardarProyecto;

    private DatabaseReference referenceNuevoProyecto;


    public NuevoProyectoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NuevoProyectoFragment newInstance(String param1, String param2) {
        NuevoProyectoFragment fragment = new NuevoProyectoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_nuevo_proyecto, container, false);

        editTextNombreProyecto = (EditText) v.findViewById(R.id.edit_text_nombre_proyecto);
        guardarProyecto = (Button) v.findViewById(R.id.button_guardarProyecto);
        referenceNuevoProyecto = FirebaseDatabase.getInstance().getReference();

        guardarProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProyecto();
            }
        });

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
        if (context instanceof ComunicaNuevoProyectoFragmentConActivity) {
            mListener = (ComunicaNuevoProyectoFragmentConActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " debes implementar ComunicaNuevoProyectoFragmentConActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void guardarProyecto(){
        String nombreProyecto = editTextNombreProyecto.getText().toString();
        String idProyecto = referenceNuevoProyecto.push().getKey();

        // OJO!!!!! Creamos un proyecto con lista de usuarios a NULL
        Proyecto proyecto = new Proyecto(nombreProyecto, idProyecto,null,null);
        referenceNuevoProyecto.child("Proyectos").child(idProyecto).setValue(proyecto);
    }

    public interface ComunicaNuevoProyectoFragmentConActivity {
        void onFragmentInteraction(Uri uri);
    }
}
