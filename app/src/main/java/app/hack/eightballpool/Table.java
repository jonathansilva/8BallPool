package app.hack.eightballpool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Table extends View {
    private Paint line;
    private Paint reflectLine;
    private Paint circleOne;
    private Paint circleOneMiddle;
    private Paint circleOneMiddleBorder;
    private Paint circleAuxTop;
    private Paint circleAuxBottom;
    private Paint circleTwo;
    private Paint circleAuxControls;
    private Paint circleAuxControlTop;
    private Paint circleAuxControlBottom;
    private Paint circleAuxControlLeft;
    private Paint circleAuxControlRight;

    float xOnTouch, yOnTouch;
    float xOnMotion, yOnMotion;
    float xCircleOne, yCircleOne;
    float xCircleTwo, yCircleTwo;
    float xCircleAuxTop, yCircleAuxTop;
    float xCircleAuxBottom, yCircleAuxBottom;
    float xCircleAuxControls, yCircleAuxControls;
    float xCircleAuxControlTop, yCircleAuxControlTop;
    float xCircleAuxControlBottom, yCircleAuxControlBottom;
    float xCircleAuxControlLeft, yCircleAuxControlLeft;
    float xCircleAuxControlRight, yCircleAuxControlRight;
    float angle;

    int strokeWidth = 6;
    int circle;
    int radiusCircleOne = 100;
    int radiusCircleTwo = 100;
    int radiusCircleAux = 50;
    int radiusCircleAuxControls = 60;
    int radiusCircleAuxControl = 50;

    boolean trackStatus, touchedTheWall;

    public Table(Context context) {
        super(context);

        init();
    }

    public Table(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public Table(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        line = new Paint();
        reflectLine = new Paint();
        circleOne = new Paint();
        circleOneMiddle = new Paint();
        circleOneMiddleBorder = new Paint();
        circleTwo = new Paint();
        circleAuxTop = new Paint();
        circleAuxBottom = new Paint();
        circleAuxControls = new Paint();
        circleAuxControlTop = new Paint();
        circleAuxControlBottom = new Paint();
        circleAuxControlLeft = new Paint();
        circleAuxControlRight = new Paint();

        line.setStrokeWidth(4.5f);
        line.setStrokeCap(Paint.Cap.ROUND);
        line.setColor(Color.WHITE);
        line.setAntiAlias(true);

        reflectLine.setStrokeWidth(strokeWidth);
        reflectLine.setStrokeCap(Paint.Cap.ROUND);
        reflectLine.setPathEffect(new DashPathEffect(new float[] { 10, 20 }, 0));
        reflectLine.setColor(Color.WHITE);
        reflectLine.setAntiAlias(true);

        circleOne.setStyle(Paint.Style.STROKE);
        circleOne.setStrokeWidth(10);
        circleOne.setColor(getContext().getColor(R.color.colorRed));
        circleOne.setAntiAlias(true);

        circleOneMiddle.setStyle(Paint.Style.FILL);
        circleOneMiddle.setColor(getContext().getColor(R.color.colorRed));
        circleOneMiddle.setAntiAlias(true);

        circleOneMiddleBorder.setStyle(Paint.Style.FILL);
        circleOneMiddleBorder.setColor(getContext().getColor(R.color.colorWhite));
        circleOneMiddleBorder.setAntiAlias(true);

        circleTwo.setStyle(Paint.Style.STROKE);
        circleTwo.setStrokeWidth(10);
        circleTwo.setColor(Color.BLACK);
        circleTwo.setAntiAlias(true);

        circleAuxTop.setStyle(Paint.Style.FILL);
        circleAuxTop.setColor(getContext().getColor(R.color.colorAlphaWhite));
        circleAuxTop.setAntiAlias(true);

        circleAuxBottom.setStyle(Paint.Style.FILL);
        circleAuxBottom.setColor(getContext().getColor(R.color.colorAlphaWhite));
        circleAuxBottom.setAntiAlias(true);

        circleAuxControls.setStyle(Paint.Style.FILL);
        circleAuxControls.setColor(getContext().getColor(R.color.colorAlphaWhite));
        circleAuxControls.setAntiAlias(true);

        circleAuxControlTop.setStyle(Paint.Style.FILL);
        circleAuxControlTop.setColor(getContext().getColor(R.color.colorWhite));
        circleAuxControlTop.setAntiAlias(true);

        circleAuxControlBottom.setStyle(Paint.Style.FILL);
        circleAuxControlBottom.setColor(getContext().getColor(R.color.colorWhite));
        circleAuxControlBottom.setAntiAlias(true);

        circleAuxControlLeft.setStyle(Paint.Style.FILL);
        circleAuxControlLeft.setColor(getContext().getColor(R.color.colorWhite));
        circleAuxControlLeft.setAntiAlias(true);

        circleAuxControlRight.setStyle(Paint.Style.FILL);
        circleAuxControlRight.setColor(getContext().getColor(R.color.colorWhite));
        circleAuxControlRight.setAntiAlias(true);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xOnTouch = event.getX();
                yOnTouch = event.getY();

                trackStatus = false;

                if (xOnTouch > (xCircleOne - radiusCircleOne)
                        && xOnTouch < (xCircleOne + radiusCircleOne)
                        && yOnTouch > (yCircleOne - radiusCircleOne)
                        && yOnTouch < (yCircleOne + radiusCircleOne)
                ) {
                    trackStatus = true;
                    circle = 1;
                }

                if (xOnTouch > (xCircleTwo - radiusCircleTwo)
                        && xOnTouch < (xCircleTwo + radiusCircleTwo)
                        && yOnTouch > (yCircleTwo - radiusCircleTwo)
                        && yOnTouch < (yCircleTwo + radiusCircleTwo)
                ) {
                    trackStatus = true;
                    circle = 2;
                }

                if (xOnTouch > (xCircleAuxTop - radiusCircleAux)
                        && xOnTouch < (xCircleAuxTop + radiusCircleAux)
                        && yOnTouch > (yCircleAuxTop - radiusCircleAux)
                        && yOnTouch < (yCircleAuxTop + radiusCircleAux)
                ) {
                    trackStatus = true;
                    circle = 3;
                }

                if (xOnTouch > (xCircleAuxBottom - radiusCircleAux)
                        && xOnTouch < (xCircleAuxBottom + radiusCircleAux)
                        && yOnTouch > (yCircleAuxBottom - radiusCircleAux)
                        && yOnTouch < (yCircleAuxBottom + radiusCircleAux)
                ) {
                    trackStatus = true;
                    circle = 4;
                }

                if (xOnTouch > (xCircleAuxControls - radiusCircleAuxControls)
                        && xOnTouch < (xCircleAuxControls + radiusCircleAuxControls)
                        && yOnTouch > (yCircleAuxControls - radiusCircleAuxControls)
                        && yOnTouch < (yCircleAuxControls + radiusCircleAuxControls)
                ) {
                    trackStatus = true;
                    circle = 5;
                }

                // Control: Top
                if (xOnTouch > (xCircleAuxControlTop - radiusCircleAuxControl)
                        && xOnTouch < (xCircleAuxControlTop + radiusCircleAuxControl)
                        && yOnTouch > (yCircleAuxControlTop - radiusCircleAuxControl)
                        && yOnTouch < (yCircleAuxControlTop + radiusCircleAuxControl)
                ) {
                    yCircleOne = yCircleOne - 1.0f;

                    xCircleAuxTop = xCircleOne + 110;
                    yCircleAuxTop = yCircleOne - 140;

                    xCircleAuxBottom = xCircleOne - 110;
                    yCircleAuxBottom = yCircleOne + 140;
                }

                // Control: Bottom
                if (xOnTouch > (xCircleAuxControlBottom - radiusCircleAuxControl)
                        && xOnTouch < (xCircleAuxControlBottom + radiusCircleAuxControl)
                        && yOnTouch > (yCircleAuxControlBottom - radiusCircleAuxControl)
                        && yOnTouch < (yCircleAuxControlBottom + radiusCircleAuxControl)
                ) {
                    yCircleOne = yCircleOne + 1.0f;

                    xCircleAuxTop = xCircleOne + 110;
                    yCircleAuxTop = yCircleOne - 140;

                    xCircleAuxBottom = xCircleOne - 110;
                    yCircleAuxBottom = yCircleOne + 140;
                }

                // Control: Left
                if (xOnTouch > (xCircleAuxControlLeft - radiusCircleAuxControl)
                        && xOnTouch < (xCircleAuxControlLeft + radiusCircleAuxControl)
                        && yOnTouch > (yCircleAuxControlLeft - radiusCircleAuxControl)
                        && yOnTouch < (yCircleAuxControlLeft + radiusCircleAuxControl)
                ) {
                    xCircleOne = xCircleOne - 1.0f;

                    xCircleAuxTop = xCircleOne + 110;
                    yCircleAuxTop = yCircleOne - 140;

                    xCircleAuxBottom = xCircleOne - 110;
                    yCircleAuxBottom = yCircleOne + 140;
                }

                // Control: Right
                if (xOnTouch > (xCircleAuxControlRight - radiusCircleAuxControl)
                        && xOnTouch < (xCircleAuxControlRight + radiusCircleAuxControl)
                        && yOnTouch > (yCircleAuxControlRight - radiusCircleAuxControl)
                        && yOnTouch < (yCircleAuxControlRight + radiusCircleAuxControl)
                ) {
                    xCircleOne = xCircleOne + 1.0f;

                    xCircleAuxTop = xCircleOne + 110;
                    yCircleAuxTop = yCircleOne - 140;

                    xCircleAuxBottom = xCircleOne - 110;
                    yCircleAuxBottom = yCircleOne + 140;
                }

                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                xOnMotion = event.getX();
                yOnMotion = event.getY();

                if (circle == 1) {
                    xCircleOne = xOnMotion;
                    yCircleOne = yOnMotion;

                    xCircleAuxTop = xCircleOne + 110;
                    yCircleAuxTop = yCircleOne - 140;

                    xCircleAuxBottom = xCircleOne - 110;
                    yCircleAuxBottom = yCircleOne + 140;

                    checkCircleOneCollision();
                }

                if (circle == 2) {
                    xCircleTwo = xOnMotion;
                    yCircleTwo = yOnMotion;

                    touchedTheWall = false;

                    // Top
                    if (yOnMotion < 0) {
                        yCircleTwo = 0;
                        touchedTheWall = true;
                    }

                    // Bottom
                    if (yOnMotion > getHeight()) {
                        yCircleTwo = getHeight();
                        touchedTheWall = true;
                    }

                    // Left
                    if (xOnMotion < 0) {
                        xCircleTwo = 0;
                        touchedTheWall = true;
                    }

                    // Right
                    if (xOnMotion > getWidth()) {
                        xCircleTwo = getWidth();
                        touchedTheWall = true;
                    }
                }

                if (circle == 3) {
                    xCircleAuxTop = xOnMotion;
                    yCircleAuxTop = yOnMotion;

                    xCircleOne = xCircleAuxTop - 110;
                    yCircleOne = yCircleAuxTop + 140;

                    xCircleAuxBottom = xCircleOne - 110;
                    yCircleAuxBottom = yCircleOne + 140;

                    checkCircleOneCollision();
                }

                if (circle == 4) {
                    xCircleAuxBottom = xOnMotion;
                    yCircleAuxBottom = yOnMotion;

                    xCircleOne = xCircleAuxBottom + 110;
                    yCircleOne = yCircleAuxBottom - 140;

                    xCircleAuxTop = xCircleOne + 110;
                    yCircleAuxTop = yCircleOne - 140;

                    checkCircleOneCollision();
                }

                if (circle == 5) {
                    yCircleAuxControls = yOnMotion;

                    xCircleAuxControlTop = xCircleAuxControls;
                    yCircleAuxControlTop = yCircleAuxControls - 140;

                    xCircleAuxControlBottom = xCircleAuxControls;
                    yCircleAuxControlBottom = yCircleAuxControls + 140;

                    xCircleAuxControlLeft = xCircleAuxControls - 140;
                    yCircleAuxControlLeft = yCircleAuxControls;

                    xCircleAuxControlRight = xCircleAuxControls + 140;
                    yCircleAuxControlRight = yCircleAuxControls;

                    checkCircleAuxControlsCollision();
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

        canvas.drawLine(xCircleOne, yCircleOne, xCircleTwo, yCircleTwo, line);

        calculateAngle();

        // Top or Bottom
        if (touchedTheWall) {

            // Left or Right
            if (xCircleTwo == getWidth() || xCircleTwo == 0) {
                angle -= 180;
            }

            drawReflectLine(canvas);
        }

        canvas.drawCircle(xCircleOne, yCircleOne, radiusCircleOne, circleOne);
        canvas.drawCircle(xCircleOne, yCircleOne, 14, circleOneMiddleBorder);
        canvas.drawCircle(xCircleOne, yCircleOne, 12, circleOneMiddle);
        canvas.drawCircle(xCircleTwo, yCircleTwo, radiusCircleTwo, circleTwo);
        canvas.drawCircle(xCircleAuxTop, yCircleAuxTop, radiusCircleAux, circleAuxTop);
        canvas.drawCircle(xCircleAuxBottom, yCircleAuxBottom, radiusCircleAux, circleAuxBottom);
        canvas.drawCircle(xCircleAuxControls, yCircleAuxControls, radiusCircleAuxControls, circleAuxControls);
        canvas.drawCircle(xCircleAuxControlTop, yCircleAuxControlTop, radiusCircleAuxControl, circleAuxControlTop);
        canvas.drawCircle(xCircleAuxControlBottom, yCircleAuxControlBottom, radiusCircleAuxControl, circleAuxControlBottom);
        canvas.drawCircle(xCircleAuxControlLeft, yCircleAuxControlLeft, radiusCircleAuxControl, circleAuxControlLeft);
        canvas.drawCircle(xCircleAuxControlRight, yCircleAuxControlRight, radiusCircleAuxControl, circleAuxControlRight);
    }

    private void checkCircleOneCollision() {
        float controlWidth = 90 + (radiusCircleAuxControl * 4) + (radiusCircleAuxControls * 2);

        if (xCircleOne >= (getWidth() - controlWidth)) {
            xCircleAuxControls = 200;
            xCircleAuxControlTop = xCircleAuxControls;
            xCircleAuxControlBottom = xCircleAuxControls;
            xCircleAuxControlLeft = xCircleAuxControls - 140;
            xCircleAuxControlRight = xCircleAuxControls + 140;
        }

        if (xCircleOne <= controlWidth) {
            xCircleAuxControls = getWidth() - 200;
            xCircleAuxControlTop = xCircleAuxControls;
            xCircleAuxControlBottom = xCircleAuxControls;
            xCircleAuxControlLeft = xCircleAuxControls - 140;
            xCircleAuxControlRight = xCircleAuxControls + 140;
        }

        if (xCircleOne < 0) {
            xCircleOne = 0;
            xCircleAuxTop = xCircleOne + 110;
            yCircleAuxTop = yCircleOne - 140;
        }

        if (yCircleOne < 0) {
            yCircleOne = 0;
            xCircleAuxBottom = xCircleOne - 110;
            yCircleAuxBottom = yCircleOne + 140;
        }

        if (xCircleOne > getWidth()) {
            xCircleOne = getWidth();
            xCircleAuxBottom = xCircleOne - 110;
            yCircleAuxBottom = yCircleOne + 140;
        }

        if (yCircleOne > getHeight()) {
            yCircleOne = getHeight();
            xCircleAuxTop = xCircleOne + 110;
            yCircleAuxTop = yCircleOne - 140;
        }
    }

    private void checkCircleAuxControlsCollision() {
        if ((yCircleAuxControlTop - radiusCircleAuxControl) < 0) {
            yCircleAuxControls = 200;
            yCircleAuxControlTop = yCircleAuxControls - 140;
            yCircleAuxControlBottom = yCircleAuxControls + 140;
            yCircleAuxControlLeft = yCircleAuxControls;
            yCircleAuxControlRight = yCircleAuxControls;
        }

        if ((yCircleAuxControlBottom + radiusCircleAuxControl) > getHeight()) {
            yCircleAuxControls = getHeight() - 200;
            yCircleAuxControlTop = yCircleAuxControls - 140;
            yCircleAuxControlBottom = yCircleAuxControls + 140;
            yCircleAuxControlLeft = yCircleAuxControls;
            yCircleAuxControlRight = yCircleAuxControls;
        }
    }

    private void drawReflectLine(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, xCircleTwo, yCircleTwo);
        canvas.drawLine(xCircleTwo, yCircleTwo, xCircleTwo + 1500, yCircleTwo, reflectLine);
        canvas.restore();
    }

    private void calculateAngle() {
        float deltaX = xCircleTwo - xCircleOne;
        float deltaY = yCircleOne - yCircleTwo;

        angle = (float) Math.toDegrees(Math.atan2( deltaY, deltaX ));

        if (angle < 0) {
            angle += 360;
        }
    }

    public void setPositionCircleOne(float x, float y) {
        xCircleOne = x;
        yCircleOne = y;

        xCircleAuxTop = x + 110;
        yCircleAuxTop = y - 140;

        xCircleAuxBottom = x - 110;
        yCircleAuxBottom = y + 140;
    }

    public void setPositionCircleTwo(float x, float y) {
        xCircleTwo = x;
        yCircleTwo = y;
    }

    public void setPositionControls(float x, float y) {
        xCircleAuxControls = x;
        yCircleAuxControls = y;

        xCircleAuxControlTop = xCircleAuxControls;
        yCircleAuxControlTop = yCircleAuxControls - 140;

        xCircleAuxControlBottom = xCircleAuxControls;
        yCircleAuxControlBottom = yCircleAuxControls + 140;

        xCircleAuxControlLeft = xCircleAuxControls - 140;
        yCircleAuxControlLeft = yCircleAuxControls;

        xCircleAuxControlRight = xCircleAuxControls + 140;
        yCircleAuxControlRight = yCircleAuxControls;
    }
}
