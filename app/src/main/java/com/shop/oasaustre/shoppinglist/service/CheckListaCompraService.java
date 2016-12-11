package com.shop.oasaustre.shoppinglist.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.GastosMensualesActivity;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;

public class CheckListaCompraService extends Service {
    public CheckListaCompraService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        crearNotificacion();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void crearNotificacion(){
        NotificationCompat.Builder notific = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Notificación del informe de gastos")
                .setSmallIcon(R.drawable.ic_notif_shopping)
                .setContentText("Haga click en la notificación para ver el resumen de gastos.");

        Intent intent = new Intent(getApplicationContext(),GastosMensualesActivity.class);

        PendingIntent intencionPendiente = PendingIntent.getActivity(
                getApplicationContext(), 0, intent, 0);

        notific.setLights(Color.MAGENTA,2000,1000);

        notific.setContentIntent(intencionPendiente);

        NotificationManager notificationManager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(AppConstant.ID_NOTIFICATION_LISTA, notific.build());
    }
}
