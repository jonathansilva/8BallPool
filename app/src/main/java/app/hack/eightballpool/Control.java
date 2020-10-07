package app.hack.eightballpool;

import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

public class Control {
    static void start(Context context) {
        Helper.startGame(context);
        ContextCompat.startForegroundService(context, new Intent(context, ViewService.class));
    }

    static void stop(Context context) {
        context.stopService(new Intent(context, ViewService.class));
    }
}
