package com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.IntentService;
import android.app.ProgressDialog;
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
import com.google.gson.JsonObject;
import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentChat;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentCreateService;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentInbox;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentListService;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentService;
import com.mecanicosgruas.edu.mecanicosgruas.model.HorarioClass;
import com.mecanicosgruas.edu.mecanicosgruas.model.Inbox;
import com.mecanicosgruas.edu.mecanicosgruas.model.Message;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LUNA on 15/05/2018.
 */

public class ManagerREST {
   // private static final String URI = "http://movilesapp.servehttp.com";
    private static final String URI = "http://192.168.1.72";
    private static final String ENDPOINT_REGISTER = "/register/user";
    private static final String ENDPOINT_LOGIN = "/login";
    private static final String ENDPOINT_UPDATE_USER  = "/update/user";
    private static final String ENDPOINT_PUBLIC_SERVICE = "/update/service";
    private static final String ENDPOINT_FIND_SERVICE = "/find/service";
    private static final String ENDPOINT_GET_SERVICES = "/get/services";
    private static final String ENDPOINT_FAVORITO = "/like/favorito";
    private static final String ENDPOINT_SEND_MESSAGE = "/message/user";
    private static final String ENDPOINT_GET_USER_MESSAGE = "/message/list_user";
    private static final String ENDPOINT_GET_CHAT_RAW = "/message/chat/user";
    private static final String ENDPOINT_LOGIN_WITH_TWITTER = "/login/twitter";
    private static final String ENDPOINT_GET_SIZE_SERVICES = "/size/services";
    private static final String ENDPOINT_GET_SIZE_INBOX = "/get/notify/inbox";

    public static String getURI() {
        return URI;
    }

    static public void RegisterUser(final User user, final Context context)  {

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
                                    //Register??
                                    if(response.getString("register").equals("1")) {
                                        LoginUser(user.getNickname(),user.getPassword(),context);
                                    }

                                }

                                Log.i("Message Register",response.getString("messageResponse"));
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

                                if(response.getString("Status").equals("200") ||
                                    response.getString("Status").equals("202")) {
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
                                    ApiManager.setUser(user);
                                    new ManagerBD(context).TruncTableUser();
                                    new ManagerBD(context).InsertTableUser(user);

                                    if(response.getString("Status").equals("200")) {
                                        Servicio s = new Servicio();
                                        s.setNickname(response.getString("nickname"));
                                        s.setNombreServicio(response.getString("nameService"));
                                        s.setDescService(response.getString("descService"));
                                        s.setEndpointImageBackground(response.getString("endpointImg"));
                                        s.setCiudad(response.getString("ciudad"));
                                        s.setColonia(response.getString("colonia"));
                                        s.setCalle(response.getString("calle"));
                                        s.setNum(response.getString("num"));
                                        s.setTelefono(response.getString("telefono"));
                                        s.setEndpointImageUser(response.getString("endPointImgUser"));
                                        s.setNumStars(Float.parseFloat(response.getString("countFavoritios")));

                                        List<HorarioClass> listHorario = ApiManager.getFormatListHorario(response, 1);
                                        ApiManager.setServicio(s);
                                        new ManagerBD(context).TruncTableService();
                                        new ManagerBD(context).InsertTableService(s);
                                    }
                                    else {
                                        ApiManager.setServicio(null);
                                        new ManagerBD(context).TruncTableService();
                                    }
                                     ApiManager.StartAcivtyInicio(context);

                                }

                                Log.i("Login User",response.getString("messageResponse"));
                                Toast.makeText(context.getApplicationContext(),response.getString("messageResponse"),Toast.LENGTH_LONG).show();

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
                            ApiManager.setUser(user);
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

