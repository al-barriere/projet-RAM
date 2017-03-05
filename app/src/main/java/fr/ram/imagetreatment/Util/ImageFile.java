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
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.ram.imagetreatment.Activities.ImageTreatmentActivity;
import fr.ram.imagetreatment.R;

public class ImageFile {
    public static File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory("Ramstagram");
        if (!storageDir.exists())
            storageDir.mkdirs();
        String filename = imageFileName + ".jpg";

        return new File(storageDir, filename);
    }

    public static File createTempImageFile(Activity activity) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

    public static void saveImage(final Activity activity, Bitmap image) {
        if (PermissionUtil.getWritePermission(activity) != PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(activity.findViewById(R.id.activity_main), R.string.storage_permissions_not_granted, Snackbar.LENGTH_LONG).setAction(R.string.open_permissions, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPermissionsIntent(activity);
                }
            }).show();
        }
        else {
            File pictureFile = null;
            try {
                pictureFile = ImageFile.createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d("FileNotFoundException", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("IOException", "Error accessing file: " + e.getMessage());
            } catch (NullPointerException e) {
                Log.d("NullPointerException", "Error accessing file: " + e.getMessage());
            } finally {
                if (pictureFile != null) {
                    ((ImageTreatmentActivity) activity).getImageView().setImageModified(false);
                    Snackbar.make(activity.findViewById(R.id.activity_main), R.string.image_saved, Snackbar.LENGTH_SHORT).show();

                    MediaScannerConnection.scanFile(activity,
                            new String[]{pictureFile.toString()}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> uri=" + uri);
                                }
                            });
                }
            }
        }
    }

    private static void openPermissionsIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }
}

