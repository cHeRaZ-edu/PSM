package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdapaterHorario;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdaptadorPantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.HorarioClass;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LUNA on 02/04/2018.
 */

public class FragmentCreateService extends Fragment {
    Activity myactivity;
    View myview;
    ListView listViewHorario;
    Button btnGuardar;
    EditText editTxtNameCreateService;
    EditText editTxtDescCreateService;
    ImageButton imgBtnUserCreateService;
    ImageButton imgBtnBackgroundCreateService;
    EditText editTxtCiudadCreateService;
    EditText editTxtColoniaCreateService;
    EditText editTxtCalleCreateService;
    EditText editTextNumCreateService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_create_service,container,false);

        return myview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myactivity = getActivity();
        //Get View
        btnGuardar = (Button)myview.findViewById(R.id.btnGuardarCreateService);
        listViewHorario = (ListView)myview.findViewById(R.id.idListViewHorarios);
        editTxtNameCreateService = (EditText)myview.findViewById(R.id.editTxtNameServiceCreateService);
        editTxtDescCreateService = (EditText)myview.findViewById(R.id.editTxtDescCreateService);
        imgBtnUserCreateService = (ImageButton)myview.findViewById(R.id.imgBtnUserCreateService);
        imgBtnBackgroundCreateService = (ImageButton)myactivity.findViewById(R.id.imgBtnBackgroundCreateService);
        editTxtCiudadCreateService = (EditText)myactivity.findViewById(R.id.editTxtCuidadCreateService);
        editTxtColoniaCreateService = (EditText)myactivity.findViewById(R.id.editTxtColoniaCreateService);
        editTxtCalleCreateService = (EditText)myactivity.findViewById(R.id.editTxtCalleCreateService);
        editTextNumCreateService = (EditText)myactivity.findViewById(R.id.editTxtNumCreateService);
        InflarListaDiasSemana();
        setEnabledView(false);
        btnEventEdit();
        /*
        spinner = (Spinner)view.findViewById(R.id.spinnerHorario);
        array_hours = Resources.getSystem().getStringArray(R.array.string_array_hours);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, array_hours);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        */

    }
    private void btnEventEdit()
    {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnGuardar.getText().toString().equals("Editar servicio"))
                {
                    btnGuardar.setText("Crear Servicio");
                    setEnabledView(true);
                }
                else
                {
                    btnGuardar.setText("Editar servicio");
                    setEnabledView(false);
                }
            }
        });
    }
    private void setEnabledView(boolean enabledView)
    {
        listViewHorario.setEnabled(enabledView);
        editTxtNameCreateService.setEnabled(enabledView);
        editTxtDescCreateService.setEnabled(enabledView);
        imgBtnUserCreateService.setEnabled(enabledView);
        imgBtnBackgroundCreateService.setEnabled(enabledView);
        editTxtCiudadCreateService.setEnabled(enabledView);
        editTxtColoniaCreateService.setEnabled(enabledView);
        editTxtCalleCreateService.setEnabled(enabledView);
        editTxtNameCreateService.setEnabled(enabledView);
    }

    void InflarListaDiasSemana()
    {
        List<HorarioClass> listaHorario = new ArrayList<>();
        ListAdapaterHorario adapter;

        listaHorario.add(new HorarioClass("Lunes"));
        listaHorario.add(new HorarioClass("Martes"));
        listaHorario.add(new HorarioClass("Miercoles"));
        listaHorario.add(new HorarioClass("Jueves"));
        listaHorario.add(new HorarioClass("Viernes"));
        listaHorario.add(new HorarioClass("Sabado"));
        listaHorario.add(new HorarioClass("Domingo"));

        adapter = new ListAdapaterHorario(listaHorario);

        listViewHorario.setAdapter(adapter);
    }

}
