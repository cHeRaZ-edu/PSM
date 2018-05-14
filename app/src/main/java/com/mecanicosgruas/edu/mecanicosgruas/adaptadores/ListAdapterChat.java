package com.mecanicosgruas.edu.mecanicosgruas.adaptadores;

import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.model.Message;

import java.util.List;

/**
 * Created by LUNA on 11/05/2018.
 */

public class ListAdapterChat extends BaseAdapter {
    List<Message> listMessage;

    public ListAdapterChat(List<Message> listMessage) {
        this.listMessage = listMessage;
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

        if(view == null)
        {
            //Inflate xml to view
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_user,null);
        }
        TextView txtViewUsernickname = (TextView)view.findViewById(R.id.txtViewUsuarioMessage);
        TextView textViewMessage = (TextView)view.findViewById(R.id.txtViewMessage);
        CardView cardLayout = (CardView)view.findViewById(R.id.idCardViewChat);
        Message message = listMessage.get(position);

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


        textViewMessage.setText(message.getMessage());


        return view;
    }
}
