package com.example.tasker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PerfilFragment extends Fragment {


    private ComunicaPerfilconActivity mListener;
    private Button bGuardar, bEditar;
    private EditText edit_profile_name, edit_edad, edit_email;
    private DatabaseReference referencePerfil;

    public PerfilFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
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
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_perfil, container, false);

        edit_profile_name = v.findViewById(R.id.edit_text_nombre_usuario);
        edit_edad = v.findViewById(R.id.edit_text_edad);
        edit_email = v.findViewById(R.id.edit_text_email);

        edit_profile_name.setEnabled(false);
        edit_edad.setEnabled(false);
        edit_email.setEnabled(false);

        bGuardar = v.findViewById(R.id.button_guardar);

        referencePerfil = FirebaseDatabase.getInstance().getReference();

        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_profile_name.setEnabled(false);
                edit_edad.setEnabled(false);
                edit_email.setEnabled(false);
                guardarPerfil();
            }
        });
        bEditar = v.findViewById(R.id.button_edit);
        bEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_profile_name.setEnabled(true);
                edit_edad.setEnabled(true);
                edit_email.setEnabled(true);
            }
        });
        return v;
    }


 //   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComunicaPerfilconActivity) {
            mListener = (ComunicaPerfilconActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " tienes que implementar la interfaz ComunicaPerfilconActivity");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void guardarPerfil(){
        Proyecto proyectoRelacionado = new Proyecto();
        String nombre = edit_profile_name.getText().toString();
        String edad = edit_edad.getText().toString();
        String email = edit_email.getText().toString();
        String id = referencePerfil.push().getKey();
        String id_proyecto = proyectoRelacionado.getId();

        Usuario perfil = new Usuario(id, nombre, edad, email, id_proyecto);
        referencePerfil.child("Perfiles").child(id).setValue(perfil);

    }

    public interface ComunicaPerfilconActivity {

    }

}
