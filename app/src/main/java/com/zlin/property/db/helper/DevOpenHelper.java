package com.zlin.property.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



/**
 * Created by zhanglin on 16/9/26.
 */

public class DevOpenHelper extends DaoMaster.OpenHelper {

    Context mContext;

    public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
        mContext = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");

    }
}
