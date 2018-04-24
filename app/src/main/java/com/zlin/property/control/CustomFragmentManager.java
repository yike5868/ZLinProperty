package com.zlin.property.control;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.zlin.property.FuApp;
import com.zlin.property.function.FuContentFragment;
import com.zlin.property.function.FuMainFragment;
import com.zlin.property.function.FuServerFragment;
import com.zlin.property.function.FuServerListFragment;
import com.zlin.property.function.FuWelcomeFragment;

/**
 *
 *
 */
public class CustomFragmentManager {

    public static final int INIT_NAV = 0;

    public static final int LOGIN_REGIST = 1;

    public static final int MAIN = 2;

    public static final int CONTENT = 3;


    private FragmentManager mManager;

    private FuFmArray[] mFuFmArray;

    public static CustomFragmentManager getInstance(Context context) {

        FuApp app = (FuApp) ((Activity) context).getApplication();

        return app.getFragmentManager();
    }

    private int mCurrentNameId;

    public void initFragmentManager(int nameId, FragmentManager manager) {

        mManager = manager;

        mCurrentNameId = nameId;
    }

    public void ExitFreeResource() {

        mFuFmArray = null;
    }

    /**
     * @param fragementID
     * @param action
     */
    public void addFragment(int fragementID, int action) {

        if (mManager == null) {
            throw new IllegalStateException("Please init FragmentManager");
        }

        Log.i("action---", "==" + action);

        Fragment lCurentFragment = createFragment(action, null);

        if (lCurentFragment == null) {
            return;
        }

        ActionFragment lActionObj = new ActionFragment();
        lActionObj.setmEventAction(action);
        lActionObj.setmFragment(lCurentFragment);
        addFragment(lActionObj);

        mCurrentAction = action;

        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.add(fragementID, lCurentFragment);
        transaction.commit();
    }

    private Fragment createNewFragment(int action, Bundle bundle) {

        switch (action) {
            case FuUiFrameManager.FU_WELCOME:
                return new FuWelcomeFragment();
            case FuUiFrameManager.FU_CONTENT:
                return new FuContentFragment();
            case FuUiFrameManager.FU_MAIN_HOME:
                return new FuMainFragment();
            case FuUiFrameManager.FU_SERVER:
                return new FuServerFragment();
            case FuUiFrameManager.FU_SERVER_LIST:
                return new FuServerListFragment();
        }

        return null; // 默认进入初始化页面
    }

    private Fragment createFragment(int action, Bundle bundle) {

        if (mFuFmArray == null) {

            return createNewFragment(action, bundle);

        } else {

            boolean isFond = false;

            int index = 0;

            for (; index < mFuFmArray.length; index++) {

                int lIdName = mFuFmArray[index].getmManagerNameId();

                if (lIdName == mCurrentNameId) {
                    isFond = true;
                    break;
                }
            }

            if (!isFond) {
                return createNewFragment(action, bundle);
            }

            ActionFragment[] lFragments = mFuFmArray[index].getmActionArray();

            if (lFragments == null) {

                return createNewFragment(action, bundle);
            }

            boolean isFondFragment = false;

            int i = 0;

            for (; i < lFragments.length; i++) {

                ActionFragment lFragment = lFragments[i];

                if (lFragment.getmEventAction() == action) {
                    isFondFragment = true;
                    break;
                }

            }

            if (!isFondFragment) {

                return createNewFragment(action, bundle);
            }

            Fragment lCurrentLocus = lFragments[i].getmFragment();

            if (bundle != null) {

                lCurrentLocus.setArguments(bundle);
            }

            return lCurrentLocus;
        }

    }

    public int CurrentAction() {

        return mCurrentAction;
    }

    private Fragment hasRunningLoaders(int action) {

        if (mFuFmArray == null) {

            return null;
        }

        boolean isFond = false;

        int index = 0;

        for (; index < mFuFmArray.length; index++) {

            int lIdName = mFuFmArray[index].getmManagerNameId();

            if (lIdName == mCurrentNameId) {
                isFond = true;
                break;
            }
        }

        if (!isFond) {
            return null;
        }

        isFond = false;

        ActionFragment[] lFragments = mFuFmArray[index].getmActionArray();

        int i = 0;

        for (; i < lFragments.length; i++) {

            ActionFragment lFragment = lFragments[i];

            if (lFragment.getmEventAction() == action) {
                isFond = true;
                break;
            }

        }

        if (!isFond) {
            return null;
        }

        return lFragments[i].getmFragment();

    }

