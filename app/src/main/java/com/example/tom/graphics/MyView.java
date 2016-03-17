package com.example.tom.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by Tom on 2016-03-08.
 */
class MyView extends View {

    private Paint brush;
    public static double scale;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        scale = getHeight()/4/MainActivity.initialPoints.get(0)[1];
        double xShift = getWidth()/2 - MainActivity.initialPoints.get(0)[0]*scale ;
        double yShift = getHeight()/2 - MainActivity.initialPoints.get(0)[1]*-scale;

        for(int[] line: MainActivity.lines){
            canvas.drawLine((int)(MainActivity.points.get(line[0])[0] * scale + xShift),
                            (int)(MainActivity.points.get(line[0])[1] * -scale + yShift),
                            (int)(MainActivity.points.get(line[1])[0] * scale + xShift),
                            (int)(MainActivity.points.get(line[1])[1] * -scale + yShift),
                    brush);
        }
    }

    private void init(){
        brush = new Paint();
        brush.setStrokeWidth(5);
    }

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

}