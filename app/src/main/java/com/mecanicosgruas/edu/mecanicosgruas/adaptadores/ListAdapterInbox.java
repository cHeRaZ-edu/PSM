package com.mecanicosgruas.edu.mecanicosgruas.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.model.Inbox;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by LUNA on 11/05/2018.
 */

public class ListAdapterInbox extends BaseAdapter {
    List<Inbox> listInbox;

    public ListAdapterInbox(List<Inbox> listInbox) {
        this.listInbox = listInbox;
    }

    @Override
    public int getCount() {
        return listInbox.size();
    }

    @Override
    public Object getItem(int position) {
        return listInbox.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        //Check if exist
        if(view == null)
        {
            //Inflate xml to view
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_user_inbox,null);
        }

        CircleImageView imageView = (CircleImageView)view.findViewById(R.id.imgViewInboxUser);
        TextView nickname = (TextView)view.findViewById(R.id.TxtViewUserInbox);
        TextView message = (TextView)view.findViewById(R.id.TxtViewMessageInbox);

        Inbox inbox = listInbox.get(position);
        nickname.setText(inbox.getNickname());
        message.setText(inbox.getLast_message());

        return view;
    }
}
