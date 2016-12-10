package com.shop.oasaustre.shoppinglist.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.UpdateCategoryTask;
import com.shop.oasaustre.shoppinglist.activity.task.UpdateTiendaTask;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;

public class TiendaSaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_save);

        initializeUI();

        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();


    }

    private void initializeUI(){

        TextView btnGuardar = (TextView) findViewById(R.id.btnGuardar);
        TextView btnCancelar = (TextView) findViewById(R.id.btnCancelar);

        EditText fieldTiendaTxt = (EditText) this.findViewById(R.id.fieldTiendaTxt);
        TextView fieldIdTienda = (TextView) this.findViewById(R.id.fieldIdTienda);

        fieldTiendaTxt.setText(getIntent().getStringExtra(AppConstant.TITLE_INTENT));
        fieldIdTienda.setText(getIntent().getExtras().get(AppConstant.ID_INTENT).toString());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTienda();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }



    private void updateTienda(){
        Tienda tienda = null;

        EditText fieldTiendaTxt = (EditText) this.findViewById(R.id.fieldTiendaTxt);
        TextView fieldIdTienda = (TextView) this.findViewById(R.id.fieldIdTienda);

        tienda = new Tienda();

        tienda.setNombre(fieldTiendaTxt.getText().toString());
        tienda.setId(new Long(fieldIdTienda.getText().toString()));

        UpdateTiendaTask task = new UpdateTiendaTask(this);
        task.execute(tienda);

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
