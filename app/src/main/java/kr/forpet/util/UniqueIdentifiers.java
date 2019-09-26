package kr.forpet.util;

import android.content.Context;
import android.provider.Settings;

public class UniqueIdentifiers {

    public static String getSettingSecureAndroidId(Context context) {
        // https://developer.android.com/training/articles/user-data-ids#java
        // https://healingpaper.github.io/android/2019/07/01/privacy-changes-in-android-q-2.html
        String SSAID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return (SSAID != null) ? SSAID : "";
    }
}
