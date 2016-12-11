package com.shop.oasaustre.shoppinglist.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.CategoriaDialog;
import com.shop.oasaustre.shoppinglist.activity.task.LoadCategoriesTask;

public class CategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
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
        TextView btnNewCategory = (TextView) findViewById(R.id.btnNewCategory);
        btnNewCategory.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                createCategoria();
            }
        });
    }

    private void createCategoria(){
        CategoriaDialog dialog = new CategoriaDialog();
        dialog.show(getSupportFragmentManager(), "Nueva Categoría");
    }


    /***** EVENTOS DEL CICLO DE VIDA *****/

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        LoadCategoriesTask loadCategoriesTask = new LoadCategoriesTask(this);
        loadCategoriesTask.execute();
    }
    @Override
    protected void onPause() {
        super.onPause();
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
