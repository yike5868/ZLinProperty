package com.zlin.property.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.zlin.property.R;
import com.zlin.property.control.FragmentParent;

/**
 * Created by zhanglin03 on 2018/4/26.
 */

public class MediaTools {
    public static void chooseCamera(FragmentParent context) {
        if (!FileTools.isExternalStorage()) {
            Toast.makeText(context.getContext(), "请插入sd卡!", Toast.LENGTH_LONG).show();
        } else if (FileTools.storageIsFull()) {
            Toast.makeText(context.getContext(), "存储空间不足!", Toast.LENGTH_LONG).show();
        } else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(FileTools.createCameraPhoto()));
            context.startActivityForResult(cameraIntent, AppConfig.FROM_CAMERA);
        }
    }
}
