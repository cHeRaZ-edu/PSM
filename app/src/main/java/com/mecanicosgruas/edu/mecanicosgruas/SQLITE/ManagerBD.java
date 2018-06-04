package com.mecanicosgruas.edu.mecanicosgruas.SQLITE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mecanicosgruas.edu.mecanicosgruas.model.User;

/**
 * Created by LUNA on 04/06/2018.
 */

public class ManagerBD extends SQLHelper {
    public ManagerBD(Context context) {
        super(context);
    }

    public void insertContact(User user,int color)
    {
        //Objecto ContectValues PARCIAL 3
        ContentValues values = new ContentValues();//Funciona como sharedpreferences
        values.put(UserTable.COLUMN_NAME,user.getName());
        values.put(UserTable.COLUMN_LASTNAME,user.getLast_name());
        values.put(UserTable.COLUMN_NICKNAME,user.getNickname());
        values.put(UserTable.COLUMN_TELEFONO,user.getTelefono());
        values.put(UserTable.COLUMN_COLOR,""+color);

        //Establecer la conexion BD
        SQLiteDatabase db = getWritableDatabase();

        long id = db.insert(UserTable.TABLE_NAME,null,values);//Devuelve el id del elemento que se inserto

        //Cerrarla conexion
        db.close();
    }
    /*
    * CREATE TABLE data   (id INTEGER PRIMARY KEY, event_id INTEGER, track_id INTEGER, value REAL);
CREATE UNIQUE INDEX data_idx ON data(event_id, track_id);*/

    public void ChangeColor(String name,String color)
    {
        if(existsService(name))
            updateService(name,color);
        else
            insertService(name,color);


    }
    void insertService(String name,String color)
    {
        //Objecto ContectValues PARCIAL 3
        ContentValues values = new ContentValues();//Funciona como sharedpreferences
        values.put(AspectActivityTable.COLUMN_NAME_ACTIVITY,name);
        values.put(AspectActivityTable.COLUMN_COLOR,color);

        //Establecer la conexion BD
        SQLiteDatabase db = getWritableDatabase();

        long id = db.insert(AspectActivityTable.TABLE_NAME,null,values);//Devuelve el id del elemento que se inserto

        //Cerrarla conexion
        db.close();
    }
    boolean existsService(String name)
    {

        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();
        String where = AspectActivityTable.COLUMN_NAME_ACTIVITY + " = " + name;
        //Cursor 3 parcial
        Cursor cursor = db.query(AspectActivityTable.TABLE_NAME, null,where,null,null,null,null);

        // Apuntamos el cursor al primer elemento
        if(cursor.moveToFirst())//Retorna falso si no encontro un resultado
        {
            result = true;
            cursor.close();
        }

        db.close();

        return result;
    }
    void updateService(String name,String color)
    {
        //Objecto ContectValues PARCIAL 3
        ContentValues values = new ContentValues();//Funciona como sharedpreferences
        values.put(AspectActivityTable.COLUMN_NAME_ACTIVITY,name);
        values.put(AspectActivityTable.COLUMN_COLOR,color);

        SQLiteDatabase db  = getWritableDatabase();

        String where =  AspectActivityTable.COLUMN_NAME_ACTIVITY + " = " + name;

        db.update(AspectActivityTable.TABLE_NAME,values,where,null);//Parametro null sirve para realizar un where muy largo

        db.close();
    }

}
