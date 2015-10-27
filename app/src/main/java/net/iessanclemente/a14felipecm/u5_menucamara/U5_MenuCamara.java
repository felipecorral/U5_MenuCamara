package net.iessanclemente.a14felipecm.u5_menucamara;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import net.iessanclemente.a14felipecm.u5_menucamara_a14felipecm.R;

import java.io.File;

public class U5_MenuCamara extends AppCompatActivity {
    ImageView imagenCamara;
    TextView textLanzarCamara;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u5__menu_camara);
        //Tollbar que viene por defecto al crear blank activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imagenCamara = (ImageView) findViewById(R.id.imagev_foto);
        textLanzarCamara= (TextView) findViewById(R.id.text_view_camara);

        //Asociamos el menu contextual al textview
        registerForContextMenu(textLanzarCamara);

        if (savedInstanceState != null){
            Bitmap bitMap= (Bitmap) savedInstanceState.getParcelable("IMG");
            imagenCamara.setImageBitmap(bitMap);
            Log.v("ESTADO","onCreate de vuelta");
        }

        //Esto no hace falta en esta practica pero venia por defecto y se me dio por usarlo
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarCamara();
            }
        });
    }//Fin oncreate

    //metodo q llama a la camara
    public void llamarCamara(){
        Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intento, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode== RESULT_OK){
            Bitmap bitMap= (Bitmap) data.getExtras().get("data");
            imagenCamara.setImageBitmap(bitMap);
        }

    }//Fin

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        if (v.getId()==R.id.text_view_camara){
            inflater.inflate(R.menu.menu_contextual, menu);
        }
    }//Fin

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.ctx_menu_camara:
                llamarCamara();
                return true;
            case R.id.ctx_menu_salir:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_u5__menu_camara, menu);
        return true;
    }//Fin

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_camara:
                llamarCamara();
                return true;
            case R.id.action_salir:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Parcelable p_imagen = imagenCamara.getDrawingCache();
        savedInstanceState.putParcelable("IMG", p_imagen);
        super.onSaveInstanceState(savedInstanceState);
        Log.v("ESTADO","onSaveInstanceState");
    }//Fin

    /*
    * Recuperamos los datos del bundle antes guardado
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Bitmap bitMap= (Bitmap) savedInstanceState.getParcelable("IMG");
        imagenCamara.setImageBitmap(bitMap);
        Log.v("ESTADO","onRestoreInstanceState");
    }//Fin



}
