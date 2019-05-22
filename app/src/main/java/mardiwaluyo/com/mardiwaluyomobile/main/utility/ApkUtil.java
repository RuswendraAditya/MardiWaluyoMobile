package mardiwaluyo.com.mardiwaluyomobile.main.utility;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Wendra on 11/2/2017.
 */

public class ApkUtil {

    public String getAppVersion(Context context) {

        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        String myVersionName = "-"; // initialize String

        try {
            myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return myVersionName;
    }
}
