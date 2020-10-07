package app.hack.eightballpool;

import android.content.Context;
import android.content.Intent;

class Helper {
    static void startGame(Context context) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.miniclip.eightballpool");

        if (launchIntent != null) {
            context.startActivity(launchIntent);
        }
    }
}
