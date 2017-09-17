package com.shop.oasaustre.shoppinglist.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.activity.task.TaskFactory;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.dto.firebase.TiendaDto;

public class TiendaSaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_save);

        initializeUI();

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
                App app = (App) TiendaSaveActivity.this.getApplication();
                if(app.isUserActive()){
                    updateTiendaFB();
                }else{
                    updateTienda();
                }
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

        ITask task = TaskFactory.getInstance().createUpdateTiendaTask(this, (App) this.getApplication());
        task.run(tienda);

    }

    private void updateTiendaFB(){
        TiendaDto tiendaDto = null;

        EditText fieldTiendaTxt = (EditText) this.findViewById(R.id.fieldTiendaTxt);
        TextView fieldIdTienda = (TextView) this.findViewById(R.id.fieldIdTienda);

        tiendaDto = new TiendaDto();

        tiendaDto.setNombre(fieldTiendaTxt.getText().toString());
        tiendaDto.setUid(fieldIdTienda.getText().toString());

        ITask task = TaskFactory.getInstance().createUpdateTiendaTask(this, (App) this.getApplication());
        task.run(tiendaDto);

    }


    /***** EVENTOS DEL CICLO DE VIDA *****/

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
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
