package com.mecanicosgruas.edu.mecanicosgruas;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.IntentService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.ReadPath.ReadPathUtil;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.ServiceUtils.ServiceUtils;
import com.mecanicosgruas.edu.mecanicosgruas.StorageUtils.StorageUtils;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentCreateService;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentInbox;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentListService;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentService;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentSettings;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class PantallaInicio extends AppCompatActivity {


    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String fragmentTag = "";
    public CircleImageView imagViewPerfil;
    public ImageView imgViewBackground;
    private TextView txtViewNickname;
    private TextView txtViewEmail;
    public String fragmentSharedPrefernces;
    Context context;
    Intent intentService;
    DataReceivedListener listener;
    //Gestures
    GestureOverlayView m_gestureOverlayView;
    //Objeto el cual tendra nuestras gesturas que creemos
    // nosotros mismos con la app "Gesture Builder"
    GestureLibrary m_gestureLibrary;
    //Sensor Manager
    SensorManager m_sensorManager;
    Sensor m_sensor;
    private boolean enabled = false;
    private boolean enabled_sensor = false;

    public void setDataReceivedListener(DataReceivedListener listener) {
        this.listener = listener;
    }

    public interface DataReceivedListener {
        void onReceived(int requestCode, int resultCode, Intent data);

        void onShutdown();

        void OnResumed();
        void OnChangeDarkMode();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
        context = getApplicationContext();
        //get view
        toolbar = (Toolbar) findViewById(R.id.toolbarIniId);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_Inicio);
        navigationView = (NavigationView) findViewById(R.id.nav_panel);
        View header = navigationView.getHeaderView(0);
        imagViewPerfil = (CircleImageView) header.findViewById(R.id.imgViewUserPanel);
        imgViewBackground = (ImageView) header.findViewById(R.id.imgViewBackgroundUserPanel);
        txtViewNickname = (TextView) header.findViewById(R.id.textViewNickname);
        txtViewEmail = (TextView) header.findViewById(R.id.textViewEmail);
        m_gestureOverlayView = (GestureOverlayView) findViewById(R.id.gesture_overlay);

        m_gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);

        // Si no se pudo cargar nuestra libreria (coleccion) de gesturas
        if (!m_gestureLibrary.load()) {
            Quit();
        }




        //setup hardcore toolbar
        int idColor = Resources.getSystem().getColor(android.R.color.background_light);
        toolbar.setTitle("Inicio");
        toolbar.setTitleTextColor(idColor);
        toolbar.setNavigationIcon(R.drawable.ic_action_menu_holo_dark);

        //Events NavigationBarSide
        NavEventMenuItemView();

        //Use my toolbar as actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (fragmentTag.equals(""))
            changeFragment(new FragmentListService(), "inicio");

        if (ApiManager.getUser() != null) {
            txtViewNickname.setText(ApiManager.getUser().getNickname());
            txtViewEmail.setText(ApiManager.getUser().getEmail());
            UpdateImage();
            //... active background service

            intentService = new Intent(this, ServiceUtils.class);
            startService(intentService);
        }

        InizalizarSensor();
        EventGesture();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_inicio, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //event item actionbar
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                return true;
            //case R.id.app_bar_search:
              //  Toast.makeText(getApplicationContext(), "Call search", Toast.LENGTH_LONG).show();
               // return true;
            case R.id.extra_changeColor:
                //Toast.makeText(this,"Cambiar color",Toast.LENGTH_LONG).show();
                ApiManager.AcivitySelectColor = fragmentSharedPrefernces;
                Intent intent = new Intent(PantallaInicio.this, ChangeColorAcivity.class);
                startActivityForResult(intent, ApiManager.CODE_RESULT_CHANGE_COLOR);
                return true;
            case R.id.extra_enabled:
                enabled = !enabled;
                if (!enabled)
                    item.setTitle(R.string.enabled_ges);
                else
                    item.setTitle(R.string.disable_ges);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    //Cambiar de fragment
    private void NavEventMenuItemView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean checkBoton = false;
                switch (item.getItemId()) {
                    case R.id.nav_inicio:
                        //call fragment inicio
                        changeFragment(new FragmentListService(), "inicio");
                        checkBoton = true;
                        break;
                    case R.id.nav_service:
                        //call fragement service manager
                        changeFragment(new FragmentCreateService(), "CreateService");
                        getSupportActionBar().setTitle("Servicio");
                        break;
                    case R.id.nav_inbox:
                        //call fragement inbox
                        changeFragment(new FragmentInbox(), "Inbox");
                        checkBoton = true;
                        break;
                    case R.id.nav_setting:
                        //call fragemnt setting
                        changeFragment(new FragmentSettings(), "Settings");
                        checkBoton = true;
                        break;
                    case R.id.nav_logout:
                        //saving perfil form web server
                        //close activity
                        LogOut();

                        break;
                    default:
                        break;
                }
                //change title actionbar
                if (checkBoton) {
                    getSupportActionBar().setTitle(item.getTitle());
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
        return;
    }
    @Override
    public void onBackPressed() {
        // Si el drawerLayout esta abierto y se presiona back
        //cerrar drawerLayout
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            switch (fragmentTag) {
                case "service_chat":
                    changeFragment(new FragmentService(), "CreateService");
                    getSupportActionBar().setTitle("Servicio");
                    break;
                case "chat":
                    changeFragment(new FragmentInbox(), "Inbox");
                    getSupportActionBar().setTitle("Inbox");
                    break;
                case "Inbox":
                case "CreateService":
                case "Settings":
                case "servicio":
                    changeFragment(new FragmentListService(), "inicio");
                    getSupportActionBar().setTitle("Inicio");
                    break;
                case "color_fragment":
                    changeFragment(new FragmentListService(), "Settings");
                    getSupportActionBar().setTitle("Inicio");
                    break;
                case "inicio":
                    //super.onBackPressed();
                    LogOut();
                    break;
            }
            //Cuano el drawerLayout esta cerrado
            //cerrar la activity

        }
    }
    public void changeFragment(Fragment newFragment, String tag) {
        FragmentManager fragmentManager = getFragmentManager();

        //Operaciones de agregar, remplazar y eliminar

        // Administra los frgamentos de un activity
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        //Buscar si ya existe el mismo frgamento "abierto"
        android.support.v4.app.Fragment fragmentoActual = fm.findFragmentByTag(tag);
        if (fragmentoActual != null && fragmentoActual.isVisible()) {
            Toast.makeText(getApplicationContext(), "Ya se esta mostrando", Toast.LENGTH_LONG).show();
            return;
        }
        if (listener != null)
            listener.onShutdown();
        // realiza las "transicciones" de un fragmento
        // Agregar, remplazar y eliminar
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fragment_base, newFragment, tag);

        ft.commit();//aplicar cambios
        fragmentTag = tag;
    }
    public void changeFragment(Fragment newFragment, String tag, String titleActionBar) {


        FragmentManager fragmentManager = getFragmentManager();

        //Operaciones de agregar, remplazar y eliminar

        // Administra los frgamentos de un activity
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        //Buscar si ya existe el mismo frgamento "abierto"
        android.support.v4.app.Fragment fragmentoActual = fm.findFragmentByTag(tag);
        if (fragmentoActual != null && fragmentoActual.isVisible()) {
            Toast.makeText(getApplicationContext(), "Ya se esta mostrando", Toast.LENGTH_LONG).show();
            return;
        }
        if (listener != null)
            listener.onShutdown();
        // realiza las "transicciones" de un fragmento
        // Agregar, remplazar y eliminar
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fragment_base, newFragment, tag);

        ft.commit();//aplicar cambios
        fragmentTag = tag;
        getSupportActionBar().setTitle(titleActionBar);
    }
    void LogOut() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Salir")
                .setMessage("Salir de mecanicos y Gruas")
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Quit();
                    }
                })
                .setNegativeButton("Quedarse", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
    public void UpdateImage() {
        if (ApiManager.getUser().getEndPointImagePerfil() != null) {
            Picasso.with(PantallaInicio.this)
                    .load(ManagerREST.getURI() + ApiManager.getUser().getEndPointImagePerfil())
                    .placeholder(R.drawable.user)
                    .into(imagViewPerfil);
        }

        if (ApiManager.getUser().getEndPointImageBackground() != null) {
            Picasso.with(PantallaInicio.this)
                    .load(ManagerREST.getURI() + ApiManager.getUser().getEndPointImageBackground())
                    .placeholder(R.drawable.background)
                    .into(imgViewBackground);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (listener != null) {
            listener.onReceived(requestCode, resultCode, data);
        }
        /*
        if (resultCode == RESULT_OK && PHOTO_SHOT == requestCode)
        {
            // Guardamos el thumbnail de la imagen en un archivo con el siguiente nombre
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

           // mImageView.setImageBitmap(bitmap);
        } else if(resultCode == RESULT_OK && STORAGE_IMAGE == requestCode)
        {
            Uri uri = data.getData();
            String pathImage = ReadPathUtil.getRealPathFromURI_API19(PantallaInicio.this,uri);

            Log.i("Path Sotrage",pathImage);

        }
        */
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //ApiManager.setUser(null);
    }
    @Override
    protected void onResume() {
        super.onResume();
        m_sensorManager.registerListener(sensorEventListener,
                m_sensor, SensorManager.SENSOR_DELAY_NORMAL);
        UpdateImage();
        if (listener != null)
            listener.OnResumed();
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();

    }
    @Override
    protected void onStop() {
        super.onStop();
        if (listener != null)
            listener.onShutdown();
            //DesactivarSensor();
    }
    public void Resumed() {
        if (listener != null)
            listener.OnResumed();
    }

    private void Quit() {
        // continue with delete
        //DesactivarSensor();
        if (listener != null)
            listener.onShutdown();
        ApiManager.LogOutTwitter();
        new ManagerBD(context).TruncTableUser();
        new ManagerBD(context).TruncTableService();
        if (intentService != null)
            stopService(intentService);
        finish();
    }
    private void EventGesture() {

        m_gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                if (!enabled)
                    return;
                List<Prediction> predictionList = m_gestureLibrary.recognize(gesture);

                if (predictionList.size() > 0) {
                    //gesture
                    Prediction prediction = predictionList.get(0);

                    if (prediction.score > 1.0) {//Detecta la figura aunque este deforme

                        String name = prediction.name;

                        switch (name) {
                            case ApiManager.GESTURE_2:
                                changeFragment(new FragmentSettings(), "Settings");
                                getSupportActionBar().setTitle("Configurar");
                                //Toast.makeText(context,ApiManager.GESTURE_2, Toast.LENGTH_SHORT).show();
                                break;
                            case ApiManager.GESTURE_7:
                                changeFragment(new FragmentInbox(), "Inbox");
                                getSupportActionBar().setTitle("Inbox");
                                //Toast.makeText(context,ApiManager.GESTURE_7, Toast.LENGTH_SHORT).show();
                                break;
                            case ApiManager.GESTURE_8:
                                changeFragment(new FragmentCreateService(), "CreateService");
                                getSupportActionBar().setTitle("Servicio");
                                //Toast.makeText(context,ApiManager.GESTURE_8, Toast.LENGTH_SHORT).show();
                                break;
                            case ApiManager.GESTURE_CIRCULO:
                                //Toast.makeText(context,ApiManager.GESTURE_CIRCULO, Toast.LENGTH_SHORT).show();
                                break;
                            case ApiManager.GESTURE_S:
                                changeFragment(new FragmentListService(), "inicio");
                                getSupportActionBar().setTitle("Inicio");
                                //Toast.makeText(context,ApiManager.GESTURE_S, Toast.LENGTH_SHORT).show();
                                break;
                            case ApiManager.GESTURE_CUADRADO:
                                //Toast.makeText(context,ApiManager.GESTURE_CUADRADO, Toast.LENGTH_SHORT).show();
                                break;
                            case ApiManager.GESTURE_TRIANGULO:
                                //Toast.makeText(context,ApiManager.GESTURE_TRIANGULO, Toast.LENGTH_SHORT).show();
                                break;

                            default:
                        }


                    }
                }
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        m_sensorManager.unregisterListener(sensorEventListener);
    }
    private void InizalizarSensor(){
        // Obtenemos el servicio para utilizar el sensor
        m_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Sensor de luz: Solo devuelve un valor que dependera de la cantidad de luz que se obtenga
        m_sensor = m_sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }
    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float luz = event.values[0];
            ChangeModeDark(luz);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

     private void ChangeModeDark(float luz) {
         if(luz<ApiManager.DARK_MODE)
             ApiManager.ENABLED_DARK_MODE = true;
         else
             ApiManager.ENABLED_DARK_MODE = false;

         if(listener!=null)
             listener.OnChangeDarkMode();

         if(ApiManager.ENABLED_DARK_MODE)
             Mode_Dark_Menu_Side();
         else
             Mode_Normal_Menu_Side();



         Log.i("Sensor luz",Float.toString(luz));
     }
    private void Mode_Dark_Menu_Side() {
        navigationView.setBackgroundColor(ApiManager.COLOR_DARK);

    }
    private void Mode_Normal_Menu_Side() {
        navigationView.setBackgroundColor(Color.WHITE);

        MenuItem menuInicio = navigationView.findViewById(R.id.nav_inicio);
        MenuItem menuInbox = navigationView.findViewById(R.id.nav_inbox);
        MenuItem menuServicio = navigationView.findViewById(R.id.nav_service);
        MenuItem menuOtrasOpciones = navigationView.findViewById(R.id.nav_subheader);
        MenuItem menuSettings = navigationView.findViewById(R.id.nav_setting);
        MenuItem menuCerrarSesion = navigationView.findViewById(R.id.nav_logout);

    }

}

