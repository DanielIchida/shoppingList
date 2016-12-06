package com.shop.oasaustre.shoppinglist.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;

public class ArticleSaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_save);

        ArrayAdapter<String> adapterText = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, new String[]{"kg","g","pieza","bolsa","pack","l","docena"});

        Spinner spinner = (Spinner) findViewById(R.id.fieldUnit);

        spinner.setAdapter(adapterText);

        initializeUI();

        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();


    }

    private void initializeUI(){

        TextView btnGuardar = (TextView) findViewById(R.id.btnGuardar);
        TextView btnCancelar = (TextView) findViewById(R.id.btnCancelar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateArticleInShoppingList();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView btnPlus = (ImageView) findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUnit(AppConstant.OP_PLUS);
            }
        });

        ImageView btnMinus = (ImageView) findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUnit(AppConstant.OP_MINUS);
            }
        });

    }


    private void updateUnit(Integer operation){
        Integer cantidad = null;
        EditText fieldCantidad = (EditText) findViewById(R.id.fieldCantidad);


        if(fieldCantidad.getText() != null && !fieldCantidad.getText().toString().equals("")){
            cantidad = new Integer(fieldCantidad.getText().toString());
            if(operation == AppConstant.OP_PLUS){
                cantidad+=1;
            }else if(operation == AppConstant.OP_MINUS && cantidad > 1){
                cantidad-=1;
            }
        }else{
            if(operation == AppConstant.OP_PLUS){
                cantidad = 1;
            }
        }

        if(cantidad != null){
            fieldCantidad.setText(cantidad.toString());
        }

    }

    private void updateArticleInShoppingList(){
        Categoria categoria = null;
        Tienda tienda = null;

        EditText fieldBarcode = (EditText) this.findViewById(R.id.fieldBarcode);
        EditText fieldArticulo = (EditText) this.findViewById(R.id.fieldArticulo);
        EditText fieldCantidad = (EditText) this.findViewById(R.id.fieldCantidad);
        Spinner fieldCategory = (Spinner) this.findViewById(R.id.fieldCategory);
        Spinner fieldTienda = (Spinner) this.findViewById(R.id.fieldTienda);
        EditText fieldPrice = (EditText) this.findViewById(R.id.fieldPrice);
        EditText fieldNotes = (EditText) this.findViewById(R.id.fieldNotes);
        TextView fieldIdListaCompra = (TextView) this.findViewById(R.id.fieldIdLIstaCompra);
        TextView fieldIdArticulo = (TextView) this.findViewById(R.id.fieldIdArticulo);

        ListaCompra listaCompra = new ListaCompra();
        Articulo articulo = new Articulo();
        Lista lista = ((App) getApplication()).getListaActive();

        listaCompra.setId(new Long(fieldIdListaCompra.getText().toString()));



        articulo.setId(new Long(fieldIdArticulo.getText().toString()));

        if(fieldBarcode.getText() != null){
            articulo.setCodigoBarras(fieldBarcode.getText().toString());
        }

        if(fieldArticulo.getText() != null){
            articulo.setNombre(fieldArticulo.getText().toString());
        }

        if(fieldCantidad.getText() != null && !fieldCantidad.getText().toString().equals(AppConstant.BLANK)){
            listaCompra.setUnidades(new Long(fieldCantidad.getText().toString()));
        }else{
            listaCompra.setUnidades(null);
        }

        if(fieldPrice.getText() != null && !fieldPrice.getText().toString().equals(AppConstant.BLANK)){
            listaCompra.setPrecio(new Double(fieldPrice.getText().toString()));
        }else{
            listaCompra.setPrecio(null);
        }

        if(fieldNotes.getText() != null && !fieldNotes.getText().toString().equals(AppConstant.BLANK)){
            listaCompra.setNotas(fieldNotes.getText().toString());
        }else{
            listaCompra.setNotas(null);
        }

        listaCompra.setArticulo(articulo);
        listaCompra.setLista(lista);

        categoria = (Categoria) fieldCategory.getSelectedItem();

        if(categoria != null && categoria.getId() != AppConstant.ID_DEFAULT){
            listaCompra.setCategoria(categoria);
        }

        tienda = (Tienda) fieldTienda.getSelectedItem();

        if(tienda != null && tienda.getId() != AppConstant.ID_DEFAULT){
            listaCompra.setTienda(tienda);
        }

        UpdateArticleDetailTask updateArticleDetailTask = new UpdateArticleDetailTask(this);
        updateArticleDetailTask.execute(listaCompra);

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

        Long idListaCompra = (Long) getIntent().getExtras().get("idListaCompra");

        ArticleDetailTask articleDetailTask = new ArticleDetailTask(this);
        articleDetailTask.execute(idListaCompra);


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
