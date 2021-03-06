package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.StorageUtils.StorageUtils;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdapterChat;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdapterHorarioDisplay;
import com.mecanicosgruas.edu.mecanicosgruas.model.ColorAcivity;
import com.mecanicosgruas.edu.mecanicosgruas.model.HorarioClass;
import com.mecanicosgruas.edu.mecanicosgruas.model.Message;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LUNA on 13/05/2018.
 */

public class FragmentService extends Fragment implements PantallaInicio.DataReceivedListener {
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
    //private ImageButton SendMessage;
    //private ImageButton SendMessagePhoto;
    //private EditText editTextMessage;
    private FragmentService fragment;
    private View frgamentView;

    private PantallaInicio activity;
    LinearLayout layout;
    int colorDefault;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_services,container,false);
        frgamentView =view;
        fragment = this;
        activity = (PantallaInicio) getActivity();
        activity.setDataReceivedListener(this);
        activity.fragmentSharedPrefernces = activity.getString(R.string.ServiceDisplayKeyColor);
        list_viewHorario = (ListView)view.findViewById(R.id.listViewDisplay_horario);
        list_chat = (ListView)view.findViewById(R.id.listView_chat);

        ratingBar = (RatingBar)view.findViewById(R.id.ratingBarServiceDisplay);
        txtViewServiceName = (TextView)view.findViewById(R.id.txtViewNameServiceDisplay);
        imgViewBackground = (ImageView)view.findViewById(R.id.imgViewBackgroundServiceDisplay);
        imgViewService = (CircleImageView)view.findViewById(R.id.imgViewService);

        txtViewDescService = (TextView)view.findViewById(R.id.txtViewDescServiceContanier);
        txtViewTelefono = (TextView)view.findViewById(R.id.txtViewTelefono);

        //Direccion
        txtViewCiudad = (TextView)view.findViewById(R.id.txtViewCiudad);
        txtViewColonia = (TextView)view.findViewById(R.id.txtViewColonia);
        txtViewCalle = (TextView)view.findViewById(R.id.txtViewCalle);
        txtViewNum = (TextView)view.findViewById(R.id.txtViewNum);

        //Button
        SendInbox = (Button)view.findViewById(R.id.btnMandarInbox);

        //SendMessage = (ImageButton)view.findViewById(R.id.imgBtnSendMessage);
        //SendMessagePhoto = (ImageButton)view.findViewById(R.id.imgBtnSendMessageImg);

        //Edit
        //editTextMessage = (EditText)view.findViewById(R.id.editTxtMessage);

        if(ApiManager.isInternetConnection(getContext()))
            ManagerREST.FindService(getContext(),null,this,2);

        StorageUtils.InizilateSharedPrefernces(activity);
        layout = view.findViewById(R.id.id_fragment);
        int color = StorageUtils.getColor(getString(R.string.ServiceDisplayKeyColor));
        if(color!=0)
            colorDefault = color;
        OnChangeDarkMode();

        return view;
    }

    void EventButtons()
    {

        SendInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ApiManager.getUser().getNickname().equals(servicioSelect.getNickname()))
                {
                    Toast.makeText(activity.getApplicationContext(),"Este es tu servicio",Toast.LENGTH_LONG).show();
                    return;
                }
                User userSelect = new User();
                userSelect.setNickname(servicioSelect.getNickname());
                userSelect.setEndPointImagePerfil(servicioSelect.getEndpointImageUser());
                ApiManager.setUser_select(userSelect);
                activity.changeFragment(new FragmentChat(),"service_chat","Chat");
            }
        });

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ManagerREST.FavoritoService(ApiManager.getUser().getNickname(),servicioSelect.getNickname(), getContext(),fragment);
                return true;
            }
        });
    }

    void MostrarHorarios()
    {
        List<HorarioClass> listaHorario = servicioSelect.getListHorario();
        ListAdapterHorarioDisplay adapter;


        adapter = new ListAdapterHorarioDisplay(listaHorario);

        list_viewHorario.setAdapter(adapter);
    }

    void MostrarChat()
    {
        List<Message> list_mensajes = new ArrayList<>();



        ListAdapterChat adapter;

        adapter = new ListAdapterChat(list_mensajes,getContext());

        list_chat.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        EventButtons();

    }

    public void updateData()
    {
        servicioSelect  = ApiManager.getService_select();

        txtViewServiceName.setText(servicioSelect.getNombreServicio());
        txtViewDescService.setText(servicioSelect.getDescService());
        txtViewTelefono.setText("Numero Telefono: " + servicioSelect.getTelefono());
        txtViewCiudad.setText("Ciudad: " + servicioSelect.getCiudad());
        txtViewColonia.setText("Colonia: " + servicioSelect.getColonia());
        txtViewCalle.setText("Calle: " + servicioSelect.getCalle());
        txtViewNum.setText("Numero: " + servicioSelect.getNum());
        updateFavorito((int)servicioSelect.getNumStars());


        Picasso.with(getContext())
                .load( ManagerREST.getURI() + servicioSelect.getEndpointImageUser())
                .placeholder(R.drawable.user)
                .into(imgViewService);

        Picasso.with(getContext())
                .load( ManagerREST.getURI() + servicioSelect.getEndpointImageBackground())
                .placeholder(R.drawable.background_service)
                .into(imgViewBackground);

        MostrarHorarios();
    }

    public void updateFavorito(int countFavorito)
    {
        int  count = ApiManager.COUNT_FAVORITO;

        float result = ((float)countFavorito/(float)count)*(float)ApiManager.COUNT_STARS;

        ratingBar.setRating(result);
    }

    @Override
    public void onReceived(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == ApiManager.CODE_RESULT_CHANGE_COLOR) {
            StorageUtils.InizilateSharedPrefernces(activity);
            int color = StorageUtils.getColor(getString(R.string.ServiceDisplayKeyColor));
            if(color!=0) {
                LinearLayout layout = frgamentView.findViewById(R.id.id_fragment);
                layout.setBackgroundColor(color);
            }
        }
    }

    @Override
    public void onShutdown() {

    }

    @Override
    public void OnResumed() {

    }

    @Override
    public void OnChangeDarkMode() {
        if(layout!=null && ApiManager.ENABLED_DARK_MODE)
            layout.setBackgroundColor(ApiManager.COLOR_DARK);
        else
            layout.setBackgroundColor(colorDefault);
    }
}
