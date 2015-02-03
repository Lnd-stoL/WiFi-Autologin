package com.ilovefuncan.wifi_autologin;

import android.app.NotificationManager;
import android.content.*;
import android.net.NetworkInfo;
import android.net.wifi.*;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class WifiEventsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (info != null) {
            if (info.isConnected()) {

                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                LinuxShell.runCommands("ls /");
                notifyAutologin(context, intent, wifiInfo);
            }
        }
    }


    private void notifyAutologin(Context context, Intent intent, WifiInfo wifiInfo) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.abc_btn_radio_to_on_mtrl_000)
            .setContentTitle("WiFi-Autologin")
            .setContentText("Logging into " + wifiInfo.getSSID());

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(001, notificationBuilder.build());
    }
}
