package com.mecanicosgruas.edu.mecanicosgruas.SQLITE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import com.mecanicosgruas.edu.mecanicosgruas.model.ColorAcivity;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;

import java.util.ArrayList;
import java.util.List;

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
        //values.put(UserTable.COLUMN_COLOR,""+color);

        //Establecer la conexion BD
        SQLiteDatabase db = getWritableDatabase();

        long id = db.insert(UserTable.TABLE_NAME,null,values);//Devuelve el id del elemento que se inserto

        //Cerrarla conexion
        db.close();
    }
    /*
    * CREATE TABLE data   (id INTEGER PRIMARY KEY, event_id INTEGER, track_id INTEGER, value REAL);
CREATE UNIQUE INDEX data_idx ON data(event_id, track_id);*/
    public void TruncTableUser(){
        //Establecer la conexion BD
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + UserTable.TABLE_NAME );
        //result = db.delete(UserTable.TABLE_NAME,null,null) ;
        db.close();
    }
    public void TruncTableService(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + ServiceTable.TABLE_NAME + ";");
        //db.delete(ServiceTable.TABLE_NAME,null,null);
        db.close();
    }
    public void InsertTableUser(User user){
        ContentValues values = new ContentValues();
        values.put(UserTable.COLUMN_NAME,user.getName());
        values.put(UserTable.COLUMN_LASTNAME,user.getLast_name());
        values.put(UserTable.COLUMN_NICKNAME,user.getNickname());
        values.put(UserTable.COLUMN_PASSWORD,user.getPassword());
        values.put(UserTable.COLUMN_EMAIL,user.getEmail());
        values.put(UserTable.COLUMN_TELEFONO,user.getTelefono());
        values.put(UserTable.COLUMN_ENDPOINT_IMG,user.getEndPointImagePerfil());
        values.put(UserTable.COLUMN_ENDPOINT_IMG_PORT,user.getEndPointImageBackground());
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(UserTable.TABLE_NAME,null,values);
        db.close();
    }
    public void UpdateTableuser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserTable.COLUMN_NAME,user.getName());
        values.put(UserTable.COLUMN_LASTNAME,user.getLast_name());
        values.put(UserTable.COLUMN_NICKNAME,user.getNickname());
        values.put(UserTable.COLUMN_PASSWORD,user.getPassword());
        values.put(UserTable.COLUMN_EMAIL,user.getEmail());
        values.put(UserTable.COLUMN_TELEFONO,user.getTelefono());
        values.put(UserTable.COLUMN_ENDPOINT_IMG,user.getEndPointImagePerfil());
        values.put(UserTable.COLUMN_ENDPOINT_IMG_PORT,user.getEndPointImageBackground());

        String where = UserTable.COLUMN_NICKNAME + " = '" + user.getNickname() + "'";
        SQLiteDatabase db = getWritableDatabase();

        db.update(UserTable.TABLE_NAME,values,where,null);//Parametro null sirve para realizar un where muy largo

        db.close();
    }
    public void InsertTableService(Servicio service) {
        ContentValues values = new ContentValues();
        values.put(ServiceTable.COLUMN_NAME,service.getNombreServicio());
        values.put(ServiceTable.COLUMN_DESC_SERVICE,service.getDescService());
        values.put(ServiceTable.COLUMN_ENDPOINT_IMG,service.getEndpointImageBackground());
        values.put(ServiceTable.COLUMN_CIUDAD,service.getCiudad());
        values.put(ServiceTable.COLUMN_COLONIA,service.getColonia());
        values.put(ServiceTable.COLUMN_CALLE,service.getCalle());
        values.put(ServiceTable.COLUMN_NUM,service.getNum());
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(ServiceTable.TABLE_NAME,null,values);
        db.close();
    }
    public void UpdateTableService(Servicio service) {
        ContentValues values = new ContentValues();
        values.put(ServiceTable.COLUMN_NAME,service.getNombreServicio());
        values.put(ServiceTable.COLUMN_DESC_SERVICE,service.getDescService());
        values.put(ServiceTable.COLUMN_ENDPOINT_IMG,service.getEndpointImageBackground());
        values.put(ServiceTable.COLUMN_CIUDAD,service.getCiudad());
        values.put(ServiceTable.COLUMN_COLONIA,service.getColonia());
        values.put(ServiceTable.COLUMN_CALLE,service.getCalle());
        values.put(ServiceTable.COLUMN_NUM,service.getNum());
        String where = ServiceTable.COLUMN_NAME + " = '" + service.getNombreServicio() + "'";
        SQLiteDatabase db = getWritableDatabase();
        db.update(ServiceTable.TABLE_NAME,values,where,null);
        db.close();
    }
    public User SelectTableUser() {
        User user = null;
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + UserTable.TABLE_NAME,null);//Permite utilizar la sintaxis de sql completo



            while(cursor.moveToNext())//Obtiene la posicion anterior al ultimo
            {
                user = new User();
                //int idContact = cursor.getInt( cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString( cursor.getColumnIndex(UserTable.COLUMN_NAME));
                String last_name = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_LASTNAME));
                String nickname = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_NICKNAME));
                String password = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_PASSWORD));
                String email = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_EMAIL));
                String telefono = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_TELEFONO));
                String endpoint_img = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_ENDPOINT_IMG));
                String endpoint_back_img = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_ENDPOINT_IMG_PORT));

                user.setName(name);
                user.setLast_name(last_name);
                user.setNickname(nickname);
                user.setPassword(password);
                user.setEmail(email);
                user.setTelefono(telefono);
                user.setEndPointImagePerfil(endpoint_img);
                user.setEndPointImageBackground(endpoint_back_img);
            }
            cursor.close();


        db.close();

        return user;
    }
    public Servicio SelectTableServicio() {
        Servicio servicio = null;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ServiceTable.TABLE_NAME,null);//Permite utilizar la sintaxis de sql completo



            while(cursor.moveToNext())//Obtiene la posicion anterior al ultimo
            {
                servicio = new Servicio();
                String name =  cursor.getString( cursor.getColumnIndex(ServiceTable.COLUMN_NAME));
                String desc_service = cursor.getString(cursor.getColumnIndex(ServiceTable.COLUMN_DESC_SERVICE));
                String endpointimg = cursor.getString(cursor.getColumnIndex(ServiceTable.COLUMN_ENDPOINT_IMG));
                String ciudad = cursor.getString(cursor.getColumnIndex(ServiceTable.COLUMN_CIUDAD));
                String colonia = cursor.getString(cursor.getColumnIndex(ServiceTable.COLUMN_COLONIA));
                String calle = cursor.getString(cursor.getColumnIndex(ServiceTable.COLUMN_CALLE));
                String num = cursor.getString(cursor.getColumnIndex(ServiceTable.COLUMN_NUM));

                servicio.setNombreServicio(name);
                servicio.setDescService(desc_service);
                servicio.setEndpointImageUser(endpointimg);
                servicio.setCiudad(ciudad);
                servicio.setColonia(colonia);
                servicio.setCalle(calle);
                servicio.setNum(num);
            }
            cursor.close();


        db.close();

        return servicio;
    }
    public void TruncTableTableServiceDisplay() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + ServiceDisplayTable.TABLE_NAME );
        db.close();
    }
    public void InsertTableServiceDisplay(Servicio service) {
        ContentValues values = new ContentValues();
        values.put(ServiceDisplayTable.COLUMN_NAME,service.getNombreServicio());
        values.put(ServiceDisplayTable.COLUMN_CIUDAD,service.getCiudad());
        values.put(ServiceDisplayTable.COLUMN_TELEFONO,service.getTelefono());
        values.put(ServiceDisplayTable.COLUMN_ENDPOINT_IMG,service.getEndpointImageBackground());
        values.put(ServiceDisplayTable.COLUMN_NUM_STARTS,Float.toString(service.getNumStars()));

        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(ServiceDisplayTable.TABLE_NAME,null,values);
        db.close();
    }
    public List<Servicio> SelectServiciosDisplay() {
        List<Servicio>list_servicio = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ServiceDisplayTable.TABLE_NAME,null);//Permite utilizar la sintaxis de sql completo



        while(cursor.moveToNext())//Obtiene la posicion anterior al ultimo
        {
            Servicio servicio = new Servicio();
            String name =  cursor.getString( cursor.getColumnIndex(ServiceDisplayTable.COLUMN_NAME));
            String ciudad = cursor.getString(cursor.getColumnIndex(ServiceDisplayTable.COLUMN_CIUDAD));
            String telefono = cursor.getString(cursor.getColumnIndex(ServiceDisplayTable.COLUMN_TELEFONO));
            String endpointimg = cursor.getString(cursor.getColumnIndex(ServiceDisplayTable.COLUMN_ENDPOINT_IMG));
            String num_stars = cursor.getString(cursor.getColumnIndex(ServiceDisplayTable.COLUMN_NUM_STARTS));

            servicio.setNombreServicio(name);
            servicio.setCiudad(ciudad);
            servicio.setTelefono(telefono);
            servicio.setEndpointImageBackground(endpointimg);
            servicio.setNumStars(Float.parseFloat(num_stars));

            list_servicio.add(servicio);

        }
        cursor.close();


        db.close();
        return list_servicio;
    }
