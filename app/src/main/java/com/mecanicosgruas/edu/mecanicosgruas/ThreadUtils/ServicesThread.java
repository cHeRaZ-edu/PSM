package com.mecanicosgruas.edu.mecanicosgruas.ThreadUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentListService;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;

import java.util.List;

/**
 * Created by LUNA on 13/06/2018.
 */

public class ServicesThread extends AsyncTask<FragmentListService,String,String> {
    FragmentListService fragmentListService;



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(FragmentListService... fragment) {
        fragmentListService = fragment[0];
        new ManagerBD(fragmentListService.getContext()).TruncTableTableServiceDisplay();

        while(true) {

            if(isCancelled())
                break;
            //...Actualizar en sqlite
            //UpdateSqliteTablesServicios();

            if(fragmentListService.list_service.size()!=fragmentListService.count_service)
                fragmentListService.isUpdate = true;

            //...Checar si hay cambios
            if(!fragmentListService.isUpdate)
                ManagerREST.get_Size_Service(fragmentListService);

            else {
                //...Actualizar listView
                fragmentListService.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Actulizar desde el web services
                        if(fragmentListService.list_service.size()!=fragmentListService.count_service)
                            ManagerREST.getService(fragmentListService.getContext(),fragmentListService);
                    }
                });
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        //Toast.makeText(fragmentListService.getContext(),"Thread Done !!!",Toast.LENGTH_LONG).show();

        return null;
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        new ManagerBD(fragmentListService.getContext()).TruncTableTableServiceDisplay();
    }
    /*
    void UpdateSqliteTablesServicios(){
        ManagerBD managerBD = new ManagerBD(fragmentListService.getContext());
        managerBD.TruncTableTableServiceDisplay();
        for(int i = 0;i<list_servicio.size();i++) {
            Servicio servicio = list_servicio.get(i);
            managerBD.InsertTableServiceDisplay(servicio);
        }
    }
    */

}
