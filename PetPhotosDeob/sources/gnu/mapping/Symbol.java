package gnu.mapping;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

/* loaded from: classes.dex */
public class Symbol implements EnvironmentKey, Comparable, Externalizable {
    public static final Symbol FUNCTION = makeUninterned("(function)");
    public static final Symbol PLIST = makeUninterned("(property-list)");
    protected String name;
    Namespace namespace;

    @Override // gnu.mapping.EnvironmentKey
    public final Symbol getKeySymbol() {
        return this;
    }

    @Override // gnu.mapping.EnvironmentKey
    public final Object getKeyProperty() {
        return null;
    }

    @Override // gnu.mapping.EnvironmentKey
    public boolean matches(EnvironmentKey key) {
        return equals(key.getKeySymbol(), this) && key.getKeyProperty() == null;
    }

    @Override // gnu.mapping.EnvironmentKey
    public boolean matches(Symbol symbol, Object property) {
        return equals(symbol, this) && property == null;
    }

    public final String getNamespaceURI() {
        Namespace ns = getNamespace();
        if (ns == null) {
            return null;
        }
        return ns.getName();
    }

    public final String getLocalPart() {
        return this.name;
    }

    public final String getPrefix() {
        Namespace ns = this.namespace;
        return ns == null ? "" : ns.prefix;
    }

    public final boolean hasEmptyNamespace() {
        String nsname;
        Namespace ns = getNamespace();
        return ns == null || (nsname = ns.getName()) == null || nsname.length() == 0;
    }

    public final String getLocalName() {
        return this.name;
    }

    public final String getName() {
        return this.name;
    }

    public static Symbol make(String uri, String name, String prefix) {
        return Namespace.valueOf(uri, prefix).getSymbol(name.intern());
    }

    public static Symbol make(Object namespace, String name) {
        Namespace ns = namespace instanceof String ? Namespace.valueOf((String) namespace) : (Namespace) namespace;
        if (ns == null || name == null) {
            return makeUninterned(name);
        }
        return ns.getSymbol(name.intern());
    }

    public static SimpleSymbol valueOf(String name) {
        return (SimpleSymbol) Namespace.EmptyNamespace.getSymbol(name.intern());
    }

    public static Symbol valueOf(String name, Object spec) {
        Namespace ns;
        if (spec == null || spec == Boolean.FALSE) {
            return makeUninterned(name);
        }
        if (spec instanceof Namespace) {
            ns = (Namespace) spec;
        } else if (spec == Boolean.TRUE) {
            ns = Namespace.EmptyNamespace;
        } else {
            ns = Namespace.valueOf(((CharSequence) spec).toString());
        }
        return ns.getSymbol(name.intern());
    }

    public static Symbol valueOf(String name, String namespace, String prefix) {
        return Namespace.valueOf(namespace, prefix).getSymbol(name.intern());
    }

    public static Symbol parse(String symbol) {
        int slen = symbol.length();
        int lbr = -1;
        int rbr = -1;
        int braceCount = 0;
        int mainStart = 0;
        int prefixEnd = 0;
        int i = 0;
        while (true) {
            if (i < slen) {
                char ch = symbol.charAt(i);
                if (ch == ':' && braceCount == 0) {
                    prefixEnd = i;
                    mainStart = i + 1;
                    break;
                }
                if (ch == '{') {
                    if (lbr < 0) {
                        prefixEnd = i;
                        lbr = i;
                    }
                    braceCount++;
                }
                if (ch == '}') {
                    braceCount--;
                    if (braceCount == 0) {
                        rbr = i;
                        mainStart = (i >= slen || symbol.charAt(i + 1) != ':') ? i + 1 : i + 2;
                    } else if (braceCount < 0) {
                        mainStart = prefixEnd;
                        break;
                    }
                }
                i++;
            } else {
                break;
            }
        }
        if (lbr >= 0 && rbr > 0) {
            String uri = symbol.substring(lbr + 1, rbr);
            String prefix = prefixEnd > 0 ? symbol.substring(0, prefixEnd) : null;
            return valueOf(symbol.substring(mainStart), uri, prefix);
        } else if (prefixEnd > 0) {
            return makeWithUnknownNamespace(symbol.substring(mainStart), symbol.substring(0, prefixEnd));
        } else {
            return valueOf(symbol);
        }
    }

    public static Symbol makeWithUnknownNamespace(String local, String prefix) {
        return Namespace.makeUnknownNamespace(prefix).getSymbol(local.intern());
    }

    public Symbol() {
    }

    public static Symbol makeUninterned(String name) {
        return new Symbol(null, name);
    }

    public Symbol(Namespace ns, String name) {
        this.name = name;
        this.namespace = ns;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object o) {
        Symbol other = (Symbol) o;
        if (getNamespaceURI() != other.getNamespaceURI()) {
            throw new IllegalArgumentException("comparing Symbols in different namespaces");
        }
        return getLocalName().compareTo(other.getLocalName());
    }

    public static boolean equals(Symbol sym1, Symbol sym2) {
        if (sym1 == sym2) {
            return true;
        }
        if (sym1 == null || sym2 == null) {
            return false;
        }
        if (sym1.name == sym2.name) {
            Namespace namespace1 = sym1.namespace;
            Namespace namespace2 = sym2.namespace;
            if (namespace1 != null && namespace2 != null) {
                return namespace1.name == namespace2.name;
            }
        }
        return false;
    }

    public final boolean equals(Object o) {
        return (o instanceof Symbol) && equals(this, (Symbol) o);
    }

    public int hashCode() {
        if (this.name == null) {
            return 0;
        }
        return this.name.hashCode();
    }

    public final Namespace getNamespace() {
        return this.namespace;
    }

    public final void setNamespace(Namespace ns) {
        this.namespace = ns;
    }

    public String toString() {
        return toString('P');
    }

    public String toString(char style) {
        boolean hasPrefix = true;
        String uri = getNamespaceURI();
        String prefix = getPrefix();
        boolean hasUri = uri != null && uri.length() > 0;
        if (prefix == null || prefix.length() <= 0) {
            hasPrefix = false;
        }
        String name = getName();
        if (hasUri || hasPrefix) {
            StringBuilder sbuf = new StringBuilder();
            if (hasPrefix && (style != 'U' || !hasUri)) {
                sbuf.append(prefix);
            }
            if (hasUri && (style != 'P' || !hasPrefix)) {
                sbuf.append('{');
                sbuf.append(getNamespaceURI());
                sbuf.append('}');
            }
            sbuf.append(':');
            sbuf.append(name);
            return sbuf.toString();
        }
        return name;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        Namespace ns = getNamespace();
        out.writeObject(ns);
        out.writeObject(getName());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.namespace = (Namespace) in.readObject();
        this.name = (String) in.readObject();
    }

    public Object readResolve() throws ObjectStreamException {
        return this.namespace == null ? this : make(this.namespace, getName());
    }
}
