package fr.ram.imagetreatment.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;

import fr.ram.imagetreatment.Fragments.FileLoadErrorDialogFragment;
import fr.ram.imagetreatment.Fragments.ImageTreatmentActivityBackButtonDialogFragment;
import fr.ram.imagetreatment.Fragments.SeekbarColorDialogFragment;
import fr.ram.imagetreatment.Fragments.SeekbarHueDialogFragment;
import fr.ram.imagetreatment.Fragments.SeekbarValueDialogFragment;
import fr.ram.imagetreatment.Fragments.SizeMaskDialogFragment;
import fr.ram.imagetreatment.R;
import fr.ram.imagetreatment.Treatments.ColorFilter;
import fr.ram.imagetreatment.Treatments.Contrast;
import fr.ram.imagetreatment.Treatments.Convolution.GaussianBlur;
import fr.ram.imagetreatment.Treatments.Convolution.Laplacian;
import fr.ram.imagetreatment.Treatments.Convolution.AverageBlur;
import fr.ram.imagetreatment.Treatments.Convolution.Sobel;
import fr.ram.imagetreatment.Treatments.InverseColor;
import fr.ram.imagetreatment.Treatments.MedianFilter;
import fr.ram.imagetreatment.Treatments.CartoonEffect;
import fr.ram.imagetreatment.Treatments.HistogramEqualization;
import fr.ram.imagetreatment.Treatments.HueChoice;
import fr.ram.imagetreatment.Treatments.Exposure;
import fr.ram.imagetreatment.Treatments.Pencil;
import fr.ram.imagetreatment.Treatments.Sepia;
import fr.ram.imagetreatment.Treatments.ShadesOfGrey;
import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Util.BundleArgs;
import fr.ram.imagetreatment.Util.FragmentTags;
import fr.ram.imagetreatment.Util.ImageFile;
import fr.ram.imagetreatment.Util.PermissionUtil;

public class ImageTreatmentActivity extends AppCompatActivity {
    private Bitmap imageBitmap;
    private Uri photoUri;
    private CustomImageView imageView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_treatment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (CustomImageView) findViewById(R.id.imageView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        // Save the current image Uri
        photoUri = (Uri) extras.getParcelable(BundleArgs.IMAGE_URI);

        try {
            // Load the image from its Uri
            imageBitmap = ImageFile.loadImageFromUri(photoUri, this);
        } catch (IOException e) {
            // If an error happened, inform the user
            showFileLoadErrorDialogFragment();
        }

        // Set the Bitmap to the CustomImageView
        imageView.setImageBitmap(imageBitmap);

        // Request for storage permissions
        PermissionUtil.verifyStoragePermissions(this);
    }

