package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.StorageUtils.StorageUtils;
import com.mecanicosgruas.edu.mecanicosgruas.ThreadUtils.ServicesThread;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdaptadorPantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.ColorAcivity;
import com.mecanicosgruas.edu.mecanicosgruas.model.Message;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LUNA on 01/04/2018.
 */

public class FragmentListService extends Fragment implements PantallaInicio.DataReceivedListener{
    private View fragmentView;
    private PantallaInicio activity;
    public List<Servicio>list_service = new ArrayList<Servicio>();
    public boolean isUpdate = false;
    private ListView list_view;
    ListAdaptadorPantallaInicio adapter;
    public int count_service = -1;
    private ServicesThread thread_fragment;
    LinearLayout layout;
    int colorDefault;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_service,container,false);
        fragmentView = view;
        list_view = (ListView) view.findViewById(R.id.listview_services);
        activity = (PantallaInicio) getActivity();
        activity.fragmentSharedPrefernces = activity.getString(R.string.InicioKeyColor);
        activity.setDataReceivedListener(this);

        count_service = -1;
        thread_fragment = new ServicesThread();
        thread_fragment.execute(this);

            //ManagerREST.getService(activity,this);

        //ColorAcivity colorAcivity =  new ManagerBD(activity).GetColorActivity(ApiManager.INICIO_FRAGMENT);
        StorageUtils.InizilateSharedPrefernces(activity);
        int color = StorageUtils.getColor(getString(R.string.InicioKeyColor));
        layout = view.findViewById(R.id.id_fragment);
        if(color!=0)
            colorDefault = color;
        OnChangeDarkMode();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventListView();
    }

    private void iniflateListViewServicios()
    {
        if(adapter!=null)
            return;

        adapter = new ListAdaptadorPantallaInicio(list_service,getContext());

        list_view.setAdapter(adapter);
    }
    private void EventListView()
    {

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                ApiManager.setService_select(list_service.get(position));
                String data = ((TextView)view.findViewById(R.id.txtViewNameService)).getText().toString();
                Toast.makeText(activity.getApplicationContext(),data + ": " + Integer.toString(position),Toast.LENGTH_LONG).show();
                activity.changeFragment(new FragmentService(), "servicio","Servicio");
            }
        });
    }
    public void UpdateServices(List<Servicio>list_serviceParam) {
        if(list_serviceParam.size()!=0)
        {
            for(int i = list_service.size(); i<list_serviceParam.size();i++)
            {
                Servicio servicio = list_serviceParam.get(i);
                list_service.add(servicio);
            }

        }
        count_service = list_serviceParam.size();
        isUpdate = false;
        if(adapter==null) {
            adapter = new ListAdaptadorPantallaInicio(list_service,getContext());
            list_view.setAdapter(adapter);

        }
        else
        {
            adapter = new ListAdaptadorPantallaInicio(list_service,getContext());
            list_view.setAdapter(adapter);
        }
    }
    @Override
    public void onReceived(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == ApiManager.CODE_RESULT_CHANGE_COLOR) {
            StorageUtils.InizilateSharedPrefernces(activity);
            int color = StorageUtils.getColor(getString(R.string.InicioKeyColor));
            if(color!=0) {
                LinearLayout layout = fragmentView.findViewById(R.id.id_fragment);
                layout.setBackgroundColor(color);
            }
        }
    }
    @Override
    public void onShutdown() {
        if(thread_fragment!=null)
        {
            thread_fragment.cancel(true);
        }

        Toast.makeText(getContext(),"Thread done !!!",Toast.LENGTH_LONG).show();
    }
    @Override
    public void OnResumed() {
        if(thread_fragment.isCancelled())
        {
            thread_fragment = new ServicesThread();
            thread_fragment.execute(this);
        }
    }

    @Override
    public void OnChangeDarkMode() {
        if(layout!=null && ApiManager.ENABLED_DARK_MODE)
            layout.setBackgroundColor(ApiManager.COLOR_DARK);
        else
            layout.setBackgroundColor(colorDefault);
    }
}
