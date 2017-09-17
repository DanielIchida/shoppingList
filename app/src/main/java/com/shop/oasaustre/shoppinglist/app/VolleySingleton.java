package com.shop.oasaustre.shoppinglist.app;

/**
 * Created by oasaustre on 3/09/17.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by oasaustre on 4/02/17.
 */

public class VolleySingleton {

    private static VolleySingleton ourInstance = null;

    private RequestQueue colaPeticiones;
    private ImageLoader lectorImagenes;



    public static VolleySingleton getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new VolleySingleton(context);
        }
        return ourInstance;
    }

    private VolleySingleton(Context context){
        colaPeticiones = Volley.newRequestQueue(context);
        lectorImagenes = new ImageLoader(colaPeticiones,
                new ImageLoader.ImageCache() {

                    private final LruCache<String, Bitmap> cache =
                            new LruCache<String, Bitmap>(10);

                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }

                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }
                });
    }

    public RequestQueue getColaPeticiones() {
        return colaPeticiones;
    }

    public ImageLoader getLectorImagenes() {
        return lectorImagenes;
    }
}
