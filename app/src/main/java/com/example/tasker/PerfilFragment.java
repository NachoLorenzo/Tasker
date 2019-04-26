package com.example.tasker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilFragment.ComunicaPerfilconActivity} interface
 * to handle interaction events.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {


    private ComunicaPerfilconActivity mListener;
    private Button bGuardar, bEditar;
    private EditText edit_profile_name, edit_edad, edit_email;
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
        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_profile_name.setEnabled(false);
                edit_edad.setEnabled(false);
                edit_email.setEnabled(false);
                mListener.datosGuardados();
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
    public interface ComunicaPerfilconActivity {
        // TODO: Update argument type and name
        void datosGuardados();
    }

}
