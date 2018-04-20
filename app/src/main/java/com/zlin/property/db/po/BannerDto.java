package com.zlin.property.db.po;

import java.util.ArrayList;

/**
 * Created by zhanglin03 on 2018/4/19.
 */

public class BannerDto extends Dto {

    private ArrayList<Banner> data;

    public ArrayList<Banner> getData() {
        return data;
    }

    public void setData(ArrayList<Banner> data) {
        this.data = data;
    }

}
