package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;

/* loaded from: classes.dex */
public class BindingInitializer extends Initializer {
    static final ClassType typeThreadLocation = ClassType.make("gnu.mapping.ThreadLocation");
    Declaration decl;
    Expression value;

    public static void create(Declaration decl, Expression value, Compilation comp) {
        BindingInitializer init = new BindingInitializer(decl, value);
        if (decl.field == null ? decl.isStatic() : decl.field.getStaticFlag()) {
            init.next = comp.clinitChain;
            comp.clinitChain = init;
            return;
        }
        init.next = comp.mainLambda.initChain;
        comp.mainLambda.initChain = init;
    }

    public BindingInitializer(Declaration decl, Expression value) {
        this.decl = decl;
        this.value = value;
        this.field = decl.field;
    }

    @Override // gnu.expr.Initializer
    public void emit(Compilation comp) {
        Object val;
        if (this.decl.ignorable()) {
            if (this.value != null) {
                this.value.compile(comp, Target.Ignore);
                return;
            }
            return;
        }
        CodeAttr code = comp.getCode();
        if ((this.value instanceof QuoteExp) && (val = ((QuoteExp) this.value).getValue()) != null && !(val instanceof String)) {
            Literal lit = comp.litTable.findLiteral(val);
            if (lit.field == this.field) {
                return;
            }
        }
        int line = this.decl.getLineNumber();
        SourceMessages messages = comp.getMessages();
        SourceLocator saveLoc = messages.swapSourceLocator(this.decl);
        if (line > 0) {
            code.putLineNumber(this.decl.getFileName(), line);
        }
        if (this.field != null && !this.field.getStaticFlag()) {
            code.emitPushThis();
        }
        if (this.value == null) {
            boolean func = comp.getLanguage().hasSeparateFunctionNamespace();
            Object property = (func && this.decl.isProcedureDecl()) ? EnvironmentKey.FUNCTION : null;
            Object name = this.decl.getSymbol();
            if (this.decl.getFlag(268500992L)) {
                if (name instanceof String) {
                    name = Namespace.EmptyNamespace.getSymbol((String) name);
                }
                comp.compileConstant(name, Target.pushObject);
                if (property == null) {
                    code.emitPushNull();
                } else {
                    comp.compileConstant(property, Target.pushObject);
                }
                code.emitInvokeStatic(typeThreadLocation.getDeclaredMethod("getInstance", 2));
            } else if (this.decl.isFluid()) {
                Type[] atypes = new Type[1];
                atypes[0] = name instanceof Symbol ? Compilation.typeSymbol : Type.toStringType;
                comp.compileConstant(name, Target.pushObject);
                Method m = typeThreadLocation.getDeclaredMethod("makeAnonymous", atypes);
                code.emitInvokeStatic(m);
            } else {
                comp.compileConstant(name, Target.pushObject);
                code.emitInvokeStatic(makeLocationMethod(name));
            }
        } else {
            Type type = this.field == null ? this.decl.getType() : this.field.getType();
            this.value.compileWithPosition(comp, StackTarget.getInstance(type));
        }
        if (this.field == null) {
            Variable var = this.decl.getVariable();
            if (var == null) {
                var = this.decl.allocateVariable(code);
            }
            code.emitStore(var);
        } else if (this.field.getStaticFlag()) {
            code.emitPutStatic(this.field);
        } else {
            code.emitPutField(this.field);
        }
        messages.swapSourceLocator(saveLoc);
    }

    public static Method makeLocationMethod(Object name) {
        Type[] atypes = new Type[1];
        if (name instanceof Symbol) {
            atypes[0] = Compilation.typeSymbol;
        } else {
            atypes[0] = Type.javalangStringType;
        }
        return Compilation.typeLocation.getDeclaredMethod("make", atypes);
    }
}
