package com.mecanicosgruas.edu.mecanicosgruas.adaptadores;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.model.Message;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LUNA on 11/05/2018.
 */

public class ListAdapterChat extends BaseAdapter {
    List<Message> listMessage;
    Context context;

    public ListAdapterChat(List<Message> listMessage,Context context) {
        this.listMessage = listMessage;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listMessage.size();
    }

    @Override
    public Object getItem(int position) {
        return listMessage.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Message message = listMessage.get(position);
        if(view == null)
        {
            //Inflate xml to view
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_user,null);
            /*
            LinearLayout ContainerMssage = (LinearLayout)view.findViewById(R.id.linearLayout_container_Message);
            ImageView imgViewPhoto = new ImageView(viewGroup.getContext());
            imgViewPhoto.setImageResource(R.drawable.background);
            ContainerMssage.addView(imgViewPhoto);
            */

        }
        TextView txtViewUsernickname = (TextView)view.findViewById(R.id.txtViewUsuarioMessage);
        TextView textViewMessage = (TextView)view.findViewById(R.id.txtViewMessage);
        TextView textViewHora = (TextView)view.findViewById(R.id.txtViewHora);
        CardView cardLayout = (CardView)view.findViewById(R.id.idCardViewChat);

        txtViewUsernickname.setText(message.getNickname());
        textViewHora.setText(message.getTimeSend());
        textViewMessage.setText(message.getMessage());


        if(!message.isMe())
        {
            cardLayout.setCardBackgroundColor(0xFFFFFFFF);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.START;

            cardLayout.setLayoutParams(params);


        }
        else
        {
            cardLayout.setCardBackgroundColor(0xFF9BDFDB);


           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.END;
            cardLayout.setLayoutParams(params);

        }
        ImageView imgView = view.findViewById(R.id.imgViewMessage);
        imgView.setVisibility(View.VISIBLE);

        if(!message.getEndpointImg().equals(""))
        {

            Picasso.with(context)
                    .load( ManagerREST.getURI() + message.getEndpointImg())
                    .placeholder(R.drawable.user)
                    .fit()
                    .into(imgView);
        }
        else
        {
            imgView.setVisibility(View.GONE);
        }



        return view;
    }
}
