package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.xml.NodeTree;
import gnu.xml.XMLFilter;

/* loaded from: classes.dex */
public abstract class NodeConstructor extends MethodProc implements Inlineable {
    static final ClassType typeXMLFilter = ClassType.make("gnu.xml.XMLFilter");
    static final ClassType typeKNode = ClassType.make("gnu.kawa.xml.KNode");
    static final ClassType typeNodeConstructor = ClassType.make("gnu.kawa.xml.NodeConstructor");
    static final Method pushNodeContextMethod = typeNodeConstructor.getDeclaredMethod("pushNodeContext", 1);
    static final Method popNodeContextMethod = typeNodeConstructor.getDeclaredMethod("popNodeContext", 2);
    static final Method pushNodeConsumerMethod = typeNodeConstructor.getDeclaredMethod("pushNodeConsumer", 1);
    static final Method popNodeConsumerMethod = typeNodeConstructor.getDeclaredMethod("popNodeConsumer", 2);

    public abstract void compileToNode(ApplyExp applyExp, Compilation compilation, ConsumerTarget consumerTarget);

    public static XMLFilter pushNodeConsumer(Consumer out) {
        return out instanceof XMLFilter ? (XMLFilter) out : new XMLFilter(new NodeTree());
    }

    public static void popNodeConsumer(Consumer saved, Consumer current) {
        if (saved != current) {
            boolean z = current instanceof XMLFilter;
            KNode kNode = current;
            if (z) {
                kNode = KNode.make((NodeTree) ((XMLFilter) current).out);
            }
            saved.writeObject(kNode);
        }
    }

    public static XMLFilter pushNodeContext(CallContext ctx) {
        Consumer out = ctx.consumer;
        if (out instanceof XMLFilter) {
            return (XMLFilter) out;
        }
        XMLFilter filter = new XMLFilter(new NodeTree());
        ctx.consumer = filter;
        return filter;
    }

    public static void popNodeContext(Consumer saved, CallContext ctx) {
        Object obj = ctx.consumer;
        if (saved != obj) {
            if (obj instanceof XMLFilter) {
                obj = KNode.make((NodeTree) ((XMLFilter) obj).out);
            }
            saved.writeObject(obj);
            ctx.consumer = saved;
        }
    }

    public static void compileChild(Expression arg, Compilation comp, ConsumerTarget target) {
        if (arg instanceof ApplyExp) {
            ApplyExp app = (ApplyExp) arg;
            Expression func = app.getFunction();
            if (func instanceof QuoteExp) {
                Object proc = ((QuoteExp) func).getValue();
                if (proc instanceof NodeConstructor) {
                    ((NodeConstructor) proc).compileToNode(app, comp, target);
                    return;
                }
            }
        }
        arg.compileWithPosition(comp, target);
    }

    public static void compileUsingNodeTree(Expression exp, Compilation comp, Target target) {
        Method makeMethod = typeNodeConstructor.getDeclaredMethod("makeNode", 0);
        Method makeKNodeMethod = typeNodeConstructor.getDeclaredMethod("finishNode", 1);
        ConsumerTarget.compileUsingConsumer(exp, comp, target, makeMethod, makeKNodeMethod);
    }

    public static XMLFilter makeNode() {
        return new XMLFilter(new NodeTree());
    }

    public static KNode finishNode(XMLFilter filter) {
        return KNode.make((NodeTree) filter.out);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        if (target instanceof IgnoreTarget) {
            ApplyExp.compile(exp, comp, target);
        } else if (!(target instanceof ConsumerTarget)) {
            compileUsingNodeTree(exp, comp, target);
        } else {
            ConsumerTarget ctarget = (ConsumerTarget) target;
            Variable cvar = ctarget.getConsumerVariable();
            Type ctype = cvar.getType();
            if (ctype.isSubtype(typeXMLFilter)) {
                compileToNode(exp, comp, ctarget);
                return;
            }
            Expression[] args = exp.getArgs();
            int length = args.length;
            CodeAttr code = comp.getCode();
            Scope scope = code.pushScope();
            Variable xvar = scope.addVariable(code, typeXMLFilter, null);
            if (ctarget.isContextTarget()) {
                comp.loadCallContext();
                code.emitInvokeStatic(pushNodeContextMethod);
            } else {
                code.emitLoad(cvar);
                code.emitInvokeStatic(pushNodeConsumerMethod);
            }
            code.emitStore(xvar);
            code.emitTryStart(true, Type.void_type);
            ConsumerTarget xtarget = new ConsumerTarget(xvar);
            compileToNode(exp, comp, xtarget);
            code.emitTryEnd();
            code.emitFinallyStart();
            code.emitLoad(cvar);
            if (ctarget.isContextTarget()) {
                comp.loadCallContext();
                code.emitInvokeStatic(popNodeContextMethod);
            } else {
                code.emitLoad(xvar);
                code.emitInvokeStatic(popNodeConsumerMethod);
            }
            code.emitFinallyEnd();
            code.emitTryCatchEnd();
            code.popScope();
        }
    }

    @Override // gnu.mapping.Procedure
    public Type getReturnType(Expression[] args) {
        return Compilation.typeObject;
    }
}
