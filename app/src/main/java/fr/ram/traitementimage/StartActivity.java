package fr.ram.traitementimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * When you start the app, you have 2 choices :
 *  - take a picture with your camera
 *  - use a picture of your gallery
 */

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button camera = (Button) findViewById(R.id.camera);
        Button gallery = (Button) findViewById(R.id.gallery);
    }
}
