package com.hipo.drawingview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawingView extends SurfaceView {


    private Paint canvasPaint;
    private Bitmap canvasBmp;
    private Path mPath;
    private Paint mPaint;
    private Canvas mCanvas;
    private float strokeWidth = 15;
    private int paintColor = 0xFF000000;
    private SurfaceHolder mSurfaceHolder;


    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public DrawingView(Context context, float strokeWidth, int paintColor) {
        super(context);
        this.strokeWidth = strokeWidth;
        this.paintColor = paintColor;
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawingView, 0, 0);
            if (a.hasValue(R.styleable.DrawingView_stroke_width)) {
                //stroke width
                final float strokeWidth = a.getFloat(R.styleable.DrawingView_stroke_width, 15);
                setStrokeWidth(strokeWidth);
            }
            if (a.hasValue(R.styleable.DrawingView_paint_color)) {
                //color
                final int color = a.getColor(R.styleable.DrawingView_paint_color, 0xFF000000);
                setPaintColor(color);
            }
            try {
                a.recycle();
            } catch (Exception e) {
            }

        }
        if (this.getBackground() == null) {
            setBackgroundResource(R.drawable.android);
        }

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                setup();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                canvasBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(canvasBmp);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setPaintColor(int color) {
        paintColor = color;
    }

    private void setup() {
        // This paint object will be used for drawing path ,
        mPaint = new Paint();
        mPaint.setColor(paintColor);
        //for smooth edge
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
        //Create Path for later use
        mPath = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBmp, 0, 0, canvasPaint);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float X = event.getX();
        final float Y = event.getY();
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(X, Y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(X, Y);
                break;
            case MotionEvent.ACTION_UP:
                mPath.lineTo(X, Y);
                mCanvas.drawPath(mPath, mPaint);
                mPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;

    }

    /**
     * Returns saved bitmap
     */
    public Bitmap save() {
        return getBitmapFromView();
    }

    //Create a bitmap with the same size of DrawingView and pass it to the Canvas
    private Bitmap getBitmapFromView() {
        final Bitmap returnedBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(returnedBitmap);
        this.draw(canvas);
        return returnedBitmap;
    }


}
