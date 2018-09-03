package com.zlin.property.tools;

import android.os.Environment;

import com.zlin.property.db.po.RoomItem;
import com.zlin.property.db.po.TempRoom;

import java.io.File;

/**
 * Created by zhanglin03 on 2018/4/26.
 */

public class AppConfig {

//    public final static String HTTP="http://www.greatzhongtuo.com:8088";
//    public final static String VUE = "http://www.greatzhongtuo.com:8080";
//    public final static String HTTP_PULL = VUE+"/amz/vue/#/pull";


    public final static String HTTP="http://192.168.2.41:8088";
    public final static String VUE = "http://192.168.2.41:8080";
    public final static String HTTP_PULL = VUE+"/#/test";
    public final static String HTTP_URL = VUE+"/#";



    public static final String APP_NAME = "zlProperty";

    public static String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static String DATA_PATH = Environment.getDownloadCacheDirectory().getAbsolutePath();

    public static final String IMAGE_SD_CACHE_PATH = SD_PATH + "/" + APP_NAME + "/image_cache/";

    public static final String IMAGE_SD_PATH = SD_PATH + "/" + APP_NAME + "/image/";

    public static final String TYPE_PNG = ".png";
    public static File CamerPhotoFile;

    public static final int FROM_CAMERA = 3333;
    public static final int PHOTO_GALLERY_CODE =4444;
    public static final int PAGE_SIZE = 5;

    public static final String PAY_NO = "PAYNO";//未付款
    public static final String PAY_YES = "PAYYES";//已付款

    public static TempRoom tempRoom = new TempRoom();
}
