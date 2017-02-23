package fr.ram.traitementimage.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;

import fr.ram.traitementimage.Fragments.MainActivityBackButtonDialogFragment;
import fr.ram.traitementimage.Fragments.SeekbarHueColorDialogFragment;
import fr.ram.traitementimage.Fragments.SeekbarValueDialogFragment;
import fr.ram.traitementimage.Fragments.SizeMaskDialogFragment;
import fr.ram.traitementimage.R;
import fr.ram.traitementimage.Treatment.ColorFilter;
import fr.ram.traitementimage.Treatment.Convolution.Gaussien;
import fr.ram.traitementimage.Treatment.Convolution.Laplacien;
import fr.ram.traitementimage.Treatment.Convolution.Moyenneur;
import fr.ram.traitementimage.Treatment.Convolution.Sobel;
import fr.ram.traitementimage.Treatment.HueChoice;
import fr.ram.traitementimage.Treatment.OverExposure;
import fr.ram.traitementimage.Treatment.Sepia;
import fr.ram.traitementimage.Treatment.ShadesOfGray;
import fr.ram.traitementimage.Util.CustomImageView;
import fr.ram.traitementimage.Util.ImageFile;

public class ImageTreatmentActivity extends AppCompatActivity {
    private Bitmap imageBitmap;
    private Uri photoUri;
    private CustomImageView imageView;
    private Toolbar toolbar;
    private HorizontalScrollView bottomBar;
    private RelativeLayout imageContainer;
    private int option;

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
            e.printStackTrace();
        }

        imageView.setImageBitmap(imageBitmap);
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
                onHomePressed();
                break;
            case R.id.resetImageButton:
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                    imageBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    imageView.setImageBitmap(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.saveButton:
                if (imageView.getImageModified()) {
                    ImageFile.saveImage(this, imageBitmap);
                    imageView.setImageModified(false);
                    Snackbar.make(findViewById(R.id.activity_main), R.string.image_saved, Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onHomePressed() {
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
        onHomePressed();
    }

    /**
     * OnClick event
     */
    public void toShadesOfGray(View view) {
        ShadesOfGray shadesOfGray = new ShadesOfGray();
        shadesOfGray.calcul(imageView, null);
    }

    public void toSepia(View view) {
        Sepia sepia = new Sepia();
        sepia.calcul(imageView, null);
    }

    public void choiceHue(View view) {
        option = 0;
        SeekbarHueColorDialogFragment newFragments = new SeekbarHueColorDialogFragment();
        newFragments.show(getFragmentManager(), "choice hue");
    }

    public void colorFilter(View view) {
        option = 1;
        SeekbarHueColorDialogFragment newFragments = new SeekbarHueColorDialogFragment();
        newFragments.show(getFragmentManager(), "colorFilter");
    }

    public void overExposure(View view) {
        SeekbarValueDialogFragment newFragments = new SeekbarValueDialogFragment();
        newFragments.show(getFragmentManager(), "overexposure");
    }

    public void moyenneur(View view) {
        SizeMaskDialogFragment newFragment=new SizeMaskDialogFragment();
        newFragment.show(getFragmentManager(), "sizeMask");
    }

    public void gaussien(View view) {
        Gaussien g = new Gaussien();
        g.calcul(imageView, null);
    }

    public void laplacien(View view) {
        Laplacien l = new Laplacien();
        l.calcul(imageView, null);
    }

    public void sobel(View view) {
        Sobel s = new Sobel();
        s.calcul(imageView, null);
    }

    /**
     * Functions Dialogfragment
     */
    public void hueChoice(int hue) {
        HueChoice hueChoice = new HueChoice();
        Bundle seekData = new Bundle();
        seekData.putInt("value", hue);
        hueChoice.calcul(imageView, seekData);
    }

    public void filterColor(int color) {
        ColorFilter colorFilter = new ColorFilter();
        Bundle seekData = new Bundle();
        seekData.putInt("color", color);
        colorFilter.calcul(imageView, seekData);
    }

    public void overExposureTreatment(int value) {
        OverExposure overExposure = new OverExposure();
        Bundle seekData = new Bundle();
        seekData.putInt("value", value);
        overExposure.calcul(imageView, seekData);
    }

    public void moyenneurTreatment(int maskSize)
    {
        Moyenneur m = new Moyenneur();
        Bundle seekData=new Bundle();
        seekData.putInt("mask_size", maskSize);
        m.calcul(imageView, seekData);
    }

    public int getOption() {
        return option;
    }
}
