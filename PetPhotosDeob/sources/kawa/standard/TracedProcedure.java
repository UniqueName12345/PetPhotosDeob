package kawa.standard;

import gnu.kawa.functions.ObjectFormat;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Symbol;
import gnu.math.IntNum;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/* loaded from: classes.dex */
public class TracedProcedure extends ProcedureN {
    boolean enabled;
    public Procedure proc;
    static int indentationStep = 2;
    static Symbol curIndentSym = Symbol.makeUninterned("current-indentation");

    public TracedProcedure(Procedure proc, boolean enable) {
        this.proc = proc;
        this.enabled = enable;
        String name = proc.getName();
        if (name != null) {
            setName(name);
        }
    }

    static void put(Object value, PrintWriter out) {
        try {
            if (!ObjectFormat.format(value, (Writer) out, 50, true)) {
                out.print("...");
            }
        } catch (IOException ex) {
            out.print("<caught ");
            out.print(ex);
            out.print('>');
        }
    }

    static void indent(int i, PrintWriter out) {
        while (true) {
            i--;
            if (i >= 0) {
                out.print(' ');
            } else {
                return;
            }
        }
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) throws Throwable {
        int curIndent;
        if (this.enabled) {
            Environment env = Environment.getCurrent();
            Location curIndentLoc = env.getLocation(curIndentSym);
            Object oldIndent = curIndentLoc.get(null);
            if (!(oldIndent instanceof IntNum)) {
                curIndent = 0;
                curIndentLoc.set(IntNum.zero());
            } else {
                curIndent = ((IntNum) oldIndent).intValue();
            }
            PrintWriter out = OutPort.errDefault();
            String name = getName();
            if (name == null) {
                name = "??";
            }
            indent(curIndent, out);
            out.print("call to ");
            out.print(name);
            int len = args.length;
            out.print(" (");
            for (int i = 0; i < len; i++) {
                if (i > 0) {
                    out.print(' ');
                }
                put(args[i], out);
            }
            out.println(")");
            IntNum newIndentation = IntNum.make(indentationStep + curIndent);
            Object save = curIndentLoc.setWithSave(newIndentation);
            try {
                try {
                    Object result = this.proc.applyN(args);
                    curIndentLoc.setRestore(save);
                    indent(curIndent, out);
                    out.print("return from ");
                    out.print(name);
                    out.print(" => ");
                    put(result, out);
                    out.println();
                    return result;
                } catch (RuntimeException e) {
                    indent(curIndent, out);
                    out.println("procedure " + name + " throws exception " + e);
                    throw e;
                }
            } catch (Throwable th) {
                curIndentLoc.setRestore(save);
                throw th;
            }
        }
        return this.proc.applyN(args);
    }

    public static Procedure doTrace(Procedure proc, boolean enable) {
        if (proc instanceof TracedProcedure) {
            ((TracedProcedure) proc).enabled = enable;
            return proc;
        }
        return new TracedProcedure(proc, enable);
    }

    public void print(PrintWriter ps) {
        ps.print("#<procedure ");
        String n = getName();
        if (n == null) {
            ps.print("<unnamed>");
        } else {
            ps.print(n);
        }
        ps.print(this.enabled ? ", traced>" : ">");
    }
}
