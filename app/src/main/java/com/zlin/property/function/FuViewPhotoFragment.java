package com.zlin.property.function;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zlin.property.FuApp;
import com.zlin.property.activity.FuContentActivity;
import com.zlin.property.control.FragmentParent;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuResponse;
import com.zlin.property.control.FuUiFrameManager;

import java.util.List;

/**
 * Created by zhanglin03 on 2018/7/9.
 */

public class FuViewPhotoFragment extends FragmentParent {

    List<String> photoList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FuUiFrameManager lFuUiFrameManager = ((FuApp) getActivity()
                .getApplication()).getFuUiFrameManager();

        mModel = lFuUiFrameManager.createFuModel(
                FuUiFrameManager.FU_VIEW_PHOTO, getActivity(),
                new OnEventClick());
        Bundle lBundle = ((FuContentActivity) getActivity()).getIntentBundle();
        if(lBundle!=null) {
            photoList = lBundle.getStringArrayList("photoList");
        }

        ((FuViewPhotoView)mModel).setPhotoList(photoList);
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
            switch (event){
            }
        }
    }


}
