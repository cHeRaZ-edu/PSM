package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.ServicioDisplayActivity;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdaptadorPantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LUNA on 01/04/2018.
 */

public class FragmentListService extends Fragment {
    private Activity activity;
    private List<Servicio>list_service = new ArrayList<Servicio>();
    private ListView list_view;
    private List<Servicio> testLista = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_service,container,false);

        list_view = (ListView) view.findViewById(R.id.listview_services);
        activity = getActivity();
        iniflateListViewServicios();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventListView();
    }

    private void iniflateListViewServicios()
    {

        ListAdaptadorPantallaInicio adapter;
        for(int i = 0;i<25;i++)
        {
            Servicio s = new Servicio("Example","Example","123456789",4.5f);
            testLista.add(s);
        }

        adapter = new ListAdaptadorPantallaInicio(testLista);

        list_view.setAdapter(adapter);
    }

    private void EventListView()
    {

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String data = ((TextView)view.findViewById(R.id.txtViewNameService)).getText().toString();
                Toast.makeText(activity.getApplicationContext(),data + ": " + Integer.toString(position),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity, ServicioDisplayActivity.class);
                intent.putExtra("serviceSelect", testLista.get(position));
                startActivity(intent);
            }
        });
    }
}
