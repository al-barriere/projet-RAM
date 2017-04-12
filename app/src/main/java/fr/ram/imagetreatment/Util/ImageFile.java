package fr.ram.imagetreatment.Util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.ram.imagetreatment.Activities.ImageTreatmentActivity;
import fr.ram.imagetreatment.R;

public class ImageFile {
    /***
     * Generate a new image filename without the file extension
     * @return The filename
     */
    private static String getImageFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return  "JPEG_" + timeStamp + "_";
    }

    /***
     * Create an image (accessible by the user image gallery)
     * @return The created file
     * @throws IOException
     */
    public static File createImageFile() throws IOException {
        String imageFileName = getImageFileName();

        // If the image folder "Ramstagram" does not exist, we create it
        File storageDir = Environment.getExternalStoragePublicDirectory("Ramstagram");
        if (!storageDir.exists())
            storageDir.mkdirs();

        // Add the file extension
        String filename = imageFileName + ".jpg";

        return new File(storageDir, filename);
    }

    /***
     * Create a temporary image (not accessible by the user image gallery)
     * @param activity The current activity
     * @return The created file
     * @throws IOException
     */
    public static File createTempImageFile(FragmentActivity activity) throws IOException {
        String imageFileName = getImageFileName();

        // Create a temporary file in app the Pictures directory
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

    /***
     * Save the image passed in parameter on the external stoage
     * @param activity The current activity
     * @param image The image to save
     * @return
     *      true if the image was successfully created
     *      false if an error happened
     */
    public static boolean saveImage(final FragmentActivity activity, Bitmap image) {
        // If the activity can't write on the external storage
        if (PermissionUtil.getWritePermission(activity) != PackageManager.PERMISSION_GRANTED) {
            // Show a snackbar to alert the user
            Snackbar.make(activity.findViewById(R.id.activity_main), R.string.storage_permissions_not_granted, Snackbar.LENGTH_LONG).setAction(R.string.open_permissions, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPermissionsIntent((Activity) activity);
                }
            }).show();
        }
        // Else, the activity can write on the external storage
        else {
            File pictureFile = null;
            try {
                // Create a new image file
                pictureFile = ImageFile.createImageFile();
                FileOutputStream fos = new FileOutputStream(pictureFile);
                // Save the image without compression
                image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (IOException e) {
                // Show a scnackar to alert the user that a write permission error happened
                Snackbar.make(activity.findViewById(R.id.activity_main), R.string.imagefile_save_ioexception_content, Snackbar.LENGTH_LONG).show();
            } finally {
                // If the image was successfully created
                if (pictureFile != null) {
                    // Set the CustomImageView unmodified in order to avoid showing the leaving activity popup
                    ((ImageTreatmentActivity) activity).getImageView().setImageModified(false);

                    // Alert the user
                    Snackbar.make(activity.findViewById(R.id.activity_main), R.string.image_saved, Snackbar.LENGTH_SHORT).show();

                    // Add the new image to the user image gallery
                    MediaScannerConnection.scanFile(activity,
                            new String[]{pictureFile.toString()}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> uri=" + uri);
                                }
                            });

                    return true;
                }
            }
        }
        return false;
    }

    private static void openPermissionsIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }
}

