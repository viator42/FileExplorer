package com.viator42.app.fileexplorer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by viator42 on 2017/6/9.
 */

public class CommonUtils {
    /**
     * 读取图片文件
     * @param context
     * @param path
     * @return
     */
    public static Bitmap loadBitmap(Context context, String path) {
        try
        {
            File file = new File(path);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                return bitmap;
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

    public static String loadText(Context context, String path) {
        File file = new File(path);
        BufferedReader reader = null;
        StringBuffer restultBuffer = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                restultBuffer.append(tempString);
                line++;
            }
            reader.close();
            return restultBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
