package com.zlin.property.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zlin.property.R;
import com.zlin.property.control.FuResponse;
import com.zlin.property.db.po.Room;
import com.zlin.property.db.po.UserInfo;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.NetCallBack;
import com.zlin.property.net.NetManager;
import com.zlin.property.net.TaskManager;
import com.zlin.property.tools.AESUtil;
import com.zlin.property.tools.MD5Utils;
import com.zlin.property.tools.StringUtil;
import com.zlin.property.tools.ToastUtil;
import com.zlin.property.tools.ToolUtil;
import com.zlin.property.tools.encrypt.AES;
import com.zlin.property.view.FuEditText;
import com.zlin.property.view.FuImageView;
import com.zlin.property.view.FuTextView;
import com.zlin.property.view.JellyInterpolator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhanglin03 on 2018/4/19.
 */

public class FuLoginActivity extends FuParentActivity implements View.OnClickListener {

    @Bind(R.id.et_username)
    FuEditText et_username;
    @Bind(R.id.et_password)
    FuEditText et_password;

    @Bind(R.id.tv_title)
    FuTextView tv_title;
    @Bind(R.id.tv_right)
    FuTextView tv_right;
    @Bind(R.id.iv_left)
    FuImageView iv_left;

    @Bind(R.id.btn_login)
    TextView btn_login;

    @Bind(R.id.rl_title)
    RelativeLayout rl_title;

    @Bind(R.id.view_split)
    View view_split;

    private View progress;

    private View mInputLayout;

    private float mWidth, mHeight;

    private LinearLayout mName, mPsw;

    UserInfo userInfo;
    String userName;
    String password;
    public static final int MSG_MESSAGE = 1;
    public static final int MSG_MAIN = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fu_login_activity);
        ButterKnife.bind(this);
        initView();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ToolUtil.hidePopLoading();
            switch (msg.what) {
                case MSG_MESSAGE:
                    ToastUtil.showToast(msg.obj.toString());
                    break;
                case MSG_MAIN:
                    if (getSP("selectRoom", Room.class) == null) {
                        if (!ToolUtil.isEmpty(userInfo.getRoomList()))
                            saveSP("selectRoom", userInfo.getRoomList().get(0));
                    }
                    startActivity(new Intent(FuLoginActivity.this, FuMainActivity.class));
                    finish();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                userName = et_username.getText().toString().trim();
                password = et_password.getText().toString().trim();

                if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
                    ToastUtil.showToast("请输入用户名/密码");
                    return;
                }
                login();

                break;
            case R.id.tv_right:
                Intent intent = new Intent(FuLoginActivity.this, FuRegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void login() {
        ToolUtil.showPopWindowLoading(FuLoginActivity.this);
        userInfo = new UserInfo();
        userInfo.setUserName(userName);
        password = MD5Utils.encode2hex(password);
        userInfo.setPassword(password);
        MyTask loginTask = TaskManager.getInstace().login(new mNetCallBack(), userInfo);
        NetManager manager = NetManager.getInstance(this);
        manager.addNetTask(loginTask);
        manager.excuteNetTask(loginTask);
    }


    class mNetCallBack implements NetCallBack {

        @Override
        public void cancel(int taskId) {
            Message message = handler.obtainMessage();
            message.obj = "已取消登录！";
            message.what = MSG_MESSAGE;
            handler.sendMessage(message);
        }

        @Override
        public void loadData(int taskId, FuResponse rspObj) {
            Message message = handler.obtainMessage();
            switch (taskId) {
                case MyTask.GET_VERSION:

                    break;

                case MyTask.LOGIN:
                    break;
            }
            if (rspObj != null && rspObj.getSuccess()) {
                userInfo = JSON.parseObject(rspObj.getData().toString(), UserInfo.class);
                saveSP("userInfo", userInfo);
                handler.sendEmptyMessage(MSG_MAIN);
            } else if (rspObj != null) {
                message.obj = rspObj.getMessage();
                message.what = MSG_MESSAGE;
                handler.sendMessage(message);
            } else {
                message.obj = "网络连接错误";
                message.what = MSG_MESSAGE;
                handler.sendMessage(message);
            }
        }

        @Override
        public void netError(int taskId, String msg) {
            Message message = handler.obtainMessage();
            message.obj = msg;
            message.what = MSG_MESSAGE;
            handler.sendMessage(message);
        }
    }


    private void initView() {
        view_split.setVisibility(View.GONE);
        rl_title.setBackgroundResource(R.color.titleColor);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("注册");
        tv_right.setOnClickListener(this);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = (LinearLayout) findViewById(R.id.input_layout_name);
        mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
        btn_login.setOnClickListener(this);

        userInfo = getSP("userInfo",UserInfo.class);
        if(userInfo !=null){
            et_username.setText(userInfo.getUserName());
        }
    }

    ValueAnimator animator;
    AnimatorSet set;

    private void inputAnimator(final View view, float w, float h) {

        set = new AnimatorSet();

        animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);
                login();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

    }

    ObjectAnimator animator3;

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }
}
