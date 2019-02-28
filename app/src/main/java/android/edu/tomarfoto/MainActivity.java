package android.edu.tomarfoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final int DRIVE=137;
    private static final int CAMARA=138;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tomarFotoDrive(View view) {

        Log.d("MIAPP", "QUIERO COGER UNA FOTO DEL DISPOSITIVO");
        Intent intentDrive = new Intent ();
        intentDrive.setAction(Intent.ACTION_PICK);
        intentDrive.setType("image/*");//TIPO MIME
        startActivityForResult(intentDrive, DRIVE);
    }
    public void tomarFotoCamara(View view) {
        Log.d("MIAPP", "QUIERO HACER UNA FOTO CON LA CAMARA");
        Intent intentCamara = new Intent ();
        intentCamara.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intentCamara.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentCamara, CAMARA);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==CAMARA)//viene de mi petición de tirar mi foto
        {
            switch (resultCode)
            {
                case RESULT_OK:
                    Log.d("MIAPP", "Tiró la foto bien");
                    try {
                         Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                        //Log.d("MIAPP", "URI = " +data.getData().toString());
                        ImageView im = findViewById(R.id.imagen);
                        im.setImageBitmap(thumbnail);
                    }catch (Throwable t)
                    {
                        Log.e("MIAPP", "ERROR AL SETEAR LA FOTO", t);
                    }
                    break;

                case RESULT_CANCELED:Log.d("MIAPP", "Canceló la foto");
                    break;

            }
        } else if (requestCode==DRIVE)
        {
            switch (resultCode)
            {
                case RESULT_OK:Log.d("MIAPP", "Seleccionó foto ok");
                    Uri uri = data.getData();

                    try {
                        Bitmap  bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ImageView imageView =findViewById(R.id.imagen);
                        imageView.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case RESULT_CANCELED:Log.d("MIAPP", "Canceló la foto");
                    break;

            }
        }

    }


}
