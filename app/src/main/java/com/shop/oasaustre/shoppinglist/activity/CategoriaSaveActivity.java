package com.shop.oasaustre.shoppinglist.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.activity.task.TaskFactory;
import com.shop.oasaustre.shoppinglist.activity.task.UpdateCategoryTask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;

public class CategoriaSaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_save);

        initializeUI();



    }

    private void initializeUI(){

        TextView btnGuardar = (TextView) findViewById(R.id.btnGuardar);
        TextView btnCancelar = (TextView) findViewById(R.id.btnCancelar);

        EditText fieldCategoriaTxt = (EditText) this.findViewById(R.id.fieldCategoriaTxt);
        TextView fieldIdCategoria = (TextView) this.findViewById(R.id.fieldIdCategoria);

        fieldCategoriaTxt.setText(getIntent().getStringExtra(AppConstant.TITLE_INTENT));
        fieldIdCategoria.setText(getIntent().getExtras().get(AppConstant.ID_INTENT).toString());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App app = (App) CategoriaSaveActivity.this.getApplication();
                if(app.isUserActive()){
                    updateCategoriaFB();
                }else{
                    updateCategoria();
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



    private void updateCategoria(){
        Categoria categoria = null;

        EditText fieldCategoriaTxt = (EditText) this.findViewById(R.id.fieldCategoriaTxt);
        TextView fieldIdCategoria = (TextView) this.findViewById(R.id.fieldIdCategoria);

        categoria = new Categoria();

        categoria.setNombre(fieldCategoriaTxt.getText().toString());
        categoria.setId(new Long(fieldIdCategoria.getText().toString()));

        ITask task = TaskFactory.getInstance().createUpdateCategoryTask(this, (App) this.getApplication());
        task.run(categoria);

    }

    private void updateCategoriaFB(){
        CategoriaDto categoriaDto = null;

        EditText fieldCategoriaTxt = (EditText) this.findViewById(R.id.fieldCategoriaTxt);
        TextView fieldIdCategoria = (TextView) this.findViewById(R.id.fieldIdCategoria);

        categoriaDto = new CategoriaDto();

        categoriaDto.setNombre(fieldCategoriaTxt.getText().toString());
        categoriaDto.setUid(fieldIdCategoria.getText().toString());

        ITask task = TaskFactory.getInstance().createUpdateCategoryTask(this, (App) this.getApplication());
        task.run(categoriaDto);


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
