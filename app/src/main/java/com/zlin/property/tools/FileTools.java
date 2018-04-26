package com.zlin.property.tools;

/**
 * Created by zhanglin03 on 2018/4/26.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FileTools {

    static final String TAG = "FileTools";
    public static String tempname;
    /**
     * 复制文件
     *
     * @param is
     * @param os
     */
    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (;;) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    /**
     * 读取文件
     *
     * @param pathName
     *            文件路径+名字
     * @return
     */
    public static InputStream readFile(String pathName) {
        File file = new File(pathName);
        FileInputStream fis = null;
        if (file.exists()) {
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                Log.w(TAG, e.getMessage());
            }
        }
        return fis;
    }

    /**
     * 读取文件内容
     *
     * @param pathName
     *            文件路径
     * @return
     */
    public static String readFileString(String pathName) {
        File file = new File(pathName);
        FileInputStream fis = null;
        BufferedReader reader = null;
        String string = null;
        if (file.exists()) {
            try {
                fis = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
                StringBuilder builder = new StringBuilder();
                for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                    builder.append(s);
                }
                string = builder.toString();
            } catch (Exception e) {
                Log.w(TAG, e.getMessage());
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return string;
    }

    /**
     * 写文件
     *
     * @param pathName
     *            文件路径 + JSON内容
     * @param content
     */
    public static void writeFile(String pathName, String content) {
        File file = new File(pathName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(content);
            bw.close();
        } catch (Exception e) {
            Log.w(TAG, e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.w(TAG, e.getMessage());
                }
            }
        }
    }

    /**
     * 保存图片
     *
     * @param path
     * @param name
     * @param bitmap
     */
    public static void writeBitmap(String path, String name, Bitmap bitmap) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        File _file = new File(path + name);
        if (_file.exists()) {
            _file.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(_file);
            if (name != null && !"".equals(name)) {
                int index = name.lastIndexOf(".");
                if (index != -1 && (index + 1) < name.length()) {
                    String extension = name.substring(index + 1).toLowerCase();
                    if ("png".equals(extension)) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    } else if ("jpg".equals(extension) || "jpeg".equals(extension)) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Log.w(TAG, e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.w(TAG, e.getMessage());
                }
            }
        }
    }

    /**
     * 读取图片
     *
     * @param pathName
     * @return
     */
    public static Bitmap readBitmap(String pathName) {
        InputStream in = FileTools.readFile(pathName);
        if (in != null) {
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            try {
                in.close();
            } catch (IOException e) {
                Log.w(TAG, e.getMessage());
            }
            return bitmap;
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file != null && file.isFile()) {
            file.delete();
        }
    }

    /**
     * 判断SD是否存在
     *
     * @return
     */
    public static boolean isExternalStorage() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 判断SD卡空间是否满
     *
     * @return
     */
    public static boolean storageIsFull() {
        StatFs fs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        return !(fs.getAvailableBlocks() > 1);
    }

    /**
     * 获取文件名
     *
     * @param filePathName
     * @return
     */
    public static String getFileName(String filePathName) {
        if (filePathName != null && !"".equals(filePathName)) {
            int index = filePathName.lastIndexOf('/');
            if (index != -1 && (index + 1) < filePathName.length()) {
                return filePathName.substring(index + 1);
            }
        }
        return "";
    }


    public static File saveAudio(byte[] b, String path, String name) {
        BufferedOutputStream stream = null;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File _file = new File(path + name);
        try {
            FileOutputStream fstream = new FileOutputStream(_file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return _file;
    }

    /**
     * 递归删除文件夹
     *
     * @param f
     */
    public static void delFile(File f) {
        if (f.isDirectory()) {
            File[] list = f.listFiles();
            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    delFile(list[i]);
                } else {
                    if (list[i].isFile()) {
                        list[i].delete();
                    }
                }
            }
            f.delete();
        } else {
            if (f.isFile())
                f.delete();
        }
    }

    /**
     * 计算文件夹大小
     *
     * @param f
     * @return
     */
    public static long calculateSpace(File f) {
        long length = 0;
        if (f != null && f.isDirectory()) {
            File[] list = f.listFiles();
            for (int i = 0; i < list.length; i++) {
                if (list[i].isFile()) {
                    length += list[i].length();
                }
            }
        }
        return length;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static File createCameraPhoto() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        tempname = AppConfig.IMAGE_SD_CACHE_PATH+"IMG" + dateFormat.format(date) + AppConfig.TYPE_PNG;
        AppConfig.CamerPhotoFile = new File(tempname);
        return AppConfig.CamerPhotoFile;
    }
}
