package gnu.kawa.functions;

import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;

/* loaded from: classes.dex */
public class Apply extends ProcedureN {
    ApplyToArgs applyToArgs;

    public Apply(String name, ApplyToArgs applyToArgs) {
        super(name);
        this.applyToArgs = applyToArgs;
    }

    public static Object[] getArguments(Object[] args, int skip, Procedure proc) {
        int last_count;
        int count = args.length;
        if (count < skip + 1) {
            throw new WrongArguments("apply", 2, "(apply proc [args] args) [count:" + count + " skip:" + skip + "]");
        }
        Object last = args[count - 1];
        if (last instanceof Object[]) {
            Object[] last_arr = (Object[]) last;
            if (count != 2) {
                last_count = last_arr.length;
            } else {
                return last_arr;
            }
        } else if (last instanceof Sequence) {
            last_count = ((Sequence) last).size();
        } else {
            last_count = -1;
        }
        if (last_count < 0) {
            throw new WrongType(proc, count, last, "sequence or array");
        }
        int numArgs = last_count + ((count - skip) - 1);
        Object[] proc_args = new Object[numArgs];
        int i = 0;
        while (i < (count - skip) - 1) {
            proc_args[i] = args[i + skip];
            i++;
        }
        if (last instanceof Object[]) {
            System.arraycopy((Object[]) last, 0, proc_args, i, last_count);
        } else {
            while (last instanceof Pair) {
                Pair pair = (Pair) last;
                proc_args[i] = pair.getCar();
                last = pair.getCdr();
                last_count--;
                i++;
            }
            if (last_count > 0) {
                Sequence last_seq = (Sequence) last;
                int j = 0;
                int i2 = i;
                while (j < last_count) {
                    proc_args[i2] = last_seq.get(j);
                    j++;
                    i2++;
                }
            }
        }
        return proc_args;
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) throws Throwable {
        return this.applyToArgs.applyN(getArguments(args, 0, this));
    }

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) throws Throwable {
        Object[] args = ctx.getArgs();
        this.applyToArgs.checkN(getArguments(args, 0, this), ctx);
    }
}
