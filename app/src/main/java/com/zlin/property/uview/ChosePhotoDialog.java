package com.zlin.property.uview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zlin.property.R;
import com.zlin.property.control.FragmentParent;
import com.zlin.property.view.FuButton;

/**
 * Created by zhanglin03 on 2018/4/25.
 */

public class ChosePhotoDialog extends Dialog {
    private Handler mHandler;
    private FuButton btn_cam;
    private Button btn_chose;
    private Button btn_cancel;
    public ChosePhotoDialog(Context context, Handler mHandle){
        super(context, R.style.TransparentDialog);
        this.mHandler = mHandle;
    }
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.chose_photo_dialog);
        btn_cam = (FuButton)findViewById(R.id.btn_cam);
        btn_chose = (FuButton)findViewById(R.id.btn_chose);
        btn_cancel = (FuButton)findViewById(R.id.btn_cancel);
        btn_cam.setOnClickListener(new OnChildClickListener(FragmentParent.MSG_CAMEAR));
        btn_chose.setOnClickListener(new OnChildClickListener(FragmentParent.MSG_CHOSE));
        btn_cancel.setOnClickListener(new OnChildClickListener(FragmentParent.MSG_CANCEL));
    }


    private class OnChildClickListener implements View.OnClickListener {
        private int type;
        public OnChildClickListener(int type) {
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            // 创建Message并填充数据，通过mHandle联系Activity接收处理
            Message msg = new Message();
            msg.what = type;
            mHandler.sendMessage(msg);
        }

    }
}
