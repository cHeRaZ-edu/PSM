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
import com.mecanicosgruas.edu.mecanicosgruas.model.User;

import org.json.JSONObject;

import java.text.Normalizer;

public class ActivityRegistro extends AppCompatActivity {

    Button btnCrearCuenta;
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
                    User usuario = new User(nameUser,apellidoUser,email.getText().toString(),password.getText().toString(),telefono.getText().toString());
                    messageJson = serializeToJson(usuario);
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
        //Primer paso definir los parametros del soap
        String NAMESPACE = "http://www.example.org/register/";
        String METHOD_NAME = "registeruser";
        String SOAP_ACTION = "http://www.example.org/register/registeruser";
        String url = "http://appmecanicogruas.servehttp.com/application/php/registerSoap.php?wsdl";

        try
        {
            /*
            //Inicizalizar request
            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
            //Agregar Propoiedad del mensaje
            request.addProperty("object",messageJson);
            //Inicizalizar envolpe
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            //Inicializar Transporte http
            HttpTransportSE httpMessage = new HttpTransportSE(url);
            httpMessage.call(SOAP_ACTION,envelope);
            //Obtener respuesta del servidor
            SoapPrimitive resultSoap = (SoapPrimitive)envelope.getResponse();
            result = (String)resultSoap.toString();
            */
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
