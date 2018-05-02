package com.mecanicosgruas.edu.mecanicosgruas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdapterHorarioDisplay;
import com.mecanicosgruas.edu.mecanicosgruas.model.HorarioClass;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ServicioDisplayActivity extends AppCompatActivity {
    private ListView list_viewHorario;
    private Servicio servicioSelect;
    private RatingBar ratingBar;
    private TextView txtViewServiceName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_display);
        list_viewHorario = (ListView)findViewById(R.id.listViewDisplay_horario);
        ratingBar = (RatingBar)findViewById(R.id.ratingBarServiceDisplay);
        txtViewServiceName = (TextView)findViewById(R.id.txtViewNameServiceDisplay);
        Intent intent = getIntent();
        servicioSelect = intent.getExtras().getParcelable("serviceSelect");
        ratingBar.setRating(servicioSelect.getNumStars());
        txtViewServiceName.setText(servicioSelect.getNombreServicio());
        iniflateListViewServicios();

    }
    private void iniflateListViewServicios()
    {
        List<HorarioClass> listaHorario = new ArrayList<>();
        ListAdapterHorarioDisplay adapter;

        listaHorario.add(new HorarioClass("Martes","13:00","18:00"));
        listaHorario.add(new HorarioClass("Miercoles","13:00","18:00"));
        listaHorario.add(new HorarioClass("Jueves","13:00","18:00"));
        listaHorario.add(new HorarioClass("Viernes","12:00","16:00"));

        adapter = new ListAdapterHorarioDisplay(listaHorario);

        list_viewHorario.setAdapter(adapter);
    }
}


