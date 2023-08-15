package gnu.kawa.xml;

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
import gnu.expr.Special;
import gnu.expr.Target;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;

/* loaded from: classes.dex */
public class MakeAttribute extends NodeConstructor {
    public static final MakeAttribute makeAttribute = new MakeAttribute();
    public static final QuoteExp makeAttributeExp = new QuoteExp(makeAttribute);
    static final ClassType typeMakeAttribute = ClassType.make("gnu.kawa.xml.MakeAttribute");
    static final Method startAttributeMethod = typeMakeAttribute.getDeclaredMethod("startAttribute", 2);
    static final Method endAttributeMethod = Compilation.typeConsumer.getDeclaredMethod("endAttribute", 0);

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return -4095;
    }

    public static void startAttribute(Consumer out, Object type) {
        out.startAttribute(type);
    }

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) {
        Consumer saved = ctx.consumer;
        Consumer out = pushNodeContext(ctx);
        try {
            Object type = ctx.getNextArg();
            startAttribute(out, type);
            Special endMarker = Special.dfault;
            while (true) {
                Object arg = ctx.getNextArg(endMarker);
                if (arg != endMarker) {
                    if (arg instanceof Consumable) {
                        ((Consumable) arg).consume(out);
                    } else {
                        ctx.writeValue(arg);
                    }
                } else {
                    out.endAttribute();
                    return;
                }
            }
        } finally {
            popNodeContext(saved, ctx);
        }
    }

    @Override // gnu.kawa.xml.NodeConstructor
    public void compileToNode(ApplyExp exp, Compilation comp, ConsumerTarget target) {
        Variable consumer = target.getConsumerVariable();
        Expression[] args = exp.getArgs();
        int nargs = args.length;
        CodeAttr code = comp.getCode();
        code.emitLoad(consumer);
        code.emitDup();
        args[0].compile(comp, Target.pushObject);
        code.emitInvokeStatic(startAttributeMethod);
        for (int i = 1; i < nargs; i++) {
            compileChild(args[i], comp, target);
        }
        code.emitInvokeInterface(endAttributeMethod);
    }

    @Override // gnu.kawa.xml.NodeConstructor, gnu.mapping.Procedure
    public Type getReturnType(Expression[] args) {
        return Compilation.typeObject;
    }
}
