package com.example.neiky.markerinimage;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    ImageView addedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    int height = 120;   // TODO how to obtain the real displayed size of the marker?
                    int width = 120;
                    int posX = x - (width / 2), posY = y - (height / 2);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    if (addedImage == null) {
                        addedImage = new ImageView(getApplicationContext());
                    } else {
                        ((ViewGroup) v).removeView(addedImage);
                    }

                    //addedImage.setImageDrawable(getResources().getDrawable(R.drawable.marker));
                    addedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.marker));

                    if (x < width / 2) {
                        posX = 0;
                    }
                    if (y < height / 2) {
                        posY = 0;
                    }

                    if (x > container.getWidth() - width / 2) {
                        posX = container.getWidth() - width;
                    }
                    if (y > container.getHeight() - height / 2) {
                        posY = container.getHeight() - height;
                    }
                    lp.setMargins(posX, posY, 0, 0);
                    addedImage.setLayoutParams(lp);
                    ((ViewGroup) v).addView(addedImage);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
