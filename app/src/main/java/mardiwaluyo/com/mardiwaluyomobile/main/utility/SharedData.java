package mardiwaluyo.com.mardiwaluyomobile.main.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Wendra on 10/17/2017.
 */

public class SharedData {
    private static final String MyPREFERENCES = "BethesdaPrefs";
    private static SharedPreferences sharedpreferences;

    public static String getKey(Context c, String key) {
        sharedpreferences = c.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "");
    }

    public static void setKey(Context c, String key, String value) {
        sharedpreferences = c.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void clearData(Context c) {
        sharedpreferences = c.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }


    public static void removeData(Context c,String key) {
        sharedpreferences = c.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}

