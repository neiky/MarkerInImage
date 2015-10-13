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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView addedImage;
    ImageView clickImage;
    List<Point> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        points = new ArrayList<>();
        points.add(new Point(0.5, 0.5));
        points.add(new Point(0.2, 0.1));
        points.add(new Point(0.7, 0.8));
        points.add(new Point(0.1, 0.6));


        clickImage = (ImageView) findViewById(R.id.imageView);
        clickImage.post(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.drawMarkers();
            }
        });

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

                    addedImage.setImageDrawable(markerIcon);

                    final int actualHeight;
                    final int actualWidth;
                    final int imageViewHeight = clickImage.getHeight();
                    final int imageViewWidth = clickImage.getWidth();
                    final int bitmapHeight = clickImage.getDrawable().getIntrinsicHeight();
                    final int bitmapWidth = clickImage.getDrawable().getIntrinsicWidth();
                    if (imageViewHeight * bitmapWidth <= imageViewWidth * bitmapHeight) {
                        actualHeight = imageViewHeight;
                        // obtain width of rendered using the aspect ratio of the image view
                        actualWidth = bitmapWidth * imageViewHeight / bitmapHeight;
                    } else {
                        // obtain height of rendered using the aspect ratio of the image view
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

    public void drawMarkers(){
        final RelativeLayout container = (RelativeLayout) findViewById(R.id.container);

        final int actualHeight;
        final int actualWidth;
        final int imageViewHeight = clickImage.getHeight();
        final int imageViewWidth = clickImage.getWidth();
        final int bitmapHeight = clickImage.getDrawable().getIntrinsicHeight();
        final int bitmapWidth = clickImage.getDrawable().getIntrinsicWidth();
        if (imageViewHeight * bitmapWidth <= imageViewWidth * bitmapHeight) {
            actualHeight = imageViewHeight;
            // obtain width of rendered using the aspect ratio of the image view
            actualWidth = bitmapWidth * imageViewHeight / bitmapHeight;
        } else {
            // obtain height of rendered using the aspect ratio of the image view
            actualHeight = bitmapHeight * imageViewWidth / bitmapWidth;
            actualWidth = imageViewWidth;
        }

        int distanceTop = (clickImage.getHeight()-actualHeight) / 2;
        int distanceLeft = (clickImage.getWidth()-actualWidth) / 2;

        Drawable markerIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.marker_stored);
        int height = markerIcon.getIntrinsicHeight();
        int width = markerIcon.getIntrinsicWidth();

        for (final Point point : points) {
            ImageView addedImage1 = new ImageView(getApplicationContext());
            addedImage1.setTag(point);
            addedImage1.setImageDrawable(markerIcon);

            int posX = (int) (point.getX() * container.getWidth());
            int posY = (int) (point.getY() * container.getHeight());
            posX = (int) (point.getX()*actualWidth + distanceLeft) - width/2;
            posY = (int) (point.getY()*actualHeight + distanceTop) - height/2;

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(posX, posY, 0, 0);
            addedImage1.setLayoutParams(lp);
            container.addView(addedImage1);
        }
    }
}
