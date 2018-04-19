package com.zlin.property.db.po;

import java.util.ArrayList;

/**
 * Created by zhanglin03 on 2018/4/19.
 */

public class BannerDto extends Dto {

    private ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        private String bannerMessage;
        private String bannerUrl;

        public String getBannerMessage() {
            return bannerMessage;
        }

        public void setBannerMessage(String bannerMessage) {
            this.bannerMessage = bannerMessage;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }
    }
}
