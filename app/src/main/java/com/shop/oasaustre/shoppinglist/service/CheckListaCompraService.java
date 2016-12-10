package com.shop.oasaustre.shoppinglist.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.shop.oasaustre.shoppinglist.R;
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
                .setContentTitle("Notificación Lista de la Compra")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Texto descriptivo");

        /*Intent intencionPararMusica = new Intent(context,PararMusicaActivity.class);
        intencionPararMusica.putExtra("cuerpoSMS",message.getMessageBody());

        PendingIntent intencionPendiente = PendingIntent.getActivity(
                context, 0, intencionPararMusica, 0);

        notific.setContentIntent(intencionPendiente);*/

        NotificationManager notificationManager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(AppConstant.ID_NOTIFICATION_LISTA, notific.build());
    }
}
