package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.Type;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.MethodProc;
import java.lang.reflect.Array;

/* compiled from: LambdaExp.java */
/* loaded from: classes.dex */
class Closure extends MethodProc {
    Object[][] evalFrames;
    LambdaExp lambda;

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return this.lambda.min_args | (this.lambda.max_args << 12);
    }

    public Closure(LambdaExp lexp, CallContext ctx) {
        this.lambda = lexp;
        Object[][] oldFrames = ctx.evalFrames;
        if (oldFrames != null) {
            int n = oldFrames.length;
            while (n > 0 && oldFrames[n - 1] == null) {
                n--;
            }
            this.evalFrames = new Object[n];
            System.arraycopy(oldFrames, 0, this.evalFrames, 0, n);
        }
        setSymbol(this.lambda.getSymbol());
    }

    @Override // gnu.mapping.Procedure
    public int match0(CallContext ctx) {
        return matchN(new Object[0], ctx);
    }

    @Override // gnu.mapping.Procedure
    public int match1(Object arg1, CallContext ctx) {
        return matchN(new Object[]{arg1}, ctx);
    }

    @Override // gnu.mapping.Procedure
    public int match2(Object arg1, Object arg2, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2}, ctx);
    }

    @Override // gnu.mapping.Procedure
    public int match3(Object arg1, Object arg2, Object arg3, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2, arg3}, ctx);
    }

    @Override // gnu.mapping.Procedure
    public int match4(Object arg1, Object arg2, Object arg3, Object arg4, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2, arg3, arg4}, ctx);
    }

    @Override // gnu.mapping.Procedure
    public int matchN(Object[] args, CallContext ctx) {
        int key_i;
        Location value;
        int i;
        int num = numArgs();
        int nargs = args.length;
        int min = num & 4095;
        if (nargs < min) {
            return (-983040) | min;
        }
        int max = num >> 12;
        if (nargs > max && max >= 0) {
            return (-917504) | max;
        }
        Object[] evalFrame = new Object[this.lambda.frameSize];
        int key_args = this.lambda.keywords == null ? 0 : this.lambda.keywords.length;
        int opt_args = this.lambda.defaultArgs == null ? 0 : this.lambda.defaultArgs.length - key_args;
        int opt_i = 0;
        int min_args = this.lambda.min_args;
        Declaration decl = this.lambda.firstDecl();
        int key_i2 = 0;
        int i2 = 0;
        while (decl != null) {
            if (i2 < min_args) {
                i = i2 + 1;
                value = args[i2];
                key_i = key_i2;
            } else if (i2 < min_args + opt_args) {
                if (i2 < nargs) {
                    i = i2 + 1;
                    value = args[i2];
                } else {
                    value = this.lambda.evalDefaultArg(opt_i, ctx);
                    i = i2;
                }
                opt_i++;
                key_i = key_i2;
            } else if (this.lambda.max_args < 0 && i2 == min_args + opt_args) {
                if (decl.type instanceof ArrayType) {
                    int rem = nargs - i2;
                    Type elementType = ((ArrayType) decl.type).getComponentType();
                    if (elementType == Type.objectType) {
                        Object[] rest = new Object[rem];
                        System.arraycopy(args, i2, rest, 0, rem);
                        value = rest;
                    } else {
                        Class elementClass = elementType.getReflectClass();
                        value = Array.newInstance(elementClass, rem);
                        for (int j = 0; j < rem; j++) {
                            try {
                                Object el = elementType.coerceFromObject(args[i2 + j]);
                                Array.set(value, j, el);
                            } catch (ClassCastException e) {
                                return (-786432) | (i2 + j);
                            }
                        }
                    }
                    key_i = key_i2;
                    i = i2;
                } else {
                    value = LList.makeList(args, i2);
                    key_i = key_i2;
                    i = i2;
                }
            } else {
                key_i = key_i2 + 1;
                Keyword keyword = this.lambda.keywords[key_i2];
                int key_offset = min_args + opt_args;
                value = Keyword.searchForKeyword(args, key_offset, keyword);
                if (value == Special.dfault) {
                    value = this.lambda.evalDefaultArg(opt_i, ctx);
                }
                opt_i++;
                i = i2;
            }
            if (decl.type != null) {
                try {
                    value = decl.type.coerceFromObject(value);
                } catch (ClassCastException e2) {
                    return (-786432) | i;
                }
            }
            if (decl.isIndirectBinding()) {
                Location loc = decl.makeIndirectLocationFor();
                loc.set(value);
                value = loc;
            }
            evalFrame[decl.evalIndex] = value;
            decl = decl.nextDecl();
            key_i2 = key_i;
            i2 = i;
        }
        ctx.values = evalFrame;
        ctx.where = 0;
        ctx.next = 0;
        ctx.proc = this;
        return 0;
    }

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) throws Throwable {
        int level = ScopeExp.nesting(this.lambda);
        Object[] evalFrame = ctx.values;
        Object[][] saveFrames = ctx.evalFrames;
        int numFrames = this.evalFrames == null ? 0 : this.evalFrames.length;
        if (level >= numFrames) {
            numFrames = level;
        }
        Object[][] newFrames = new Object[numFrames + 10];
        if (this.evalFrames != null) {
            System.arraycopy(this.evalFrames, 0, newFrames, 0, this.evalFrames.length);
        }
        newFrames[level] = evalFrame;
        ctx.evalFrames = newFrames;
        try {
            if (this.lambda.body == null) {
                StringBuffer sbuf = new StringBuffer("procedure ");
                String name = this.lambda.getName();
                if (name == null) {
                    name = "<anonymous>";
                }
                sbuf.append(name);
                int line = this.lambda.getLineNumber();
                if (line > 0) {
                    sbuf.append(" at line ");
                    sbuf.append(line);
                }
                sbuf.append(" was called before it was expanded");
                throw new RuntimeException(sbuf.toString());
            }
            this.lambda.body.apply(ctx);
        } finally {
            ctx.evalFrames = saveFrames;
        }
    }

    @Override // gnu.mapping.PropertySet
    public Object getProperty(Object key, Object defaultValue) {
        Object value = super.getProperty(key, defaultValue);
        if (value == null) {
            return this.lambda.getProperty(key, defaultValue);
        }
        return value;
    }
}
