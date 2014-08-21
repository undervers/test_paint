package com.undervers.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class PaintView extends View implements View.OnTouchListener {

    static final float BRUSH_SIZE = 25;

    private List <Pair <Paint, Path>> picture;

    public PaintView(Context context) {
        super(context);
        init();
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){

        picture = new ArrayList<Pair<Paint, Path>>();

        setOnTouchListener(this);
    }

    private Paint createBrush(int color){
        Paint brush = new Paint(Paint.ANTI_ALIAS_FLAG);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeWidth(BRUSH_SIZE);
        brush.setColor(color);

        return brush;
    }

    private Paint createDefaultBrush(){
        return createBrush(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Path drawPoints;
        Paint brush;

        for(Pair <Paint, Path> draw : picture){

            brush = draw.first;
            drawPoints = draw.second;

            if(drawPoints != null){
                canvas.drawPath(drawPoints, brush);
            }
        }
    }

    public void changePaintColor(int color){
        picture.add(new Pair<Paint, Path>(createBrush(color), new Path()));
    }

    public void clear(){
        Paint lastSelectedBrush = picture.remove(picture.size() - 1).first;
        picture.clear();

        invalidate();

        picture.add(new Pair<Paint, Path>(lastSelectedBrush, null));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Path drawPoints;

        if(event.getActionMasked() == MotionEvent.ACTION_DOWN){

            drawPoints = new Path();
            drawPoints.moveTo(event.getX(), event.getY());

            Paint brush = !picture.isEmpty() ? picture.get(picture.size() - 1).first : createDefaultBrush();

            picture.add(new Pair<Paint, Path>(brush, drawPoints));
        }else{
            drawPoints = picture.get(picture.size() - 1).second;

            drawPoints.lineTo(event.getX(), event.getY());
        }

        invalidate();

        return true;
    }
}
