package app.hack.eightballpool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class NineBall extends View {
    private Paint line;
    private Paint circleOne;
    private Paint circleTwo;

    float xCircleOne, yCircleOne;
    float xCircleTwo, yCircleTwo;
    float xStartLine, yStartLine;
    float xEndLine, yEndLine;

    public NineBall(Context context) {
        super(context);

        init();
    }

    public NineBall(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public NineBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        line = new Paint();
        circleOne = new Paint();
        circleTwo = new Paint();

        line.setStrokeWidth(4.5f);
        line.setStrokeCap(Paint.Cap.ROUND);
        line.setColor(Color.WHITE);
        line.setShadowLayer(6, 0, 0, Color.BLACK);
        line.setAntiAlias(true);

        circleOne.setStyle(Paint.Style.STROKE);
        circleOne.setStrokeWidth(4.5f);
        circleOne.setColor(Color.WHITE);
        circleOne.setShadowLayer(6, 0, 0, Color.BLACK);
        circleOne.setAntiAlias(true);

        circleTwo.setStyle(Paint.Style.STROKE);
        circleTwo.setStrokeWidth(13);
        circleTwo.setColor(getContext().getColor(R.color.colorBlack));
        circleTwo.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(xStartLine, yStartLine, xEndLine, yEndLine, line);

        canvas.drawCircle(xCircleOne, yCircleOne, 21, circleOne);
        canvas.drawCircle(xCircleTwo, yCircleTwo, 41, circleTwo);
    }

    public void setPositionCircleOne(float x, float y) {
        xCircleOne = x;
        yCircleOne = y;
    }

    public void setPositionCircleTwo(float x, float y) {
        xCircleTwo = x;
        yCircleTwo = y;
    }

    public void setPositionLine(float xStart, float yStart, float xEnd, float yEnd) {
        xStartLine = xStart;
        yStartLine = yStart;

        xEndLine = xEnd;
        yEndLine = yEnd;
    }
}
