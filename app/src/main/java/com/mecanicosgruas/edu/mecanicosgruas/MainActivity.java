package com.mecanicosgruas.edu.mecanicosgruas;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.model.ColorAcivity;

import java.util.List;

// Inicio de sesion
public class MainActivity extends AppCompatActivity {

    EditText editTxtUser;
    EditText editTxtPassword;
    Button btnLogin;
    Button btnRegister;
    //Button btnLoginFace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTxtUser = (EditText)findViewById(R.id.editTxtUser);
        editTxtPassword = (EditText)findViewById(R.id.editTxtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);
       // btnLoginFace = (Button)findViewById(R.id.btnLoginFacebook);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name;
                String password;
                name = editTxtUser.getText().toString();
                password = editTxtPassword.getText().toString();
                //Validar edit vacios
                if(name.toString().trim().isEmpty() || password.toString().trim().isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Falta llenar campos" , Toast.LENGTH_SHORT).show();
                    return;
                }
                //Validar desde el servidor, iniciar sesion
                if(ApiManager.isInternetConnection(getApplicationContext()))
                ManagerREST.LoginUser(name,password,MainActivity.this);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this,ActivityRegistro.class);

                startActivity(intent);
            }
        });

        ColorAcivity colorAcivity =  new ManagerBD(this).GetColorActivity(ApiManager.LOGIN_ACTIIVTY);
       if(colorAcivity!=null)
       {
           ConstraintLayout ConsLayout = findViewById(R.id.id_activity);
           int color  = colorAcivity.parseColor();
           ConsLayout.setBackgroundColor(color);
       }
    }
}
