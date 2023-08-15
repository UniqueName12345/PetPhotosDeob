package gnu.kawa.xml;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Location;

/* loaded from: classes.dex */
public class DocumentConstructor extends NodeConstructor {
    public static final DocumentConstructor documentConstructor = new DocumentConstructor();
    static final Method startDocumentMethod = Compilation.typeConsumer.getDeclaredMethod("startDocument", 0);
    static final Method endDocumentMethod = Compilation.typeConsumer.getDeclaredMethod("endDocument", 0);

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) {
        Consumer saved = ctx.consumer;
        Consumer out = pushNodeContext(ctx);
        try {
            String endMarker = Location.UNBOUND;
            out.startDocument();
            while (true) {
                Object arg = ctx.getNextArg(endMarker);
                if (arg != endMarker) {
                    if (arg instanceof Consumable) {
                        ((Consumable) arg).consume(out);
                    } else {
                        out.writeObject(arg);
                    }
                } else {
                    out.endDocument();
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
        CodeAttr code = comp.getCode();
        code.emitLoad(consumer);
        code.emitInvokeInterface(startDocumentMethod);
        for (Expression expression : args) {
            compileChild(expression, comp, target);
        }
        code.emitLoad(consumer);
        code.emitInvokeInterface(endDocumentMethod);
    }
}
