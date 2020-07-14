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
    private Paint circleAuxTop;
    private Paint circleAuxBottom;
    private Paint circleTwo;

    float xonTouch, yonTouch, xonMotion, yonMotion;
    float xCircleOne, yCircleOne, xCircleTwo, yCircleTwo;
    float xCircleAuxTop, yCircleAuxTop, xCircleAuxBottom, yCircleAuxBottom;
    float angle;

    int strokeWidth = 6;
    int radiusCircleOne = 100;
    int radiusCircleOneMiddle = 12;
    int radiusCircleTwo = 100;
    int radiusCircleAux = 50;
    int circleNo;

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
        circleTwo = new Paint();
        circleAuxTop = new Paint();
        circleAuxBottom = new Paint();

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
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xonTouch = event.getX();
                yonTouch = event.getY();

                trackStatus = false;

                if (xonTouch > (xCircleOne - radiusCircleOne)
                        && xonTouch < (xCircleOne + radiusCircleOne)
                        && yonTouch > (yCircleOne - radiusCircleOne)
                        && yonTouch < (yCircleOne + radiusCircleOne)
                ) {
                    trackStatus = true;
                    circleNo = 1;
                }

                if (xonTouch > (xCircleTwo - radiusCircleTwo)
                        && xonTouch < (xCircleTwo + radiusCircleTwo)
                        && yonTouch > (yCircleTwo - radiusCircleTwo)
                        && yonTouch < (yCircleTwo + radiusCircleTwo)
                ) {
                    trackStatus = true;
                    circleNo = 2;
                }

                if (xonTouch > (xCircleAuxTop - radiusCircleAux)
                        && xonTouch < (xCircleAuxTop + radiusCircleAux)
                        && yonTouch > (yCircleAuxTop - radiusCircleAux)
                        && yonTouch < (yCircleAuxTop + radiusCircleAux)
                ) {
                    trackStatus = true;
                    circleNo = 3;
                }

                if (xonTouch > (xCircleAuxBottom - radiusCircleAux)
                        && xonTouch < (xCircleAuxBottom + radiusCircleAux)
                        && yonTouch > (yCircleAuxBottom - radiusCircleAux)
                        && yonTouch < (yCircleAuxBottom + radiusCircleAux)
                ) {
                    trackStatus = true;
                    circleNo = 4;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                xonMotion = event.getX();
                yonMotion = event.getY();

                if (circleNo == 1) {
                    xCircleOne = xonMotion;
                    yCircleOne = yonMotion;

                    xCircleAuxTop = xCircleOne + 140;
                    yCircleAuxTop = yCircleOne - 110;

                    xCircleAuxBottom = xCircleOne - 140;
                    yCircleAuxBottom = yCircleOne + 110;

                    checkCircleOneCollision();
                }

                if (circleNo == 2) {
                    xCircleTwo = xonMotion;
                    yCircleTwo = yonMotion;

                    touchedTheWall = false;

                    // Left
                    if (xonMotion < 0) {
                        xCircleTwo = 0;
                        touchedTheWall = true;
                    }

                    // Top
                    if (yonMotion < 0) {
                        yCircleTwo = 0;
                        touchedTheWall = true;
                    }

                    // Right
                    if (xonMotion > getWidth()) {
                        xCircleTwo = getWidth();
                        touchedTheWall = true;
                    }

                    // Bottom
                    if (yonMotion > getHeight()) {
                        yCircleTwo = getHeight();
                        touchedTheWall = true;
                    }
                }

                if (circleNo == 3) {
                    xCircleAuxTop = xonMotion;
                    yCircleAuxTop = yonMotion;

                    xCircleOne = xCircleAuxTop - 140;
                    yCircleOne = yCircleAuxTop + 110;

                    xCircleAuxBottom = xCircleOne - 140;
                    yCircleAuxBottom = yCircleOne + 110;

                    checkCircleOneCollision();
                }

                if (circleNo == 4) {
                    xCircleAuxBottom = xonMotion;
                    yCircleAuxBottom = yonMotion;

                    xCircleOne = xCircleAuxBottom + 140;
                    yCircleOne = yCircleAuxBottom - 110;

                    xCircleAuxTop = xCircleOne + 140;
                    yCircleAuxTop = yCircleOne - 110;

                    checkCircleOneCollision();
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

        // Se o centro do círculo preto tocar no top ou bottom, exibe a linha na vertical
        if (touchedTheWall) {

            /*
            * Caso o toque for no lado vertical ( left ou right )
            * subtrai 180 graus do ângulo para exibir a linha na horizontal
            */
            if (xCircleTwo == getWidth() || xCircleTwo == 0) {
                angle -= 180;
            }

            drawReflectLine(canvas);
        }

        canvas.drawCircle(xCircleOne, yCircleOne, radiusCircleOne, circleOne);
        canvas.drawCircle(xCircleOne, yCircleOne, radiusCircleOneMiddle, circleOneMiddle);
        canvas.drawCircle(xCircleTwo, yCircleTwo, radiusCircleTwo, circleTwo);
        canvas.drawCircle(xCircleAuxTop, yCircleAuxTop, radiusCircleAux, circleAuxTop);
        canvas.drawCircle(xCircleAuxBottom, yCircleAuxBottom, radiusCircleAux, circleAuxBottom);
    }

    private void checkCircleOneCollision() {
        if (xCircleOne < 0) {
            xCircleOne = 0;
            xCircleAuxTop = xCircleOne + 140;
            yCircleAuxTop = yCircleOne - 110;
        }

        if (yCircleOne < 0) {
            yCircleOne = 0;
            xCircleAuxBottom = xCircleOne - 140;
            yCircleAuxBottom = yCircleOne + 110;
        }

        if (xCircleOne > getWidth()) {
            xCircleOne = getWidth();
            xCircleAuxBottom = xCircleOne - 140;
            yCircleAuxBottom = yCircleOne + 110;
        }

        if (yCircleOne > getHeight()) {
            yCircleOne = getHeight();
            xCircleAuxTop = xCircleOne + 140;
            yCircleAuxTop = yCircleOne - 110;
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

    public void setCenterCanvasCircleOne(float x, float y) {
        xCircleOne = x;
        yCircleOne = y;

        xCircleAuxTop = x + 110;
        yCircleAuxTop = y - 140;

        xCircleAuxBottom = x - 110;
        yCircleAuxBottom = y + 140;
    }

    public void setCenterCanvasCircleTwo(float x, float y) {
        xCircleTwo = (int) x;
        yCircleTwo = (int) y;
    }
}