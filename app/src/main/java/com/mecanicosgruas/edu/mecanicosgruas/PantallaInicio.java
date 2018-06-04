package com.mecanicosgruas.edu.mecanicosgruas;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentCreateService;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentInbox;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentListService;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentService;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentSettings;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class PantallaInicio extends AppCompatActivity {


    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String fragmentTag="";
    public CircleImageView imagViewPerfil;
    public ImageView imgViewBackground;
    private TextView txtViewNickname;
    private  TextView txtViewEmail;

    DataReceivedListener listener;

    public void setDataReceivedListener(DataReceivedListener listener) {
        this.listener = listener;
    }


    public interface DataReceivedListener {
        void onReceived(int requestCode, int resultCode, Intent data);
        void onShutdown();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_inicio,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //event item actionbar
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                return true;
            case R.id.app_bar_search:
                Toast.makeText(getApplicationContext(),"Call search",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
        //get view
        toolbar = (Toolbar) findViewById(R.id.toolbarIniId);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_Inicio);
        navigationView = (NavigationView)findViewById(R.id.nav_panel);
        View header  = navigationView.getHeaderView(0);
        imagViewPerfil = (CircleImageView)header.findViewById(R.id.imgViewUserPanel);
        imgViewBackground = (ImageView)header.findViewById(R.id.imgViewBackgroundUserPanel);
        txtViewNickname = (TextView) header.findViewById(R.id.textViewNickname);
        txtViewEmail = (TextView)header.findViewById(R.id.textViewEmail);



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
        if(fragmentTag.equals(""))
        changeFragment(new FragmentListService(),"inicio");

        if(ApiManager.getUser()!=null)
        {
            txtViewNickname.setText(ApiManager.getUser().getNickname());
            txtViewEmail.setText(ApiManager.getUser().getEmail());
            UpdateImage();

        }




    }
    //Cambair de fragment
    private void NavEventMenuItemView()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean checkBoton = false;
                switch (item.getItemId())
                {
                    case R.id.nav_inicio:
                        //call fragment inicio
                        changeFragment(new FragmentListService(),"inicio");
                        checkBoton = true;
                        break;
                    case R.id.nav_service:
                        //call fragement service manager
                        changeFragment(new FragmentCreateService(),"CreateService");
                        getSupportActionBar().setTitle("Servicio");
                        break;
                    case R.id.nav_inbox:
                        //call fragement inbox
                        changeFragment(new FragmentInbox(),"Inbox");
                        checkBoton = true;
                        break;
                    case R.id.nav_setting:
                        //call fragemnt setting
                        changeFragment(new FragmentSettings(),"Settings");
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
                if(checkBoton)
                {
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
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            switch (fragmentTag)
            {
                case "service_chat":
                    changeFragment(new FragmentService(),"CreateService");
                    getSupportActionBar().setTitle("Servicio");
                    break;
                case "chat":
                    changeFragment(new FragmentInbox(),"Inbox");
                    getSupportActionBar().setTitle("Inbox");
                    break;
                case "Inbox":
                case "CreateService":
                case "Settings":
                case "servicio":
                    changeFragment(new FragmentListService(),"inicio");
                    getSupportActionBar().setTitle("Inicio");
                    break;
                case "color_fragment":
                    changeFragment(new FragmentListService(),"Settings");
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
    public void changeFragment(Fragment newFragment, String tag)
    {
        if (listener != null) {
            listener.onShutdown();
        }
        FragmentManager fragmentManager = getFragmentManager();

        //Operaciones de agregar, remplazar y eliminar

        // Administra los frgamentos de un activity
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        //Buscar si ya existe el mismo frgamento "abierto"
        android.support.v4.app.Fragment fragmentoActual = fm.findFragmentByTag(tag);
        if(fragmentoActual != null && fragmentoActual.isVisible())
        {
            Toast.makeText(getApplicationContext(),"Ya se esta mostrando",Toast.LENGTH_LONG).show();
            return;
        }
        // realiza las "transicciones" de un fragmento
        // Agregar, remplazar y eliminar
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fragment_base,newFragment, tag);

        ft.commit();//aplicar cambios
        fragmentTag = tag;
    }

    public void changeFragment(Fragment newFragment, String tag,String titleActionBar)
    {
        if (listener != null) {
            listener.onShutdown();
        }
        FragmentManager fragmentManager = getFragmentManager();

        //Operaciones de agregar, remplazar y eliminar

        // Administra los frgamentos de un activity
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        //Buscar si ya existe el mismo frgamento "abierto"
        android.support.v4.app.Fragment fragmentoActual = fm.findFragmentByTag(tag);
        if(fragmentoActual != null && fragmentoActual.isVisible())
        {
            Toast.makeText(getApplicationContext(),"Ya se esta mostrando",Toast.LENGTH_LONG).show();
            return;
        }
        // realiza las "transicciones" de un fragmento
        // Agregar, remplazar y eliminar
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fragment_base,newFragment, tag);

        ft.commit();//aplicar cambios
        fragmentTag = tag;
        getSupportActionBar().setTitle(titleActionBar);
    }

    void LogOut()
    {
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
                        // continue with delete
                        finish();
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


    public void UpdateImage()
    {
        if(ApiManager.getUser().getEndPointImagePerfil()!=null)
        {
            Picasso.with(PantallaInicio.this)
                    .load( ManagerREST.getURI() + ApiManager.getUser().getEndPointImagePerfil())
                    .placeholder(R.drawable.user)
                    .into(imagViewPerfil);
        }

        if(ApiManager.getUser().getEndPointImageBackground()!=null)
        {
            Picasso.with(PantallaInicio.this)
                    .load( ManagerREST.getURI() + ApiManager.getUser().getEndPointImageBackground())
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
        UpdateImage();
    }
}
