package com.example.appreporte.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appreporte.Interface.InterfaceAdd;
import com.example.appreporte.Model.Incidencia;
import com.example.appreporte.Presenter.AddIncidenciaPresenter;
import com.example.appreporte.Presenter.LoginPresenter;
import com.example.appreporte.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pusher.client.Pusher;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

public class IncidenciaActivity extends AppCompatActivity  implements InterfaceAdd {


    ImageView imglocation;
    TextView tvdireccion;
    EditText etdescription;
    ImageView imgFoto,imgcamera,imgSave,imgGaleri;
    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final short REQUEST_CODE = 6545;
    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private static final int COD_FOTO = 20;
    private StorageReference storageReference;
    File fileImagen;
    Uri urifoto;
    Bitmap bitmap;
    File originalFile;
    private String path,type_incidence_id,organization_id;
    android.app.AlertDialog.Builder builder1;
    AlertDialog alert1;
    private AddIncidenciaPresenter addPresenter;

    PusherOptions options = new PusherOptions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencia);


        imgFoto = (ImageView) findViewById(R.id.imgfoto);
        imgGaleri=(ImageView)findViewById(R.id.imggalery);
        imgcamera = (ImageView) findViewById(R.id.imgcamera);
        etdescription=(EditText)findViewById(R.id.etdescription);

        imgSave=(ImageView)findViewById(R.id.imgsave);
        imglocation=(ImageView)findViewById(R.id.imglocation);
        imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abriCamara();
            }
        });

        imgGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirgaleria();
            }
        });
        imgSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String description=etdescription.getText().toString();
                saveIncidence(description);
            }
        });

        if (solicitaPermisosVersionesSuperiores()){
        }
        type_incidence_id=getIntent().getStringExtra("id");
        organization_id=getIntent().getStringExtra("organization_id");

        storageReference = FirebaseStorage.getInstance().getReference();
        addPresenter= new AddIncidenciaPresenter(this,storageReference);




        Pusher pusher = new Pusher("82e50670bca8ae634294", options);
        Channel channel = pusher.subscribe("my-channel");
        pusher.connect();
        imglocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendpushhser();
            }
        });

//        channel.bind("my-event", new SubscriptionEventListener() {
//            @Override
//            public void onEvent(String channelName, String eventName, String data) {
//                System.out.println(data);
//            }
//        });

    }

    private void sendpushhser() {

        addPresenter.sendPusher();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveIncidence(String description) {
        SharedPreferences sharedPreferences2 = getSharedPreferences("USER", MODE_PRIVATE);
        String usuario_id =sharedPreferences2.getString("id", "");
        String lat_share =sharedPreferences2.getString("lat", "");
        String lon_share =sharedPreferences2.getString("lon", "");
        String curren_date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Incidencia obj = new Incidencia("",curren_date,type_incidence_id,"nueva incidencia",description,"mi casa we","",Integer.parseInt(usuario_id),"",Double.parseDouble(lat_share),Double.parseDouble(lon_share),organization_id);

        addPresenter.uploadPhoto(obj,urifoto);
    }

    private void abrirgaleria() {
        Intent intent=new Intent(Intent.ACTION_PICK,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);// 10

    }

    private void abriCamara() {

        File miFile=new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada=miFile.exists();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if(isCreada==false){
            isCreada=miFile.mkdirs();
            Toast.makeText(this, "La foto no fue creada", Toast.LENGTH_SHORT).show();
        }

        if(isCreada==true){
            //  Toast.makeText(this, "foto creada", Toast.LENGTH_SHORT).show();
            Long consecutivo= System.currentTimeMillis()/1000;
            String nombre=consecutivo.toString()+".jpg";
            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombre;//indicamos la ruta de almacenamiento

            fileImagen=new File(path);
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));

            //  uri=Uri.fromFile(fileImagen);
            //Toast.makeText(this, "esta version", Toast.LENGTH_SHORT).show();
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            {
                String authorities=getApplicationContext().getPackageName()+".provider";
                Uri imageUri= FileProvider.getUriForFile(getBaseContext(),authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                urifoto=imageUri;
                //  Toast.makeText(this, "menor version", Toast.LENGTH_SHORT).show();
            }else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
                // uri=imageUri;
            }
            startActivityForResult(intent,COD_FOTO);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            //TODO : ESTO SELECCIONA DE LA GALERIA
            case COD_SELECCIONA:
                // Uri miPath=data.getData();  data.getData().getLastPathSegment()
                if (data==null){
                    return;
                }
                urifoto=data.getData();
             //   txtrutasubio.setText(data.getDataString());
                imgFoto.setImageURI(urifoto);

                try {
                    bitmap= MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(),urifoto);
                    imgFoto.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case COD_FOTO:
                //TODO : ESTO PARA TOMAR FOTO
                MediaScannerConnection.scanFile(getBaseContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri2) {
                                Log.e("Path",""+path);
                              //  txtrutasubio.setText(path);
                                originalFile = new File(path);
                            }
                        });

                bitmap= BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);
                break;
            case RESULT_CANCELED:
                Toast.makeText(this, "se canceló la foto", Toast.LENGTH_SHORT).show();
                break;
        }
        if (bitmap==null){
            urifoto=null;
            Toast.makeText(this, "se canceló la foto", Toast.LENGTH_SHORT).show();
            return;

        }
        bitmap=redimensionarImagen(bitmap,600,800);

    }

    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto= altoNuevo/alto;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }
    }

//    private void checkSelfPermission() {
//
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    REQUEST_CODE);
//
//        } else {
//
//            executeDownload(ruta);
//
//        }
//    }
    private boolean solicitaPermisosVersionesSuperiores() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){//validamos si estamos en android menor a 6 para no buscar los permisos
            return true;
        }

        //validamos si los permisos ya fueron aceptados
        if((getBaseContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&getBaseContext().checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED){
            return true;
        }

        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)||(shouldShowRequestPermissionRationale(CAMERA)))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MIS_PERMISOS);
        }

        return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui
    }
    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(getBaseContext());
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==MIS_PERMISOS){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){//el dos representa los 2 permisos
                Toast.makeText(getApplicationContext(),"Permisos concedidos",Toast.LENGTH_SHORT);
                //imgFoto.setEnabled(true);
            }
        }else{
            solicitarPermisosManual();
        }

//        if (requestCode==REQUEST_CODE){
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                executeDownload(ruta);
//            } else {
//
//                Toast.makeText(this, "Please give permissions ", Toast.LENGTH_LONG).show();
//            }
        //    return;
//        }
    }
    private void solicitarPermisosManual() {
        //TODO es para otrad casaoas
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getBaseContext());//estamos en fragment
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getBaseContext().getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getBaseContext(),"Los permisos no fueron concedidos",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    @Override
    public void showLoading(boolean state) {

    }

    @Override
    public void onRequestSuccess(Incidencia object) {
        dialogoSuccess();
    }

    @Override
    public void onRequestError(Object object) {

    }

    @Override
    public void onFail(String message) {

    }

    @Override
    public Context getContext() {
        return this;
    }


    private  void  dialogoSuccess(){
        builder1 = new AlertDialog.Builder(getContext());
        Button btncerrrar;
        TextView tvestado;
        builder1.setTitle("Enviado");
        ImageView imgseguridad;
        View v = LayoutInflater.from(getContext()).inflate(R.layout.diaolog_message, null);
        btncerrrar=(Button)v.findViewById(R.id.btncerrardialogo2);

        btncerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert1.dismiss();
            }
        });

        builder1.setView(v);
        alert1  = builder1.create();
        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert1.show();
    }
}