package com.shop.oasaustre.shoppinglist.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteArticlesDialog;
import com.shop.oasaustre.shoppinglist.activity.dialog.ListDialog;
import com.shop.oasaustre.shoppinglist.activity.dialog.VoiceResultDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ArticleInShoppingListTask;
import com.shop.oasaustre.shoppinglist.activity.task.FindBarcodeTask;
import com.shop.oasaustre.shoppinglist.activity.task.LoadArticlesTask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.app.SettingsHelper;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InitActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



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
            lanzarSettings();
            return true;
        } else if (id == R.id.action_lista) {
            createLista();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_admin_categoria) {
            navCategorias();
        } else if (id == R.id.nav_admin_lista) {
            navListas();
        } else if (id == R.id.nav_admin_tienda) {
            navTiendas();
        } else if (id == R.id.nav_gastos) {
            navGastos();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void lanzarSettings(){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivityForResult(i,AppConstant.RES_SETTINGS);
    }

    private void createLista() {
        ListDialog listDialog = new ListDialog();
        listDialog.setActivo(true);
        listDialog.show(getSupportFragmentManager(), "Nueva Lista");

    }

    private void navCategorias() {
        Intent intent = new Intent(this, CategoriaActivity.class);
        startActivity(intent);
    }

    private void navTiendas() {
        Intent intent = new Intent(this, TiendaActivity.class);
        startActivity(intent);
    }

    private void navListas() {
        Intent intent = new Intent(this, ListaActivity.class);
        startActivity(intent);
    }

    private void navGastos(){
        Intent intent = new Intent(this, GastosMensualesActivity.class);
        startActivity(intent);
    }


    private void initializeTextFind() {

        AutoCompleteTextView textFind = (AutoCompleteTextView) findViewById(R.id.txtBuscarArticulo);
        textFind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ImageView imgBarcode = (ImageView) findViewById(R.id.imgBarcode);
                ImageView imgVoice = (ImageView) findViewById(R.id.imgVoice);
                ImageView imgDone = (ImageView) findViewById(R.id.imgDone);
                ImageView imgClear = (ImageView) findViewById(R.id.imgClear);

                if (charSequence != null && charSequence.length() > 0) {
                    imgBarcode.setVisibility(View.INVISIBLE);
                    imgVoice.setVisibility(View.INVISIBLE);
                    imgDone.setVisibility(View.VISIBLE);
                    imgClear.setVisibility(View.VISIBLE);
                } else {
                    imgBarcode.setVisibility(View.VISIBLE);
                    imgVoice.setVisibility(View.VISIBLE);
                    imgDone.setVisibility(View.INVISIBLE);
                    imgClear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initializeUI() {
        ImageView imgBarcode = (ImageView) findViewById(R.id.imgBarcode);
        ImageView imgVoice = (ImageView) findViewById(R.id.imgVoice);
        ImageView imgDone = (ImageView) findViewById(R.id.imgDone);
        ImageView imgClear = (ImageView) findViewById(R.id.imgClear);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.deleteArticleFloat);

        imgBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeScan();
            }
        });


        imgVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeVoice();
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


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteArticlesDialog dialog = new DeleteArticlesDialog();
                dialog.show(getSupportFragmentManager(), "Eliminar Artículos");
            }
        });


        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoCompleteTextView textFind = (AutoCompleteTextView) findViewById(R.id.txtBuscarArticulo);
                textFind.setText(AppConstant.BLANK);
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initializeVoice(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Reconocimiento de voz");

        startActivityForResult(intent, AppConstant.RES_VOICE);

    }

    private void initializeScan() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            callBarcodeScan();

        }else {
            solicitarPermisoCamara();
        }

    }


    private void callBarcodeScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        List<String> target = new ArrayList<String>();
        target.add("com.shop.oasaustre.shoppinglist");
        integrator.setTargetApplications(target);
        integrator.initiateScan();
    }


    private void solicitarPermisoCamara() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                AppConstant.CAMERA_PERMISSION);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstant.RES_UPDATE_ARTICLE && resultCode == RESULT_OK) {
            LoadArticlesTask task = new LoadArticlesTask(this);
            task.execute();
        }else if(requestCode == AppConstant.RES_VOICE){
            if(resultCode == RESULT_OK && data != null){
                getResultVoiceWords(data);
            }else{
                View view = this.findViewById(R.id.content_init);
                Snackbar.make(view, "No se ha reconocido ninguna palabras. Inténtalo de nuevo",
                        Snackbar.LENGTH_LONG).show();
            }
        }else if(requestCode == AppConstant.RES_SETTINGS){
            SettingsHelper helper = new SettingsHelper(this,(App) getApplication());
            helper.configure();
        }
        else{
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanResult != null) {
                FindBarcodeTask task = new FindBarcodeTask(this);
                task.execute(scanResult.getContents());
            }
        }
    }

    private void getResultVoiceWords(Intent data){
        List<String> result = data
                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        if(result != null && result.size() > 0){
            VoiceResultDialog  dialog = new VoiceResultDialog(this);
            dialog.setWords(result);
            dialog.show();
        }else{
            View view = this.findViewById(R.id.content_init);
            Snackbar.make(view, "No se ha reconocido ninguna palabras. Inténtalo de nuevo",
                    Snackbar.LENGTH_LONG).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == AppConstant.CAMERA_PERMISSION) {
            if (grantResults.length== 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callBarcodeScan();
            } else {

            }
        }
    }

    /*****
     * EVENTOS DEL CICLO DE VIDA
     *****/

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();


        Lista listaActive = ((App) this.getApplication()).getListaActive();
        getSupportActionBar().setTitle(listaActive.getNombre());

        LoadArticlesTask task = new LoadArticlesTask(this);
        task.execute();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
