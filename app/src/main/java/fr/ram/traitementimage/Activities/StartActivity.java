package fr.ram.traitementimage.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import fr.ram.traitementimage.R;
import fr.ram.traitementimage.Util.ImageFile;

/**
 * When you start the app, you have 2 choices :
 * - take a picture with your camera
 * - use a picture of your gallery
 */

public class StartActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int SELECT_IMAGE = 2;
    private Uri mCurrentPhotoUri;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button camera = (Button) findViewById(R.id.camera);
        Button gallery = (Button) findViewById(R.id.gallery);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                startTreatmentActivity(mCurrentPhotoUri, true);
            }
        }
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    startTreatmentActivity(data.getData(), false);
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
                photoFile = ImageFile.createImageFile(this);
                if (photoFile != null) {
                    mCurrentPhotoUri = FileProvider.getUriForFile(this, "fr.ram.traitementimage.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoUri);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            } catch (IOException ex) {
            }
        }
    }

    public void onButtonGaleryClick(View view) {
        dispatchSelectPictureIntent();
    }

    public void onButtonCameraClick(View view) {
        dispatchTakePictureIntent();
    }

    private void startTreatmentActivity(Uri imageUri, boolean fromCamera) {
        Intent intent = new Intent(this, ImageTreatmentActivity.class);
        intent.putExtra("image", imageUri);
        intent.putExtra("fromCamera", fromCamera);
        startActivity(intent);
    }
}
