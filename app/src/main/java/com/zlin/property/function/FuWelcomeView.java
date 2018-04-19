package com.zlin.property.function;

import android.content.Context;
import android.view.LayoutInflater;

import com.zlin.property.R;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuUiFrameModel;
import com.zlin.property.view.CustomAlertDialog;


public class FuWelcomeView extends FuUiFrameModel {

	public FuWelcomeView(Context cxt, FuEventCallBack callBack) {
		super(cxt, callBack);
	}

	@Override
	protected void createFuLayout() {

		mFuView = LayoutInflater.from(mContext).inflate(
				R.layout.fu_welcome_view, null);
	}

	@Override
	protected void initFuData() {

		findView();
	}

	@Override
	protected void initWidget() {

	}

	/**
	 * 发现View 给Button设置监听事件
	 */
	public void findView() {

	}

	public void openDownLoadDialog(final String url, String avText) {

		CustomAlertDialog
				.show(mContext,
						CustomAlertDialog.DOUBLE_BUTTON_HTML_TEXTVIEW,
						(avText == null || avText.trim().length() < 1) ? "有新版本哦!建议立即下载使用"
								: avText, "提示",
						new String[] { "立即下载", "暂不更新" },
						new CustomAlertDialog.UzCustomAlertDialogCallBack() {

							@Override
							public void onClickConfirm(int type) {

								mEventCallBack.EventClick(
										FuWelcomeFragment.EVENT_DOWNLOAD_NOW,
										url);
							}

							@Override
							public void onClickCancel(int type) {

								mEventCallBack.EventClick(
										FuWelcomeFragment.EVENT_NOT_UPDATA,
										null);
							}
						});
	}

	public void openImportantDownLoadDialog(final String url, String avText) {

		CustomAlertDialog.show(mContext, CustomAlertDialog.DOUBLE_BUTTON_HTML_TEXTVIEW,
				(avText == null || avText.trim().length() < 1) ? "有重大版本更新！立即下载"
						: avText, "提示", new String[] { "立即下载", "退出程序" },
				new CustomAlertDialog.UzCustomAlertDialogCallBack() {

					@Override
					public void onClickConfirm(int type) {

						mEventCallBack.EventClick(
								FuWelcomeFragment.EVENT_DOWNLOAD_IMPORT, url);
					}

					@Override
					public void onClickCancel(int type) {

						mEventCallBack.EventClick(
								FuWelcomeFragment.EVENT_APP_FINISH, null);
					}
				});
	}
}
