package com.mecanicosgruas.edu.mecanicosgruas.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.model.HorarioClass;

import java.util.List;

/**
 * Created by LUNA on 02/04/2018.
 */

public class ListAdapaterHorario extends BaseAdapter {
        List<HorarioClass> listHorario;
        List<String> listHoras;

    public ListAdapaterHorario(List<HorarioClass> listHorario, List<String> listHoras) {
        this.listHorario = listHorario;
        this.listHoras = listHoras;
    }

    @Override
    public int getCount() {
        return listHorario.size();
    }

    @Override
    public Object getItem(int position) {
        return listHorario.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View viewTemp;
        viewTemp = view;
        //Check if exist
        if(viewTemp == null)
        {
            //Inflate xml to view
            viewTemp = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_item_horario,null);
        }
        TextView txtViewDiaSemana = viewTemp.findViewById(R.id.txtView_diaSemana);
        Spinner spinnerHoraIni = viewTemp.findViewById(R.id.spinnerHorarioIni);
        Spinner spinnerHoraFinal = viewTemp.findViewById(R.id.spinnerHorarioFinal);

        HorarioClass horario = listHorario.get(position);
        txtViewDiaSemana.setText(horario.getDiaSemana());

        spinnerHoraIni.setSelection(listHoras.indexOf(horario.getHorarioInicial()));
        spinnerHoraFinal.setSelection(listHoras.indexOf(horario.getHorarioFinal()));

        final int pos = position;

        //if(!horario.getHorarioFinal().equals(spinnerHoraFinal.getSelectedItem().toString()))
        horario.setHorarioFinal(spinnerHoraFinal.getSelectedItem().toString());
        spinnerHoraIni.setOnItemSelectedListener(null);
        spinnerHoraIni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner sp = adapterView.findViewById(R.id.spinnerHorarioIni);
                listHorario.get(pos).setHorarioInicial(sp.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerHoraFinal.setOnItemSelectedListener(null);
        spinnerHoraFinal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner sp = adapterView.findViewById(R.id.spinnerHorarioFinal);
                listHorario.get(pos).setHorarioFinal(sp.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return viewTemp;
    }
}
