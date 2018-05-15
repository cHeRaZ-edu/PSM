package com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by LUNA on 15/05/2018.
 */

public class ConnectionREST {
    private static ConnectionREST mInstance;
    private static Context mContext;
    private static RequestQueue mRequestQueue;

    private ConnectionREST(Context context)
    {
        this.mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized ConnectionREST getInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance =  new ConnectionREST(context);
        }

        return mInstance;
    }

    public static RequestQueue getRequestQueue()
    {
        if(mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request)
    {
        getRequestQueue().add(request);
    }
}
