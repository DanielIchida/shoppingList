package com.shop.oasaustre.shoppinglist.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.CategoriaDialog;
import com.shop.oasaustre.shoppinglist.activity.dialog.ShareListDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.activity.task.TaskFactory;
import com.shop.oasaustre.shoppinglist.adapter.firebase.ShareListAdapter;
import com.shop.oasaustre.shoppinglist.app.App;

public class ShareListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarShare);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initializeUI();
    }


    private void initializeUI(){
        TextView btnNewCategory = (TextView) findViewById(R.id.btnNewShareUser);
        btnNewCategory.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                createShareList();
            }
        });
    }

    private void createShareList(){
        ShareListDialog dialog = new ShareListDialog();
        dialog.show(getSupportFragmentManager(), "Añadir un amigo usando su correo electrónico");
    }


    /***** EVENTOS DEL CICLO DE VIDA *****/

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        ITask task = TaskFactory.getInstance().createLoadShareListTask(this,(App) this.getApplication());
        task.run();
    }
    @Override
    protected void onPause() {
        super.onPause();
        App app = (App) this.getApplication();
        if(app.isUserActive()){
            RecyclerView rvShareList = (RecyclerView) this.findViewById(R.id.rv_shareList);
            ShareListAdapter shareListAdapter = (ShareListAdapter) rvShareList.getAdapter();
            shareListAdapter.deactivateListener();
        }

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
