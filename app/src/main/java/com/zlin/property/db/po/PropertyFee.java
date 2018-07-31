package com.zlin.property.db.po;

import java.math.BigDecimal;
import java.util.Date;

public class PropertyFee {
    private String id;
    /**
     * 物业费时间
     */
    private Date beginDate;

    private Date endDate;
    /**
     * 缴费 名称
     */

    private String payName;
    /**
     * 缴费时间
     */
    private Date payDate;

    /**
     * 缴费金额
     */
    private BigDecimal payMoney;

    /**
     * 房间id
     */
    private String roomId;
    /**
     * 缴费人id
     */
    private String userId;

    /**
     * 支付状态
     */
    private String payState;

    /**
     * 付钱类型
     */
    private String payType;

    /**
     * 缴费人
     */
    private String userName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
