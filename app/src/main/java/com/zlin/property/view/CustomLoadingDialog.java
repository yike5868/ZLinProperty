package com.zlin.property.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.zlin.property.R;


public class CustomLoadingDialog extends ProgressDialog {

	public CustomLoadingDialog(Context context) {
		super(context, R.style.LoadingDialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fu_view_loading_dialog);
		setCanceledOnTouchOutside(false);
	}

}
