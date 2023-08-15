package gnu.kawa.xml;

import gnu.mapping.Namespace;
import gnu.xml.NamespaceBinding;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

/* loaded from: classes.dex */
public class XmlNamespace extends Namespace implements Externalizable {
    public static final XmlNamespace HTML = valueOf("http://www.w3.org/1999/xhtml", "");
    public static final NamespaceBinding HTML_BINDINGS = new NamespaceBinding(null, "http://www.w3.org/1999/xhtml", NamespaceBinding.predefinedXML);
    public static final String XHTML_NAMESPACE = "http://www.w3.org/1999/xhtml";

    public static XmlNamespace getInstance(String prefix, String uri) {
        String xname = prefix + " [xml] -> " + uri;
        synchronized (nsTable) {
            Object old = nsTable.get(xname);
            if (old instanceof XmlNamespace) {
                return (XmlNamespace) old;
            }
            XmlNamespace ns = new XmlNamespace();
            ns.setName(uri.intern());
            ns.prefix = prefix.intern();
            nsTable.put(xname, ns);
            return ns;
        }
    }

    public static XmlNamespace valueOf(String name, String prefix) {
        return getInstance(prefix, name);
    }

    @Override // gnu.mapping.Namespace, gnu.mapping.HasNamedParts
    public Object get(String name) {
        ElementType type = ElementType.make(getSymbol(name));
        if (this == HTML) {
            type.setNamespaceNodes(HTML_BINDINGS);
        }
        return type;
    }

    @Override // gnu.mapping.Namespace, gnu.mapping.HasNamedParts
    public boolean isConstant(String key) {
        return true;
    }

    @Override // gnu.mapping.Namespace, java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
        out.writeObject(this.prefix);
    }

    @Override // gnu.mapping.Namespace, java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setName((String) in.readObject());
        this.prefix = (String) in.readObject();
    }

    @Override // gnu.mapping.Namespace
    public Object readResolve() throws ObjectStreamException {
        String xname = this.prefix + " -> " + getName();
        Namespace ns = (Namespace) nsTable.get(xname);
        if (!(ns instanceof XmlNamespace)) {
            nsTable.put(xname, this);
            return this;
        }
        return ns;
    }
}
