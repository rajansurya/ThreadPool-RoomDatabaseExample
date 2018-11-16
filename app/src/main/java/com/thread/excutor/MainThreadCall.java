package com.thread.excutor;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainThreadCall extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<PhotoTask> photoTasks=new ArrayList<PhotoTask>();
        try {
            URL url=new URL("https://www.google.com/imgres?imgurl=https%3A%2F%2Fprocessing.org%2Freference%2Fimages%2Ffilter_2_invert.png&imgrefurl=https%3A%2F%2Fprocessing.org%2Freference%2Ffilter_.html&docid=JYozfYKL-IRt-M&tbnid=1i0x51Glox8UcM%3A&vet=10ahUKEwiKi96N-tjeAhWDWysKHS43DN8QMwhtKAMwAw..i&w=100&h=100&safe=active&bih=648&biw=1304&q=image&ved=0ahUKEwiKi96N-tjeAhWDWysKHS43DN8QMwhtKAMwAw&iact=mrc&uact=8");
            PhotoTask photoTask=   new PhotoTask();
            photoTask.setimageURL(url);
            photoTasks.add(photoTask);

            PhotoTask photoTask1=   new PhotoTask();
            URL url1=new URL("https://www.google.com/imgres?imgurl=https%3A%2F%2Fimagesvc.timeincapp.com%2Fv3%2Fmm%2Fimage%3Furl%3Dhttps%253A%252F%252Ffortunedotcom.files.wordpress.com%252F2018%252F11%252Fgettyimages-1046001872.jpg%253Fw%253D1024%26w%3D100%26h%3D100%26c%3Dsc%26poi%3Dface%26q%3D85&imgrefurl=http%3A%2F%2Ffortune.com%2F2018%2F10%2F12%2Fhow-to-post-3d-pictures-on-facebook%2F&docid=zVws-veOchVlPM&tbnid=jPAE5_1Rt1PDFM%3A&vet=10ahUKEwiKi96N-tjeAhWDWysKHS43DN8QMwhqKAEwAQ..i&w=100&h=100&safe=active&bih=648&biw=1304&q=image&ved=0ahUKEwiKi96N-tjeAhWDWysKHS43DN8QMwhqKAEwAQ&iact=mrc&uact=8");
            photoTask1.setimageURL(url1);
            photoTasks.add(photoTask1);
            PhotoTask photoTask2=   new PhotoTask();
            URL url2=new URL("https://www.google.com/imgres?imgurl=http%3A%2F%2Fwww.visitbala.org%2Fimages%2Fmade%2Fimages%2Fremote%2Fhttp_www.visitbala.org.uk%2Fuploads%2Fimage-galleries%2FBoofing_on_the_River_Tryweryn_Large_100_100_c1.JPG&imgrefurl=http%3A%2F%2Fwww.visitbala.org%2Fabout-the-area%2Fimage-gallery%2F&docid=92mydiGNiUzs3M&tbnid=SWWR1M4VDtsZfM%3A&vet=10ahUKEwiKi96N-tjeAhWDWysKHS43DN8QMwiNASgUMBQ..i&w=100&h=100&safe=active&bih=648&biw=1304&q=image&ved=0ahUKEwiKi96N-tjeAhWDWysKHS43DN8QMwiNASgUMBQ&iact=mrc&uact=8");
            photoTask2.setimageURL(url2);
            photoTasks.add(photoTask2);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        for (int i=0;i<3;i++) {
            PhotoManager.startDownload(photoTasks.get(i),false);
        }
    }
}