    private void showFileLoadErrorDialogFragment() {
        DialogFragment fragmentFileError = new FileLoadErrorDialogFragment();
        fragmentFileError.show(getSupportFragmentManager(), null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Finish the current activity
            case android.R.id.home:
                goBack();
                break;
            // Reset the current image
            // Inform the CustomImageView that the image was not modified by any image effects
            case R.id.resetImageButton:
                try {
                    // Load the image from its Uri
                    imageBitmap = ImageFile.loadImageFromUri(photoUri, this);
                } catch (IOException e) {
                    // If an error happened, inform the user
                    showFileLoadErrorDialogFragment();
                }
                imageView.setImageBitmap(imageBitmap);
                imageView.setImageModified(false);
                break;
            // If the image is modified, save it on the external storage
            case R.id.saveButton:
                if (imageView.getImageModified())
                    // Save the image in a new file
                    ImageFile.saveImage(this, imageBitmap);
                break;
            // Reset the image zoom and scroll position
            case R.id.resestZoom:
                Matrix matrix = imageView.setMatrixCenter();
                imageView.setImageMatrix(matrix);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /***
     * Leave the current activity
     * If the image is modified, show an alert dialog for saving the image before leaving
     */
    private void goBack() {
        if (imageView.getImageModified()) {
            Bundle args = new Bundle();
            args.putParcelable(BundleArgs.IMAGE_BITMAP, imageBitmap);

            DialogFragment fragmentLeave = new ImageTreatmentActivityBackButtonDialogFragment();
            fragmentLeave.setArguments(args);
            fragmentLeave.show(getSupportFragmentManager(), null);
        } else {
            this.finish();
        }
    }

    /***
     * When the back button or the back arrow is pressed
     */
    @Override
    public void onBackPressed() {
        goBack();
    }

    public CustomImageView getImageView() {
        return imageView;
    }

    /**
     * OnClick events
     */
    public void inverseColors(View view) {
        InverseColor inverseColor = new InverseColor();
        inverseColor.compute(ImageTreatmentActivity.this, imageView, null);
    }

    public void toShadesOfGrey(View view) {
        ShadesOfGrey shadesOfGrey = new ShadesOfGrey();
        shadesOfGrey.compute(ImageTreatmentActivity.this, imageView, null);
    }

    public void cartoonEffect(View view) {
        CartoonEffect t = new CartoonEffect();
        t.compute(ImageTreatmentActivity.this, imageView, null);
    }

    public void toSepia(View view) {
        Sepia sepia = new Sepia();
        sepia.compute(ImageTreatmentActivity.this, imageView, null);
    }

    public void choiceHue(View view) {
        SeekbarHueDialogFragment newFragments = new SeekbarHueDialogFragment();
        newFragments.show(getFragmentManager(), FragmentTags.CHOICE_HUE);
    }

    public void colorFilter(View view) {
        SeekbarColorDialogFragment newFragments = new SeekbarColorDialogFragment();
        newFragments.show(getFragmentManager(), FragmentTags.COLOR_FILTER);
    }

    public void exposure(View view) {
        SeekbarValueDialogFragment newFragments = new SeekbarValueDialogFragment();
        newFragments.show(getFragmentManager(), FragmentTags.EXPOSURE);
    }

    public void averageBlur(View view) {
        SizeMaskDialogFragment newFragment = new SizeMaskDialogFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putString(BundleArgs.FILTER, BundleArgs.AVERAGE_BLUR);
        newFragment.setArguments(fragmentArgs);
        newFragment.show(getFragmentManager(), FragmentTags.SIZE_MASK);
    }

    public void gaussianBlur(View view) {
        SizeMaskDialogFragment newFragment = new SizeMaskDialogFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putString(BundleArgs.FILTER, BundleArgs.GAUSSIAN_BLUR);
        newFragment.setArguments(fragmentArgs);
        newFragment.show(getFragmentManager(), FragmentTags.SIZE_MASK);
    }

    public void laplacianFilter(View view) {
        Laplacian l = new Laplacian();
        l.compute(ImageTreatmentActivity.this, imageView, null);
    }

    public void sobelFilter(View view) {
        Sobel s = new Sobel();
        s.compute(ImageTreatmentActivity.this, imageView, null);
    }

    public void pencil(View view) {
        Pencil p = new Pencil();
        p.compute(ImageTreatmentActivity.this, imageView, null);
    }

    public void contrast(View view) {
        SeekbarValueDialogFragment newFragments = new SeekbarValueDialogFragment();
        newFragments.show(getFragmentManager(), FragmentTags.CONTRAST);
    }

    public void histogramEqualization(View view) {
        HistogramEqualization he = new HistogramEqualization();
        he.compute(ImageTreatmentActivity.this, imageView, null);
    }

    public void medianFilter(View view) {
        MedianFilter medianFilter = new MedianFilter();
        Bundle args = new Bundle();
        medianFilter.compute(ImageTreatmentActivity.this, imageView, args);
    }

    /**
     * Functions DialogFragment
     */
    public void hueChoice(int hue) {
        HueChoice hueChoice = new HueChoice();
        Bundle seekData = new Bundle();
        seekData.putInt(BundleArgs.VALUE, hue);
        hueChoice.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void filterColor(int color) {
        ColorFilter colorFilter = new ColorFilter();
        Bundle seekData = new Bundle();
        seekData.putInt(BundleArgs.COLOR, color);
        colorFilter.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void exposureTreatment(int value) {
        Exposure exposure = new Exposure();
        Bundle seekData = new Bundle();
        seekData.putInt(BundleArgs.VALUE, value);
        exposure.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void contrastTreatment(int value) {
        Contrast contrast = new Contrast();
        Bundle seekData = new Bundle();
        seekData.putInt(BundleArgs.VALUE, value);
        contrast.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void averageBlurTreatment(int maskSize) {
        AverageBlur m = new AverageBlur();
        Bundle seekData = new Bundle();
        seekData.putInt(BundleArgs.MASK_SIZE, maskSize);
        m.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void gaussianFilterTreatment(int maskSize) {
        GaussianBlur g = new GaussianBlur();
        Bundle seekData = new Bundle();
        seekData.putInt(BundleArgs.MASK_SIZE, maskSize);
        g.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    /***
     * Applies the new image
     * Informs the UI that the image has been modified
     * Hides the effect ProgressDialog
     * @param result The new bitmap
     * @param progressDialog The effect ProgressDialog
     */
    public void processFinish(Bitmap result, ProgressDialog progressDialog) {
        imageBitmap = result;
        imageView.setImageBitmap(result);
        imageView.setImageModified(true);
        progressDialog.dismiss();
    }
}
