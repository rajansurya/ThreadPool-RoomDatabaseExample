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


            PhotoTask photoTask3 = new PhotoTask();
            URL url3 = new URL("https://www.androidpit.com/feed/main.xml");
            photoTask3.setimageURL(url3);
            photoTasks.add(photoTask3);

            PhotoTask photoTask21 = new PhotoTask();
            URL url21 = new URL("https://www.stacktips.com/api/get_category_posts/?dev=1&slug=android");
            photoTask21.setimageURL(url21);

            photoTasks.add(photoTask21);

            PhotoTask photoTask22 = new PhotoTask();
            URL url22 = new URL("https://www.androidpit.com/feed/main.xml");
            photoTask22.setimageURL(url22);
            photoTasks.add(photoTask22);
            PhotoTask photoTask23 = new PhotoTask();
            URL url23 = new URL("https://www.stacktips.com/api/get_category_posts/?dev=1&slug=android");
            photoTask23.setimageURL(url23);
            photoTasks.add(photoTask23);
            PhotoTask photoTask24 = new PhotoTask();
            URL url24 = new URL("https://www.androidpit.com/feed/main.xml");
            photoTask24.setimageURL(url24);
            photoTasks.add(photoTask24);
            PhotoTask photoTask25 = new PhotoTask();
            URL url25 = new URL("https://www.stacktips.com/api/get_category_posts/?dev=1&slug=android");
            photoTask25.setimageURL(url25);
            photoTasks.add(photoTask25);
            PhotoTask photoTask26 = new PhotoTask();
            URL url26 = new URL("https://www.androidpit.com/feed/main.xml");
            photoTask26.setimageURL(url26);
            photoTasks.add(photoTask26);

            PhotoTask photoTask27 = new PhotoTask();
            URL url27 = new URL("https://www.stacktips.com/api/get_category_posts/?dev=1&slug=android");
            photoTask27.setimageURL(url27);

            photoTasks.add(photoTask27);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        PhotoManager.getInstance().attachactivity(this);
        for (int i = 0; i < photoTasks.size(); i++) {
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
//        PhotoManager.cancelAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        PhotoManager.cancelAll();
    }
}
