package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.ImageUtil.ImageUtil;
import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.ReadPath.ReadPathUtil;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdapaterHorario;
import com.mecanicosgruas.edu.mecanicosgruas.adaptadores.ListAdaptadorPantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.model.HorarioClass;
import com.mecanicosgruas.edu.mecanicosgruas.model.Servicio;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LUNA on 02/04/2018.
 */

public class FragmentCreateService extends Fragment implements PantallaInicio.DataReceivedListener {
    List<HorarioClass> listaHorario = new ArrayList<>();
    ListAdapaterHorario adapter;
    PantallaInicio myActivity;
    View myview;
    ListView listViewHorario;
    Button btnGuardar;
    EditText editTxtNameCreateService;
    EditText editTxtDescCreateService;
    ImageButton imgBtnBackgroundCreateService;
    EditText editTxtCiudadCreateService;
    EditText editTxtColoniaCreateService;
    EditText editTxtCalleCreateService;
    EditText editTextNumCreateService;
    Bitmap imgServicePort;
    CircleImageView imgViewService;
    ImageView imgViewServicePort;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_create_service,container,false);
        //Get View
        btnGuardar = (Button)myview.findViewById(R.id.btnGuardarCreateService);
        listViewHorario = (ListView)myview.findViewById(R.id.idListViewHorarios);
        editTxtNameCreateService = (EditText)myview.findViewById(R.id.editTxtNameServiceCreateService);
        editTxtDescCreateService = (EditText)myview.findViewById(R.id.editTxtDescCreateService);
        imgBtnBackgroundCreateService = (ImageButton)myview.findViewById(R.id.imgBtnBackgroundCreateService);
        editTxtCiudadCreateService = (EditText)myview.findViewById(R.id.editTxtCuidadCreateService);
        editTxtColoniaCreateService = (EditText)myview.findViewById(R.id.editTxtColoniaCreateService);
        editTxtCalleCreateService = (EditText)myview.findViewById(R.id.editTxtCalleCreateService);
        editTextNumCreateService = (EditText)myview.findViewById(R.id.editTxtNumCreateService);

        imgViewServicePort  = (ImageView)myview.findViewById(R.id.imgBackgroundServiceCreateService);
        return myview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myActivity = (PantallaInicio) getActivity();
        myActivity.setDataReceivedListener(this);
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
                    //validar
                    publicarServicio();
                    btnGuardar.setText("Editar servicio");
                    setEnabledView(false);
                }


                imgBtnBackgroundCreateService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ApiManager.ImageSelect(myActivity);
                    }
                });



            }
        });
    }
    private void setEnabledView(boolean enabledView)
    {
        //listViewHorario.setEnabled(enabledView);
        editTxtNameCreateService.setEnabled(enabledView);
        editTxtDescCreateService.setEnabled(enabledView);
        imgBtnBackgroundCreateService.setEnabled(enabledView);
        editTxtCiudadCreateService.setEnabled(enabledView);
        editTxtColoniaCreateService.setEnabled(enabledView);
        editTxtCalleCreateService.setEnabled(enabledView);
        editTextNumCreateService.setEnabled(enabledView);
    }

    void InflarListaDiasSemana()
    {



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

    @Override
    public void onReceived(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && ApiManager.PHOTO_SHOT == requestCode)
        {
            // Guardamos el thumbnail de la imagen en un archivo con el siguiente nombre
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                imgServicePort  = bitmap;
                imgViewServicePort.setImageBitmap(imgServicePort);


            // mImageView.setImageBitmap(bitmap);
        } else if(resultCode == RESULT_OK && ApiManager.STORAGE_IMAGE == requestCode)
        {
            Uri uri = data.getData();
            String pathImage = ReadPathUtil.getRealPathFromURI_API19(myActivity,uri);

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(pathImage,bmOptions);

                imgServicePort  = bitmap;
                imgViewServicePort.setImageBitmap(imgServicePort);


            Log.i("Path Sotrage",pathImage);

        }
    }

    private boolean ValidarDatos()
    {
        if(
            editTxtNameCreateService.getText().toString().trim().isEmpty() ||
            editTxtDescCreateService.getText().toString().trim().isEmpty() ||
            editTxtCiudadCreateService.getText().toString().trim().isEmpty() ||
            editTxtColoniaCreateService.getText().toString().trim().isEmpty() ||
            editTxtCalleCreateService.getText().toString().trim().isEmpty() ||
            editTextNumCreateService.getText().toString().trim().isEmpty() ||
            imgServicePort == null
        )
        {
            Toast.makeText(getContext(),"Falta llenar campos",Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }
    public void publicarServicio()
    {
        if(!ValidarDatos())
            return;

        //Normalizar nombre
        String nombre = editTxtNameCreateService.getText().toString();
        String descService = editTxtDescCreateService.getText().toString();
        nombre = Normalizer.normalize(nombre, Normalizer.Form.NFD);
        nombre = nombre.replaceAll("[^\\p{ASCII}]", "");
        descService = Normalizer.normalize(descService, Normalizer.Form.NFD);
        descService = descService.replaceAll("[^\\p{ASCII}]", "");

        Servicio s = new Servicio
                (
                  nombre,
                  descService,
                  editTxtCiudadCreateService.getText().toString(),
                  editTxtColoniaCreateService.getText().toString(),
                  editTxtCalleCreateService.getText().toString(),
                  editTextNumCreateService.getText().toString()
                );
        JSONObject jsonHorario = new JSONObject();
        try
        {
            if(!((HorarioClass)adapter.getItem(0)).getHorarioInicial().equals("Cerrado") && !((HorarioClass)adapter.getItem(0)).equals("Cerrado"))
            {
                jsonHorario.put("lun",listaHorario.get(0).getHorarioInicial() + ApiManager.SPLIT_CODE + listaHorario.get(0).getHorarioFinal());
            }

            if(!listaHorario.get(1).getHorarioInicial().equals("Cerrado") && !listaHorario.get(1).getHorarioFinal().equals("Cerrado"))
            {
                jsonHorario.put("mar",listaHorario.get(1).getHorarioInicial() + ApiManager.SPLIT_CODE + listaHorario.get(1).getHorarioFinal());
            }

            if(!listaHorario.get(2).getHorarioInicial().equals("Cerrado") && !listaHorario.get(2).getHorarioFinal().equals("Cerrado"))
            {
                jsonHorario.put("mie",listaHorario.get(2).getHorarioInicial() + ApiManager.SPLIT_CODE + listaHorario.get(2).getHorarioFinal());
            }

            if(!listaHorario.get(3).getHorarioInicial().equals("Cerrado") && !listaHorario.get(3).getHorarioFinal().equals("Cerrado"))
            {
                jsonHorario.put("jue",listaHorario.get(3).getHorarioInicial() + ApiManager.SPLIT_CODE + listaHorario.get(3).getHorarioFinal());
            }

            if(!listaHorario.get(4).getHorarioInicial().equals("Cerrado") && !listaHorario.get(4).getHorarioFinal().equals("Cerrado"))
            {
                jsonHorario.put("vie",listaHorario.get(4).getHorarioInicial() + ApiManager.SPLIT_CODE + listaHorario.get(4).getHorarioFinal());
            }

            if(!listaHorario.get(5).getHorarioInicial().equals("Cerrado") && !listaHorario.get(5).getHorarioFinal().equals("Cerrado"))
            {
                jsonHorario.put("sab",listaHorario.get(5).getHorarioInicial() + ApiManager.SPLIT_CODE + listaHorario.get(5).getHorarioFinal());
            }
            if(!listaHorario.get(6).getHorarioInicial().equals("Cerrado") && !listaHorario.get(6).getHorarioFinal().equals("Cerrado"))
            {
                jsonHorario.put("dom",listaHorario.get(6).getHorarioInicial() + ApiManager.SPLIT_CODE + listaHorario.get(6).getHorarioFinal());
            }

            ManagerREST.PublicService(s,jsonHorario, ImageUtil.encodeBase64(imgServicePort),myActivity);

        } catch(JSONException ex)
        {
            ex.printStackTrace();
        }

    }

}
