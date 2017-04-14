package fr.ram.imagetreatment.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import java.io.IOException;

import fr.ram.imagetreatment.Enums.EffectModeEnum;
import fr.ram.imagetreatment.Fragments.MainActivityBackButtonDialogFragment;
import fr.ram.imagetreatment.Fragments.SeekbarHueColorDialogFragment;
import fr.ram.imagetreatment.Fragments.SeekbarValueDialogFragment;
import fr.ram.imagetreatment.Fragments.SizeMaskDialogFragment;
import fr.ram.imagetreatment.R;
import fr.ram.imagetreatment.Treatments.ColorFilter;
import fr.ram.imagetreatment.Treatments.Contrast;
import fr.ram.imagetreatment.Treatments.Convolution.GaussianBlur;
import fr.ram.imagetreatment.Treatments.Convolution.Laplacian;
import fr.ram.imagetreatment.Treatments.Convolution.AverageBlur;
import fr.ram.imagetreatment.Treatments.Convolution.Sobel;
import fr.ram.imagetreatment.Treatments.test;
import fr.ram.imagetreatment.Treatments.FilterChoiceEnum;
import fr.ram.imagetreatment.Treatments.HistogramEqualization;
import fr.ram.imagetreatment.Treatments.HueChoice;
import fr.ram.imagetreatment.Treatments.OverExposure;
import fr.ram.imagetreatment.Treatments.Pencil;
import fr.ram.imagetreatment.Treatments.Sepia;
import fr.ram.imagetreatment.Treatments.ShadesOfGrey;
import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Util.ImageFile;
import fr.ram.imagetreatment.Util.PermissionUtil;

import static fr.ram.imagetreatment.Enums.EffectModeEnum.EFFECT_SELECTION;

public class ImageTreatmentActivity extends AppCompatActivity {
    private Bitmap imageBitmap;
    private Uri photoUri;
    private CustomImageView imageView;
    private Toolbar toolbar;
    private HorizontalScrollView bottomBar;
    private RelativeLayout imageContainer;
    private FilterChoiceEnum option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_treatment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (CustomImageView) findViewById(R.id.imageView);
        bottomBar = (HorizontalScrollView) findViewById(R.id.bottomBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        photoUri = (Uri) extras.getParcelable("image");

        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
            imageBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
        } catch (IOException e) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.imagefile_load_ioexception_title)
                    .setMessage(R.string.imagefile_load_ioexception_content)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ImageTreatmentActivity.this.finish();
                        }
                    })
                    .show();
        }

        imageView.setImageBitmap(imageBitmap);
        PermissionUtil.verifyStoragePermissions(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                break;
            case R.id.effectModeButton:
                switch (imageView.getEffectMode()) {
                    case EFFECT_ALL:
                        imageView.setEffectMode(EFFECT_SELECTION);
                        item.setIcon(R.drawable.ic_gesture_24dp);
                        item.setTitle(R.string.modeZoom);
                        break;
                    case EFFECT_SELECTION:
                        imageView.setEffectMode(EffectModeEnum.EFFECT_ALL);
                        item.setIcon(R.drawable.ic_select_all_24dp);
                        item.setTitle(R.string.modeSelection);
                        break;
                }
                break;
            case R.id.resetImageButton:
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                    imageBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    imageView.setImageBitmap(imageBitmap);
                    imageView.setImageModified(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.saveButton:
                if (imageView.getImageModified()) {
                    ImageFile.saveImage(this, imageBitmap);
                }
                break;
            case R.id.resestZoom:
                Matrix matrix=imageView.setMatrixCenter();
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
            args.putParcelable("imageBitmap", imageBitmap);

            DialogFragment fragmentLeave = new MainActivityBackButtonDialogFragment();
            fragmentLeave.setArguments(args);
            fragmentLeave.show(getSupportFragmentManager(), null);
        } else {
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    public CustomImageView getImageView() {
        return imageView;
    }

    /**
     * OnClick event
     */
    public void toShadesOfGrey(View view) {
        ShadesOfGrey shadesOfGrey = new ShadesOfGrey();
        shadesOfGrey.compute(ImageTreatmentActivity.this, imageView, null);
    }

    public void test(View view)
    {
        test t=new test();
        t.compute(ImageTreatmentActivity.this,imageView,null);
    }

    public void toSepia(View view) {
        Sepia sepia = new Sepia();
        sepia.compute(ImageTreatmentActivity.this, imageView, null);
    }

    public void choiceHue(View view) {
        option = FilterChoiceEnum.HUE;
        SeekbarHueColorDialogFragment newFragments = new SeekbarHueColorDialogFragment();
        newFragments.show(getFragmentManager(), "choice hue");
    }

    public void colorFilter(View view) {
        option = FilterChoiceEnum.COLOR;
        SeekbarHueColorDialogFragment newFragments = new SeekbarHueColorDialogFragment();
        newFragments.show(getFragmentManager(), "colorFilter");
    }

    public void exposure(View view) {
        SeekbarValueDialogFragment newFragments = new SeekbarValueDialogFragment();
        newFragments.show(getFragmentManager(), "overexposure");
    }

    public void averageBlur(View view) {
        SizeMaskDialogFragment newFragment = new SizeMaskDialogFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putString("filter", "averageBlur");
        newFragment.setArguments(fragmentArgs);
        newFragment.show(getFragmentManager(), "sizeMask");
    }

    public void gaussianBlur(View view) {
        SizeMaskDialogFragment newFragment = new SizeMaskDialogFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putString("filter", "gaussianBlur");
        newFragment.setArguments(fragmentArgs);
        newFragment.show(getFragmentManager(), "sizeMask");
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
        newFragments.show(getFragmentManager(), "contrast");
    }

    public void histogramEqualization(View view) {
        HistogramEqualization he = new HistogramEqualization();
        he.compute(ImageTreatmentActivity.this, imageView, null);
    }

    /**
     * Functions Dialogfragment
     */
    public void hueChoice(int hue) {
        HueChoice hueChoice = new HueChoice();
        Bundle seekData = new Bundle();
        seekData.putInt("value", hue);
        hueChoice.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void filterColor(int color) {
        ColorFilter colorFilter = new ColorFilter();
        Bundle seekData = new Bundle();
        seekData.putInt("color", color);
        colorFilter.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void overExposureTreatment(int value) {
        OverExposure overExposure = new OverExposure();
        Bundle seekData = new Bundle();
        seekData.putInt("value", value);
        overExposure.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void contrastTreatment(int value) {
        Contrast contrast = new Contrast();
        Bundle seekData = new Bundle();
        seekData.putInt("value", value);
        contrast.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void averageBlurTreatment(int maskSize) {
        AverageBlur m = new AverageBlur();
        Bundle seekData = new Bundle();
        seekData.putInt("maskSize", maskSize);
        m.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void gaussianFilterTreatment(int maskSize) {
        GaussianBlur g = new GaussianBlur();
        Bundle seekData = new Bundle();
        seekData.putInt("maskSize", maskSize);
        g.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public FilterChoiceEnum getOption() {
        return option;
    }

    /***
     * Applies the new image
     * Informs the UI that the image has been modified
     * Hides the effect ProgressDialog
     * @param result : The new bitmap
     * @param progressDialog : The effect ProgressDialog
     */
    public void processFinish(Bitmap result, ProgressDialog progressDialog) {
        imageView.setImageBitmap(result);
        imageView.setImageModified(true);
        progressDialog.dismiss();
    }
}
