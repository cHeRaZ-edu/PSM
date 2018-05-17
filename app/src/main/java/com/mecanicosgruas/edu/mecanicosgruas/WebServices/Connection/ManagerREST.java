package com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by LUNA on 15/05/2018.
 */

public class ManagerREST {
    private static final String URI = "http://appmecanicogruas.servehttp.com";
    private static final String ENDPOINT_REGISTER = "/register/user";
    private static final String ENDPOINT_LOGIN = "/login";
    private static final String ENDPOINT_UPDATE_USER  = "/update/user";
    private static final  String ENDPOINT_PUBLIC_SERVICE = "/update/service";

    public static String getURI() {
        return URI;
    }

    static public void RegisterUser(User user, final Context context)
    {

        try
        {
            RequestQueue requestQueue = ConnectionREST.getInstance(context.getApplicationContext())
                    .getRequestQueue();

            final String URL = URI + ENDPOINT_REGISTER;

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("nickname",user.getNickname());
            jsonBody.put("name",user.getName());
            jsonBody.put("last_name",user.getLast_name());
            jsonBody.put("email",user.getEmail());
            jsonBody.put("password",user.getPassword());
            jsonBody.put("telefono",user.getTelefono());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getString("Status")!=null)
                                {
                                    Toast.makeText(context.getApplicationContext(),response.getString("messageResponse"),Toast.LENGTH_LONG).show();
                                }

                                Log.i("Response: ", response.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Toast.makeText(context.getApplicationContext(),"El Servidor no esta disponible",Toast.LENGTH_LONG).show();
                        }
                    });

            ConnectionREST.getInstance(context).addToRequestQueue(jsonObjectRequest);

        } catch( JSONException ex)
        {
            ex.printStackTrace();
        }
    }

    static public void LoginUser(String nickname, String password, final Context context)
    {
        try
        {

            RequestQueue requestQueue = ConnectionREST.getInstance(context.getApplicationContext())
                    .getRequestQueue();

            final String URL = URI + ENDPOINT_LOGIN;

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("nickname",nickname);
            jsonBody.put("password",password);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Get  endpoint user
                            try {
                                if(response==null)
                                    return;

                                if(response.getString("Status").equals("200"))
                                {
                                    User user = new User
                                            (
                                                    response.getString("nickname"),
                                                    response.getString("name"),
                                                    response.getString("last_name"),
                                                    response.getString("email"),
                                                    response.getString("phone"),
                                                    response.getString("user_pathImg"),
                                                    response.getString("user_pathPortImg"),
                                                    response.getString("pathFileGPS")
                                            );
                                    Servicio service = new Servicio();
                                    service.setEndpointImageBackground(response.getString("service_pathPortImg"));
                                    ApiManager.setUser(user);
                                    ApiManager.setServicio(service);

                                    ApiManager.StartAcivtyInicio(context);
                                }

                                    Toast.makeText(context,response.getString("messageResponse"),Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context.getApplicationContext(),"El Servidor no esta disponible",Toast.LENGTH_LONG).show();
                        }
                    });




            ConnectionREST.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } catch(JSONException ex)
        {
            ex.printStackTrace();
        }
    }


    static public void UpdateUser(User user,String codeBase64ImgPerfil,String codeBase64ImgBackground, final Context context,final PantallaInicio activity)
    {
        RequestQueue requestQueue = ConnectionREST.getInstance(context.getApplicationContext())
                .getRequestQueue();
        /*
        * json ('nickname','name','last_name','email','telefono',
                  'imgBase64Perfil','imgBase64Background')
        */


        try {

            final String URL = URI + ENDPOINT_UPDATE_USER;

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("nickname",user.getNickname());
            jsonBody.put("name",user.getName());
            jsonBody.put("last_name",user.getLast_name());
            jsonBody.put("email",user.getEmail());
            jsonBody.put("telefono",user.getTelefono());
            jsonBody.put("imgBase64Perfil",codeBase64ImgPerfil);
            jsonBody.put("imgBase64Background",codeBase64ImgBackground);

            JsonObjectRequest jsonObjectRequest  =new JsonObjectRequest(
                    Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // Get  endpoint user
                    try {
                        if(response==null)
                            return;

                        if(response.getString("Status").equals("200"))
                        {
                            User user = new User
                                    (
                                            response.getString("nickname"),
                                            response.getString("name"),
                                            response.getString("last_name"),
                                            response.getString("email"),
                                            response.getString("phone"),
                                            response.getString("user_pathImg"),
                                            response.getString("user_pathPortImg"),
                                            response.getString("pathFileGPS")
                                    );
                            Servicio service = new Servicio();
                            service.setEndpointImagePort(response.getString("service_pathImg"));
                            service.setEndpointImageBackground(response.getString("service_pathPortImg"));
                            ApiManager.setUser(user);
                            ApiManager.setServicio(service);
                            activity.UpdateImage();
                        }

                        Toast.makeText(context,response.getString("messageResponse"),Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context.getApplicationContext(),"El Servidor no esta disponible",Toast.LENGTH_LONG).show();
                }
            });

            ConnectionREST.getInstance(context).addToRequestQueue(jsonObjectRequest);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    static public void PublicService(Servicio service,JSONObject jsonBody,String base64codeImgPort,final Context context)
    {
        RequestQueue requestQueue = ConnectionREST.getInstance(context.getApplicationContext())
                .getRequestQueue();

        /*
        * @param json ('nickname','name','descService','ciudad','colonia',
*               'calle','numero','imgBase64Service','imgBase64BackgroundService','lun'...'dom')
        * */

        try
        {
            final String URL = URI + ENDPOINT_PUBLIC_SERVICE;

            jsonBody.put("nickname",ApiManager.getUser().getNickname());
            jsonBody.put("name",service.getNombreServicio());
            jsonBody.put("descService",service.getDescService());
            jsonBody.put("ciudad",service.getCiudad());
            jsonBody.put("colonia",service.getColonia());
            jsonBody.put("calle",service.getCalle());
            jsonBody.put("numero",service.getNum());
            jsonBody.put("imgBase64BackgroundService",base64codeImgPort);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try
                    {
                        if(response==null)
                            return;

                        if(response.getString("Status").equals("200"))
                        {

                        }

                        Toast.makeText(context,response.getString("messageResponse"),Toast.LENGTH_LONG).show();
                    } catch(JSONException ex)
                    {
                        ex.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context.getApplicationContext(),"El Servidor no esta disponible",Toast.LENGTH_LONG).show();
                }
            });

            ConnectionREST.getInstance(context).addToRequestQueue(jsonObjectRequest);


        }catch (JSONException e) {
            e.printStackTrace();
        }
    }


}