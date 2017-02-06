package fr.ram.traitementimage.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;

import fr.ram.traitementimage.Fragments.MainActivityBackButtonDialogFragment;
import fr.ram.traitementimage.R;
import fr.ram.traitementimage.Util.ImageFile;
import fr.ram.traitementimage.Treatment.ShadesOfGray;

public class ImageTreatmentActivity extends AppCompatActivity {
    private Bitmap imageBitmap;
    private Uri photoUri;
    private ImageView imageView;
    private Toolbar toolbar;
    private LinearLayout bottomBar;
    private RelativeLayout imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_treatment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.imageView);
        bottomBar = (LinearLayout) findViewById(R.id.bottomBar);
        imageContainer = (RelativeLayout) findViewById(R.id.imageContainer);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        photoUri = (Uri) extras.getParcelable("image");
        boolean fromCamera = extras.getBoolean("fromCamra");


        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
            imageBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
            if (fromCamera)
                imageBitmap = ImageFile.rotationImage(photoUri, imageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageContainer.getViewTreeObserver().removeOnPreDrawListener(this);

                ViewGroup.LayoutParams params2 = bottomBar.getLayoutParams();
                int height = imageContainer.getHeight() - params2.height;
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, height);
                imageContainer.setLayoutParams(lp);

                return false;
            }
        });

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
            case R.id.saveButton:
                Toast.makeText(getApplicationContext(), "Sauvegarder", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onHomePressed() {
        DialogFragment newFragment = new MainActivityBackButtonDialogFragment();
        newFragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onBackPressed() {
        onHomePressed();
    }

    public void onclick(View view) {
        ShadesOfGray shadesOfGray=new ShadesOfGray();
        shadesOfGray.calcul(imageBitmap,imageView);
    }
}
