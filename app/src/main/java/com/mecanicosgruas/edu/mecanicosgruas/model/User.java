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

    public User(String nickname,String name, String apellido, String email, String password, String telefono) {
        this.nickname = nickname;
        this.name = name;
        this.last_name = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
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

    public String getApellido() {
        return last_name;
    }

    public void setApellido(String apellido) {
        this.last_name = apellido;
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

}
