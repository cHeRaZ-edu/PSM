package com.mecanicosgruas.edu.mecanicosgruas.ApiManager;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.ParcelUuid;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.HorarioClass;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

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
    public static String AcivitySelectColor="";
    public static final int CODE_RESULT_CHANGE_COLOR= 333;

    public static final String LOGIN_ACTIIVTY = "login";
    public static final String REGISTER_ACTIIVT = "register";
    public static final String INICIO_FRAGMENT = "inicio";
    public static final String SERVICE_DISPLAY_FRAGMENT = "servicio";
    public static final String SERVICE_EDIT_FRGAMENT = "CreateService";
    public static final String SETTINGS_FRGAMENT = "Settings";
    public static final String INBOX_FRAGMENT = "Inbox";
    public static final String CHAT_FRAGMENT = "chat";
    public static final String CHAT_SERVICE = "service_chat";
    public static final String CHANGE_COLOR_FRGAMENT  = "color_fragment";
    public static LocationManager locationManager;
    public static String CHANNEL_ID = "channel";
    public static String CHANNEL_ID_MESSAGE = "channel2";
    public static int notificationId = 100;
    public static int notificationId_MESSAGE = 200;

    public static int count_inbox = 0;
    public static int old_count_inbox = 0;
    public static final float DARK_MODE = 3.0f;
    public  static boolean ENABLED_DARK_MODE = false;
    public static int COLOR_DARK  = Color.rgb(90,90,90);
    //Gestures tag
    public static final String GESTURE_2 = "gesture_2";
    public static final String GESTURE_7 = "gesture_7";
    public static final String GESTURE_8 = "gesture_8";
    public static final String GESTURE_CIRCULO = "gesture_o";
    public static final String GESTURE_S = "gesture_s";
    public static final String GESTURE_CUADRADO = "gesture_square";
    public static final String GESTURE_TRIANGULO= "gesture_triangulo";

    public static boolean runApplicationInbox = false;
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
    public static void StartAcivtyInicio(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), PantallaInicio.class);
        context.startActivity(intent);
    }
    public static void ImageSelect(final PantallaInicio activity) {
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
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        activity.Resumed();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
    public static void InizialiteProgresdialog(Context context) {
        progressDialog = null;
        progressDialog  = new ProgressDialog(context.getApplicationContext());
        progressDialog.setMessage("Fetching The File....");
        progressDialog.show();
    }
    public static void finishProgresDialog() {
        if(progressDialog!=null)
        progressDialog.dismiss();
    }
    public static boolean isInternetConnection(Context context) {
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
    public static void SetupLoginWithTwitter(String key, String secret, Context context) {
        TwitterAuthConfig twitterAuthConfig = new TwitterAuthConfig(key,secret);
        Fabric.with(context,new Twitter(twitterAuthConfig));
    }
    public static boolean isLoginWithTiwtter() {
        TwitterSession twitterSession = Twitter.getSessionManager().getActiveSession();
        if(twitterSession==null)
            return false;
        return true;
    }
    public static void LogOutTwitter() {
        TwitterSession twitterSession1 = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if(twitterSession1!=null) {
            CookieManager cookieManager = CookieManager.getInstance();
            if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP)
                cookieManager.removeAllCookies(null);
            else
                cookieManager.removeAllCookie();
            Twitter.getSessionManager().clearActiveSession();
            Twitter.logOut();
        }
    }
    public static boolean checkPersmios(Activity activity) {
        // Apartir de Android 6.0+ necesitamos pedir el permiso de ubicacion
        // directamente en tiempo de ejecucion de la app
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Si no tenemos permiso para la ubicacion
            // Solicitamos permiso
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        return false;
        } else {
            // Ya se han concedido los permisos anteriormente
            return true;
        }
    }
    public static LatLng getCurrentLocation(Context context) {
        LatLng latLng = new LatLng(0,0);
        if(locationManager!=null){
            List<String> providers = locationManager.getProviders(true);
            //Buscar el provedor mas precisio
            Location bestLocation = null;
            for(String provider : providers) {
                //Retorna la ultima ubicacion obtenida de culaquier aplicacion
                Location l = locationManager.getLastKnownLocation(provider);

                if(l == null) {
                    continue;
                }

                if(bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            }

            if(bestLocation == null) {
                Toast.makeText(context,"No se pudo obtener tu ubicacion",Toast.LENGTH_LONG).show();
                return null;
            }

            //** Nota existe un servicio muy presicio
            // android service location
            return new LatLng(bestLocation.getLatitude(),bestLocation.getLongitude());
        }
        return latLng;
    }
    public static List<String> getAddress(Activity activity, LatLng latLng) throws IOException {
        List<String> addressLocation = new ArrayList<>();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(activity, Locale.getDefault());

        addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String colonia = addresses.get(0).getSubLocality();
        String calle = addresses.get(0).getThoroughfare();
        String num = addresses.get(0).getSubThoroughfare();
        String postalCode = addresses.get(0).getPostalCode();



        addressLocation.add(address);
        addressLocation.add(city);
        addressLocation.add(colonia);
        addressLocation.add(calle);
        addressLocation.add(num);
        addressLocation.add(postalCode);

        return addressLocation;
    }
    public static List<HorarioClass> getFormatListHorario(JSONObject response, int Mode){
        List<HorarioClass> listHorario= new ArrayList<>();
        try {
            if(response.has("lun"))
                listHorario.add(new HorarioClass("Lunes",response.getString("lun")));
            else
            {
                if(Mode!=2)
                    listHorario.add(new HorarioClass("Lunes"));
            }


            if(response.has("mar"))
                listHorario.add(new HorarioClass("Martes",response.getString("mar")));
            else
            {
                if(Mode!=2)
                    listHorario.add(new HorarioClass("Martes"));
            }

            if(response.has("mie"))
                listHorario.add(new HorarioClass("Miercoles",response.getString("mie")));
            else
            {
                if(Mode!=2)
                    listHorario.add(new HorarioClass("Miercoles"));
            }


            if(response.has("jue"))
                listHorario.add(new HorarioClass("Jueves",response.getString("jue")));
            else
            {
                if(Mode!=2)
                    listHorario.add(new HorarioClass("Jueves"));
            }


            if(response.has("vie"))
                listHorario.add(new HorarioClass("Viernes",response.getString("vie")));
            else
            {
                if(Mode!=2)
                    listHorario.add(new HorarioClass("Viernes"));
            }

            if(response.has("sab"))

                    listHorario.add(new HorarioClass("Sabado",response.getString("sab")));

            else
            {
                if(Mode!=2)
                    listHorario.add(new HorarioClass("Sabado"));
            }


            if(response.has("dom"))
                listHorario.add(new HorarioClass("Domingo",response.getString("dom")));
            else
            {
                if(Mode!=2)
                    listHorario.add(new HorarioClass("Domingo"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listHorario;
    }
    public static void ShowNotify_Geolocalizacion(Context context,String title, String desc) {
        //No soprotado para oreo en adelante por ahora
        //debes de crear un canal para las versiones mencinadas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            return;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                .setContentTitle(title)
                .setContentText(desc)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, mBuilder.build());
    }
    public static void ShowNotify_Message(Context context,String title, String desc) {
        //No soprotado para oreo en adelante por ahora
        //debes de crear un canal para las versiones mencinadas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            return;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID_MESSAGE)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle(title)
                .setContentText(desc)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId_MESSAGE, mBuilder.build());
    }
    public static Bitmap rezieBitmapApplicaition(Bitmap bitmap){
        if(bitmap == null)
            return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        for(int i =1;width>400 || height>400;i++){
            if(width>400)
            width = bitmap.getWidth()/i;
            if(height>400)
            height = bitmap.getHeight()/i;
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }
}
