package com.mecanicosgruas.edu.mecanicosgruas.model;

/**
 * Created by LUNA on 05/04/2018.
 */

public class User {
    private String nickname;
    private String name;
    private String last_name;
    private String email;
    private String password;
    private String telefono;
    //Sotrage internal
    private String pathImagePerfil;
    private String pathImageBackground;
    private String path_last_GPS;
    //Storage form Server
    private String EndPointImagePerfil;
    private String EndPointImageBackground;
    private String EndPoint_last_GPS;

    public User(String nickname,String name, String apellido, String email, String password, String telefono) {
        this.nickname = nickname;
        this.name = name;
        this.last_name = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
    }

    public User(String nickname) {
        this.nickname = nickname;
    }

    public User(String nickname, String name, String last_name, String email, String telefono, String endPointImagePerfil, String endPointImageBackground, String endPoint_last_GPS) {
        this.nickname = nickname;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.telefono = telefono;
        EndPointImagePerfil = endPointImagePerfil;
        EndPointImageBackground = endPointImageBackground;
        EndPoint_last_GPS = endPoint_last_GPS;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPathImagePerfil() {
        return pathImagePerfil;
    }

    public void setPathImagePerfil(String pathImagePerfil) {
        this.pathImagePerfil = pathImagePerfil;
    }

    public String getPathImageBackground() {
        return pathImageBackground;
    }

    public void setPathImageBackground(String pathImageBackground) {
        this.pathImageBackground = pathImageBackground;
    }

    public String getEndPointImagePerfil() {
        return EndPointImagePerfil;
    }

    public void setEndPointImagePerfil(String endPointImagePerfil) {
        EndPointImagePerfil = endPointImagePerfil;
    }

    public String getEndPointImageBackground() {
        return EndPointImageBackground;
    }

    public void setEndPointImageBackground(String endPointImageBackground) {
        EndPointImageBackground = endPointImageBackground;
    }

    public String getPath_last_GPS() {
        return path_last_GPS;
    }

    public void setPath_last_GPS(String path_last_GPS) {
        this.path_last_GPS = path_last_GPS;
    }

    public String getEndPoint_last_GPS() {
        return EndPoint_last_GPS;
    }

    public void setEndPoint_last_GPS(String endPoint_last_GPS) {
        EndPoint_last_GPS = endPoint_last_GPS;
    }
}
