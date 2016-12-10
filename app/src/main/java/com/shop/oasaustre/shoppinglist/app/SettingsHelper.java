package com.shop.oasaustre.shoppinglist.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.preference.PreferenceManager;

import com.shop.oasaustre.shoppinglist.app.Settings;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.service.CheckListaCompraService;

import java.util.Calendar;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by oasaustre on 10/12/16.
 */

public class SettingsHelper {

    private Context context;
    private App app;
    private ScheduledFuture<?> sf;

    public SettingsHelper(Context context, App app){
        this.context = context;
        this.app = app;
    }

    public void configure(){
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(context);
        ScheduledEnum scheduledEnum = null;

        String currency = pref.getString(AppConstant.CURRENCY_PREF, AppConstant.CURRENCY_DEFAULT);
        Boolean activeNotif = pref.getBoolean(AppConstant.ACTIVE_NOTIF_PREF,Boolean.FALSE);
        String periodicNotif = pref.getString(AppConstant.SCHEDULED_NOTIF_PREF,AppConstant.SCHEDULED_NOTIF_DEFAULT);

        Settings settings = new Settings(currency,activeNotif,periodicNotif);;
        app.setSettings(settings);
        if(sf != null && sf.isDone()){
            sf.cancel(false);
        }
        if(activeNotif == Boolean.TRUE){
            scheduledEnum = ScheduledEnum.getType(periodicNotif);
            sf = app.getExec().scheduleAtFixedRate(new ScheduleTask(scheduledEnum),0, 1, TimeUnit.MINUTES);
        }else{

        }

    }

    class ScheduleTask implements Runnable {
        ScheduledEnum scheduledEnum;

        ScheduleTask(ScheduledEnum scheduledEnum){
            this.scheduledEnum = scheduledEnum;
        }
        @Override
        public void run() {
            System.out.println("Hello world");
            if( Calendar.getInstance().get(Calendar.DAY_OF_MONTH)== 1 && scheduledEnum == ScheduledEnum.MENSUAL){
                context.startService(new Intent(context, CheckListaCompraService.class));
            }else if((Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 1
                    || Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 15) && scheduledEnum == ScheduledEnum.QUINCENAL){
                context.startService(new Intent(context, CheckListaCompraService.class));
            }else if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && scheduledEnum == ScheduledEnum.SEMANAL){
                context.startService(new Intent(context, CheckListaCompraService.class));
            }else if(scheduledEnum == ScheduledEnum.DIARIA){
                context.startService(new Intent(context, CheckListaCompraService.class));
            }

        }
    }


}
