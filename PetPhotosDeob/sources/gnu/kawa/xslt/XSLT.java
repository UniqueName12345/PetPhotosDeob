package gnu.kawa.xslt;

import androidx.appcompat.widget.ActivityChooserView;
import gnu.expr.ApplicationMainSupport;
import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.xml.Document;
import gnu.kawa.xml.Focus;
import gnu.kawa.xml.KDocument;
import gnu.lists.Consumer;
import gnu.lists.TreeList;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.text.Lexer;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.xquery.lang.XQParser;
import gnu.xquery.lang.XQResolveNames;
import gnu.xquery.lang.XQuery;
import java.io.IOException;

/* loaded from: classes.dex */
public class XSLT extends XQuery {
    public static XSLT instance;
    public static Symbol nullMode = Symbol.make(null, "");

    @Override // gnu.xquery.lang.XQuery, gnu.expr.Language
    public String getName() {
        return "XSLT";
    }

    public XSLT() {
        instance = this;
        ModuleBody.setMainPrintValues(true);
    }

    public static XSLT getXsltInstance() {
        if (instance == null) {
            new XSLT();
        }
        return instance;
    }

    @Override // gnu.xquery.lang.XQuery, gnu.expr.Language
    public Lexer getLexer(InPort inp, SourceMessages messages) {
        return new XslTranslator(inp, messages, this);
    }

    @Override // gnu.xquery.lang.XQuery, gnu.expr.Language
    public boolean parse(Compilation comp, int options) throws IOException, SyntaxException {
        Compilation.defaultCallConvention = 2;
        ((XslTranslator) comp.lexer).parse(comp);
        comp.setState(4);
        XQParser xqparser = new XQParser(null, comp.getMessages(), this);
        XQResolveNames resolver = new XQResolveNames(comp);
        resolver.functionNamespacePath = xqparser.functionNamespacePath;
        resolver.parser = xqparser;
        resolver.resolveModule(comp.mainLambda);
        return true;
    }

    public static void registerEnvironment() {
        Language.setDefaults(new XSLT());
    }

    public static void defineCallTemplate(Symbol name, double priority, Procedure template) {
    }

    public static void defineApplyTemplate(String pattern, double priority, Symbol mode, Procedure template) {
        if (mode == null) {
            mode = nullMode;
        }
        TemplateTable table = TemplateTable.getTemplateTable(mode);
        table.enter(pattern, priority, template);
    }

    public static void defineTemplate(Symbol name, String pattern, double priority, Symbol mode, Procedure template) {
        if (name != null) {
            defineCallTemplate(name, priority, template);
        }
        if (pattern != null) {
            defineApplyTemplate(pattern, priority, mode, template);
        }
    }

    public static void process(TreeList doc, Focus pos, CallContext ctx) throws Throwable {
        Consumer out = ctx.consumer;
        while (true) {
            int ipos = pos.ipos;
            int kind = doc.getNextKind(ipos);
            switch (kind) {
                case 29:
                    break;
                case 30:
                case 31:
                case 32:
                default:
                    return;
                case 33:
                    Object type = pos.getNextTypeObject();
                    String name = pos.getNextTypeName();
                    Procedure proc = TemplateTable.nullModeTable.find(name);
                    if (proc != null) {
                        proc.check0(ctx);
                        ctx.runUntilDone();
                    } else {
                        out.startElement(type);
                        int child = doc.firstAttributePos(ipos);
                        if (child == 0) {
                            child = doc.firstChildPos(ipos);
                        }
                        pos.push(doc, child);
                        process(doc, pos, ctx);
                        pos.pop();
                        out.endElement();
                    }
                    ipos = doc.nextDataIndex(ipos >>> 1) << 1;
                    pos.gotoNext();
                    continue;
                    pos.ipos = ipos;
                case 34:
                    ipos = doc.firstChildPos(ipos);
                    continue;
                    pos.ipos = ipos;
                case 35:
                    pos.getNextTypeObject();
                    String name2 = pos.getNextTypeName();
                    Procedure proc2 = TemplateTable.nullModeTable.find(GetNamedPart.CAST_METHOD_NAME + name2);
                    if (proc2 != null) {
                        proc2.check0(ctx);
                        ctx.runUntilDone();
                        continue;
                        pos.ipos = ipos;
                    }
                    break;
                case 36:
                case 37:
                    ipos = doc.nextDataIndex(ipos >>> 1) << 1;
                    continue;
                    pos.ipos = ipos;
            }
            int ichild = ipos >>> 1;
            int next = doc.nextNodeIndex(ichild, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
            if (ichild == next) {
                next = doc.nextDataIndex(ichild);
            }
            doc.consumeIRange(ichild, next, out);
            ipos = next << 1;
            pos.ipos = ipos;
        }
    }

    public static void runStylesheet() throws Throwable {
        CallContext ctx = CallContext.getInstance();
        ApplicationMainSupport.processSetProperties();
        String[] args = ApplicationMainSupport.commandLineArgArray;
        for (String arg : args) {
            KDocument doc = Document.parse(arg);
            Focus pos = Focus.getCurrent();
            pos.push(doc.sequence, doc.ipos);
            process((TreeList) doc.sequence, pos, ctx);
        }
    }

    public static void applyTemplates(String select, Symbol mode) throws Throwable {
        if (mode == null) {
            mode = nullMode;
        }
        TemplateTable.getTemplateTable(mode);
        CallContext ctx = CallContext.getInstance();
        Focus pos = Focus.getCurrent();
        TreeList doc = (TreeList) pos.sequence;
        pos.push(doc, doc.firstChildPos(pos.ipos));
        process(doc, pos, ctx);
        pos.pop();
    }
}
