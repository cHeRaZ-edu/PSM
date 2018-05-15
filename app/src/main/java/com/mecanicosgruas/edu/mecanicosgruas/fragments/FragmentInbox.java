package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.MainActivity;
import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdaptadorPantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdapterInbox;
import com.mecanicosgruas.edu.mecanicosgruas.model.Inbox;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LUNA on 11/05/2018.
 */

public class FragmentInbox extends android.support.v4.app.Fragment {
    private PantallaInicio activity;
    ListView listViewInbox;
    List<Inbox> inboxList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox,container,false);

        listViewInbox = (ListView) view.findViewById(R.id.listView_inbox);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (PantallaInicio) getActivity();
        Inizialitate();
        EventListView();
    }

    void Inizialitate()
    {
        ListAdapterInbox adapter;
        for(int i = 0;i<25;i++)
        {
           inboxList.add(new Inbox("edu1234","Mi mensaje"));
        }

        adapter = new ListAdapterInbox(inboxList);

        listViewInbox.setAdapter(adapter);
    }

    void EventListView()
    {
        listViewInbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                activity.changeFragment(new FragmentChat(),"chat","Chat");
            }
        });
    }
}
