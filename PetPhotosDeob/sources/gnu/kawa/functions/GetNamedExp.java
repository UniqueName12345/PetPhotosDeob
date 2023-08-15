package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.PrimProcedure;
import gnu.expr.ReferenceExp;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.Symbol;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CompileNamedPart.java */
/* loaded from: classes.dex */
public class GetNamedExp extends ApplyExp {
    public String combinedName;
    char kind;
    PrimProcedure[] methods;
    static final Declaration fieldDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.SlotGet", "field");
    static final Declaration staticFieldDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.SlotGet", "staticField");
    static final Declaration makeDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.Invoke", "make");
    static final Declaration invokeDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.Invoke", "invoke");
    static final Declaration invokeStaticDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.Invoke", "invokeStatic");
    static final Declaration instanceOfDecl = Declaration.getDeclarationFromStatic("kawa.standard.Scheme", "instanceOf");
    static final Declaration castDecl = Declaration.getDeclarationFromStatic("gnu.kawa.functions.Convert", "as");

    @Override // gnu.expr.ApplyExp, gnu.expr.Expression, gnu.mapping.Procedure
    public void apply(CallContext ctx) throws Throwable {
        if (this.combinedName != null) {
            Environment env = Environment.getCurrent();
            Symbol sym = env.getSymbol(this.combinedName);
            String unb = Location.UNBOUND;
            Object value = env.get(sym, null, unb);
            if (value != unb) {
                ctx.writeValue(value);
                return;
            }
        }
        super.apply(ctx);
    }

    public GetNamedExp(Expression[] args) {
        super(GetNamedPart.getNamedPart, args);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public GetNamedExp setProcedureKind(char kind) {
        this.type = Compilation.typeProcedure;
        this.kind = kind;
        return this;
    }

    @Override // gnu.expr.Expression
    public Expression validateApply(ApplyExp exp, InlineCalls visitor, Type required, Declaration decl) {
        Declaration decl2;
        Expression[] xargs;
        Expression[] pargs = getArgs();
        Expression context = pargs[0];
        Expression[] args = exp.getArgs();
        switch (this.kind) {
            case 'C':
                decl2 = castDecl;
                xargs = new Expression[args.length + 1];
                System.arraycopy(args, 1, xargs, 2, args.length - 1);
                xargs[0] = context;
                xargs[1] = args[0];
                break;
            case 'I':
                decl2 = instanceOfDecl;
                xargs = new Expression[args.length + 1];
                System.arraycopy(args, 1, xargs, 2, args.length - 1);
                xargs[0] = args[0];
                xargs[1] = context;
                break;
            case 'M':
                decl2 = invokeDecl;
                xargs = new Expression[args.length + 2];
                xargs[0] = pargs[0];
                xargs[1] = pargs[1];
                System.arraycopy(args, 0, xargs, 2, args.length);
                break;
            case 'N':
                decl2 = makeDecl;
                xargs = new Expression[args.length + 1];
                System.arraycopy(args, 0, xargs, 1, args.length);
                xargs[0] = context;
                break;
            case 'S':
                decl2 = invokeStaticDecl;
                xargs = new Expression[args.length + 2];
                xargs[0] = context;
                xargs[1] = pargs[1];
                System.arraycopy(args, 0, xargs, 2, args.length);
                break;
            default:
                return exp;
        }
        ApplyExp result = new ApplyExp((Expression) new ReferenceExp(decl2), xargs);
        result.setLine(exp);
        return visitor.visit((Expression) result, required);
    }

    @Override // gnu.expr.ApplyExp, gnu.expr.Expression
    public boolean side_effects() {
        if (this.kind == 'S' || this.kind == 'N' || this.kind == 'C' || this.kind == 'I') {
            return false;
        }
        if (this.kind == 'M') {
            return getArgs()[0].side_effects();
        }
        return true;
    }
}
