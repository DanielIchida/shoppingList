package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.ListaCompraService;
import com.shop.oasaustre.shoppinglist.dto.ArticuloDetalleDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaCompraFBDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.TiendaDto;

/**
 * Created by oasaustre on 4/12/16.
 */

public class ArticleDetailTask implements ITask{

    private Activity activity = null;

    private final static String SHOPPING_LIST = "lista_compra";

    public ArticleDetailTask(Activity activity){
        this.activity = activity;
    }

    protected void onPostExecute(ListaCompraFBDto listaCompra) {
        Articulo articulo = null;
        Tienda tienda = null;
        Categoria categoria = null;
        ArrayAdapter<CategoriaDto> categoriaAdapter = null;
        ArrayAdapter<TiendaDto> tiendaAdapter = null;
        int posItemSelected;

        App app = (App) activity.getApplication();


        EditText fieldBarcode = (EditText) activity.findViewById(R.id.fieldBarcode);
        EditText fieldArticulo = (EditText) activity.findViewById(R.id.fieldArticulo);
        EditText fieldCantidad = (EditText) activity.findViewById(R.id.fieldCantidad);
        Spinner fieldCategory = (Spinner) activity.findViewById(R.id.fieldCategory);
        Spinner fieldTienda = (Spinner) activity.findViewById(R.id.fieldTienda);
        EditText fieldPrice = (EditText) activity.findViewById(R.id.fieldPrice);
        EditText fieldNotes = (EditText) activity.findViewById(R.id.fieldNotes);
        TextView fieldIdListaCompra = (TextView) activity.findViewById(R.id.fieldIdLIstaCompra);
        TextView fieldIdArticulo = (TextView) activity.findViewById(R.id.fieldIdArticulo);


        if(listaCompra.getArticulo() != null){
            fieldBarcode.setText((String)listaCompra.getArticulo().get("barcode"));
            fieldArticulo.setText((String)listaCompra.getArticulo().get("articleName"));
            fieldIdArticulo.setText((String)listaCompra.getArticulo().get("idArticle"));

        }


        if(listaCompra != null){
            fieldCantidad.setText((listaCompra.getUnidades()!=null)?listaCompra.getUnidades().toString():AppConstant.BLANK);
            fieldPrice.setText((listaCompra.getPrecio()!=null)?listaCompra.getPrecio().toString():AppConstant.BLANK);
            fieldNotes.setText(listaCompra.getNotas());
            fieldIdListaCompra.setText(listaCompra.getUid());
        }

        categoriaAdapter = new ArrayAdapter<CategoriaDto>(activity,
                android.R.layout.simple_spinner_dropdown_item,
                app.getListaCategoriaDto());

        fieldCategory.setAdapter(categoriaAdapter);


        if(listaCompra.getCategoria() != null){
            CategoriaDto categoriaDto = new CategoriaDto();

            categoriaDto.setUid((String) listaCompra.getCategoria().get("idCategory"));
            categoriaDto.setNombre((String) listaCompra.getCategoria().get("categoryName"));

            posItemSelected = categoriaAdapter.getPosition(categoriaDto);
            fieldCategory.setSelection(posItemSelected);
        }else{
            fieldCategory.setSelection(AppConstant.POSITION_DEFAULT);
        }

        tiendaAdapter = new ArrayAdapter<TiendaDto>(activity,
                android.R.layout.simple_spinner_dropdown_item,
                app.getListaTiendaDto());

        fieldTienda.setAdapter(tiendaAdapter);



        if(listaCompra.getCategoria() != null){
            TiendaDto tiendaDto = new TiendaDto();
            tiendaDto.setUid((String) listaCompra.getTienda().get("idTienda"));
            tiendaDto.setNombre((String) listaCompra.getTienda().get("tiendaName"));

            posItemSelected = tiendaAdapter.getPosition(tiendaDto);
            fieldTienda.setSelection(posItemSelected);
        }else{
            fieldTienda.setSelection(AppConstant.POSITION_DEFAULT);
        }





    }

    @Override
    public void run(Object... params) {
        App app = (App) activity.getApplication();
        DatabaseReference reference = app.getDatabase().getReference().child(SHOPPING_LIST).child((String) params[0]);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ListaCompraFBDto listaCompra = dataSnapshot.getValue(ListaCompraFBDto.class);
                    onPostExecute(listaCompra);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
