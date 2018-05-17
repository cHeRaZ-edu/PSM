package com.mecanicosgruas.edu.mecanicosgruas.ApiManager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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
    public static  final int STORAGE_IMAGE = 200;
    public static final int PHOTO_SHOT = 300;
    public static final String SPLIT_CODE = "<SPACE>";

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


}
