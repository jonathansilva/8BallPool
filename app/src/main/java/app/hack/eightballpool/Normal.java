package app.hack.eightballpool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import myproject.R;

public class Normal extends View {
    private Paint line, shadow, circle;

    float xOnTouch, yOnTouch;
    float xOnMotion, yOnMotion;
    float xCircle, yCircle;

    int radius = 100;
    int strokeShadowWidth = 42;
    int strokeLineWidth = 6;

    boolean trackStatus;

    public Normal(Context context) {
        super(context);

        init();
    }

    public Normal(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public Normal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        line = new Paint();
        shadow = new Paint();
        circle = new Paint();

        line.setStrokeWidth(strokeLineWidth);
        line.setStrokeCap(Paint.Cap.ROUND);
        line.setColor(Color.WHITE);
        line.setAntiAlias(true);

        shadow.setStrokeWidth(strokeShadowWidth);
        shadow.setStrokeCap(Paint.Cap.ROUND);
        shadow.setColor(Color.WHITE);
        shadow.setAntiAlias(true);
        shadow.setAlpha(100);

        circle.setStyle(Paint.Style.STROKE);
        circle.setStrokeWidth(10);
        circle.setColor(getContext().getColor(R.color.colorRed));
        circle.setAntiAlias(true);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xOnTouch = event.getX();
                yOnTouch = event.getY();

                trackStatus = xOnTouch > (xCircle - radius)
                    && xOnTouch < (xCircle + radius)
                    && yOnTouch > (yCircle - radius)
                    && yOnTouch < (yCircle + radius);

                break;
            case MotionEvent.ACTION_MOVE:
                xOnMotion = event.getX();
                yOnMotion = event.getY();

                xCircle = xOnMotion;
                yCircle = yOnMotion;

                // Top
                if (yCircle < 0) {
                    yCircle = 0;
                }

                // Bottom
                if (yCircle > getHeight()) {
                    yCircle = getHeight();
                }

                // Left
                if (xCircle < 0) {
                    xCircle = 0;
                }

                // Right
                if (xCircle > getWidth()) {
                    xCircle = getWidth();
                }

                trackStatus = true;

                invalidate();

                break;
            case MotionEvent.ACTION_UP:
                trackStatus = false;

                break;
        }

        return trackStatus;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Middle top
        canvas.drawLine(xCircle, yCircle - radius, getWidth() / 2f, 3, shadow);
        canvas.drawLine(xCircle, yCircle - radius, getWidth() / 2f, 3, line);

        // Middle bottom
        canvas.drawLine(xCircle, yCircle + radius, getWidth() / 2f, getHeight() - 3, shadow);
        canvas.drawLine(xCircle, yCircle + radius, getWidth() / 2f, getHeight() - 3, line);

        // Top left
        canvas.drawLine(xCircle - (70 / 100f * radius), yCircle - (70 / 100f * radius), 10, 10, shadow);
        canvas.drawLine(xCircle - (70 / 100f * radius), yCircle - (70 / 100f * radius), 10, 10, line);

        // Top right
        canvas.drawLine(xCircle + (70 / 100f * radius), yCircle - (70 / 100f * radius), getWidth() - 10, 10, shadow);
        canvas.drawLine(xCircle + (70 / 100f * radius), yCircle - (70 / 100f * radius), getWidth() - 10, 10, line);

        // Bottom left
        canvas.drawLine(xCircle - (70 / 100f * radius), yCircle + (70 / 100f * radius), 10, getHeight() - 10, shadow);
        canvas.drawLine(xCircle - (70 / 100f * radius), yCircle + (70 / 100f * radius), 10, getHeight() - 10, line);

        // Bottom right
        canvas.drawLine(xCircle + (70 / 100f * radius), yCircle + (70 / 100f * radius), getWidth() - 10, getHeight() - 10, shadow);
        canvas.drawLine(xCircle + (70 / 100f * radius), yCircle + (70 / 100f * radius), getWidth() - 10, getHeight() - 10, line);

        canvas.drawCircle(xCircle, yCircle, radius, circle);
    }

    public void setPositionCircle(float x, float y) {
        xCircle = x;
        yCircle = y;
    }
}
