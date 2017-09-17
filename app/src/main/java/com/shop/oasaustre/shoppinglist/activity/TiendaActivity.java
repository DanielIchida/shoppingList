package com.shop.oasaustre.shoppinglist.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.TiendaDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.activity.task.LoadTiendasTask;
import com.shop.oasaustre.shoppinglist.activity.task.TaskFactory;
import com.shop.oasaustre.shoppinglist.adapter.firebase.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.adapter.firebase.TiendaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;

public class TiendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
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

    }


    private void initializeUI(){
        TextView btnNewTienda = (TextView) findViewById(R.id.btnNewTienda);
        btnNewTienda.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                createTienda();
            }
        });
    }

    private void createTienda(){
        TiendaDialog dialog = new TiendaDialog();
        dialog.show(getSupportFragmentManager(), "Nueva Tienda");
    }


    /***** EVENTOS DEL CICLO DE VIDA *****/

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        ITask task = TaskFactory.getInstance().createLoadTiendasTask(this,(App) this.getApplication());
        task.run();
    }
    @Override
    protected void onPause() {
        super.onPause();
        App app = (App) this.getApplication();
        if(app.isUserActive()){
            RecyclerView rvTienda = (RecyclerView) this.findViewById(R.id.rv_tiendaList);
            TiendaAdapter adapter = (TiendaAdapter) rvTienda.getAdapter();
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
