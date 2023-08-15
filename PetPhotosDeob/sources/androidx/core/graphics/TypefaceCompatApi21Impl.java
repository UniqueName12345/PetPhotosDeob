package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.provider.FontsContractCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static final String TAG = "TypefaceCompatApi21Impl";

    private File getFile(ParcelFileDescriptor fd) {
        try {
            String path = Os.readlink("/proc/self/fd/" + fd.getFd());
            if (OsConstants.S_ISREG(Os.stat(path).st_mode)) {
                return new File(path);
            }
            return null;
        } catch (ErrnoException e) {
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fonts, int style) {
        if (fonts.length < 1) {
            return null;
        }
        FontsContractCompat.FontInfo bestFont = findBestInfo(fonts, style);
        ContentResolver resolver = context.getContentResolver();
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(bestFont.getUri(), "r", cancellationSignal);
            File file = getFile(pfd);
            if (file != null && file.canRead()) {
                Typeface createFromFile = Typeface.createFromFile(file);
                if (pfd != null) {
                    if (0 != 0) {
                        pfd.close();
                        return createFromFile;
                    }
                    pfd.close();
                    return createFromFile;
                }
                return createFromFile;
            }
            FileInputStream fis = new FileInputStream(pfd.getFileDescriptor());
            Throwable th = null;
            try {
                Typeface createFromInputStream = super.createFromInputStream(context, fis);
                if (fis != null) {
                    if (0 != 0) {
                        try {
                            fis.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    } else {
                        fis.close();
                    }
                }
                if (pfd != null) {
                    if (0 != 0) {
                        pfd.close();
                        return createFromInputStream;
                    }
                    pfd.close();
                    return createFromInputStream;
                }
                return createFromInputStream;
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    if (fis != null) {
                        if (th3 != null) {
                            try {
                                fis.close();
                            } catch (Throwable th5) {
                                th3.addSuppressed(th5);
                            }
                        } else {
                            fis.close();
                        }
                    }
                    throw th4;
                }
            }
        } catch (IOException e) {
            return null;
        }
    }
}
