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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
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

    private FloatingActionButton añadir, añadirProyecto, añadirUsuario, añadirTarea;
    Animation fabOpen, fabClose, rotarForward, rotarBackward;
    boolean isOpen = false;

    private Proyecto proyecto;
    private Fragment fNuevoProyecto, fNuevaTarea;
    private FragmentTransaction ft;
    private FragmentManager fm;
    private RecyclerView listaUsuarios, listaTareas;
    private RecyclerView.LayoutManager lm;
    private ArrayList<Usuario> arrayUsuarios;
    private ArrayList<Tarea> arrayTareas;
    private ArrayAdapter<Usuario> adapterListaUsuarios;
    private ArrayAdapter<Tarea> adapterListaTareas;
    private AdaptadorRecyclerUsuaris aru;

    private ArrayList<Proyecto> listadoProyectos;
    private ArrayList<String> listadoNombres_UsuariosProyecto;
    ArrayList<String> listadoNombresProyectos;
    private ArrayList<Proyecto> llistatProjectes;
    private ArrayList<Perfil> llistatPerfilsProjecte;
    private HashMap<String,Perfil> llistaDeTotsElsPerfils = new HashMap<>();
    private ArrayList<String> llistatNomsProjecte;
    private  ArrayList<Perfil> llistatPerfils;

    public ProyectoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProyectoFragment newInstance() {
        ProyectoFragment fragment = new ProyectoFragment();
        return fragment;
    }


    public void consultaProjectes() {
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


        arrayTareas = new ArrayList<Tarea>();


        adapterListaTareas = new ArrayAdapter<Tarea>(getActivity(), android.R.layout.simple_list_item_1, arrayTareas);

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

        //SPiNNER

        //Inicialitzem l'arrayList de projectes
        llistatProjectes = new ArrayList<Proyecto>(); //ArrayList amb TOTS els projectes disponibles
        llistatNomsProjecte = new ArrayList<>(); //ArrayList només amb els noms dels projectes (per a l'spinner)
        omplirSpinnerDeProjectes(); //Consulta firebase i ompli l'spinner amb els noms dels projectes.

        return v;
    }

    private void omplirSpinnerDeProjectes(){
        //FIREBASE
        Query consultaListaProjectes= FirebaseDatabase.getInstance().getReference().child("Proyectos").orderByChild("nombreProyecto");

        //Consultem la llista de projectes
        consultaListaProjectes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    Proyecto unProjecte = d.getValue(Proyecto.class);
                    ArrayList <String> llistaDusuarisDelProjecte = new ArrayList<String>();
                    //obtenim el llistat d'usuaris del projecte.
                    for(DataSnapshot unUsuari: d.child("usuarios").getChildren()){
                        llistaDusuarisDelProjecte.add(unUsuari.getValue(String.class));
                    }
                    // Afegiml'arraylist d'usuaris del projecte a l'objecte Projecte
                    unProjecte.setId_usu(llistaDusuarisDelProjecte);
                    llistatProjectes.add(unProjecte);
                    llistatNomsProjecte.add(unProjecte.getNombreProyecto());
                }

                ArrayAdapter<String> adaptadorSpinner = new ArrayAdapter<>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,llistatNomsProjecte);
                adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //Omplim l'spinner amb el llistat de projectes
                spinnerProyecto.setAdapter(adaptadorSpinner);
                spinnerProyecto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //Consultem en firebase pels usuaris del projecte seleccionat
                        //mostraUsuarisProjecte(i);
                        carregaTotsElsPerfilsDelProjecte(i);
                        ompliRecyclerUsuaris();
                        carregarTotesLesTasquesDelProjecte();
                       // omplirRecyclerTasques();
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

    private void carregarTotesLesTasquesDelProjecte() {

    }

    private void ompliRecyclerUsuaris() {
        // LayoutManager del RecyclerView Usuarios
        lm = new LinearLayoutManager(getActivity().getApplicationContext());
        listaUsuarios.setLayoutManager(lm);
         aru=new AdaptadorRecyclerUsuaris(llistatPerfilsProjecte);
        listaUsuarios.setAdapter(aru);

    }

    private void carregaTotsElsPerfilsDelProjecte(int posicioProjecte) {
        Proyecto proyectoSeleccionadoPorUsuario = llistatProjectes.get(posicioProjecte);
        final ArrayList<String> identificadorsUsuarisProjecte;
         llistatPerfilsProjecte = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Perfiles");
        identificadorsUsuarisProjecte = proyectoSeleccionadoPorUsuario.getId_usu();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Perfil unPerfil = d.getValue(Perfil.class);
                    //obtenim el llistat de TOTS perfils.
                    llistaDeTotsElsPerfils.put(unPerfil.getId(), unPerfil);
                    Log.d("MANEL","Components: "+unPerfil.getNombre()+"-"+unPerfil.getEmail());
                }
                //Obtenim un ArrayList amb només els perfils del projecte seleccionat
                for(String idPerfil:identificadorsUsuarisProjecte){
                    if(llistaDeTotsElsPerfils.containsKey(idPerfil)){
                        Log.d("MANEL","Usuaris del projecte: "+llistaDeTotsElsPerfils.get(idPerfil).getNombre()+"-"+llistaDeTotsElsPerfils.get(idPerfil).getEmail());
                        llistatPerfilsProjecte.add(llistaDeTotsElsPerfils.get(idPerfil));

                    }
                }
                aru.notifyDataSetChanged();

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
            consultaProjectes();
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
