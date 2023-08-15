package gnu.text;

import gnu.lists.FString;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

/* loaded from: classes.dex */
public class FilePath extends Path implements Comparable<FilePath> {
    final File file;
    final String path;

    private FilePath(File file) {
        this.file = file;
        this.path = file.toString();
    }

    private FilePath(File file, String path) {
        this.file = file;
        this.path = path;
    }

    public static FilePath valueOf(String str) {
        return new FilePath(new File(str), str);
    }

    public static FilePath valueOf(File file) {
        return new FilePath(file);
    }

    public static FilePath coerceToFilePathOrNull(Object path) {
        String str;
        if (path instanceof FilePath) {
            return (FilePath) path;
        }
        if (path instanceof URIPath) {
            return valueOf(new File(((URIPath) path).uri));
        }
        if (path instanceof URI) {
            return valueOf(new File((URI) path));
        }
        if (path instanceof File) {
            return valueOf((File) path);
        }
        if (path instanceof FString) {
            str = path.toString();
        } else if (path instanceof String) {
            str = (String) path;
        } else {
            return null;
        }
        return valueOf(str);
    }

    public static FilePath makeFilePath(Object arg) {
        FilePath path = coerceToFilePathOrNull(arg);
        if (path == null) {
            throw new WrongType((String) null, -4, arg, "filepath");
        }
        return path;
    }

    @Override // gnu.text.Path
    public boolean isAbsolute() {
        return this == Path.userDirPath || this.file.isAbsolute();
    }

    @Override // gnu.text.Path
    public boolean isDirectory() {
        int len;
        char last;
        if (this.file.isDirectory()) {
            return true;
        }
        return !this.file.exists() && (len = this.path.length()) > 0 && ((last = this.path.charAt(len + (-1))) == '/' || last == File.separatorChar);
    }

    @Override // gnu.text.Path
    public boolean delete() {
        return toFile().delete();
    }

    @Override // gnu.text.Path
    public long getLastModified() {
        return this.file.lastModified();
    }

    @Override // gnu.text.Path
    public boolean exists() {
        return this.file.exists();
    }

    @Override // gnu.text.Path
    public long getContentLength() {
        long length = this.file.length();
        if (length != 0 || this.file.exists()) {
            return length;
        }
        return -1L;
    }

    @Override // gnu.text.Path
    public String getPath() {
        return this.file.getPath();
    }

    @Override // gnu.text.Path
    public String getLast() {
        return this.file.getName();
    }

    @Override // gnu.text.Path
    public FilePath getParent() {
        File parent = this.file.getParentFile();
        if (parent == null) {
            return null;
        }
        return valueOf(parent);
    }

    @Override // java.lang.Comparable
    public int compareTo(FilePath path) {
        return this.file.compareTo(path.file);
    }

    public boolean equals(Object obj) {
        return (obj instanceof FilePath) && this.file.equals(((FilePath) obj).file);
    }

    public int hashCode() {
        return this.file.hashCode();
    }

    public String toString() {
        return this.path;
    }

    public File toFile() {
        return this.file;
    }

    @Override // gnu.text.Path
    public URL toURL() {
        if (this == Path.userDirPath) {
            return resolve("").toURL();
        }
        if (!isAbsolute()) {
            return getAbsolute().toURL();
        }
        try {
            return this.file.toURI().toURL();
        } catch (Throwable ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }

    private static URI toUri(File file) {
        try {
            if (file.isAbsolute()) {
                return file.toURI();
            }
            String fname = file.toString();
            char fileSep = File.separatorChar;
            if (fileSep != '/') {
                fname = fname.replace(fileSep, '/');
            }
            return new URI(null, null, fname, null);
        } catch (Throwable ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }

    @Override // gnu.text.Path
    public URI toUri() {
        return this == Path.userDirPath ? resolve("").toURI() : toUri(this.file);
    }

    @Override // gnu.text.Path
    public InputStream openInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override // gnu.text.Path
    public OutputStream openOutputStream() throws IOException {
        return new FileOutputStream(this.file);
    }

    @Override // gnu.text.Path
    public String getScheme() {
        if (isAbsolute()) {
            return "file";
        }
        return null;
    }

    @Override // gnu.text.Path
    public Path resolve(String relative) {
        File nfile;
        if (Path.uriSchemeSpecified(relative)) {
            return URLPath.valueOf(relative);
        }
        File rfile = new File(relative);
        if (rfile.isAbsolute()) {
            return valueOf(rfile);
        }
        char sep = File.separatorChar;
        if (sep != '/') {
            relative = relative.replace('/', sep);
        }
        if (this == Path.userDirPath) {
            nfile = new File(System.getProperty("user.dir"), relative);
        } else {
            nfile = new File(isDirectory() ? this.file : this.file.getParentFile(), relative);
        }
        return valueOf(nfile);
    }

    @Override // gnu.text.Path
    public Path getCanonical() {
        try {
            File canon = this.file.getCanonicalFile();
            if (!canon.equals(this.file)) {
                return valueOf(canon);
            }
            return this;
        } catch (Throwable th) {
            return this;
        }
    }
}
