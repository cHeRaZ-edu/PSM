package com.mecanicosgruas.edu.mecanicosgruas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;

// Inicio de sesion
public class MainActivity extends AppCompatActivity {

    EditText editTxtUser;
    EditText editTxtPassword;
    Button btnLogin;
    Button btnRegister;
    Button btnLoginFace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTxtUser = (EditText)findViewById(R.id.editTxtUser);
        editTxtPassword = (EditText)findViewById(R.id.editTxtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnLoginFace = (Button)findViewById(R.id.btnLoginFacebook);

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
    }
}
