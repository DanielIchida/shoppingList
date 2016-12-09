package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.ListaCompraService;
import com.shop.oasaustre.shoppinglist.dto.ArticuloDetalleDto;

/**
 * Created by oasaustre on 4/12/16.
 */

public class ArticleDetailTask  extends AsyncTask<Long,Void,ArticuloDetalleDto> {

    private Activity activity = null;

    public ArticleDetailTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected ArticuloDetalleDto doInBackground(Long... params) {
        ListaCompraService listaCompraService = null;
        ArticuloDetalleDto articuloDetalleDto = null;

        listaCompraService = new ListaCompraService((App) activity.getApplication());


        articuloDetalleDto = listaCompraService.getArticleDetail(params[0]);

        return articuloDetalleDto;
    }

    @Override
    protected void onPostExecute(ArticuloDetalleDto articuloDetalleDto) {
        Articulo articulo = null;
        Tienda tienda = null;
        Categoria categoria = null;
        ListaCompra listaCompra = null;
        ArrayAdapter<Categoria> categoriaAdapter = null;
        ArrayAdapter<Tienda> tiendaAdapter = null;
        int posItemSelected;


        EditText fieldBarcode = (EditText) activity.findViewById(R.id.fieldBarcode);
        EditText fieldArticulo = (EditText) activity.findViewById(R.id.fieldArticulo);
        EditText fieldCantidad = (EditText) activity.findViewById(R.id.fieldCantidad);
        Spinner fieldCategory = (Spinner) activity.findViewById(R.id.fieldCategory);
        Spinner fieldTienda = (Spinner) activity.findViewById(R.id.fieldTienda);
        EditText fieldPrice = (EditText) activity.findViewById(R.id.fieldPrice);
        EditText fieldNotes = (EditText) activity.findViewById(R.id.fieldNotes);
        TextView fieldIdListaCompra = (TextView) activity.findViewById(R.id.fieldIdLIstaCompra);
        TextView fieldIdArticulo = (TextView) activity.findViewById(R.id.fieldIdArticulo);

        listaCompra = articuloDetalleDto.getListaCompra();


        articulo = listaCompra.getArticulo();

        if(articulo != null){
            fieldBarcode.setText(articulo.getCodigoBarras());
            fieldArticulo.setText(articulo.getNombre());
            fieldIdArticulo.setText(articulo.getId().toString());
        }

        if(listaCompra != null){
            fieldCantidad.setText((listaCompra.getUnidades()!=null)?listaCompra.getUnidades().toString():AppConstant.BLANK);
            fieldPrice.setText((listaCompra.getPrecio()!=null)?listaCompra.getPrecio().toString():AppConstant.BLANK);
            fieldNotes.setText(listaCompra.getNotas());
            fieldIdListaCompra.setText(listaCompra.getId().toString());
        }

        categoriaAdapter = new ArrayAdapter<Categoria>(activity,
                android.R.layout.simple_spinner_dropdown_item,
                articuloDetalleDto.getAllCategorias());

        fieldCategory.setAdapter(categoriaAdapter);

        categoria = listaCompra.getCategoria();

        if(categoria != null){
            posItemSelected = categoriaAdapter.getPosition(categoria);
            fieldCategory.setSelection(posItemSelected);

        }else{
            fieldCategory.setSelection(AppConstant.POSITION_DEFAULT);
        }

        tiendaAdapter = new ArrayAdapter<Tienda>(activity,
                android.R.layout.simple_spinner_dropdown_item,
                articuloDetalleDto.getAllTiendas());

        fieldTienda.setAdapter(tiendaAdapter);

        tienda = listaCompra.getTienda();

        if(tienda != null){
            posItemSelected = tiendaAdapter.getPosition(tienda);
            fieldTienda.setSelection(posItemSelected);
        }else{
            fieldTienda.setSelection(AppConstant.POSITION_DEFAULT);
        }





    }
}
