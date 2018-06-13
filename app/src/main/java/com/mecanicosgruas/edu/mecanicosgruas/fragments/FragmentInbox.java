package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.MainActivity;
import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.StorageUtils.StorageUtils;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdaptadorPantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdapterInbox;
import com.mecanicosgruas.edu.mecanicosgruas.model.ColorAcivity;
import com.mecanicosgruas.edu.mecanicosgruas.model.Inbox;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LUNA on 11/05/2018.
 */

public class FragmentInbox extends android.support.v4.app.Fragment implements PantallaInicio.DataReceivedListener {
    private View fragmentView;
    private PantallaInicio activity;
    ListView listViewInbox;
    List<Inbox> inboxList = new ArrayList<>();
    ListAdapterInbox adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox,container,false);
        fragmentView = view;
        activity = (PantallaInicio) getActivity();
        activity.setDataReceivedListener(this);
        activity.fragmentSharedPrefernces = activity.getString(R.string.InboxKeyColor);
        listViewInbox = (ListView) view.findViewById(R.id.listView_inbox);
        if(ApiManager.isInternetConnection(getContext()))
        ManagerREST.get_users_message(ApiManager.getUser().getNickname(),getContext(),this);

        StorageUtils.InizilateSharedPrefernces(activity);
        int color = StorageUtils.getColor(getString(R.string.InboxKeyColor));
        if(color!=0)
        {
            LinearLayout layout = view.findViewById(R.id.id_fragment);
            layout.setBackgroundColor(color);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EventListView();
    }

    void Inizialitate()
    {
        /*
        for(int i = 0;i<25;i++)
        {
           inboxList.add(new Inbox("edu1234","Mi mensaje"));
        }
        */

        adapter = new ListAdapterInbox(inboxList,getContext());

        listViewInbox.setAdapter(adapter);
    }

    void EventListView()
    {
        listViewInbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Inbox inbox = inboxList.get(position);
                User u = new User();
                u.setNickname(inbox.getNickname());
                u.setEndPointImagePerfil(inbox.getEndPointImagePerfil());
                ApiManager.setUser_select(u);
                activity.changeFragment(new FragmentChat(),"chat","Chat");
            }
        });
    }

    public void uodateListUser(List<Inbox> listInboxParam)
    {
        inboxList = listInboxParam;

        Inizialitate();
    }

    @Override
    public void onReceived(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == ApiManager.CODE_RESULT_CHANGE_COLOR) {
            StorageUtils.InizilateSharedPrefernces(activity);
            int color = StorageUtils.getColor(getString(R.string.InboxKeyColor));
            if(color!=0) {
                LinearLayout layout = fragmentView.findViewById(R.id.id_fragment);
                layout.setBackgroundColor(color);
            }
        }
    }

    @Override
    public void onShutdown() {

    }
}
