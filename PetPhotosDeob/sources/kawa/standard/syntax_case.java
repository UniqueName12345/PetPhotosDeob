package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BlockExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExitExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.math.IntNum;
import kawa.lang.Pattern;
import kawa.lang.PatternScope;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxPattern;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class syntax_case extends Syntax {
    public static final syntax_case syntax_case = new syntax_case();
    PrimProcedure call_error;

    static {
        syntax_case.setName("syntax-case");
    }

    Expression rewriteClauses(Object clauses, syntax_case_work work, Translator tr) {
        Expression output;
        Language language = tr.getLanguage();
        if (clauses == LList.Empty) {
            Expression[] args = {new QuoteExp("syntax-case"), new ReferenceExp(work.inputExpression)};
            if (this.call_error == null) {
                ClassType clas = ClassType.make("kawa.standard.syntax_case");
                Type[] argtypes = {Compilation.javaStringType, Type.objectType};
                Method method = clas.addMethod("error", argtypes, Type.objectType, 9);
                this.call_error = new PrimProcedure(method, language);
            }
            return new ApplyExp(this.call_error, args);
        }
        Object savePos = tr.pushPositionOf(clauses);
        try {
            if (clauses instanceof Pair) {
                Object clause = ((Pair) clauses).getCar();
                if (clause instanceof Pair) {
                    Pair pair = (Pair) clause;
                    PatternScope clauseScope = PatternScope.push(tr);
                    clauseScope.matchArray = tr.matchArray;
                    tr.push(clauseScope);
                    SyntaxForm syntax = null;
                    Object tail = pair.getCdr();
                    while (tail instanceof SyntaxForm) {
                        syntax = (SyntaxForm) tail;
                        tail = syntax.getDatum();
                    }
                    if (tail instanceof Pair) {
                        int outerVarCount = clauseScope.pattern_names.size();
                        SyntaxPattern pattern = new SyntaxPattern(pair.getCar(), work.literal_identifiers, tr);
                        int varCount = pattern.varCount();
                        if (varCount > work.maxVars) {
                            work.maxVars = varCount;
                        }
                        BlockExp block = new BlockExp();
                        Expression[] args2 = {new QuoteExp(pattern), new ReferenceExp(work.inputExpression), new ReferenceExp(tr.matchArray), new QuoteExp(IntNum.zero())};
                        Expression tryMatch = new ApplyExp(new PrimProcedure(Pattern.matchPatternMethod, language), args2);
                        int newVarCount = varCount - outerVarCount;
                        Expression[] inits = new Expression[newVarCount];
                        int i = newVarCount;
                        while (true) {
                            i--;
                            if (i < 0) {
                                break;
                            }
                            inits[i] = QuoteExp.undefined_exp;
                        }
                        clauseScope.inits = inits;
                        Pair pair2 = (Pair) tail;
                        if (pair2.getCdr() != LList.Empty) {
                            Expression fender = tr.rewrite_car(pair2, syntax);
                            if (pair2.getCdr() instanceof Pair) {
                                Pair pair3 = (Pair) pair2.getCdr();
                                if (pair3.getCdr() == LList.Empty) {
                                    output = new IfExp(fender, tr.rewrite_car(pair3, syntax), new ExitExp(block));
                                }
                            }
                            return tr.syntaxError("syntax-case:  bad clause");
                        }
                        output = tr.rewrite_car(pair2, syntax);
                        clauseScope.setBody(output);
                        tr.pop(clauseScope);
                        PatternScope.pop(tr);
                        block.setBody(new IfExp(tryMatch, clauseScope, new ExitExp(block)), rewriteClauses(((Pair) clauses).getCdr(), work, tr));
                        return block;
                    }
                    return tr.syntaxError("missing syntax-case output expression");
                }
            }
            return tr.syntaxError("syntax-case:  bad clause list");
        } finally {
            tr.popPositionOf(savePos);
        }
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        syntax_case_work work = new syntax_case_work();
        Object obj = form.getCdr();
        if ((obj instanceof Pair) && (((Pair) obj).getCdr() instanceof Pair)) {
            Expression[] linits = new Expression[2];
            LetExp let = new LetExp(linits);
            work.inputExpression = let.addDeclaration((Object) null);
            Declaration matchArrayOuter = tr.matchArray;
            Declaration matchArray = let.addDeclaration((Object) null);
            matchArray.setType(Compilation.objArrayType);
            matchArray.setCanRead(true);
            tr.matchArray = matchArray;
            work.inputExpression.setCanRead(true);
            tr.push(let);
            Pair form2 = (Pair) obj;
            linits[0] = tr.rewrite(form2.getCar());
            work.inputExpression.noteValue(linits[0]);
            Pair form3 = (Pair) form2.getCdr();
            work.literal_identifiers = SyntaxPattern.getLiteralsList(form3.getCar(), null, tr);
            let.body = rewriteClauses(form3.getCdr(), work, tr);
            tr.pop(let);
            Method allocVars = ClassType.make("kawa.lang.SyntaxPattern").getDeclaredMethod("allocVars", 2);
            Expression[] args = new Expression[2];
            args[0] = new QuoteExp(IntNum.make(work.maxVars));
            if (matchArrayOuter == null) {
                args[1] = QuoteExp.nullExp;
            } else {
                args[1] = new ReferenceExp(matchArrayOuter);
            }
            linits[1] = new ApplyExp(allocVars, args);
            matchArray.noteValue(linits[1]);
            tr.matchArray = matchArrayOuter;
            return let;
        }
        return tr.syntaxError("insufficiant arguments to syntax-case");
    }

    public static Object error(String kind, Object arg) {
        Translator tr = (Translator) Compilation.getCurrent();
        if (tr == null) {
            throw new RuntimeException("no match in syntax-case");
        }
        Syntax syntax = tr.getCurrentSyntax();
        String name = syntax == null ? "some syntax" : syntax.getName();
        String msg = "no matching case while expanding " + name;
        return tr.syntaxError(msg);
    }
}
