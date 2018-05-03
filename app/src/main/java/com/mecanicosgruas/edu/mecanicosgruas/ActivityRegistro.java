package com.mecanicosgruas.edu.mecanicosgruas;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    String messageResult;
    String messageJson;

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
                    messageJson = serializeToJson(usuario);//Usuario en Json
                    //Registrar desde el servidor
                    SegundoPlano tarea = new SegundoPlano();
                    tarea.execute();
                }

            }
        });

    }

    // Serialize a single object.
    public String serializeToJson(User myClass) {
        Gson gson = new Gson();
        String j = gson.toJson(myClass);
        return j;
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


        //Validar nombre y correo electronico en el servidor

        return true;
    }
    private String RegisterRequest(String messageJson)
    {
        String result = "";
        /*
        //Primer paso definir los parametros del soap
        String NAMESPACE = "http://www.example.org/register/";
        String METHOD_NAME = "registeruser";
        String SOAP_ACTION = "http://www.example.org/register/registeruser";
        String url = "http://appmecanicogruas.servehttp.com/application/php/registerSoap.php?wsdl";
        */
        try
        {
            //Endpoint
            URL endpoint = new URL("http://appmecanicogruas.servehttp.com/register/user");
            // Create connection
            HttpURLConnection ConnectEndPoint = (HttpURLConnection)endpoint.openConnection();
            //Type method http
            ConnectEndPoint.setRequestMethod("POST");
            //Voy a mandar un mensaje
            ConnectEndPoint.setDoOutput(true);
            ConnectEndPoint.getOutputStream().write(messageJson.getBytes());
            //Get Response
            if(ConnectEndPoint.getResponseCode() == 200)//Is OK
            {
                //Body
                BufferedReader br = new BufferedReader(new InputStreamReader(ConnectEndPoint.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                result =  sb.toString();
            }
            else
            {
                //Error 404,400,500... etc
                result = "El servidor no esta disponible";
            }

            ConnectEndPoint.disconnect();

        }
        catch(Exception ex)
        {
            result = "Error: " + ex.getMessage();
        }

        return result;
    }
    private class SegundoPlano extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),messageResult,Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            messageResult = RegisterRequest(messageJson);
            return null;
        }
    }
}
