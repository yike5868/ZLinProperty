package com.zlin.property.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.zlin.property.R;


public class CustomGuideDialog extends Dialog implements
		View.OnClickListener {

	private int mResId;

	public CustomGuideDialog(Context context, int resId) {
		super(context, R.style.GuideDialog);
		mResId = resId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fu_view_guide_dialog);
		Window win = getWindow();
		win.getDecorView().setPadding(0, 0, 0, 0);
		WindowManager.LayoutParams lp = win.getAttributes();
		        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		        win.setAttributes(lp);

		ImageView view = (ImageView) findViewById(R.id.guide_View);
		view.setOnClickListener(this);
		view.setBackgroundResource(mResId);
	}

	@Override
	public void onClick(View v) {
		dismiss();
	}

}
