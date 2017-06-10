package com.viator42.app.fileexplorer.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.viator42.app.fileexplorer.MainActivity;
import com.viator42.app.fileexplorer.R;
import com.viator42.app.fileexplorer.activity.ImgPreviewActivity;
import com.viator42.app.fileexplorer.activity.TextPreviewActivity;
import com.viator42.app.fileexplorer.utils.StaticValues;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by viator42 on 2017/6/4.
 */

public class FileItemAdapter extends BaseAdapter{
    //填充数据的List
    List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();

    //用来导入布局
    private LayoutInflater inflater =null;
    private MainActivity mainActivity;

    //构造器
    public FileItemAdapter(List<Map<String,Object>> list,Context context){
        this.list=list;
        this.mainActivity = (MainActivity) context;
        inflater=LayoutInflater.from(context);
    }

    private int width;
    private int height;
    private ViewHolder holder;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        int id = (int) list.get(position).get("id");
        return new Long(id);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            holder = new ViewHolder();
            convertView =inflater.inflate(R.layout.file_item, null);
            holder.name = (TextView)convertView.findViewById(R.id.name);
            holder.path = (TextView)convertView.findViewById(R.id.path);

            //为view设置标签
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.name.setText(list.get(position).get("name").toString());
        holder.path.setText(list.get(position).get("path").toString());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mainActivity, list.get(position).get("path").toString(), Toast.LENGTH_SHORT).show();
                int type = (int) list.get(position).get("type");
                switch (type) {
                    case StaticValues.FILE_ITEM_TYPE_DIRECTORY:
                        mainActivity.load(list.get(position).get("path").toString());
                        break;

                    case StaticValues.FILE_ITEM_TYPE_FILE:
                        String sub = list.get(position).get("sub").toString();
                        Intent intent = null;
                        Bundle bundle = null;
                        switch (sub) {
                            case "jpg":
                                intent = new Intent(mainActivity, ImgPreviewActivity.class);
                                bundle = new Bundle();
                                bundle.putString("path", list.get(position).get("path").toString());
                                intent.putExtras(bundle);
                                mainActivity.startActivity(intent);

                                break;
                            case "txt":
                                intent = new Intent(mainActivity, TextPreviewActivity.class);
                                bundle = new Bundle();
                                bundle.putString("path", list.get(position).get("path").toString());
                                intent.putExtras(bundle);
                                mainActivity.startActivity(intent);

                                break;
                            case "apk":
                                Toast.makeText(mainActivity, "this is a txt file", Toast.LENGTH_SHORT).show();

                                try {
                                    intent = new Intent(Intent.ACTION_VIEW);
                                    Uri uri = Uri.fromFile(new File(list.get(position).get("path").toString()));
                                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mainActivity.startActivity(intent);
                                }
                                catch (Exception e) {
                                    mainActivity.getResources().getText(R.string.file_open_failed);
                                }


                                break;
                        }
                        break;
                }

            }
        });

        width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);

        convertView.measure(width,height);

        height=convertView.getMeasuredHeight();
        width=convertView.getMeasuredWidth();

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView path;
    }
}
