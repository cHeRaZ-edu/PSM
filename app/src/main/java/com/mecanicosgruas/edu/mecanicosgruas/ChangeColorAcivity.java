package com.mecanicosgruas.edu.mecanicosgruas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.StorageUtils.StorageUtils;
import com.mecanicosgruas.edu.mecanicosgruas.fragments.FragmentSettings;
import com.skydoves.colorpickerview.ColorListener;
import com.skydoves.colorpickerview.ColorPickerView;

public class ChangeColorAcivity extends AppCompatActivity {

    ColorPickerView colorPickerView;
    String color_string;
    Button btnSelectColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_color_acivity);

        btnSelectColor = (Button) findViewById(R.id.idBtnChangeColor);
        colorPickerView = (ColorPickerView) findViewById(R.id.colorPickerView);
        StorageUtils.InizilateSharedPrefernces(this);

        colorPickerView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(int color) {
                RelativeLayout layout = findViewById(R.id.idRelativeLayout);
                layout.setBackgroundColor(color);
                color_string = "#"+Integer.toHexString(color);
            }
        });

        btnSelectColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert shared prefenreces
                String key = ApiManager.AcivitySelectColor;
                StorageUtils.SetColorAcivity(key,color_string);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        setResult(RESULT_CANCELED);
    }
}
