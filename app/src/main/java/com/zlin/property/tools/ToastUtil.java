package com.zlin.property.tools;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.zlin.property.FuApp;

public class ToastUtil {
	
	private static Toast mToast;

	/**
	 * Toast快速切换
	 */
	public static void showToast(Context context, String msg, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, msg, duration);
		} else {
			mToast.setText(msg);
		}

		mToast.show();
	}
	public static void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(FuApp.getInstance(), msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
		}
		mToast.show();
	}
	/**
	 * 立即取消Toast
	 */
	public static void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}
}
