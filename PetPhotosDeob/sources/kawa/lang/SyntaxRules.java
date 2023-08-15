package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.ErrorExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure1;
import gnu.text.Printable;
import gnu.text.ReportFormat;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class SyntaxRules extends Procedure1 implements Printable, Externalizable {
    Object[] literal_identifiers;
    int maxVars;
    SyntaxRule[] rules;

    public SyntaxRules() {
        this.maxVars = 0;
    }

    public SyntaxRules(Object[] literal_identifiers, SyntaxRule[] rules, int maxVars) {
        this.maxVars = 0;
        this.literal_identifiers = literal_identifiers;
        this.rules = rules;
        this.maxVars = maxVars;
    }

    public SyntaxRules(Object[] literal_identifiers, Object srules, Translator tr) {
        this.maxVars = 0;
        this.literal_identifiers = literal_identifiers;
        int rules_count = Translator.listLength(srules);
        if (rules_count < 0) {
            rules_count = 0;
            tr.syntaxError("missing or invalid syntax-rules");
        }
        this.rules = new SyntaxRule[rules_count];
        SyntaxForm rules_syntax = null;
        int i = 0;
        while (i < rules_count) {
            while (srules instanceof SyntaxForm) {
                rules_syntax = (SyntaxForm) srules;
                srules = rules_syntax.getDatum();
            }
            Pair rules_pair = (Pair) srules;
            SyntaxForm rule_syntax = rules_syntax;
            Object syntax_rule = rules_pair.getCar();
            while (syntax_rule instanceof SyntaxForm) {
                SyntaxForm rule_syntax2 = syntax_rule;
                rule_syntax = rule_syntax2;
                syntax_rule = rule_syntax.getDatum();
            }
            if (!(syntax_rule instanceof Pair)) {
                tr.syntaxError("missing pattern in " + i + "'th syntax rule");
                return;
            }
            SyntaxForm pattern_syntax = rule_syntax;
            Pair syntax_rule_pair = (Pair) syntax_rule;
            Object pattern = syntax_rule_pair.getCar();
            String save_filename = tr.getFileName();
            int save_line = tr.getLineNumber();
            int save_column = tr.getColumnNumber();
            SyntaxForm template_syntax = rule_syntax;
            try {
                tr.setLine(syntax_rule_pair);
                Object syntax_rule2 = syntax_rule_pair.getCdr();
                while (syntax_rule2 instanceof SyntaxForm) {
                    template_syntax = syntax_rule2;
                    syntax_rule2 = template_syntax.getDatum();
                }
                if (!(syntax_rule2 instanceof Pair)) {
                    tr.syntaxError("missing template in " + i + "'th syntax rule");
                    return;
                }
                Pair syntax_rule_pair2 = (Pair) syntax_rule2;
                if (syntax_rule_pair2.getCdr() != LList.Empty) {
                    tr.syntaxError("junk after " + i + "'th syntax rule");
                    return;
                }
                Object template = syntax_rule_pair2.getCar();
                PatternScope patternScope = PatternScope.push(tr);
                tr.push(patternScope);
                while (pattern instanceof SyntaxForm) {
                    pattern_syntax = pattern;
                    pattern = pattern_syntax.getDatum();
                }
                StringBuffer programbuf = new StringBuffer();
                if (!(pattern instanceof Pair)) {
                    tr.syntaxError("pattern does not start with name");
                    return;
                }
                literal_identifiers[0] = ((Pair) pattern).getCar();
                Pair p = (Pair) pattern;
                programbuf.append('\f');
                programbuf.append((char) 24);
                Object pattern2 = p.getCdr();
                SyntaxPattern spattern = new SyntaxPattern(programbuf, pattern2, pattern_syntax, literal_identifiers, tr);
                this.rules[i] = new SyntaxRule(spattern, template, template_syntax, tr);
                PatternScope.pop(tr);
                tr.pop();
                tr.setLine(save_filename, save_line, save_column);
                i++;
                srules = rules_pair.getCdr();
            } finally {
                tr.setLine(save_filename, save_line, save_column);
            }
        }
        int i2 = this.rules.length;
        while (true) {
            i2--;
            if (i2 < 0) {
                return;
            }
            int size = this.rules[i2].patternNesting.length();
            if (size > this.maxVars) {
                this.maxVars = size;
            }
        }
    }

    @Override // gnu.mapping.Procedure1, gnu.mapping.Procedure
    public Object apply1(Object arg) {
        if (arg instanceof SyntaxForm) {
            SyntaxForm sf = (SyntaxForm) arg;
            Translator tr = (Translator) Compilation.getCurrent();
            ScopeExp save_scope = tr.currentScope();
            tr.setCurrentScope(sf.getScope());
            try {
                return expand(sf, tr);
            } finally {
                tr.setCurrentScope(save_scope);
            }
        }
        return expand(arg, (Translator) Compilation.getCurrent());
    }

    public Object expand(Object obj, Translator tr) {
        Object[] vars = new Object[this.maxVars];
        Macro macro = (Macro) tr.getCurrentSyntax();
        for (int i = 0; i < this.rules.length; i++) {
            SyntaxRule rule = this.rules[i];
            if (rule == null) {
                return new ErrorExp("error defining " + macro);
            }
            Pattern pattern = rule.pattern;
            boolean matched = pattern.match(obj, vars, 0);
            if (matched) {
                return rule.execute(vars, tr, TemplateScope.make(tr));
            }
        }
        return tr.syntaxError("no matching syntax-rule for " + this.literal_identifiers[0]);
    }

    @Override // gnu.text.Printable
    public void print(Consumer out) {
        out.write("#<macro ");
        ReportFormat.print(this.literal_identifiers[0], out);
        out.write(62);
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.literal_identifiers);
        out.writeObject(this.rules);
        out.writeInt(this.maxVars);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.literal_identifiers = (Object[]) in.readObject();
        this.rules = (SyntaxRule[]) in.readObject();
        this.maxVars = in.readInt();
    }
}
