package gnu.text;

import gnu.bytecode.Access;
import gnu.lists.FString;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/* loaded from: classes.dex */
public class URIPath extends Path implements Comparable<URIPath> {
    final URI uri;

    /* JADX INFO: Access modifiers changed from: package-private */
    public URIPath(URI uri) {
        this.uri = uri;
    }

    public static URIPath coerceToURIPathOrNull(Object path) {
        String str;
        if (path instanceof URIPath) {
            return (URIPath) path;
        }
        if (path instanceof URL) {
            return URLPath.valueOf((URL) path);
        }
        if (path instanceof URI) {
            return valueOf((URI) path);
        }
        if ((path instanceof File) || (path instanceof Path) || (path instanceof FString)) {
            str = path.toString();
        } else if (path instanceof String) {
            str = (String) path;
        } else {
            return null;
        }
        return valueOf(str);
    }

    public static URIPath makeURI(Object arg) {
        URIPath path = coerceToURIPathOrNull(arg);
        if (path == null) {
            throw new WrongType((String) null, -4, arg, "URI");
        }
        return path;
    }

    public static URIPath valueOf(URI uri) {
        return new URIPath(uri);
    }

    public static URIPath valueOf(String uri) {
        try {
            return new URIStringPath(new URI(encodeForUri(uri, Access.INNERCLASS_CONTEXT)), uri);
        } catch (Throwable ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }

    @Override // gnu.text.Path
    public boolean isAbsolute() {
        return this.uri.isAbsolute();
    }

    @Override // gnu.text.Path
    public boolean exists() {
        boolean z = true;
        try {
            URLConnection conn = toURL().openConnection();
            if (conn instanceof HttpURLConnection) {
                if (((HttpURLConnection) conn).getResponseCode() != 200) {
                    z = false;
                }
            } else if (conn.getLastModified() == 0) {
                z = false;
            }
            return z;
        } catch (Throwable th) {
            return false;
        }
    }

    @Override // gnu.text.Path
    public long getLastModified() {
        return URLPath.getLastModified(toURL());
    }

    @Override // gnu.text.Path
    public long getContentLength() {
        return URLPath.getContentLength(toURL());
    }

    @Override // gnu.text.Path
    public URI toUri() {
        return this.uri;
    }

    @Override // gnu.text.Path
    public String toURIString() {
        return this.uri.toString();
    }

    @Override // gnu.text.Path
    public Path resolve(String rstr) {
        if (Path.uriSchemeSpecified(rstr)) {
            return valueOf(rstr);
        }
        char fileSep = File.separatorChar;
        if (fileSep != '/') {
            if (rstr.length() >= 2 && ((rstr.charAt(1) == ':' && Character.isLetter(rstr.charAt(0))) || (rstr.charAt(0) == fileSep && rstr.charAt(1) == fileSep))) {
                return FilePath.valueOf(new File(rstr));
            }
            rstr = rstr.replace(fileSep, '/');
        }
        try {
            URI resolved = this.uri.resolve(new URI(null, rstr, null));
            return valueOf(resolved);
        } catch (Throwable ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(URIPath path) {
        return this.uri.compareTo(path.uri);
    }

    public boolean equals(Object obj) {
        return (obj instanceof URIPath) && this.uri.equals(((URIPath) obj).uri);
    }

    public int hashCode() {
        return this.uri.hashCode();
    }

    public String toString() {
        return toURIString();
    }

    @Override // gnu.text.Path
    public URL toURL() {
        return Path.toURL(this.uri.toString());
    }

    @Override // gnu.text.Path
    public InputStream openInputStream() throws IOException {
        return URLPath.openInputStream(toURL());
    }

    @Override // gnu.text.Path
    public OutputStream openOutputStream() throws IOException {
        return URLPath.openOutputStream(toURL());
    }

    @Override // gnu.text.Path
    public String getScheme() {
        return this.uri.getScheme();
    }

    @Override // gnu.text.Path
    public String getHost() {
        return this.uri.getHost();
    }

    @Override // gnu.text.Path
    public String getAuthority() {
        return this.uri.getAuthority();
    }

    @Override // gnu.text.Path
    public String getUserInfo() {
        return this.uri.getUserInfo();
    }

    @Override // gnu.text.Path
    public int getPort() {
        return this.uri.getPort();
    }

    @Override // gnu.text.Path
    public String getPath() {
        return this.uri.getPath();
    }

    @Override // gnu.text.Path
    public String getQuery() {
        return this.uri.getQuery();
    }

    @Override // gnu.text.Path
    public String getFragment() {
        return this.uri.getFragment();
    }

    @Override // gnu.text.Path
    public Path getCanonical() {
        if (isAbsolute()) {
            URI norm = this.uri.normalize();
            if (norm != this.uri) {
                return valueOf(norm);
            }
            return this;
        }
        return getAbsolute().getCanonical();
    }

    public static String encodeForUri(String str, char mode) {
        int i;
        int b;
        StringBuffer sbuf = new StringBuffer();
        int len = str.length();
        for (int i2 = 0; i2 < len; i2 = i) {
            i = i2 + 1;
            int ch = str.charAt(i2);
            if (ch >= 55296 && ch < 56320 && i < len) {
                ch = ((ch - 55296) * 1024) + (str.charAt(i) - 56320) + 65536;
                i++;
            }
            if (mode != 'H' ? !((ch < 97 || ch > 122) && ((ch < 65 || ch > 90) && ((ch < 48 || ch > 57) && ch != 45 && ch != 95 && ch != 46 && ch != 126 && (mode != 'I' || (ch != 59 && ch != 47 && ch != 63 && ch != 58 && ch != 42 && ch != 39 && ch != 40 && ch != 41 && ch != 64 && ch != 38 && ch != 61 && ch != 43 && ch != 36 && ch != 44 && ch != 91 && ch != 93 && ch != 35 && ch != 33 && ch != 37))))) : !(ch < 32 || ch > 126)) {
                sbuf.append((char) ch);
            } else {
                int pos = sbuf.length();
                int nbytes = 0;
                if (ch >= 128 && ch >= 2048 && ch < 65536) {
                }
                do {
                    int availbits = nbytes == 0 ? 7 : 6 - nbytes;
                    if (ch < (1 << availbits)) {
                        b = ch;
                        if (nbytes > 0) {
                            b |= (65408 >> nbytes) & 255;
                        }
                        ch = 0;
                    } else {
                        b = (ch & 63) | 128;
                        ch >>= 6;
                    }
                    nbytes++;
                    for (int j = 0; j <= 1; j++) {
                        int hex = b & 15;
                        sbuf.insert(pos, (char) (hex <= 9 ? hex + 48 : (hex - 10) + 65));
                        b >>= 4;
                    }
                    sbuf.insert(pos, '%');
                } while (ch != 0);
            }
        }
        return sbuf.toString();
    }
}
