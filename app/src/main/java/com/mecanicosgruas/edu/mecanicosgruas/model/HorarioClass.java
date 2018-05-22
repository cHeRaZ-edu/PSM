package com.mecanicosgruas.edu.mecanicosgruas.model;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;

/**
 * Created by LUNA on 02/04/2018.
 */

public class HorarioClass {
    private String diaSemana;
    private String horarioInicial;
    private String horarioFinal;

    public HorarioClass() {
    }
    public HorarioClass(String diaSemana) {
        this.diaSemana = diaSemana;
        this.horarioInicial = "Cerrado";
        this.horarioFinal = "Cerrado";
    }

    public HorarioClass(String diaSemana, String horarioInicial, String horarioFinal) {
        this.diaSemana = diaSemana;
        this.horarioInicial = horarioInicial;
        this.horarioFinal = horarioFinal;
    }

    public HorarioClass(String diaSemana,String split)
    {
        this.diaSemana = diaSemana;
        String[] arrayHorario = split.split(ApiManager.SPLIT_CODE);
        setHorarioInicial(arrayHorario[0]);
        setHorarioFinal(arrayHorario[1]);
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHorarioInicial() {
        return horarioInicial;
    }

    public void setHorarioInicial(String horarioInicial) {
        this.horarioInicial = horarioInicial;
    }

    public String getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(String horarioFinal) {
        this.horarioFinal = horarioFinal;
    }
}
