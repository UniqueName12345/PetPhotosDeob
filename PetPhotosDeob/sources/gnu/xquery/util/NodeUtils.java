package gnu.xquery.util;

import gnu.bytecode.ClassType;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.xml.Document;
import gnu.kawa.xml.KDocument;
import gnu.kawa.xml.KElement;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.Nodes;
import gnu.kawa.xml.SortedNodes;
import gnu.kawa.xml.UntypedAtomic;
import gnu.lists.Consumer;
import gnu.lists.PositionConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.Path;
import gnu.xml.NamespaceBinding;
import gnu.xml.NodeTree;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import gnu.xquery.lang.XQuery;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Stack;

/* loaded from: classes.dex */
public class NodeUtils {
    static String collectionNamespace = "http://gnu.org/kawa/cached-collections";
    public static final Symbol collectionResolverSymbol = Symbol.make(XQuery.LOCAL_NAMESPACE, "collection-resolver", "qexo");

    public static Object nodeName(Object node) {
        if (node == Values.empty || node == null) {
            return node;
        }
        if (!(node instanceof KNode)) {
            throw new WrongType("node-name", 1, node, "node()?");
        }
        Symbol sym = ((KNode) node).getNodeSymbol();
        if (sym == null) {
            return Values.empty;
        }
        return sym;
    }

    public static String name(Object node) {
        Object name;
        if (node == Values.empty || node == null || (name = ((KNode) node).getNodeNameObject()) == null || name == Values.empty) {
            return "";
        }
        return name.toString();
    }

    public static String localName(Object node) {
        if (node == Values.empty || node == null) {
            return "";
        }
        if (!(node instanceof KNode)) {
            throw new WrongType("local-name", 1, node, "node()?");
        }
        Object name = ((KNode) node).getNodeNameObject();
        if (name == null || name == Values.empty) {
            return "";
        }
        if (name instanceof Symbol) {
            return ((Symbol) name).getName();
        }
        return name.toString();
    }

    public static Object namespaceURI(Object node) {
        if (node != Values.empty && node != null) {
            if (!(node instanceof KNode)) {
                throw new WrongType("namespace-uri", 1, node, "node()?");
            }
            Object name = ((KNode) node).getNodeNameObject();
            if (name instanceof Symbol) {
                return QNameUtils.namespaceURIFromQName(name);
            }
        }
        return "";
    }

    public static void prefixesFromNodetype(XName name, Consumer out) {
        NamespaceBinding bindings = name.getNamespaceNodes();
        for (NamespaceBinding ns = bindings; ns != null; ns = ns.getNext()) {
            String uri = ns.getUri();
            if (uri != null) {
                String prefix = ns.getPrefix();
                NamespaceBinding ns2 = bindings;
                while (true) {
                    if (ns2 == ns) {
                        if (prefix == null) {
                            prefix = "";
                        }
                        out.writeObject(prefix);
                    } else if (ns2.getPrefix() != prefix) {
                        ns2 = ns2.getNext();
                    }
                }
            }
        }
    }

    public static void inScopePrefixes$X(Object node, CallContext ctx) {
        KElement element = (KElement) node;
        Object type = element.getNodeNameObject();
        if (type instanceof XName) {
            prefixesFromNodetype((XName) type, ctx.consumer);
        } else {
            ctx.consumer.writeObject("xml");
        }
    }

    public static void data$X(Object arg, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (arg instanceof Values) {
            Values vals = (Values) arg;
            int ipos = vals.startPos();
            while (true) {
                ipos = vals.nextPos(ipos);
                if (ipos != 0) {
                    out.writeObject(KNode.atomicValue(vals.getPosPrevious(ipos)));
                } else {
                    return;
                }
            }
        } else {
            out.writeObject(KNode.atomicValue(arg));
        }
    }

    public static Object root(Object arg) {
        if (arg != null && arg != Values.empty) {
            if (!(arg instanceof KNode)) {
                throw new WrongType("root", 1, arg, "node()?");
            }
            KNode node = (KNode) arg;
            return Nodes.root((NodeTree) node.sequence, node.getPos());
        }
        return arg;
    }

