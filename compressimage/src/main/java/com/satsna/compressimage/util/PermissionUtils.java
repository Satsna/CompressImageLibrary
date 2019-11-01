package com.satsna.compressimage.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/29
 *     desc  : utils about permission
 * </pre>
 */
public final class PermissionUtils {
    /**
     * Return whether <em>you</em> have been granted the permissions.
     *
     * @param permissions The permissions.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isGranted(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!isGranted(context, permission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isGranted(Context context, String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || PackageManager.PERMISSION_GRANTED
                == ContextCompat.checkSelfPermission(context, permission);
    }


}