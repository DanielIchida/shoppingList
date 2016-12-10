package com.shop.oasaustre.shoppinglist.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.ArticleDetailTask;
import com.shop.oasaustre.shoppinglist.activity.task.UpdateArticleDetailTask;
import com.shop.oasaustre.shoppinglist.activity.task.UpdateCategoryTask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;

public class CategoriaSaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_save);

        initializeUI();

        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();


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
                updateCategoria();
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

        UpdateCategoryTask task = new UpdateCategoryTask(this);
        task.execute(categoria);

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
