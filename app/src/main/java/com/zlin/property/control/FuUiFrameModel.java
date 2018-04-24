package com.zlin.property.control;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;


import com.zlin.property.FuApp;
import com.zlin.property.db.helper.ALocalSqlHelper;
import com.zlin.property.uview.TopMiddlePopup;
import com.zlin.property.view.FuEditText;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class FuUiFrameModel {

    protected Context mContext;

    protected static int PAGE_SIZE = 10;

    protected FuEventCallBack mEventCallBack;

    ALocalSqlHelper sqlHelper;

    private Calendar m_Calendar;        //时间

    private TopMiddlePopup middlePopup;//上面的弹出框

    public FuUiFrameModel(Context cxt, FuEventCallBack callBack) {

        mContext = cxt;
        mEventCallBack = callBack;
        sqlHelper = ((FuApp) mContext.getApplicationContext()).getSqlHelper();
        createFuLayout();
        initWidget();
        initFuData();
    }


    protected View mFuView; // 对应的视图

    protected abstract void createFuLayout(); // 创建布局

    protected abstract void initFuData(); // 初始化基础数据和状态

    protected abstract void initWidget(); //初始化控件


    /**
     * 获取当前视图
     *
     * @return
     */
    public View getFuView() {

        return mFuView;
    }

    /**
     * 二级菜单
     */


    /**
     * @param str
     * @param layout
     */
    public void setText(String str, LinearLayout layout) {
        if (str == null)
            return;
        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof FuEditText) {
                ((FuEditText) v).setText(str);
            } else if (v instanceof LinearLayout) {
                setText(str, (LinearLayout) v);
            }
        }
    }

    public void setText(Integer integer, LinearLayout layout) {
        if (integer == null)
            return;
        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof FuEditText) {
                ((FuEditText) v).setText(integer);
            } else if (v instanceof LinearLayout) {
                setText(integer, (LinearLayout) v);
            }
        }
    }

    public void setDate(View v) {
        final TextView FuTextView = (TextView) v;

        m_Calendar = Calendar.getInstance();
        String o_date = FuTextView.getText().toString();
        if (!"".equals(o_date)) {
            m_Calendar.set(Calendar.YEAR,
                    Integer.parseInt(o_date.split("-")[0]));
            m_Calendar.set(Calendar.MONTH,
                    Integer.parseInt(o_date.split("-")[1]) - 1);
            m_Calendar.set(Calendar.DAY_OF_MONTH,
                    Integer.parseInt(o_date.split("-")[2]));
        }

        DatePickerDialog dialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String t_date = year + "-" + (monthOfYear + 1)
                                + "-" + dayOfMonth;
                        FuTextView.setText(t_date);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = sdf.parse(t_date);
                            FuTextView.setTag(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }, m_Calendar.get(Calendar.YEAR), m_Calendar
                .get(Calendar.MONTH), m_Calendar
                .get(Calendar.DAY_OF_MONTH));
        DatePicker dp = dialog.getDatePicker();

        dp.setMinDate(-5364691200000L);
        dialog.show();
    }

    public Integer getInt(LinearLayout linearLayout) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View v = linearLayout.getChildAt(i);
            if (v instanceof FuEditText) {
                String text = ((FuEditText) v).getText().toString().trim();
                if (isNumeric(text))
                    return Integer.parseInt(text);
            } else if (v instanceof LinearLayout) {
                Integer integer = getInt((LinearLayout) v);
                if (integer != null)
                    return integer;
            }
        }
        return null;
    }

    public String getString(LinearLayout linearLayout) {
        String text = null;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View v = linearLayout.getChildAt(i);
            if (v instanceof FuEditText) {
                text = ((FuEditText) v).getText().toString().trim();
                return text;
            } else if (v instanceof LinearLayout) {
                text = getString((LinearLayout) v);
            }
        }
        return text;
    }

    public String getRadioString(LinearLayout linearLayout) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View v = linearLayout.getChildAt(i);
            if (v instanceof FuEditText) {
                String text = ((FuEditText) v).getText().toString().trim();
                return text;
            } else if (v instanceof RadioButton) {
                if (((RadioButton) v).isChecked())
                    return ((RadioButton) v).getText().toString().trim();
            } else if (v instanceof LinearLayout) {
                return getRadioString((LinearLayout) v);
            }
        }
        return null;
    }

    public boolean isNumeric(String str) {
        return str.matches("-?[0-9]+.*[0-9]*");
    }


    int screenW;
    int screenH;
    /**
     * 设置弹窗
     *
     * @param type
     */
    public void setPopup(View v,int type, AdapterView.OnItemClickListener onItemClickListener, ArrayList<String> listName) {
        getScreenPixels();
        middlePopup = new TopMiddlePopup(mContext, screenW, screenH,
                onItemClickListener, listName, type);
        middlePopup.show(v);
    }

    /**
     * 获取屏幕的宽和高
     */
    public void getScreenPixels() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;
    }

}
