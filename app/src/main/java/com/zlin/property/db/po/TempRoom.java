package com.zlin.property.db.po;

/**
 * Created by zhanglin03 on 2018/4/27.
 */

public class TempRoom {
    private String microdistrictId;
    private String microdistrictName;
    private String buildingId;
    private String buildingName;
    private String unitId;
    private String unitName;
    private String roomId;
    private String roomName;

    public String getMicrodistrictName() {
        return microdistrictName;
    }

    public void setMicrodistrictName(String microdistrictName) {
        this.microdistrictName = microdistrictName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getMicrodistrictId() {
        return microdistrictId;
    }

    public void setMicrodistrictId(String microdistrictId) {
        this.microdistrictId = microdistrictId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
