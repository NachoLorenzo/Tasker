package com.example.tasker;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UsuarioFragment.ComunicaUsuarioConActivity} interface
 * to handle interaction events.
 * Use the {@link UsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuarioFragment extends Fragment {


    private String nombre, edad, email, idUsuario;
    private ComunicaUsuarioConActivity mListener;
    private ImageView b_enviar;
    private EditText edit_comentario;
    private Spinner spinnerUsuario, spinnerTarea;
    private SpinnerAdapter adapterSpinnerUsuario;
    private ListView lista_comentarios;
    private ArrayList<String> list_comments;
    private ArrayAdapter<String> list_adapter;
    private CalendarView calendar;
    //private Long fechaCalendar = calendar.getDate();
    //private DateFormat formatoFecha = new SimpleDateFormat("dd-mm-yyyy");
    //private String fechaFinal = formatoFecha.format(fechaCalendar);
    private ArrayList<Usuario> lista_usuarios;
    private ArrayAdapter<Usuario> adapter_lista_usuarios;

    private Usuario objetoUsuario;

    private DatabaseReference referenceUsuario;

    public UsuarioFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UsuarioFragment newInstance(String param1, String param2) {
        UsuarioFragment fragment = new UsuarioFragment();
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
        v = inflater.inflate(R.layout.fragment_usuario, container, false);

        b_enviar = v.findViewById(R.id.button_enviar);
        edit_comentario = v.findViewById(R.id.edit_text_comentar);
        calendar = v.findViewById(R.id.calendarUsuario);
        spinnerTarea = v.findViewById(R.id.spinnerTarea);

        lista_comentarios = v.findViewById(R.id.list_comentarios);
        registerForContextMenu(lista_comentarios);
        list_comments = new ArrayList<String>();
        list_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_comments);
        lista_comentarios.setAdapter(list_adapter);

        spinnerUsuario = (Spinner) v.findViewById(R.id.spinnerUsuario);
        adapterSpinnerUsuario = new SpinnerAdapter() {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) { return null; }
            @Override
            public void registerDataSetObserver(DataSetObserver observer) { }
            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) { }
            @Override
            public int getCount() { return 0; }
            @Override
            public Object getItem(int position) { return null; }
            @Override
            public long getItemId(int position) { return 0; }
            @Override
            public boolean hasStableIds() { return false; }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) { return null; }
            @Override
            public int getItemViewType(int position) { return 0; }
            @Override
            public int getViewTypeCount() { return 0; }
            @Override
            public boolean isEmpty() { return false; }
            };
        //spinnerUsuario.setAdapter(adapterSpinnerUsuario);
        lista_usuarios = new ArrayList<Usuario>();
        adapter_lista_usuarios = new ArrayAdapter<Usuario>(getActivity(), android.R.layout.simple_spinner_item, lista_usuarios);
        adapter_lista_usuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        referenceUsuario = FirebaseDatabase.getInstance().getReference("Usuario").child("tasker-93d8e");
        //LISTENER BOTÓN ENVIAR
        b_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_comentario.getText().toString().matches("")) {
                    Toast.makeText(getContext(), "Escribe algo", Toast.LENGTH_SHORT).show();
                } else {
                    String comentario = edit_comentario.getText().toString();
                    list_comments.add(comentario);
                    list_adapter.notifyDataSetChanged();
                    edit_comentario.setText("");
                }
            }
        });

        /*spinnerUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referenceUsuario.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });*/
        return v;
    }

    @Override//MENU AL MANTENER PULSADO. OPCIÓN DE BORRAR COMENTARIO
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.list_comentarios){
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_delete, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo adapterContextMenu = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.action_delete:
                    list_comments.remove(list_adapter.getItem(adapterContextMenu.position));
                    list_adapter.notifyDataSetChanged();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UsuarioFragment.ComunicaUsuarioConActivity) {
            mListener = (ComunicaUsuarioConActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " tienes que implementar la interfaz ComunicaUsuarioConActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void registrarUsuario(){
        nombre = spinnerUsuario.getSelectedItem().toString();
        edad = objetoUsuario.getEdad();
        email = objetoUsuario.getEmail();
        idUsuario = referenceUsuario.push().getKey();
        //String fecha = fechaFinal.toString();
        //String tarea = spinnerTarea.getSelectedItem().toString();
        Usuario usuario = new Usuario(nombre, edad, email, idUsuario);
        referenceUsuario.child("Usuario").child(idUsuario).setValue(usuario);
    }

    public interface ComunicaUsuarioConActivity {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
