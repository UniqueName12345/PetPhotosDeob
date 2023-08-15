package gnu.ecmascript;

import gnu.expr.QuoteExp;
import gnu.lists.Sequence;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.Hashtable;

/* loaded from: classes.dex */
public class Lexer extends gnu.text.Lexer {
    static Hashtable reserved;
    private boolean prevWasCR;
    public static final Char lparenToken = Char.make(40);
    public static final Char rparenToken = Char.make(41);
    public static final Char lbraceToken = Char.make(123);
    public static final Char rbraceToken = Char.make(125);
    public static final Char lbracketToken = Char.make(91);
    public static final Char rbracketToken = Char.make(93);
    public static final Char dotToken = Char.make(46);
    public static final Char condToken = Char.make(63);
    public static final Char commaToken = Char.make(44);
    public static final Char colonToken = Char.make(58);
    public static final Char equalToken = Char.make(61);
    public static final Char tildeToken = Char.make(126);
    public static final Char notToken = Char.make(33);
    public static final Char semicolonToken = Char.make(59);
    public static final Object eolToken = Char.make(10);
    public static final Object eofToken = Sequence.eofValue;
    public static final Reserved elseToken = new Reserved("else", 38);
    public static final Reserved newToken = new Reserved("new", 39);

    public Lexer(InPort port) {
        super(port);
        this.prevWasCR = false;
    }

    static synchronized void initReserved() {
        synchronized (Lexer.class) {
            if (reserved == null) {
                reserved = new Hashtable(20);
                reserved.put("null", new QuoteExp(null));
                reserved.put("true", new QuoteExp(Boolean.TRUE));
                reserved.put("false", new QuoteExp(Boolean.FALSE));
                reserved.put("var", new Reserved("var", 30));
                reserved.put("if", new Reserved("if", 31));
                reserved.put("while", new Reserved("while", 32));
                reserved.put("for", new Reserved("for", 33));
                reserved.put("continue", new Reserved("continue", 34));
                reserved.put("break", new Reserved("break", 35));
                reserved.put("return", new Reserved("return", 36));
                reserved.put("with", new Reserved("with", 37));
                reserved.put("function", new Reserved("function", 41));
                reserved.put("this", new Reserved("this", 40));
                reserved.put("else", elseToken);
                reserved.put("new", newToken);
            }
        }
    }

    public static Object checkReserved(String name) {
        if (reserved == null) {
            initReserved();
        }
        return reserved.get(name);
    }

    public Double getNumericLiteral(int c) throws IOException {
        int c2;
        int next;
        double dval;
        int radix = 10;
        if (c == 48) {
            c = read();
            if (c == 120 || c == 88) {
                radix = 16;
                c = read();
            } else if (c != 46 && c != 101 && c != 69) {
                radix = 8;
            }
        }
        int i = this.port.pos;
        if (c >= 0) {
            i--;
        }
        this.port.pos = i;
        long ival = readDigitsInBuffer(this.port, radix);
        boolean digit_seen = this.port.pos > i;
        if (digit_seen && this.port.pos < this.port.limit) {
            char c3 = this.port.buffer[this.port.pos];
            if (!Character.isLetterOrDigit(c3) && c3 != '.') {
                if (ival >= 0) {
                    dval = ival;
                } else {
                    dval = IntNum.valueOf(this.port.buffer, i, this.port.pos - i, radix, false).doubleValue();
                }
                return new Double(dval);
            }
        }
        if (radix != 10) {
            error("invalid character in non-decimal number");
        }
        StringBuffer str = new StringBuffer(20);
        if (digit_seen) {
            str.append(this.port.buffer, i, this.port.pos - i);
        }
        int point_loc = -1;
        int exp = 0;
        while (true) {
            c2 = this.port.read();
            if (Character.digit((char) c2, radix) >= 0) {
                digit_seen = true;
                str.append((char) c2);
            } else {
                switch (c2) {
                    case 46:
                        if (point_loc >= 0) {
                            error("duplicate '.' in number");
                            break;
                        } else {
                            point_loc = str.length();
                            str.append('.');
                            break;
                        }
                    case 69:
                    case 101:
                        if (radix == 10 && ((next = this.port.peek()) == 43 || next == 45 || Character.digit((char) next, 10) >= 0)) {
                            if (!digit_seen) {
                                error("mantissa with no digits");
                            }
                            exp = readOptionalExponent();
                            c2 = read();
                            break;
                        }
                        break;
                }
            }
        }
        if (c2 >= 0) {
            this.port.unread();
        }
        if (exp != 0) {
            str.append('e');
            str.append(exp);
        }
        return new Double(str.toString());
    }

