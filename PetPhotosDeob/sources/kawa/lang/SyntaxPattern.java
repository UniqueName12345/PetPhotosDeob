package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintWriter;
import java.util.Vector;

/* loaded from: classes.dex */
public class SyntaxPattern extends Pattern implements Externalizable {
    static final int MATCH_ANY = 3;
    static final int MATCH_ANY_CAR = 7;
    static final int MATCH_EQUALS = 2;
    static final int MATCH_IGNORE = 24;
    static final int MATCH_LENGTH = 6;
    static final int MATCH_LREPEAT = 5;
    static final int MATCH_MISC = 0;
    static final int MATCH_NIL = 8;
    static final int MATCH_PAIR = 4;
    static final int MATCH_VECTOR = 16;
    static final int MATCH_WIDE = 1;
    Object[] literals;
    String program;
    int varCount;

    @Override // kawa.lang.Pattern
    public int varCount() {
        return this.varCount;
    }

    @Override // kawa.lang.Pattern
    public boolean match(Object obj, Object[] vars, int start_vars) {
        boolean r = match(obj, vars, start_vars, 0, null);
        return r;
    }

    public SyntaxPattern(String program, Object[] literals, int varCount) {
        this.program = program;
        this.literals = literals;
        this.varCount = varCount;
    }

