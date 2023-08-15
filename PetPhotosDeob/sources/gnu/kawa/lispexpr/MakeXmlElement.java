package gnu.kawa.lispexpr;

import gnu.bytecode.ClassType;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.kawa.xml.MakeElement;
import gnu.kawa.xml.MakeText;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.xml.NamespaceBinding;
import kawa.lang.Syntax;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class MakeXmlElement extends Syntax {
    public static final MakeXmlElement makeXml = new MakeXmlElement();
    static final ClassType typeNamespace;

    static {
        makeXml.setName("$make-xml$");
        typeNamespace = ClassType.make("gnu.mapping.Namespace");
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        Namespace namespace;
        Object value;
        Pair pair1 = (Pair) form.getCdr();
        Object namespaceList = pair1.getCar();
        Object obj = pair1.getCdr();
        boolean nsSeen = false;
        NamespaceBinding saveBindings = tr.xmlElementNamespaces;
        NamespaceBinding nsBindings = saveBindings;
        while (namespaceList instanceof Pair) {
            if (!nsSeen) {
                tr.letStart();
                nsSeen = true;
            }
            Pair namespacePair = (Pair) namespaceList;
            Pair namespaceNode = (Pair) namespacePair.getCar();
            String nsPrefix = (String) namespaceNode.getCar();
            String nsPrefix2 = nsPrefix.length() == 0 ? null : nsPrefix.intern();
            Object valueList = namespaceNode.getCdr();
            StringBuilder sbuf = new StringBuilder();
            while (valueList instanceof Pair) {
                Pair valuePair = (Pair) valueList;
                Object valueForm = valuePair.getCar();
                if (LList.listLength(valueForm, false) == 2 && (valueForm instanceof Pair) && ((Pair) valueForm).getCar() == MakeText.makeText) {
                    value = ((Pair) ((Pair) valueForm).getCdr()).getCar();
                } else {
                    Expression valueExp = tr.rewrite_car(valuePair, false);
                    value = valueExp.valueIfConstant();
                }
                if (value == null) {
                    Object savePos = tr.pushPositionOf(valuePair);
                    tr.error('e', "namespace URI must be literal");
                    tr.popPositionOf(savePos);
                } else {
                    sbuf.append(value);
                }
                valueList = valuePair.getCdr();
            }
            String nsUri = sbuf.toString().intern();
            NamespaceBinding nsBindings2 = new NamespaceBinding(nsPrefix2, nsUri == "" ? null : nsUri, nsBindings);
            if (nsPrefix2 == null) {
                namespace = Namespace.valueOf(nsUri);
                nsPrefix2 = "[default-element-namespace]";
            } else {
                namespace = XmlNamespace.getInstance(nsPrefix2, nsUri);
            }
            Symbol nsSymbol = Namespace.EmptyNamespace.getSymbol(nsPrefix2);
            Declaration decl = tr.letVariable(nsSymbol, typeNamespace, new QuoteExp(namespace));
            decl.setFlag(2121728L);
            namespaceList = namespacePair.getCdr();
            nsBindings = nsBindings2;
        }
        MakeElement mkElement = new MakeElement();
        mkElement.setNamespaceNodes(nsBindings);
        tr.xmlElementNamespaces = nsBindings;
        if (nsSeen) {
            try {
                tr.letEnter();
            } finally {
                tr.xmlElementNamespaces = saveBindings;
            }
        }
        Expression result = tr.rewrite(Translator.makePair(form, mkElement, obj));
        if (nsSeen) {
            result = tr.letDone(result);
        }
        return result;
    }
}
