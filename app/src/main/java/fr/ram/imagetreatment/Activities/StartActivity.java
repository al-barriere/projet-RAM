package fr.ram.imagetreatment.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.IOException;

import fr.ram.imagetreatment.R;
import fr.ram.imagetreatment.Util.ImageFile;

/**
 * When you start the app, you have 2 choices :
 * - take a picture with your camera
 * - use a picture of your gallery
 */

public class StartActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int SELECT_IMAGE = 2;
    private Uri mCurrentPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                startTreatmentActivity(mCurrentPhotoUri);
            }
        }
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    startTreatmentActivity(data.getData());
                }
            }
        }
    }

    private void dispatchSelectPictureIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = ImageFile.createTempImageFile(this);
                if (photoFile != null) {
                    mCurrentPhotoUri = FileProvider.getUriForFile(this, "fr.ram.traitementimage.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoUri);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            } catch (IOException ex) {
            }
        }
    }

    public void onButtonGalleryClick(View view) {
        dispatchSelectPictureIntent();
    }

    public void onButtonCameraClick(View view) {
        dispatchTakePictureIntent();
    }

    private void startTreatmentActivity(Uri imageUri) {
        Intent intent = new Intent(this, ImageTreatmentActivity.class);
        intent.putExtra("image", imageUri);
        startActivity(intent);
    }
}