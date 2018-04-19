// Copyright (C) 2012-2013 UUZZ All rights reserved
package com.zlin.property.net;

import android.content.Context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class NetManager {

	private static NetManager mManager;

	private static Context mContext;

	private HashMap<MyTask, NetTaskThread> mThreadTable; // 任务线程对应 线程

	private MyTask[] mTasks; // 联网任务列表

	private static synchronized void syncInit(Context context) {

		if (mManager == null) {
			mManager = new NetManager();
		}

		mContext = context;
	}

	public static NetManager getInstance(Context context) {

		if (mManager == null) {
			syncInit(context);
		}

		return mManager;
	}

	/**
	 * 添加联网任务到当前任务列表
	 * 
	 * @param task
	 *            待添加的任务
	 * 
	 * @return true则表示添加成功,否则失败
	 */
	public boolean addNetTask(MyTask task) {
		if (task == null) {
			return false;
		}

		if (mTasks == null) {
			mTasks = new MyTask[1];
		} else {
			MyTask[] temp = new MyTask[mTasks.length + 1];
			System.arraycopy(mTasks, 0, temp, 0, mTasks.length);
			mTasks = temp;
			temp = null;
		}

		mTasks[mTasks.length - 1] = task;

		return true;
	}

	/**
	 * 从任务列表中删除指定的任务
	 * 
	 * @param task
	 *            指定的任务
	 * @return
	 */
	public boolean deleteNetTask(MyTask task) {
		if (task == null) {
			return false;
		}

		if (mTasks == null || mTasks.length == 0) {
			return false;
		}
		boolean isFond = false;
		int i = 0;
		int count = mTasks.length;
		for (; i < count; i++) {

			MyTask lTask = mTasks[i];

			if (task.equals(lTask)) {
				isFond = true;
				break;
			}
		}

		if (!isFond) {
			return false;
		}

		if (count == 1) {
			mTasks = null;
		} else {

			MyTask[] temp = new MyTask[count - 1];
			if (i == 0) {
				System.arraycopy(mTasks, 1, temp, 0, temp.length);
			} else if (i == count - 1) {
				System.arraycopy(mTasks, 0, temp, 0, temp.length);
			} else {
				System.arraycopy(mTasks, 0, temp, 0, i);
				System.arraycopy(mTasks, i + 1, temp, i, temp.length - i);
			}

			mTasks = temp;
			temp = null;
		}

		return true;
	}

	/**
	 * 从任务列表中删除指定的任务
	 * 
	 * @param id
	 *            任务的ID
	 * @return
	 */
	public boolean deleteNetTask(int id) {
		if (mTasks == null || mTasks.length == 0) {
			return false;
		}

		boolean isFond = false;
		int i = 0;
		int count = mTasks.length;
		for (; i < count; i++) {

			int lcurTag = mTasks[i].mTaskId;

			if (lcurTag == id) {
				isFond = true;
				break;
			}
		}

		if (!isFond) {
			return false;
		}

		if (count == 1) {
			mTasks = null;
		} else {

			MyTask[] temp = new MyTask[count - 1];
			if (i == 0) {
				System.arraycopy(mTasks, 1, temp, 0, temp.length);
			} else if (i == count - 1) {
				System.arraycopy(mTasks, 0, temp, 0, temp.length);
			} else {
				System.arraycopy(mTasks, 0, temp, 0, i);
				System.arraycopy(mTasks, i + 1, temp, i, temp.length - i);
			}

			mTasks = temp;
			temp = null;
		}

		return true;
	}

	/**
	 * 执行指定的网络任务
	 * 
	 * @param task
	 *            当前待执行的任务
	 */
	@SuppressWarnings("rawtypes")
	public void excuteNetTask(MyTask task) {
		if (task == null) {
			return;
		}

		boolean isAddTask = deleteNetTask(task);

		if (!isAddTask) {
			// 提示用户必须将任务添加至任务列表中才能执行网络任务
			return;
		}

		boolean isFond = false; // 当一个任务没有执行完毕之前,新的任务不会被执行

		NetManager lNetManager = new NetManager(); // 采用影子实例的方法来实现同步

		if (mThreadTable != null) {

			lNetManager.mThreadTable = mThreadTable;

			Set set = lNetManager.mThreadTable.keySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {

				MyTask lTask = (MyTask) iterator.next();

				if (task.mTaskId == lTask.mTaskId) {
					isFond = true;
					break;
				}
			}
		}

		if (isFond) {
			return;
		}

		NetTaskThread thread = new NetTaskThread(mContext, task,
				new netOverCallBack());

		thread.start();

		if (lNetManager.mThreadTable == null) {
			lNetManager.mThreadTable = new HashMap<MyTask, NetTaskThread>();
		}

		lNetManager.mThreadTable.put(task, thread);

		mThreadTable = lNetManager.mThreadTable;
	}

	/**
			 * 线程结束或主动取消任务的回调
			 * 
			 * @author wangzehua
			 * 
			 */
	interface netOverOrCancel {

		public void NetThreadOver(int id);
	}

	class netOverCallBack implements netOverOrCancel {

		@SuppressWarnings("rawtypes")
		@Override
		public void NetThreadOver(int id) {

			if (mThreadTable == null) {
				return;
			}

			NetManager lNetManager = new NetManager(); // 采用影子实例的方法来实现同步
			lNetManager.mThreadTable = mThreadTable;

			while (true) {

				int lCount = lNetManager.mThreadTable.size();

				if (lCount < 1) {
					break;
				}

				Set set = lNetManager.mThreadTable.keySet();

				Iterator iterator = set.iterator();

				int lSize = set.size();

				boolean lDelete = false;

				for (int i = 0; i < lSize; i++) {

					if (!iterator.hasNext()) {
						break;
					}

					MyTask key = (MyTask) iterator.next();

					int lName = key.mTaskId;

					if (lName != id) {
						continue;
					}

					lDelete = true;

					lNetManager.mThreadTable.remove(key);
					break;
				}

				if (!lDelete) {
					break;
				}
			}

			mThreadTable = lNetManager.mThreadTable;
		}
	}

	/**
	 * 执行任务列表中的�?��任务
	 */
	public void excuteNetTask() {

	}

	@SuppressWarnings("rawtypes")
	public void cnacelAllNetTask() {

		if (mThreadTable == null) {
			return;
		}

		NetManager lNetManager = new NetManager(); // 采用影子实例的方法来实现同步
		lNetManager.mThreadTable = mThreadTable;
		

		while (true) {

			Set set = lNetManager.mThreadTable.keySet();

			int lSzie = set.size();

			if (lSzie < 1) {
				break;
			}

			Iterator iterator = set.iterator();

			if (!iterator.hasNext()) {
				break;
			}

			MyTask key = (MyTask) iterator.next();

			NetTaskThread thread = lNetManager.mThreadTable.get(key);
			

			if (thread != null) {
				thread.cancel();
				lNetManager.mThreadTable.remove(key);
			}
		}

		mThreadTable = lNetManager.mThreadTable;

	}

	/**
	 * 结束指定的网络任务
	 * 
	 * @param task
	 *            结束指定的任务
	 */
	@SuppressWarnings("rawtypes")
	public void cancelNetTask(MyTask task) {

		if (mThreadTable == null) {
			return;
		}

		NetManager lNetManager = new NetManager(); // 采用影子实例的方法来实现同步
		lNetManager.mThreadTable = mThreadTable;

		while (true) {

			Set set = lNetManager.mThreadTable.keySet();

			Iterator iterator = set.iterator();

			int lSize = set.size();

			boolean lDelete = false;

			for (int i = 0; i < lSize; i++) {

				if (!iterator.hasNext()) {
					break;
				}

				MyTask key = (MyTask) iterator.next();

				int lName = key.mTaskId;

				if (lName != task.mTaskId) {
					continue;
				}

				lDelete = true;

				NetTaskThread thread = lNetManager.mThreadTable.get(key);

				if (thread != null) {
					thread.cancel();
					lNetManager.mThreadTable.remove(key);
				}

			}

			if (!lDelete) {
				break;
			}
		}

		mThreadTable = lNetManager.mThreadTable;

	}
}
