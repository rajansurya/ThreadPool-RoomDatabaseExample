package com.thread.excutor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.roomwordsample.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainThreadCall extends Activity implements PhotoManager.sendToAcivity {
    ImageView  two, three;
    TextView one;
   static int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        one = (TextView) findViewById(R.id.one);
        two = (ImageView) findViewById(R.id.two);
        three = (ImageView) findViewById(R.id.three);

        ArrayList<PhotoTask> photoTasks = new ArrayList<PhotoTask>();
        try {
            URL url = new URL("https://www.cinemablend.com/rss_review.php");
            PhotoTask photoTask = new PhotoTask();
            photoTask.setimageURL(url);
            photoTasks.add(photoTask);

            PhotoTask photoTask1 = new PhotoTask();
            URL url1 = new URL("https://www.stacktips.com/api/get_category_posts/?dev=1&slug=android");
            photoTask1.setimageURL(url1);
            photoTasks.add(photoTask1);
            PhotoTask photoTask2 = new PhotoTask();
            URL url2 = new URL("https://www.androidpit.com/feed/main.xml");
            photoTask2.setimageURL(url2);
            photoTasks.add(photoTask2);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        PhotoManager.getInstance().attachactivity(this);
        for (int i = 0; i < 3; i++) {
            PhotoManager.startDownload(photoTasks.get(i), false);
        }

    }

    @Override
    public void sendtoactivity(String bitmap) {
        System.out.println("sendtoactivity=============="+i);
//        if (i == 0)
            one.setText(bitmap);
//        if (i == 1)
//            two.setImageBitmap(bitmap);
//        if (i == 2)
//            three.setImageBitmap(bitmap);
        i++;
    }

    @Override
    protected void onPause() {
        super.onPause();
        PhotoManager.cancelAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PhotoManager.cancelAll();
    }
}
