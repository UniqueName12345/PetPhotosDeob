package gnu.xquery.util;

import gnu.kawa.functions.NumberCompare;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XDataType;
import gnu.kawa.xml.XTimeType;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.math.DFloNum;
import gnu.math.DateTime;
import gnu.math.Duration;
import gnu.math.Unit;

/* loaded from: classes.dex */
public class Compare extends Procedure2 {
    static final int LENIENT_COMPARISON = 64;
    static final int LENIENT_EQ = 72;
    static final int RESULT_EQU = 0;
    static final int RESULT_GRT = 1;
    static final int RESULT_LSS = -1;
    static final int RESULT_NAN = -2;
    static final int RESULT_NEQ = -3;
    static final int TRUE_IF_EQU = 8;
    static final int TRUE_IF_GRT = 16;
    static final int TRUE_IF_LSS = 4;
    static final int TRUE_IF_NAN = 2;
    static final int TRUE_IF_NEQ = 1;
    static final int VALUE_COMPARISON = 32;
    int flags;
    public static final Compare $Eq = make("=", 8);
    public static final Compare $Ex$Eq = make("!=", 23);
    public static final Compare $Gr = make(">", 16);
    public static final Compare $Gr$Eq = make(">=", 24);
    public static final Compare $Ls = make("<", 4);
    public static final Compare $Ls$Eq = make("<=", 12);
    public static final Compare valEq = make("eq", 40);
    public static final Compare valNe = make("ne", 55);
    public static final Compare valGt = make("gt", 48);
    public static final Compare valGe = make("ge", 56);
    public static final Compare valLt = make("lt", 36);
    public static final Compare valLe = make("le", 44);

    public static Compare make(String name, int flags) {
        Compare proc = new Compare();
        proc.setName(name);
        proc.setProperty(Procedure.validateApplyKey, "gnu.xquery.util.CompileMisc:validateCompare");
        proc.flags = flags;
        return proc;
    }

    public static boolean apply(int flags, Object arg1, Object arg2, NamedCollator collator) {
        if (arg1 instanceof Values) {
            Values values1 = (Values) arg1;
            int index = 0;
            while (true) {
                int next = values1.nextDataIndex(index);
                if (next < 0) {
                    return false;
                }
                if (apply(flags, values1.getPosNext(index << 1), arg2, collator)) {
                    return true;
                }
                index = next;
            }
        } else if (arg2 instanceof Values) {
            Values values2 = (Values) arg2;
            int index2 = 0;
            while (true) {
                int next2 = values2.nextDataIndex(index2);
                if (next2 < 0) {
                    return false;
                }
                if (apply(flags, arg1, values2.getPosNext(index2 << 1), collator)) {
                    return true;
                }
                index2 = next2;
            }
        } else {
            return atomicCompare(flags, KNode.atomicValue(arg1), KNode.atomicValue(arg2), collator);
        }
    }

    public static boolean equalityComparison(int flags) {
        return ((flags & 16) != 0) == ((flags & 4) != 0);
    }

