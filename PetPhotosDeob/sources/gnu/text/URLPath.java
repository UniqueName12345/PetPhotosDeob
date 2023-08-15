package gnu.text;

import gnu.mapping.WrappedException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/* loaded from: classes.dex */
public class URLPath extends URIPath {
    final URL url;

    URLPath(URL url) {
        super(toUri(url));
        this.url = url;
    }

    public static URLPath valueOf(URL url) {
        return new URLPath(url);
    }

    @Override // gnu.text.URIPath, gnu.text.Path
    public boolean isAbsolute() {
        return true;
    }

    @Override // gnu.text.URIPath, gnu.text.Path
    public long getLastModified() {
        return getLastModified(this.url);
    }

    public static long getLastModified(URL url) {
        try {
            return url.openConnection().getLastModified();
        } catch (Throwable th) {
            return 0L;
        }
    }

    @Override // gnu.text.URIPath, gnu.text.Path
    public long getContentLength() {
        return getContentLength(this.url);
    }

    public static int getContentLength(URL url) {
        try {
            return url.openConnection().getContentLength();
        } catch (Throwable th) {
            return -1;
        }
    }

    @Override // gnu.text.URIPath, gnu.text.Path
    public URL toURL() {
        return this.url;
    }

    public static URI toUri(URL url) {
        try {
            return url.toURI();
        } catch (Throwable ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }

    @Override // gnu.text.URIPath, gnu.text.Path
    public URI toUri() {
        return toUri(this.url);
    }

    @Override // gnu.text.URIPath, gnu.text.Path
    public String toURIString() {
        return this.url.toString();
    }

    @Override // gnu.text.URIPath, gnu.text.Path
    public Path resolve(String relative) {
        try {
            return valueOf(new URL(this.url, relative));
        } catch (Throwable ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }

    public static InputStream openInputStream(URL url) throws IOException {
        return url.openConnection().getInputStream();
    }

    @Override // gnu.text.URIPath, gnu.text.Path
    public InputStream openInputStream() throws IOException {
        return openInputStream(this.url);
    }

    public static OutputStream openOutputStream(URL url) throws IOException {
        String str = url.toString();
        if (str.startsWith("file:")) {
            try {
                return new FileOutputStream(new File(new URI(str)));
            } catch (Throwable th) {
            }
        }
        URLConnection conn = url.openConnection();
        conn.setDoInput(false);
        conn.setDoOutput(true);
        return conn.getOutputStream();
    }

    @Override // gnu.text.URIPath, gnu.text.Path
    public OutputStream openOutputStream() throws IOException {
        return openOutputStream(this.url);
    }

    public static URLPath classResourcePath(Class clas) {
        URL url;
        try {
            try {
                url = ResourceStreamHandler.makeURL(clas);
            } catch (SecurityException e) {
                String classFileName = clas.getName().replace('.', '/') + ".class";
                url = clas.getClassLoader().getResource(classFileName);
            }
            return valueOf(url);
        } catch (Throwable ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }
}