    public String getStringLiteral(char quote) throws IOException, SyntaxException {
        int i = this.port.pos;
        int limit = this.port.limit;
        char[] buffer = this.port.buffer;
        while (i < limit) {
            char c = buffer[i];
            if (c == quote) {
                this.port.pos = i + 1;
                return new String(buffer, i, i - i);
            } else if (c == '\\' || c == '\n' || c == '\r') {
                break;
            } else {
                i++;
            }
        }
        this.port.pos = i;
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(buffer, i, i - i);
        while (true) {
            int ch = this.port.read();
            if (ch == quote) {
                return sbuf.toString();
            }
            if (ch < 0) {
                eofError("unterminated string literal");
            }
            if (ch == 10 || ch == 13) {
                fatal("string literal not terminated before end of line");
            }
            if (ch == 92) {
                ch = this.port.read();
                switch (ch) {
                    case -1:
                        eofError("eof following '\\' in string");
                    case 10:
                    case 13:
                        fatal("line terminator following '\\' in string");
                        break;
                    case 34:
                    case 39:
                    case 92:
                        break;
                    case 98:
                        ch = 8;
                        break;
                    case 102:
                        ch = 12;
                        break;
                    case 110:
                        ch = 10;
                        break;
                    case 114:
                        ch = 13;
                        break;
                    case 116:
                        ch = 9;
                        break;
                    case 117:
                    case 120:
                        int val = 0;
                        int i2 = ch == 120 ? 2 : 4;
                        while (true) {
                            i2--;
                            if (i2 >= 0) {
                                int d = this.port.read();
                                if (d < 0) {
                                    eofError("eof following '\\" + ((char) ch) + "' in string");
                                }
                                int d2 = Character.forDigit((char) d, 16);
                                if (d2 < 0) {
                                    error("invalid char following '\\" + ((char) ch) + "' in string");
                                    val = 63;
                                } else {
                                    val = (val * 16) + d2;
                                }
                            }
                        }
                        ch = val;
                        break;
                    default:
                        if (ch >= 48 && ch <= 55) {
                            int val2 = 0;
                            int i3 = 3;
                            while (true) {
                                i3--;
                                if (i3 >= 0) {
                                    int d3 = this.port.read();
                                    if (d3 < 0) {
                                        eofError("eof in octal escape in string literal");
                                    }
                                    int d4 = Character.forDigit((char) d3, 8);
                                    if (d4 < 0) {
                                        this.port.unread_quick();
                                    } else {
                                        val2 = (val2 * 8) + d4;
                                    }
                                }
                            }
                            ch = val2;
                            break;
                        }
                        break;
                }
            }
            sbuf.append((char) ch);
        }
    }

    public String getIdentifier(int ch) throws IOException {
        int i = this.port.pos;
        int start = i - 1;
        int limit = this.port.limit;
        char[] buffer = this.port.buffer;
        while (i < limit && Character.isJavaIdentifierPart(buffer[i])) {
            i++;
        }
        this.port.pos = i;
        if (i < limit) {
            return new String(buffer, start, i - start);
        }
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(buffer, start, i - start);
        while (true) {
            int ch2 = this.port.read();
            if (ch2 < 0) {
                break;
            } else if (Character.isJavaIdentifierPart((char) ch2)) {
                sbuf.append((char) ch2);
            } else {
                this.port.unread_quick();
                break;
            }
        }
        return sbuf.toString();
    }

    public Object maybeAssignment(Object token) throws IOException, SyntaxException {
        int ch = read();
        if (ch == 61) {
            error("assignment operation not implemented");
        }
        if (ch >= 0) {
            this.port.unread_quick();
        }
        return token;
    }

