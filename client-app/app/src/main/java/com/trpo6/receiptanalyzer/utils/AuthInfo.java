package com.trpo6.receiptanalyzer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Класс для получения, хранения и редактирования
 * информаци об авторизации
 */

public class AuthInfo{
    /** Token string */
    private static String key = "";

    /** Username */
    private static String name = "";

    /** Table key */
    private static String tableKey = "";

    private static final String path = "user_auth";

    public static void setTableKey(String tableKey) {
        AuthInfo.tableKey = tableKey;
    }

    public static String getTableKey() {

        return tableKey;
    }

    public static void setKey(String key) {
        AuthInfo.key = key;
    }

    public static void setName(String name) {
        AuthInfo.name = name;
    }

    public static String getName() {

        return name;
    }

    public static String getKey() {
        return key;
    }

    /**
     * Проверяет, авторизован ли текущий пользователь
     * @param context
     * @return
     */
    public static boolean isAuthorized(Context context) {
        SharedPreferences sp = context.getSharedPreferences(path, Context.MODE_PRIVATE);
        key = sp.getString("token","");
        name=sp.getString("name","");
        if (key.isEmpty() || name.isEmpty())
            return false;
        return true;
    }

    /**
     * Проверяет, подключен ли текущий пользователь к столу
     * @param context
     * @return
     */
    public static boolean isConnectedToTable(Context context) {
        SharedPreferences sp = context.getSharedPreferences(path, Context.MODE_PRIVATE);
        tableKey = sp.getString("key_table","");
        if (tableKey.isEmpty())
            return false;
        Log.i("connect","true");
        return true;
    }

    /** Сохранение данных об авторизации */
    public static void authSave(Context context, String name, String token) {
        SharedPreferences sp =  context.getSharedPreferences(path,Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("token", token);
        e.putString("name",name);
        setKey(token);
        setName(name);
        e.commit();
    }

    /** Удаление данных об авторизации */
    public static void authClear(Context context) {
        // стираем старый токен
        SharedPreferences sp = context.getSharedPreferences(path, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("token","");
        e.putString("name","");
        e.putString("key_table","");
        e.commit();
    }

    /** Сохранение данных о подключении к столу */
    public static void keyTableSave(Context context, String key){
        SharedPreferences sp =  context.getSharedPreferences(path,Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("key_table", key);
        e.commit();
        setTableKey(key);
    }

    /** Удаление данных о подключении к столу */
    public static void keyTableClear(Context context){
        SharedPreferences sp =  context.getSharedPreferences(path,Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("key_table", "");
        e.commit();
    }
}