    public SyntaxPattern(Object pattern, Object[] literal_identifiers, Translator tr) {
        this(new StringBuffer(), pattern, null, literal_identifiers, tr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SyntaxPattern(StringBuffer programbuf, Object pattern, SyntaxForm syntax, Object[] literal_identifiers, Translator tr) {
        Vector literalsbuf = new Vector();
        translate(pattern, programbuf, literal_identifiers, 0, literalsbuf, null, (char) 0, tr);
        this.program = programbuf.toString();
        this.literals = new Object[literalsbuf.size()];
        literalsbuf.copyInto(this.literals);
        this.varCount = tr.patternScope.pattern_names.size();
    }

    public void disassemble() {
        disassemble(OutPort.errDefault(), (Translator) Compilation.getCurrent(), 0, this.program.length());
    }

    public void disassemble(PrintWriter ps, Translator tr) {
        disassemble(ps, tr, 0, this.program.length());
    }

    void disassemble(PrintWriter ps, Translator tr, int start, int limit) {
        Vector pattern_names = null;
        if (tr != null && tr.patternScope != null) {
            pattern_names = tr.patternScope.pattern_names;
        }
        int value = 0;
        int i = start;
        while (i < limit) {
            char ch = this.program.charAt(i);
            ps.print(" " + i + ": " + ((int) ch));
            i++;
            int opcode = ch & 7;
            value = (value << 13) | (ch >> 3);
            switch (opcode) {
                case 0:
                    ps.print("[misc ch:" + ((int) ch) + " n:8]");
                    if (ch == '\b') {
                        ps.println(" - NIL");
                        break;
                    } else if (ch == 16) {
                        ps.println(" - VECTOR");
                        break;
                    } else {
                        if (ch == 24) {
                            ps.println(" - IGNORE");
                            break;
                        }
                        ps.println(" - " + opcode + '/' + value);
                        break;
                    }
                case 1:
                    ps.println(" - WIDE " + value);
                    continue;
                case 2:
                    ps.print(" - EQUALS[" + value + "]");
                    if (this.literals != null && value >= 0 && value < this.literals.length) {
                        ps.print(this.literals[value]);
                    }
                    ps.println();
                    break;
                case 3:
                case 7:
                    ps.print((opcode == 3 ? " - ANY[" : " - ANY_CAR[") + value + "]");
                    if (pattern_names != null && value >= 0 && value < pattern_names.size()) {
                        ps.print(pattern_names.elementAt(value));
                    }
                    ps.println();
                    break;
                case 4:
                    ps.println(" - PAIR[" + value + "]");
                    break;
                case 5:
                    ps.println(" - LREPEAT[" + value + "]");
                    disassemble(ps, tr, i, i + value);
                    int i2 = i + value;
                    int i3 = i2 + 1;
                    ps.println(" " + i2 + ": - repeat first var:" + (this.program.charAt(i2) >> 3));
                    i = i3 + 1;
                    ps.println(" " + i3 + ": - repeast nested vars:" + (this.program.charAt(i3) >> 3));
                    break;
                case 6:
                    ps.println(" - LENGTH " + (value >> 1) + " pairs. " + ((value & 1) == 0 ? "pure list" : "impure list"));
                    break;
                default:
                    ps.println(" - " + opcode + '/' + value);
                    break;
            }
            value = 0;
        }
    }

    void translate(Object pattern, StringBuffer program, Object[] literal_identifiers, int nesting, Vector literals, SyntaxForm syntax, char context, Translator tr) {
        ScopeExp scope1;
        Object literal;
        ScopeExp scope2;
        PatternScope patternScope = tr.patternScope;
        Vector patternNames = patternScope.pattern_names;
        while (true) {
            if (pattern instanceof SyntaxForm) {
                syntax = (SyntaxForm) pattern;
                pattern = syntax.getDatum();
            } else if (pattern instanceof Pair) {
                Object savePos = tr.pushPositionOf(pattern);
                try {
                    int start_pc = program.length();
                    program.append((char) 4);
                    Pair pair = (Pair) pattern;
                    SyntaxForm car_syntax = syntax;
                    Object next = pair.getCdr();
                    while (next instanceof SyntaxForm) {
                        syntax = (SyntaxForm) next;
                        next = syntax.getDatum();
                    }
                    boolean repeat = false;
                    if ((next instanceof Pair) && tr.matches(((Pair) next).getCar(), "...")) {
                        repeat = true;
                        next = ((Pair) next).getCdr();
                        while (next instanceof SyntaxForm) {
                            syntax = (SyntaxForm) next;
                            next = syntax.getDatum();
                        }
                    }
                    int subvar0 = patternNames.size();
                    if (context == 'P') {
                        context = 0;
                    }
                    translate(pair.getCar(), program, literal_identifiers, repeat ? nesting + 1 : nesting, literals, car_syntax, context == 'V' ? (char) 0 : 'P', tr);
                    int subvarN = patternNames.size() - subvar0;
                    int width = (((program.length() - start_pc) - 1) << 3) | (repeat ? 5 : 4);
                    if (width > 65535) {
                        start_pc += insertInt(start_pc, program, (width >> 13) + 1);
                    }
                    program.setCharAt(start_pc, (char) width);
                    int restLength = Translator.listLength(next);
                    if (restLength == Integer.MIN_VALUE) {
                        tr.syntaxError("cyclic pattern list");
                        return;
                    }
                    if (repeat) {
                        addInt(program, subvar0 << 3);
                        addInt(program, subvarN << 3);
                        if (next == LList.Empty) {
                            program.append('\b');
                            return;
                        }
                        addInt(program, ((restLength >= 0 ? restLength << 1 : ((-restLength) << 1) - 1) << 3) | 6);
                    }
                    pattern = next;
                } finally {
                    tr.popPositionOf(savePos);
                }
            } else if (pattern instanceof Symbol) {
                int j = literal_identifiers.length;
                do {
                    j--;
                    if (j < 0) {
                        if (patternNames.contains(pattern)) {
                            tr.syntaxError("duplicated pattern variable " + pattern);
                        }
                        int i = patternNames.size();
                        patternNames.addElement(pattern);
                        boolean matchCar = context == 'P';
                        int n = (nesting << 1) + (matchCar ? 1 : 0);
                        patternScope.patternNesting.append((char) n);
                        Declaration decl = patternScope.addDeclaration(pattern);
                        decl.setLocation(tr);
                        tr.push(decl);
                        addInt(program, (matchCar ? 7 : 3) | (i << 3));
                        return;
                    }
                    ScopeExp current = tr.currentScope();
                    scope1 = syntax == null ? current : syntax.getScope();
                    literal = literal_identifiers[j];
                    if (literal instanceof SyntaxForm) {
                        SyntaxForm syntax2 = (SyntaxForm) literal;
                        literal = syntax2.getDatum();
                        scope2 = syntax2.getScope();
                    } else {
                        scope2 = tr.currentMacroDefinition != null ? tr.currentMacroDefinition.getCapturedScope() : current;
                    }
                } while (!literalIdentifierEq(pattern, scope1, literal, scope2));
                int i2 = SyntaxTemplate.indexOf(literals, pattern);
                if (i2 < 0) {
                    i2 = literals.size();
                    literals.addElement(pattern);
                }
                addInt(program, (i2 << 3) | 2);
                return;
            } else if (pattern == LList.Empty) {
                program.append('\b');
                return;
            } else if (!(pattern instanceof FVector)) {
                int i3 = SyntaxTemplate.indexOf(literals, pattern);
                if (i3 < 0) {
                    i3 = literals.size();
                    literals.addElement(pattern);
                }
                addInt(program, (i3 << 3) | 2);
                return;
            } else {
                program.append((char) 16);
                pattern = LList.makeList((FVector) pattern);
                context = 'V';
            }
        }
    }

    private static void addInt(StringBuffer sbuf, int val) {
        if (val > 65535) {
            addInt(sbuf, (val << 13) + 1);
        }
        sbuf.append((char) val);
    }

    private static int insertInt(int offset, StringBuffer sbuf, int val) {
        if (val > 65535) {
            offset += insertInt(offset, sbuf, (val << 13) + 1);
        }
        sbuf.insert(offset, (char) val);
        return offset + 1;
    }

    boolean match_car(Pair p, Object[] vars, int start_vars, int pc, SyntaxForm syntax) {
        int pc2 = pc + 1;
        char ch = this.program.charAt(pc);
        int value = ch >> 3;
        while ((ch & 7) == 1) {
            ch = this.program.charAt(pc2);
            value = (value << 13) | (ch >> 3);
            pc2++;
        }
        if ((ch & 7) != 7) {
            return match(p.getCar(), vars, start_vars, pc, syntax);
        }
        if (syntax != null && !(p.getCar() instanceof SyntaxForm)) {
            p = Translator.makePair(p, SyntaxForms.fromDatum(p.getCar(), syntax), p.getCdr());
        }
        vars[start_vars + value] = p;
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:180:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0188, code lost:
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean match(java.lang.Object r39, java.lang.Object[] r40, int r41, int r42, kawa.lang.SyntaxForm r43) {
        /*
            Method dump skipped, instructions count: 700
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.SyntaxPattern.match(java.lang.Object, java.lang.Object[], int, int, kawa.lang.SyntaxForm):boolean");
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.program);
        out.writeObject(this.literals);
        out.writeInt(this.varCount);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.literals = (Object[]) in.readObject();
        this.program = (String) in.readObject();
        this.varCount = in.readInt();
    }

    public static Object[] allocVars(int varCount, Object[] outer) {
        Object[] vars = new Object[varCount];
        if (outer != null) {
            System.arraycopy(outer, 0, vars, 0, outer.length);
        }
        return vars;
    }

    public static boolean literalIdentifierEq(Object id1, ScopeExp sc1, Object id2, ScopeExp sc2) {
        if (id1 == id2 || !(id1 == null || id2 == null || !id1.equals(id2))) {
            if (sc1 != sc2) {
                Declaration d1 = null;
                Declaration d2 = null;
                while (sc1 != null && !(sc1 instanceof ModuleExp)) {
                    d1 = sc1.lookup(id1);
                    if (d1 != null) {
                        break;
                    }
                    sc1 = sc1.outer;
                }
                while (sc2 != null && !(sc2 instanceof ModuleExp)) {
                    d2 = sc2.lookup(id2);
                    if (d2 != null) {
                        break;
                    }
                    sc2 = sc2.outer;
                }
                return d1 == d2;
            }
            return true;
        }
        return false;
    }

    public static Object[] getLiteralsList(Object list, SyntaxForm syntax, Translator tr) {
        Object wrapped;
        Object savePos = tr.pushPositionOf(list);
        int count = Translator.listLength(list);
        if (count < 0) {
            tr.error('e', "missing or malformed literals list");
            count = 0;
        }
        Object[] literals = new Object[count + 1];
        for (int i = 1; i <= count; i++) {
            while (list instanceof SyntaxForm) {
                SyntaxForm syntax2 = (SyntaxForm) list;
                list = syntax2.getDatum();
            }
            Pair pair = (Pair) list;
            tr.pushPositionOf(pair);
            Object literal = pair.getCar();
            if (literal instanceof SyntaxForm) {
                wrapped = literal;
                literal = ((SyntaxForm) literal).getDatum();
            } else {
                wrapped = literal;
            }
            if (!(literal instanceof Symbol)) {
                tr.error('e', "non-symbol '" + literal + "' in literals list");
            }
            literals[i] = wrapped;
            list = pair.getCdr();
        }
        tr.popPositionOf(savePos);
        return literals;
    }

    @Override // gnu.text.Printable
    public void print(Consumer out) {
        out.write("#<syntax-pattern>");
    }
}
