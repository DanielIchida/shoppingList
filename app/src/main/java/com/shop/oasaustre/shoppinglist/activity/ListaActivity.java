package com.shop.oasaustre.shoppinglist.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.ListDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.activity.task.TaskFactory;
import com.shop.oasaustre.shoppinglist.adapter.firebase.ListaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;

public class ListaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initializeUI();


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
    }
    @Override
    protected void onResume() {
        super.onResume();
        ITask task = TaskFactory.getInstance().createLoadListasTask(this, (App) this.getApplication());
        task.run();
    }
    @Override
    protected void onPause() {
        super.onPause();
        App app = (App) this.getApplication();
        if(app.isUserActive()){
            RecyclerView rvLista = (RecyclerView) this.findViewById(R.id.rv_listaList);
            ListaAdapter adapter = (ListaAdapter) rvLista.getAdapter();
            adapter.deactivateListener();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
