package com.mecanicosgruas.edu.mecanicosgruas.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.model.HorarioClass;

import java.util.List;

/**
 * Created by LUNA on 02/04/2018.
 */

public class ListAdapaterHorario extends BaseAdapter {
        List<HorarioClass> listHorario;

    public ListAdapaterHorario(List<HorarioClass> listHorario) {
        this.listHorario = listHorario;
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
        HorarioClass horario = listHorario.get(position);
        txtViewDiaSemana.setText(horario.getDiaSemana());

        return viewTemp;
    }
}
