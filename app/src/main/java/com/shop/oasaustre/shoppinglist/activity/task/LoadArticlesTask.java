package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.ArticleSaveActivity;
import com.shop.oasaustre.shoppinglist.activity.InitActivity;
import com.shop.oasaustre.shoppinglist.adapter.ListaCompraAdapter;
import com.shop.oasaustre.shoppinglist.adapter.item.ContentItem;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.service.ListaCompraService;
import com.shop.oasaustre.shoppinglist.dto.ListaCompraDto;

/**
 * Created by oasaustre on 3/12/16.
 */

public class LoadArticlesTask extends AsyncTask<Void,Void,ListaCompraDto> implements ITask {

    private Activity activity = null;
    private ListaCompraAdapter adapter = null;

    public LoadArticlesTask(Activity activity){
        this.activity = activity;
    }
    @Override
    protected ListaCompraDto doInBackground(Void... voids) {
        ListaCompraDto listaCompraDto = null;
        ListaCompraService listaCompraService = null;
        Lista listaActive = null;


        try {
            listaCompraDto = new ListaCompraDto();
            listaCompraService = new ListaCompraService((App) activity.getApplication());

            listaActive = ((App) activity.getApplication()).getListaActive();


            listaCompraDto = listaCompraService.loadListaCompraActive(listaActive.getId());

        }catch(Exception ex){
                Log.e(this.getClass().getName(),"Error al obtener la lista de la compra activa");
            }

        return listaCompraDto;
    }

    @Override
    protected void onPostExecute(ListaCompraDto listaCompraDto) {

        DividerItemDecoration did = new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);

        Lista listaActive = ((App) activity.getApplication()).getListaActive();

        ((InitActivity)activity).getSupportActionBar().setTitle(listaActive.getNombre());

        adapter = new ListaCompraAdapter(activity, listaCompraDto.getLstCompra(),(App) activity.getApplication());

        adapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                int positionItemSelect = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
                ListaCompraAdapter adapter = (ListaCompraAdapter)((RecyclerView) view.getParent()).getAdapter();
                ListaCompra listaCompra = ((ContentItem) adapter.getLista().get(positionItemSelect)).getListaCompra();


                Intent i = new Intent(activity,ArticleSaveActivity.class);
                i.putExtra("idListaCompra",listaCompra.getId());
                activity.startActivityForResult(i,AppConstant.RES_UPDATE_ARTICLE);
            }
        });

        RecyclerView rvListaCompra = (RecyclerView) activity.findViewById(R.id.rv_listaCompraActual);
        rvListaCompra.addItemDecoration(did);
        rvListaCompra.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rvListaCompra.setLayoutManager(layoutManager);


        ArrayAdapter<Articulo> adapterText = new ArrayAdapter<Articulo>(activity,
                android.R.layout.simple_spinner_dropdown_item, listaCompraDto.getAllArticles());

        AutoCompleteTextView textView = (AutoCompleteTextView) activity.findViewById(R.id.txtBuscarArticulo);

        // Numero de caracteres necesarios para que se empiece
        // a mostrar la lista
        textView.setThreshold(AppConstant.MIN_CHAR_ACTIVE_FIND);

        // Se establece el Adapter
        textView.setAdapter(adapterText);

        TextView txtTotalArticle = (TextView) activity.findViewById(R.id.txtTotalArticle);
        TextView txtTotalPrice = (TextView) activity.findViewById(R.id.txtTotalPrice);

        txtTotalArticle.setText(listaCompraDto.getTotalUnidades().toString());
        txtTotalPrice.setText(listaCompraDto.getSumTotalListaCompra().toString() + "€");
    }

    @Override
    public void run(Object ... params) {
        this.execute();
    }
}
