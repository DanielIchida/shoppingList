package com.shop.oasaustre.shoppinglist.app;

import android.app.Application;

import com.shop.oasaustre.shoppinglist.db.dao.DaoMaster;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by oasaustre on 29/11/16.
 */

public class App extends Application {

    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    private Lista listaCompraActive;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            copyDataBase("shopping_list.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "shopping_list_encrypted.db" : "shopping_list.db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        db.execSQL("PRAGMA foreign_keys=ON");
        daoSession = new DaoMaster(db).newSession();
        loadShoppingListActive();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public Lista getListaActive(){
        return listaCompraActive;
    }


    private void loadShoppingListActive(){
        List<Lista> currentList = null;
        WhereCondition.StringCondition condition = new WhereCondition.StringCondition("activo = 1");
        Query<Lista> query = daoSession.getListaDao().queryBuilder().where(condition).build();
        currentList = query.list();

        if(currentList != null && currentList.size() > 0){
            listaCompraActive = currentList.get(0);
        }else{
            createInitialShoppingList();
        }
    }

    private void createInitialShoppingList(){

        try {
            daoSession.getDatabase().beginTransaction();
            Lista initialList = new Lista();
            long rowid;

            initialList.setNombre("Lista Compra");
            initialList.setFecha(System.currentTimeMillis());
            initialList.setActivo(1l);


            rowid = daoSession.getListaDao().insert(initialList);

            initialList.setId(rowid);

            daoSession.getDatabase().setTransactionSuccessful();

            listaCompraActive = initialList;

        }catch (Exception ex){

        }finally {
            daoSession.getDatabase().endTransaction();
        }

    }

    private void copyDataBase(String dbname) throws IOException {
        // Open your local db as the input stream
        InputStream myInput = this.getAssets().open(dbname);
        // Path to the just created empty db
        File outFileName = getDatabasePath(dbname);
        if(!outFileName.exists()) {
            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
    }
}
