package com.shop.oasaustre.shoppinglist.adapter.helper;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.shop.oasaustre.shoppinglist.adapter.item.ContentFBItem;
import com.shop.oasaustre.shoppinglist.adapter.item.ContentItem;
import com.shop.oasaustre.shoppinglist.adapter.item.HeaderItem;
import com.shop.oasaustre.shoppinglist.adapter.item.ListItem;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaCompraFBDto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by oasaustre on 7/12/16.
 */

public class ListaCompraAdapterHelper {

    public static List<ListItem> performTransform(List<ListaCompra> lstCompra){
        List<ListItem> resultList = new ArrayList<ListItem>();
        Multimap<String, ListItem> mapItems = ArrayListMultimap.create();
        ListItem contentItem = null;
        if(lstCompra != null && lstCompra.size() > 0){
            for(ListaCompra listCompra:lstCompra){

                contentItem = new ContentItem(listCompra);

                if(listCompra.getCategoria() != null){
                    mapItems.put(listCompra.getCategoria().getNombre(),contentItem);
                }else{
                    mapItems.put(AppConstant.TITLE_CATEGORY_DEFAULT,contentItem);
                }
            }

            if(mapItems.asMap() != null && mapItems.asMap().keySet() != null){
                for(String key:mapItems.asMap().keySet()){
                    ListItem headerItem = new HeaderItem(key);
                    resultList.add(headerItem);
                    resultList.addAll(mapItems.get(key));
                }

            }
        }


        return resultList;
    }

    public static List<ListItem> addItem(List<ListItem> items, ListaCompra listaCompra){

        List<ListaCompra> lstListaCompra = null;
        ListItem item = new ContentItem(listaCompra);
        items.add(item);

        lstListaCompra = performTransformInverse(items);

        return performTransform(lstListaCompra);
    }

    public static List<ListItem> removeItem(List<ListItem> items, List<Long> selectedItems){
        boolean find;
        ListItem listItem = null;
        List<ListaCompra> lstListaCompra = null;
        for(Long selectItem:selectedItems){
            find = false;
            Iterator it = items.iterator();
            while(it.hasNext() && !find){
                listItem = (ListItem) it.next();
                if((listItem instanceof ContentItem) &&
                    ((ContentItem) listItem).getListaCompra().getId().longValue() == selectItem.longValue()){
                    it.remove();
                    find = true;
                }
            }
        }

        lstListaCompra = performTransformInverse(items);

        return performTransform(lstListaCompra);
    }

    public static List<ListaCompra> performTransformInverse(List<ListItem> items){
        List<ListaCompra> lstListaCompra = new ArrayList<ListaCompra>();

        for(ListItem listItem: items){
            if(listItem instanceof ContentItem){
                lstListaCompra.add(((ContentItem)listItem).getListaCompra());
            }
        }

        return lstListaCompra;
    }

    public static List<ListItem> convert(List<ListaCompraFBDto> lstCompra){
        List<ListItem> resultList = new ArrayList<ListItem>();
        Multimap<String, ListItem> mapItems = ArrayListMultimap.create();
        ListItem contentItem = null;
        if(lstCompra != null && lstCompra.size() > 0){
            for(ListaCompraFBDto listCompra:lstCompra){

                contentItem = new ContentFBItem(listCompra);
                if(listCompra.getCategoria() != null && !listCompra.getCategoria().isEmpty()){
                    mapItems.put((String)listCompra.getCategoria().get("categoryName"),contentItem);
                }else{
                    mapItems.put(AppConstant.TITLE_CATEGORY_DEFAULT,contentItem);
                }
            }

            if(mapItems.asMap() != null && mapItems.asMap().keySet() != null){
                for(String key:mapItems.asMap().keySet()){
                    ListItem headerItem = new HeaderItem(key);
                    resultList.add(headerItem);
                    resultList.addAll(mapItems.get(key));
                }

            }
        }


        return resultList;
    }

    public static List<ListaCompraFBDto>  convertInverse(List<ListItem> items){
        List<ListaCompraFBDto> lstListaCompra = new ArrayList<ListaCompraFBDto>();

        for(ListItem listItem: items){
            if(listItem instanceof ContentFBItem){
                lstListaCompra.add(((ContentFBItem)listItem).getListaCompra());
            }
        }

        return lstListaCompra;
    }


    public static List<ListItem> addFBItem(List<ListItem> items, ListaCompraFBDto listaCompra){

        List<ListaCompraFBDto> lstListaCompra = null;
        ListItem item = new ContentFBItem(listaCompra);
        items.add(item);

        lstListaCompra = convertInverse(items);

        return convert(lstListaCompra);
    }

    public static List<ListItem> removeFBItem(List<ListItem> items, List<Long> selectedItems){
        boolean find;
        ListItem listItem = null;
        List<ListaCompra> lstListaCompra = null;
        for(Long selectItem:selectedItems){
            find = false;
            Iterator it = items.iterator();
            while(it.hasNext() && !find){
                listItem = (ListItem) it.next();
                if((listItem instanceof ContentItem) &&
                        ((ContentItem) listItem).getListaCompra().getId().longValue() == selectItem.longValue()){
                    it.remove();
                    find = true;
                }
            }
        }

        lstListaCompra = performTransformInverse(items);

        return performTransform(lstListaCompra);
    }
}
