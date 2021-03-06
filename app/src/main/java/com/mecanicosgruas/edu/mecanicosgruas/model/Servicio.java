package com.mecanicosgruas.edu.mecanicosgruas.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LUNA on 28/03/2018.
 */

public class Servicio implements Parcelable{
    private String nickname;
    private String NombreServicio;
    private String descService;
    private String Ciudad;
    private String Colonia;
    private String Calle;
    private String Num;
    private String Telefono;
    private float numStars;

    private String endpointImageUser;
    private String endpointImageBackground;
    private List<HorarioClass> listHorario;
    public Servicio() {
    }

    public Servicio(String nombreServicio, String ciudad, String telefono, float numStars) {
        NombreServicio = nombreServicio;
        Ciudad = ciudad;
        Telefono = telefono;
        this.numStars = numStars;
    }

    public Servicio(String nombreServicio, String descService, String ciudad, String colonia, String calle, String num) {
        NombreServicio = nombreServicio;
        this.descService = descService;
        Ciudad = ciudad;
        Colonia = colonia;
        Calle = calle;
        Num = num;

    }

    public Servicio(String nickname, String nombreServicio, String ciudad, String telefono, float numStars, String endpointImageBackground) {
        this.nickname = nickname;
        NombreServicio = nombreServicio;
        Ciudad = ciudad;
        Telefono = telefono;
        this.numStars = numStars;
        this.endpointImageBackground = endpointImageBackground;
    }

    public Servicio(Parcel parcel)
    {
        NombreServicio = parcel.readString();
        Ciudad = parcel.readString();
        Telefono = parcel.readString();
        numStars = parcel.readFloat();
    }



    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<HorarioClass> getListHorario() {
        return listHorario;
    }

    public String getEndpointImageUser() {
        return endpointImageUser;
    }

    public void setEndpointImageUser(String endpointImageUser) {
        this.endpointImageUser = endpointImageUser;
    }

    public void setListHorario(List<HorarioClass> listHorario) {
        this.listHorario = listHorario;
    }

    public String getNombreServicio() {
        return NombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        NombreServicio = nombreServicio;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public float getNumStars() {
        return numStars;
    }

    public void setNumStars(float numStars) {
        this.numStars = numStars;
    }

    public String getDescService() {
        return descService;
    }

    public void setDescService(String descService) {
        this.descService = descService;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getEndpointImageBackground() {
        return endpointImageBackground;
    }

    public void setEndpointImageBackground(String endpointImageBackground) {
        this.endpointImageBackground = endpointImageBackground;
    }

    @Override
    public String toString() {
        return "NombreServicio='" + NombreServicio + '\'' +
                ", Ciudad='" + Ciudad + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", numStars=" + numStars;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //used when un-parceling our parcel (creating the object)
    public static final Parcelable.Creator<Servicio> CREATOR = new Parcelable.Creator<Servicio>(){

        @Override
        public Servicio createFromParcel(Parcel in) {
            return new Servicio(in);
        }

        @Override
        public Servicio[] newArray(int size) {
            return new Servicio[0];
        }
    };

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(getNombreServicio());
        out.writeString(getCiudad());
        out.writeString(getTelefono());
        out.writeFloat(getNumStars());

    }
}
