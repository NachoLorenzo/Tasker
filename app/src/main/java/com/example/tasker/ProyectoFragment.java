package com.example.tasker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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

    private FloatingActionButton añadir, añadirProyecto, añadirUsuario;
    Animation fabOpen, fabClose, rotarForward, rotarBackward;
    boolean isOpen = false;

    private Proyecto proyecto;
    private Fragment fNuevoProyecto;
    private FragmentTransaction ft;
    private FragmentManager fm;
    private ListView listaUsuarios, listaTareas;
    private ArrayList<Usuario> arrayUsuarios;
    private ArrayList<Tarea> arrayTareas;
    private ArrayAdapter<Usuario> adapterListaUsuarios;
    private ArrayAdapter<Tarea> adapterListaTareas;

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

        //FIREBASE
        referenceProyecto = FirebaseDatabase.getInstance().getReference();
        //SPINNER PARA SELECCIONAR PROYECTO
        spinnerProyecto = (Spinner) v.findViewById(R.id.spinnerProyecto);

        //LISTAS Y ADAPTERS PARA AÑADIR A LOS FLOATING ACTION BUTTON
        listaUsuarios = v.findViewById(R.id.lista_usuarios);
        listaTareas = v.findViewById(R.id.lista_tareas);
        registerForContextMenu(listaUsuarios);
        registerForContextMenu(listaTareas);

        arrayUsuarios = new ArrayList<Usuario>();
        arrayTareas = new ArrayList<Tarea>();

        adapterListaUsuarios = new ArrayAdapter<Usuario>(getActivity(), android.R.layout.simple_list_item_1, arrayUsuarios);
        adapterListaTareas = new ArrayAdapter<Tarea>(getActivity(), android.R.layout.simple_list_item_1, arrayTareas);

        //BLOQUE DE FLOATING ACTION BUTTON
        añadir = (FloatingActionButton) v.findViewById(R.id.añadir);
        añadirProyecto = (FloatingActionButton) v.findViewById(R.id.añadir_proyecto);
        añadirUsuario = (FloatingActionButton) v.findViewById(R.id.añadir_usuario);

        //ANIMACIONES DEL FLOATING ACTION BUTTON
        fabOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        rotarForward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotar_forward);
        rotarBackward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotar_backward);

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animarFab();
            }
        });

        añadirProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fNuevoProyecto = new NuevoProyectoFragment().newInstance("","");
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fr_contenido_ppal, fNuevoProyecto);
                ft.commit();
            }
        });

        añadirUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Añadir usuario", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        return v;
    }

    private void animarFab(){
        if(isOpen){
            añadir.startAnimation(rotarForward);
            añadirProyecto.startAnimation(fabClose);
            añadirUsuario.startAnimation(fabClose);
            añadirProyecto.setClickable(false);
            añadirUsuario.setClickable(false);
            isOpen=false;
        }else{
            añadir.startAnimation(rotarBackward);
            añadirProyecto.startAnimation(fabOpen);
            añadirUsuario.startAnimation(fabOpen);
            añadirProyecto.setClickable(true);
            añadirUsuario.setClickable(true);
            isOpen=true;
        }
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
