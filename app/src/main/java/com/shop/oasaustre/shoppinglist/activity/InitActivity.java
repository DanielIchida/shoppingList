package com.shop.oasaustre.shoppinglist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.ListDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ArticleInShoppingListTask;
import com.shop.oasaustre.shoppinglist.activity.task.LoadArticlesTask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;

public class InitActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LoadArticlesTask task = new LoadArticlesTask(this);
        task.execute();

        initializeTextFind();
        initializeUI();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.init, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_lista){
            createLista();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void createLista(){
        ListDialog listDialog = new ListDialog();
        listDialog.show(getSupportFragmentManager(), "SimpleDialog");

    }


    private void initializeTextFind(){

        AutoCompleteTextView textFind = (AutoCompleteTextView) findViewById(R.id.txtBuscarArticulo);
        textFind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ImageView imgBarcode = (ImageView) findViewById(R.id.imgBarcode);
                ImageView imgHistory = (ImageView) findViewById(R.id.imgHistory);
                ImageView imgFavorite = (ImageView) findViewById(R.id.imgFavorite);
                ImageView imgEditar = (ImageView) findViewById(R.id.imgEditar);
                ImageView imgDone = (ImageView) findViewById(R.id.imgDone);
                ImageView imgClear = (ImageView) findViewById(R.id.imgClear);

                if(charSequence != null && charSequence.length() > 0){
                    imgBarcode.setVisibility(View.INVISIBLE);
                    imgHistory.setVisibility(View.INVISIBLE);
                    imgFavorite.setVisibility(View.INVISIBLE);
                    imgEditar.setVisibility(View.VISIBLE);
                    imgDone.setVisibility(View.VISIBLE);
                    imgClear.setVisibility(View.VISIBLE);
                }else{
                    imgBarcode.setVisibility(View.VISIBLE);
                    imgHistory.setVisibility(View.VISIBLE);
                    imgFavorite.setVisibility(View.VISIBLE);
                    imgEditar.setVisibility(View.INVISIBLE);
                    imgDone.setVisibility(View.INVISIBLE);
                    imgClear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initializeUI(){
        ImageView imgBarcode = (ImageView) findViewById(R.id.imgBarcode);
        ImageView imgHistory = (ImageView) findViewById(R.id.imgHistory);
        ImageView imgFavorite = (ImageView) findViewById(R.id.imgFavorite);
        ImageView imgEditar = (ImageView) findViewById(R.id.imgEditar);
        ImageView imgDone = (ImageView) findViewById(R.id.imgDone);
        ImageView imgClear = (ImageView) findViewById(R.id.imgClear);

        imgBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoCompleteTextView textFind = (AutoCompleteTextView) findViewById(R.id.txtBuscarArticulo);
                ArticleInShoppingListTask task = new ArticleInShoppingListTask(InitActivity.this);
                task.execute(textFind.getText().toString());
                textFind.setText(AppConstant.BLANK);



            }
        });


        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode == AppConstant.RES_UPDATE_ARTICLE && resultCode == RESULT_OK){
            LoadArticlesTask task = new LoadArticlesTask(this);
            task.execute();
        }
    }

    /***** EVENTOS DEL CICLO DE VIDA *****/

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();

        Lista listaActive = ((App) this.getApplication()).getListaActive();
        getSupportActionBar().setTitle(listaActive.getNombre());

        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }
}
