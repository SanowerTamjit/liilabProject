package com.santam.liilabproject;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {

    public int height, width;
    private static Bitmap mBitmap;
    private static Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    Context context;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        mPath = new Path();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(8f);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);

    }

//    public void SaveCanvasToImg(){
//
//
//        mCanvas.setBitmap(mBitmap);
//        File file = new File(Environment.getExternalStorageDirectory() + "/sign.png");
//
//        try {
//            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
//            Log.d("Save: ", file.getAbsolutePath().toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    private void onTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;

    }

    private void StartTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;

    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;

        }
    }

    public void clearCanvas() {
        mPath.reset();
        invalidate();
    }

    public void upTouch() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

//        int action = MotionEventCompat.getActionMasked(event);
//// Get the index of the pointer associated with the action.
//        int index = MotionEventCompat.getActionIndex(event);
//        int xPos = -1;
//        int yPos = -1;
//
//
//        if (event.getPointerCount() > 1) {
//            Log.d("TouchEvent","Multitouch event");
//            // The coordinates of the current screen contact, relative to
//            // the responding View or Activity.
//            xPos = (int)MotionEventCompat.getX(event, index);
//            yPos = (int)MotionEventCompat.getY(event, index);
//
//        } else {
//            // Single touch event
//            Log.d("TouchEvent","Single touch event");
//            xPos = (int)MotionEventCompat.getX(event, index);
//            yPos = (int)MotionEventCompat.getY(event, index);
//            onTouch(xPos,yPos);
//            invalidate();
//        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                StartTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;

        }

        return true;
    }

}

