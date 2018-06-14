package com.mecanicosgruas.edu.mecanicosgruas.StorageUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import com.google.android.gms.maps.model.LatLng;
import com.mecanicosgruas.edu.mecanicosgruas.R;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LUNA on 15/05/2018.
 */

public class StorageUtils {
    static SharedPreferences sharedPreferences;
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

    public static final String LAT ="latitud";
    public static final String LNG ="logintud";


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

    public static void InizilateSharedPrefernces(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.SharedPreferencesFile),context.MODE_PRIVATE);
    }

    public static void SetColorAcivity(String key, String value){
        sharedPreferences.edit().putString(key,value).apply();
    }
    public static int getColor(String key){
        String color = sharedPreferences.getString(key,"");
        if(color.equals(""))
            return 0;
        return Color.parseColor(color);
    }
    public static  void SetLatLng(LatLng latLng){
        sharedPreferences.edit().putFloat(LAT,(float) latLng.latitude).apply();
        sharedPreferences.edit().putFloat(LNG,(float) latLng.longitude).apply();
    }
    public static LatLng GetLatLng() {
        float lat = sharedPreferences.getFloat(LAT,0.0f);
        float lng = sharedPreferences.getFloat(LNG,0.0f);

        return new LatLng(lat,lng);
    }
    public static void SetUbicacion(List<String> list_geo){
        sharedPreferences.edit().putString("ciudad",list_geo.get(1));
        sharedPreferences.edit().putString("colonia",list_geo.get(2));
        sharedPreferences.edit().putString("calle",list_geo.get(3));
        sharedPreferences.edit().putString("numero",list_geo.get(4));
    }
    public static List<String> GetUbicacion(){
        List<String> list_geo = new ArrayList<>();
        list_geo.add(sharedPreferences.getString("ciudad",""));
        list_geo.add(sharedPreferences.getString("colonia",""));
        list_geo.add(sharedPreferences.getString("calle",""));
        list_geo.add(sharedPreferences.getString("numero",""));

        return  list_geo;
    }

}