    /**
     * @param fragementID
     * @param eventAction
     * @param bundle
     */

    public void replaceFragment(int fragementID, int eventAction, Bundle bundle) {

        if (mManager == null) {
            throw new IllegalStateException("Please init FragmentManager");
        }

        FragmentTransaction transaction = mManager.beginTransaction();

        Fragment lCurentFragment = hasRunningLoaders(eventAction);

        boolean isFond = false;

        int index = 0;

        for (; index < mFuFmArray.length; index++) {

            int lIdName = mFuFmArray[index].getmManagerNameId();

            if (lIdName == mCurrentNameId) {
                isFond = true;
                break;
            }
        }

        if (!isFond) {
            return;
        }

        ActionFragment[] lFragments = mFuFmArray[index].getmActionArray();

        if (lCurentFragment == null) { // 没有add过或者remove掉了

            lCurentFragment = createNewFragment(eventAction, bundle);

            transaction.hide(lFragments[lFragments.length - 1].getmFragment())
                    .add(fragementID, lCurentFragment).commit();

            ActionFragment lActionObj = new ActionFragment();
            lActionObj.setmEventAction(eventAction);
            lActionObj.setmFragment(lCurentFragment);
            addFragment(lActionObj);

        } else {

            transaction.hide(lFragments[lFragments.length - 1].getmFragment())
                    .show(lCurentFragment).commit();

            // ----排序，始终保证队列顶端为显示的当前Fragment
            isFond = false;

            int i = 0;

            for (; i < lFragments.length; i++) {

                ActionFragment lFragment = lFragments[i];

                if (lFragment.getmEventAction() == eventAction) {
                    isFond = true;
                    break;
                }

            }

            // -----------------排序，始终保持当前显示页在队列最前端

            ActionFragment[] lTemp = new ActionFragment[lFragments.length];

            if (i == 0) {

                System.arraycopy(lFragments, 1, lTemp, 0, lFragments.length - 1);
                lTemp[lTemp.length - 1] = lFragments[0];

            } else {

                System.arraycopy(lFragments, 0, lTemp, 0, i);
                System.arraycopy(lFragments, i + 1, lTemp, i, lFragments.length
                        - 1 - i);

                lTemp[lTemp.length - 1] = lFragments[i];
            }

            mFuFmArray[index].setmActionArray(lTemp);
            lTemp = null;
        }

        mCurrentAction = eventAction;
    }

    /**
     * 添加当前fragment
     *
     * @param fragment
     */
    private void addFragment(ActionFragment fragment) {

        if (fragment == null) {
            return;
        }

        if (mFuFmArray == null) {

            FuFmArray lArray = new FuFmArray();
            lArray.setmManagerNameId(mCurrentNameId);
            lArray.setmActionArray(fragment);

            mFuFmArray = new FuFmArray[1];

            mFuFmArray[0] = lArray;

        } else {

            boolean isFond = false;

            int index = 0;

            for (; index < mFuFmArray.length; index++) {

                int lIdName = mFuFmArray[index].getmManagerNameId();

                if (lIdName == mCurrentNameId) {
                    isFond = true;
                    break;
                }
            }

            if (isFond) {

                mFuFmArray[index].setmActionArray(fragment);

            } else {

                FuFmArray lArray = new FuFmArray();
                lArray.setmManagerNameId(mCurrentNameId);
                lArray.setmActionArray(fragment);

                FuFmArray[] lTemp = new FuFmArray[mFuFmArray.length + 1];

                System.arraycopy(mFuFmArray, 0, lTemp, 0, mFuFmArray.length);

                mFuFmArray = lTemp;

                lTemp = null;

                mFuFmArray[mFuFmArray.length - 1] = lArray;

            }
        }
    }

