package gnu.kawa.xml;

import androidx.fragment.app.FragmentTransaction;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Values;
import gnu.xml.NodeTree;
import gnu.xml.TextUtils;
import gnu.xml.XMLFilter;

/* loaded from: classes.dex */
public class MakeText extends NodeConstructor {
    public static final MakeText makeText = new MakeText();

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object apply1(Object arg) {
        if (arg != null) {
            if (!(arg instanceof Values) || !((Values) arg).isEmpty()) {
                NodeTree node = new NodeTree();
                TextUtils.textValue(arg, new XMLFilter(node));
                return KText.make(node);
            }
            return arg;
        }
        return arg;
    }

    public static void text$X(Object arg, CallContext ctx) {
        if (arg != null) {
            if (!(arg instanceof Values) || !((Values) arg).isEmpty()) {
                Consumer saved = ctx.consumer;
                Consumer out = NodeConstructor.pushNodeContext(ctx);
                try {
                    TextUtils.textValue(arg, out);
                } finally {
                    NodeConstructor.popNodeContext(saved, ctx);
                }
            }
        }
    }

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) {
        text$X(ctx.getNextArg(null), ctx);
    }

    @Override // gnu.kawa.xml.NodeConstructor, gnu.expr.Inlineable
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        ApplyExp.compile(exp, comp, target);
    }

    @Override // gnu.kawa.xml.NodeConstructor
    public void compileToNode(ApplyExp exp, Compilation comp, ConsumerTarget target) {
        CodeAttr code = comp.getCode();
        Expression[] args = exp.getArgs();
        Expression texp = args[0];
        Variable cvar = target.getConsumerVariable();
        if (texp instanceof QuoteExp) {
            Object tval = ((QuoteExp) texp).getValue();
            if (tval instanceof String) {
                String str = (String) tval;
                String segments = CodeAttr.calculateSplit(str);
                int numSegments = segments.length();
                ClassType ctype = (ClassType) cvar.getType();
                Method writer = ctype.getMethod("write", new Type[]{Type.string_type});
                int segStart = 0;
                for (int seg = 0; seg < numSegments; seg++) {
                    code.emitLoad(cvar);
                    int segEnd = segStart + segments.charAt(seg);
                    code.emitPushString(str.substring(segStart, segEnd));
                    code.emitInvoke(writer);
                    segStart = segEnd;
                }
                return;
            }
        }
        texp.compile(comp, Target.pushObject);
        code.emitLoad(cvar);
        code.emitInvokeStatic(ClassType.make("gnu.xml.TextUtils").getDeclaredMethod("textValue", 2));
    }
}
