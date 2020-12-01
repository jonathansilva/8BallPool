package app.hack.eightballpool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class NineBall extends View {
    private Paint line;
    private Paint circle;

    float xCircle, yCircle;
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
        circle = new Paint();

        line.setStrokeWidth(4.5f);
        line.setStrokeCap(Paint.Cap.ROUND);
        line.setColor(Color.WHITE);
        line.setShadowLayer(6, 0, 0, Color.BLACK);
        line.setAntiAlias(true);

        circle.setStyle(Paint.Style.STROKE);
        circle.setStrokeWidth(13);
        circle.setColor(getContext().getColor(R.color.colorBlack));
        circle.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(xStartLine, yStartLine, xEndLine, yEndLine, line);

        canvas.drawCircle(xCircle, yCircle, 41, circle);
    }

    public void setPositionCircle(float x, float y) {
        xCircle = x;
        yCircle = y;
    }

    public void setPositionLine(float xStart, float yStart, float xEnd, float yEnd) {
        xStartLine = xStart;
        yStartLine = yStart;

        xEndLine = xEnd;
        yEndLine = yEnd;
    }
}
