package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.app.Dialog;

import com.shop.oasaustre.shoppinglist.activity.dialog.CategoriaDialog;
import com.shop.oasaustre.shoppinglist.activity.dialog.TiendaDialog;
import com.shop.oasaustre.shoppinglist.activity.task.firebase.LoadArticlesTask;
import com.shop.oasaustre.shoppinglist.app.App;

/**
 * Created by oasaustre on 16/09/17.
 */

public class TaskFactory {

    private static TaskFactory taskFactory;

    private TaskFactory(){}

    public static TaskFactory getInstance(){
        if(taskFactory == null){
            taskFactory = new TaskFactory();
        }
        return taskFactory;

    }

    public ITask createLoadArticlesTask(Activity activity, App app){

        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.LoadArticlesTask(app, activity);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.LoadArticlesTask(activity);
        }


        return task;
    }

    public ITask createArticleInShoppingListTask(Activity activity, App app){

        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.ArticleInShoppingListTask(activity);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.ArticleInShoppingListTask(activity);
        }


        return task;
    }

    public ITask createFindBarcodeTask(Activity activity,App app){
        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.FindBarcodeTask(activity);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.FindBarcodeTask(activity);
        }


        return task;
    }

    public ITask createLoadCategoriesTask(Activity activity,App app){
        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.LoadCategoriesTask(activity);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.LoadCategoriesTask(activity);
        }


        return task;
    }

    public ITask createLoadTiendasTask(Activity activity,App app){
        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.LoadTiendasTask(activity);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.LoadTiendasTask(activity);
        }


        return task;
    }

    public ITask createLoadListasTask(Activity activity,App app){
        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.LoadListasTask(activity);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.LoadListasTask(activity);
        }


        return task;
    }

    public ITask createNewCategoryTask(CategoriaDialog dialog, App app){
        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.NewCategoryTask(dialog);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.NewCategoryTask(dialog);
        }


        return task;
    }

    public ITask createNewTiendaTask(TiendaDialog dialog, App app){
        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.NewTiendaTask(dialog);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.NewTiendaTask(dialog);
        }


        return task;
    }

    public ITask createUpdateCategoryTask(Activity activity, App app){
        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.UpdateCategoryTask(activity);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.UpdateCategoryTask(activity);
        }


        return task;
    }


    public ITask createUpdateListaTask(Activity activity, App app){
        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.UpdateListaTask(activity);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.UpdateListaTask(activity);
        }


        return task;
    }

    public ITask createUpdateTiendaTask(Activity activity, App app){
        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.UpdateTiendaTask(activity);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.UpdateTiendaTask(activity);
        }


        return task;
    }


    public ITask createUpdateArticleDetailTask(Activity activity, App app){
        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.UpdateArticleDetailTask(activity);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.UpdateArticleDetailTask(activity);
        }


        return task;
    }

    public ITask createArticleDetailTask(Activity activity, App app){
        ITask task;
        if(app.isUserActive()){
            task = new com.shop.oasaustre.shoppinglist.activity.task.firebase.ArticleDetailTask(activity);
        }else{
            task = new com.shop.oasaustre.shoppinglist.activity.task.ArticleDetailTask(activity);
        }


        return task;
    }



}
