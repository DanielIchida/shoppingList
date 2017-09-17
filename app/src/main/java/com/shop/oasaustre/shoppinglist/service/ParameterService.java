package com.shop.oasaustre.shoppinglist.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.dto.firebase.ArticuloDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.TiendaDto;

import java.util.ArrayList;
import java.util.List;

public class ParameterService extends Service {

    private final static String TIENDA = "tienda";
    private final static String CATEGORY ="categoria";

    public ParameterService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getParameters();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void getParameters(){
        App app = (App) this.getApplication();
        app.getDatabase().getReference().child(CATEGORY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<CategoriaDto> lista = new ArrayList<CategoriaDto>();
                CategoriaDto categoriaDto = null;
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    categoriaDto = child.getValue(CategoriaDto.class);
                    lista.add(categoriaDto);
                }
                App app = (App) ParameterService.this.getApplication();
                app.setListaCategoriaDto(lista);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        app.getDatabase().getReference().child(TIENDA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<TiendaDto> lista = new ArrayList<TiendaDto>();
                TiendaDto tiendaDto = null;
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    tiendaDto = child.getValue(TiendaDto.class);
                    lista.add(tiendaDto);
                }
                App app = (App) ParameterService.this.getApplication();
                app.setListaTiendaDto(lista);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
