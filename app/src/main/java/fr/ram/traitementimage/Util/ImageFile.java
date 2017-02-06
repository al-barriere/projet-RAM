package fr.ram.traitementimage.Util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageFile {
    public static File createImageFile(Activity activity) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        return image;
    }

    public static Bitmap rotationImage(Uri photoUri, Bitmap bitmap) throws IOException {
        ExifInterface exif = new ExifInterface(photoUri.getPath());
        int exifOrientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        int rotate = 0;

        switch (exifOrientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
        }

        if (rotate != 0) {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            Matrix mtx = new Matrix();
            mtx.preRotate(rotate);

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            return bitmap;
        }
        return bitmap;
    }

    public static void getPixelsAndHistogram(Bitmap image, int[] pixels, int[][] histogram) {
        int taille = image.getWidth() * image.getHeight();
        int couleur[][] = new int[taille][3];
        int tempCouleur;

        if (pixels == null) {
            pixels = new int[taille];
        }
        image.getPixels(pixels, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

        if (histogram != null) {
            for (int i = 0; i <= 255; i++) {
                for (int j = 0; j < 3; j++) {
                    histogram[i][j] = 0;
                }
            }
        }


        for (int i = 0; i < taille; i++) {
            couleur[i][0] = Color.red(pixels[i]);
            couleur[i][1] = Color.green(pixels[i]);
            couleur[i][2] = Color.blue(pixels[i]);
            for (int j = 0; j < 3; j++) {
                tempCouleur = couleur[i][j];
                if (histogram != null) {
                    histogram[tempCouleur][j]++;
                }
            }
        }

    }
}

