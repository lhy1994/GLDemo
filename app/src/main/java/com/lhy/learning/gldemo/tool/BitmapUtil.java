package com.lhy.learning.gldemo.tool;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.lhy.learning.gldemo.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {
    private static final String TAG = "BitmapUtil";

    public static void saveBitmap(Bitmap bitmap, String name) {
        if (bitmap == null) {
            return;
        }
        File file = new File(getTestDir(), name);
        Log.i(TAG, "saveBitmap: 1" + file.getAbsolutePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            Log.i(TAG, "saveBitmap: 2");
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } catch (Exception e) {
            Log.i(TAG, "saveBitmap: 3 " + e.getMessage());
            e.printStackTrace();
        } finally {
            IOUtil.close(os);
        }
    }

    public static String getTestDir() {
        String dir = MyApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/A" + File.separator;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }
}
