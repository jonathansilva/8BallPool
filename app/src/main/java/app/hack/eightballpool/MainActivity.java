package app.hack.eightballpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 2084;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Settings.canDrawOverlays(this)) {
            startActivityForResult(
                    new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())),
                    REQUEST_CODE
            );
        } else {
            init();
        }

        // Dimensions of device
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int deviceWidth = metrics.widthPixels;
        int deviceHeight = metrics.heightPixels;

        System.out.println("DEBUG - Device Width: " + deviceWidth);
        System.out.println("DEBUG - Device Height: " + deviceHeight);
    }

    public void init() {
        Control.start(this);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                init();
            } else {
                Toast.makeText(this, "Permission not available", Toast.LENGTH_SHORT).show();
                finish();
            }

            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}