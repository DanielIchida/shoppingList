package com.shop.oasaustre.shoppinglist.db.service.firebase;

import android.app.Activity;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.activity.InitActivity;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.app.User;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.entity.Usuario;
import com.shop.oasaustre.shoppinglist.db.service.IUsuarioService;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oasaustre on 9/09/17.
 */

public class UsuarioService implements IUsuarioService{

    private static final String USERS = "usuario";
    private static final String LISTS = "lista";
    private static final String SHARE = "compartida";

    private App app;
    private Activity activity;

    public UsuarioService(Activity activity, App app){
        this.app = app;
        this.activity = activity;
    }

    @Override
    public void createUser(final Usuario user) {
            final FirebaseDatabase database = app.getDatabase();
            final DatabaseReference currentUserReference = database.getReference(USERS).child(user.getUid());

        ValueEventListener userListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    Map<String,Object> lista = new HashMap<String,Object>();
                    Map<String,Object> listaAttributes = new HashMap<String,Object>();
                    listaAttributes.put("nombre","Lista Compra");
                    listaAttributes.put("fecha",System.currentTimeMillis());

                    String key = database.getReference().child(LISTS).push().getKey();
                    listaAttributes.put("uid",key);
                    lista.put(key,listaAttributes);
                    database.getReference().child(LISTS).setValue(lista);

                    /*
                     * Se establece la lista activa
                     */
                    ListaDto listaActive = new ListaDto();
                    listaActive.setUid(key);
                    listaActive.setNombre((String) listaAttributes.get("nombre"));
                    listaActive.setFecha((Long) listaAttributes.get("fecha"));
                    listaActive.setActivo((Long) listaAttributes.get("activo"));

                    app.setListaFBActive(listaActive);

                    Map<String,Object> compartida = new HashMap<String,Object>();
                    compartida.put(key,true);
                    user.setCompartida(compartida);
                    currentUserReference.setValue(user);

                    Map<String,Object> listaCompartida = new HashMap<String,Object>();
                    final Map<String,Object> usuariosLista = new HashMap<String,Object>();

                    usuariosLista.put(user.getUid(),true);

                    listaCompartida.put(key,usuariosLista);

                    database.getReference().child(SHARE).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.exists()){
                                dataSnapshot.getRef().setValue(usuariosLista);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    executeInit();


                }else{

                    database.getReference().child(SHARE).orderByChild(app.getUser().getUid()).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                DataSnapshot childSnapshot = dataSnapshot.getChildren().iterator().next();
                                database.getReference().child(LISTS).child(childSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            ListaDto listaDto = dataSnapshot.getValue(ListaDto.class);
                                            app.setListaFBActive(listaDto);
                                            executeInit();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        currentUserReference.addListenerForSingleValueEvent(userListener);

    }


    private void executeInit(){
        Intent i = new Intent(activity, InitActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(i);
        activity.finish();
    }
}
