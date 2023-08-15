package gnu.kawa.functions;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.ProcedureN;

/* loaded from: classes.dex */
public class MakeList extends ProcedureN implements Inlineable {
    public static final MakeList list = new MakeList();

    static {
        list.setName("list");
    }

    public static Object list$V(Object[] args) {
        LList result = LList.Empty;
        int i = args.length;
        while (true) {
            LList result2 = result;
            i--;
            if (i >= 0) {
                result = new Pair(args[i], result2);
            } else {
                return result2;
            }
        }
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) {
        return list$V(args);
    }

    @Override // gnu.expr.Inlineable
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        compile(args, 0, comp);
        target.compileFromStack(comp, exp.getType());
    }

    public static void compile(Expression[] args, int offset, Compilation comp) {
        int len = args.length - offset;
        CodeAttr code = comp.getCode();
        if (len == 0) {
            new QuoteExp(LList.Empty).compile(comp, Target.pushObject);
        } else if (len <= 4) {
            for (int i = 0; i < len; i++) {
                args[offset + i].compile(comp, Target.pushObject);
            }
            Method method = Compilation.scmListType.getDeclaredMethod("list" + len, (Type[]) null);
            code.emitInvokeStatic(method);
        } else {
            args[offset].compile(comp, Target.pushObject);
            Method method2 = Compilation.scmListType.getDeclaredMethod("list1", (Type[]) null);
            code.emitInvokeStatic(method2);
            code.emitDup(1);
            int offset2 = offset + 1;
            int len2 = len - 1;
            while (len2 >= 4) {
                args[offset2].compile(comp, Target.pushObject);
                args[offset2 + 1].compile(comp, Target.pushObject);
                args[offset2 + 2].compile(comp, Target.pushObject);
                args[offset2 + 3].compile(comp, Target.pushObject);
                len2 -= 4;
                offset2 += 4;
                Method method3 = Compilation.scmListType.getDeclaredMethod("chain4", (Type[]) null);
                code.emitInvokeStatic(method3);
            }
            while (len2 > 0) {
                args[offset2].compile(comp, Target.pushObject);
                len2--;
                offset2++;
                Method method4 = Compilation.scmListType.getDeclaredMethod("chain1", (Type[]) null);
                code.emitInvokeStatic(method4);
            }
            code.emitPop(1);
        }
    }

    @Override // gnu.mapping.Procedure
    public Type getReturnType(Expression[] args) {
        return args.length > 0 ? Compilation.typePair : LangObjType.listType;
    }
}
