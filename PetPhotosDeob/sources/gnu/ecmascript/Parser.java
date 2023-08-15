package gnu.ecmascript;

import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.LambdaExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SetExp;
import gnu.lists.Sequence;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.Vector;
import kawa.standard.Scheme;

/* loaded from: classes.dex */
public class Parser {
    public int errors;
    Lexer lexer;
    InPort port;
    Object previous_token;
    Object token;
    public static Expression eofExpr = new QuoteExp(Sequence.eofValue);
    public static final Expression[] emptyArgs = new Expression[0];
    static Expression emptyStatement = new QuoteExp(Values.empty);

    public Parser(InPort port) {
        this.port = port;
        this.lexer = new Lexer(port);
    }

    public Expression parseConditionalExpression() throws IOException, SyntaxException {
        Expression exp1 = parseBinaryExpression(1);
        Object result = peekToken();
        if (result == Lexer.condToken) {
            skipToken();
            Expression exp2 = parseAssignmentExpression();
            if (getToken() != Lexer.colonToken) {
                return syntaxError("expected ':' in conditional expression");
            }
            Expression exp3 = parseAssignmentExpression();
            return new IfExp(exp1, exp2, exp3);
        }
        return exp1;
    }

    public Expression parseAssignmentExpression() throws IOException, SyntaxException {
        Expression exp1 = parseConditionalExpression();
        Object token = peekToken();
        if (token == Lexer.equalToken) {
            skipToken();
            Expression exp2 = parseAssignmentExpression();
            if (exp1 instanceof ReferenceExp) {
                SetExp sex = new SetExp(((ReferenceExp) exp1).getName(), exp2);
                sex.setDefining(true);
                return sex;
            }
            return syntaxError("unmplemented non-symbol ihs in assignment");
        } else if (token instanceof Reserved) {
            Reserved op = (Reserved) token;
            if (op.isAssignmentOp()) {
                skipToken();
                Expression exp22 = parseAssignmentExpression();
                Expression[] args = {exp1, exp22};
                return new ApplyExp((Expression) new QuoteExp(op.proc), args);
            }
            return exp1;
        } else {
            return exp1;
        }
    }

    public Expression parseExpression() throws IOException, SyntaxException {
        Expression[] exps = null;
        int nExps = 0;
        while (true) {
            Expression exp1 = parseAssignmentExpression();
            boolean last = peekToken() != Lexer.commaToken;
            if (exps == null) {
                if (!last) {
                    exps = new Expression[2];
                } else {
                    return exp1;
                }
            } else if (!last ? exps.length <= nExps : exps.length != nExps + 1) {
                int newsize = last ? nExps + 1 : exps.length * 2;
                Expression[] new_exps = new Expression[newsize];
                System.arraycopy(exps, 0, new_exps, 0, nExps);
                exps = new_exps;
            }
            int nExps2 = nExps + 1;
            exps[nExps] = exp1;
            if (last) {
                return new BeginExp(exps);
            }
            skipToken();
            nExps = nExps2;
        }
    }

    public Object peekTokenOrLine() throws IOException, SyntaxException {
        if (this.token == null) {
            this.token = this.lexer.getToken();
        }
        return this.token;
    }

    public Object peekToken() throws IOException, SyntaxException {
        if (this.token == null) {
            this.token = this.lexer.getToken();
        }
        while (this.token == Lexer.eolToken) {
            skipToken();
            this.token = this.lexer.getToken();
        }
        return this.token;
    }

    public Object getToken() throws IOException, SyntaxException {
        Object result = peekToken();
        skipToken();
        return result;
    }

    public final void skipToken() {
        if (this.token != Lexer.eofToken) {
            this.previous_token = this.token;
            this.token = null;
        }
    }

    public void getSemicolon() throws IOException, SyntaxException {
        this.token = peekToken();
        if (this.token == Lexer.semicolonToken) {
            skipToken();
        } else if (this.token != Lexer.rbraceToken && this.token != Lexer.eofToken && this.previous_token != Lexer.eolToken) {
            syntaxError("missing ';' after expression");
        }
    }

    public Expression parsePrimaryExpression() throws IOException, SyntaxException {
        Object result = getToken();
        if (result instanceof QuoteExp) {
            return (QuoteExp) result;
        }
        if (result instanceof String) {
            return new ReferenceExp((String) result);
        }
        if (result == Lexer.lparenToken) {
            Expression expr = parseExpression();
            Object token = getToken();
            return token != Lexer.rparenToken ? syntaxError("expected ')' - got:" + token) : expr;
        }
        return syntaxError("unexpected token: " + result);
    }

