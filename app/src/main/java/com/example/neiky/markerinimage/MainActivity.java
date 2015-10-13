package com.example.neiky.markerinimage;

import android.graphics.drawable.Drawable;
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
    ImageView clickImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickImage = (ImageView) findViewById(R.id.imageView);

        // the container holds the image and all the added markers
        final RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    Drawable markerIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.marker);
                    int height = markerIcon.getIntrinsicHeight();
                    int width = markerIcon.getIntrinsicWidth();
                    int posX = x - (width / 2);
                    int posY = y - (height / 2);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    if (addedImage == null) {
                        addedImage = new ImageView(getApplicationContext());
                    } else {
                        ((ViewGroup) v).removeView(addedImage);
                    }

                    //addedImage.setImageDrawable(getResources().getDrawable(R.drawable.marker));
                    addedImage.setImageDrawable(markerIcon);

                    final int actualHeight;
                    final int actualWidth;
                    final int imageViewHeight = clickImage.getHeight();
                    final int imageViewWidth = clickImage.getWidth();
                    final int bitmapHeight = clickImage.getDrawable().getIntrinsicHeight();
                    final int bitmapWidth = clickImage.getDrawable().getIntrinsicWidth();
                    if (imageViewHeight * bitmapWidth <= imageViewWidth * bitmapHeight) {
                        actualHeight = imageViewHeight;
                        // Breite der ImageView anhand des Seitenverhältnisses des umgebenden Containers (rackImage) bestimmen
                        actualWidth = bitmapWidth * imageViewHeight / bitmapHeight;
                    } else {
                        // Höhe der ImageView anhand des Seitenverhältnisses des umgebenden Containers (rackImage) bestimmen
                        actualHeight = bitmapHeight * imageViewWidth / bitmapWidth;
                        actualWidth = imageViewWidth;
                    }

                    int distanceTop = (clickImage.getHeight()-actualHeight) / 2;
                    int distanceLeft = (clickImage.getWidth()-actualWidth) / 2;

                    if (x < distanceLeft + width/2)
                    {
                        posX = distanceLeft;
                    }
                    if (x > clickImage.getMeasuredWidth() - distanceLeft - width / 2) {
                        posX = clickImage.getMeasuredWidth() - distanceLeft - width;
                    }

                    if (y < distanceTop + height / 2) {
                        posY = distanceTop;
                    }
                    if (y > clickImage.getMeasuredHeight() - distanceTop - height){
                        posY = clickImage.getMeasuredHeight() - distanceTop - height;
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
