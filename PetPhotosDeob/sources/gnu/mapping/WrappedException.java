package gnu.mapping;

/* loaded from: classes.dex */
public class WrappedException extends RuntimeException {
    public WrappedException() {
    }

    public WrappedException(String message) {
        super(message);
    }

    public WrappedException(Throwable e) {
        this(e.toString(), e);
    }

    public WrappedException(String message, Throwable e) {
        super(message, e);
    }

    public Throwable getException() {
        return getCause();
    }

    @Override // java.lang.Throwable
    public String toString() {
        return getMessage();
    }

    public static RuntimeException wrapIfNeeded(Throwable ex) {
        return ex instanceof RuntimeException ? (RuntimeException) ex : new WrappedException(ex);
    }
}
