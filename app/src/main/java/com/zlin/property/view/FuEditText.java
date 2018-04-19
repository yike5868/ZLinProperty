package com.zlin.property.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhanglin on 16/8/17.
 */

public class FuEditText extends EditText {

    private int maxByteLength;
    private int int_length;
    private int decimals_length;

    private String encoding = "GBK";

    String namespace = "http://schemas.android.com/apk/res-auto";

    public FuEditText(Context context) {
        super(context);
    }

    public FuEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        String max = attrs.getAttributeValue(namespace, "maxByteLength");
        if (max != null && !"".equals(max)) {

            if (max.indexOf(",") > -1) {
                int_length = Integer.parseInt(max.split(",")[0])-1;
                decimals_length = Integer.parseInt(max.split(",")[1]);
                int_length = int_length-decimals_length;

                initNum();
            } else {
                maxByteLength = Integer.parseInt(max);
                initMax();
            }
        }


    }

    public String getString(){
        return getText().toString();
    }
    public Double getDouble() {

        String str_int = getText().toString().trim();
        if (isNumeric(str_int))
            return Double.parseDouble(str_int);
        else
            return null;
    }
    public Integer getInt() {

      String str_int = getText().toString().trim();
        if (isNumeric(str_int))
            return Integer.parseInt(str_int);
        else
            return null;
    }

    public boolean isNumeric(String str) {
        return str.matches("-?[0-9]+.*[0-9]*");
    }

    /**
     * 设置输入数字监听
     */
    private void initNum() {
        setFilters(new InputFilter[]{inputFilterNum});
    }



    public Date getDate(){
        if(getTag()==null)
            return null;
        else{
            return (Date)getTag();
        }
    }

    @Override
    public Editable getText() {
        return super.getText();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if(text==null)
            text = "";
        super.setText(text, type);
    }

    public void setText(Double d){
        if(d==null){
            super.setText("");
        }else{
            super.setText(d+"");
        }
    }
    public void setText(Integer i){
        if(i==null){
            super.setText("");
        }else{
            super.setText(i+"");
        }
    }
    public void setText(Date i, Date j){
        if(i !=null) {
            String mm = new SimpleDateFormat("yyyyMM").format(i);
            super.setText(mm);
        }
        if(j !=null)
            super.setText(j+"");
    }
    public void setText(Date i){
        if(i !=null) {
            String mm = new SimpleDateFormat("yyyyMM").format(i);
            super.setText(mm);
        }
    }
    public void setText(Integer i, Integer j){
        if(i !=null)
            super.setText(i+"");
        if(j !=null)
            super.setText(j+"");
    }
    public void setText(Double i, Double j){
        if(i !=null)
            super.setText(i+"");
        if(j !=null)
            super.setText(j+"");
    }
    public void setText(Integer i, String j){
        if(i !=null)
            super.setText(i+"");
        if(j !=null)
            super.setText(j);
    }

    public void setText(String i, String j){
        if(i !=null)
            super.setText(i);
        if(j !=null)
            super.setText(j);
    }

    private void initMax() {
        setFilters(new InputFilter[]{inputFilter});
    }

    public int getMaxByteLength() {
        return maxByteLength;
    }

    public void setMaxByteLength(int maxByteLength) {
        this.maxByteLength = maxByteLength;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * input输入过滤数字长度
     */
    private InputFilter inputFilterNum = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {

            try {
                boolean more = false;
                do {

                    if(dest.toString().indexOf(".")>-1&&source.toString().indexOf(".")>-1){
                        more = true;
                    }else if(dest.toString().indexOf(".")>-1){
                        if(dest.toString().split("\\.").length>1&&dest.toString().split("\\.")[1].length()>=decimals_length)
                            more = true;
                    }else{
                        if(dest.toString().length() > int_length){
                            more = true;
                        }
                        if(dest.toString().indexOf(".")<0&&source.toString().indexOf(".")>-1){
                            more = false;
                        }
                    }
                    if(decimals_length==0&&source.toString().indexOf(".")>-1){
                        more = true;
                    }
                    if (more) {
                        end--;
                        source = source.subSequence(start, end);
                    }
                } while (more);
                return source;
            } catch (Exception e) {
                return "Exception";
            }

        }
    };

    /**
     * input输入过滤字节长度
     */
    private InputFilter inputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            try {
                int len = 0;
                boolean more = false;
                do {
                    SpannableStringBuilder builder =
                            new SpannableStringBuilder(dest).replace(dstart, dend, source.subSequence(start, end));
                    len = builder.toString().getBytes(encoding).length;
                    more = len > maxByteLength;
                    if (more) {
                        end--;
                        source = source.subSequence(start, end);
                    }
                } while (more);
                return source;
            } catch (UnsupportedEncodingException e) {
                return "Exception";
            }
        }
    };
}
