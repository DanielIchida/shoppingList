package com.shop.oasaustre.shoppinglist.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.ArticleDetailTask;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.activity.task.TaskFactory;
import com.shop.oasaustre.shoppinglist.activity.task.UpdateArticleDetailTask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaCompraFBDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.TiendaDto;

import java.util.HashMap;
import java.util.Map;

public class ArticleSaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_save);

        initializeUI();

    }

    private void initializeUI(){

        TextView btnGuardar = (TextView) findViewById(R.id.btnGuardar);
        TextView btnCancelar = (TextView) findViewById(R.id.btnCancelar);
        TextView txtCurrency = (TextView) findViewById(R.id.titleCurrency);

        final App app = (App)this.getApplication();

        txtCurrency.setText(app.getSettings().getCurrency());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(app.isUserActive()){
                    updateArticleInShoppingListFB();
                }else{
                    updateArticleInShoppingList();
                }

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

        ITask task = TaskFactory.getInstance().createUpdateArticleDetailTask(this,(App) this.getApplication());
        task.run(listaCompra);
    }


    private void updateArticleInShoppingListFB(){
        CategoriaDto categoriaDto = null;
        TiendaDto tiendaDto = null;

        EditText fieldBarcode = (EditText) this.findViewById(R.id.fieldBarcode);
        EditText fieldArticulo = (EditText) this.findViewById(R.id.fieldArticulo);
        EditText fieldCantidad = (EditText) this.findViewById(R.id.fieldCantidad);
        Spinner fieldCategory = (Spinner) this.findViewById(R.id.fieldCategory);
        Spinner fieldTienda = (Spinner) this.findViewById(R.id.fieldTienda);
        EditText fieldPrice = (EditText) this.findViewById(R.id.fieldPrice);
        EditText fieldNotes = (EditText) this.findViewById(R.id.fieldNotes);
        TextView fieldIdListaCompra = (TextView) this.findViewById(R.id.fieldIdLIstaCompra);
        TextView fieldIdArticulo = (TextView) this.findViewById(R.id.fieldIdArticulo);

        ListaCompraFBDto listaCompra = new ListaCompraFBDto();
        Map<String,Object> articulo = new HashMap<String,Object>();
        Map<String,Object> lista = new HashMap<String,Object>();
        Map<String,Object> categoria = new HashMap<String,Object>();
        Map<String,Object> tienda = new HashMap<String,Object>();

        ListaDto listaDtoActive = ((App) getApplication()).getListaaFBActive();

        listaCompra.setUid(fieldIdListaCompra.getText().toString());



        articulo.put("idArticle",fieldIdArticulo.getText().toString());


        if(fieldBarcode.getText() != null){
            articulo.put("barcode",fieldBarcode.getText().toString());
        }

        if(fieldArticulo.getText() != null){
            articulo.put("articleName",fieldArticulo.getText().toString());
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

        lista.put("idLista", listaDtoActive.getUid());
        lista.put("listaName", listaDtoActive.getNombre());

        listaCompra.setArticulo(articulo);
        listaCompra.setLista(lista);

        categoriaDto = (CategoriaDto) fieldCategory.getSelectedItem();

        if(categoriaDto != null){
            categoria.put("idCategory",categoriaDto.getUid());
            categoria.put("categoryName",categoriaDto.getNombre());
            listaCompra.setCategoria(categoria);
        }else{
            listaCompra.setCategoria(null);
        }

        tiendaDto = (TiendaDto) fieldTienda.getSelectedItem();

        if(tiendaDto != null){
            tienda.put("idShop",tiendaDto.getUid());
            tienda.put("shopName",tiendaDto.getNombre());
            listaCompra.setTienda(tienda);
        }
        ITask task = TaskFactory.getInstance().createUpdateArticleDetailTask(this,(App) this.getApplication());
        task.run(listaCompra);

    }


    /***** EVENTOS DEL CICLO DE VIDA *****/

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        App app = (App)this.getApplication();

        ITask task = TaskFactory.getInstance().createArticleDetailTask(this,app);

        if(app.isUserActive()){
            String idListaCompra = (String) getIntent().getExtras().get("idListaCompra");
            task.run(idListaCompra);
        }else{
            Long idListaCompra = (Long) getIntent().getExtras().get("idListaCompra");
            task.run(idListaCompra);
        }



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
