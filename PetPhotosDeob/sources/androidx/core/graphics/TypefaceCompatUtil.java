package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.os.StrictMode;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    @Nullable
    public static File getTempFile(Context context) {
        String prefix = CACHE_FILE_PREFIX + Process.myPid() + "-" + Process.myTid() + "-";
        for (int i = 0; i < 100; i++) {
            File file = new File(context.getCacheDir(), prefix + i);
            if (file.createNewFile()) {
                return file;
            }
        }
        return null;
    }

    @Nullable
    @RequiresApi(19)
    private static ByteBuffer mmap(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileChannel channel = fis.getChannel();
            long size = channel.size();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, size);
            if (fis != null) {
                if (0 != 0) {
                    fis.close();
                } else {
                    fis.close();
                }
            }
            return map;
        } catch (IOException e) {
            return null;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Found unreachable blocks
        	at jadx.core.dex.visitors.blocks.DominatorTree.sortBlocks(DominatorTree.java:35)
        	at jadx.core.dex.visitors.blocks.DominatorTree.compute(DominatorTree.java:25)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.computeDominators(BlockProcessor.java:202)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:45)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    @androidx.annotation.Nullable
    @androidx.annotation.RequiresApi(19)
    public static java.nio.ByteBuffer mmap(android.content.Context r13, android.os.CancellationSignal r14, android.net.Uri r15) {
        /*
            android.content.ContentResolver r9 = r13.getContentResolver()
            java.lang.String r1 = "r"
            android.os.ParcelFileDescriptor r8 = r9.openFileDescriptor(r15, r1, r14)     // Catch: java.io.IOException -> L1b
            r11 = 0
            if (r8 != 0) goto L22
            r1 = 0
            if (r8 == 0) goto L15
            if (r11 == 0) goto L1e
            r8.close()     // Catch: java.lang.Throwable -> L16
        L15:
            return r1
        L16:
            r2 = move-exception
            r11.addSuppressed(r2)     // Catch: java.io.IOException -> L1b
            goto L15
        L1b:
            r6 = move-exception
            r1 = 0
            goto L15
        L1e:
            r8.close()     // Catch: java.io.IOException -> L1b
            goto L15
        L22:
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L55
            java.io.FileDescriptor r1 = r8.getFileDescriptor()     // Catch: java.lang.Throwable -> L55
            r7.<init>(r1)     // Catch: java.lang.Throwable -> L55
            r10 = 0
            java.nio.channels.FileChannel r0 = r7.getChannel()     // Catch: java.lang.Throwable -> L6e
            long r4 = r0.size()     // Catch: java.lang.Throwable -> L6e
            java.nio.channels.FileChannel$MapMode r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch: java.lang.Throwable -> L6e
            r2 = 0
            java.nio.MappedByteBuffer r1 = r0.map(r1, r2, r4)     // Catch: java.lang.Throwable -> L6e
            if (r7 == 0) goto L43
            if (r10 == 0) goto L63
            r7.close()     // Catch: java.lang.Throwable -> L50
        L43:
            if (r8 == 0) goto L15
            if (r11 == 0) goto L6a
            r8.close()     // Catch: java.lang.Throwable -> L4b
            goto L15
        L4b:
            r2 = move-exception
            r11.addSuppressed(r2)     // Catch: java.io.IOException -> L1b
            goto L15
        L50:
            r2 = move-exception
            r10.addSuppressed(r2)     // Catch: java.lang.Throwable -> L55
            goto L43
        L55:
            r1 = move-exception
            throw r1     // Catch: java.lang.Throwable -> L57
        L57:
            r2 = move-exception
            r12 = r2
            r2 = r1
            r1 = r12
        L5b:
            if (r8 == 0) goto L62
            if (r2 == 0) goto L8a
            r8.close()     // Catch: java.lang.Throwable -> L85
        L62:
            throw r1     // Catch: java.io.IOException -> L1b
        L63:
            r7.close()     // Catch: java.lang.Throwable -> L55
            goto L43
        L67:
            r1 = move-exception
            r2 = r11
            goto L5b
        L6a:
            r8.close()     // Catch: java.io.IOException -> L1b
            goto L15
        L6e:
            r1 = move-exception
            throw r1     // Catch: java.lang.Throwable -> L70
        L70:
            r2 = move-exception
            r12 = r2
            r2 = r1
            r1 = r12
        L74:
            if (r7 == 0) goto L7b
            if (r2 == 0) goto L81
            r7.close()     // Catch: java.lang.Throwable -> L7c
        L7b:
            throw r1     // Catch: java.lang.Throwable -> L55
        L7c:
            r3 = move-exception
            r2.addSuppressed(r3)     // Catch: java.lang.Throwable -> L55
            goto L7b
        L81:
            r7.close()     // Catch: java.lang.Throwable -> L55
            goto L7b
        L85:
            r3 = move-exception
            r2.addSuppressed(r3)     // Catch: java.io.IOException -> L1b
            goto L62
        L8a:
            r8.close()     // Catch: java.io.IOException -> L1b
            goto L62
        L8e:
            r1 = move-exception
            r2 = r10
            goto L74
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompatUtil.mmap(android.content.Context, android.os.CancellationSignal, android.net.Uri):java.nio.ByteBuffer");
    }

    @Nullable
    @RequiresApi(19)
    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        ByteBuffer byteBuffer = null;
        File tmpFile = getTempFile(context);
        if (tmpFile != null) {
            try {
                if (copyToFile(tmpFile, res, id)) {
                    byteBuffer = mmap(tmpFile);
                }
            } finally {
                tmpFile.delete();
            }
        }
        return byteBuffer;
    }

    public static boolean copyToFile(File file, InputStream is) {
        FileOutputStream os = null;
        StrictMode.ThreadPolicy old = StrictMode.allowThreadDiskWrites();
        try {
            try {
                FileOutputStream os2 = new FileOutputStream(file, false);
                try {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int readLen = is.read(buffer);
                        if (readLen == -1) {
                            closeQuietly(os2);
                            StrictMode.setThreadPolicy(old);
                            return true;
                        }
                        os2.write(buffer, 0, readLen);
                    }
                } catch (IOException e) {
                    e = e;
                    os = os2;
                    Log.e(TAG, "Error copying resource contents to temp file: " + e.getMessage());
                    closeQuietly(os);
                    StrictMode.setThreadPolicy(old);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    os = os2;
                    closeQuietly(os);
                    StrictMode.setThreadPolicy(old);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e2) {
            e = e2;
        }
    }

    public static boolean copyToFile(File file, Resources res, int id) {
        InputStream is = null;
        try {
            is = res.openRawResource(id);
            return copyToFile(file, is);
        } finally {
            closeQuietly(is);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
