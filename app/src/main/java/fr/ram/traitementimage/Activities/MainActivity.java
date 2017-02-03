package fr.ram.traitementimage.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import fr.ram.traitementimage.Fragments.MainActivityBackButtonDialogFragment;
import fr.ram.traitementimage.R;

public class MainActivity extends AppCompatActivity {
    private Bitmap imageBitmap;
    private ImageView imageView;
    private Toolbar toolbar;
    private LinearLayout bottomBar;
    private RelativeLayout imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.imageView);
        bottomBar = (LinearLayout) findViewById(R.id.bottomBar);
        imageContainer = (RelativeLayout) findViewById(R.id.imageContainer);
        imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_image);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
}
