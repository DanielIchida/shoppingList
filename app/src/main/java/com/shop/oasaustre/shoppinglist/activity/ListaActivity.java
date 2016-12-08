package com.shop.oasaustre.shoppinglist.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.ListDialog;
import com.shop.oasaustre.shoppinglist.activity.dialog.TiendaDialog;
import com.shop.oasaustre.shoppinglist.activity.task.LoadListasTask;
import com.shop.oasaustre.shoppinglist.activity.task.LoadTiendasTask;

public class ListaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.btn_star));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initializeUI();

        LoadListasTask loadListasTask = new LoadListasTask(this);
        loadListasTask.execute();
    }

    private void initializeUI(){
        TextView btnNewLista = (TextView) findViewById(R.id.btnNewLista);
        btnNewLista.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                createLista();
            }
        });
    }

    private void createLista(){
        ListDialog dialog = new ListDialog();
        dialog.setActivo(false);
        dialog.show(getSupportFragmentManager(), "Nueva Lista");
    }


    /***** EVENTOS DEL CICLO DE VIDA *****/

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

}
