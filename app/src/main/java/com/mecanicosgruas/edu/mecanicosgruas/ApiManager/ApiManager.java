package com.mecanicosgruas.edu.mecanicosgruas.ApiManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.ParcelUuid;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;

import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;

/**
 * Created by LUNA on 15/05/2018.
 */

public class ApiManager {
    private static User user;
    private static Servicio servicio;
    private static  Servicio service_select;
    private static User user_select;
    public static  final int STORAGE_IMAGE = 200;
    public static final int PHOTO_SHOT = 300;
    public static final String SPLIT_CODE = "<SPACE>";
    private static ProgressDialog progressDialog;
    public static final int COUNT_FAVORITO = 20;
    public static int COUNT_STARS  = 5;

    static final String LOGIN_ACTIIVTY = "login";
    static final String REGISTER_ACTIIVT = "register";
    static final String INICIO_FRAGMENT = "inicio";
    static final String SERVICE_DISPLAY_FRAGMENT = "servicio";
    static final String SERVICE_EDIT_FRGAMENT = "CreateService";
    static final String SETTINGS_FRGAMENT = "Settings";
    static final String INBOX_FRAGMENT = "Inbox";
    static final String CHAT_FRAGMENT = "chat";
    static final String CHAT_SERVICE = "service_chat";
    static final String CHANGE_COLOR_FRGAMENT  = "color_fragment";

    public static User getUser_select() {
        return user_select;
    }

    public static void setUser_select(User user_select) {
        ApiManager.user_select = user_select;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        ApiManager.user = user;
    }

    public static Servicio getServicio() {
        return servicio;
    }

    public static void setServicio(Servicio servicio) {
        ApiManager.servicio = servicio;
    }

    public static Servicio getService_select() {
        return service_select;
    }

    public static void setService_select(Servicio service_select) {
        ApiManager.service_select = service_select;
    }

    public static void StartAcivtyInicio(Context context)
    {
        Intent intent = new Intent(context.getApplicationContext(), PantallaInicio.class);
        context.startActivity(intent);
    }

    public static void ImageSelect(final Activity activity)
    {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activity);
        }
        builder.setTitle("Medio")
                .setMessage("Elige la opcion para la imagen")
                .setPositiveButton("Foto", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        activity.startActivityForResult(intent, ApiManager.PHOTO_SHOT);
                    }
                })
                .setNegativeButton("SD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                        i.setType("image/*");
                        i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                        activity.startActivityForResult(Intent.createChooser(i,"Selecciona una foto"),ApiManager.STORAGE_IMAGE);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }


   public static void InizialiteProgresdialog(Context context)
    {
        progressDialog = null;
        progressDialog  = new ProgressDialog(context.getApplicationContext());
        progressDialog.setMessage("Fetching The File....");
        progressDialog.show();
    }

   public static void finishProgresDialog()
    {
        if(progressDialog!=null)
        progressDialog.dismiss();
    }

    public static boolean isInternetConnection(Context context)
    {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        return connected;
    }





}
