// Copyright (C) 2012-2013 UUZZ All rights reserved
package com.zlin.property.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.zlin.property.R;
import com.zlin.property.tools.Tool;


/**
 * 完成各种提示对话框的显示
 */
public class CustomAlertDialog extends Dialog implements
		View.OnClickListener {

	private int mType;

	public final static int DOUBLE_BUTTON_DES = 1; // 拥有两个Button的说明型（没有输入框）的对话框
	/**
	 * 两个按钮，一个内容窗口（可以解析html）
	 */
	public final static int DOUBLE_BUTTON_HTML_TEXTVIEW = 2;
	public final static int ONE_BUTTON_DES = 3; // 拥有一个Button的说明型（没有输入框）的对话框
	public final static int DOUBLE_TEXTVIEW_ITEM = 4; // 2行文本的弹出框

	public final static int DOUBLE_EDIT = 5; // 包含输入框的对话框

	private UzCustomAlertDialogCallBack mCallBack;

	private CustomAlertDialogEidtCallBack mEditCallBack;

	private int mTypeClick = -1; // 对话框的类型，又使用者决定，以便处理button的点击事件

	private String mText;

	private int mLayoutId;

	private Context mContext;

	private EditText mShopistName;
	private EditText mShoplistDescription;

	public static CustomAlertDialog show(Context context, int type,
                                         String title, CustomAlertDialogEidtCallBack callback) {

		CustomAlertDialog mDialog = new CustomAlertDialog(context);
		mDialog.setTextTitle(title);
		mDialog.setOnClickLinstenBtn(callback, type);
		mDialog.setCustomContentView(type);

		mDialog.showCustomDialog();

		return mDialog;
	}

	public static CustomAlertDialog show(Context context, int type,
                                         String content, String title, String[] lBtnName,
                                         UzCustomAlertDialogCallBack callback) {

		CustomAlertDialog mDialog = new CustomAlertDialog(context);
		mDialog.setTextTitle(title);
		mDialog.setCustomBtnName(lBtnName);
		mDialog.setCustomContent(content == null ? "" : content);
		mDialog.setOnClickLinstenBtn(callback, type);
		mDialog.setCustomContentView(type);

		mDialog.showCustomDialog();

		return mDialog;
	}

	public CustomAlertDialog(Context context) {
		super(context, R.style.MyDialogTheme);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View lViewDialog = LayoutInflater.from(mContext).inflate(
				R.layout.custom_alert_dialog, null);

		this.setContentView(lViewDialog);

		LinearLayout mLin = (LinearLayout) findViewById(R.id.uz_alert_dialog_content);
		setCanceledOnTouchOutside(false);
		TextView lText = (TextView) findViewById(R.id.uz_alert_dialog_title);
		lText.setText(mText == null ? "" : mText);

		View lView = LayoutInflater.from(mContext).inflate(mLayoutId, null);
		lView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		if (lView != null) {

			int lCount = mLin.getChildCount();

			if (lCount > 0) {
				mLin.removeAllViews();
			}

			switch (mType) {
			case DOUBLE_EDIT:

				mShopistName = (EditText) lView
						.findViewById(R.id.shoplist_name);
				mShoplistDescription = (EditText) lView
						.findViewById(R.id.shoplist_description);

				Button shoplist_sure = (Button) lView
						.findViewById(R.id.shoplist_sure);
				shoplist_sure.setText("确定");
				shoplist_sure.setOnClickListener(this);

				Button lBtnDoubleCancel = (Button) lView
						.findViewById(R.id.shoplist_cancel);
				lBtnDoubleCancel.setText("取消");
				lBtnDoubleCancel.setOnClickListener(this);

				break;
			case DOUBLE_TEXTVIEW_ITEM:

				TextView lEdit = (TextView) lView.findViewById(R.id.edit_info);

				lEdit.setOnClickListener(this);

				TextView lDelete = (TextView) lView
						.findViewById(R.id.delete_info);

				lDelete.setOnClickListener(this);

				break;

			case DOUBLE_BUTTON_DES:

				TextView lDoubleContent = (TextView) lView
						.findViewById(R.id.uz_double_des);
				lDoubleContent.setText(mContent == null ? "" : mContent);

				Button lBtnDouble1 = (Button) lView
						.findViewById(R.id.uz_double_btn1);
				lBtnDouble1.setText(mAlertBtn == null ? "立即开通" : mAlertBtn[0]);
				lBtnDouble1.setOnClickListener(this);

				Button lBtnDouble2 = (Button) lView
						.findViewById(R.id.uz_double_btn2);
				lBtnDouble2.setText(mAlertBtn == null ? "稍后再说" : mAlertBtn[1]);
				lBtnDouble2.setOnClickListener(this);

				break;
			case DOUBLE_BUTTON_HTML_TEXTVIEW:

				TextView lHtmlContent = (TextView) lView
						.findViewById(R.id.html_text_content);
				lHtmlContent.setMovementMethod(ScrollingMovementMethod
						.getInstance());

				lHtmlContent.setText(Html.fromHtml(mContent == null ? ""
						: mContent));

				Button lHtmlContentBtn1 = (Button) lView
						.findViewById(R.id.uz_double_btn1);
				lHtmlContentBtn1.setText(mAlertBtn == null ? "立即开通"
						: mAlertBtn[0]);
				lHtmlContentBtn1.setOnClickListener(this);

				Button lHtmlContentBtn2 = (Button) lView
						.findViewById(R.id.uz_double_btn2);
				lHtmlContentBtn2.setText(mAlertBtn == null ? "稍后再说"
						: mAlertBtn[1]);
				lHtmlContentBtn2.setOnClickListener(this);

				break;
			case ONE_BUTTON_DES:

				TextView lOneContent = (TextView) lView
						.findViewById(R.id.uz_double_des);
				lOneContent.setText(mContent == null ? "" : mContent);

				Button lBtnOne1 = (Button) lView
						.findViewById(R.id.uz_double_btn1);
				lBtnOne1.setText(mAlertBtn == null ? "立即开通" : mAlertBtn[0]);
				lBtnOne1.setOnClickListener(this);

				Button lBtnOne2 = (Button) lView
						.findViewById(R.id.uz_double_btn2);
				lBtnOne2.setVisibility(View.GONE);
				break;
			}

			mLin.addView(lView);
		}

		Window dialogWindow = this.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = Tool.dipTopx(280, mContext);
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

		dialogWindow.setAttributes(lp);
	}

	// ----------------------------------------------

	public void setTextTitle(String title) {

		if (title == null) {
			return;
		}

		mText = title;
	}

	private String mContent;

	private void setCustomContent(String content) {

		if (content == null) {
			return;
		}
		mContent = content;
	}

	private String[] mAlertBtn;

	private void setCustomBtnName(String[] btn) {
		mAlertBtn = btn;
	}

	private void setCustomContentView(int layoutId) {

		mType = layoutId;

		switch (mType) {

		case ONE_BUTTON_DES:
		case DOUBLE_BUTTON_DES:

			mLayoutId = R.layout.uz_double_button_des;
			break;
		case DOUBLE_TEXTVIEW_ITEM:

			mLayoutId = R.layout.friend_info_dialog_view;
			break;
		case DOUBLE_EDIT:

			mLayoutId = R.layout.add_menu_list_dialog;
			break;
		case DOUBLE_BUTTON_HTML_TEXTVIEW:

			mLayoutId = R.layout.dialog_double_button_html_textview;
			break;
		}

	}

	/**
	 * 设置对话框点击事件的响应回调
	 * 
	 * @param callback
	 *            回调对象
	 * @param type
	 *            对话框类型，用于一个页面有多个对话框弹出，回调的区分
	 */
	private void setOnClickLinstenBtn(UzCustomAlertDialogCallBack callback,
			int type) {

		if (callback == null) {
			return;
		}
		mCallBack = callback;
		mTypeClick = type;
	}

	/**
	 * 设置对话框点击事件的响应回调 (拥有输入框的对话框回调)
	 * 
	 * @param callback
	 *            回调对象
	 * @param type
	 *            对话框类型，用于一个页面有多个对话框弹出，回调的区分
	 */
	private void setOnClickLinstenBtn(CustomAlertDialogEidtCallBack callback,
			int type) {

		if (callback == null) {
			return;
		}
		mEditCallBack = callback;
		mTypeClick = type;
	}

	@Override
	public void onClick(View v) {

		if (mCallBack == null && mEditCallBack == null) {
			return;
		}

		switch (v.getId()) {
		case R.id.shoplist_sure:

			String lName = mShopistName.getText().toString().trim();

			String lDes = mShoplistDescription.getText().toString().trim();

			String[] lData = new String[2];
			lData[0] = lName;
			lData[1] = lDes;

			mEditCallBack.onClickConfirm(lData);

			break;
		case R.id.uz_double_btn1:
		case R.id.edit_info:

			mCallBack.onClickConfirm(mTypeClick);

			break;

		case R.id.uz_double_btn2:
		case R.id.delete_info:

			mCallBack.onClickCancel(mTypeClick);

			break;
		}

		this.dismiss();

	}

	// ---------------------------------------------------

	private void showCustomDialog() {
		try {
			show();
		} catch (Exception e) {

			return;
		}
	}

	public interface UzCustomAlertDialogCallBack {

		public void onClickConfirm(int type);

		public void onClickCancel(int type);

	}

	public interface CustomAlertDialogEidtCallBack {

		public void onClickConfirm(String[] content);

	}

}
