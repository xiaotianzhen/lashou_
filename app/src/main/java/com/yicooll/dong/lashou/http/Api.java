package com.yicooll.dong.lashou.http;

/**
 * Created by 45990 on 2017/9/6.
 */

public class Api {

    /**
     * 手机测试
     */
    public static final String Mobile_HOST="http://192.168.0.105:8080/ls_server";
    public static final String HOST="http://192.168.6.2:8080/ls_server";
    public static final String CITY_LIST=Mobile_HOST+"/api/city";
    public static final String GOODS_LIST=Mobile_HOST+"/api/goods";
    public static final String NEARBY_GODDS=Mobile_HOST+"/api/nearby";
    public static final String REIGISTER=Mobile_HOST+"/api/user?flag=register";
    public static final String LOGIN=Mobile_HOST+"/api/user?flag=login";
    public static final String SOCIAL=Mobile_HOST+"/api/user?flag=social";

}
