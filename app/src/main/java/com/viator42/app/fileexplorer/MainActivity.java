package com.viator42.app.fileexplorer;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.viator42.app.fileexplorer.adapter.FileListAdapter;
import com.viator42.app.fileexplorer.utils.StaticValues;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public ListView fileListView;
    public GridView fileGridView;
    public FileListAdapter fileListAdapter;
    public Toolbar toolbar;
    private List fileListData;
    private File currentFile;
    private String homePath;
    private int currentListMode = StaticValues.MODE_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fileListView = (ListView) findViewById(R.id.file_list);
        fileGridView = (GridView) findViewById(R.id.file_grid);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homePath = Environment.getExternalStorageDirectory().getPath();

        toolbar.setNavigationIcon(R.drawable.arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        load(homePath);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//                String[] result = new DirectoryAction().list();
//                if(result != null) {
//                    Log.v("FileExplorer", result.toString());
//
//                }
//                else
//                {
//                    Log.v("FileExplorer", "获取文件失败");
//                }
//
//
//            }
//        });
    }

    public void load(String path) {
        currentFile = new File(path);
        File[] files = currentFile.listFiles();

        if(files != null) {
            fileListData = new ArrayList<Map<String,Object>>();
            for(int a=0; a<files.length; a++) {
                File file = files[a];
                Map line = new HashMap();
                line.put("id", a);
                line.put("name", file.getName());
                line.put("path", file.getPath());
                if(file.isDirectory()) {
                    line.put("type", StaticValues.FILE_ITEM_TYPE_DIRECTORY);
                }
                if(file.isFile()) {
                    line.put("type", StaticValues.FILE_ITEM_TYPE_FILE);
                    line.put("sub", getFileType(file.getName()));
                }

                fileListData.add(line);

            }

            changeListMode();
        }

    }

    public void changeListMode() {
        switch (currentListMode) {
            case StaticValues.MODE_LIST:
                fileListView.setVisibility(View.VISIBLE);
                fileGridView.setVisibility(View.GONE);

                fileListAdapter = new FileListAdapter(fileListData, MainActivity.this, currentListMode);
                fileListView.setAdapter(fileListAdapter);

                break;

            case StaticValues.MODE_GRID:
                fileGridView.setVisibility(View.VISIBLE);
                fileListView.setVisibility(View.GONE);

                fileListAdapter = new FileListAdapter(fileListData, MainActivity.this, currentListMode);
                fileGridView.setAdapter(fileListAdapter);

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.view_mode_list:
                currentListMode = StaticValues.MODE_LIST;
                load(homePath);
                break;
            case R.id.view_mode_grid:
                currentListMode = StaticValues.MODE_GRID;
                load(homePath);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        //返回键
        if(!currentFile.getPath().equals(homePath) && currentFile.isDirectory()) {
            load(currentFile.getParent());
        }

    }

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    public String getFileType(String fileName) {
        String[] strArray = fileName.split("\\.");
        int suffixIndex = strArray.length -1;
        return strArray[suffixIndex];
    }

}
