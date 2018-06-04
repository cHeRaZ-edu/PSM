package com.mecanicosgruas.edu.mecanicosgruas.model;

import android.graphics.Color;

/**
 * Created by LUNA on 04/06/2018.
 */

public class ColorAcivity {
     private String color;
     private String name;

    public ColorAcivity(String name, String color) {
        this.color = color;
        this.name = name;
    }

    public ColorAcivity() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int parseColor()
    {
        return  Color.parseColor(color);
    }
}
