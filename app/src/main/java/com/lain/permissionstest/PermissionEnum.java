package com.lain.permissionstest;

import android.util.Log;


// 本枚举类列举需要进行代码申请的运行时权限
public enum PermissionEnum {

    CAMERA("android.permission.CAMERA", "相机", 0),
    CALL_PHONE("android.permission.CALL_PHONE", "电话", 1),
    ACCESS_FINE_LOCATION("android.permission.ACCESS_COARSE_LOCATION", "原生GPS定位", 2),
    ACCESS_WIFI_STATE("android.permission.ACCESS_WIFI_STATE", "查看WIFI状态", 3),
    CHANGE_WIFI_STATE("android.permission.CHANGE_WIFI_STATE", "改变WIFI状态", 4),
    ACCESS_NETWORK_STATE("android.permission.ACCESS_NETWORK_STATE", "查看网络状态", 5),
    ;


    private String permission;
    private String chineseName;
    private Integer code;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    PermissionEnum(String permission, String chineseName, Integer code) {
        this.permission = permission;
        this.chineseName = chineseName;
        this.code = code;
    }

    // 返回权限的中文名
    public static String getPermissionChineseName(String ...permissionStr) {
        for (PermissionEnum value : values()) {
            for (String permission : permissionStr) {
                if (permission.equals(value.getPermission())) {
                    return value.getChineseName();
                }
            }
        }
        return null;
    }


}