    public Expression makePropertyAccessor(Expression exp, Expression prop) {
        return null;
    }

    public Expression[] parseArguments() throws IOException, SyntaxException {
        skipToken();
        if (peekToken() == Lexer.rparenToken) {
            skipToken();
            return emptyArgs;
        }
        Vector args = new Vector(10);
        while (true) {
            Expression arg = parseAssignmentExpression();
            args.addElement(arg);
            Object token = getToken();
            if (token != Lexer.rparenToken) {
                if (token != Lexer.commaToken) {
                    syntaxError("invalid token '" + token + "' in argument list");
                }
            } else {
                Expression[] exps = new Expression[args.size()];
                args.copyInto(exps);
                return exps;
            }
        }
    }

    public Expression makeNewExpression(Expression exp, Expression[] args) {
        if (args == null) {
            args = emptyArgs;
        }
        return new ApplyExp((Expression) null, args);
    }

    public Expression makeCallExpression(Expression exp, Expression[] args) {
        return new ApplyExp(exp, args);
    }

    public String getIdentifier() throws IOException, SyntaxException {
        Object token = getToken();
        if (token instanceof String) {
            return (String) token;
        }
        syntaxError("missing identifier");
        return "??";
    }

    public Expression parseLeftHandSideExpression() throws IOException, SyntaxException {
        int newCount = 0;
        while (peekToken() == Lexer.newToken) {
            newCount++;
            skipToken();
        }
        Expression exp = parsePrimaryExpression();
        while (true) {
            Object token = peekToken();
            if (token == Lexer.dotToken) {
                skipToken();
                String name = getIdentifier();
                exp = makePropertyAccessor(exp, new QuoteExp(name));
            } else if (token == Lexer.lbracketToken) {
                skipToken();
                Expression prop = parseExpression();
                Object token2 = getToken();
                if (token2 != Lexer.rbracketToken) {
                    return syntaxError("expected ']' - got:" + token2);
                }
                exp = makePropertyAccessor(exp, prop);
            } else if (token == Lexer.lparenToken) {
                Expression[] args = parseArguments();
                System.err.println("after parseArgs:" + peekToken());
                if (newCount > 0) {
                    exp = makeNewExpression(exp, args);
                    newCount--;
                } else {
                    exp = makeCallExpression(exp, args);
                }
            } else {
                while (newCount > 0) {
                    exp = makeNewExpression(exp, null);
                    newCount--;
                }
                return exp;
            }
        }
    }

    public Expression parsePostfixExpression() throws IOException, SyntaxException {
        Expression exp = parseLeftHandSideExpression();
        Object op = peekTokenOrLine();
        if (op == Reserved.opPlusPlus || op == Reserved.opMinusMinus) {
            skipToken();
            Expression[] args = {exp};
            return new ApplyExp((Expression) new QuoteExp(((Reserved) op).proc), args);
        }
        return exp;
    }

    public Expression parseUnaryExpression() throws IOException, SyntaxException {
        return parsePostfixExpression();
    }

    public Expression syntaxError(String message) {
        this.errors++;
        OutPort err = OutPort.errDefault();
        String current_filename = this.port.getName();
        int current_line = this.port.getLineNumber() + 1;
        int current_column = this.port.getColumnNumber() + 1;
        if (current_line > 0) {
            if (current_filename != null) {
                err.print(current_filename);
            }
            err.print(':');
            err.print(current_line);
            if (current_column > 1) {
                err.print(':');
                err.print(current_column);
            }
            err.print(": ");
        }
        err.println(message);
        return new ErrorExp(message);
    }

    public Expression parseBinaryExpression(int prio) throws IOException, SyntaxException {
        Expression exp1 = parseUnaryExpression();
        while (true) {
            this.token = peekToken();
            if (!(this.token instanceof Reserved)) {
                break;
            }
            Reserved op = (Reserved) this.token;
            if (op.prio < prio) {
                break;
            }
            getToken();
            Expression exp2 = parseBinaryExpression(op.prio + 1);
            Expression[] args = {exp1, exp2};
            exp1 = new ApplyExp((Expression) new QuoteExp(op.proc), args);
        }
        return exp1;
    }

