package com.zlin.property.function;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zlin.property.R;
import com.zlin.property.activity.FuContentActivity;
import com.zlin.property.activity.FuMainActivity;
import com.zlin.property.activity.FuParentActivity;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.control.FuUiFrameModel;
import com.zlin.property.tools.AppConfig;
import com.zlin.property.tools.ToolUtil;
import com.zlin.property.view.FuImageView;
import com.zlin.property.view.FuTextView;
import com.zlin.property.view.FuWebView;

/**
 * Created by zhanglin03 on 2018/7/23.
 */

public class FuWebBodyView extends FuUiFrameModel implements View.OnClickListener {

    FuWebView webView;
    FuTextView tv_title;
    FuImageView iv_left;
    String h5param = "1123";

    public  boolean isFinish = false;
    public String url;
    @Override
    protected void createFuLayout() {

        mFuView = LayoutInflater.from(mContext).inflate(
                R.layout.fu_web_view, null);
    }

    public FuWebBodyView(Context cxt, FuEventCallBack callBack) {
        super(cxt, callBack);
    }

    @Override
    protected void initFuData() {

    }

    @Override
    protected void initWidget() {
        tv_title = (FuTextView)mFuView.findViewById(R.id.tv_title);
        iv_left = (FuImageView) mFuView.findViewById(R.id.iv_left);
        tv_title.setVisibility(View.VISIBLE);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        webView  =(FuWebView) mFuView.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        /**
         * 原因：webview 从Lollipop(5.0)开始 webview默认不允许混合模式，https当中不能加载http资源，如果要加载，需单独设置开启。
         * 文件服务器返回的文件路径是http开头的，为防止广告侵入，我们的页面路径是https开头的
         */
        if (Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();
        // 清除cookie即可彻底清除缓存
        CookieSyncManager.createInstance(mContext);
        CookieManager.getInstance().removeAllCookie();
        //不使用缓存：
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        try {
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    System.out.println("url -> " + url);
                    ToolUtil.showPopWindowLoading(mContext);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    isFinish = true;
                    webView.loadUrl("javascript:localInfo('')");
                    System.out.println(" -> onPageFinished");
                    tv_title.setText(view.getTitle());
                    ToolUtil.hidePopLoading();
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    System.out.println("onReceivedError -->> ");
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    ToolUtil.hidePopLoading();
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    System.out.println("onReceivedSslError -->>");
                    handler.proceed();
                    ToolUtil.hidePopLoading();
                }
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    goNewPage(webView,url);
                    return true;
                }
            });
            webView.addJavascriptInterface(new JavaScriptInterface(), "appInterface");
            System.out.println("start .....  \nurl -> " + url);
        } catch (Exception e) {
            Log.d("tag", "onCreate", e);
        }

    }
    public void goNewPage(WebView view,String url){
        if(mContext instanceof FuMainActivity){
            Bundle bundle = new Bundle();
            bundle.putSerializable("url",url);
            ((FuParentActivity) mContext).addFragment( FuUiFrameManager.FU_WEB_VIEW, bundle);
        }else{
            view.loadUrl(url);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
               if(!goBack()){
                  if(((Activity)mContext) instanceof FuContentActivity){
                      ((FuContentActivity) mContext).onKeyDown(KeyEvent.KEYCODE_BACK,null);
                  }
               }
               break;
        }
    }

    class JavaScriptInterface {
        @JavascriptInterface
        public void showLocalBar(String titleColor) {
            System.out.println("showLocalBar ---->>>>>> titleColor -> " + titleColor);
        }
        @JavascriptInterface
        public void jumpToNextPage(String param){
            if(!param.startsWith("http")){
                param = AppConfig.VUE_URL+param;
            }
            goNewPage(webView,param);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        webView.loadUrl(url);
    }

    public boolean goBack(){
        if(webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return false;
    }
}
