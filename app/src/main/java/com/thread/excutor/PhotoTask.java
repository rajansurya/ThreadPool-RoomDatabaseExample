/*
 * Copyright (C) 2012 The Android Open Source Project
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

import com.thread.excutor.PhotoDownloadRunnable.TaskRunnableDownloadMethods;

import java.net.URL;

public class PhotoTask implements TaskRunnableDownloadMethods {


    // The image's URL
    private URL mImageURL;

    private Runnable mDownloadRunnable;
//    private Runnable mDecodeRunnable;

    // A buffer for containing the bytes that make up the image
    StringBuilder mImageBuffer;

    // The decoded image
//    private Bitmap mDecodedImage;
    /*
     * Field containing the Thread this task is running on.
     */
    Thread mThreadThis;

    // The Thread on which this task is currently running.
    private Thread mCurrentThread;

    /*
     * An object that contains the ThreadPool singleton.
     */
    private static PhotoManager sPhotoManager;

    /**
     * Creates an PhotoTask containing a download object and a decoder object.
     */
    PhotoTask() {
        // Create the runnables
        mDownloadRunnable = new PhotoDownloadRunnable(this);
        sPhotoManager = PhotoManager.getInstance();
    }

    /**
     * Initializes the Task
     *
     * @param photoManager A ThreadPool object
     * @param cacheFlag    Whether caching is enabled
     */
    void initializeDownloaderTask(PhotoManager photoManager, boolean cacheFlag) {
        // Sets this object's ThreadPool field to be the input argument
        sPhotoManager = photoManager;


    }

    // Implements HTTPDownloaderRunnable.getByteBuffer
    @Override
    public StringBuilder getByteBuffer() {

        // Returns the global field
        return mImageBuffer;
    }

    /**
     * Recycles an PhotoTask object before it's put back into the pool. One reason to do
     * this is to avoid memory leaks.
     */
    void recycle() {

        // Releases references to the byte buffer and the BitMap
        mImageBuffer = null;
//        mDecodedImage = null;
    }


    // Implements PhotoDownloadRunnable.getImageURL. Returns the global Image URL.
    @Override
    public URL getImageURL() {
        return mImageURL;
    }

    @Override
    public void setimageURL(URL var) {
        mImageURL = var;
    }


    // Implements PhotoDownloadRunnable.setByteBuffer. Sets the image buffer to a buffer object.
    @Override
    public void setByteBuffer(StringBuilder imageBuffer) {
        mImageBuffer = imageBuffer;
    }

    // Delegates handling the current state of the task to the PhotoManager object
    void handleState(int state) {
        sPhotoManager.handleState(this, state);
    }


    // Returns the instance that downloaded the image
    Runnable getHTTPDownloadRunnable() {
        return mDownloadRunnable;
    }


    /*
     * Returns the Thread that this Task is running on. The method must first get a lock on a
     * static field, in this case the ThreadPool singleton. The lock is needed because the
     * Thread object reference is stored in the Thread object itself, and that object can be
     * changed by processes outside of this app.
     */
    public Thread getCurrentThread() {
        synchronized (sPhotoManager) {
            return mCurrentThread;
        }
    }

    /*
     * Sets the identifier for the current Thread. This must be a synchronized operation; see the
     * notes for getCurrentThread()
     */
    public void setCurrentThread(Thread thread) {
        synchronized (sPhotoManager) {
            mCurrentThread = thread;
        }
    }

    // Implements ImageCoderRunnable.setImage(). Sets the Bitmap for the current image.

    // Implements PhotoDownloadRunnable.setHTTPDownloadThread(). Calls setCurrentThread().
    @Override
    public void setDownloadThread(Thread currentThread) {
        setCurrentThread(currentThread);
    }

    /*
     * Implements PhotoDownloadRunnable.handleHTTPState(). Passes the download state to the
     * ThreadPool object.
     */

    @Override
    public void handleDownloadState(int state) {
        int outState;

        // Converts the download state to the overall state
        switch (state) {
            case PhotoDownloadRunnable.HTTP_STATE_COMPLETED:
                outState = PhotoManager.DOWNLOAD_COMPLETE;
                break;
            case PhotoDownloadRunnable.HTTP_STATE_FAILED:
                outState = PhotoManager.DOWNLOAD_FAILED;
                break;
            default:
                outState = PhotoManager.DOWNLOAD_STARTED;
                break;
        }
        // Passes the state to the ThreadPool object.
        handleState(outState);
    }


}