    public Object getToken() throws IOException, SyntaxException {
        int ch = read();
        while (ch >= 0) {
            if (Character.isWhitespace((char) ch)) {
                if (ch == 13) {
                    this.prevWasCR = true;
                    return eolToken;
                } else if (ch == 10 && !this.prevWasCR) {
                    return eolToken;
                } else {
                    this.prevWasCR = false;
                    ch = read();
                }
            } else {
                switch (ch) {
                    case 33:
                        if (this.port.peek() == 61) {
                            this.port.skip_quick();
                            return Reserved.opNotEqual;
                        }
                        return notToken;
                    case 34:
                    case 39:
                        return new QuoteExp(getStringLiteral((char) ch));
                    case 37:
                        return maybeAssignment(Reserved.opRemainder);
                    case 38:
                        if (this.port.peek() == 38) {
                            this.port.skip_quick();
                            return maybeAssignment(Reserved.opBoolAnd);
                        }
                        return maybeAssignment(Reserved.opBitAnd);
                    case 40:
                        return lparenToken;
                    case 41:
                        return rparenToken;
                    case 42:
                        return maybeAssignment(Reserved.opTimes);
                    case 43:
                        if (this.port.peek() == 43) {
                            this.port.skip_quick();
                            return maybeAssignment(Reserved.opPlusPlus);
                        }
                        return maybeAssignment(Reserved.opPlus);
                    case 44:
                        return commaToken;
                    case 45:
                        if (this.port.peek() == 45) {
                            this.port.skip_quick();
                            return maybeAssignment(Reserved.opMinusMinus);
                        }
                        return maybeAssignment(Reserved.opMinus);
                    case 46:
                        int ch2 = this.port.peek();
                        if (ch2 >= 48 && ch2 <= 57) {
                            return new QuoteExp(getNumericLiteral(46));
                        }
                        return dotToken;
                    case 47:
                        return maybeAssignment(Reserved.opDivide);
                    case 48:
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                        return new QuoteExp(getNumericLiteral(ch));
                    case 58:
                        return colonToken;
                    case 59:
                        return semicolonToken;
                    case 60:
                        int ch3 = this.port.peek();
                        switch (ch3) {
                            case 60:
                                this.port.skip_quick();
                                return maybeAssignment(Reserved.opLshift);
                            case 61:
                                this.port.skip_quick();
                                return Reserved.opLessEqual;
                            default:
                                return Reserved.opLess;
                        }
                    case 61:
                        if (this.port.peek() == 61) {
                            this.port.skip_quick();
                            return Reserved.opEqual;
                        }
                        return equalToken;
                    case 62:
                        int ch4 = this.port.peek();
                        switch (ch4) {
                            case 61:
                                this.port.skip_quick();
                                return Reserved.opGreaterEqual;
                            case 62:
                                this.port.skip_quick();
                                if (this.port.peek() == 62) {
                                    this.port.skip_quick();
                                    return maybeAssignment(Reserved.opRshiftUnsigned);
                                }
                                return maybeAssignment(Reserved.opRshiftSigned);
                            default:
                                return Reserved.opGreater;
                        }
                    case 63:
                        return condToken;
                    case 91:
                        return lbracketToken;
                    case 93:
                        return rbracketToken;
                    case 94:
                        return maybeAssignment(Reserved.opBitXor);
                    case 123:
                        return lbraceToken;
                    case 124:
                        if (this.port.peek() == 124) {
                            this.port.skip_quick();
                            return maybeAssignment(Reserved.opBoolOr);
                        }
                        return maybeAssignment(Reserved.opBitOr);
                    case 125:
                        return rbraceToken;
                    case 126:
                        return tildeToken;
                    default:
                        if (Character.isJavaIdentifierStart((char) ch)) {
                            String word = getIdentifier(ch).intern();
                            Object token = checkReserved(word);
                            return token == null ? word : token;
                        }
                        return Char.make((char) ch);
                }
            }
        }
        return eofToken;
    }

    public static Object getToken(InPort inp) throws IOException, SyntaxException {
        return new Lexer(inp).getToken();
    }

    public static void main(String[] args) {
        Object token;
        InPort inp = InPort.inDefault();
        Lexer reader = new Lexer(inp);
        do {
            try {
                token = reader.getToken();
                OutPort out = OutPort.outDefault();
                out.print("token:");
                out.print(token);
                out.println(" [class:" + token.getClass() + "]");
            } catch (Exception ex) {
                System.err.println("caught exception:" + ex);
                return;
            }
        } while (token != Sequence.eofValue);
    }
}
