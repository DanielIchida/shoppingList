package com.shop.oasaustre.shoppinglist.db.service;

import com.shop.oasaustre.shoppinglist.dto.ArticuloBarcodeDto;

/**
 * Created by oasaustre on 17/09/17.
 */

public interface IArticuloService {


    ArticuloBarcodeDto findArticuloByBarcode(String barcode);
}
