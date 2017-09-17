package com.shop.oasaustre.shoppinglist.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.activity.task.TaskFactory;
import com.shop.oasaustre.shoppinglist.activity.task.UpdateListaTask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;

public class ListaSaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_save);

        initializeUI();

    }

    private void initializeUI(){

        TextView btnGuardar = (TextView) findViewById(R.id.btnGuardar);
        TextView btnCancelar = (TextView) findViewById(R.id.btnCancelar);

        EditText fieldListaTxt = (EditText) this.findViewById(R.id.fieldListaTxt);
        TextView fieldIdLista = (TextView) this.findViewById(R.id.fieldIdLista);
        TextView fieldActivo = (TextView) this.findViewById(R.id.fieldActivo);
        TextView fieldFecha = (TextView) this.findViewById(R.id.fieldFecha);

        fieldListaTxt.setText(getIntent().getStringExtra(AppConstant.TITLE_INTENT));
        fieldIdLista.setText(getIntent().getExtras().get(AppConstant.ID_INTENT).toString());
        fieldActivo.setText(getIntent().getExtras().get(AppConstant.ACTIVE_INTENT).toString());
        fieldFecha.setText(getIntent().getExtras().get(AppConstant.FECHA_INTENT).toString());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App app = (App) ListaSaveActivity.this.getApplication();
                if(app.isUserActive()){
                    updateListaFB();
                }else{
                    updateLista();
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



    private void updateLista(){
        Lista lista = null;

        EditText fieldListaTxt = (EditText) this.findViewById(R.id.fieldListaTxt);
        TextView fieldIdLista = (TextView) this.findViewById(R.id.fieldIdLista);
        TextView fieldActivo = (TextView) this.findViewById(R.id.fieldActivo);
        TextView fieldFecha = (TextView) this.findViewById(R.id.fieldFecha);

        lista = new Lista();

        lista.setNombre(fieldListaTxt.getText().toString());
        lista.setId(new Long(fieldIdLista.getText().toString()));
        lista.setActivo(new Long(fieldActivo.getText().toString()));
        lista.setFecha(new Long(fieldFecha.getText().toString()));

        ITask task = TaskFactory.getInstance().createUpdateListaTask(this,(App) this.getApplication());
        task.run(lista);
        ;

    }


    private void updateListaFB(){
        ListaDto listaDto = null;

        EditText fieldListaTxt = (EditText) this.findViewById(R.id.fieldListaTxt);
        TextView fieldIdLista = (TextView) this.findViewById(R.id.fieldIdLista);
        TextView fieldActivo = (TextView) this.findViewById(R.id.fieldActivo);
        TextView fieldFecha = (TextView) this.findViewById(R.id.fieldFecha);

        listaDto = new ListaDto();

        listaDto.setNombre(fieldListaTxt.getText().toString());
        listaDto.setUid(fieldIdLista.getText().toString());
        listaDto.setFecha(new Long(fieldFecha.getText().toString()));

        ITask task = TaskFactory.getInstance().createUpdateListaTask(this,(App) this.getApplication());
        task.run(listaDto);


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