    /**
     * 删除指定模块的所有页面Fragment
     */
    public void deleteAllFragment(int lNameId) {

        if (mFuFmArray == null) {
            return;
        }

        boolean isFond = false;

        int index = 0;

        for (; index < mFuFmArray.length; index++) {

            int lIdName = mFuFmArray[index].getmManagerNameId();

            if (lIdName == mCurrentNameId) {
                isFond = true;
                break;
            }
        }

        if (isFond) {

            ActionFragment[] lTemp = mFuFmArray[index].getmActionArray();

            if (lTemp == null) {
                return;
            }

            FragmentTransaction transaction = mManager.beginTransaction();

            for (int i = 0; i < lTemp.length; i++) {

                Fragment lCurFragemt = lTemp[lTemp.length - 1].getmFragment();

                if (lCurFragemt == null) {
                    continue;
                }

                transaction.remove(lCurFragemt);

                transaction.commit();
            }

            lTemp = null;
            mFuFmArray[index].setmActionArray(lTemp);

        }
    }

    /**
     * 出栈操作，指定模块
     *
     * @param lNameId
     */
    private void popFragment(int lNameId) {

        if (mFuFmArray == null) {
            return;
        }

        boolean isFond = false;

        int index = 0;

        for (; index < mFuFmArray.length; index++) {

            int lIdName = mFuFmArray[index].getmManagerNameId();

            if (lIdName == lNameId) {
                isFond = true;
                break;
            }
        }

        if (isFond) {

            ActionFragment[] lFragments = mFuFmArray[index].getmActionArray();

            if (lFragments.length - 1 < 1) {

                lFragments = null;

                mFuFmArray[index].setmActionArray(lFragments);

                return;
            }

            ActionFragment[] lTemp = new ActionFragment[lFragments.length - 1];
            System.arraycopy(lFragments, 0, lTemp, 0, lTemp.length);
            lFragments = lTemp;
            lTemp = null;

            mFuFmArray[index].setmActionArray(lFragments);

        }

    }


    private int mCurrentAction;

    public int mcurrentAction() {

        return mCurrentAction;
    }

    public Fragment mCurrentFragment(int lNameId) {

        if (mFuFmArray == null) {
            return null;
        }

        boolean isFond = false;

        int index = 0;

        for (; index < mFuFmArray.length; index++) {

            int lIdName = mFuFmArray[index].getmManagerNameId();

            if (lIdName == mCurrentNameId) {
                isFond = true;
                break;
            }
        }

        if (!isFond) {
            return null;
        }

        ActionFragment[] lFragments = mFuFmArray[index].getmActionArray();

        return lFragments[lFragments.length - 1].getmFragment();
    }

    /**
     * 清除指定lNameId 当前fragementID 之前的所有该条件下的轨迹
     *
     * @param lNameId
     */
    public void clearBackFragment(int lNameId) {

        if (mFuFmArray == null) {
            return;
        }

        if (mManager == null) {
            throw new IllegalStateException("Please init FragmentManager");
        }

        boolean isFond = false;

        int index = 0;

        for (; index < mFuFmArray.length; index++) {

            int lIdName = mFuFmArray[index].getmManagerNameId();

            if (lIdName == lNameId) {
                isFond = true;
                break;
            }
        }

        if (!isFond) {

            return;
        }

        ActionFragment[] lFragments = mFuFmArray[index].getmActionArray();

        if (lFragments == null) {
            return;
        }

        if (lFragments.length < 2) {
            return;
        }

        FragmentTransaction transaction = mManager.beginTransaction();

        for (int i = 0; i < lFragments.length - 1; i++) {

            transaction.remove(lFragments[i].getmFragment());

        }

        transaction.commit();

        ActionFragment[] lTemp = new ActionFragment[1];
        lTemp[0] = lFragments[lFragments.length - 1];
        lFragments = lTemp;
        lTemp = null;

        mFuFmArray[index].setmActionArray(lFragments);

    }

