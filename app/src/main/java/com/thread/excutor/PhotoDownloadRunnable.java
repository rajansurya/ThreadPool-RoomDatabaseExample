/*
 * Copyright (C) ${year} The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thread.excutor;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class PhotoDownloadRunnable implements Runnable {
    // Sets the size for each read action (bytes)
    private static final int READ_SIZE = 1024 * 2;

    // Sets a tag for this class
    @SuppressWarnings("unused")
    private static final String LOG_TAG = "PhotoDownloadRunnable";

    // Constants for indicating the state of the download
    static final int HTTP_STATE_FAILED = -1;
    static final int HTTP_STATE_STARTED = 0;
    static final int HTTP_STATE_COMPLETED = 1;

    // Defines a field that contains the calling object of type PhotoTask.
    final TaskRunnableDownloadMethods mPhotoTask;

    /**
     * An interface that defines methods that PhotoTask implements. An instance of
     * PhotoTask passes itself to an PhotoDownloadRunnable instance through the
     * PhotoDownloadRunnable constructor, after which the two instances can access each other's
     * variables.
     */
    interface TaskRunnableDownloadMethods {

        /**
         * Sets the Thread that this instance is running on
         *
         * @param currentThread the current Thread
         */
        void setDownloadThread(Thread currentThread);

        /**
         * Returns the current contents of the download buffer
         *
         * @return The byte array downloaded from the URL in the last read
         */
        StringBuilder getByteBuffer();

        /**
         * Sets the current contents of the download buffer
         *
         * @param buffer The bytes that were just read
         */
        void setByteBuffer(StringBuilder buffer);

        /**
         * Defines the actions for each state of the PhotoTask instance.
         *
         * @param state The current state of the task
         */
        void handleDownloadState(int state);

        /**
         * Gets the URL for the image being downloaded
         *
         * @return The image URL
         */
        URL getImageURL();
        void setimageURL(URL var);
    }

    /**
     * This constructor creates an instance of PhotoDownloadRunnable and stores in it a reference
     * to the PhotoTask instance that instantiated it.
     *
     * @param photoTask The PhotoTask, which implements TaskRunnableDecodeMethods
     */
    PhotoDownloadRunnable(TaskRunnableDownloadMethods photoTask) {
        mPhotoTask = photoTask;
    }

    /*
     * Defines this object's task, which is a set of instructions designed to be run on a Thread.
     */
    @SuppressWarnings("resource")
    @Override
    public void run() {

        /*
         * Stores the current Thread in the the PhotoTask instance, so that the instance
         * can interrupt the Thread.
         */
        mPhotoTask.setDownloadThread(Thread.currentThread());

        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        /*
         * Gets the image cache buffer object from the PhotoTask instance. This makes the
         * to both PhotoDownloadRunnable and PhotoTask.
         */
//        byte[] byteBuffer = mPhotoTask.getByteBuffer();

        /*
         * A try block that downloads a Picasa image from a URL. The URL value is in the field
         * PhotoTask.mImageURL
         */
        // Tries to download the picture from Picasa
        try {
            // Before continuing, checks to see that the Thread hasn't been
            // interrupted
            if (Thread.interrupted()) {

                throw new InterruptedException();
            }
            StringBuilder total=null;
            // If there's no cache buffer for this image
//            if (null == byteBuffer) {



                // Defines a handle for the byte download stream
                InputStream byteStream = null;

                // Downloads the image and catches IO errors
                try {
                    System.out.println("mPhotoTask.getImageURL() "+mPhotoTask.getImageURL());
                    // Opens an HTTP connection to the image's URL
                    HttpURLConnection httpConn = (HttpURLConnection) mPhotoTask.getImageURL().openConnection();

                    // Sets the user agent to report to the server
//                    httpConn.setRequestProperty("User-Agent", Constants.USER_AGENT);

                    // Before continuing, checks to see that the Thread
                    // hasn't been interrupted

                    if (Thread.interrupted()) {

                        throw new InterruptedException();
                    }
                    // Gets the input stream containing the image
                    byteStream = httpConn.getInputStream();

                    if (Thread.interrupted()) {

                        throw new InterruptedException();
                    }
                    /*
                     * Gets the size of the file being downloaded. This
                     * may or may not be returned.
                     */
//                    int contentSize = httpConn.getContentLength();
                    BufferedReader r = new BufferedReader(new InputStreamReader(byteStream));
                     total = new StringBuilder();
                    for (String line; (line = r.readLine()) != null; ) {
                        total.append(line).append('\n');
                    }


                    if (Thread.interrupted()) {

                        throw new InterruptedException();
                    }

                    // If an IO error occurs, returns immediately
                } catch (IOException e) {
                    e.printStackTrace();
                    return;

                    /*
                     * If the input stream is still open, close it
                     */
                } finally {
                    if (null != byteStream) {
                        try {
                            byteStream.close();
                        } catch (Exception e) {

                        }
                    }
                }
//            }

            /*
             * Stores the downloaded bytes in the byte buffer in the PhotoTask instance.
             */
            mPhotoTask.setByteBuffer(total);

            /*
             * Sets the status message in the PhotoTask instance. This sets the
             * ImageView background to indicate that the image is being
             * decoded.
             */
            mPhotoTask.handleDownloadState(HTTP_STATE_COMPLETED);

            // Catches exceptions thrown in response to a queued interrupt
        } catch (InterruptedException e1) {

            // Does nothing

            // In all cases, handle the results
        } finally {


            // Sets the reference to the current Thread to null, releasing its storage
            mPhotoTask.setDownloadThread(null);

            // Clears the Thread's interrupt flag
            Thread.interrupted();
        }
    }
}

