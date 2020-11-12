package app.hack.eightballpool;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.core.app.NotificationCompat;

import java.util.Objects;

import static app.hack.eightballpool.App.CHANNEL_ID;

public class ViewService extends Service {
    private WindowManager windowManager;
    private SensorManager sensorManager;
    private View view;
    private RelativeLayout board;
    private Normal normal;
    private Table table;
    private Button btn_normal, btn_table;
    private MediaPlayer mediaPlayer;

    private float accel, accelCurrent, accelLast;

    public ViewService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("InflateParams")
    @Override
    public void onCreate() {
        super.onCreate();

        view = LayoutInflater.from(this).inflate(R.layout.main, null);

        board = view.findViewById(R.id.board);
        normal = view.findViewById(R.id.normal);
        table = view.findViewById(R.id.table);

        btn_normal = view.findViewById(R.id.btn_normal);
        btn_table = view.findViewById(R.id.btn_table);
        Button btn_close = view.findViewById(R.id.btn_close);

        mediaPlayer = MediaPlayer.create(this, R.raw.touch);

        btn_normal.setOnClickListener(showNormal);
        btn_table.setOnClickListener(showTable);
        btn_close.setOnClickListener(close);

        layoutParams();
        sensorManager();
    }

    private void showNormal() {
        table.setVisibility(View.GONE);
        normal.setVisibility(View.VISIBLE);

        float widthCanvas = (int) getResources().getDimension(R.dimen.canvasWidth);
        float heightCanvas = (int) getResources().getDimension(R.dimen.canvasHeight);

        normal.setPositionCircle(widthCanvas / 2, heightCanvas / 2);

        normal.setRotation(0);
    }

    private void showTable() {
        normal.setVisibility(View.GONE);
        table.setVisibility(View.VISIBLE);

        float widthCanvas = (int) getResources().getDimension(R.dimen.canvasWidth);
        float heightCanvas = (int) getResources().getDimension(R.dimen.canvasHeight);

        table.setPositionCircleOne((widthCanvas / 2) - 200, heightCanvas / 2);
        table.setPositionCircleTwo(200 + (widthCanvas / 2), heightCanvas / 2);

        table.setPositionControls(widthCanvas - 200, 200.0f);

        table.setRotation(0);
    }

    private View.OnClickListener showNormal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.start();
            btn_normal.setBackgroundResource(R.drawable.button_normal_clicked);
            btn_table.setBackgroundResource(R.drawable.button_table);
            showNormal();
        }
    };

    private View.OnClickListener showTable = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.start();
            btn_table.setBackgroundResource(R.drawable.button_table_clicked);
            btn_normal.setBackgroundResource(R.drawable.button_normal);
            showTable();
        }
    };

    private View.OnClickListener close = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.start();
            board.setVisibility(View.GONE);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (Objects.equals(intent.getAction(), "STOP")) {
                stopForegroundService();
            }

            Intent stopNotificationIntent = new Intent(this, ViewService.class);
            stopNotificationIntent.setAction("STOP");
            PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_android)
                    .setContentIntent(pendingIntent)
                    .addAction(0, "ENCERRAR", stopPendingIntent)
                    .build();

            startForeground(1, notification);
        }

        return START_NOT_STICKY;
    }

    private void stopForegroundService() {
        Control.stop(this);
        stopForeground(true);
        stopSelf();
    }

    private void layoutParams() {
        float boardMarginBottom = getResources().getDimension(R.dimen.boardMarginBottom);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        params.verticalMargin = boardMarginBottom;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(view, params);
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float y = event.values[1];
            float z = event.values[2];

            accelLast = accelCurrent;
            accelCurrent = (float) Math.sqrt(y * y + z * z);

            float delta = accelCurrent - accelLast;

            accel = accel * 0.9f + delta;

            if (accel > 8 && accel < 15) {
                board.setVisibility(View.VISIBLE);
                board.setAlpha(0.0f);
                board.animate().setDuration(1000).alpha(1.0f).setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        board.animate().setListener(null);
                    }
                });
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    public void sensorManager() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Objects.requireNonNull(sensorManager)
                .registerListener(
                        mSensorListener,
                        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        SensorManager.SENSOR_DELAY_FASTEST
                );

        accel = 10f;
        accelCurrent = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        sensorManager.unregisterListener(mSensorListener);

        if (view != null) {
            windowManager.removeView(view);
        }
    }
}