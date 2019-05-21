package com.example.tasker;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Main3Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        PerfilFragment.ComunicaPerfilconActivity,
        UsuarioFragment.ComunicaUsuarioConActivity,
        ProyectoFragment.ComunicaProyectoConActivity,
        NuevoProyectoFragment.ComunicaNuevoProyectoFragmentConActivity,
        NuevaTareaFragment.ComunicaNuevaTareaConActivity {

        Fragment fperfil, fusuario, fproyecto;
        FragmentManager fm, fm2, fm3;
        FragmentTransaction ft, ft2, ft3;

        //private DatabaseReference reference;


    //  FrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //fl = findViewById(R.id.fr_contenido_ppal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //reference = FirebaseDatabase.getInstance().getReference("reference");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            //Creamos el fragment Perfil
            fperfil = new PerfilFragment().newInstance("","");
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            ft.replace(R.id.fr_contenido_ppal, fperfil);
            ft.commit();

        } else if (id == R.id.nav_proyectos) {
            //Creamos el fragment Proyecto
            fproyecto = new ProyectoFragment().newInstance();
            fm3 = getSupportFragmentManager();
            ft3 = fm3.beginTransaction();
            ft3.replace(R.id.fr_contenido_ppal, fproyecto);
            ft3.commit();

        } else if (id == R.id.nav_usuario) {
            //Creamos el fragment Usuario
            fusuario = new UsuarioFragment().newInstance("","");
            fm2 = getSupportFragmentManager();
            ft2 = fm2.beginTransaction();
            ft2.replace(R.id.fr_contenido_ppal, fusuario);
            ft2.commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void datosGuardados() {//HACER QUE ENVÍE DATOS A LA BD
        ft=fm.beginTransaction();
        ft.remove(fperfil);
        ft.commit();
    }
    //Interfaz UsuarioFragment
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
