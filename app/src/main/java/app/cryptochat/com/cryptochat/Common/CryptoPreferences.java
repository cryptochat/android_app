package app.cryptochat.com.cryptochat.Common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.prefs.Preferences;

/**
 * Created by romankov on 01.04.17.
 */

public class CryptoPreferences {
    private static CryptoPreferences _instance;
    private SharedPreferences _prefs;


    public static void init(Context context){
        _instance = new CryptoPreferences(context);
    }

    private CryptoPreferences(Context context){
        _prefs = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static void save(String key, String value) {
        // Извлеките редактор, чтобы изменить Общие настройки.
        SharedPreferences.Editor editor = _instance._prefs.edit();
        // Запишите новые значения примитивных типов в объект Общих настроек.
        editor.putString(key, value);
        // Сохраните изменения.
        editor.apply();
    }

    public static void remove(String key) {
        SharedPreferences.Editor editor = _instance._prefs.edit();
        editor.remove(key);
        editor.apply();
    }

    public static String get(String key, String def) {
        return _instance._prefs.getString(key, def);
    }


}
