package com.zlin.property.db.po;

import java.util.Date;
import java.util.List;

public class Repair extends Entry {
    private String repairsId;
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 信息
     */
    private String message;
    /**
     * 方便维修时间
     */
    private Date beginTime;
    /**
     * 方便维修结束时间
     */
    private Date endTime;

    private Date beginDate;

    private Date endDate;
    /**
     * 图片列表
     */
    private String photos;
    private List<Photo> PhotoList;
    /**
     * 状态
     */
    private String state;
    /**
     * 工作人
     */
    private String workerId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 评价
     */
    private String evaluate;

    /**
     * 星级
     */

    private String stars;

    /**
     * 服务类型
     */
    private String type;

    /**

     */
    private int pageSize = 5;
    private int pageIndex = 1;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRepairsId() {
        return repairsId;
    }

    public void setRepairsId(String repairsId) {
        this.repairsId = repairsId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Photo> getPhotoList() {
        return PhotoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        PhotoList = photoList;
    }
}