    static public void FindService(final Context context, final FragmentCreateService fragment, final FragmentService fragemtnService, final int Mode)
    {
        RequestQueue requestQueue = ConnectionREST.getInstance(context.getApplicationContext())
                .getRequestQueue();

        try
        {
            final String URL = URI + ENDPOINT_FIND_SERVICE;
            JSONObject jsonBody = new JSONObject();

            if(Mode==1)
                jsonBody.put("nickname",ApiManager.getUser().getNickname());
            else
                jsonBody.put("nickname",ApiManager.getService_select().getNickname());

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
                            Servicio s = new Servicio();
                            s.setNickname(response.getString("nickname"));
                            s.setNombreServicio(response.getString("nameService"));
                            s.setDescService(response.getString("descService"));
                            s.setEndpointImageBackground(response.getString("endpointImg"));
                            s.setCiudad(response.getString("ciudad"));
                            s.setColonia(response.getString("colonia"));
                            s.setCalle(response.getString("calle"));
                            s.setNum(response.getString("num"));
                            s.setTelefono(response.getString("telefono"));
                            s.setEndpointImageUser(response.getString("endPointImgUser"));
                            s.setNumStars(Float.parseFloat(response.getString("countFavoritios")));

                            List<HorarioClass> listHorario = ApiManager.getFormatListHorario(response,Mode);

                            s.setListHorario(listHorario);

                            if(Mode==1)
                            {
                                ApiManager.setServicio(s);

                                fragment.updateData();

                            } else if(Mode==2)
                            {
                                ApiManager.setService_select(s);
                                fragemtnService.updateData();
                            }


                        } else if(response.getString("Status").equals("202"))
                        {
                            ApiManager.setServicio(null);
                        }


                        Log.i("FindService",response.getString("messageResponse"));
                        //Toast.makeText(context,response.getString("messageResponse"),Toast.LENGTH_LONG).show();
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
    static public void getService(final Context context, final FragmentListService fragment)
    {
        RequestQueue requestQueue = ConnectionREST.getInstance(context.getApplicationContext())
                .getRequestQueue();

        try {

            String URL = URI + ENDPOINT_GET_SERVICES;

            JSONObject jsonBody= new JSONObject();
            jsonBody.put("none","none");

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
                            List<Servicio> listServices = new ArrayList<>();
                            for(int i = 0; response.has(Integer.toString(i));i++)
                            {
                                JSONObject jsonObject  = response.getJSONObject(Integer.toString(i));
                                listServices.add(
                                        new Servicio(
                                                        jsonObject.getString("nickname"),
                                                        jsonObject.getString("name"),
                                                        jsonObject.getString("ciudad"),
                                                        jsonObject.getString("telefono"),
                                                        Float.parseFloat(jsonObject.getString("countFavoritios")),
                                                        jsonObject.getString("endpointImg")
                                                    )
                                                );
                            }

                          ManagerBD managerBD =  new ManagerBD(fragment.getContext());
                          managerBD.TruncTableTableServiceDisplay();
                          for(int i = 0;i<listServices.size();i++) {
                              managerBD.InsertTableServiceDisplay(listServices.get(i));
                          }
                          fragment.UpdateServices(listServices);
                        }


                        Log.i("getService in Thread",response.getString("messageResponse"));
                        //Toast.makeText(context,response.getString("messageResponse"),Toast.LENGTH_LONG).show();
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

        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    static public void FavoritoService(String nicknameUser,String nicknameService,final Context context,final FragmentService fragment)
    {
        RequestQueue requestQueue = ConnectionREST.getInstance(context.getApplicationContext())
                .getRequestQueue();

        try {

            String URL = URI + ENDPOINT_FAVORITO;

            JSONObject jsonBody= new JSONObject();
            jsonBody.put("nicknameUser",nicknameUser);
            jsonBody.put("nicknameService",nicknameService);

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
                            fragment.updateFavorito(Integer.parseInt(response.getString("countFavorito")));
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

        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    static public void addMessage(String nicknameUser,String nicknameRevecie, String messageString, String base64CodeImg,final Context context)
    {
        RequestQueue requestQueue = ConnectionREST.getInstance(context.getApplicationContext())
                .getRequestQueue();

        try
        {
            String URL = URI + ENDPOINT_SEND_MESSAGE;

            JSONObject jsonBody= new JSONObject();
            jsonBody.put("nicknameParamSend",nicknameUser);
            jsonBody.put("nicknameParamRevecie",nicknameRevecie);
            jsonBody.put("messageParam",messageString);
            jsonBody.put("imgBase64Code",base64CodeImg);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody,
                new Response.Listener<JSONObject>() {
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

        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    static public void get_users_message(String nickname, final Context context, final FragmentInbox fragmentInbox)
    {
        RequestQueue requestQueue = ConnectionREST.getInstance(context.getApplicationContext())
                .getRequestQueue();

        try
        {
            String URL = URI + ENDPOINT_GET_USER_MESSAGE;

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("nickname",nickname);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try
                    {
                        if(response==null)
                            return;

                        if(response.getString("Status").equals("200"))
                        {
                            List<Inbox> listInbox = new ArrayList<>();
                            for(int i = 0; response.has(Integer.toString(i));i++)
                            {
                                JSONObject jsonUser = response.getJSONObject(Integer.toString(i));
                                Inbox inbox = new Inbox();
                                inbox.setNickname(jsonUser.getString("nickname"));
                                inbox.setEndPointImagePerfil(jsonUser.getString("endpointImg"));
                                inbox.setLast_message(jsonUser.getString("last_message"));
                                inbox.setTimeSend(jsonUser.getString("timeSend"));
                                listInbox.add(inbox);
                            }

                            fragmentInbox.updateListUser(listInbox);
                        }

                        Log.i("get_users_message",response.getString("messageResponse"));
                        //Toast.makeText(context,response.getString("messageResponse"),Toast.LENGTH_LONG).show();
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

        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    static public void getMessage_Raw(String nickname, String nicknameSend,int sizeMessage, final Context context, final FragmentChat fragment)
    {
        RequestQueue requestQueue = ConnectionREST.getInstance(context.getApplicationContext())
                .getRequestQueue();

        try
        {

            String URL = URI + ENDPOINT_GET_CHAT_RAW;

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("nicknameUserReceive",nickname);
            jsonBody.put("nicknameUserSend",nicknameSend);
            jsonBody.put("sizeMessage",sizeMessage);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try
                    {
                        int size = 0;
                        if(response==null)
                            return;

                        if(response.getString("Status").equals("200"))
                        {
                            List<Message> listMessage = new ArrayList<>();
                            for(int i = 0; response.has(Integer.toString(i));i++)
                            {
                                JSONObject jsonObject  = response.getJSONObject(Integer.toString(i));
                                Message message  = new Message();

                                message.setNickname(jsonObject.getString("nickname"));
                                message.setEndpointImg(jsonObject.getString("endpointImg"));
                                message.setMessage(jsonObject.getString("message"));
                                message.setTimeSend(jsonObject.getString("time_stamp"));
                                if(ApiManager.getUser().getNickname().equals(message.getNickname()))
                                    message.setMe(true);
                                else
                                    message.setMe(false);

                                listMessage.add(message);

                            }

                            if(response.has("size_Message"))
                            size  = response.getInt("size_Message");

                            fragment.Update_Chat(listMessage);
                        }

                        Log.i("getMessage_Raw",response.getString("messageResponse") + " Size: " + size);

                        //Toast.makeText(context,response.getString("messageResponse") + " Size: " + size,Toast.LENGTH_LONG).show();
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

        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    static public void LoginWithTwitter(String nickname, final Context context) {
        RequestQueue requestQueue = ConnectionREST.getInstance(context.getApplicationContext())
                .getRequestQueue();
        try{
            String URL = URI + ENDPOINT_LOGIN_WITH_TWITTER;
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("nickname",nickname);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (
                    Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response==null)
                            return;
                        if(response.getString("Status").equals("200")) {
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
                            ApiManager.setUser(user);
                            ApiManager.StartAcivtyInicio(context);
                        }

                        Toast.makeText(context,response.getString("messageResponse"),Toast.LENGTH_LONG).show();
                    } catch(JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context.getApplicationContext(),"El Servidor no esta disponible",Toast.LENGTH_LONG).show();
                }
            }
            );
            ConnectionREST.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    static public void get_Size_Service(final FragmentListService fragmentListService) {
        RequestQueue requestQueue = ConnectionREST.getInstance(fragmentListService.getContext())
                .getRequestQueue();
        try {
            String URL = URI + ENDPOINT_GET_SIZE_SERVICES;
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("nickname","");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response==null)
                            return;
                        if(response.getString("Status").equals("200")) {
                            String size = response.getString("count_service");
                            fragmentListService.count_service = Integer.parseInt(size);
                        }

                    } catch(JSONException ex) {
                    ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(fragmentListService.getContext(),"El Servidor no esta disponible",Toast.LENGTH_LONG).show();
                }
            }
            );

            ConnectionREST.getInstance(fragmentListService.getContext()).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    static public void get_SizeInbox(final Context context,String nickname){
        RequestQueue requestQueue = ConnectionREST.getInstance(context)
                .getRequestQueue();
        try {
                String URL = URI + ENDPOINT_GET_SIZE_INBOX;
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("nickname",nickname);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response==null)
                            return;
                        if(response.getString("Status").equals("200")) {
                            String count  = response.getString("count_inbox");
                            ApiManager.count_inbox = Integer.parseInt(count);
                        }

                    } catch(JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,"El Servidor no esta disponible",Toast.LENGTH_LONG).show();
                }
            }
            );

            ConnectionREST.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}