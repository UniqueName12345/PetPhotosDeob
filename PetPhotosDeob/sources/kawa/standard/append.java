package kawa.standard;

import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongType;

/* loaded from: classes.dex */
public class append extends ProcedureN {
    public static final append append = new append();

    static {
        append.setName("append");
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) {
        return append$V(args);
    }

    public static Object append$V(Object[] args) {
        int count = args.length;
        if (count == 0) {
            return LList.Empty;
        }
        Pair result = args[count - 1];
        int i = count - 1;
        while (true) {
            Object obj = result;
            i--;
            if (i >= 0) {
                Object list = args[i];
                Pair pair = null;
                Pair last = null;
                while (true) {
                    result = pair;
                    if (!(list instanceof Pair)) {
                        break;
                    }
                    Pair list_pair = (Pair) list;
                    Pair new_pair = new Pair(list_pair.getCar(), null);
                    if (last == null) {
                        pair = new_pair;
                    } else {
                        last.setCdr(new_pair);
                        pair = result;
                    }
                    last = new_pair;
                    list = list_pair.getCdr();
                }
                if (list != LList.Empty) {
                    throw new WrongType(append, i + 1, args[i], "list");
                }
                if (last != null) {
                    last.setCdr(obj);
                } else {
                    result = obj;
                }
            } else {
                return obj;
            }
        }
    }
}
