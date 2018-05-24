package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.ImageUtil.ImageUtil;
import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.ReadPath.ReadPathUtil;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdapterChat;
import com.mecanicosgruas.edu.mecanicosgruas.model.Message;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LUNA on 11/05/2018.
 */

public class FragmentChat extends Fragment implements PantallaInicio.DataReceivedListener

{
    ListView listViewMessage;
    public User user;
    List<Message> listMessage = new ArrayList<>();
    TextView nicknameUser;
    ImageButton btn_ImageLoad;
    ImageButton btn_SendMessage;
    EditText editTxtMessage;
    PantallaInicio activty;
    CircleImageView imgPerfilUser;
    String encodeBase64Img;
    ListAdapterChat adapter;
    ThreadChatSlow callChatThread;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        activty = (PantallaInicio)getActivity();
        activty.setDataReceivedListener(this);
        user = ApiManager.getUser_select();
        encodeBase64Img = "";
        listViewMessage = (ListView)view.findViewById(R.id.listView_chat);
        nicknameUser = (TextView)view.findViewById(R.id.TxtViewUserInbox);
        TextView message_last = (TextView)view.findViewById(R.id.TxtViewMessageInbox);
        TextView timeSend = (TextView)view.findViewById(R.id.TxtViewTimeSend);
        imgPerfilUser = (CircleImageView)view.findViewById(R.id.imgViewInboxUser);
        btn_ImageLoad = (ImageButton)view.findViewById(R.id.idBtnImagenLoad);
        btn_SendMessage = (ImageButton) view.findViewById(R.id.idBtnSendMessage);
        editTxtMessage = (EditText)view.findViewById(R.id.idEditTxtMessage);

        message_last.setVisibility(View.GONE);
        timeSend.setVisibility(View.GONE);
        nicknameUser.setText(user.getNickname());

        Picasso.with(getContext())
                .load( ManagerREST.getURI() + user.getEndPointImagePerfil())
                .placeholder(R.drawable.user)
                .into(imgPerfilUser);

        //if(ApiManager.isInternetConnection(getContext()))
        //ManagerREST.getMessage_Raw(ApiManager.getUser().getNickname(),user.getNickname(),getContext(),this);
        callChatThread = new ThreadChatSlow();

        callChatThread.execute(this);


        EventButton();


        return view;
    }

    public void EventButton()
    {
        btn_SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTxtMessage.getText().toString();
                if(!message.trim().isEmpty())
                {
                    if(ApiManager.isInternetConnection(getContext()))
                    ManagerREST.addMessage(ApiManager.getUser().getNickname(),user.getNickname(),message,encodeBase64Img,getContext());
                    encodeBase64Img="";
                    editTxtMessage.setText("");
                }
            }
        });

        btn_ImageLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShutdown();
                ApiManager.ImageSelect(activty);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    void adapterListView()
    {
        if(adapter!=null)
        {
            adapter.notifyDataSetChanged();

            listViewMessage.setSelection(listMessage.size() - 1);

            return;
        }


        adapter = new ListAdapterChat(listMessage,getContext());

        listViewMessage.setAdapter(adapter);

        listViewMessage.setSelection(listMessage.size() - 1);
    }

    @Override
    public void onReceived(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && ApiManager.PHOTO_SHOT == requestCode)
        {
            // Guardamos el thumbnail de la imagen en un archivo con el siguiente nombre
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            if(bitmap!=null)
                encodeBase64Img = ImageUtil.encodeBase64(bitmap);



            // mImageView.setImageBitmap(bitmap);
        } else if(resultCode == RESULT_OK && ApiManager.STORAGE_IMAGE == requestCode)
        {
            Uri uri = data.getData();
            String pathImage = ReadPathUtil.getRealPathFromURI_API19(activty,uri);

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(pathImage,bmOptions);
            if(bitmap!=null)
                encodeBase64Img = ImageUtil.encodeBase64(bitmap);



            Log.i("Path Sotrage",pathImage);



        }


           if( callChatThread.isCancelled())
           {
               callChatThread = new ThreadChatSlow();
               callChatThread.execute(this);
           }

    }
    @Override
    public void onShutdown()
    {
        callChatThread.cancel(true);
    }

    public void Update_Chat(List<Message> listMessageParam)
    {
        if(listMessageParam.size()!=0)
        {
            for(int i = listMessage.size(); i<listMessageParam.size();i++)
            {
                Message msg = listMessageParam.get(i);
                listMessage.add(msg);
            }

            adapterListView();
        }
    }



}

class ThreadChatSlow extends AsyncTask<FragmentChat,String,String>
{

    @Override
    protected String doInBackground(FragmentChat... fragmentChats) {
        final FragmentChat fragment = fragmentChats[0];
        for(;;)
        {
            if(!ApiManager.isInternetConnection(fragment.getContext()))
                break;

            if(isCancelled())
            {
                Log.d("Destroy","Thread destroy !!!");
                break;
            }

            //Este metodo se llama en el hilo principal
            fragment.activty.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    ManagerREST.getMessage_Raw(ApiManager.getUser().getNickname(),fragment.user.getNickname(),fragment.listMessage.size(),fragment.getContext(),fragment);

                }
            });

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}