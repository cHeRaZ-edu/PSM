package com.mecanicosgruas.edu.mecanicosgruas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentCreateService;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentListService;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentSettings;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;

public class PantallaInicio extends AppCompatActivity {
    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navPanel;

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
                mDrawerLayout.openDrawer(Gravity.START);
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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_Inicio);
        navPanel = (NavigationView)findViewById(R.id.nav_panel);
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
        changeFragment(new FragmentListService(),"ListService");




    }
    private void NavEventMenuItemView()
    {
        navPanel.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean checkBoton = false;
                switch (item.getItemId())
                {
                    case R.id.nav_inicio:
                        //call fragment inicio
                        changeFragment(new FragmentListService(),"ListService");
                        checkBoton = true;
                        break;
                    case R.id.nav_service:
                        //call fragement service manager
                        changeFragment(new FragmentCreateService(),"CreateService");
                        checkBoton = true;
                        break;
                    case R.id.nav_inbox:
                        //call fragement inbox
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
                        finish();
                        break;
                    default:
                        break;
                }
                //change title actionbar
                if(checkBoton)
                {
                    getSupportActionBar().setTitle(item.getTitle());
                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
        return;
    }

    private void changeFragment(Fragment fragment,String tag)
    {
        FragmentManager fragmentManager = getFragmentManager();

        //Check if visible
        Fragment FragmentDisplay = fragmentManager.findFragmentByTag(tag);
        if(FragmentDisplay!=null&&FragmentDisplay.isVisible())
            return;
        //show fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_base,fragment,tag);
        fragmentTransaction.commit();
    }
}
