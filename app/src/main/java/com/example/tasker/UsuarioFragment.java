package com.example.tasker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


    private ComunicaUsuarioConActivity mListener;
    private ImageView b_enviar;
    private EditText edit_comentario;
    private Spinner spinnerUsuario, spinnerTarea;
    private ListView lista_comentarios;
    private CalendarView calendar;
    //private Long fechaCalendar = calendar.getDate();
    //private DateFormat formatoFecha = new SimpleDateFormat("dd-mm-yyyy");
    //private String fechaFinal = formatoFecha.format(fechaCalendar);
    private ArrayList<String> list_items;
    private ArrayAdapter<String> list_adapter;

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
        list_items = new ArrayList<String>();
        list_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_items);
        lista_comentarios.setAdapter(list_adapter);
        spinnerUsuario = (Spinner) v.findViewById(R.id.spinnerUsuario);

        referenceUsuario = FirebaseDatabase.getInstance().getReference("Usuario").child("tasker-93d8e");
        //LISTENER BOTÓN ENVIAR
        b_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_comentario.getText().toString().matches("")) {
                    Toast.makeText(getContext(), "Escribe algo", Toast.LENGTH_SHORT).show();
                } else {
                    String comentario = edit_comentario.getText().toString();
                    list_items.add(comentario);
                    list_adapter.notifyDataSetChanged();
                    edit_comentario.setText("");
                }
            }
        });
        lista_comentarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();
            }
        });


        /*referenceUsuario.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                Log.d(TAG, "showData: id: " + usuario.getId());
                Log.d(TAG, "showData: nombre: " + usuario.getNombre());
                Log.d(TAG, "showData: edad: " + usuario.getEdad());
                Log.d(TAG, "showData: email: " + usuario.getEmail());
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
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
                    list_items.remove(list_adapter.getItem(adapterContextMenu.position));
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
        String nombre = spinnerUsuario.getSelectedItem().toString();
        String edad = objetoUsuario.getEdad().toString();
        String email = objetoUsuario.getEmail().toString();
        //String fecha = fechaFinal.toString();
        String tarea = spinnerTarea.getSelectedItem().toString();
    }

    public interface ComunicaUsuarioConActivity {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
