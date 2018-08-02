package com.zlin.property;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.zlin.property.control.CustomFragmentManager;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.db.helper.ALocalSqlHelper;


public class FuApp extends Application {
	private static Application _instance = null;

	private boolean isNeedCaughtExeption = true;// 是否捕获未知异常
	private PendingIntent mRestartIntent;

	public static Application getInstance(){
		return _instance;
	}

	private ALocalSqlHelper sqlHelper;

	@Override
	public void onCreate() {
		super.onCreate();
		_instance = this;
		if (isNeedCaughtExeption) {
//			cauchException();
		}

		//捕捉异常 提示
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(getApplicationContext());

		/**
		 * 初始化common库
		 * 参数1:上下文，不能为空
		 * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
		 * 参数3:Push推送业务的secret
		 */
//		UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "393c80ce94f9250429b0b3fb8b6d3bec");

		sqlHelper = new ALocalSqlHelper(this);
	}

	public static Context getContext(){
		return _instance.getApplicationContext();
	}


//	private void cauchException() {
//
//		Intent intent = new Intent();
//		// 参数1：包名，参数2：程序入口的activity
//		intent.setClassName(getPackageName(), getPackageName()
//				+ ".JkxInitNavActivity");
//		mRestartIntent = PendingIntent.getActivity(getApplicationContext(), -1,
//				intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//
//		// 程序崩溃时触发线程
//		UIHandler uiHandler = new UIHandler();
//		Thread.setDefaultUncaughtExceptionHandler(uiHandler);
//
//	}

	public String getChannelValue() {

		String channel = "";

//		try {
//			channel = this.getPackageManager().getApplicationInfo(
//					getPackageName(), PackageManager.GET_META_DATA).metaData
//					.getString("FU_CHANNEL");
//
//		} catch (NameNotFoundException e) {
//
//			channel = "gx_base_fu"; // 默认渠道为基础版本
//		}

		return (channel == null || "channel".equals(channel)) ? "gx_base_fu"
				: channel;
	}

	private CustomFragmentManager mFragmentManager;

	public CustomFragmentManager getFragmentManager() {

		if (mFragmentManager == null) {

			mFragmentManager = new CustomFragmentManager();
		}

		return mFragmentManager;
	}

	public void exitPersonInfo() {

	}

	private FuUiFrameManager mFuUiFrameManager;

	public FuUiFrameManager getFuUiFrameManager() {

		if (mFuUiFrameManager == null) {
			mFuUiFrameManager = new FuUiFrameManager();
		}

		return mFuUiFrameManager;
	}


	// -------------------------

	private class UIHandler implements Thread.UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread thread, Throwable ex) {

			// 1秒钟后重启应用
			AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
					mRestartIntent);

			android.os.Process.killProcess(android.os.Process.myPid());

		}
	}

	public ALocalSqlHelper getSqlHelper() {
		if(sqlHelper == null)
			sqlHelper = new ALocalSqlHelper(this);
		return sqlHelper;
	}

}
