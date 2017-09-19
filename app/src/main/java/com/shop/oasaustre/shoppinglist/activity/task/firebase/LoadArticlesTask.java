package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.ArticleSaveActivity;
import com.shop.oasaustre.shoppinglist.activity.InitActivity;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.adapter.firebase.ListaCompraAdapter;
import com.shop.oasaustre.shoppinglist.adapter.item.ContentFBItem;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.dto.firebase.ArticuloDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaCompraFBDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oasaustre on 13/09/17.
 */

public class LoadArticlesTask implements ITask {

    private final static String SHOPPING_LIST = "lista_compra";
    private final static String ARTICLE ="articulo";

    private Activity activity = null;
    private App app = null;
    private ListaCompraAdapter adapter = null;
    private ListaCompraFBDto listaCompraDto = null;

    public LoadArticlesTask(App app,Activity activity){
        this.app = app;
        this.activity = activity;
    }


    public void run(Object ... params) {

        ListaDto listaActive = null;
        Query queryShopping = null;

        listaActive =  app.getListaaFBActive();

        queryShopping = app.getDatabase().getReference().child(SHOPPING_LIST).orderByChild("lista/idLista").equalTo(listaActive.getUid());
        onPostExecute(queryShopping);


        /*queryShopping.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaCompraDto = dataSnapshot.getValue(ListaCompraFBDto.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }


    public void onPostExecute(Query reference) {

        DividerItemDecoration did = new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);

        ListaDto listaActive = ((App) activity.getApplication()).getListaaFBActive();

        ((InitActivity)activity).getSupportActionBar().setTitle(listaActive.getNombre());

        adapter = new ListaCompraAdapter(activity,reference, app);

        adapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                int positionItemSelect = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
                ListaCompraAdapter adapter = (ListaCompraAdapter)((RecyclerView) view.getParent()).getAdapter();
                ListaCompraFBDto listaCompra = ((ContentFBItem) adapter.getLista().get(positionItemSelect)).getListaCompra();


                Intent i = new Intent(activity,ArticleSaveActivity.class);
                i.putExtra("idListaCompra",listaCompra.getUid());
                activity.startActivityForResult(i, AppConstant.RES_UPDATE_ARTICLE);
            }
        });

        RecyclerView rvListaCompra = (RecyclerView) activity.findViewById(R.id.rv_listaCompraActual);
        rvListaCompra.addItemDecoration(did);
        rvListaCompra.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rvListaCompra.setLayoutManager(layoutManager);

        adapter.activateListener();

        app.getDatabase().getReference().child(ARTICLE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ArticuloDto> lstArticuloDto = new ArrayList<ArticuloDto>();
                ArticuloDto articuloDto = null;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    articuloDto = child.getValue(ArticuloDto.class);
                    lstArticuloDto.add(articuloDto);
                }

                if(!lstArticuloDto.isEmpty()){
                    ArrayAdapter<ArticuloDto> adapterText = new ArrayAdapter<ArticuloDto>(activity,
                            android.R.layout.simple_spinner_dropdown_item, lstArticuloDto);
                    AutoCompleteTextView textView = (AutoCompleteTextView) activity.findViewById(R.id.txtBuscarArticulo);
                    // Numero de caracteres necesarios para que se empiece
                    // a mostrar la lista
                    textView.setThreshold(AppConstant.MIN_CHAR_ACTIVE_FIND);

                    // Se establece el Adapter
                    textView.setAdapter(adapterText);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









    }


}
