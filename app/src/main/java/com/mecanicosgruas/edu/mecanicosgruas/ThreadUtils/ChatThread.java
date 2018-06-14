package com.mecanicosgruas.edu.mecanicosgruas.ThreadUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentChat;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentListService;

/**
 * Created by LUNA on 14/06/2018.
 */

public class ChatThread extends AsyncTask <FragmentChat,String,String> {
    FragmentChat fragment;


    @Override
    protected String doInBackground(FragmentChat... fragmentChats) {

        fragment = fragmentChats[0];

        while(true){

            if(isCancelled())
                break;
            if(fragment == null)
                break;
            if(ApiManager.isInternetConnection(fragment.getContext())) {
                //Este metodo se llama en el hilo principal
                fragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ManagerREST.getMessage_Raw(ApiManager.getUser().getNickname(),fragment.user.getNickname(),fragment.listMessage.size(),fragment.getContext(),fragment);
                    }
                });
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.i("Thread Chat","Thread done !!!");

        return null;
    }
}
