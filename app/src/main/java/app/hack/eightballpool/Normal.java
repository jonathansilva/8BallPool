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
    private Paint lineMiddleTop;
    private Paint lineTopLeft;
    private Paint lineTopRight;
    private Paint lineMiddleBottom;
    private Paint lineBottomLeft;
    private Paint lineBottomRight;
    private Paint circle;

    float xonTouch, yonTouch, xonMotion, yonMotion;
    float xCircle, yCircle;
    float radiusCircle;

    int strokeWidth = 6;
    int shadowRadius = 6;

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
        circle = new Paint();

        lineMiddleTop.setStrokeWidth(strokeWidth);
        lineMiddleTop.setStrokeCap(Paint.Cap.ROUND);
        lineMiddleTop.setColor(Color.WHITE);
        lineMiddleTop.setShadowLayer(shadowRadius, 0, 0, Color.BLACK);
        lineMiddleTop.setAntiAlias(true);

        lineTopLeft.setStrokeWidth(strokeWidth);
        lineTopLeft.setStrokeCap(Paint.Cap.ROUND);
        lineTopLeft.setColor(Color.WHITE);
        lineTopLeft.setShadowLayer(shadowRadius, 0, 0, Color.BLACK);
        lineTopLeft.setAntiAlias(true);

        lineTopRight.setStrokeWidth(strokeWidth);
        lineTopRight.setStrokeCap(Paint.Cap.ROUND);
        lineTopRight.setColor(Color.WHITE);
        lineTopRight.setShadowLayer(shadowRadius, 0, 0, Color.BLACK);
        lineTopRight.setAntiAlias(true);

        lineMiddleBottom.setStrokeWidth(strokeWidth);
        lineMiddleBottom.setStrokeCap(Paint.Cap.ROUND);
        lineMiddleBottom.setColor(Color.WHITE);
        lineMiddleBottom.setShadowLayer(shadowRadius, 0, 0, Color.BLACK);
        lineMiddleBottom.setAntiAlias(true);

        lineBottomLeft.setStrokeWidth(strokeWidth);
        lineBottomLeft.setStrokeCap(Paint.Cap.ROUND);
        lineBottomLeft.setColor(Color.WHITE);
        lineBottomLeft.setShadowLayer(shadowRadius, 0, 0, Color.BLACK);
        lineBottomLeft.setAntiAlias(true);

        lineBottomRight.setStrokeWidth(strokeWidth);
        lineBottomRight.setStrokeCap(Paint.Cap.ROUND);
        lineBottomRight.setColor(Color.WHITE);
        lineBottomRight.setShadowLayer(shadowRadius, 0, 0, Color.BLACK);
        lineBottomRight.setAntiAlias(true);

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
                xonTouch = event.getX();
                yonTouch = event.getY();

                trackStatus = xonTouch > (xCircle - radiusCircle)
                        && xonTouch < (xCircle + radiusCircle)
                        && yonTouch > (yCircle - radiusCircle)
                        && yonTouch < (yCircle + radiusCircle);

                break;
            case MotionEvent.ACTION_MOVE:
                xonMotion = event.getX();
                yonMotion = event.getY();

                xCircle = xonMotion;
                yCircle = yonMotion;

                // Left
                if (xCircle < 0) {
                    xCircle = 0;
                }

                // Top
                if (yCircle < 0) {
                    yCircle = 0;
                }

                // Right
                if (xCircle > getWidth()) {
                    xCircle = getWidth();
                }

                // Bottom
                if (yCircle > getHeight()) {
                    yCircle = getHeight();
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

        canvas.drawLine(xCircle, yCircle - radiusCircle, getWidth() / 2f, 3, lineMiddleTop);
        canvas.drawLine(xCircle, yCircle + radiusCircle, getWidth() / 2f, getHeight() - 3, lineMiddleBottom);
        canvas.drawLine(xCircle - (70 / 100f * radiusCircle), yCircle - (70 / 100f * radiusCircle), 10, 10, lineTopLeft);
        canvas.drawLine(xCircle + (70 / 100f * radiusCircle), yCircle - (70 / 100f * radiusCircle), getWidth() - 10, 10, lineTopRight);
        canvas.drawLine(xCircle - (70 / 100f * radiusCircle), yCircle + (70 / 100f * radiusCircle), 10, getHeight() - 10, lineBottomLeft);
        canvas.drawLine(xCircle + (70 / 100f * radiusCircle), yCircle + (70 / 100f * radiusCircle), getWidth() - 10, getHeight() - 10, lineBottomRight);

        canvas.drawCircle(xCircle, yCircle, radiusCircle, circle);
    }

    public void setCenterCanvas(float x, float y) {
        xCircle = x;
        yCircle = y;
    }

    public void setCircleRadiusRatio(float ratio){
        radiusCircle = ratio;
    }
}