package com.zlin.property.function;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zlin.property.FuApp;
import com.zlin.property.control.FragmentParent;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuResponse;
import com.zlin.property.control.FuUiFrameManager;

/**
 * Created by zhanglin03 on 2018/7/10.
 */

public class FuMineFragment extends FragmentParent {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FuUiFrameManager lFuUiFrameManager = ((FuApp) getActivity()
                .getApplication()).getFuUiFrameManager();

        mModel = lFuUiFrameManager.createFuModel(
                FuUiFrameManager.FU_MINE, getActivity(),
                new OnEventClick());

        return mModel.getFuView();
    }
    @Override
    protected void loadDataChild(int taskId, FuResponse rspObj) {

    }

    @Override
    protected void netErrorChild(int taskId, String msg) {

    }

    @Override
    protected void cancelChild(int taskId) {

    }

    @Override
    public void initData(Bundle bundle) {

    }
    class OnEventClick implements FuEventCallBack {
        @Override
        public void EventClick(int event, Object object) {

        }
    }

}
