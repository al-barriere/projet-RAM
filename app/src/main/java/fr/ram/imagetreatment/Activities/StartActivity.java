package fr.ram.imagetreatment.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.IOException;

import fr.ram.imagetreatment.Fragments.FileSaveErrorDialogFragment;
import fr.ram.imagetreatment.R;
import fr.ram.imagetreatment.Util.BundleArgs;
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

    /***
     * Manage the ActivityResult : If a photo was taken or selected, start a new ImageTreatmentActivity which loads the image
     * @param requestCode REQUEST_TAKE_PHOTO (for capturing an image with the camera) or SELECT_IMAGE (for selecting an image with the gallery)
     * @param resultCode Activity.RESULT_OK if there wasn't any error
     * @param data The Activity result data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK)
                    startImageTreatmentActivity(mCurrentPhotoUri);
                break;
            case SELECT_IMAGE:
                if (resultCode == Activity.RESULT_OK && data != null)
                    startImageTreatmentActivity(data.getData());
                break;
        }
    }

    /***
     * Start a new ImageTreatmentActivity and pass it an image Uri
     * @param imageUri The image Uri to load
     */
    private void startImageTreatmentActivity(Uri imageUri) {
        Intent intent = new Intent(this, ImageTreatmentActivity.class);
        intent.putExtra(BundleArgs.IMAGE_URI, imageUri);
        startActivity(intent);
    }

    /***
     * Dispatch an image chooser in order to pick an image
     */
    private void dispatchSelectPictureIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    /***
     * Dispatch the camera intent in order to take a picture
     */
    private void dispatchTakePictureIntent() {
        // Create a ne take picture intent instance
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                // Create a file where the image will be saved
                photoFile = ImageFile.createTempImageFile(this);
                if (photoFile != null) {
                    // Save the Uri of the picture
                    mCurrentPhotoUri = FileProvider.getUriForFile(this, "fr.ram.imagetreatment.fileprovider", photoFile);
                    // Tell the intent where to save the picture
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoUri);
                    // Start the take picture intent
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            } catch (IOException ex) {
                // If an error happened, inform the user
                DialogFragment fragmentFileError = new FileSaveErrorDialogFragment();
                fragmentFileError.show(getSupportFragmentManager(), null);
            }
        }
    }

    /***
     * If the user click on the button "Gallery", dispatch the image chooser intent
     * @param view The target of the event
     */
    public void onButtonGalleryClick(View view) {
        dispatchSelectPictureIntent();
    }

    /***
     * If the user click on the button "Camera", dispatch the camera intent
     * @param view The target of the event
     */
    public void onButtonCameraClick(View view) {
        dispatchTakePictureIntent();
    }
}