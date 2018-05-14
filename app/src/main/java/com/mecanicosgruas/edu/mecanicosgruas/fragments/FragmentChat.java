package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdapterChat;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdapterInbox;
import com.mecanicosgruas.edu.mecanicosgruas.model.Inbox;
import com.mecanicosgruas.edu.mecanicosgruas.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LUNA on 11/05/2018.
 */

public class FragmentChat extends Fragment {
    ListView listViewMessage;
    String nameUser;
    List<Message> listMessage = new ArrayList<>();
    TextView nicknameUser;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);

        listViewMessage = (ListView)view.findViewById(R.id.listView_chat);
        nicknameUser = (TextView)view.findViewById(R.id.TxtViewUserInbox);
        TextView message_last = (TextView)view.findViewById(R.id.TxtViewMessageInbox);
        message_last.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Inizialitate();
    }

    void Inizialitate()
    {
        ListAdapterChat adapter;
        for(int i = 0;i<25;i++)
        {
            if(i%2==0 || i == 0)
                listMessage.add(new Message("Yo","A la verga, te pasas de verga perro, conmigo vergazso a la verga perro",true));
            else
                listMessage.add(new Message("Pobreton","Que onda",false));
        }

        adapter = new ListAdapterChat(listMessage);

        listViewMessage.setAdapter(adapter);
    }

}
