package com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection;

import android.content.Context;
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
    private final String ENDPOINT_LOGIN = "/login";

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
            jsonBody.put("last_name",user.getApellido());
            jsonBody.put("email",user.getEmail());
            jsonBody.put("password",user.getPassword());
            jsonBody.put("telefono",user.getTelefono());

            final String requestBody = jsonBody.toString();
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

}


            /*

            //SEGUNDA OPCION soloamente con String
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        Log.i("VOLLEY Response", responseString);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            */