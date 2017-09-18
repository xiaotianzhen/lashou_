package com.yicooll.dong.lashou.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 45990 on 2017/9/5.
 */

public class SpUtil {

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences("dong.yicooll.com",Context.MODE_PRIVATE);
    }

    public static void putBoolean(Context context,String key,boolean value){
        SharedPreferences sp=getSharedPreferences(context);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    public static boolean getBoolean(Context context,String key,boolean value){
        SharedPreferences sp=getSharedPreferences(context);
        return sp.getBoolean(key,value);
    }


    public static void putString(Context context,String key,String value){
        SharedPreferences sp=getSharedPreferences(context);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public static String getString(Context context,String key){
        SharedPreferences sp=getSharedPreferences(context);
        return sp.getString(key,"");
    }

    public static void clearShare(Context context){
        SharedPreferences sp=getSharedPreferences(context);
        SharedPreferences.Editor editor=sp.edit();
        editor.clear();
        editor.commit();
    }

}
