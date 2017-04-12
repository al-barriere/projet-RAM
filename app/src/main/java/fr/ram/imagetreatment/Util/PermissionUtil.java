package fr.ram.imagetreatment.Util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Rémi on 01/03/2017.
 */

public class PermissionUtil {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    // The permissions to request
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /***
     * Verify if the app can write on the external storage
     * @param activity The current activity
     * @return PackageManager.PERMISSION_GRANTED if the permission if granteed; PackageManager.PERMISSION_DENIED, if the permission is denied
     */
    public static int getWritePermission(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /***
     * Verify if the app can card on the external storage
     * @param activity The current activity
     * @return PackageManager.PERMISSION_GRANTED if the permission if granteed; PackageManager.PERMISSION_DENIED, if the permission is denied
     */
    public static int getReadPermission(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    /***
     * Verify if the app can write and read on the external storage; if it can't, ask the user for the permissions
     * @param activity The current activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        int writePermission = getWritePermission(activity);
        int readPermission = getReadPermission(activity);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
