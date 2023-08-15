package com.google.appinventor.components.runtime.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.provider.Contacts;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.VideoView;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.errors.PermissionException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class MediaUtil {
    private static final String LOG_TAG = "MediaUtil";
    private static final Map<String, File> tempFileMap = new HashMap();
    private static ConcurrentHashMap<String, String> pathCache = new ConcurrentHashMap<>(2);

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum MediaSource {
        ASSET,
        REPL_ASSET,
        SDCARD,
        FILE_URL,
        URL,
        CONTENT_URI,
        CONTACT_URI
    }

    private MediaUtil() {
    }

    static String fileUrlToFilePath(String mediaPath) throws IOException {
        try {
            return new File(new URL(mediaPath).toURI()).getAbsolutePath();
        } catch (IllegalArgumentException e) {
            throw new IOException("Unable to determine file path of file url " + mediaPath);
        } catch (Exception e2) {
            throw new IOException("Unable to determine file path of file url " + mediaPath);
        }
    }

    @SuppressLint({"SdCardPath"})
    private static MediaSource determineMediaSource(Form form, String mediaPath) {
        MediaSource mediaSource;
        if (mediaPath.startsWith(QUtil.getExternalStoragePath(form)) || mediaPath.startsWith("/sdcard/")) {
            return MediaSource.SDCARD;
        }
        if (mediaPath.startsWith("content://contacts/")) {
            return MediaSource.CONTACT_URI;
        }
        if (mediaPath.startsWith("content://")) {
            return MediaSource.CONTENT_URI;
        }
        try {
            URL url = new URL(mediaPath);
            if (mediaPath.startsWith("file:")) {
                if (url.getPath().startsWith("/android_asset/")) {
                    mediaSource = MediaSource.ASSET;
                } else {
                    mediaSource = MediaSource.FILE_URL;
                }
            } else {
                mediaSource = MediaSource.URL;
            }
            return mediaSource;
        } catch (MalformedURLException e) {
            if (form instanceof ReplForm) {
                if (((ReplForm) form).isAssetsLoaded()) {
                    return MediaSource.REPL_ASSET;
                }
                return MediaSource.ASSET;
            }
            return MediaSource.ASSET;
        }
    }

    @SuppressLint({"SdCardPath"})
    @Deprecated
    public static boolean isExternalFileUrl(String mediaPath) {
        Log.w(LOG_TAG, "Calling deprecated version of isExternalFileUrl", new IllegalAccessException());
        return mediaPath.startsWith(new StringBuilder().append("file://").append(QUtil.getExternalStoragePath(Form.getActiveForm())).toString()) || mediaPath.startsWith("file:///sdcard/");
    }

    @SuppressLint({"SdCardPath"})
    public static boolean isExternalFileUrl(Context context, String mediaPath) {
        if (Build.VERSION.SDK_INT >= 29) {
            return false;
        }
        return mediaPath.startsWith(new StringBuilder().append("file://").append(QUtil.getExternalStorageDir(context)).toString()) || mediaPath.startsWith("file:///sdcard");
    }

    @SuppressLint({"SdCardPath"})
    @Deprecated
    public static boolean isExternalFile(String mediaPath) {
        Log.w(LOG_TAG, "Calling deprecated version of isExternalFile", new IllegalAccessException());
        return mediaPath.startsWith(QUtil.getExternalStoragePath(Form.getActiveForm())) || mediaPath.startsWith("/sdcard/") || isExternalFileUrl(Form.getActiveForm(), mediaPath);
    }

    @SuppressLint({"SdCardPath"})
    public static boolean isExternalFile(Context context, String mediaPath) {
        if (Build.VERSION.SDK_INT >= 29) {
            return false;
        }
        return mediaPath.startsWith(QUtil.getExternalStoragePath(context)) || mediaPath.startsWith("/sdcard/") || isExternalFileUrl(context, mediaPath);
    }

    private static String findCaseinsensitivePath(Form form, String mediaPath) throws IOException {
        if (!pathCache.containsKey(mediaPath)) {
            String newPath = findCaseinsensitivePathWithoutCache(form, mediaPath);
            if (newPath == null) {
                return null;
            }
            pathCache.put(mediaPath, newPath);
        }
        return pathCache.get(mediaPath);
    }

    private static String findCaseinsensitivePathWithoutCache(Form form, String mediaPath) throws IOException {
        String[] mediaPathlist = form.getAssets().list("");
        int l = Array.getLength(mediaPathlist);
        for (int i = 0; i < l; i++) {
            String temp = mediaPathlist[i];
            if (temp.equalsIgnoreCase(mediaPath)) {
                return temp;
            }
        }
        return null;
    }

    private static InputStream getAssetsIgnoreCaseInputStream(Form form, String mediaPath) throws IOException {
        try {
            return form.getAssets().open(mediaPath);
        } catch (IOException e) {
            String path = findCaseinsensitivePath(form, mediaPath);
            if (path == null) {
                throw e;
            }
            return form.getAssets().open(path);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static InputStream openMedia(Form form, String mediaPath, MediaSource mediaSource) throws IOException {
        InputStream is;
        switch (mediaSource) {
            case ASSET:
                if (mediaPath.startsWith("file:")) {
                    mediaPath = mediaPath.substring(mediaPath.indexOf("/android_asset/") + "/android_asset/".length());
                }
                return getAssetsIgnoreCaseInputStream(form, mediaPath);
            case REPL_ASSET:
                if (RUtil.needsFilePermission(form, mediaPath, null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                return new FileInputStream(new File(URI.create(form.getAssetPath(mediaPath))));
            case SDCARD:
                if (RUtil.needsFilePermission(form, mediaPath, null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                return new FileInputStream(mediaPath);
            case FILE_URL:
                if (isExternalFileUrl(form, mediaPath) && RUtil.needsFilePermission(form, mediaPath, null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                    break;
                }
                break;
            case URL:
                break;
            case CONTENT_URI:
                return form.getContentResolver().openInputStream(Uri.parse(mediaPath));
            case CONTACT_URI:
                if (SdkLevel.getLevel() >= 12) {
                    is = HoneycombMR1Util.openContactPhotoInputStreamHelper(form.getContentResolver(), Uri.parse(mediaPath));
                } else {
                    is = Contacts.People.openContactPhotoInputStream(form.getContentResolver(), Uri.parse(mediaPath));
                }
                if (is != null) {
                    return is;
                }
                throw new IOException("Unable to open contact photo " + mediaPath + ".");
            default:
                throw new IOException("Unable to open media " + mediaPath + ".");
        }
        return new URL(mediaPath).openStream();
    }

    public static InputStream openMedia(Form form, String mediaPath) throws IOException {
        return openMedia(form, mediaPath, determineMediaSource(form, mediaPath));
    }

    public static File copyMediaToTempFile(Form form, String mediaPath) throws IOException {
        MediaSource mediaSource = determineMediaSource(form, mediaPath);
        return copyMediaToTempFile(form, mediaPath, mediaSource);
    }

    private static File copyMediaToTempFile(Form form, String mediaPath, MediaSource mediaSource) throws IOException {
        InputStream in = openMedia(form, mediaPath, mediaSource);
        File file = null;
        try {
            try {
                file = File.createTempFile("AI_Media_", null);
                file.deleteOnExit();
                FileUtil.writeStreamToFile(in, file.getAbsolutePath());
                return file;
            } catch (IOException e) {
                if (file != null) {
                    Log.e(LOG_TAG, "Could not copy media " + mediaPath + " to temp file " + file.getAbsolutePath());
                    file.delete();
                } else {
                    Log.e(LOG_TAG, "Could not copy media " + mediaPath + " to temp file.");
                }
                throw e;
            }
        } finally {
            in.close();
        }
    }

    private static File cacheMediaTempFile(Form form, String mediaPath, MediaSource mediaSource) throws IOException {
        File tempFile = tempFileMap.get(mediaPath);
        if (tempFile == null || !tempFile.exists()) {
            Log.i(LOG_TAG, "Copying media " + mediaPath + " to temp file...");
            File tempFile2 = copyMediaToTempFile(form, mediaPath, mediaSource);
            Log.i(LOG_TAG, "Finished copying media " + mediaPath + " to temp file " + tempFile2.getAbsolutePath());
            tempFileMap.put(mediaPath, tempFile2);
            return tempFile2;
        }
        return tempFile;
    }

    public static BitmapDrawable getBitmapDrawable(Form form, String mediaPath) throws IOException {
        if (mediaPath == null || mediaPath.length() == 0) {
            return null;
        }
        final Synchronizer<BitmapDrawable> syncer = new Synchronizer<>();
        AsyncCallbackPair<BitmapDrawable> continuation = new AsyncCallbackPair<BitmapDrawable>() { // from class: com.google.appinventor.components.runtime.util.MediaUtil.1
            @Override // com.google.appinventor.components.runtime.util.AsyncCallbackPair
            public void onFailure(String message) {
                Synchronizer.this.error(message);
            }

            @Override // com.google.appinventor.components.runtime.util.AsyncCallbackPair
            public void onSuccess(BitmapDrawable result) {
                Synchronizer.this.wakeup(result);
            }
        };
        getBitmapDrawableAsync(form, mediaPath, continuation);
        syncer.waitfor();
        BitmapDrawable result = syncer.getResult();
        if (result == null) {
            String error = syncer.getError();
            if (error.startsWith("PERMISSION_DENIED:")) {
                throw new PermissionException(error.split(":")[1]);
            }
            throw new IOException(error);
        }
        return result;
    }

    public static void getBitmapDrawableAsync(Form form, String mediaPath, AsyncCallbackPair<BitmapDrawable> continuation) {
        getBitmapDrawableAsync(form, mediaPath, -1, -1, continuation);
    }

    public static void getBitmapDrawableAsync(final Form form, final String mediaPath, final int desiredWidth, final int desiredHeight, final AsyncCallbackPair<BitmapDrawable> continuation) {
        if (mediaPath == null || mediaPath.length() == 0) {
            continuation.onSuccess(null);
            return;
        }
        final MediaSource mediaSource = determineMediaSource(form, mediaPath);
        Runnable loadImage = new Runnable() { // from class: com.google.appinventor.components.runtime.util.MediaUtil.2
            @Override // java.lang.Runnable
            public void run() {
                Log.d(MediaUtil.LOG_TAG, "mediaPath = " + mediaPath);
                InputStream is = null;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                try {
                    try {
                        try {
                            is = MediaUtil.openMedia(form, mediaPath, mediaSource);
                            while (true) {
                                int read = is.read(buf);
                                if (read <= 0) {
                                    break;
                                }
                                bos.write(buf, 0, read);
                            }
                            byte[] buf2 = bos.toByteArray();
                            if (is != null) {
                                try {
                                    is.close();
                                } catch (IOException e) {
                                    Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e);
                                }
                            }
                            try {
                                bos.close();
                            } catch (IOException e2) {
                            }
                            ByteArrayInputStream bis = new ByteArrayInputStream(buf2);
                            try {
                                try {
                                    bis.mark(buf2.length);
                                    BitmapFactory.Options options = MediaUtil.getBitmapOptions(form, bis, mediaPath);
                                    bis.reset();
                                    BitmapDrawable originalBitmapDrawable = new BitmapDrawable(form.getResources(), MediaUtil.decodeStream(bis, null, options));
                                    originalBitmapDrawable.setTargetDensity(form.getResources().getDisplayMetrics());
                                    boolean needsResize = desiredWidth > 0 && desiredHeight >= 0;
                                    if (!needsResize && (options.inSampleSize != 1 || form.deviceDensity() == 1.0f)) {
                                        continuation.onSuccess(originalBitmapDrawable);
                                        if (bis != null) {
                                            try {
                                                bis.close();
                                                return;
                                            } catch (IOException e3) {
                                                Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e3);
                                                return;
                                            }
                                        }
                                        return;
                                    }
                                    int scaledWidth = (int) ((desiredWidth > 0 ? desiredWidth : originalBitmapDrawable.getIntrinsicWidth()) * form.deviceDensity());
                                    int scaledHeight = (int) ((desiredHeight > 0 ? desiredHeight : originalBitmapDrawable.getIntrinsicHeight()) * form.deviceDensity());
                                    Log.d(MediaUtil.LOG_TAG, "form.deviceDensity() = " + form.deviceDensity());
                                    Log.d(MediaUtil.LOG_TAG, "originalBitmapDrawable.getIntrinsicWidth() = " + originalBitmapDrawable.getIntrinsicWidth());
                                    Log.d(MediaUtil.LOG_TAG, "originalBitmapDrawable.getIntrinsicHeight() = " + originalBitmapDrawable.getIntrinsicHeight());
                                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmapDrawable.getBitmap(), scaledWidth, scaledHeight, false);
                                    BitmapDrawable scaledBitmapDrawable = new BitmapDrawable(form.getResources(), scaledBitmap);
                                    scaledBitmapDrawable.setTargetDensity(form.getResources().getDisplayMetrics());
                                    System.gc();
                                    continuation.onSuccess(scaledBitmapDrawable);
                                    if (bis != null) {
                                        try {
                                            bis.close();
                                        } catch (IOException e4) {
                                            Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e4);
                                        }
                                    }
                                } catch (Exception e5) {
                                    Log.w(MediaUtil.LOG_TAG, "Exception while loading media.", e5);
                                    continuation.onFailure(e5.getMessage());
                                    if (bis != null) {
                                        try {
                                            bis.close();
                                        } catch (IOException e6) {
                                            Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e6);
                                        }
                                    }
                                }
                            } catch (Throwable th) {
                                if (bis != null) {
                                    try {
                                        bis.close();
                                    } catch (IOException e7) {
                                        Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e7);
                                    }
                                }
                                throw th;
                            }
                        } catch (IOException e8) {
                            if (mediaSource == MediaSource.CONTACT_URI) {
                                BitmapDrawable drawable = new BitmapDrawable(form.getResources(), BitmapFactory.decodeResource(form.getResources(), 17301606, null));
                                continuation.onSuccess(drawable);
                                if (is != null) {
                                    try {
                                        is.close();
                                    } catch (IOException e9) {
                                        Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e9);
                                    }
                                }
                                try {
                                    bos.close();
                                } catch (IOException e10) {
                                }
                                return;
                            }
                            Log.d(MediaUtil.LOG_TAG, "IOException reading file.", e8);
                            continuation.onFailure(e8.getMessage());
                            if (is != null) {
                                try {
                                    is.close();
                                } catch (IOException e11) {
                                    Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e11);
                                }
                            }
                            try {
                                bos.close();
                            } catch (IOException e12) {
                            }
                        }
                    } catch (PermissionException e13) {
                        continuation.onFailure("PERMISSION_DENIED:" + e13.getPermissionNeeded());
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e14) {
                                Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e14);
                            }
                        }
                        try {
                            bos.close();
                        } catch (IOException e15) {
                        }
                    }
                } catch (Throwable th2) {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e16) {
                            Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e16);
                        }
                    }
                    try {
                        bos.close();
                    } catch (IOException e17) {
                    }
                    throw th2;
                }
            }
        };
        AsynchUtil.runAsynchronously(loadImage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bitmap decodeStream(InputStream is, Rect outPadding, BitmapFactory.Options opts) {
        return BitmapFactory.decodeStream(new FlushedInputStream(is), outPadding, opts);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0;
            while (totalBytesSkipped < n) {
                long bytesSkipped = this.in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0) {
                    if (read() < 0) {
                        break;
                    }
                    bytesSkipped = 1;
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static BitmapFactory.Options getBitmapOptions(Form form, InputStream is, String mediaPath) {
        int maxWidth;
        int maxHeight;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeStream(is, null, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        Display display = ((WindowManager) form.getSystemService("window")).getDefaultDisplay();
        if (Form.getCompatibilityMode()) {
            maxWidth = 720;
            maxHeight = 840;
        } else {
            maxWidth = (int) (display.getWidth() / form.deviceDensity());
            maxHeight = (int) (display.getHeight() / form.deviceDensity());
        }
        int sampleSize = 1;
        while (imageWidth / sampleSize > maxWidth && imageHeight / sampleSize > maxHeight) {
            sampleSize *= 2;
        }
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        Log.d(LOG_TAG, "getBitmapOptions: sampleSize = " + sampleSize + " mediaPath = " + mediaPath + " maxWidth = " + maxWidth + " maxHeight = " + maxHeight + " display width = " + display.getWidth() + " display height = " + display.getHeight());
        options2.inSampleSize = sampleSize;
        return options2;
    }

    private static AssetFileDescriptor getAssetsIgnoreCaseAfd(Form form, String mediaPath) throws IOException {
        try {
            return form.getAssets().openFd(mediaPath);
        } catch (IOException e) {
            String path = findCaseinsensitivePath(form, mediaPath);
            if (path == null) {
                throw e;
            }
            return form.getAssets().openFd(path);
        }
    }

    public static int loadSoundPool(SoundPool soundPool, Form form, String mediaPath) throws IOException {
        MediaSource mediaSource = determineMediaSource(form, mediaPath);
        switch (mediaSource) {
            case ASSET:
                return soundPool.load(getAssetsIgnoreCaseAfd(form, mediaPath), 1);
            case REPL_ASSET:
                if (RUtil.needsFilePermission(form, mediaPath, null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                return soundPool.load(fileUrlToFilePath(form.getAssetPath(mediaPath)), 1);
            case SDCARD:
                if (RUtil.needsFilePermission(form, mediaPath, null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                return soundPool.load(mediaPath, 1);
            case FILE_URL:
                if (isExternalFileUrl(form, mediaPath) || RUtil.needsFilePermission(form, mediaPath, null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                return soundPool.load(fileUrlToFilePath(mediaPath), 1);
            case URL:
            case CONTENT_URI:
                File tempFile = cacheMediaTempFile(form, mediaPath, mediaSource);
                return soundPool.load(tempFile.getAbsolutePath(), 1);
            case CONTACT_URI:
                throw new IOException("Unable to load audio for contact " + mediaPath + ".");
            default:
                throw new IOException("Unable to load audio " + mediaPath + ".");
        }
    }

    public static void loadMediaPlayer(MediaPlayer mediaPlayer, Form form, String mediaPath) throws IOException {
        MediaSource mediaSource = determineMediaSource(form, mediaPath);
        switch (mediaSource) {
            case ASSET:
                AssetFileDescriptor afd = getAssetsIgnoreCaseAfd(form, mediaPath);
                try {
                    FileDescriptor fd = afd.getFileDescriptor();
                    long offset = afd.getStartOffset();
                    long length = afd.getLength();
                    mediaPlayer.setDataSource(fd, offset, length);
                    return;
                } finally {
                    afd.close();
                }
            case REPL_ASSET:
                if (RUtil.needsFilePermission(form, mediaPath, null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                mediaPlayer.setDataSource(fileUrlToFilePath(form.getAssetPath(mediaPath)));
                return;
            case SDCARD:
                if (RUtil.needsFilePermission(form, mediaPath, null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                mediaPlayer.setDataSource(mediaPath);
                return;
            case FILE_URL:
                if (isExternalFileUrl(form, mediaPath) || RUtil.needsFilePermission(form, mediaPath, null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                mediaPlayer.setDataSource(fileUrlToFilePath(mediaPath));
                return;
            case URL:
                mediaPlayer.setDataSource(mediaPath);
                return;
            case CONTENT_URI:
                mediaPlayer.setDataSource(form, Uri.parse(mediaPath));
                return;
            case CONTACT_URI:
                throw new IOException("Unable to load audio or video for contact " + mediaPath + ".");
            default:
                throw new IOException("Unable to load audio or video " + mediaPath + ".");
        }
    }

    public static void loadVideoView(VideoView videoView, Form form, String mediaPath) throws IOException {
        MediaSource mediaSource = determineMediaSource(form, mediaPath);
        switch (mediaSource) {
            case ASSET:
            case URL:
                File tempFile = cacheMediaTempFile(form, mediaPath, mediaSource);
                videoView.setVideoPath(tempFile.getAbsolutePath());
                return;
            case REPL_ASSET:
                if (RUtil.needsFilePermission(form, mediaPath, null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                videoView.setVideoPath(fileUrlToFilePath(form.getAssetPath(mediaPath)));
                return;
            case SDCARD:
                if (RUtil.needsFilePermission(form, mediaPath, null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                videoView.setVideoPath(mediaPath);
                return;
            case FILE_URL:
                if (isExternalFileUrl(form, mediaPath) || RUtil.needsFilePermission(form, mediaPath, null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                videoView.setVideoPath(fileUrlToFilePath(mediaPath));
                return;
            case CONTENT_URI:
                videoView.setVideoURI(Uri.parse(mediaPath));
                return;
            case CONTACT_URI:
                throw new IOException("Unable to load video for contact " + mediaPath + ".");
            default:
                throw new IOException("Unable to load video " + mediaPath + ".");
        }
    }
}
