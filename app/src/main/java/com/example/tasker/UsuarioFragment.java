package com.example.tasker;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.DIRECTORY_PICTURES;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UsuarioFragment.ComunicaUsuarioConActivity} interface
 * to handle interaction events.
 * Use the {@link UsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuarioFragment extends Fragment {


    private String nombre, edad, email, idUsuario, idProyecto;
    private ComunicaUsuarioConActivity mListener;
    private ImageView b_enviar;
    private EditText edit_comentario;
    private Spinner spinnerUsuario, spinnerTarea;
    private SpinnerAdapter adapterSpinnerUsuario;
    private ListView lista_comentarios, zonaDocumentos;
    private ArrayList<String> list_comments, list_documents;
    private ArrayAdapter<String> list_adapter, adapter_lista_documentos;
    private CalendarView calendar;
    private ArrayList<Usuario> lista_usuarios;
    private ArrayAdapter<Usuario> adapter_lista_usuarios;
    private FloatingActionButton upload, download;
    private static final int gallery_intent = 1;

    private Usuario objetoUsuario;

    private DatabaseReference referenceUsuario;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private StorageReference reference; //REFERENCE PARA DESCARGAR ARCHIVOS

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

        upload = v.findViewById(R.id.upload);
        download = v.findViewById(R.id.download);
        storageRef = FirebaseStorage.getInstance().getReference();
        zonaDocumentos = v.findViewById(R.id.zonaDocumentos);
        list_documents = new ArrayList<>();
        adapter_lista_documentos = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list_documents);
        zonaDocumentos.setAdapter(adapter_lista_documentos);
        registerForContextMenu(zonaDocumentos);

        lista_comentarios = v.findViewById(R.id.list_comentarios);
        registerForContextMenu(lista_comentarios);
        list_comments = new ArrayList<>();
        list_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list_comments);
        lista_comentarios.setAdapter(list_adapter);


        spinnerUsuario = v.findViewById(R.id.spinnerUsuario);
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

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirDocumento();
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargar();
            }
        });

        return v;
    }

    @Override//MENU AL MANTENER PULSADO. OPCIÓN DE BORRAR COMENTARIO Y DESCARGAR DOCUMENTO
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        if(v.getId() == R.id.zonaDocumentos){
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_download, menu);
        }
        if(v.getId() == R.id.list_comentarios) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_delete, menu);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo adapterContextMenu = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.action_download:
                descargar();
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
        idProyecto = objetoUsuario.getId_proyecto();

        Usuario usuario = new Usuario(nombre, edad, email, idUsuario, idProyecto);
        referenceUsuario.child("Usuario").child(idUsuario).setValue(usuario);
    }

    private void subirDocumento() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        startActivityForResult(intent, gallery_intent);

    }

    private void descargar(){

        reference = storageRef.child("Documentos").child("image:6225");
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                descargaArchivo(getActivity().getApplicationContext(), "image:6225", ".jpeg", DIRECTORY_DOWNLOADS, url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void descargaArchivo(Context context, String nombreArchivo, String extension, String directorioDestino, String url){

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, directorioDestino, nombreArchivo + extension);

        downloadManager.enqueue(request);


    }

    //ALMACENAMOS LOS DOCUMENTOS SUBIDOS A LA APP
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == gallery_intent && resultCode == RESULT_OK){

            Uri uri = data.getData();
            //Creamos la carpeta Documentos y metemos el documento que está en el URI y le asigna un valor
            StorageReference path = storageRef.child("Documentos").child(uri.getLastPathSegment());
            path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity().getApplicationContext(), "Archivo subido", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public interface ComunicaUsuarioConActivity {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
