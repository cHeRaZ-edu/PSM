package com.mecanicosgruas.edu.mecanicosgruas.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;


public class ListAdaptadorPantallaInicio extends BaseAdapter{
    private List<Servicio> ListService;
    private Context context;

    public ListAdaptadorPantallaInicio(List<Servicio> listService,Context context) {
        ListService = listService;
        this.context = context;
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
        Servicio s = ListService.get(position);
        //Check if exist
        if(viewTemp == null)
        {
            //Inflate xml to view
            viewTemp = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_layout_item,null);
            ImageView imgViewService = (ImageView) viewTemp.findViewById(R.id.imgService);
            Picasso.with(context)
                    .load( ManagerREST.getURI() + s.getEndpointImageBackground())
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit()
                    .into(imgViewService);
        }
        RatingBar stars = (RatingBar)viewTemp.findViewById(R.id.ratingBarStar);
        TextView txtViewName = (TextView) viewTemp.findViewById(R.id.txtViewNameService);
        TextView txtViewCiudad = (TextView) viewTemp.findViewById(R.id.txtVewCiudad);
        TextView txtViewTelefono = (TextView) viewTemp.findViewById(R.id.txtViewNum);



        String nameService = "Servicio: " + s.getNombreServicio();
        String nameCiudad = "Ciudad: " + s.getCiudad();
        String numTelefono = "Telefono: " + s.getTelefono();
        txtViewName.setText(nameService);
        txtViewCiudad.setText(nameCiudad);
        txtViewTelefono.setText(numTelefono);

        stars.setRating((s.getNumStars()/(float)ApiManager.COUNT_FAVORITO)*ApiManager.COUNT_STARS);

        return viewTemp;
    }
}
