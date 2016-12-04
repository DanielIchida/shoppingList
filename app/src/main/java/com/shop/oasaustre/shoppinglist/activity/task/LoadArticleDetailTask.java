package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.ListaCompraService;

/**
 * Created by oasaustre on 4/12/16.
 */

public class LoadArticleDetailTask extends AsyncTask<Long,Void,ListaCompra> {

    private Activity activity = null;

    public LoadArticleDetailTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected ListaCompra doInBackground(Long... params) {
        ListaCompraService listaCompraService = null;
        ListaCompra listaCompra = null;

        listaCompraService = new ListaCompraService((App) activity.getApplication());


        listaCompra = listaCompraService.findById(params[0]);
        if(listaCompra != null){
            listaCompra.getCategoria();
            listaCompra.getLista();
            listaCompra.getArticulo();
            listaCompra.getTienda();
        }

        return listaCompra;
    }

    @Override
    protected void onPostExecute(ListaCompra listaCompra) {
        Articulo articulo = null;
        Tienda tienda = null;
        Categoria categoria = null;


        EditText fieldBarcode = (EditText) activity.findViewById(R.id.fieldBarcode);
        EditText fieldArticulo = (EditText) activity.findViewById(R.id.fieldArticulo);
        EditText fieldCantidad = (EditText) activity.findViewById(R.id.fieldCantidad);
        Spinner fieldCategory = (Spinner) activity.findViewById(R.id.fieldCategory);
        Spinner fieldTienda = (Spinner) activity.findViewById(R.id.fieldTienda);
        EditText fieldPrice = (EditText) activity.findViewById(R.id.fieldPrice);
        EditText fieldNotes = (EditText) activity.findViewById(R.id.fieldNotes);
        TextView fieldIdLIstaCompra = (TextView) activity.findViewById(R.id.fieldIdLIstaCompra);


        articulo = listaCompra.getArticulo();

        if(articulo != null){
            fieldBarcode.setText(articulo.getCodigoBarras());
            fieldArticulo.setText(articulo.getNombre());
        }

        if(listaCompra != null){
            fieldCantidad.setText((listaCompra.getUnidades()!=null)?listaCompra.getUnidades().toString():"");
            fieldPrice.setText((listaCompra.getPrecio()!=null)?listaCompra.getPrecio().toString():"");
            fieldNotes.setText(listaCompra.getNotas());
            fieldIdLIstaCompra.setText(listaCompra.getId().toString());
        }

        if(categoria != null){

        }

        if(tienda != null){

        }





    }
}
