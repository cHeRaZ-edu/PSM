package com.mecanicosgruas.edu.mecanicosgruas;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.StorageUtils.StorageUtils;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.model.ColorAcivity;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

// Inicio de sesion
public class MainActivity extends AppCompatActivity {

    EditText editTxtUser;
    EditText editTxtPassword;
    Button btnLogin;
    Button btnRegister;
    TwitterLoginButton btnLoginWithTwitter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        //Setup key and secrcet
        ApiManager.SetupLoginWithTwitter(
                context.getString(R.string.Twitter_Key),
                context.getString(R.string.Twitter_Secret),
                context);
        setContentView(R.layout.activity_main);

        editTxtUser = (EditText)findViewById(R.id.editTxtUser);
        editTxtPassword = (EditText)findViewById(R.id.editTxtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnLoginWithTwitter = (TwitterLoginButton)findViewById(R.id.twitterLoginButton);

        ButtonEvents();


        //ColorAcivity colorAcivity =  new ManagerBD(this).GetColorActivity(ApiManager.LOGIN_ACTIIVTY);
       StorageUtils.InizilateSharedPrefernces(this);
       int color = StorageUtils.getColor(getString(R.string.LoginKeyColor));
       if(color!=0)
       {
           ConstraintLayout ConsLayout = findViewById(R.id.id_activity);
           ConsLayout.setBackgroundColor(color);
       }
       /*
        Date date =  Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

      Date date1 = new GregorianCalendar(2018, 000, 11).getTime();
      Date  date2 = new GregorianCalendar(2018, 006, 10).getTime();
      if(date1.after(date2)){
          Toast.makeText(this,"Actualizar datos mysql",Toast.LENGTH_LONG).show();
      }
      else if(date1.before(date2)){
          Toast.makeText(this,"Actualizar datos sqlite",Toast.LENGTH_LONG).show();
      }
      */
    }

    private void ButtonEvents()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ApiManager.isInternetConnection(getApplicationContext()))
                    Login();
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


        btnLoginWithTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //... Web services
                    //Checar si existe en la bases de datos
                        //Registrar si no existe
                //Iniciar sesion
                String username = result.data.getUserName();
                String id = result.data.getUserId()+ "";

                //Validar desde el servidor, iniciar sesion
                if(ApiManager.isInternetConnection(getApplicationContext()))
                    ManagerREST.LoginWithTwitter(username,MainActivity.this);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(context, "El usuario o la contraseña no son correctos", Toast.LENGTH_SHORT).show();
            }
        });

        Loggedin();
    }

    private void Login()
    {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_extra,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //event item actionbar
        switch (item.getItemId())
        {
            case R.id.extra_changeColor:
                //Toast.makeText(this,"Cambiar color",Toast.LENGTH_LONG).show();
                ApiManager.AcivitySelectColor = getString(R.string.LoginKeyColor);
                Intent intent = new Intent(MainActivity.this,ChangeColorAcivity.class);
                startActivityForResult(intent,ApiManager.CODE_RESULT_CHANGE_COLOR);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        btnLoginWithTwitter.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == ApiManager.CODE_RESULT_CHANGE_COLOR) {
            StorageUtils.InizilateSharedPrefernces(this);
            int color = StorageUtils.getColor(getString(R.string.LoginKeyColor));
            if(color!=0) {
                ConstraintLayout ConsLayout = findViewById(R.id.id_activity);
                ConsLayout.setBackgroundColor(color);
            }
        }

    }

    private void Loggedin() {
        if(ApiManager.isLoginWithTiwtter()) {
            TwitterSession twitterSession = Twitter.getSessionManager().getActiveSession();
            String username = twitterSession.getUserName();
            if(ApiManager.isInternetConnection(getApplicationContext()))
                ManagerREST.LoginWithTwitter(username,MainActivity.this);
            //Toast.makeText(context,"Login Twitter iniciado",Toast.LENGTH_LONG).show();
        }
        else {
            User user = new ManagerBD(this).SelectTableUser();
            if(user!=null) {
                Servicio servicio = new ManagerBD(this).SelectTableServicio();
                ApiManager.setUser(user);
                ApiManager.setServicio(servicio);
                Intent intent = new Intent(this,PantallaInicio.class);
                startActivity(intent);
            }
        }
    }
}