    public Expression parseIfStatement() throws IOException, SyntaxException {
        Expression else_part;
        skipToken();
        Object token = getToken();
        if (token != Lexer.lparenToken) {
            return syntaxError("expected '(' - got:" + token);
        }
        Expression test_part = parseExpression();
        Object token2 = getToken();
        if (token2 != Lexer.rparenToken) {
            return syntaxError("expected ')' - got:" + token2);
        }
        Expression then_part = parseStatement();
        if (peekToken() == Lexer.elseToken) {
            skipToken();
            else_part = parseStatement();
        } else {
            else_part = null;
        }
        return new IfExp(test_part, then_part, else_part);
    }

    public Expression buildLoop(Expression init, Expression test, Expression incr, Expression body) {
        if (init != null) {
            Expression[] pair = {init, buildLoop(null, test, incr, body)};
            return new BeginExp(pair);
        }
        throw new Error("not implemented - buildLoop");
    }

    public Expression parseWhileStatement() throws IOException, SyntaxException {
        skipToken();
        Object token = getToken();
        if (token != Lexer.lparenToken) {
            return syntaxError("expected '(' - got:" + token);
        }
        Expression test_part = parseExpression();
        Object token2 = getToken();
        if (token2 != Lexer.rparenToken) {
            return syntaxError("expected ')' - got:" + token2);
        }
        Expression body = parseStatement();
        return buildLoop(null, test_part, null, body);
    }

    public Expression parseFunctionDefinition() throws IOException, SyntaxException {
        skipToken();
        String name = getIdentifier();
        Object token = getToken();
        if (token != Lexer.lparenToken) {
            return syntaxError("expected '(' - got:" + token);
        }
        Vector args = new Vector(10);
        if (peekToken() == Lexer.rparenToken) {
            skipToken();
        } else {
            while (true) {
                String arg = getIdentifier();
                args.addElement(arg);
                Object token2 = getToken();
                if (token2 == Lexer.rparenToken) {
                    break;
                } else if (token2 != Lexer.commaToken) {
                    syntaxError("invalid token '" + token2 + "' in argument list");
                }
            }
        }
        Expression body = parseBlock();
        LambdaExp lexp = new LambdaExp(body);
        lexp.setName(name);
        SetExp sexp = new SetExp(name, lexp);
        sexp.setDefining(true);
        return sexp;
    }

    public Expression parseBlock() throws IOException, SyntaxException {
        boolean last;
        Expression[] exps = null;
        if (getToken() != Lexer.lbraceToken) {
            return syntaxError("extened '{'");
        }
        int nExps = 0;
        while (true) {
            this.token = peekToken();
            if (this.token == Lexer.rbraceToken) {
                skipToken();
                if (exps == null) {
                    return emptyStatement;
                }
                last = true;
            } else {
                last = false;
            }
            if (exps == null) {
                exps = new Expression[2];
            } else if (!last ? exps.length <= nExps : exps.length != nExps) {
                int newsize = last ? nExps : exps.length * 2;
                Expression[] new_exps = new Expression[newsize];
                System.arraycopy(exps, 0, new_exps, 0, nExps);
                exps = new_exps;
            }
            if (last) {
                return new BeginExp(exps);
            }
            exps[nExps] = parseStatement();
            nExps++;
        }
    }

    public Expression parseStatement() throws IOException, SyntaxException {
        Object token = peekToken();
        if (token instanceof Reserved) {
            switch (((Reserved) token).prio) {
                case 31:
                    return parseIfStatement();
                case 32:
                    return parseWhileStatement();
                case 41:
                    return parseFunctionDefinition();
            }
        }
        if (token == Lexer.eofToken) {
            return eofExpr;
        }
        if (token == Lexer.semicolonToken) {
            skipToken();
            return emptyStatement;
        } else if (token == Lexer.lbraceToken) {
            return parseBlock();
        } else {
            Expression parseExpression = parseExpression();
            getSemicolon();
            return parseExpression;
        }
    }

    public static void main(String[] args) {
        new Scheme();
        InPort inp = InPort.inDefault();
        if (inp instanceof TtyInPort) {
            Prompter prompter = new Prompter();
            ((TtyInPort) inp).setPrompter(prompter);
        }
        Parser parser = new Parser(inp);
        OutPort out = OutPort.outDefault();
        while (true) {
            try {
                Expression expr = parser.parseStatement();
                if (expr != eofExpr) {
                    out.print("[Expression: ");
                    expr.print(out);
                    out.println("]");
                    Object result = expr.eval(Environment.user());
                    out.print("result: ");
                    out.print(result);
                    out.println();
                } else {
                    return;
                }
            } catch (Throwable ex) {
                System.err.println("caught exception:" + ex);
                ex.printStackTrace(System.err);
                return;
            }
        }
    }
}
