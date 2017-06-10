package com.viator42.app.fileexplorer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.viator42.app.fileexplorer.R;
import com.viator42.app.fileexplorer.utils.CommonUtils;

public class TextPreviewActivity extends AppCompatActivity {
    private TextView  contentTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_preview);

        contentTextView = (TextView) findViewById(R.id.content);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = getIntent().getExtras();
        String path = bundle.getString("path");

        String content = CommonUtils.loadText(TextPreviewActivity.this, path);
        if(content != null) {
            contentTextView.setText(content);
        }

    }
}

