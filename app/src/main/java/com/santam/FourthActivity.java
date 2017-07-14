package com.santam.liilabproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.santam.liilabproject.CanvasView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FourthActivity extends AppCompatActivity implements Html.ImageGetter {

    TextView mImageText;
    private CanvasView canvasView;
    Button mClear;
    Button mSubmit;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);


        setTitle("LiiLab: 4th");
        mImageText = (TextView) findViewById(R.id.TxtImgView);

        Drawable innerDrawable = getResources().getDrawable(R.drawable.liilab);
//        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
//        spannableStringBuilder.append(" ");
//        spannableStringBuilder.setSpan(new ImageSpan(getApplicationContext(), R.drawable.liilab,ImageSpan.ALIGN_BOTTOM),
//                spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 0);
//        spannableStringBuilder.append(" This is a Textview which is showing an image." +
//                "This is a Textview which is showing an image." +
//                "This is a Textview which is showing an image." +
//                "This is a Textview which is showing an image." +
//                "This is a Textview which is showing an image." +
//                "This is a Textview which is showing an image." +
//                "This is a Textview which is showing an image.");
//
//
//        mImageText.setText(spannableStringBuilder);
        GravityCompoundDrawable gravityDrawable = new GravityCompoundDrawable(innerDrawable);
        innerDrawable.setBounds(0, 0, innerDrawable.getIntrinsicWidth(), innerDrawable.getIntrinsicHeight());
        gravityDrawable.setBounds(0, 0, innerDrawable.getIntrinsicWidth(), innerDrawable.getIntrinsicHeight());
        mImageText.setCompoundDrawables(gravityDrawable, null, null, null);
        mImageText.setTextSize(16);

        canvasView = (CanvasView) findViewById(R.id.canvas);
        mClear = (Button) findViewById(R.id.btnClear);
        mSubmit = (Button) findViewById(R.id.btnSubmit);

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCanvas(v);
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDoalog = new ProgressDialog(FourthActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage("Picture Saving....");
                progressDoalog.show();
                String saveImage = saveCanvas();
                progressDoalog.dismiss();
                if(saveImage == null || saveImage.equals(""))
                    Toast.makeText(getApplicationContext(), "Picture Cannot be Save. File Path " + saveImage, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Picture Save Successfully. File Path " + saveImage, Toast.LENGTH_LONG).show();

            }
        });

//        mClear.setOnClickListener(this);
//        mSubmit.setOnClickListener(this);

    }


    public void clearCanvas(View v) {
        canvasView.clearCanvas();
    }

    public String saveCanvas() {
        try {
            CanvasView content = (CanvasView) findViewById(R.id.canvas);
            content.setDrawingCacheEnabled(true);
            Bitmap bitmap = content.getDrawingCache();
            File file, f;

            file = new File(Environment.getExternalStorageDirectory(), "LiiLabProject");
            if (!file.exists()) {

                file.mkdirs();
                Log.d("Dir: ", "Create Directory Success");
            }
            Log.d("Dir: ", "Already Created Dir");
            f = new File(file.getAbsolutePath() + "/liilab.png");

            FileOutputStream ostream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);

            ostream.close();

            Log.d("Save Image: ", f.getAbsolutePath().toString());

            return f.getAbsolutePath().toString();

        } catch (IOException e1) {
            Log.d("error", e1.toString());
            e1.printStackTrace();
        }
        return null;
//        try {
//            CanvasView content = (CanvasView) findViewById(R.id.canvas);
//            content.setDrawingCacheEnabled(true);
//            Bitmap bitmap = content.getDrawingCache();
////            Canvas canvas = new Canvas(bitmap);
////            canvas.drawBitmap(bitmap, 0, 0, null);
////
////            Bitmap waterMark = BitmapFactory.decodeResource(getResources(), R.drawable.liilab);
////            canvas.drawBitmap(waterMark, 0, 0, null);
//
//            File file, f;
//            f = new File(Environment.getExternalStorageDirectory() + "/liilab.png");
//            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
//                file = new File(android.os.Environment.getExternalStorageDirectory(), "TTImages_cache");
//                if (!file.exists()) {
//                    file.mkdirs();
//
//                }
//
//            }
//            FileOutputStream ostream = new FileOutputStream(f);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
//
//            ostream.close();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }

    }

    @Override
    public Drawable getDrawable(String arg0) {
        // TODO Auto-generated method stub
        int id = 0;

        if (arg0.equals("addbutton.png")) {
            id = R.drawable.liilab;
        }

        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(id);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        return d;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int menuId = item.getItemId();

        if (menuId == R.id.scrn1)
            startActivity(new Intent(FourthActivity.this, FirstActivity.class));

        else if (menuId == R.id.scrn2)
            startActivity(new Intent(FourthActivity.this, SecondActivity.class));

        else if (menuId == R.id.scrn3)
            startActivity(new Intent(FourthActivity.this, ThirdActivity.class));

        else if (menuId == R.id.scrn4)
            startActivity(new Intent(FourthActivity.this, FourthActivity.class));

        else
            startActivity(new Intent(FourthActivity.this, FirstActivity.class));

        // Return Statement
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onClick(View v) {
//
//    }
}

class GravityCompoundDrawable extends Drawable {

    // inner Drawable
    private final Drawable mDrawable;

    public GravityCompoundDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    @Override
    public int getIntrinsicWidth() {
        return mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawable.getIntrinsicHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        int halfCanvas = canvas.getHeight() / 2;
        int halfDrawable = mDrawable.getIntrinsicHeight() / 2;

        // align to top
        canvas.save();
        canvas.translate(0, -halfCanvas + halfDrawable);
        mDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}