package com.shop.oasaustre.shoppinglist.db.service.firebase;

import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.IListaService;
import com.shop.oasaustre.shoppinglist.dto.GastosDto;
import com.shop.oasaustre.shoppinglist.dto.ListaActivaDto;

import java.util.List;

/**
 * Created by oasaustre on 10/09/17.
 */

public class ListaService implements IListaService{
    @Override
    public List<Lista> loadAll() {
        return null;
    }

    @Override
    public void saveLista(Lista lista) {

    }

    @Override
    public void saveAndChangeLista(Lista lista) {

    }

    @Override
    public ListaActivaDto setListaActiva(Lista lista) {
        return null;
    }

    @Override
    public Boolean removeLista(Lista lista) {
        return null;
    }

    @Override
    public void updateLista(Lista lista) {

    }

    @Override
    public List<GastosDto> calculateGastos() {
        return null;
    }

    @Override
    public void getListaActive() {

    }
}
