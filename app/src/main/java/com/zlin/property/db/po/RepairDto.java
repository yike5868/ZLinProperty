package com.zlin.property.db.po;

import java.util.Date;

public class RepairDto {
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
    private Date beaginTime;
    /**
     * 方便维修结束时间
     */
    private Date endTime;
    /**
     * 图片列表
     */
    private String photos;
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
     *  服务类型
     */
    private String type;

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

    public Date getBeaginTime() {
        return beaginTime;
    }

    public void setBeaginTime(Date beaginTime) {
        this.beaginTime = beaginTime;
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
}
