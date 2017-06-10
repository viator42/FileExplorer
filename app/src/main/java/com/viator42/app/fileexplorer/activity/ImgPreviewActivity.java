package com.viator42.app.fileexplorer.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.viator42.app.fileexplorer.R;
import com.viator42.app.fileexplorer.utils.CommonUtils;

public class ImgPreviewActivity extends AppCompatActivity implements android.view.GestureDetector.OnGestureListener {
    private Bitmap bitmap;
    private ImageView imgView;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_preview);
        imgView = (ImageView) findViewById(R.id.img);

        backBtn = (ImageView) findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImgPreviewActivity.this.onBackPressed();
                ImgPreviewActivity.this.finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = getIntent().getExtras();
        String path = bundle.getString("path");

        bitmap = CommonUtils.loadBitmap(ImgPreviewActivity.this, path);
        if(bitmap != null) {
            imgView.setImageBitmap(bitmap);
        }

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

}
