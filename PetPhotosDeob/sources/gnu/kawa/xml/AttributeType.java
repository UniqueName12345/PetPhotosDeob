package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.TypeValue;
import gnu.lists.AbstractSequence;
import gnu.lists.AttributePredicate;
import gnu.lists.SeqPosition;
import gnu.mapping.Symbol;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.xml.namespace.QName;

/* loaded from: classes.dex */
public class AttributeType extends NodeType implements TypeValue, Externalizable, AttributePredicate {
    Symbol qname;
    public static final ClassType typeAttributeType = ClassType.make("gnu.kawa.xml.AttributeType");
    static final Method coerceMethod = typeAttributeType.getDeclaredMethod("coerce", 3);
    static final Method coerceOrNullMethod = typeAttributeType.getDeclaredMethod("coerceOrNull", 3);

    public static AttributeType make(String namespaceURI, String localName) {
        Symbol qname;
        if (namespaceURI != null) {
            qname = Symbol.make(namespaceURI, localName);
        } else if (localName == "") {
            qname = ElementType.MATCH_ANY_QNAME;
        } else {
            qname = new Symbol(null, localName);
        }
        return new AttributeType(qname);
    }

    public static AttributeType make(Symbol qname) {
        return new AttributeType(qname);
    }

    public AttributeType(Symbol qname) {
        this(null, qname);
    }

    public AttributeType(String name, Symbol qname) {
        super((name == null || name.length() <= 0) ? "ATTRIBUTE " + qname + " (*)" : name);
        this.qname = qname;
    }

    @Override // gnu.kawa.xml.NodeType, gnu.bytecode.ObjectType, gnu.bytecode.Type
    public Type getImplementationType() {
        return ClassType.make("gnu.kawa.xml.KAttr");
    }

    public final String getNamespaceURI() {
        return this.qname.getNamespaceURI();
    }

    public final String getLocalName() {
        return this.qname.getLocalName();
    }

    @Override // gnu.kawa.xml.NodeType, gnu.bytecode.ObjectType, gnu.bytecode.Type
    public void emitCoerceFromObject(CodeAttr code) {
        code.emitPushString(this.qname.getNamespaceURI());
        code.emitPushString(this.qname.getLocalName());
        code.emitInvokeStatic(coerceMethod);
    }

    @Override // gnu.kawa.xml.NodeType, gnu.bytecode.ObjectType, gnu.bytecode.Type
    public Object coerceFromObject(Object obj) {
        return coerce(obj, this.qname.getNamespaceURI(), this.qname.getLocalName());
    }

    @Override // gnu.kawa.xml.NodeType, gnu.lists.ItemPredicate
    public boolean isInstancePos(AbstractSequence seq, int ipos) {
        int kind = seq.getNextKind(ipos);
        if (kind == 35) {
            return isInstance(seq, ipos, seq.getNextTypeObject(ipos));
        }
        if (kind == 32) {
            return isInstance(seq.getPosNext(ipos));
        }
        return false;
    }

    @Override // gnu.lists.AttributePredicate
    public boolean isInstance(AbstractSequence seq, int ipos, Object attrType) {
        String curNamespaceURI;
        String curLocalName;
        String namespaceURI = this.qname.getNamespaceURI();
        String localName = this.qname.getLocalName();
        if (attrType instanceof Symbol) {
            Symbol qname = (Symbol) attrType;
            curNamespaceURI = qname.getNamespaceURI();
            curLocalName = qname.getLocalName();
        } else if (attrType instanceof QName) {
            QName qtype = (QName) attrType;
            curNamespaceURI = qtype.getNamespaceURI();
            curLocalName = qtype.getLocalPart();
        } else {
            curNamespaceURI = "";
            curLocalName = attrType.toString().intern();
        }
        if (localName != null && localName.length() == 0) {
            localName = null;
        }
        return (localName == curLocalName || localName == null) && (namespaceURI == curNamespaceURI || namespaceURI == null);
    }

    @Override // gnu.kawa.xml.NodeType, gnu.bytecode.ObjectType, gnu.bytecode.Type
    public boolean isInstance(Object obj) {
        return coerceOrNull(obj, this.qname.getNamespaceURI(), this.qname.getLocalName()) != null;
    }

    public static KAttr coerceOrNull(Object obj, String namespaceURI, String localName) {
        String curNamespaceURI;
        String curLocalName;
        KNode pos = NodeType.coerceOrNull(obj, 4);
        if (pos == null) {
            return null;
        }
        if (localName != null && localName.length() == 0) {
            localName = null;
        }
        Object curName = pos.getNextTypeObject();
        if (curName instanceof Symbol) {
            Symbol qname = (Symbol) curName;
            curNamespaceURI = qname.getNamespaceURI();
            curLocalName = qname.getLocalName();
        } else if (curName instanceof QName) {
            QName qtype = (QName) curName;
            curNamespaceURI = qtype.getNamespaceURI();
            curLocalName = qtype.getLocalPart();
        } else {
            curNamespaceURI = "";
            curLocalName = curName.toString().intern();
        }
        if ((localName == curLocalName || localName == null) && (namespaceURI == curNamespaceURI || namespaceURI == null)) {
            return (KAttr) pos;
        }
        return null;
    }

    public static SeqPosition coerce(Object obj, String namespaceURI, String localName) {
        SeqPosition pos = coerceOrNull(obj, namespaceURI, localName);
        if (pos == null) {
            throw new ClassCastException();
        }
        return pos;
    }

    @Override // gnu.kawa.xml.NodeType
    protected void emitCoerceOrNullMethod(Variable incoming, Compilation comp) {
        CodeAttr code = comp.getCode();
        if (incoming != null) {
            code.emitLoad(incoming);
        }
        code.emitPushString(this.qname.getNamespaceURI());
        code.emitPushString(this.qname.getLocalName());
        code.emitInvokeStatic(coerceOrNullMethod);
    }

    @Override // gnu.kawa.xml.NodeType, java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        String name = getName();
        if (name == null) {
            name = "";
        }
        out.writeUTF(name);
        out.writeObject(this.qname);
    }

    @Override // gnu.kawa.xml.NodeType, java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        String name = in.readUTF();
        if (name.length() > 0) {
            setName(name);
        }
        this.qname = (Symbol) in.readObject();
    }

    @Override // gnu.kawa.xml.NodeType, gnu.bytecode.Type
    public String toString() {
        return "AttributeType " + this.qname;
    }
}