    public static KDocument rootDocument(Object arg) {
        if (!(arg instanceof KNode)) {
            throw new WrongType("root-document", 1, arg, "node()?");
        }
        KNode node = (KNode) arg;
        KNode node2 = Nodes.root((NodeTree) node.sequence, node.getPos());
        if (!(node2 instanceof KDocument)) {
            throw new WrongType("root-document", 1, arg, "document()");
        }
        return (KDocument) node2;
    }

    public static String getLang(KNode node) {
        NodeTree seq = (NodeTree) node.sequence;
        int attr = seq.ancestorAttribute(node.ipos, NamespaceBinding.XML_NAMESPACE, "lang");
        if (attr == 0) {
            return null;
        }
        return KNode.getNodeValue(seq, attr);
    }

    public static boolean lang(Object testlang, Object node) {
        String teststr;
        if (testlang == null || testlang == Values.empty) {
            teststr = "";
        } else {
            teststr = TextUtils.stringValue(testlang);
        }
        String lang = getLang((KNode) node);
        if (lang == null) {
            return false;
        }
        int langlen = lang.length();
        int testlen = teststr.length();
        if (langlen > testlen && lang.charAt(testlen) == '-') {
            lang = lang.substring(0, testlen);
        }
        return lang.equalsIgnoreCase(teststr);
    }

    public static Object documentUri(Object arg) {
        if (arg == null || arg == Values.empty) {
            return arg;
        }
        if (!(arg instanceof KNode)) {
            throw new WrongType("xs:document-uri", 1, arg, "node()?");
        }
        KNode node = (KNode) arg;
        Object uri = ((NodeTree) node.sequence).documentUriOfPos(node.ipos);
        return uri == null ? Values.empty : uri;
    }

    public static Object nilled(Object arg) {
        if (arg != null && arg != Values.empty) {
            if (!(arg instanceof KNode)) {
                throw new WrongType("nilled", 1, arg, "node()?");
            }
            if (!(arg instanceof KElement)) {
                return Values.empty;
            }
            return Boolean.FALSE;
        }
        return arg;
    }

    public static Object baseUri(Object arg) {
        if (arg == null || arg == Values.empty) {
            return arg;
        }
        if (!(arg instanceof KNode)) {
            throw new WrongType("base-uri", 1, arg, "node()?");
        }
        Path uri = ((KNode) arg).baseURI();
        if (uri == null) {
            return Values.empty;
        }
        return uri;
    }

    static Object getIDs(Object arg, Object collector) {
        Stack st;
        if (arg instanceof KNode) {
            arg = KNode.atomicValue(arg);
        }
        if (arg instanceof Values) {
            Object[] ar = ((Values) arg).getValues();
            int i = ar.length;
            while (true) {
                i--;
                if (i >= 0) {
                    collector = getIDs(ar[i], collector);
                } else {
                    return collector;
                }
            }
        } else {
            String str = StringUtils.coerceToString(arg, "fn:id", 1, "");
            int len = str.length();
            int i2 = 0;
            Stack stack = collector;
            while (i2 < len) {
                int i3 = i2 + 1;
                char ch = str.charAt(i2);
                if (Character.isWhitespace(ch)) {
                    i2 = i3;
                } else {
                    int start = XName.isNameStart(ch) ? i3 - 1 : len;
                    while (i3 < len) {
                        char ch2 = str.charAt(i3);
                        if (Character.isWhitespace(ch2)) {
                            break;
                        }
                        i3++;
                        if (start < len && !XName.isNamePart(ch2)) {
                            start = len;
                        }
                    }
                    if (start < len) {
                        String ref = str.substring(start, i3);
                        if (stack == null) {
                            stack = ref;
                        } else {
                            if (stack instanceof Stack) {
                                st = (Stack) stack;
                            } else {
                                st = new Stack();
                                st.push(stack);
                                stack = st;
                            }
                            st.push(ref);
                        }
                    }
                    i2 = i3 + 1;
                }
            }
            return stack;
        }
    }

    public static void id$X(Object arg1, Object arg2, CallContext ctx) {
        KNode node = (KNode) arg2;
        NodeTree ntree = (NodeTree) node.sequence;
        KDocument kDocument = (KDocument) Nodes.root(ntree, node.ipos);
        Consumer out = ctx.consumer;
        Object idrefs = getIDs(arg1, null);
        if (idrefs != null) {
            ntree.makeIDtableIfNeeded();
            if ((out instanceof PositionConsumer) && ((idrefs instanceof String) || (out instanceof SortedNodes))) {
                idScan(idrefs, ntree, (PositionConsumer) out);
            } else if (idrefs instanceof String) {
                int pos = ntree.lookupID((String) idrefs);
                if (pos != -1) {
                    out.writeObject(KNode.make(ntree, pos));
                }
            } else {
                SortedNodes nodes = new SortedNodes();
                idScan(idrefs, ntree, nodes);
                Values.writeValues(nodes, out);
            }
        }
    }

