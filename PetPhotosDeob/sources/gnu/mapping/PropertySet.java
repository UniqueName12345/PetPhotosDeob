package gnu.mapping;

/* loaded from: classes.dex */
public abstract class PropertySet implements Named {
    public static final Symbol nameKey = Namespace.EmptyNamespace.getSymbol("name");
    private Object[] properties;

    @Override // gnu.mapping.Named
    public String getName() {
        Object symbol = getProperty(nameKey, null);
        if (symbol == null) {
            return null;
        }
        return symbol instanceof Symbol ? ((Symbol) symbol).getName() : symbol.toString();
    }

    @Override // gnu.mapping.Named
    public Object getSymbol() {
        return getProperty(nameKey, null);
    }

    public final void setSymbol(Object name) {
        setProperty(nameKey, name);
    }

    @Override // gnu.mapping.Named
    public final void setName(String name) {
        setProperty(nameKey, name);
    }

    public Object getProperty(Object key, Object defaultValue) {
        if (this.properties != null) {
            int i = this.properties.length;
            do {
                i -= 2;
                if (i < 0) {
                    return defaultValue;
                }
            } while (this.properties[i] != key);
            return this.properties[i + 1];
        }
        return defaultValue;
    }

    public synchronized void setProperty(Object key, Object value) {
        this.properties = setProperty(this.properties, key, value);
    }

    public static Object[] setProperty(Object[] properties, Object key, Object value) {
        int avail;
        Object[] props = properties;
        if (props == null) {
            props = new Object[10];
            properties = props;
            avail = 0;
        } else {
            avail = -1;
            int i = props.length;
            while (true) {
                i -= 2;
                if (i >= 0) {
                    Object k = props[i];
                    if (k == key) {
                        Object obj = props[i + 1];
                        props[i + 1] = value;
                        break;
                    } else if (k == null) {
                        avail = i;
                    }
                } else if (avail < 0) {
                    avail = props.length;
                    properties = new Object[avail * 2];
                    System.arraycopy(props, 0, properties, 0, avail);
                    props = properties;
                }
            }
            return properties;
        }
        props[avail] = key;
        props[avail + 1] = value;
        return properties;
    }

    public Object removeProperty(Object key) {
        Object k;
        Object[] props = this.properties;
        if (props == null) {
            return null;
        }
        int i = props.length;
        do {
            i -= 2;
            if (i < 0) {
                return null;
            }
            k = props[i];
        } while (k != key);
        Object obj = props[i + 1];
        props[i] = null;
        props[i + 1] = null;
        return obj;
    }
}
