package com.zlin.property.db.helper;

import android.content.Context;
import android.content.res.AssetManager;

import com.zlin.property.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zhanglin on 16/8/22.
 */

public class ALocalSqlHelper {

    private Context context;
    DevOpenHelper devOpenHelper;
    DaoMaster daoMaster;

    DaoSession daoSession;

    public ALocalSqlHelper(Context context) {

        this.context = context;

//        devOpenHelper = new DevOpenHelper(context, Constant.DB_LOCAL, null);
//        daoMaster = new DaoMaster(devOpenHelper.getReadableDb());

    }

    private Boolean CopyAssetsDir(String src, String des) {
        //复制安卓Assets下的“非空目录”到des文件夹，注意是否对des有写权限
        Boolean isSuccess = true;
        String[] files;
        try
        {
            files = context.getResources().getAssets().list(src);
        }
        catch (IOException e1)
        {
            return false;
        }

        if(files.length==0){
            isSuccess = CopyAssetsFile(src,des);//对于文件直接复制
            if(!isSuccess)
                return isSuccess;
        }
        else{
            File srcfile = new File(des+"/"+src);
            if(!srcfile.exists()){
                if(srcfile.mkdirs()){//对于目录自行创建
                    for(int i=0;i<files.length;i++){//新浪博客的限制，这里的小于号请自行替换为英文的
                        isSuccess = CopyAssetsDir(src + "/"+files[i], des);//递归调用
                        if(!isSuccess)
                            return isSuccess;
                    }
                }
                else{
                    return false;
                }
            }

        }
        return isSuccess;
    }

    private Boolean CopyAssetsFile(String filename, String des) {
        Boolean isSuccess = true;
        //复制安卓apk的assets目录下任意路径的单个文件到des文件夹，注意是否对des有写权限
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            String newFileName = des + "/" + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;

    }

    private boolean isExist() {

        File file = new File(Constant.DB_CHECKS);

        if (file.exists()) {

            return true;

        } else {

            return false;

        }

    }

}