    private static void idScan(Object ids, NodeTree seq, PositionConsumer out) {
        if (ids instanceof String) {
            int pos = seq.lookupID((String) ids);
            if (pos != -1) {
                out.writePosition(seq, pos);
            }
        } else if (ids instanceof Stack) {
            Stack st = (Stack) ids;
            int n = st.size();
            for (int i = 0; i < n; i++) {
                idScan(st.elementAt(i), seq, out);
            }
        }
    }

    public static Object idref(Object arg1, Object arg2) {
        KNode node = (KNode) arg2;
        KDocument kDocument = (KDocument) Nodes.root((NodeTree) node.sequence, node.getPos());
        return Values.empty;
    }

    public static void setSavedCollection(Object uri, Object value, Environment env) {
        if (uri == null) {
            uri = "#default";
        }
        Symbol sym = Symbol.make(collectionNamespace, uri.toString());
        env.put(sym, null, value);
    }

    public static void setSavedCollection(Object uri, Object value) {
        setSavedCollection(uri, value, Environment.getCurrent());
    }

    public static Object getSavedCollection(Object uri, Environment env) {
        if (uri == null) {
            uri = "#default";
        }
        Symbol sym = Symbol.make(collectionNamespace, uri.toString());
        Object coll = env.get(sym, null, null);
        if (coll == null) {
            throw new RuntimeException("collection '" + uri + "' not found");
        }
        return coll;
    }

    public static Object getSavedCollection(Object uri) {
        return getSavedCollection(uri, Environment.getCurrent());
    }

    public static Object collection(Object uri, Object base) throws Throwable {
        String str;
        int colon;
        Object uri2 = resolve(uri, base, "collection");
        Environment env = Environment.getCurrent();
        Symbol rsym = collectionResolverSymbol;
        Object rvalue = env.get(rsym, null, null);
        if (rvalue == null) {
            rvalue = env.get(Symbol.makeWithUnknownNamespace(rsym.getLocalName(), rsym.getPrefix()), null, null);
        }
        if (rvalue == null) {
            return getSavedCollection(uri2);
        }
        if (((rvalue instanceof String) || (rvalue instanceof UntypedAtomic)) && (colon = (str = rvalue.toString()).indexOf(58)) > 0) {
            String cname = str.substring(0, colon);
            String mname = str.substring(colon + 1);
            try {
                Class rclass = Class.forName(cname);
                ClassType rclassType = (ClassType) ClassType.make(rclass);
                rvalue = ClassMethods.apply(rclassType, mname, (char) 0, XQuery.instance);
                if (rvalue == null) {
                    throw new RuntimeException("invalid collection-resolver: no method " + mname + " in " + cname);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("invalid collection-resolver: class " + cname + " not found");
            } catch (Throwable ex) {
                throw new RuntimeException("invalid collection-resolver: " + ex);
            }
        }
        if (!(rvalue instanceof Procedure)) {
            throw new RuntimeException("invalid collection-resolver: " + rvalue);
        }
        return ((Procedure) rvalue).apply1(uri2);
    }

    static Object resolve(Object uri, Object base, String fname) throws Throwable {
        if (!(uri instanceof File) && !(uri instanceof Path) && !(uri instanceof URI) && !(uri instanceof URL)) {
            uri = StringUtils.coerceToString(uri, fname, 1, null);
        }
        if (uri == Values.empty || uri == null) {
            return null;
        }
        return Path.currentPath().resolve(Path.valueOf(uri));
    }

    public static Object docCached(Object uri, Object base) throws Throwable {
        Object uri2 = resolve(uri, base, "doc");
        return uri2 == null ? Values.empty : Document.parseCached(uri2);
    }

    public static boolean availableCached(Object uri, Object base) throws Throwable {
        Object uri2 = resolve(uri, base, "doc-available");
        if (uri2 == null) {
            return false;
        }
        try {
            Document.parseCached(uri2);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }
}
