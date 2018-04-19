package com.zlin.property.net;


import com.zlin.property.control.FuResponse;

public interface NetCallBack {

	void cancel(int taskId);

	void loadData(int taskId, FuResponse rspObj);

	void netError(int taskId, String msg);
}
