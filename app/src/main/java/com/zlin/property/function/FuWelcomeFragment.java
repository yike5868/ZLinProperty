package com.zlin.property.function;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zlin.property.FuApp;
import com.zlin.property.activity.FuInitNavActivity;
import com.zlin.property.control.FragmentParent;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuResponse;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.tools.ToastUtil;


@SuppressLint("HandlerLeak")
public class FuWelcomeFragment extends FragmentParent {

	private final int BEGIN_NET = 0; // 开始版本信息网络请求
	private final int SUCCESS_NET = 100; // 网络成功
	private final int ERROR_NET = 500; // 网络错误

	Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case BEGIN_NET:

				getChecks();


				break;
			case SUCCESS_NET:
				break;
			case ERROR_NET:

				ToastUtil.showToast(getActivity(), (String) msg.obj,
						Toast.LENGTH_LONG);
				((FuInitNavActivity) getActivity()).finishApp();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		Log.e("activity","FuWelcomeFragment");
		FuUiFrameManager lFuUiFrameManager = ((FuApp) getActivity()
				.getApplication()).getFuUiFrameManager();

		mModel = lFuUiFrameManager.createFuModel(
				FuUiFrameManager.FU_WELCOME, getActivity(),
				new EventCallBack());

		return mModel.getFuView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mHandler.sendEmptyMessageDelayed(BEGIN_NET, 3000);
	}

	public static final int EVENT_DOWNLOAD_NOW = 1; // 立即下载
	public static final int EVENT_NOT_UPDATA = 2; // 暂不更新
	public static final int EVENT_APP_FINISH = 3; // 退出程序
	public static final int EVENT_DOWNLOAD_IMPORT = 4; // 重要下载

	class EventCallBack implements FuEventCallBack {

		@Override
		public void EventClick(int event, Object object) {

			switch (event) {
			case EVENT_DOWNLOAD_NOW:

				((FuInitNavActivity) getActivity()).startDownloadService(1,
						(String) object);
//				ToastUtil
//						.showToast(getActivity(), "正在后台下载", Toast.LENGTH_SHORT);
//				jumpPage();
				break;
			case EVENT_NOT_UPDATA:

//				jumpPage();
				break;
			case EVENT_DOWNLOAD_IMPORT:

				((FuInitNavActivity) getActivity()).startDownloadService(1,
						(String) object);
				ToastUtil
						.showToast(getActivity(), "正在后台下载", Toast.LENGTH_SHORT);
				((FuInitNavActivity) getActivity()).finishApp();
				break;
			case EVENT_APP_FINISH:

				((FuInitNavActivity) getActivity()).finishApp();
				break;
			}
		}
	}

	/**
	 * 判断是否更新
	 */
	public void isUpDataVersion() {

		jumpPage();
	}

	private boolean getChecks(){


		if (Build.VERSION.SDK_INT >= 23) {
			int checkCallPhonePermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
			if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
//				FuWelcomeFragment.this.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
//                , Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
//				return false;
			}else{

			}
		} else {

		}
        isUpDataVersion();
		return true;

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case 111:

				if (grantResults!=null && grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "请稍等！", Toast.LENGTH_SHORT)
                            .show();

//                    isUpDataVersion();

				} else {
					Toast.makeText(getContext(), "请在设置中获取权限！", Toast.LENGTH_SHORT)
							.show();
				}


				break;
			default:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}


	/**
	 * 跳转页面
	 */
	public void jumpPage() {

			((FuInitNavActivity) getActivity()).goToLoginPage();

	}

	/**
	 * 获取版本号
	 */
	public int getVerCode() {

		int verCode = -1;
		try {
			verCode = getActivity().getPackageManager().getPackageInfo(
					"com.original.client", 0).versionCode;
		} catch (NameNotFoundException e) {
			verCode = -1;
		}
		return verCode;
	}

	/**
	 * 获得版本名称
	 */
	public String getVerName() {
		String verName = "";
		try {
			verName = getActivity().getPackageManager().getPackageInfo(
					"net.greatsoft.followup", 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}

	@Override
	protected void loadDataChild(int taskId, FuResponse rspObj) {
		Message message = Message.obtain(mHandler);
		message.what = SUCCESS_NET;
		message.obj = rspObj;
		message.sendToTarget();
	}

	@Override
	protected void netErrorChild(int taskId, String msg) {
		Message message = Message.obtain(mHandler);
		message.what = ERROR_NET;
		message.obj = msg;
		message.sendToTarget();
	}

	@Override
	protected void cancelChild(int taskId) {
	}
}
