package fr.ram.traitementimage.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import fr.ram.traitementimage.R;
import fr.ram.traitementimage.Treatment.ColorFilter;
import fr.ram.traitementimage.Treatment.Convolution.Gaussien;
import fr.ram.traitementimage.Treatment.Convolution.Moyenneur;
import fr.ram.traitementimage.Treatment.HueChoice;
import fr.ram.traitementimage.Treatment.OverExposure;
import fr.ram.traitementimage.Treatment.Sepia;
import fr.ram.traitementimage.Treatment.ShadesOfGray;
import fr.ram.traitementimage.Util.ImageFile;

public class ImageTreatmentActivity extends AppCompatActivity {
    private Bitmap imageBitmap;
    private Uri photoUri;
    private ImageView imageView;
    private Toolbar toolbar;
    private HorizontalScrollView bottomBar;
    private boolean modifiedAfterSaved;
    private RelativeLayout imageContainer;
    private Bundle seekData;
    private int option;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_treatment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.imageView);
        bottomBar = (HorizontalScrollView) findViewById(R.id.bottomBar);

        setImageModified(false);

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

    private void setImageModified(boolean modified) {
        modifiedAfterSaved = modified;
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
                if (modifiedAfterSaved) {
                    ImageFile.saveImage(this, imageBitmap);
                    setImageModified(false);
                    Snackbar.make(findViewById(R.id.activity_main), R.string.image_saved, Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onHomePressed() {
        if (modifiedAfterSaved) {
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
        shadesOfGray.calcul(imageBitmap, imageView, seekData);
        setImageModified(true);
    }

    public void toSepia(View view) {
        Sepia sepia = new Sepia();
        sepia.calcul(imageBitmap, imageView, seekData);
        setImageModified(true);
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
        Moyenneur m = new Moyenneur();
        seekData = new Bundle();
        m.calcul(imageBitmap, imageView, seekData);
    }

    public void gaussien(View view) {
        Gaussien g = new Gaussien();
        seekData = new Bundle();
        g.calcul(imageBitmap, imageView, seekData);
    }

    /**
     * Functions Dialogfragment
     */
    public void hueChoice(int hue) {
        HueChoice hueChoice = new HueChoice();
        seekData = new Bundle();
        seekData.putInt("value", hue);
        hueChoice.calcul(imageBitmap, imageView, seekData);
        setImageModified(true);
    }

    public void filterColor(int color) {
        ColorFilter colorFilter = new ColorFilter();
        seekData = new Bundle();
        seekData.putInt("color", color);
        colorFilter.calcul(imageBitmap, imageView, seekData);
        setImageModified(true);
    }

    public void overExposureTreatment(int value) {
        OverExposure overExposure = new OverExposure();
        seekData = new Bundle();
        seekData.putInt("value", value);
        overExposure.calcul(imageBitmap, imageView, seekData);
        setImageModified(true);
    }

    public int getOption() {
        return option;
    }
}
