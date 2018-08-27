package com.zlin.property.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.zlin.property.R;
import com.zlin.property.control.FuResponse;
import com.zlin.property.db.po.Room;
import com.zlin.property.db.po.UserInfo;
import com.zlin.property.db.po.Version;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.NetCallBack;
import com.zlin.property.net.NetManager;
import com.zlin.property.net.TaskManager;
import com.zlin.property.services.DownloadService;
import com.zlin.property.tools.ToastUtil;
import com.zlin.property.tools.ToolUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhanglin03 on 2018/8/2.
 */


public class FuSplishActivity extends FuParentActivity {


    private int isFinish = 0;
    Version version;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.fu_splish_view);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isFinish++;
                handler.sendEmptyMessage(MSG_MAIN);
            }
        }, 3000);
        getVersion();
    }


    private void getVersion() {
        MyTask loginTask = TaskManager.getInstace().getVersion(new mNetCallBack(), null);
        NetManager manager = NetManager.getInstance(this);
        manager.addNetTask(loginTask);
        manager.excuteNetTask(loginTask);
    }

    class mNetCallBack implements NetCallBack {

        @Override
        public void cancel(int taskId) {

        }

        @Override
        public void loadData(int taskId, FuResponse rspObj) {
            Message message = handler.obtainMessage();
            switch (taskId) {
                case MyTask.GET_VERSION:
                     if(rspObj == null)
                         return;
                    if(ToolUtil.isEmpty(rspObj.getData().toString())){
                        isFinish++;
                        handler.sendEmptyMessage(MSG_MAIN);
                        return;
                    }
                    version = JSON.parseObject(rspObj.getData().toString(), Version.class);

                    if (version.getVersionCode() > ToolUtil.getVerCode(FuSplishActivity.this)) {
//                    if (0 < ToolUtil.getVerCode(FuSplishActivity.this)) {
                        handler.sendEmptyMessage(MSG_UPDATE);
                    } else {
                        isFinish++;
                        handler.sendEmptyMessage(MSG_MAIN);
                    }
                    break;
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

    private static final int MSG_MESSAGE = 1;
    private static final int MSG_MAIN = 2;
    private static final int MSG_UPDATE = 3;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ToolUtil.hidePopLoading();
            switch (msg.what) {
                case MSG_MESSAGE:
                    ToastUtil.showToast(msg.obj.toString());
                    break;
                case MSG_MAIN:
                        if (isFinish == 2) {
                            Intent intent = new Intent(FuSplishActivity.this, FuLoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    break;
                case MSG_UPDATE:
                    showDownloadProgressDialog(version.getVersionPath());
                    break;
            }
        }
    };



    private void showDownloadProgressDialog(String path) {
        ProgressDialog progressDialog = new ProgressDialog(FuSplishActivity.this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在升级...");
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        new DownloadAPK(progressDialog).execute(path);
    }

    /**
     * 下载APK的异步任务

     */

    private class DownloadAPK extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;
        File file;

        public DownloadAPK(ProgressDialog progressDialog) {
            this.progressDialog = progressDialog;
        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection conn;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;

            try {
                url = new URL(params[0]);
                String name = params[0];
                name =name.substring(name.lastIndexOf("/")+1);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                int fileLength = conn.getContentLength();
                bis = new BufferedInputStream(conn.getInputStream());
                String path = Environment.getExternalStorageDirectory().getPath() + "/zhongtuo/";
                File dir = new File(path);
                if(!dir.exists()){
                    dir.mkdirs();
                }

                String fileName = Environment.getExternalStorageDirectory().getPath() + "/zhongtuo/"+name;
                file = new File(fileName);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                byte data[] = new byte[4 * 1024];
                long total = 0;
                int count;
                while ((count = bis.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    fos.write(data, 0, count);
                    fos.flush();
                }
                fos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            openFile(file);
            progressDialog.dismiss();
        }

        private void openFile(File file) {
            if (file!=null){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                FuSplishActivity.this.startActivity(intent);
            }

        }
    }
}
