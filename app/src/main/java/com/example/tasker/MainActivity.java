package com.example.tasker;

import android.content.Intent;
import android.media.Image;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView buttonProfile, buttonMenu;
    private ImageView buttonUsuario, buttonProyecto; //TEMPORAL, BORRAR AL HACER EL ACTIVITY "ProyectoActivity"
    private String[] titulos;
    private DrawerLayout menuLateral;
    private ListView listaMenuLateral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView buttonProfile = (ImageView) findViewById(R.id.button_profile);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PerfilActivity.class));
            }
        });

        ImageView buttonUsuario = (ImageView) findViewById(R.id.button_usuario);
        buttonUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UsuarioActivity.class));
            }
        });

        ImageView buttonProyecto = (ImageView) findViewById(R.id.button_proyecto);
        buttonUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProyectoActivity.class));
            }
        });
        
    }
}
