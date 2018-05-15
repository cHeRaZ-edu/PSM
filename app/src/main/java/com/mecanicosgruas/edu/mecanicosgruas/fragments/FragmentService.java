package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdapterChat;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdapterHorarioDisplay;
import com.mecanicosgruas.edu.mecanicosgruas.model.HorarioClass;
import com.mecanicosgruas.edu.mecanicosgruas.model.Message;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by LUNA on 13/05/2018.
 */

public class FragmentService extends Fragment {
    private ListView list_viewHorario;//List horario
    private ListView list_chat;//List chat

    private Servicio servicioSelect;
    private RatingBar ratingBar;
    private TextView txtViewServiceName;
    private ImageView imgViewBackground;
    private CircleImageView imgViewService;

    private TextView txtViewDescService;
    private TextView txtViewTelefono;
    private TextView txtViewCiudad;
    private TextView txtViewColonia;
    private TextView txtViewCalle;
    private TextView txtViewNum;

    private Button SendInbox;
    private ImageButton SendMessage;
    private ImageButton SendMessagePhoto;
    private EditText editTextMessage;

    private PantallaInicio activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_services,container,false);
        list_viewHorario = (ListView)view.findViewById(R.id.listViewDisplay_horario);
        list_chat = (ListView)view.findViewById(R.id.listView_chat);

        ratingBar = (RatingBar)view.findViewById(R.id.ratingBarServiceDisplay);
        txtViewServiceName = (TextView)view.findViewById(R.id.txtViewNameServiceDisplay);
        imgViewBackground = (ImageView)view.findViewById(R.id.imgViewBackgroundServiceDisplay);
        imgViewService = (CircleImageView)view.findViewById(R.id.imgViewService);

        txtViewDescService = (TextView)view.findViewById(R.id.txtViewDescService);
        txtViewTelefono = (TextView)view.findViewById(R.id.txtViewTelefono);

        //Direccion
        txtViewCiudad = (TextView)view.findViewById(R.id.txtViewCiudad);
        txtViewColonia = (TextView)view.findViewById(R.id.txtViewColonia);
        txtViewCalle = (TextView)view.findViewById(R.id.txtViewCalle);
        txtViewNum = (TextView)view.findViewById(R.id.txtViewNum);

        //Button
        SendInbox = (Button)view.findViewById(R.id.btnMandarInbox);

        SendMessage = (ImageButton)view.findViewById(R.id.imgBtnSendMessage);
        SendMessagePhoto = (ImageButton)view.findViewById(R.id.imgBtnSendMessageImg);

        //Edit
        editTextMessage = (EditText)view.findViewById(R.id.editTxtMessage);





        return view;
    }

    void MostrarHorarios()
    {
        List<HorarioClass> listaHorario = new ArrayList<>();
        ListAdapterHorarioDisplay adapter;

        listaHorario.add(new HorarioClass("Martes","13:00","18:00"));
        listaHorario.add(new HorarioClass("Miercoles","13:00","18:00"));
        listaHorario.add(new HorarioClass("Jueves","13:00","18:00"));
        listaHorario.add(new HorarioClass("Viernes","12:00","16:00"));

        adapter = new ListAdapterHorarioDisplay(listaHorario);

        list_viewHorario.setAdapter(adapter);
    }

    void MostrarChat()
    {
        List<Message> list_mensajes = new ArrayList<>();

        list_mensajes.add(new Message("Yo","Que onda",true));
        list_mensajes.add(new Message("Pobreton","Que onda",false));
        list_mensajes.add(new Message("Yo","A cuanto la grua",true));
        list_mensajes.add(new Message("Pobreton","Cual gruas" ,false));

        ListAdapterChat adapter;

        adapter = new ListAdapterChat(list_mensajes);

        list_chat.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (PantallaInicio) getActivity();
        MostrarHorarios();
        MostrarChat();

        SendInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.changeFragment(new FragmentChat(),"service_chat","Chat");
            }
        });

    }
}