    /**
     * 清除指定lNameId 当前fragementID和第一个fragment 之间的所有该条件下的轨迹
     *
     * @param lNameId
     */
    public void clearBackFragmentTopAndBottom(int lNameId) {

        if (mFuFmArray == null) {
            return;
        }

        if (mManager == null) {
            throw new IllegalStateException("Please init FragmentManager");
        }

        boolean isFond = false;

        int index = 0;

        for (; index < mFuFmArray.length; index++) {

            int lIdName = mFuFmArray[index].getmManagerNameId();

            if (lIdName == lNameId) {
                isFond = true;
                break;
            }
        }

        if (!isFond) {

            return;
        }

        ActionFragment[] lFragments = mFuFmArray[index].getmActionArray();

        if (lFragments == null) {
            return;
        }

        if (lFragments.length < 3) { // 至少3层，才会出现上下中间删除的可能
            return;
        }

        FragmentTransaction transaction = mManager.beginTransaction();

        for (int i = 1; i < lFragments.length - 1; i++) {

            transaction.remove(lFragments[i].getmFragment());

        }

        transaction.commit();

        ActionFragment[] lTemp = new ActionFragment[2];
        lTemp[0] = lFragments[0];
        lTemp[1] = lFragments[lFragments.length - 1];
        lFragments = lTemp;
        lTemp = null;

        mFuFmArray[index].setmActionArray(lFragments);

    }

    /**
     * 清除该视图下的所有frament
     *
     * @param lNameId
     */
    public void clearBackFragmentAll(int lNameId) {

        if (mFuFmArray == null) {
            return;
        }

        if (mManager == null) {
            throw new IllegalStateException("Please init FragmentManager");
        }

        boolean isFond = false;

        int index = 0;

        for (; index < mFuFmArray.length; index++) {

            int lIdName = mFuFmArray[index].getmManagerNameId();

            if (lIdName == lNameId) {
                isFond = true;
                break;
            }
        }

        if (!isFond) {

            return;
        }

        // mFuFmArray[index]=null;

        ActionFragment[] lFragments = mFuFmArray[index].getmActionArray();

        if (lFragments == null) {
            return;
        }

        FragmentTransaction transaction = mManager.beginTransaction();

        for (int i = 0; i < lFragments.length; i++) {

            transaction.remove(lFragments[i].getmFragment());

            popFragment(lNameId);
        }

        transaction.commit();
    }

    /**
     * 返回上一次访问的页面
     *
     * @param fragementID
     * @return
     */
    public boolean gotoBackFragment(int lNameId, int fragementID) {

        if (mFuFmArray == null) {
            return false;
        }

        if (mManager == null) {
            throw new IllegalStateException("Please init FragmentManager");
        }

        boolean isFond = false;

        int index = 0;

        for (; index < mFuFmArray.length; index++) {

            int lIdName = mFuFmArray[index].getmManagerNameId();

            if (lIdName == lNameId) {
                isFond = true;
                break;
            }
        }

        if (!isFond) {

            return false;
        }

        ActionFragment[] lFragments = mFuFmArray[index].getmActionArray();

        int lCurrentAction = lFragments[lFragments.length - 1]
                .getmEventAction();

        if (lCurrentAction == FuUiFrameManager.FU_MAIN_HOME
                || lCurrentAction == FuUiFrameManager.FU_LOGIN) { // 首页或登录页面点击返回，清除所有的Fragment,直接退出程序

            clearBackFragmentAll(MAIN);
            clearBackFragmentAll(LOGIN_REGIST);
            clearBackFragmentAll(CONTENT);

            return false;
        }

        if (lFragments.length < 2) { // 查找当前视图的上一级视图的归属activity 的Action

            popFragment(lNameId);

            ActionFragment[] lFragmentsPre = null;

            if (index < 1) {

                lFragmentsPre = mFuFmArray[0].getmActionArray();

            } else {

                lFragmentsPre = mFuFmArray[index - 1].getmActionArray();
            }

            if (lFragmentsPre == null || lFragmentsPre.length < 1) {
                return false;
            }

            mCurrentAction = lFragmentsPre[lFragmentsPre.length - 1]
                    .getmEventAction();

            return false;
        }

        FragmentTransaction transaction = mManager.beginTransaction();

        transaction.remove(lFragments[lFragments.length - 1].getmFragment());

        popFragment(lNameId);

        lFragments = mFuFmArray[index].getmActionArray();

        transaction.show(lFragments[lFragments.length - 1].getmFragment());

        transaction.commit();

        mCurrentAction = lFragments[lFragments.length - 1].getmEventAction();

        return true;
    }
}
