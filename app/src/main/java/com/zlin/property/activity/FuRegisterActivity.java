package com.zlin.property.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zlin.property.R;
import com.zlin.property.control.FuResponse;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.db.po.RoomItem;
import com.zlin.property.db.po.TempRoom;
import com.zlin.property.db.po.UserInfo;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.NetCallBack;
import com.zlin.property.net.NetManager;
import com.zlin.property.net.TaskManager;
import com.zlin.property.tools.AppConfig;
import com.zlin.property.tools.StringUtil;
import com.zlin.property.tools.ToastUtil;
import com.zlin.property.tools.ToolUtil;
import com.zlin.property.view.FuEditText;
import com.zlin.property.view.FuImageView;
import com.zlin.property.view.FuTextView;
import com.zlin.property.view.JellyInterpolator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhanglin03 on 2018/4/19.
 */

public class FuRegisterActivity extends FuParentActivity implements View.OnClickListener {

    @Bind(R.id.et_username)
    FuEditText et_username;
    @Bind(R.id.et_password)
    FuEditText et_password;
    @Bind(R.id.et_rpassword)
    FuEditText et_rpassword;


    @Bind(R.id.tv_title)
    FuTextView tv_title;
    @Bind(R.id.tv_right)
    FuTextView tv_right;
    @Bind(R.id.iv_left)
    FuImageView iv_left;

    @Bind(R.id.btn_register)
    TextView btn_register;
    @Bind(R.id.input_layout_rpsw)
    LinearLayout input_layout_rpsw;
    @Bind(R.id.input_layout_room)
    LinearLayout input_layout_room;
    @Bind(R.id.tv_room)
    FuTextView tv_room;

    @Bind(R.id.input_layout_real_name)
    LinearLayout input_layout_real_name;
    @Bind(R.id.et_realName)
    FuEditText et_realName;


    private View progress;

    private View mInputLayout;

    private float mWidth, mHeight;

    private LinearLayout mName, mPsw;
    UserInfo userInfo;
    TempRoom tempRoom ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fu_register_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        input_layout_rpsw.setVisibility(View.VISIBLE);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = (LinearLayout) findViewById(R.id.input_layout_name);
        mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
        btn_register.setOnClickListener(this);
        input_layout_room.setOnClickListener(this);
        input_layout_room.setVisibility(View.VISIBLE);
        tv_room.setOnClickListener(this);
        input_layout_real_name.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                mWidth = btn_register.getMeasuredWidth();
                mHeight = btn_register.getMeasuredHeight();
                mName.setVisibility(View.INVISIBLE);
                mPsw.setVisibility(View.INVISIBLE);
                inputAnimator(mInputLayout, mWidth, mHeight);
                break;
            case R.id.iv_left:
                startActivity(new Intent(FuRegisterActivity.this, FuLoginActivity.class));
                finish();
                break;
            case R.id.btn_register:
                String userName = et_username.getText().toString();
                String password = et_password.getText().toString();
                String repassword = et_rpassword.getText().toString();
                String realName = et_realName.getText().toString();
                if(StringUtil.isEmpty(userName)){
                    ToastUtil.showToast("请输入用户名！");
                }
                if(StringUtil.isEmpty(password)){
                    ToastUtil.showToast("请输入密码！");
                }
                if(StringUtil.isEmpty(repassword)){
                    ToastUtil.showToast("请再次输入密码！");
                }
                if(!password.equals(repassword)){
                    ToastUtil.showToast("两次密码输入不一致！");
                }
                if(StringUtil.isEmpty(realName)){
                    ToastUtil.showToast("请输入真实姓名！");
                }
                if(StringUtil.isEmpty(AppConfig.tempRoom.getRoomId())){
                    ToastUtil.showToast("请选择房间！");
                }
                userInfo = new UserInfo();
                userInfo.setUserName(userName);
                userInfo.setPassword(password);
                userInfo.setMicrodistrictId(tempRoom.getMicrodistrictId());
                userInfo.setBuildingId(tempRoom.getBuildingId());
                userInfo.setUnitId(tempRoom.getUnitId());
                userInfo.setRoomId(tempRoom.getRoomId());
                userInfo.setRealName(realName);

                MyTask loginTask = TaskManager.getInstace().register(new mNetCallBack(), userInfo);
                NetManager manager = NetManager.getInstance(this);
                manager.addNetTask(loginTask);
                manager.excuteNetTask(loginTask);
                break;

            case R.id.input_layout_room:
            case R.id.tv_room:

                Intent intent = new Intent(this, FuContentActivity.class);
                intent.putExtra(FuContentActivity.FRAGMENT_ACTION_KEY, FuUiFrameManager.FU_CHOSE_ROOM);
                startActivity(intent);

                break;
        }
    }


    public static final int MSG_MESSAGE = 1;
    public static final int MSG_MAIN = 2;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ToolUtil.hidePopLoading();
            switch (msg.what) {
                case MSG_MESSAGE :
                    ToastUtil.showToast(msg.obj.toString());
                    break;
                case MSG_MAIN:
                    startActivity(new Intent(FuRegisterActivity.this, FuLoginActivity.class));
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(AppConfig.tempRoom != null&& !StringUtil.isEmpty(AppConfig.tempRoom.getRoomId())){
            tempRoom = AppConfig.tempRoom;
            tv_room.setText(tempRoom.getMicrodistrictName()+tempRoom.getBuildingName()+tempRoom.getUnitName()+tempRoom.getRoomName());
        }
    }

    class mNetCallBack implements NetCallBack {

        @Override
        public void cancel(int taskId) {
            Message message = handler.obtainMessage();
            message.obj = "已取消注册！";
            message.what = MSG_MESSAGE;
            handler.sendMessage(message);
        }

        @Override
        public void loadData(int taskId, FuResponse rspObj) {
            Message message = handler.obtainMessage();

            if (rspObj!=null&&rspObj.getSuccess()) {
                userInfo = JSON.parseObject(rspObj.getData().toString(),UserInfo.class);
                saveSP("userInfo",userInfo);
                handler.sendEmptyMessage(MSG_MAIN);
            } else if(rspObj!=null){
                message.obj = rspObj.getErrMessage();
                message.what = MSG_MESSAGE;
                handler.sendMessage(message);
            }else{
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

    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
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

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }
}
