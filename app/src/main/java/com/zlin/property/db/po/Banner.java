package com.zlin.property.db.po;

public class Banner extends Entry{
    private String bannerId;
    private String bannerMessage;
    private String bannerUrl;
    /**
     * 需要跳转到那个页面
     */
    private String bannerJumpUrl;

    private String version;

    public String getBannerJumpUrl() {
        return bannerJumpUrl;
    }

    public void setBannerJumpUrl(String bannerJumpUrl) {
        this.bannerJumpUrl = bannerJumpUrl;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
