package com.example.tasker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private RecyclerView listaUsuarios, listaTareas;
    private ArrayList<Usuario> arrayUsuarios;
    private ArrayList<Tarea> arrayTareas;
    private ArrayAdapter<Usuario> adapterListaUsuarios;
    private ArrayAdapter<Tarea> adapterListaTareas;

    private ArrayList<Proyecto> llistatProjectes;
    private ArrayList<String> llistatNom_UsuarisProjecte;
    ArrayList<String> llistatNomsProjecte;

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
        //Inicialitzem l'arrayList de projectes
        llistatProjectes = new ArrayList();
        llistatNomsProjecte = new ArrayList<>();
        //FIREBASE
        referenceProyecto = FirebaseDatabase.getInstance().getReference().child("Proyectos");

        //Consultem la llista de projectes
        referenceProyecto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    Proyecto unProjecte = d.getValue(Proyecto.class);
                    Log.d("MANEL","he llegit:"+unProjecte.getNombreProyecto());
                    llistatProjectes.add(unProjecte);
                    llistatNomsProjecte.add(unProjecte.getNombreProyecto());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v;
        v = inflater.inflate(R.layout.fragment_proyecto, container, false);



        //SPINNER PARA SELECCIONAR PROYECTO
        spinnerProyecto = v.findViewById(R.id.spinnerProyecto);

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

        //Adaptador de l'spinner


        ArrayAdapter<String> adaptadorSpinner = new ArrayAdapter<>(this.getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,llistatNomsProjecte);
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Omplim l'spinner amb el llistat de projectes
        Log.d("MANEL","tinc: "+llistatProjectes.size());
        spinnerProyecto.setAdapter(adaptadorSpinner);
        spinnerProyecto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Consultem en firebase pels usuaris del projecte seleccionat
                //mostraUsuarisProjecte(i);
                Log.d("MANEL","has triat el projecte "+adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }

    private void mostraUsuarisProjecte(int posicioProjecte){
        Proyecto proyectoSeleccionadoPorUsuario = llistatProjectes.get(posicioProjecte);
        ArrayList<String> llistatID_UsuarisProjecte = proyectoSeleccionadoPorUsuario.getId_usu();


        for(String id_usu:llistatID_UsuarisProjecte){
            //FIREBASE
            referenceProyecto = FirebaseDatabase.getInstance().getReference().child("Perfiles").child("id");
            referenceProyecto.equalTo(id_usu);
            //Consultem la llista de projectes
            referenceProyecto.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot d: dataSnapshot.getChildren()){
                        Perfil unPerfil = d.getValue(Perfil.class);
                        Log.d("MANEL","he llegit:"+unPerfil.getNombre());
                        llistatNom_UsuarisProjecte.add(unPerfil.getNombre());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }

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