    public static boolean atomicCompare(int flags, Object arg1, Object arg2, NamedCollator collator) {
        int comp;
        int comp2;
        int comp3;
        if (arg1 instanceof UntypedAtomic) {
            String str = arg1.toString();
            if ((flags & 32) != 0) {
                arg1 = str;
            } else if (arg2 instanceof DateTime) {
                arg1 = XTimeType.parseDateTime(str, ((DateTime) arg2).components());
            } else if (arg2 instanceof Duration) {
                arg1 = Duration.parse(str, ((Duration) arg2).unit());
            } else if (arg2 instanceof Number) {
                arg1 = new DFloNum(str);
            } else if (arg2 instanceof Boolean) {
                arg1 = XDataType.booleanType.valueOf(str);
            } else {
                arg1 = str;
            }
        }
        if (arg2 instanceof UntypedAtomic) {
            String str2 = arg2.toString();
            if ((flags & 32) != 0) {
                arg2 = str2;
            } else if (arg1 instanceof DateTime) {
                arg2 = XTimeType.parseDateTime(str2, ((DateTime) arg1).components());
            } else if (arg1 instanceof Duration) {
                arg2 = Duration.parse(str2, ((Duration) arg1).unit());
            } else if (arg1 instanceof Number) {
                arg2 = new DFloNum(str2);
            } else if (arg1 instanceof Boolean) {
                arg2 = XDataType.booleanType.valueOf(str2);
            } else {
                arg2 = str2;
            }
        }
        if ((arg1 instanceof Number) || (arg2 instanceof Number)) {
            if (arg1 instanceof Duration) {
                if (!(arg2 instanceof Duration)) {
                    comp = -3;
                } else {
                    Duration d1 = (Duration) arg1;
                    Duration d2 = (Duration) arg2;
                    if ((d1.unit != d2.unit || d1.unit == Unit.duration) && !equalityComparison(flags)) {
                        comp = -3;
                    } else {
                        comp = Duration.compare(d1, d2);
                    }
                }
            } else if (arg1 instanceof DateTime) {
                if (!(arg2 instanceof DateTime)) {
                    comp = -3;
                } else {
                    DateTime d12 = (DateTime) arg1;
                    DateTime d22 = (DateTime) arg2;
                    int m1 = d12.components();
                    int m2 = d22.components();
                    if (m1 != m2) {
                        comp = -3;
                    } else if (!equalityComparison(flags) && m1 != 112 && m1 != 14 && m1 != 126) {
                        comp = -3;
                    } else {
                        comp = DateTime.compare(d12, d22);
                    }
                }
            } else if ((arg2 instanceof Duration) || (arg2 instanceof DateTime)) {
                comp = -3;
            } else {
                comp = NumberCompare.compare(arg1, arg2, false);
            }
            if (comp == -3 && (flags & 64) == 0) {
                throw new IllegalArgumentException("values cannot be compared");
            }
            return NumberCompare.checkCompareCode(comp, flags);
        }
        if (arg1 instanceof Symbol) {
            if ((arg2 instanceof Symbol) && equalityComparison(flags)) {
                comp2 = arg1.equals(arg2) ? 0 : -2;
            } else {
                comp2 = -3;
            }
        } else if (arg1 instanceof Boolean) {
            if (arg2 instanceof Boolean) {
                boolean b1 = ((Boolean) arg1).booleanValue();
                boolean b2 = ((Boolean) arg2).booleanValue();
                if (b1 == b2) {
                    comp2 = 0;
                } else {
                    comp2 = b2 ? -1 : 1;
                }
            } else {
                comp2 = -3;
            }
        } else if ((arg2 instanceof Boolean) || (arg2 instanceof Symbol)) {
            comp2 = -3;
        } else {
            String str1 = arg1.toString();
            String str22 = arg2.toString();
            if (collator != null) {
                comp3 = collator.compare(str1, str22);
            } else {
                comp3 = NamedCollator.codepointCompare(str1, str22);
            }
            if (comp3 < 0) {
                comp2 = -1;
            } else {
                comp2 = comp3 > 0 ? 1 : 0;
            }
        }
        if (comp2 == -3 && (flags & 64) == 0) {
            throw new IllegalArgumentException("values cannot be compared");
        }
        return NumberCompare.checkCompareCode(comp2, flags);
    }

    @Override // gnu.mapping.Procedure2, gnu.mapping.Procedure
    public Object apply2(Object arg1, Object arg2) {
        return (this.flags & 32) != 0 ? (arg1 == null || arg1 == Values.empty) ? arg1 : (arg2 == null || arg2 == Values.empty) ? arg2 : atomicCompare(this.flags, KNode.atomicValue(arg1), KNode.atomicValue(arg2), null) ? Boolean.TRUE : Boolean.FALSE : apply(this.flags, arg1, arg2, null) ? Boolean.TRUE : Boolean.FALSE;
    }
}
