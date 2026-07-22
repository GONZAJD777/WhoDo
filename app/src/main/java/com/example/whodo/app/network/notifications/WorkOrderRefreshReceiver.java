package com.example.whodo.app.network.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WorkOrderRefreshReceiver extends BroadcastReceiver {

    private final String TAG = "LOGGER-BROADCAST-RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG", "Señal de actualización recibida");
        RefreshEventBus.getInstance().postRefreshSignal();
    }
}

