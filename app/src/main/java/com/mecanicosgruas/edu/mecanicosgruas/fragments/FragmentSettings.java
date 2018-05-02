package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.R;

/**
 * Created by LUNA on 03/04/2018.
 */

public class FragmentSettings extends Fragment {
    View myFragmentView;
    Activity myActivity;
    //Variables
    Button btnEdit;
    EditText editTxtNombre;
    EditText editTxtApellido;
    TextView txtViewEmail;
    ImageButton btnImgBackground;
    ImageButton btnImgPerfilUser;
    EditText editTxtTelefono;
    Spinner spinnerLenguaje;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_settings,container,false);
        myActivity = getActivity();
        return myFragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Almacenar los datos del usuario
        String name="Usuario";
        String apelllido = "Contraseña";
        String telefono = "12345678";

        //Get view
        btnEdit = (Button) myFragmentView.findViewById(R.id.btnEditCustom);
        editTxtNombre = (EditText)myFragmentView.findViewById(R.id.editTxtNameCustom);
        editTxtApellido = (EditText)myFragmentView.findViewById(R.id.editTxtApellidoCustom);
        txtViewEmail = (TextView)myFragmentView.findViewById(R.id.txtViewEmailCustom);
        btnImgBackground = (ImageButton)myFragmentView.findViewById(R.id.imgBtnBackgroundUserCustom);
        btnImgPerfilUser = (ImageButton)myFragmentView.findViewById(R.id.imgBtnUserCustom);
        editTxtTelefono = (EditText)myFragmentView.findViewById(R.id.editTxtTelefonoCustom);
        spinnerLenguaje = (Spinner)myFragmentView.findViewById(R.id.spinnerLenguajeCustom);

        editTxtNombre.setText(name.toString());
        editTxtApellido.setText(apelllido.toString());
        editTxtTelefono.setText(telefono.toString());
        txtViewEmail.setText("example@example.example");

        setEnabledView(false);

        BtnEventEdit();

    }
    private void setEnabledView(boolean enabledView)
    {
        editTxtNombre.setEnabled(enabledView);
        editTxtApellido.setEnabled(enabledView);
        btnImgBackground.setEnabled(enabledView);
        btnImgPerfilUser.setEnabled(enabledView);
        editTxtTelefono.setEnabled(enabledView);
        spinnerLenguaje.setEnabled(enabledView);
    }
    private void BtnEventEdit()
    {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnEdit.getText().toString().equals("Editar"))
                {
                    btnEdit.setText("Guardar");
                    setEnabledView(true);
                }
                else
                {
                    if(validateSettings())
                        return;
                    btnEdit.setText("Editar");
                    setEnabledView(false);
                }
            }
        });
    }
    private boolean validateSettings()
    {
        if(
        editTxtNombre.getText().toString().trim().isEmpty() ||
        editTxtApellido.getText().toString().trim().isEmpty() ||
        editTxtTelefono.getText().toString().trim().isEmpty()
        )
        {
            Toast.makeText(myActivity.getApplicationContext(),"Campo Vacio",Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(myActivity.getApplicationContext(),"Campo lleno",Toast.LENGTH_LONG).show();
        return false;
    }
}
