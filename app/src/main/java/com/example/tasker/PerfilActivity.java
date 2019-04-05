package com.example.tasker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PerfilActivity extends AppCompatActivity {

    private Button guardar, editButton;
    private EditText edit_profile_name, edit_edad, edit_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        final Button editButton = (Button) findViewById(R.id.button_edit);
        final Button guardar = (Button) findViewById(R.id.button_guardar);
        final EditText edit_profile_name = (EditText) findViewById(R.id.edit_text_nombre_usuario);
        final EditText edit_edad = (EditText) findViewById(R.id.edit_text_edad);
        final EditText edit_email = (EditText) findViewById(R.id.edit_text_email);

        edit_profile_name.setEnabled(false);
        edit_edad.setEnabled(false);
        edit_email.setEnabled(false);
        guardar.setEnabled(false);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_profile_name.setEnabled(true);
                edit_edad.setEnabled(true);
                edit_email.setEnabled(true);
                guardar.setEnabled(true);
                editButton.setVisibility(View.INVISIBLE);
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_profile_name.setEnabled(false);
                edit_edad.setEnabled(false);
                edit_email.setEnabled(false);
                guardar.setEnabled(false);
                editButton.setVisibility(View.VISIBLE);
            }
        });

    }
}
