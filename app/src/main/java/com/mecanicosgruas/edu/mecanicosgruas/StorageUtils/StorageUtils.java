package com.mecanicosgruas.edu.mecanicosgruas.StorageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by LUNA on 15/05/2018.
 */

public class StorageUtils {
    /*
    * src
    *   -images
    *   -
    *
    * */
    public enum StoragePlace {
        INTERNAL,
        EXTERNAL
    }

    /* Verifica que este "montada" la tarjeta de memoria externa y que se encuentre disponible */
    public static boolean isExternalAvailable() {
        //En gestion de memoria hay tres tipos
        // 1 base de datos sql
        // 2 shared preferences
        // 3 memoria externa
        //***************SegundoParcial**********
        //Obtenemos el estado del almacenamiento externo
        String state = Environment.getExternalStorageState();
        // Regresa true si puede utilizar el almacenamiento
        // false si no se puede utilizar
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean WriteFile(Context context,StoragePlace typeStorage,Bitmap bitmap)
    {
        FileOutputStream fos = null;
        try
        {
            if(typeStorage == StoragePlace.INTERNAL)
            {
                //Escribir un archivo
                // Metodos para escribir y  leer en memeoria interna ****segundo paracial****
                fos = context.openFileOutput("picture_" + new Date().getTime() + ".jpg",Context.MODE_APPEND);
            }
            else
            {

            }
        }  catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }




}
