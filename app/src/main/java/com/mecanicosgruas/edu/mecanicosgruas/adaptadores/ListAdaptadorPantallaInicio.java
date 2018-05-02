package com.mecanicosgruas.edu.mecanicosgruas.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;

import java.util.List;
import java.util.zip.Inflater;


public class ListAdaptadorPantallaInicio extends BaseAdapter{
    private List<Servicio> ListService;

    public ListAdaptadorPantallaInicio(List<Servicio> listService) {
        ListService = listService;
    }

    public List<Servicio> getListService() {
        return ListService;
    }

    public void setListService(List<Servicio> listService) {
        ListService = listService;
    }

    @Override
    public int getCount() {
        return ListService.size();
    }

    @Override
    public Object getItem(int position) {
        return ListService.get(position);
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
                        .inflate(R.layout.list_layout_item,null);
        }

        RatingBar stars = (RatingBar)viewTemp.findViewById(R.id.ratingBarStar);
        TextView txtViewName = (TextView) viewTemp.findViewById(R.id.txtViewNameService);
        TextView txtViewCiudad = (TextView) viewTemp.findViewById(R.id.txtVewCiudad);
        TextView txtViewTelefono = (TextView) viewTemp.findViewById(R.id.txtViewNum);

        Servicio s = ListService.get(position);
        String nameService = "Servicio: " + s.getNombreServicio();
        String nameCiudad = "Ciudad: " + s.getCiudad();
        String numTelefono = "Telefono: " + s.getTelefono();
        txtViewName.setText(nameService);
        txtViewCiudad.setText(nameCiudad);
        txtViewTelefono.setText(numTelefono);
        stars.setRating(s.getNumStars());


        return viewTemp;
    }
}
