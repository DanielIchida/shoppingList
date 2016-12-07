package com.shop.oasaustre.shoppinglist.adapter.item;

/**
 * Created by oasaustre on 7/12/16.
 */

public abstract class ListItem {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_CONTENT = 1;

    abstract public int getType();
}
