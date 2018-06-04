package com.mecanicosgruas.edu.mecanicosgruas;


import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.model.ColorAcivity;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;

import java.text.Normalizer;

public class ActivityRegistro extends AppCompatActivity {

    Button btnCrearCuenta;
    EditText nickname;
    EditText nombre;
    EditText apellido;
    EditText email;
    EditText password;
    EditText telefono;
    ActionBar actionBar;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Get view
        btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);

        nickname = (EditText)findViewById(R.id.editTxtNicknameRegister);
        nombre = (EditText)findViewById(R.id.editTxtNombreRegister);
        apellido = (EditText)findViewById(R.id.editTxtApellidoRegister);
        email = (EditText)findViewById(R.id.editTxtEmailRegister);
        password = (EditText)findViewById(R.id.editTxtPasswordRegister);
        telefono = (EditText)findViewById(R.id.editTxtTelefonoRegister);

        //Setting Activity
        actionBar = getSupportActionBar();
        actionBar.setTitle("Registro");
        actionBar.setDisplayHomeAsUpEnabled(true);


        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valideRegitser())
                {
                    String nameUser = nombre.getText().toString();
                    String apellidoUser = apellido.getText().toString();
                    nameUser = Normalizer.normalize(nameUser, Normalizer.Form.NFD);
                    nameUser = nameUser.replaceAll("[^\\p{ASCII}]", "");
                    apellidoUser = Normalizer.normalize(apellidoUser, Normalizer.Form.NFD);
                    apellidoUser = apellidoUser.replaceAll("[^\\p{ASCII}]", "");
                    User usuario = new User(
                                            nickname.getText().toString(),
                                            nameUser,
                                            apellidoUser,
                                            email.getText().toString(),
                                            password.getText().toString(),
                                            telefono.getText().toString()
                                            );
                    //Registrar desde el servidor
                    if(ApiManager.isInternetConnection(getApplicationContext()))
                    ManagerREST.RegisterUser(usuario,ActivityRegistro.this);
                }
            }
        });

    }

    private boolean valideRegitser()
    {
        String emailString = email.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        //Validar datos vacios
        if(
                nickname.getText().toString().trim().isEmpty() ||
                nombre.getText().toString().trim().isEmpty() ||
                apellido.getText().toString().trim().isEmpty() ||
                emailString.trim().isEmpty() ||
                password.getText().toString().trim().isEmpty() ||
                telefono.getText().toString().trim().isEmpty()
            )
        {
            Toast.makeText(getApplicationContext(),"Faltan llenar campos",Toast.LENGTH_LONG).show();
            return false;
        }


        // Validar email
        if (!emailString.matches(emailPattern))
        {
            Toast.makeText(getApplicationContext(),"Correo electronico no es valido",Toast.LENGTH_LONG).show();
            return false;
        }

        ColorAcivity colorAcivity =  new ManagerBD(this).GetColorActivity(ApiManager.REGISTER_ACTIIVT);
        if(colorAcivity!=null)
        {
            RelativeLayout layout = findViewById(R.id.id_activity);
            int color  = colorAcivity.parseColor();
            layout.setBackgroundColor(color);
        }
        //Validar nombre y correo electronico en el servidor

        return true;
    }
}
