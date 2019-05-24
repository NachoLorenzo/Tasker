package com.example.tasker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


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

    private FloatingActionButton añadir, añadirProyecto, añadirUsuario, añadirTarea;
    Animation fabOpen, fabClose, rotarForward, rotarBackward;
    boolean isOpen = false;

    private Proyecto proyecto;
    private Fragment fNuevoProyecto, fNuevaTarea;
    private FragmentTransaction ft;
    private FragmentManager fm;
    private RecyclerView listaUsuarios, listaTareas;
    private RecyclerView.LayoutManager lm;
    private AdaptadorRecyclerUsuarios aru;
    private AdaptadorRecyclerTareas art;


    private ArrayList<Proyecto> listadoProyectos;
    private ArrayList<Perfil> listadoPerfilesProyecto;
    private ArrayList<Tarea> listadoTareasProyecto;
    private HashMap<String,Perfil> listadoDePerfiles = new HashMap<>();
    private HashMap<String,Tarea> listadoDeTareas = new HashMap<>();
    private ArrayList<String> listadoNombresProyecto;

    public ProyectoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProyectoFragment newInstance() {
        ProyectoFragment fragment = new ProyectoFragment();
        return fragment;
    }


    public void consultaProyectos() {
        super.onStart();

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

        //SPINNER PARA SELECCIONAR PROYECTO
        spinnerProyecto = v.findViewById(R.id.spinnerProyecto);

        //LISTAS Y ADAPTERS PARA AÑADIR A LOS FLOATING ACTION BUTTON
        listaUsuarios = v.findViewById(R.id.lista_usuarios);
        listaTareas = v.findViewById(R.id.lista_tareas);
        registerForContextMenu(listaUsuarios);
        registerForContextMenu(listaTareas);

        //BLOQUE DE FLOATING ACTION BUTTON
        añadir = v.findViewById(R.id.añadir);
        añadirProyecto = v.findViewById(R.id.añadir_proyecto);
        añadirUsuario = v.findViewById(R.id.añadir_usuario);
        añadirTarea = v.findViewById(R.id.añadir_tarea);

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

        añadirTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fNuevaTarea = new NuevaTareaFragment().newInstance();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fr_contenido_ppal, fNuevaTarea);
                ft.commit();
            }
        });

        //SPiNNER

        //Inicializamos el arrayList de proyectos
        listadoProyectos = new ArrayList<Proyecto>(); //ArrayList con TODOS los proyectos disponibles
        listadoNombresProyecto = new ArrayList<>(); //ArrayList solo con nombres de proyectos para el Spinner

        llenaSpinnerDeNombres(); //Consulta firebase y llena el spinner con nombres

        return v;
    }

    private void llenaSpinnerDeNombres(){
        //FIREBASE
        Query consultaListaProyectos= FirebaseDatabase.getInstance().getReference().child("Proyectos").orderByChild("nombreProyecto");

        //Consultamos la lista de proyectos
        consultaListaProyectos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    Proyecto unProyecto = d.getValue(Proyecto.class);
                    ArrayList <String> listaUsuariosDelProyecto = new ArrayList<String>();
                    ArrayList <String> listaTareasDelProyecto = new ArrayList<String>();
                    //Obtenemos la lista de usuarios
                    for(DataSnapshot unUsuario: d.child("usuarios").getChildren()){
                        listaUsuariosDelProyecto.add(unUsuario.getValue(String.class));
                    }
                    for(DataSnapshot unaTarea: d.child("lista_tareas").getChildren()){
                        listaTareasDelProyecto.add(unaTarea.getValue(String.class));
                    }
                    //Añadimos el arrayList de usuarios del proyecto al objeto Proyecto
                    unProyecto.setId_usu(listaUsuariosDelProyecto);
                    unProyecto.setId_tareas(listaTareasDelProyecto);
                    listadoProyectos.add(unProyecto);
                    listadoNombresProyecto.add(unProyecto.getNombreProyecto());
                }

                ArrayAdapter<String> adaptadorSpinner = new ArrayAdapter<>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item, listadoNombresProyecto);
                adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //Llenamos el spinner con el listado de proyectos
                spinnerProyecto.setAdapter(adaptadorSpinner);
                spinnerProyecto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //Consultamos Firebase por los usuarios del proyecto seleccionado
                        //mostraUsuarisProjecte(i);
                        cargarPerfilesDelProyecto(i);
                        cargarTareasDelProyecto(i);
                        llenarRecyclerUsuarios();
                        llenarRecyclerTareas();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    private void llenarRecyclerUsuarios() {
        // LayoutManager del RecyclerView Usuarios
        lm = new LinearLayoutManager(getActivity().getApplicationContext());
        listaUsuarios.setLayoutManager(lm);
        aru=new AdaptadorRecyclerUsuarios(listadoPerfilesProyecto);
        listaUsuarios.setAdapter(aru);

    }

    private void llenarRecyclerTareas() {
        // LayoutManager del RecyclerView Tareas
        lm = new LinearLayoutManager(getActivity().getApplicationContext());
        listaTareas.setLayoutManager(lm);
        art = new AdaptadorRecyclerTareas(listadoTareasProyecto);
        listaTareas.setAdapter(art);
    }

    private void cargarPerfilesDelProyecto(int posProyecto) {
        Proyecto proyectoSeleccionado = listadoProyectos.get(posProyecto);
        final ArrayList<String> idUsuariosProyecto;
        listadoPerfilesProyecto = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Perfiles");
        idUsuariosProyecto = proyectoSeleccionado.getId_usu();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Perfil unPerfil = d.getValue(Perfil.class);
                    //Obtenemos el listado de TODOs los perfiles
                    listadoDePerfiles.put(unPerfil.getId(), unPerfil);
                }
                //Obtenemos un arrayList solo con los usuarios seleccionados
                for(String idPerfil:idUsuariosProyecto){
                    if(listadoDePerfiles.containsKey(idPerfil)){
                        listadoPerfilesProyecto.add(listadoDePerfiles.get(idPerfil));
                    }
                }
                aru.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void cargarTareasDelProyecto(int posProyecto){
        Proyecto proyectoSeleccionado = listadoProyectos.get(posProyecto);
        final ArrayList<String> idTareasProyecto; //Tareas del proyecto seleccionado

        listadoTareasProyecto = new ArrayList<>();

        idTareasProyecto = proyectoSeleccionado.getId_tareas();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Tareas");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Tarea unaTarea = d.getValue(Tarea.class);
                    //Obtenemos el listado de TODAS las tareas
                    listadoDeTareas.put(unaTarea.getId(), unaTarea);
                }
                //Obtenemos el listado solo con las tareas seleccionadas
                for(String idTarea:idTareasProyecto){
                    if(listadoDeTareas.containsKey(idTarea)){
                        listadoTareasProyecto.add(listadoDeTareas.get(idTarea));
                    }
                }
                art.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void animarFab(){
        if(isOpen){
            añadir.startAnimation(rotarForward);
            añadirProyecto.startAnimation(fabClose);
            añadirUsuario.startAnimation(fabClose);
            añadirTarea.startAnimation(fabClose);
            añadirProyecto.setClickable(false);
            añadirUsuario.setClickable(false);
            añadirTarea.setClickable(false);
            isOpen=false;
        }else{
            añadir.startAnimation(rotarBackward);
            añadirProyecto.startAnimation(fabOpen);
            añadirUsuario.startAnimation(fabOpen);
            añadirTarea.startAnimation(fabOpen);
            añadirProyecto.setClickable(true);
            añadirUsuario.setClickable(true);
            añadirTarea.setClickable(true);
            isOpen=true;
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComunicaProyectoConActivity) {
            mListener = (ComunicaProyectoConActivity) context;
            consultaProyectos();
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
