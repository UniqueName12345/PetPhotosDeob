package gnu.mapping;

/* loaded from: classes.dex */
public class LazyPropertyKey<T> extends PropertyKey<T> {
    public LazyPropertyKey(String name) {
        super(name);
    }

    @Override // gnu.mapping.PropertyKey
    public T get(PropertySet container, T defaultValue) {
        T t = (T) container.getProperty(this, defaultValue);
        if (t instanceof String) {
            String str = (String) t;
            int cstart = str.charAt(0) == '*' ? 1 : 0;
            int colon = str.indexOf(58);
            if (colon <= cstart || colon >= str.length() - 1) {
                throw new RuntimeException("lazy property " + this + " must have the form \"ClassName:fieldName\" or \"ClassName:staticMethodName\"");
            }
            String cname = str.substring(cstart, colon);
            String mname = str.substring(colon + 1);
            try {
                Class clas = Class.forName(cname, true, container.getClass().getClassLoader());
                T result = cstart == 0 ? (T) clas.getField(mname).get(null) : (T) clas.getDeclaredMethod(mname, Object.class).invoke(null, container);
                container.setProperty(this, result);
                return result;
            } catch (Throwable ex) {
                throw new RuntimeException("lazy property " + this + " has specifier \"" + str + "\" but there is no such " + (cstart == 0 ? "field" : "method"), ex);
            }
        }
        return t;
    }

    public void set(PropertySet container, String specifier) {
        container.setProperty(this, specifier);
    }
}
