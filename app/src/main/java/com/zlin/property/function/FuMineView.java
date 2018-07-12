package com.zlin.property.function;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xlibs.xrv.LayoutManager.XGridLayoutManager;
import com.xlibs.xrv.LayoutManager.XLinearLayoutManager;
import com.xlibs.xrv.LayoutManager.XStaggeredGridLayoutManager;
import com.xlibs.xrv.listener.OnLoadMoreListener;
import com.xlibs.xrv.listener.OnRefreshListener;
import com.xlibs.xrv.view.XRecyclerView;
import com.youth.banner.listener.OnBannerListener;
import com.zlin.property.R;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuUiFrameModel;

/**
 * Created by zhanglin03 on 2018/7/10.
 */

public class FuMineView extends FuUiFrameModel implements OnBannerListener {
    private XRecyclerView mXRecyclerView;
    BodyAdapter bodyAdapter;

    public FuMineView(Context cxt, FuEventCallBack callBack) {
        super(cxt, callBack);
    }

    @Override
    protected void createFuLayout() {

        mFuView = LayoutInflater.from(mContext).inflate(
                R.layout.fu_mine_view, null);
        mXRecyclerView = (XRecyclerView) mFuView.findViewById(R.id.xr_body);
    }


    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    protected void initFuData() {

    }

    @Override
    protected void initWidget() {

        initXR();
    }

    private void initXR() {
        // 请勿使用系统本身的 LayoutManager ，而是需要使用以下三种 LayoutManager
        XLinearLayoutManager xLinearLayoutManager = new XLinearLayoutManager(mContext);
//        XGridLayoutManager xGridLayoutManager = new XGridLayoutManager(this,2);
//        XStaggeredGridLayoutManager xStaggeredGridLayoutManager =
//                new XStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(xLinearLayoutManager);
// 添加下拉刷新的头部 和 加载更多的底部，如果不加，默认含有下拉刷新的头部，而没有加载更多的底部
//        mHeaderView = LayoutInflater.from(this).inflate(R.layout.custom_header_view, null);
//        mFooterView = LayoutInflater.from(this).inflate(R.layout.footer_view, null);
//        mXRecyclerView.addHeaderView(mHeaderView, 50);
//        mXRecyclerView.addFootView(mFooterView, 50);
// 设置adapter
        bodyAdapter = new BodyAdapter(mContext);

        mXRecyclerView.setAdapter(bodyAdapter);
// 添加下拉刷新
        mXRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
// 加载更多（如果没有添加加载更多的布局，下面那LoadMore不会执行）
        mXRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
            }
        });

    }

    static class BodyAdapter extends RecyclerView.Adapter {
        private Context myContext;
        private LayoutInflater inflater;

        public BodyAdapter(Context context) {
            this.myContext = context;
            inflater = LayoutInflater.from(myContext);
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(View itemView) {
                super(itemView);
//                this.textView=itemView.findViewById(R.id.item_text);
            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fu_mine_item, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return 5;
        }

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mXRecyclerView.destroyHandler();
//    }
}
