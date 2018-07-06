package com.zlin.property.function;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.zlin.property.R;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuUiFrameModel;
import com.zlin.property.db.po.Repair;
import com.zlin.property.view.FuListView;
import com.zlin.property.view.FuTextView;

import java.util.List;

/**
 * Created by zhanglin on 2018/4/24.
 */

public class FuServerListView extends FuUiFrameModel implements View.OnClickListener{

    FuListView lv_server;
    List<Repair> repairList;

    public FuServerListView(Context cxt, FuEventCallBack callBack) {
        super(cxt, callBack);
    }

    @Override
    protected void createFuLayout() {
        mFuView = LayoutInflater.from(mContext).inflate(
                R.layout.fu_server_list_view, null);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initFuData() {

    }

    @Override
    protected void initWidget() {
        lv_server = (FuListView)mFuView.findViewById(R.id.lv_server);
    }

    public List<Repair> getRepairList() {
        return repairList;
    }

    public void setRepairList(List<Repair> repairList) {
        this.repairList = repairList;
    }

    class RepairAdapter extends BaseAdapter{
        private LayoutInflater mInflater;

        public RepairAdapter() {
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.server_list_item, null);
                holder = new Holder();
                holder.tv_num = (FuTextView) convertView.findViewById(R.id.tv_num);
                holder.tv_body = (FuTextView) convertView.findViewById(R.id.tv_body);
                holder.tv_time = (FuTextView) convertView.findViewById(R.id.tv_time);
                holder.tv_state = (FuTextView) convertView.findViewById(R.id.tv_state);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.tv_num.setText(position);
            holder.tv_body.setText(repairList.get(position).getMessage());
            holder.tv_time.setText(repairList.get(position).getBeginTime().toString()+repairList.get(position).getEndTime().toString());
            holder.tv_state.setText(repairList.get(position).getState());

            return convertView;
        }

        class Holder {

            FuTextView tv_num;
            FuTextView tv_body;
            FuTextView tv_time;
            FuTextView tv_state;
        }
    }
}