/*
    public void ChangeColor(String name,String color)
    {
        //insertService(name,color);

        if(existsService(name))
            updateService(name,color);
        else
            insertService(name,color);


    }
    */
    /*
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
    */
    /*
    boolean existsService(String name)
    {
        boolean result = false;


        SQLiteDatabase db = getWritableDatabase();
        String where = AspectActivityTable.COLUMN_NAME_ACTIVITY + " = " + "'" +name+"'";
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
    */
/*
    void updateService(String name,String color)
    {
        //Objecto ContectValues PARCIAL 3
        ContentValues values = new ContentValues();//Funciona como sharedpreferences
        //values.put(AspectActivityTable.COLUMN_NAME_ACTIVITY,name);
        values.put(AspectActivityTable.COLUMN_COLOR,color);

        SQLiteDatabase db  = getWritableDatabase();

        String where =  AspectActivityTable.COLUMN_NAME_ACTIVITY + " = " + "'" + name + "'";

        db.update(AspectActivityTable.TABLE_NAME,values,where,null);//Parametro null sirve para realizar un where muy largo

        db.close();
    }
    */
/*
   public ColorAcivity GetColorActivity(String name)
    {
        ColorAcivity colorAcivity = null;

        SQLiteDatabase db = getWritableDatabase();
        String where = AspectActivityTable.COLUMN_NAME_ACTIVITY + " = " + "'"+name+"'";
        //Cursor 3 parcial
        Cursor cursor = db.query(AspectActivityTable.TABLE_NAME, null,where,null,null,null,null);

        // Apuntamos el cursor al primer elemento
        if(cursor.moveToFirst())//Retorna falso si no encontro un resultado
        {
            String name_view = cursor.getString( cursor.getColumnIndex(AspectActivityTable.COLUMN_NAME_ACTIVITY));
            String color = cursor.getString( cursor.getColumnIndex(AspectActivityTable.COLUMN_COLOR));
            colorAcivity = new ColorAcivity(name_view,color);
            cursor.close();
        }

        db.close();


        return colorAcivity;
    }
    */
/*
    public List<ColorAcivity> getAllColor()
    {
        List<ColorAcivity> contactList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + AspectActivityTable.TABLE_NAME,null);//Permite utilizar la sintaxis de sql completo

        if(cursor.moveToFirst())//Retorna falso si no encontro un resultado
        {
            while(!cursor.isAfterLast())//Obtiene la posicion anterior al ultimo
            {
                //int idContact = cursor.getInt( cursor.getColumnIndex(COLUMN_ID));
                String nameActivity = cursor.getString( cursor.getColumnIndex(AspectActivityTable.COLUMN_NAME_ACTIVITY));
                String color = cursor.getString( cursor.getColumnIndex(AspectActivityTable.COLUMN_COLOR));

                ColorAcivity contact = new ColorAcivity(
                        nameActivity,
                        color
                );
                contactList.add(contact);
                cursor.moveToNext();
            }
            cursor.close();
        }

        db.close();

        return  contactList;
    }
    */

}
