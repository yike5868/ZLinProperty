package com.zlin.property.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;


import com.zlin.property.R;
import com.zlin.property.control.FuResponse;
import com.zlin.property.db.po.DownLoadFile;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.NetCallBack;
import com.zlin.property.net.NetManager;
import com.zlin.property.net.TaskManager;

import java.io.File;


@SuppressLint("HandlerLeak")
public class DownloadService extends Service {

	private NotificationManager mNotificationManager;
	private Notification mNotification;

	private final String DOWNLOAD_ACTION = "android.com.download";

	private final int DOWNLOAD_SUCCESS = 100; // 下载成功
	private final int DOWNLOAD_ERROR = 0; // 下载异常失败

	private boolean isActiveKill = true; // 用户主动停止服务（任务管理器手动停止）
	Handler mHandler = new Handler() {

		@SuppressWarnings("deprecation")
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case DOWNLOAD_SUCCESS:

				// 下载完毕后变换通知形式
				mNotification.flags = Notification.FLAG_AUTO_CANCEL;
				mNotification.contentView = null;

				Intent intent = new Intent(Intent.ACTION_VIEW);

				intent.setDataAndType(
						Uri.fromFile(new File(
								Environment
										.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
								"FollowUp.apk")),
						"application/vnd.android.package-archive");

				PendingIntent contentIntent = PendingIntent.getActivity(
						DownloadService.this, 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);
//				mNotification.setLatestEventInfo(DownloadService.this, "下载完成",
//						"文件已下载完毕,请点击安装", contentIntent);

				mNotificationManager.notify(0, mNotification);

				isActiveKill = false;
				stopSelf();
				break;
			case DOWNLOAD_ERROR:

				// 下载失败后变换通知形式
				mNotification.flags = Notification.FLAG_AUTO_CANCEL;
				mNotification.contentView = null;
				String str = "请重新下载...";
				Object strSD = msg.obj;
				if (strSD != null) {
					str = (String) strSD;
				}

//				mNotification.setLatestEventInfo(DownloadService.this, "下载失败",
//						str, null);

				PendingIntent pendingIntent = PendingIntent.getActivity(
						DownloadService.this, 0, new Intent(),
						PendingIntent.FLAG_CANCEL_CURRENT);

				mNotification.contentIntent = pendingIntent;

				mNotificationManager.notify(0, mNotification);

				isActiveKill = false;
				stopSelf();
				break;
			}
		}
	};

	/**
	 * 创建通知
	 */
	@SuppressWarnings("deprecation")
	private void setDownloadNotification() {
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mNotification = new Notification(R.mipmap.ic_notification_logo,
				"开始下载", System.currentTimeMillis());
		// 放置在"正在运行"栏目中
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		PendingIntent pendingIntent = PendingIntent.getActivity(
				DownloadService.this, 0, new Intent(),
				PendingIntent.FLAG_CANCEL_CURRENT);

		mNotification.contentIntent = pendingIntent;
		RemoteViews contentView = new RemoteViews(getPackageName(),
				R.layout.fu_notification_download_view);
		contentView.setTextViewText(R.id.name, "参合助手   正在下载...");
		mNotification.contentView = contentView;

		mNotificationManager.notify(0, mNotification);
	}

	/**
	 * 注册广播
	 */
	public void broadcastReceiver() {

		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(DOWNLOAD_ACTION);
		registerReceiver(mDownloadProgressReceiver, mFilter);
	}

	private BroadcastReceiver mDownloadProgressReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();

			if (action.equals(DOWNLOAD_ACTION)) {
				// 更新通知栏进度状态
				int progress = intent.getIntExtra("Progress", 0);
				RemoteViews contentview = mNotification.contentView;
				contentview.setTextViewText(R.id.tv_progress, progress + "%");
				contentview.setProgressBar(R.id.progressbar, 100, progress,
						false);
				mNotificationManager.notify(0, mNotification);
			}
		}
	};

	/**
	 * 下载线程
	 */
	private void openDownloadThread(Intent intent) {

		if (intent == null) {
			mHandler.sendEmptyMessage(DOWNLOAD_ERROR);
			return;
		}
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			mHandler.sendMessage(mHandler.obtainMessage(DOWNLOAD_ERROR,
					"无可用SD卡,请插入或连接SD卡"));
			return;
		}
		
		String lUrl = intent.getStringExtra("Url");


		DownLoadFile loadFile = new DownLoadFile();
		loadFile.setmDownloadPath(Environment
				.getExternalStoragePublicDirectory(
						Environment.DIRECTORY_DOWNLOADS).getPath());
		loadFile.setmFileName("HealthGo.apk");
		loadFile.setmSuffix(".apk");
		loadFile.setmNofityName(DOWNLOAD_ACTION);

		MyTask lTask = TaskManager.getInstace().getDownLoadApp(
				new mNetCallBack(), lUrl, loadFile);

		NetManager manager = NetManager.getInstance(this);
		manager.addNetTask(lTask);
		manager.excuteNetTask(lTask);
	}

	class mNetCallBack implements NetCallBack {

		@Override
		public void loadData(int taskId, FuResponse rspObj) {
			Message message = Message.obtain(mHandler);
			message.what = DOWNLOAD_SUCCESS;
			message.obj = rspObj;
			message.sendToTarget();
		}

		@Override
		public void netError(int taskId, String msg) {
			Message message = Message.obtain(mHandler);
			message.what = DOWNLOAD_ERROR;
			message.obj = msg;
			message.sendToTarget();
		}

		@Override
		public void cancel(int taskId) {

		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		broadcastReceiver(); // 注册广播

		setDownloadNotification(); // 显示通知栏

		openDownloadThread(intent); // 开启下载

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		if (isActiveKill) {
			mHandler.sendEmptyMessage(DOWNLOAD_ERROR);
			return;
		}
		super.onDestroy();
		unregisterReceiver(mDownloadProgressReceiver);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
