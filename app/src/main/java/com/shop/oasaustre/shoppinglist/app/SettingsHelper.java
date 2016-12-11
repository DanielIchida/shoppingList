package com.shop.oasaustre.shoppinglist.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

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

    public SettingsHelper(Context context, App app) {
        this.context = context;
        this.app = app;
    }

    public void configure() {

        try {
            SharedPreferences pref =
                    PreferenceManager.getDefaultSharedPreferences(context);
            ScheduledEnum scheduledEnum = null;

            SharedPreferences sharedPreferences = context.getSharedPreferences("scheduled_list", Context.MODE_PRIVATE);

            String dayScheduled = sharedPreferences.getString(AppConstant.DAY_SCHEDULED, AppConstant.BLANK);
            String monthScheduled = sharedPreferences.getString(AppConstant.MONTH_SCHEDULED, AppConstant.BLANK);
            String yearScheduled = sharedPreferences.getString(AppConstant.YEAR_SCHEDULED, AppConstant.BLANK);

            String currency = pref.getString(AppConstant.CURRENCY_PREF, AppConstant.CURRENCY_DEFAULT);
            Boolean activeNotif = pref.getBoolean(AppConstant.ACTIVE_NOTIF_PREF, Boolean.FALSE);
            String periodicNotif = pref.getString(AppConstant.SCHEDULED_NOTIF_PREF, AppConstant.SCHEDULED_NOTIF_DEFAULT);

            Settings settings = new Settings(currency, activeNotif, periodicNotif);
            app.setSettings(settings);

            sf = app.getScheduled();

            if (activeNotif == Boolean.TRUE) {
                if (app.getExec() != null) {
                    if (throwsScheduled(dayScheduled,monthScheduled, yearScheduled)) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(AppConstant.DAY_SCHEDULED, String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
                        editor.putString(AppConstant.MONTH_SCHEDULED, String.valueOf(Calendar.getInstance().get(Calendar.MONTH)));
                        editor.putString(AppConstant.YEAR_SCHEDULED, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
                        editor.commit();

                        scheduledEnum = ScheduledEnum.getType(periodicNotif);
                        sf = app.getExec().scheduleAtFixedRate(new ScheduleTask(scheduledEnum), 0, 1, TimeUnit.DAYS);
                        app.setScheduled(sf);
                    }
                }
            } else {
                if (sf != null) {
                    sf.cancel(false);
                }
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "Error en el schedule:", ex);
        }

    }

    public boolean throwsScheduled(String dayScheduled,String monthScheduled, String yearScheduled) {

        boolean result = true;
        if (dayScheduled != null && !dayScheduled.equals(AppConstant.BLANK) &&
                monthScheduled != null && !monthScheduled.equals(AppConstant.BLANK) &&
                yearScheduled != null && !yearScheduled.equals(AppConstant.BLANK)) {
            if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == Integer.parseInt(dayScheduled) &&
                    Calendar.getInstance().get(Calendar.MONTH) == Integer.parseInt(monthScheduled) &&
                    Calendar.getInstance().get(Calendar.YEAR) == Integer.parseInt(yearScheduled)) {
                result = false;
            }
        }

        return result;
    }

    class ScheduleTask implements Runnable {
        ScheduledEnum scheduledEnum;

        ScheduleTask(ScheduledEnum scheduledEnum) {
            this.scheduledEnum = scheduledEnum;
        }

        @Override
        public void run() {

            if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 1) {
                context.startService(new Intent(context, CheckListaCompraService.class));
            }

        }
    }


}
