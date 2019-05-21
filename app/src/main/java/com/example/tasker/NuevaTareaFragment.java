package com.example.tasker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NuevaTareaFragment extends Fragment {

    private ComunicaNuevaTareaConActivity mListener;
    private String nombre, id;
    private EditText editNombreTarea, editDescripcionTarea;
    private Button guardarTarea;
    private DatabaseReference referenceNuevaTarea;

    public NuevaTareaFragment() {

    }

    public static NuevaTareaFragment newInstance() {
        NuevaTareaFragment fragment = new NuevaTareaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_nueva_tarea, container, false);

        editNombreTarea = v.findViewById(R.id.edit_text_nombre_tarea);
        editDescripcionTarea = v.findViewById(R.id.edit_DescripciónTarea);
        guardarTarea = v.findViewById(R.id.button_guardar_tarea);

        referenceNuevaTarea = FirebaseDatabase.getInstance().getReference();

            guardarTarea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guardarTarea();
                }
            });
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComunicaNuevaTareaConActivity) {
            mListener = (ComunicaNuevaTareaConActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ComunicaNuevaTareaConActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void guardarTarea(){

            String nombreTarea = editNombreTarea.getText().toString();
            String descripcionTarea = editDescripcionTarea.getText().toString();
            String idTarea = referenceNuevaTarea.push().getKey();

            if(nombreTarea.equals("") || descripcionTarea.equals("")){
                Snackbar.make(getView(), "Falta información", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }else {

                Tarea tarea = new Tarea(nombreTarea, descripcionTarea, idTarea);
                referenceNuevaTarea.child("Tareas").child(idTarea).setValue(tarea);
                editNombreTarea.setText("");
                editDescripcionTarea.setText("");
            }
    }

    public interface ComunicaNuevaTareaConActivity {
        void onFragmentInteraction(Uri uri);
    }
}
