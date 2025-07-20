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

import androidx.annotation.NonNull;

import myproject.R;

public class Trickshot extends View {
    private Paint line;
    private Paint reflectLine;
    private Paint circleOne;
    private Paint circleOneMiddle;
    private Paint circleOneMiddleBorder;
    private Paint circleOneAuxTop;
    private Paint circleOneAuxBottom;
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
    float xCircleOneAuxTop, yCircleOneAuxTop;
    float xCircleOneAuxBottom, yCircleOneAuxBottom;
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

    int radiusBall = 22;

    boolean trackStatus, touchedTheWall;

    boolean showSecondLine = false;

    public Trickshot(Context context) {
        super(context);

        init();
    }

    public Trickshot(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public Trickshot(Context context, AttributeSet attrs, int defStyleAttr) {
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
        circleOneAuxTop = new Paint();
        circleOneAuxBottom = new Paint();
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

        circleOneAuxTop.setStyle(Paint.Style.FILL);
        circleOneAuxTop.setColor(getContext().getColor(R.color.colorAlphaWhite));
        circleOneAuxTop.setAntiAlias(true);

        circleOneAuxBottom.setStyle(Paint.Style.FILL);
        circleOneAuxBottom.setColor(getContext().getColor(R.color.colorAlphaWhite));
        circleOneAuxBottom.setAntiAlias(true);

        circleTwo.setStyle(Paint.Style.FILL);
        circleTwo.setColor(getContext().getColor(R.color.colorWhite));
        circleTwo.setAntiAlias(true);

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

                if (xOnTouch > (xCircleOneAuxTop - radiusCircleAux)
                    && xOnTouch < (xCircleOneAuxTop + radiusCircleAux)
                    && yOnTouch > (yCircleOneAuxTop - radiusCircleAux)
                    && yOnTouch < (yCircleOneAuxTop + radiusCircleAux)
                ) {
                    trackStatus = true;
                    circle = 3;
                }

                if (xOnTouch > (xCircleOneAuxBottom - radiusCircleAux)
                    && xOnTouch < (xCircleOneAuxBottom + radiusCircleAux)
                    && yOnTouch > (yCircleOneAuxBottom - radiusCircleAux)
                    && yOnTouch < (yCircleOneAuxBottom + radiusCircleAux)
                ) {
                    trackStatus = true;
                    circle = 4;
                }

                // Control: Center
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

                    xCircleOneAuxTop = xCircleOne + 110;
                    yCircleOneAuxTop = yCircleOne - 140;

                    xCircleOneAuxBottom = xCircleOne - 110;
                    yCircleOneAuxBottom = yCircleOne + 140;
                }

                // Control: Bottom
                if (xOnTouch > (xCircleAuxControlBottom - radiusCircleAuxControl)
                    && xOnTouch < (xCircleAuxControlBottom + radiusCircleAuxControl)
                    && yOnTouch > (yCircleAuxControlBottom - radiusCircleAuxControl)
                    && yOnTouch < (yCircleAuxControlBottom + radiusCircleAuxControl)
                ) {
                    yCircleOne = yCircleOne + 1.0f;

                    xCircleOneAuxTop = xCircleOne + 110;
                    yCircleOneAuxTop = yCircleOne - 140;

                    xCircleOneAuxBottom = xCircleOne - 110;
                    yCircleOneAuxBottom = yCircleOne + 140;
                }

                // Control: Left
                if (xOnTouch > (xCircleAuxControlLeft - radiusCircleAuxControl)
                    && xOnTouch < (xCircleAuxControlLeft + radiusCircleAuxControl)
                    && yOnTouch > (yCircleAuxControlLeft - radiusCircleAuxControl)
                    && yOnTouch < (yCircleAuxControlLeft + radiusCircleAuxControl)
                ) {
                    xCircleOne = xCircleOne - 1.0f;

                    xCircleOneAuxTop = xCircleOne + 110;
                    yCircleOneAuxTop = yCircleOne - 140;

                    xCircleOneAuxBottom = xCircleOne - 110;
                    yCircleOneAuxBottom = yCircleOne + 140;
                }

                // Control: Right
                if (xOnTouch > (xCircleAuxControlRight - radiusCircleAuxControl)
                    && xOnTouch < (xCircleAuxControlRight + radiusCircleAuxControl)
                    && yOnTouch > (yCircleAuxControlRight - radiusCircleAuxControl)
                    && yOnTouch < (yCircleAuxControlRight + radiusCircleAuxControl)
                ) {
                    xCircleOne = xCircleOne + 1.0f;

                    xCircleOneAuxTop = xCircleOne + 110;
                    yCircleOneAuxTop = yCircleOne - 140;

                    xCircleOneAuxBottom = xCircleOne - 110;
                    yCircleOneAuxBottom = yCircleOne + 140;
                }

                invalidate();

                break;
            case MotionEvent.ACTION_MOVE:
                xOnMotion = event.getX();
                yOnMotion = event.getY();

                if (circle == 1) {
                    xCircleOne = xOnMotion;
                    yCircleOne = yOnMotion;

                    xCircleOneAuxTop = xCircleOne + 110;
                    yCircleOneAuxTop = yCircleOne - 140;

                    xCircleOneAuxBottom = xCircleOne - 110;
                    yCircleOneAuxBottom = yCircleOne + 140;

                    checkCircleOneCollision();
                }

                if (circle == 2) {
                    xCircleTwo = xOnMotion;
                    yCircleTwo = yOnMotion;

                    touchedTheWall = false;

                    // Top
                    if (yOnMotion < radiusBall) {
                        yCircleTwo = radiusBall;
                        touchedTheWall = true;
                    }

                    // Bottom
                    if (yOnMotion > (getHeight() - radiusBall)) {
                        yCircleTwo = getHeight() - radiusBall;
                        touchedTheWall = true;
                    }

                    // Left
                    if (xOnMotion < radiusBall) {
                        xCircleTwo = radiusBall;
                        touchedTheWall = true;
                    }

                    // Right
                    if (xOnMotion > (getWidth() - radiusBall)) {
                        xCircleTwo = getWidth() - radiusBall;
                        touchedTheWall = true;
                    }
                }

                if (circle == 3) {
                    xCircleOneAuxTop = xOnMotion;
                    yCircleOneAuxTop = yOnMotion;

                    xCircleOne = xCircleOneAuxTop - 110;
                    yCircleOne = yCircleOneAuxTop + 140;

                    xCircleOneAuxBottom = xCircleOne - 110;
                    yCircleOneAuxBottom = yCircleOne + 140;

                    checkCircleOneCollision();
                }

                if (circle == 4) {
                    xCircleOneAuxBottom = xOnMotion;
                    yCircleOneAuxBottom = yOnMotion;

                    xCircleOne = xCircleOneAuxBottom + 110;
                    yCircleOne = yCircleOneAuxBottom - 140;

                    xCircleOneAuxTop = xCircleOne + 110;
                    yCircleOneAuxTop = yCircleOne - 140;

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
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(xCircleOne, yCircleOne, xCircleTwo, yCircleTwo, line);

        calculateAngle();

        if (touchedTheWall) {
            float reflectedAngleOne = getReflectedAngleOne();

            LineIntersection firstReflectionEnd = LineIntersection.getLineIntersectionPoint(
                xCircleTwo, yCircleTwo, reflectedAngleOne, getWidth(), getHeight(), radiusBall
            );

            canvas.drawLine(xCircleTwo, yCircleTwo, firstReflectionEnd.endX, firstReflectionEnd.endY, reflectLine);
            canvas.drawCircle(firstReflectionEnd.endX, firstReflectionEnd.endY, radiusBall, circleTwo);

            if (showSecondLine) {
                float reflectedAngleTwo = getReflectedAngleTwo(reflectedAngleOne, firstReflectionEnd);

                LineIntersection secondReflectionEnd = LineIntersection.getLineIntersectionPoint(
                    firstReflectionEnd.endX, firstReflectionEnd.endY, reflectedAngleTwo, getWidth(), getHeight(), radiusBall
                );

                canvas.drawLine(firstReflectionEnd.endX, firstReflectionEnd.endY, secondReflectionEnd.endX, secondReflectionEnd.endY, reflectLine);
                canvas.drawCircle(secondReflectionEnd.endX, secondReflectionEnd.endY, radiusBall, circleTwo);
            }
        }

        canvas.drawCircle(xCircleOne, yCircleOne, radiusCircleOne, circleOne);
        canvas.drawCircle(xCircleOne, yCircleOne, 14, circleOneMiddleBorder);
        canvas.drawCircle(xCircleOne, yCircleOne, 12, circleOneMiddle);
        canvas.drawCircle(xCircleTwo, yCircleTwo, radiusBall, circleTwo);
        canvas.drawCircle(xCircleOneAuxTop, yCircleOneAuxTop, radiusCircleAux, circleOneAuxTop);
        canvas.drawCircle(xCircleOneAuxBottom, yCircleOneAuxBottom, radiusCircleAux, circleOneAuxBottom);
        canvas.drawCircle(xCircleAuxControls, yCircleAuxControls, radiusCircleAuxControls, circleAuxControls);
        canvas.drawCircle(xCircleAuxControlTop, yCircleAuxControlTop, radiusCircleAuxControl, circleAuxControlTop);
        canvas.drawCircle(xCircleAuxControlBottom, yCircleAuxControlBottom, radiusCircleAuxControl, circleAuxControlBottom);
        canvas.drawCircle(xCircleAuxControlLeft, yCircleAuxControlLeft, radiusCircleAuxControl, circleAuxControlLeft);
        canvas.drawCircle(xCircleAuxControlRight, yCircleAuxControlRight, radiusCircleAuxControl, circleAuxControlRight);
    }

    private float getReflectedAngleOne() {
        float reflectedAngleOne = angle;

        // Top
        if (yCircleTwo == radiusBall) {
            reflectedAngleOne = 360 - angle;
        }

        // Bottom
        if (yCircleTwo == (getHeight() - radiusBall)) {
            reflectedAngleOne = 360 - angle;
        }

        // Left
        if (xCircleTwo == radiusBall) {
            reflectedAngleOne = 180 - angle;
        }

        // Right
        if (xCircleTwo == (getWidth() - radiusBall)) {
            reflectedAngleOne = 180 - angle;
        }

        if (reflectedAngleOne < 0) {
            reflectedAngleOne += 360;
        }

        if (reflectedAngleOne >= 360) {
            reflectedAngleOne -= 360;
        }

        return reflectedAngleOne;
    }

    private static float getReflectedAngleTwo(float reflectedAngleOne, LineIntersection firstReflectionEnd) {
        float incidentAngleTwo; // O ângulo refletido da primeira linha se torna o incidente para a segunda

        incidentAngleTwo = reflectedAngleOne;

        float reflectedAngleTwo = incidentAngleTwo;

        int wallHitTwo = firstReflectionEnd.wallHit; // A parede atingida pela primeira reflexão

        // Determina o segundo ângulo refletido com base na parede atingida pela primeira reflexão
        if (wallHitTwo == 1 || wallHitTwo == 2) { // Parede superior ou inferior atingida pela primeira reflexão
            reflectedAngleTwo = 360 - incidentAngleTwo;
        }

        if (wallHitTwo == 3 || wallHitTwo == 4) { // Parede esquerda ou direita atingida pela primeira reflexão
            reflectedAngleTwo = 180 - incidentAngleTwo;
        }

        // Normaliza o segundo ângulo refletido para estar entre 0 e 360 graus
        if (reflectedAngleTwo < 0) {
            reflectedAngleTwo += 360;
        }

        if (reflectedAngleTwo >= 360) {
            reflectedAngleTwo -= 360;
        }

        return reflectedAngleTwo;
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

        if (xCircleOne < radiusBall) {
            xCircleOne = radiusBall;
            xCircleOneAuxTop = xCircleOne + 110;
            yCircleOneAuxTop = yCircleOne - 140;
        }

        if (yCircleOne < radiusBall) {
            yCircleOne = radiusBall;
            xCircleOneAuxBottom = xCircleOne - 110;
            yCircleOneAuxBottom = yCircleOne + 140;
        }

        if (xCircleOne > (getWidth() - radiusBall)) {
            xCircleOne = getWidth() - radiusBall;
            xCircleOneAuxBottom = xCircleOne - 110;
            yCircleOneAuxBottom = yCircleOne + 140;
        }

        if (yCircleOne > (getHeight() - radiusBall)) {
            yCircleOne = getHeight() - radiusBall;
            xCircleOneAuxTop = xCircleOne + 110;
            yCircleOneAuxTop = yCircleOne - 140;
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

    private void calculateAngle() {
        float deltaX = xCircleTwo - xCircleOne;
        float deltaY = yCircleOne - yCircleTwo;

        angle = (float) Math.toDegrees(Math.atan2(deltaY, deltaX));

        if (angle < 0) {
            angle += 360;
        }
    }

    public void setPositionCircleOne(float x, float y) {
        xCircleOne = x;
        yCircleOne = y;

        xCircleOneAuxTop = x + 110;
        yCircleOneAuxTop = y - 140;

        xCircleOneAuxBottom = x - 110;
        yCircleOneAuxBottom = y + 140;
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

    public void resetLines() {
        touchedTheWall = false;
    }

    public void secondLine(boolean secondLine) {
        showSecondLine = secondLine;

        invalidate();
    }
}
