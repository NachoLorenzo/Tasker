package com.example.tasker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


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
    private EditText edit_fecha, edit_tarea, edit_comentario;
    private ListView lista_comentarios;

    private ArrayList<String> list_items;
    private ArrayAdapter<String> list_adapter;

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
        edit_fecha = v.findViewById(R.id.edit_text_fecha);
        edit_tarea = v.findViewById(R.id.edit_text_tarea);
        lista_comentarios = v.findViewById(R.id.list_comentarios);
        registerForContextMenu(lista_comentarios);
        list_items = new ArrayList<String>();
        list_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_items);
        lista_comentarios.setAdapter(list_adapter);

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
    public interface ComunicaUsuarioConActivity {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
