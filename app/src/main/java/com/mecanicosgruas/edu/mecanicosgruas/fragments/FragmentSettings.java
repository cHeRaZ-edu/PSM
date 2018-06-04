package com.mecanicosgruas.edu.mecanicosgruas.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mecanicosgruas.edu.mecanicosgruas.ApiManager.ApiManager;
import com.mecanicosgruas.edu.mecanicosgruas.ImageUtil.ImageUtil;
import com.mecanicosgruas.edu.mecanicosgruas.PantallaInicio;
import com.mecanicosgruas.edu.mecanicosgruas.R;
import com.mecanicosgruas.edu.mecanicosgruas.ReadPath.ReadPathUtil;
import com.mecanicosgruas.edu.mecanicosgruas.SQLITE.ManagerBD;
import com.mecanicosgruas.edu.mecanicosgruas.WebServices.Connection.ManagerREST;
import com.mecanicosgruas.edu.mecanicosgruas.model.ColorAcivity;
import com.mecanicosgruas.edu.mecanicosgruas.model.User;

import java.text.Normalizer;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LUNA on 03/04/2018.
 */

public class FragmentSettings extends Fragment implements PantallaInicio.DataReceivedListener  {
    View myFragmentView;
    PantallaInicio myActivity;

    CircleImageView imgViewImagePerfil;
    ImageView imgViewImageBackground;
    //Variables
    TextView nickcname;
    Button btnEdit;
    EditText editTxtNombre;
    EditText editTxtApellido;
    TextView txtViewEmail;
    ImageButton btnImgBackground;
    ImageButton btnImgPerfilUser;
    EditText editTxtTelefono;
    Spinner spinnerLenguaje;
    Button btnColor;
    Bitmap imgPerfil;
    Bitmap imgPortada;
    boolean imageSelect;
    String encodeBase64Portada;
    String encodeBase64Img;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_settings,container,false);
        myActivity = (PantallaInicio) getActivity();
        myActivity.setDataReceivedListener(this);

        ColorAcivity colorAcivity =  new ManagerBD(getContext()).GetColorActivity(ApiManager.SETTINGS_FRGAMENT);
        if(colorAcivity!=null)
        {
            RelativeLayout layout = myFragmentView.findViewById(R.id.id_fragment);
            int color  = colorAcivity.parseColor();
            layout.setBackgroundColor(color);
        }

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
        nickcname = (TextView)myFragmentView.findViewById(R.id.txtViewNickname);
        btnEdit = (Button) myFragmentView.findViewById(R.id.btnEditCustom);
        editTxtNombre = (EditText)myFragmentView.findViewById(R.id.editTxtNameCustom);
        editTxtApellido = (EditText)myFragmentView.findViewById(R.id.editTxtApellidoCustom);
        txtViewEmail = (TextView)myFragmentView.findViewById(R.id.txtViewEmailCustom);
        btnImgBackground = (ImageButton)myFragmentView.findViewById(R.id.imgBtnBackgroundUserCustom);
        btnImgPerfilUser = (ImageButton)myFragmentView.findViewById(R.id.imgBtnUserCustom);
        editTxtTelefono = (EditText)myFragmentView.findViewById(R.id.editTxtTelefonoCustom);
        spinnerLenguaje = (Spinner)myFragmentView.findViewById(R.id.spinnerLenguajeCustom);

        imgViewImagePerfil  = (CircleImageView)myFragmentView.findViewById(R.id.imgViewUserEdit);
        imgViewImageBackground  =(ImageView)myFragmentView.findViewById(R.id.imgViewBackgroundEdit);
        btnColor = (Button)myFragmentView.findViewById(R.id.idButtonColor);


        editTxtNombre.setText(name.toString());
        editTxtApellido.setText(apelllido.toString());
        editTxtTelefono.setText(telefono.toString());
        txtViewEmail.setText("example@example.example");

        setEnabledView(false);

        BtnEventEdit();

        UpdatePerfil();

        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.changeFragment(new FragmentChangeColor(),"color_fragment");
            }
        });

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

                    String nameUser = editTxtNombre.getText().toString();
                    String apellidoUser = editTxtApellido.getText().toString();
                    nameUser = Normalizer.normalize(nameUser, Normalizer.Form.NFD);
                    nameUser = nameUser.replaceAll("[^\\p{ASCII}]", "");
                    apellidoUser = Normalizer.normalize(apellidoUser, Normalizer.Form.NFD);
                    apellidoUser = apellidoUser.replaceAll("[^\\p{ASCII}]", "");
                    User usuario = new User(
                            ApiManager.getUser().getNickname(),
                            nameUser,
                            apellidoUser,
                            ApiManager.getUser().getEmail(),
                            "",
                            editTxtTelefono.getText().toString()
                    );

                    if(ApiManager.isInternetConnection(getContext()))
                    ManagerREST.UpdateUser(usuario, encodeBase64Img,encodeBase64Portada,myActivity,myActivity);

                    btnEdit.setText("Editar");
                    setEnabledView(false);
                }
            }
        });

        btnImgPerfilUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSelect= true;
                ApiManager.ImageSelect(myActivity);
            }
        });

        btnImgBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSelect = false;
                ApiManager.ImageSelect(myActivity);
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
        //Toast.makeText(myActivity.getApplicationContext(),"Campo lleno",Toast.LENGTH_LONG).show();
        return false;
    }

    void UpdatePerfil()
    {
        if(ApiManager.getUser()!=null)
        {
            nickcname.setText(ApiManager.getUser().getNickname());
            editTxtNombre.setText(ApiManager.getUser().getName());
            editTxtApellido.setText(ApiManager.getUser().getLast_name());
            txtViewEmail.setText(ApiManager.getUser().getEmail());
            editTxtTelefono.setText(ApiManager.getUser().getTelefono());

            imgPerfil = ((BitmapDrawable)myActivity.imagViewPerfil.getDrawable()).getBitmap();
            imgPortada = ((BitmapDrawable)myActivity.imgViewBackground.getDrawable()).getBitmap();
            imgViewImagePerfil.setImageBitmap(imgPerfil);
            imgViewImageBackground.setImageBitmap(imgPortada);
        }
    }

    @Override
    public void onReceived(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && ApiManager.PHOTO_SHOT == requestCode)
        {

            // Guardamos el thumbnail de la imagen en un archivo con el siguiente nombre
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            if(imageSelect)
            {
                imgPerfil = bitmap;
                imgViewImagePerfil.setImageBitmap(imgPerfil);


                        if(bitmap!=null)
                        encodeBase64Img = ImageUtil.encodeBase64(bitmap);

            }
            else
            {
                imgPortada  = bitmap;
                imgViewImageBackground.setImageBitmap(imgPortada);

                    if(bitmap!=null)
                    encodeBase64Portada =ImageUtil.encodeBase64(bitmap);

            }

            // mImageView.setImageBitmap(bitmap);
        } else if(resultCode == RESULT_OK && ApiManager.STORAGE_IMAGE == requestCode)
        {

            Uri uri = data.getData();
            String pathImage = ReadPathUtil.getRealPathFromURI_API19(myActivity,uri);

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(pathImage,bmOptions);
            if(imageSelect)
            {
                imgPerfil = bitmap;
                imgViewImagePerfil.setImageBitmap(imgPerfil);

                    if(bitmap!=null)
                        encodeBase64Img = ImageUtil.encodeBase64(bitmap);

            }
            else
            {
                imgPortada  = bitmap;
                imgViewImageBackground.setImageBitmap(imgPortada);

                    if(bitmap!=null)
                        encodeBase64Portada = ImageUtil.encodeBase64(bitmap);

            }

            Log.i("Path Sotrage",pathImage);



        }
    }

    @Override
    public void onShutdown() {

    }
}
