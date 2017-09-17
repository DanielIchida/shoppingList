package com.shop.oasaustre.shoppinglist.db.service;

import android.app.Activity;

import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.service.firebase.UsuarioService;



/**
 * Created by oasaustre on 10/09/17.
 */

public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    private ServiceFactory(){
    }

    public static ServiceFactory getInstance(){
        if(serviceFactory == null){
            serviceFactory = new ServiceFactory();
        }

        return serviceFactory;
    }

    public IUsuarioService create(Activity activity, App app, String type){
        IUsuarioService service = null;
        if(type.equalsIgnoreCase("firebase")){
            service = new UsuarioService(activity,app);
        }

        return service;
    }

    public IListaService createListaService(App app, String type){
        IListaService service = null;
        if(type.equalsIgnoreCase("local")){
            service = new ListaService(app);
        }

        return service;
    }
}
