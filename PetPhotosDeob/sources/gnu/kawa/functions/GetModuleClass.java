package gnu.kawa.functions;

import androidx.fragment.app.FragmentTransaction;
import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BindingInitializer;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.Target;
import gnu.mapping.Namespace;
import gnu.mapping.ProcedureN;
import gnu.mapping.Symbol;
import gnu.text.Path;
import gnu.text.URLPath;

/* loaded from: classes.dex */
public class GetModuleClass extends ProcedureN implements Inlineable {
    public static final GetModuleClass getModuleClass = new GetModuleClass();
    public static final GetModuleClass getModuleUri = new GetModuleClass();
    public static final GetModuleClass getModuleUriDummy = new GetModuleClass();
    static final ClassType typeURLPath = ClassType.make("gnu.text.URLPath");
    static final Method maker = typeURLPath.getDeclaredMethod("classResourcePath", 1);
    private static Symbol CLASS_RESOURCE_NAME = Namespace.getDefaultSymbol("$class_resource_URL$");

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) {
        throw new Error("get-module-class must be inlined");
    }

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        if (this == getModuleUriDummy) {
            return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
        }
        return 0;
    }

    @Override // gnu.expr.Inlineable
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        if (this == getModuleUriDummy) {
            ReferenceExp ref = (ReferenceExp) exp.getArgs()[0];
            ref.compile(comp, target);
            Declaration decl = ref.getBinding();
            Expression init = decl.getValue();
            if (init != null) {
                BindingInitializer.create(decl, init, comp);
                decl.setValue(null);
                return;
            }
            return;
        }
        comp.loadClassRef(comp.mainClass);
        if (this == getModuleUri) {
            comp.getCode().emitInvoke(maker);
        }
        target.compileFromStack(comp, exp.getType());
    }

    @Override // gnu.mapping.Procedure
    public Type getReturnType(Expression[] args) {
        return this == getModuleClass ? Type.javalangClassType : typeURLPath;
    }

    public static Expression getModuleClassURI(Compilation comp) {
        Expression value;
        Declaration decl = comp.mainLambda.lookup(CLASS_RESOURCE_NAME);
        if (decl == null) {
            decl = new Declaration(CLASS_RESOURCE_NAME, typeURLPath);
            decl.setFlag(536889344L);
            if (comp.immediate) {
                Path path = comp.minfo.getSourceAbsPath();
                if (path == null) {
                    path = Path.currentPath();
                }
                if (!(path instanceof URLPath)) {
                    path = URLPath.valueOf(path.toURL());
                }
                value = QuoteExp.getInstance(path);
            } else {
                Expression clas = new ApplyExp(getModuleClass, Expression.noExpressions);
                value = new ApplyExp(maker, clas);
            }
            decl.setValue(value);
            comp.mainLambda.add(null, decl);
        }
        ReferenceExp ref = new ReferenceExp(decl);
        return comp.immediate ? ref : new ApplyExp(getModuleUriDummy, ref);
    }
}
