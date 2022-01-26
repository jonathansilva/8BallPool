package app.hack.eightballpool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Normal extends View {
    private Paint lineTopLeft;
    private Paint lineTopRight;
    private Paint lineBottomLeft;
    private Paint lineBottomRight;
    private Paint lineMiddleTop;
    private Paint lineMiddleBottom;
    private Paint shadow;
    private Paint circle;

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
        lineMiddleTop = new Paint();
        lineTopLeft = new Paint();
        lineTopRight = new Paint();
        lineMiddleBottom = new Paint();
        lineBottomLeft = new Paint();
        lineBottomRight = new Paint();
        shadow = new Paint();
        circle = new Paint();

        lineMiddleTop.setStrokeWidth(strokeLineWidth);
        lineMiddleTop.setStrokeCap(Paint.Cap.ROUND);
        lineMiddleTop.setColor(Color.WHITE);
        lineMiddleTop.setAntiAlias(true);

        lineTopLeft.setStrokeWidth(strokeLineWidth);
        lineTopLeft.setStrokeCap(Paint.Cap.ROUND);
        lineTopLeft.setColor(Color.WHITE);
        lineTopLeft.setAntiAlias(true);

        lineTopRight.setStrokeWidth(strokeLineWidth);
        lineTopRight.setStrokeCap(Paint.Cap.ROUND);
        lineTopRight.setColor(Color.WHITE);
        lineTopRight.setAntiAlias(true);

        lineMiddleBottom.setStrokeWidth(strokeLineWidth);
        lineMiddleBottom.setStrokeCap(Paint.Cap.ROUND);
        lineMiddleBottom.setColor(Color.WHITE);
        lineMiddleBottom.setAntiAlias(true);

        lineBottomLeft.setStrokeWidth(strokeLineWidth);
        lineBottomLeft.setStrokeCap(Paint.Cap.ROUND);
        lineBottomLeft.setColor(Color.WHITE);
        lineBottomLeft.setAntiAlias(true);

        lineBottomRight.setStrokeWidth(strokeLineWidth);
        lineBottomRight.setStrokeCap(Paint.Cap.ROUND);
        lineBottomRight.setColor(Color.WHITE);
        lineBottomRight.setAntiAlias(true);

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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(xCircle, yCircle - radius, getWidth() / 2f, 3, shadow);
        canvas.drawLine(xCircle, yCircle - radius, getWidth() / 2f, 3, lineMiddleTop);

        canvas.drawLine(xCircle, yCircle + radius, getWidth() / 2f, getHeight() - 3, shadow);
        canvas.drawLine(xCircle, yCircle + radius, getWidth() / 2f, getHeight() - 3, lineMiddleBottom);

        canvas.drawLine(xCircle - (70 / 100f * radius), yCircle - (70 / 100f * radius), 10, 10, shadow);
        canvas.drawLine(xCircle - (70 / 100f * radius), yCircle - (70 / 100f * radius), 10, 10, lineTopLeft);

        canvas.drawLine(xCircle + (70 / 100f * radius), yCircle - (70 / 100f * radius), getWidth() - 10, 10, shadow);
        canvas.drawLine(xCircle + (70 / 100f * radius), yCircle - (70 / 100f * radius), getWidth() - 10, 10, lineTopRight);

        canvas.drawLine(xCircle - (70 / 100f * radius), yCircle + (70 / 100f * radius), 10, getHeight() - 10, shadow);
        canvas.drawLine(xCircle - (70 / 100f * radius), yCircle + (70 / 100f * radius), 10, getHeight() - 10, lineBottomLeft);

        canvas.drawLine(xCircle + (70 / 100f * radius), yCircle + (70 / 100f * radius), getWidth() - 10, getHeight() - 10, shadow);
        canvas.drawLine(xCircle + (70 / 100f * radius), yCircle + (70 / 100f * radius), getWidth() - 10, getHeight() - 10, lineBottomRight);

        canvas.drawCircle(xCircle, yCircle, radius, circle);
    }

    public void setPositionCircle(float x, float y) {
        xCircle = x;
        yCircle = y;
    }
}
