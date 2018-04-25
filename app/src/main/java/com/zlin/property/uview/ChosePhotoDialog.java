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

/**
 * Created by zhanglin03 on 2018/4/25.
 */

public class ChosePhotoDialog extends Dialog {
    private Handler mHandler;
    private Button photograph;
    private Button photograph_smw;
    private Button album;
    private Button album_swm;
    private Button cancel;
    private TextView ktdInfoView;
    private TextView ktdKeyView;
    private String imageType;
    private LinearLayout ktdLinear;
    public ChosePhotoDialog(Context context, Handler mHandle, String imageType){
        super(context, R.style.TransparentDialog);
        this.mHandler = mHandle;
        this.imageType = imageType;
    }
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.chose_photo_dialog);

    }


    private class OnChildClickListener implements View.OnClickListener {
        // 点击类型索引，对应前面的CLICK_INDEX_xxx
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
