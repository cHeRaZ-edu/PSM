package com.mecanicosgruas.edu.mecanicosgruas.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LUNA on 28/03/2018.
 */

public class Servicio implements Parcelable{
    private String NombreServicio;
    private String Ciudad;
    private String Telefono;
    private float numStars;

    public Servicio() {
    }

    public Servicio(String nombreServicio, String ciudad, String telefono, float numStars) {
        NombreServicio = nombreServicio;
        Ciudad = ciudad;
        Telefono = telefono;
        this.numStars = numStars;
    }
    public Servicio(Parcel parcel)
    {
        NombreServicio = parcel.readString();
        Ciudad = parcel.readString();
        Telefono = parcel.readString();
        numStars = parcel.readFloat();
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
