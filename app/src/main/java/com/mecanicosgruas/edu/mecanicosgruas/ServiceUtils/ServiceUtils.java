package com.mecanicosgruas.edu.mecanicosgruas.ServiceUtils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.StorageUtils.StorageUtils;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;

import java.io.IOException;
import java.util.List;

/**
 * Created by LUNA on 12/06/2018.
 */

public class ServiceUtils extends Service {
    Context context;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;


    //Handler thate recieve message from thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            //Process task
            while(true) {
                try {
                    Thread.sleep(6000);
                    Geolocalizacion();
                    Inbox_Message();

                } catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            //stopSelf(msg.arg1);
        }
    }
    @Override
    public boolean stopService(Intent name) {
        stopSelf();
        return super.stopService(name);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service Done!!!",Toast.LENGTH_LONG).show();
    }
    private void Geolocalizacion() throws IOException {
        //...Process Ubicacion
        LatLng latLng =  ApiManager.getCurrentLocation(context);
        StorageUtils.SetLatLng(latLng);
        //List<String> address =  ApiManager.getAddress(,latLng);
        //StorageUtils.SetUbicacion(address);
        ApiManager.ShowNotify_Geolocalizacion(context,"Mecanicos y Gruas","Tu ubicacion ha sido actualizada " +latLng.toString());

    }
    private void Inbox_Message() {
        if(!ApiManager.isInternetConnection(context))
            return;

            if(ApiManager.count_inbox != ApiManager.old_count_inbox) {
                ApiManager.old_count_inbox = ApiManager.count_inbox;
                if(ApiManager.runApplicationInbox)
                //Notificar
                ApiManager.ShowNotify_Message(context,"Inbox","Tienes un nuevo mensaje");
                else
                    ApiManager.runApplicationInbox = true;
            }
            User user = new ManagerBD(context).SelectTableUser();
            if(user!=null)
                ManagerREST.get_SizeInbox(context,user.getNickname());

    }
}
