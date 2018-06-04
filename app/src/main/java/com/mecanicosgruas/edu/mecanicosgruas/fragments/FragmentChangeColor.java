package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.skydoves.colorpickerview.ColorListener;
import com.skydoves.colorpickerview.ColorPickerView;

/**
 * Created by LUNA on 04/06/2018.
 */

public class FragmentChangeColor extends Fragment {
ColorPickerView colorPickerView;
Spinner spinner;
View fragment;
Button btnChangeColor;
PantallaInicio activity;
String colorS;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_color,container,false);
        fragment = view;

        spinner = (Spinner)view.findViewById(R.id.spinner_activity);
        colorPickerView = (ColorPickerView)view.findViewById(R.id.colorPickerView);
        btnChangeColor = (Button)view.findViewById(R.id.idBtnChangeColor);
        activity = (PantallaInicio)getActivity();

        colorPickerView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(int color) {
                LinearLayout linearLayout = fragment.findViewById(R.id.idLinearlayout);
                linearLayout.setBackgroundColor(color);
                colorS = Integer.toBinaryString(color);
            }
        });

        btnChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameActivity = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                //insert bd sqlite
                activity.sqliteAdmin.ChangeColor(nameActivity,colorS);
                activity.changeFragment(new FragmentSettings(),"Settings");
            }
        });

        return view;
    }

}
