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
import com.zlin.property.tools.AppConfig;
import com.zlin.property.tools.StringUtil;
import com.zlin.property.view.FuWebView;

/**
 * Created by zhanglin03 on 2018/7/23.
 */

public class FuWebBodyFragment extends FragmentParent {
    String url;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FuUiFrameManager lFuUiFrameManager = ((FuApp) getActivity()
                .getApplication()).getFuUiFrameManager();

        mModel = lFuUiFrameManager.createFuModel(
                FuUiFrameManager.FU_WEB_VIEW, getActivity(),
                new OnEventClick());
        ((FuWebBodyView)mModel).setUrl(url);
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
        if(bundle == null|| StringUtil.isEmpty(bundle.getString("url")))
            url = AppConfig.VUE_PULL;
        else
            url = bundle.getString("url");
    }
    class OnEventClick implements FuEventCallBack {
        @Override
        public void EventClick(int event, Object object) {

        }
    }

    public boolean goBack(){
        return ((FuWebBodyView)mModel).goBack();
    }

}
