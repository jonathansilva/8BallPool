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

import myproject.R;

public class ViewService extends Service {
    private WindowManager windowManager;
    private SensorManager sensorManager;
    private View view;
    private RelativeLayout board;
    private Normal normal;
    private Trickshot trickshot;
    private NineBall nineBall;
    private Button btn_normal, btn_trickshot, btn_trickshot_second_line, btn_nineBall;
    private MediaPlayer mediaPlayer;

    private float accel, accelCurrent, accelLast;

    boolean secondLine = false;

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
        trickshot = view.findViewById(R.id.trickshot);
        nineBall = view.findViewById(R.id.nineBall);

        btn_normal = view.findViewById(R.id.btn_normal);
        btn_trickshot = view.findViewById(R.id.btn_trickshot);
        btn_trickshot_second_line = view.findViewById(R.id.btn_trickshot_second_ine);
        btn_nineBall = view.findViewById(R.id.btn_nineBall);

        Button btn_hide = view.findViewById(R.id.btn_hide);

        mediaPlayer = MediaPlayer.create(this, R.raw.touch);

        btn_normal.setOnClickListener(showNormal);
        btn_trickshot.setOnClickListener(showTrickshot);
        btn_trickshot_second_line.setOnClickListener(showSecondLine);
        btn_nineBall.setOnClickListener(showNineBall);
        btn_hide.setOnClickListener(hide);

        layoutParams();
        sensorManager();
    }

    private void showNormal() {
        trickshot.setVisibility(View.GONE);
        nineBall.setVisibility(View.GONE);
        normal.setVisibility(View.VISIBLE);

        btn_trickshot_second_line.setVisibility(View.GONE);

        float widthCanvas = (int) getResources().getDimension(R.dimen.canvasWidth);
        float heightCanvas = (int) getResources().getDimension(R.dimen.canvasHeight);

        normal.setPositionCircle((widthCanvas / 2f), (heightCanvas / 2f));
        normal.setRotation(0);
    }

    private void showTrickshot() {
        normal.setVisibility(View.GONE);
        nineBall.setVisibility(View.GONE);
        trickshot.setVisibility(View.VISIBLE);

        btn_trickshot_second_line.setVisibility(View.VISIBLE);

        float widthCanvas = (int) getResources().getDimension(R.dimen.canvasWidth);
        float heightCanvas = (int) getResources().getDimension(R.dimen.canvasHeight);

        // Bug fix
        trickshot.resetLines();

        trickshot.setPositionCircleOne((widthCanvas / 2f) - 200, (heightCanvas / 2f));
        trickshot.setPositionCircleTwo((widthCanvas / 2f) + 200, (heightCanvas / 2f));

        trickshot.setPositionControls(widthCanvas - 200, 200);
        trickshot.setRotation(0);
    }

    private void showSecondLine() {
        secondLine =! secondLine;

        if (!secondLine) {
            btn_trickshot_second_line.setBackgroundResource(R.drawable.button_trickshot_second_line);
        }

        trickshot.secondLine(secondLine);
    }

    private void showNineBall() {
        normal.setVisibility(View.GONE);
        trickshot.setVisibility(View.GONE);
        nineBall.setVisibility(View.VISIBLE);

        btn_trickshot_second_line.setVisibility(View.GONE);

        float widthCanvas = (int) getResources().getDimension(R.dimen.canvasWidth);
        float heightCanvas = (int) getResources().getDimension(R.dimen.canvasHeight);

        // Start line
        float left = widthCanvas - 327;
        float top = heightCanvas - 300;

        // End line
        float right = widthCanvas - 290;
        float bottom = heightCanvas - 282.5f;

        nineBall.setPositionCircleOne((widthCanvas / 2f) + 345.5f, (heightCanvas / 2f) + 39f);
        nineBall.setPositionCircleTwo((widthCanvas / 2f) - 254, (heightCanvas / 2f) - 136.5f);

        nineBall.setPositionLine(left, top, right, bottom);
        nineBall.setRotation(0);
    }

    private final View.OnClickListener showNormal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.start();

            btn_normal.setBackgroundResource(R.drawable.button_normal_clicked);
            btn_trickshot.setBackgroundResource(R.drawable.button_trickshot);
            btn_nineBall.setBackgroundResource(R.drawable.button_nineball);

            showNormal();
        }
    };

    private final View.OnClickListener showTrickshot = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.start();

            btn_trickshot.setBackgroundResource(R.drawable.button_trickshot_clicked);
            btn_normal.setBackgroundResource(R.drawable.button_normal);
            btn_nineBall.setBackgroundResource(R.drawable.button_nineball);

            showTrickshot();
        }
    };

    private final View.OnClickListener showSecondLine = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.start();

            btn_trickshot_second_line.setBackgroundResource(R.drawable.button_trickshot_second_line_clicked);

            showSecondLine();
        }
    };

    private final View.OnClickListener showNineBall = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.start();

            btn_normal.setBackgroundResource(R.drawable.button_normal);
            btn_trickshot.setBackgroundResource(R.drawable.button_trickshot);
            btn_nineBall.setBackgroundResource(R.drawable.button_nineball_clicked);

            showNineBall();
        }
    };

    private final View.OnClickListener hide = new View.OnClickListener() {
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

            @SuppressLint("UnspecifiedImmutableFlag")
            PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent notificationIntent = new Intent(this, MainActivity.class);

            @SuppressLint("UnspecifiedImmutableFlag")
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .addAction(0, getString(R.string.close), stopPendingIntent)
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
