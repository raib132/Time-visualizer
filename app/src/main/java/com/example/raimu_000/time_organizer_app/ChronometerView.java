package com.example.raimu_000.time_organizer_app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ChronometerView extends View {
    private Paint paint;
    private int height, width = 0;
    private int radius = 0;
    private boolean isInit;

    public ChronometerView(Context context) {
        super(context);
    }

    public ChronometerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChronometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChronometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initChronometer(){
        height = getHeight();
        width = getWidth();
        int min = Math.min(height, width);
        radius = (min / 2) - 16;
        paint = new Paint();
        isInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!isInit){
            initChronometer();
        }
        canvas.drawColor(Color.WHITE);
        drawCircle(canvas);
    }

    public void drawCircle(Canvas canvas){
        paint.reset();
        paint.setColor(Color.parseColor("#1976d2"));
        paint.setStrokeWidth(13);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(width/2, height/2, radius, paint);

    }
}
