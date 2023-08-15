package gnu.xquery.lang;

import gnu.bytecode.Access;
import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.kawa.functions.Convert;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.reflect.InstanceOf;
import gnu.kawa.reflect.OccurrenceType;
import gnu.kawa.reflect.SingletonType;
import gnu.kawa.xml.DescendantOrSelfAxis;
import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.MakeAttribute;
import gnu.kawa.xml.MakeElement;
import gnu.kawa.xml.MakeWithBaseUri;
import gnu.kawa.xml.NodeType;
import gnu.kawa.xml.ParentAxis;
import gnu.kawa.xml.ProcessingInstructionType;
import gnu.kawa.xml.TreeScanner;
import gnu.kawa.xml.XDataType;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.TtyInPort;
import gnu.mapping.WrappedException;
import gnu.math.IntNum;
import gnu.text.FilePath;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.Path;
import gnu.text.SourceError;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.text.URIPath;
import gnu.xml.NamespaceBinding;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import gnu.xquery.util.CastableAs;
import gnu.xquery.util.NamedCollator;
import gnu.xquery.util.QNameUtils;
import gnu.xquery.util.RelativeStep;
import gnu.xquery.util.ValuesFilter;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

/* loaded from: classes.dex */
public class XQParser extends Lexer {
    static final int ARROW_TOKEN = 82;
    static final int ATTRIBUTE_TOKEN = 252;
    static final int AXIS_ANCESTOR = 0;
    static final int AXIS_ANCESTOR_OR_SELF = 1;
    static final int AXIS_ATTRIBUTE = 2;
    static final int AXIS_CHILD = 3;
    static final int AXIS_DESCENDANT = 4;
    static final int AXIS_DESCENDANT_OR_SELF = 5;
    static final int AXIS_FOLLOWING = 6;
    static final int AXIS_FOLLOWING_SIBLING = 7;
    static final int AXIS_NAMESPACE = 8;
    static final int AXIS_PARENT = 9;
    static final int AXIS_PRECEDING = 10;
    static final int AXIS_PRECEDING_SIBLING = 11;
    static final int AXIS_SELF = 12;
    static final int CASE_DOLLAR_TOKEN = 247;
    static final int COLON_COLON_TOKEN = 88;
    static final int COLON_EQUAL_TOKEN = 76;
    static final int COMMENT_TOKEN = 254;
    static final int COUNT_OP_AXIS = 13;
    static final char DECIMAL_TOKEN = '1';
    static final int DECLARE_BASE_URI_TOKEN = 66;
    static final int DECLARE_BOUNDARY_SPACE_TOKEN = 83;
    static final int DECLARE_CONSTRUCTION_TOKEN = 75;
    static final int DECLARE_COPY_NAMESPACES_TOKEN = 76;
    static final int DECLARE_FUNCTION_TOKEN = 80;
    static final int DECLARE_NAMESPACE_TOKEN = 78;
    static final int DECLARE_OPTION_TOKEN = 111;
    static final int DECLARE_ORDERING_TOKEN = 85;
    static final int DECLARE_VARIABLE_TOKEN = 86;
    static final int DEFAULT_COLLATION_TOKEN = 71;
    static final int DEFAULT_ELEMENT_TOKEN = 69;
    static final int DEFAULT_FUNCTION_TOKEN = 79;
    static final int DEFAULT_ORDER_TOKEN = 72;
    static final int DEFINE_QNAME_TOKEN = 87;
    static final int DOCUMENT_TOKEN = 256;
    static final int DOTDOT_TOKEN = 51;
    static final char DOUBLE_TOKEN = '2';
    static final int ELEMENT_TOKEN = 251;
    static final int EOF_TOKEN = -1;
    static final int EOL_TOKEN = 10;
    static final int EVERY_DOLLAR_TOKEN = 246;
    static final int FNAME_TOKEN = 70;
    static final int FOR_DOLLAR_TOKEN = 243;
    static final int IF_LPAREN_TOKEN = 241;
    static final int IMPORT_MODULE_TOKEN = 73;
    static final int IMPORT_SCHEMA_TOKEN = 84;
    static final char INTEGER_TOKEN = '0';
    static final int LET_DOLLAR_TOKEN = 244;
    static final int MODULE_NAMESPACE_TOKEN = 77;
    static final int NCNAME_COLON_TOKEN = 67;
    static final int NCNAME_TOKEN = 65;
    static final int OP_ADD = 413;
    static final int OP_AND = 401;
    static final int OP_ATTRIBUTE = 236;
    static final int OP_AXIS_FIRST = 100;
    static final int OP_BASE = 400;
    static final int OP_CASTABLE_AS = 424;
    static final int OP_CAST_AS = 425;
    static final int OP_COMMENT = 232;
    static final int OP_DIV = 416;
    static final int OP_DOCUMENT = 234;
    static final int OP_ELEMENT = 235;
    static final int OP_EMPTY_SEQUENCE = 238;
    static final int OP_EQ = 426;
    static final int OP_EQU = 402;
    static final int OP_EXCEPT = 421;
    static final int OP_GE = 431;
    static final int OP_GEQ = 407;
    static final int OP_GRT = 405;
    static final int OP_GRTGRT = 410;
    static final int OP_GT = 430;
    static final int OP_IDIV = 417;
    static final int OP_INSTANCEOF = 422;
    static final int OP_INTERSECT = 420;
    static final int OP_IS = 408;
    static final int OP_ISNOT = 409;
    static final int OP_ITEM = 237;
    static final int OP_LE = 429;
    static final int OP_LEQ = 406;
    static final int OP_LSS = 404;
    static final int OP_LSSLSS = 411;
    static final int OP_LT = 428;
    static final int OP_MOD = 418;
    static final int OP_MUL = 415;
    static final int OP_NE = 427;
    static final int OP_NEQ = 403;
    static final int OP_NODE = 230;
    static final int OP_OR = 400;
    static final int OP_PI = 233;
    static final int OP_RANGE_TO = 412;
    static final int OP_SCHEMA_ATTRIBUTE = 239;
    static final int OP_SCHEMA_ELEMENT = 240;
    static final int OP_SUB = 414;
    static final int OP_TEXT = 231;
    static final int OP_TREAT_AS = 423;
    static final int OP_UNION = 419;
    static final int OP_WHERE = 196;
    static final int ORDERED_LBRACE_TOKEN = 249;
    static final int PI_TOKEN = 255;
    static final int PRAGMA_START_TOKEN = 197;
    static final int QNAME_TOKEN = 81;
    static final int SLASHSLASH_TOKEN = 68;
    static final int SOME_DOLLAR_TOKEN = 245;
    static final int STRING_TOKEN = 34;
    static final int TEXT_TOKEN = 253;
    static final int TYPESWITCH_LPAREN_TOKEN = 242;
    static final int UNORDERED_LBRACE_TOKEN = 250;
    static final int VALIDATE_LBRACE_TOKEN = 248;
    static final int XQUERY_VERSION_TOKEN = 89;
    public static final String[] axisNames;
    static NamespaceBinding builtinNamespaces;
    public static final QuoteExp getExternalFunction;
    Path baseURI;
    boolean baseURIDeclarationSeen;
    boolean boundarySpaceDeclarationSeen;
    boolean boundarySpacePreserve;
    int commentCount;
    Compilation comp;
    boolean constructionModeDeclarationSeen;
    boolean constructionModeStrip;
    NamespaceBinding constructorNamespaces;
    boolean copyNamespacesDeclarationSeen;
    int copyNamespacesMode;
    int curColumn;
    int curLine;
    int curToken;
    Object curValue;
    NamedCollator defaultCollator;
    String defaultElementNamespace;
    char defaultEmptyOrder;
    boolean emptyOrderDeclarationSeen;
    int enclosedExpressionsSeen;
    String errorIfComment;
    Declaration[] flworDecls;
    int flworDeclsCount;
    int flworDeclsFirst;
    public Namespace[] functionNamespacePath;
    XQuery interpreter;
    String libraryModuleNamespace;
    boolean orderingModeSeen;
    boolean orderingModeUnordered;
    int parseContext;
    int parseCount;
    NamespaceBinding prologNamespaces;
    private int saveToken;
    private Object saveValue;
    boolean seenDeclaration;
    int seenLast;
    int seenPosition;
    private boolean warnedOldStyleKindTest;
    public static boolean warnOldVersion = true;
    public static boolean warnHidePreviousDeclaration = false;
    static final Symbol DOT_VARNAME = Symbol.makeUninterned("$dot$");
    static final Symbol POSITION_VARNAME = Symbol.makeUninterned("$position$");
    static final Symbol LAST_VARNAME = Symbol.makeUninterned("$last$");
    public static final InstanceOf instanceOf = new InstanceOf(XQuery.getInstance(), "instance");
    public static final CastableAs castableAs = CastableAs.castableAs;
    public static final Convert treatAs = Convert.as;
    static PrimProcedure proc_OccurrenceType_getInstance = new PrimProcedure(ClassType.make("gnu.kawa.reflect.OccurrenceType").getDeclaredMethod("getInstance", 3));
    public static QuoteExp makeChildAxisStep = QuoteExp.getInstance(new PrimProcedure("gnu.kawa.xml.ChildAxis", "make", 1));
    public static QuoteExp makeDescendantAxisStep = QuoteExp.getInstance(new PrimProcedure("gnu.kawa.xml.DescendantAxis", "make", 1));
    public static Expression makeText = makeFunctionExp("gnu.kawa.xml.MakeText", "makeText");
    static final Expression makeCDATA = makeFunctionExp("gnu.kawa.xml.MakeCDATA", "makeCDATA");

    static {
        NamespaceBinding ns = NamespaceBinding.predefinedXML;
        builtinNamespaces = new NamespaceBinding("local", XQuery.LOCAL_NAMESPACE, new NamespaceBinding("qexo", XQuery.QEXO_FUNCTION_NAMESPACE, new NamespaceBinding("kawa", XQuery.KAWA_FUNCTION_NAMESPACE, new NamespaceBinding("html", "http://www.w3.org/1999/xhtml", new NamespaceBinding("fn", XQuery.XQUERY_FUNCTION_NAMESPACE, new NamespaceBinding("xsi", XQuery.SCHEMA_INSTANCE_NAMESPACE, new NamespaceBinding("xs", XQuery.SCHEMA_NAMESPACE, new NamespaceBinding("xml", NamespaceBinding.XML_NAMESPACE, ns))))))));
        getExternalFunction = QuoteExp.getInstance(new PrimProcedure("gnu.xquery.lang.XQuery", "getExternal", 2));
        axisNames = new String[13];
        axisNames[0] = "ancestor";
        axisNames[1] = "ancestor-or-self";
        axisNames[2] = "attribute";
        axisNames[3] = "child";
        axisNames[4] = "descendant";
        axisNames[5] = "descendant-or-self";
        axisNames[6] = "following";
        axisNames[7] = "following-sibling";
        axisNames[8] = "namespace";
        axisNames[9] = "parent";
        axisNames[10] = "preceding";
        axisNames[11] = "preceding-sibling";
        axisNames[12] = "self";
    }

    public void setStaticBaseUri(String uri) {
        try {
            this.baseURI = fixupStaticBaseUri(URIPath.valueOf(uri));
        } catch (Throwable th) {
            ex = th;
            if (ex instanceof WrappedException) {
                ex = ((WrappedException) ex).getCause();
            }
            error('e', "invalid URI: " + ex.getMessage());
        }
    }

    static Path fixupStaticBaseUri(Path path) {
        Path path2 = path.getAbsolute();
        if (path2 instanceof FilePath) {
            return URIPath.valueOf(path2.toURI());
        }
        return path2;
    }

    public String getStaticBaseUri() {
        LineBufferedReader port;
        Path path = this.baseURI;
        if (path == null) {
            Environment env = Environment.getCurrent();
            Object value = env.get(Symbol.make("", "base-uri"), null, null);
            if (value != null && !(value instanceof Path)) {
                path = URIPath.valueOf(value.toString());
            }
            if (path == null && (port = getPort()) != null) {
                path = port.getPath();
                if ((path instanceof FilePath) && (!path.exists() || (port instanceof TtyInPort) || (port instanceof CharArrayInPort))) {
                    path = null;
                }
            }
            if (path == null) {
                path = Path.currentPath();
            }
            path = fixupStaticBaseUri(path);
            this.baseURI = path;
        }
        return path.toString();
    }

    public String resolveAgainstBaseUri(String uri) {
        if (!Path.uriSchemeSpecified(uri)) {
            String base = getStaticBaseUri();
            Path basePath = Path.valueOf(base);
            return basePath.resolve(uri).toString();
        }
        return uri;
    }

    final int skipSpace() throws IOException, SyntaxException {
        return skipSpace(true);
    }

    final int skipSpace(boolean verticalToo) throws IOException, SyntaxException {
        int ch;
        while (true) {
            ch = read();
            if (ch == 40) {
                if (!checkNext(':')) {
                    return 40;
                }
                skipComment();
            } else if (ch == 123) {
                int ch2 = read();
                if (ch2 != 45) {
                    unread(ch2);
                    return 123;
                }
                int ch3 = read();
                if (ch3 != 45) {
                    unread(ch3);
                    unread(45);
                    return 123;
                }
                skipOldComment();
            } else if (!verticalToo) {
                if (ch != 32 && ch != 9) {
                    break;
                }
            } else if (ch < 0 || !Character.isWhitespace((char) ch)) {
                break;
            }
        }
        return ch;
    }

    final void skipToSemicolon() throws IOException {
        int next;
        do {
            next = read();
            if (next < 0) {
                return;
            }
        } while (next != 59);
    }

    final void skipOldComment() throws IOException, SyntaxException {
        int seenDashes = 0;
        int startLine = getLineNumber() + 1;
        int startColumn = getColumnNumber() - 2;
        warnOldVersion("use (: :) instead of old-style comment {-- --}");
        while (true) {
            int ch = read();
            if (ch == 45) {
                seenDashes++;
            } else if (ch == 125 && seenDashes >= 2) {
                return;
            } else {
                if (ch < 0) {
                    this.curLine = startLine;
                    this.curColumn = startColumn;
                    eofError("non-terminated comment starting here");
                } else {
                    seenDashes = 0;
                }
            }
        }
    }

    final void skipComment() throws IOException, SyntaxException {
        this.commentCount++;
        int startLine = getLineNumber() + 1;
        int startColumn = getColumnNumber() - 1;
        if (this.errorIfComment != null) {
            this.curLine = startLine;
            this.curColumn = startColumn;
            error('e', this.errorIfComment);
        }
        int prev = 0;
        int commentNesting = 0;
        char saveReadState = pushNesting(':');
        while (true) {
            int ch = read();
            if (ch == 58) {
                if (prev == 40) {
                    commentNesting++;
                    ch = 0;
                }
            } else if (ch == 41 && prev == 58) {
                if (commentNesting == 0) {
                    popNesting(saveReadState);
                    return;
                }
                commentNesting--;
            } else if (ch < 0) {
                this.curLine = startLine;
                this.curColumn = startColumn;
                eofError("non-terminated comment starting here");
            }
            prev = ch;
        }
    }

    final int peekNonSpace(String message) throws IOException, SyntaxException {
        int ch = skipSpace();
        if (ch < 0) {
            eofError(message);
        }
        unread(ch);
        return ch;
    }

    @Override // gnu.text.Lexer
    public void mark() throws IOException {
        super.mark();
        this.saveToken = this.curToken;
        this.saveValue = this.curValue;
    }

    @Override // gnu.text.Lexer, java.io.Reader
    public void reset() throws IOException {
        this.curToken = this.saveToken;
        this.curValue = this.saveValue;
        super.reset();
    }

    private int setToken(int token, int width) {
        this.curToken = token;
        this.curLine = this.port.getLineNumber() + 1;
        this.curColumn = (this.port.getColumnNumber() + 1) - width;
        return token;
    }

    void checkSeparator(char ch) {
        if (XName.isNameStart(ch)) {
            error('e', "missing separator", "XPST0003");
        }
    }

    int getRawToken() throws IOException, SyntaxException {
        int next;
        int next2;
        while (true) {
            int next3 = readUnicodeChar();
            if (next3 < 0) {
                return setToken(-1, 0);
            }
            if (next3 == 10 || next3 == 13) {
                if (this.nesting <= 0) {
                    return setToken(10, 0);
                }
            } else if (next3 == 40) {
                if (checkNext(':')) {
                    skipComment();
                } else if (checkNext('#')) {
                    return setToken(PRAGMA_START_TOKEN, 2);
                } else {
                    return setToken(40, 1);
                }
            } else if (next3 == 123) {
                if (!checkNext('-')) {
                    return setToken(123, 1);
                }
                if (read() != 45) {
                    unread();
                    unread();
                    return setToken(123, 1);
                }
                skipOldComment();
            } else if (next3 != 32 && next3 != 9) {
                this.tokenBufferLength = 0;
                this.curLine = this.port.getLineNumber() + 1;
                this.curColumn = this.port.getColumnNumber();
                char ch = (char) next3;
                switch (ch) {
                    case '!':
                        if (checkNext('=')) {
                            ch = 403;
                            break;
                        }
                        break;
                    case '\"':
                    case '\'':
                        char saveReadState = pushNesting((char) next3);
                        while (true) {
                            int next4 = readUnicodeChar();
                            if (next4 < 0) {
                                eofError("unexpected end-of-file in string starting here");
                            }
                            if (next4 == 38) {
                                parseEntityOrCharRef();
                            } else {
                                if (ch == next4) {
                                    if (ch == peek()) {
                                        next4 = read();
                                    } else {
                                        popNesting(saveReadState);
                                        ch = '\"';
                                        break;
                                    }
                                }
                                tokenBufferAppend(next4);
                            }
                        }
                    case '$':
                    case ')':
                    case ',':
                    case ';':
                    case '?':
                    case '@':
                    case '[':
                    case ']':
                    case '}':
                        break;
                    case '*':
                        ch = 415;
                        break;
                    case '+':
                        ch = 413;
                        break;
                    case '-':
                        ch = 414;
                        break;
                    case '/':
                        if (checkNext('/')) {
                            ch = 'D';
                            break;
                        }
                        break;
                    case ':':
                        if (checkNext('=')) {
                            ch = 'L';
                            break;
                        } else if (checkNext(':')) {
                            ch = 'X';
                            break;
                        }
                        break;
                    case '<':
                        if (!checkNext('=')) {
                            if (!checkNext('<')) {
                                ch = 404;
                                break;
                            } else {
                                ch = 411;
                                break;
                            }
                        } else {
                            ch = 406;
                            break;
                        }
                    case '=':
                        if (checkNext('>')) {
                        }
                        ch = 402;
                        break;
                    case '>':
                        if (!checkNext('=')) {
                            if (!checkNext('>')) {
                                ch = 405;
                                break;
                            } else {
                                ch = 410;
                                break;
                            }
                        } else {
                            ch = 407;
                            break;
                        }
                    case '|':
                        ch = 419;
                        break;
                    default:
                        if (Character.isDigit(ch) || (ch == '.' && Character.isDigit((char) peek()))) {
                            boolean seenDot = ch == '.';
                            while (true) {
                                tokenBufferAppend(ch);
                                next = read();
                                if (next >= 0) {
                                    ch = (char) next;
                                    if (ch == '.') {
                                        if (!seenDot) {
                                            seenDot = true;
                                        }
                                    } else if (!Character.isDigit(ch)) {
                                    }
                                }
                            }
                            if (next == 101 || next == 69) {
                                tokenBufferAppend((char) next);
                                int next5 = read();
                                if (next5 == 43 || next5 == 45) {
                                    tokenBufferAppend(next5);
                                    next5 = read();
                                }
                                int expDigits = 0;
                                while (true) {
                                    if (next5 >= 0) {
                                        char ch2 = (char) next5;
                                        if (!Character.isDigit(ch2)) {
                                            checkSeparator(ch2);
                                            unread();
                                        } else {
                                            tokenBufferAppend(ch2);
                                            next5 = read();
                                            expDigits++;
                                        }
                                    }
                                }
                                if (expDigits == 0) {
                                    error('e', "no digits following exponent", "XPST0003");
                                }
                                ch = DOUBLE_TOKEN;
                                break;
                            } else {
                                ch = seenDot ? DECIMAL_TOKEN : INTEGER_TOKEN;
                                if (next >= 0) {
                                    checkSeparator((char) next);
                                    unread(next);
                                    break;
                                }
                            }
                        } else if (ch == '.') {
                            if (checkNext('.')) {
                                ch = '3';
                                break;
                            }
                        } else if (XName.isNameStart(ch)) {
                            do {
                                tokenBufferAppend(ch);
                                next2 = read();
                                ch = (char) next2;
                            } while (XName.isNamePart(ch));
                            if (next2 < 0) {
                                ch = 'A';
                                break;
                            } else {
                                if (next2 != 58) {
                                    ch = 'A';
                                } else {
                                    next2 = read();
                                    if (next2 < 0) {
                                        eofError("unexpected end-of-file after NAME ':'");
                                    }
                                    char ch3 = (char) next2;
                                    if (XName.isNameStart(ch3)) {
                                        tokenBufferAppend(58);
                                        do {
                                            tokenBufferAppend(ch3);
                                            next2 = read();
                                            ch3 = (char) next2;
                                        } while (XName.isNamePart(ch3));
                                        ch = 'Q';
                                    } else if (ch3 == '=') {
                                        unread(ch3);
                                        ch = 'A';
                                    } else {
                                        ch = Access.CLASS_CONTEXT;
                                    }
                                }
                                unread(next2);
                                break;
                            }
                        } else if (ch >= ' ' && ch < 127) {
                            syntaxError("invalid character '" + ch + '\'');
                            break;
                        } else {
                            syntaxError("invalid character '\\u" + Integer.toHexString(ch) + '\'');
                            break;
                        }
                        break;
                }
                this.curToken = ch;
                return ch;
            }
        }
    }

    public void getDelimited(String delimiter) throws IOException, SyntaxException {
        if (!readDelimited(delimiter)) {
            eofError("unexpected end-of-file looking for '" + delimiter + '\'');
        }
    }

    public void appendNamedEntity(String name) {
        String name2 = name.intern();
        char ch = '?';
        if (name2 == "lt") {
            ch = '<';
        } else if (name2 == "gt") {
            ch = '>';
        } else if (name2 == "amp") {
            ch = '&';
        } else if (name2 == "quot") {
            ch = '\"';
        } else if (name2 == "apos") {
            ch = '\'';
        } else {
            error("unknown enity reference: '" + name2 + "'");
        }
        tokenBufferAppend(ch);
    }

    boolean match(String word1, String word2, boolean force) throws IOException, SyntaxException {
        if (match(word1)) {
            mark();
            getRawToken();
            if (match(word2)) {
                reset();
                getRawToken();
                return true;
            }
            reset();
            if (force) {
                error('e', "'" + word1 + "' must be followed by '" + word2 + "'", "XPST0003");
                return true;
            }
        }
        return false;
    }

    int peekOperator() throws IOException, SyntaxException {
        while (this.curToken == 10) {
            if (this.nesting == 0) {
                return 10;
            }
            getRawToken();
        }
        if (this.curToken == 65) {
            int len = this.tokenBufferLength;
            switch (len) {
                case 2:
                    char c1 = this.tokenBuffer[0];
                    char c2 = this.tokenBuffer[1];
                    if (c1 == 'o' && c2 == 'r') {
                        this.curToken = 400;
                        break;
                    } else if (c1 == 't' && c2 == 'o') {
                        this.curToken = 412;
                        break;
                    } else if (c1 == 'i' && c2 == 's') {
                        this.curToken = 408;
                        break;
                    } else if (c1 == 'e' && c2 == 'q') {
                        this.curToken = OP_EQ;
                        break;
                    } else if (c1 == 'n' && c2 == 'e') {
                        this.curToken = OP_NE;
                        break;
                    } else if (c1 == 'g') {
                        if (c2 != 'e') {
                            if (c2 == 't') {
                                this.curToken = OP_GT;
                                break;
                            }
                        } else {
                            this.curToken = OP_GE;
                            break;
                        }
                    } else if (c1 == 'l') {
                        if (c2 != 'e') {
                            if (c2 == 't') {
                                this.curToken = OP_LT;
                                break;
                            }
                        } else {
                            this.curToken = OP_LE;
                            break;
                        }
                    }
                    break;
                case 3:
                    char c12 = this.tokenBuffer[0];
                    char c22 = this.tokenBuffer[1];
                    char c3 = this.tokenBuffer[2];
                    if (c12 == 'a') {
                        if (c22 == 'n' && c3 == 'd') {
                            this.curToken = 401;
                            break;
                        }
                    } else if (c12 == 'm') {
                        if (c22 == 'u' && c3 == 'l') {
                            this.curToken = 415;
                        }
                        if (c22 == 'o' && c3 == 'd') {
                            this.curToken = 418;
                            break;
                        }
                    } else if (c12 == 'd' && c22 == 'i' && c3 == 'v') {
                        this.curToken = 416;
                        break;
                    }
                    break;
                case 4:
                    if (match("idiv")) {
                        this.curToken = 417;
                        break;
                    } else if (match("cast", "as", true)) {
                        this.curToken = OP_CAST_AS;
                        break;
                    }
                    break;
                case 5:
                    if (match("where")) {
                        this.curToken = 196;
                        break;
                    } else if (match("isnot")) {
                        this.curToken = 409;
                        break;
                    } else if (match("union")) {
                        this.curToken = 419;
                        break;
                    } else if (match("treat", "as", true)) {
                        this.curToken = 423;
                        break;
                    }
                    break;
                case 6:
                    if (match("except")) {
                        this.curToken = 421;
                        break;
                    }
                    break;
                case 8:
                    if (match("instance", "of", true)) {
                        this.curToken = 422;
                        break;
                    } else if (match("castable", "as", true)) {
                        this.curToken = OP_CASTABLE_AS;
                        break;
                    }
                    break;
                case 9:
                    if (match("intersect")) {
                        this.curToken = 420;
                        break;
                    }
                    break;
                case 10:
                    if (match("instanceof")) {
                        warnOldVersion("use 'instanceof of' (two words) instead of 'instanceof'");
                        this.curToken = 422;
                        break;
                    }
                    break;
            }
        }
        return this.curToken;
    }

    private boolean lookingAt(String word0, String word1) throws IOException, SyntaxException {
        if (word0.equals(this.curValue)) {
            int i = 0;
            int len = word1.length();
            while (true) {
                int ch = read();
                if (i == len) {
                    if (ch < 0) {
                        return true;
                    }
                    if (!XName.isNamePart((char) ch)) {
                        unread();
                        return true;
                    }
                    i++;
                } else if (ch < 0) {
                    break;
                } else {
                    int i2 = i + 1;
                    if (ch != word1.charAt(i)) {
                        i = i2;
                        break;
                    }
                    i = i2;
                }
            }
            this.port.skip(-i);
            return false;
        }
        return false;
    }

    int getAxis() {
        String name = new String(this.tokenBuffer, 0, this.tokenBufferLength).intern();
        int i = 13;
        do {
            i--;
            if (i < 0) {
                break;
            }
        } while (axisNames[i] != name);
        if (i < 0 || i == 8) {
            error('e', "unknown axis name '" + name + '\'', "XPST0003");
            i = 3;
        }
        return (char) (i + 100);
    }

    /* JADX WARN: Removed duplicated region for block: B:128:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x02b1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    int peekOperand() throws java.io.IOException, gnu.text.SyntaxException {
        /*
            Method dump skipped, instructions count: 1074
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.peekOperand():int");
    }

    void checkAllowedNamespaceDeclaration(String prefix, String uri, boolean inConstructor) {
        boolean xmlPrefix = "xml".equals(prefix);
        if (NamespaceBinding.XML_NAMESPACE.equals(uri)) {
            if (!xmlPrefix || !inConstructor) {
                error('e', "namespace uri cannot be the same as the prefined xml namespace", "XQST0070");
            }
        } else if (xmlPrefix || "xmlns".equals(prefix)) {
            error('e', "namespace prefix cannot be 'xml' or 'xmlns'", "XQST0070");
        }
    }

    void pushNamespace(String prefix, String uri) {
        if (uri.length() == 0) {
            uri = null;
        }
        this.prologNamespaces = new NamespaceBinding(prefix, uri, this.prologNamespaces);
    }

    public XQParser(InPort port, SourceMessages messages, XQuery interp) {
        super(port, messages);
        this.defaultCollator = null;
        this.defaultEmptyOrder = 'L';
        this.baseURI = null;
        this.copyNamespacesMode = 3;
        this.functionNamespacePath = XQuery.defaultFunctionNamespacePath;
        this.defaultElementNamespace = "";
        this.constructorNamespaces = NamespaceBinding.predefinedXML;
        this.interpreter = interp;
        this.nesting = 1;
        NamespaceBinding ns = builtinNamespaces;
        this.prologNamespaces = ns;
    }

    @Override // gnu.text.Lexer
    public void setInteractive(boolean v) {
        if (this.interactive != v) {
            if (v) {
                this.nesting--;
            } else {
                this.nesting++;
            }
        }
        this.interactive = v;
    }

    private static final int priority(int opcode) {
        switch (opcode) {
            case 400:
                return 1;
            case 401:
                return 2;
            case 402:
            case 403:
            case 404:
            case 405:
            case 406:
            case 407:
            case 408:
            case 409:
            case 410:
            case 411:
            case OP_EQ /* 426 */:
            case OP_NE /* 427 */:
            case OP_LT /* 428 */:
            case OP_LE /* 429 */:
            case OP_GT /* 430 */:
            case OP_GE /* 431 */:
                return 3;
            case 412:
                return 4;
            case 413:
            case 414:
                return 5;
            case 415:
            case 416:
            case 417:
            case 418:
                return 6;
            case 419:
                return 7;
            case 420:
            case 421:
                return 8;
            case 422:
                return 9;
            case 423:
                return 10;
            case OP_CASTABLE_AS /* 424 */:
                return 11;
            case OP_CAST_AS /* 425 */:
                return 12;
            default:
                return 0;
        }
    }

    static Expression makeBinary(Expression func, Expression exp1, Expression exp2) {
        Expression[] args = {exp1, exp2};
        return new ApplyExp(func, args);
    }

    static Expression makeExprSequence(Expression exp1, Expression exp2) {
        return makeBinary(makeFunctionExp("gnu.kawa.functions.AppendValues", "appendValues"), exp1, exp2);
    }

    Expression makeBinary(int op, Expression exp1, Expression exp2) throws IOException, SyntaxException {
        Expression func;
        switch (op) {
            case 402:
                func = makeFunctionExp("gnu.xquery.util.Compare", "=");
                break;
            case 403:
                func = makeFunctionExp("gnu.xquery.util.Compare", "!=");
                break;
            case 404:
                func = makeFunctionExp("gnu.xquery.util.Compare", "<");
                break;
            case 405:
                func = makeFunctionExp("gnu.xquery.util.Compare", ">");
                break;
            case 406:
                func = makeFunctionExp("gnu.xquery.util.Compare", "<=");
                break;
            case 407:
                func = makeFunctionExp("gnu.xquery.util.Compare", ">=");
                break;
            case 408:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Eq", "is");
                break;
            case 409:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Ne", "isnot");
                break;
            case 410:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Gr", ">>");
                break;
            case 411:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Ls", "<<");
                break;
            case 412:
                func = makeFunctionExp("gnu.xquery.util.IntegerRange", "integerRange");
                break;
            case 413:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "add", "+");
                break;
            case 414:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "sub", "-");
                break;
            case 415:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "mul", "*");
                break;
            case 416:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "div", "div");
                break;
            case 417:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "idiv", "idiv");
                break;
            case 418:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "mod", "mod");
                break;
            case 419:
                func = makeFunctionExp("gnu.kawa.xml.UnionNodes", "unionNodes");
                break;
            case 420:
                func = makeFunctionExp("gnu.kawa.xml.IntersectNodes", "intersectNodes");
                break;
            case 421:
                func = makeFunctionExp("gnu.kawa.xml.IntersectNodes", "exceptNodes");
                break;
            case 422:
            case 423:
            case OP_CASTABLE_AS /* 424 */:
            case OP_CAST_AS /* 425 */:
            default:
                return syntaxError("unimplemented binary op: " + op);
            case OP_EQ /* 426 */:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valEq", "eq");
                break;
            case OP_NE /* 427 */:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valNe", "ne");
                break;
            case OP_LT /* 428 */:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valLt", "lt");
                break;
            case OP_LE /* 429 */:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valLe", "le");
                break;
            case OP_GT /* 430 */:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valGt", "gt");
                break;
            case OP_GE /* 431 */:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valGe", "ge");
                break;
        }
        return makeBinary(func, exp1, exp2);
    }

    private void parseSimpleKindType() throws IOException, SyntaxException {
        getRawToken();
        if (this.curToken == 41) {
            getRawToken();
        } else {
            error("expected ')'");
        }
    }

    public Expression parseNamedNodeType(boolean attribute) throws IOException, SyntaxException {
        Expression qname;
        Expression tname = null;
        getRawToken();
        if (this.curToken == 41) {
            qname = QuoteExp.getInstance(ElementType.MATCH_ANY_QNAME);
            getRawToken();
        } else {
            if (this.curToken == 81 || this.curToken == 65) {
                qname = parseNameTest(attribute);
            } else {
                if (this.curToken != 415) {
                    syntaxError("expected QName or *");
                }
                qname = QuoteExp.getInstance(ElementType.MATCH_ANY_QNAME);
            }
            getRawToken();
            if (this.curToken == 44) {
                getRawToken();
                if (this.curToken == 81 || this.curToken == 65) {
                    tname = parseDataType();
                } else {
                    syntaxError("expected QName");
                }
            }
            if (this.curToken == 41) {
                getRawToken();
            } else {
                error("expected ')' after element");
            }
        }
        return makeNamedNodeType(attribute, qname, tname);
    }

    static Expression makeNamedNodeType(boolean attribute, Expression qname, Expression tname) {
        Expression[] expressionArr = new Expression[2];
        ClassType nodeType = ClassType.make(attribute ? "gnu.kawa.xml.AttributeType" : "gnu.kawa.xml.ElementType");
        ApplyExp elt = new ApplyExp(nodeType.getDeclaredMethod("make", 1), qname);
        elt.setFlag(4);
        return tname == null ? elt : new BeginExp(tname, elt);
    }

    private void warnOldStyleKindTest() {
        if (!this.warnedOldStyleKindTest) {
            error('w', "old-style KindTest - first one here");
            this.warnedOldStyleKindTest = true;
        }
    }

    public Expression parseOptionalTypeDeclaration() throws IOException, SyntaxException {
        if (!match("as")) {
            return null;
        }
        getRawToken();
        return parseDataType();
    }

    public Expression parseDataType() throws IOException, SyntaxException {
        int min;
        int max;
        Expression etype = parseItemType();
        if (etype == null) {
            if (this.curToken != OP_EMPTY_SEQUENCE) {
                return syntaxError("bad syntax - expected DataType");
            }
            parseSimpleKindType();
            if (this.curToken == 63 || this.curToken == 413 || this.curToken == 415) {
                getRawToken();
                return syntaxError("occurrence-indicator meaningless after empty-sequence()");
            }
            etype = QuoteExp.getInstance(OccurrenceType.emptySequenceType);
            min = 0;
            max = 0;
        } else if (this.curToken == 63) {
            min = 0;
            max = 1;
        } else if (this.curToken == 413) {
            min = 1;
            max = -1;
        } else if (this.curToken == 415) {
            min = 0;
            max = -1;
        } else {
            min = 1;
            max = 1;
        }
        if (this.parseContext == 67 && max != 1) {
            return syntaxError("type to 'cast as' or 'castable as' must be a 'SingleType'");
        }
        if (min != max) {
            getRawToken();
            Expression[] args = {etype, QuoteExp.getInstance(IntNum.make(min)), QuoteExp.getInstance(IntNum.make(max))};
            ApplyExp otype = new ApplyExp(proc_OccurrenceType_getInstance, args);
            otype.setFlag(4);
            return otype;
        }
        return etype;
    }

    public Expression parseMaybeKindTest() throws IOException, SyntaxException {
        Type type;
        switch (this.curToken) {
            case OP_NODE /* 230 */:
                parseSimpleKindType();
                type = NodeType.anyNodeTest;
                break;
            case OP_TEXT /* 231 */:
                parseSimpleKindType();
                type = NodeType.textNodeTest;
                break;
            case OP_COMMENT /* 232 */:
                parseSimpleKindType();
                type = NodeType.commentNodeTest;
                break;
            case OP_PI /* 233 */:
                getRawToken();
                String piTarget = null;
                if (this.curToken == 65 || this.curToken == 34) {
                    piTarget = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                    getRawToken();
                }
                if (this.curToken == 41) {
                    getRawToken();
                } else {
                    error("expected ')'");
                }
                type = ProcessingInstructionType.getInstance(piTarget);
                break;
            case OP_DOCUMENT /* 234 */:
                parseSimpleKindType();
                type = NodeType.documentNodeTest;
                break;
            case OP_ELEMENT /* 235 */:
            case OP_ATTRIBUTE /* 236 */:
                return parseNamedNodeType(this.curToken == OP_ATTRIBUTE);
            default:
                return null;
        }
        return QuoteExp.getInstance(type);
    }

    public Expression parseItemType() throws IOException, SyntaxException {
        Type type;
        peekOperand();
        Expression etype = parseMaybeKindTest();
        if (etype != null) {
            if (this.parseContext != 67) {
                return etype;
            }
            type = XDataType.anyAtomicType;
        } else if (this.curToken == OP_ITEM) {
            parseSimpleKindType();
            type = SingletonType.getInstance();
        } else if (this.curToken == 65 || this.curToken == 81) {
            String tname = new String(this.tokenBuffer, 0, this.tokenBufferLength);
            ReferenceExp rexp = new ReferenceExp(tname);
            rexp.setFlag(16);
            maybeSetLine(rexp, this.curLine, this.curColumn);
            getRawToken();
            return rexp;
        } else {
            return null;
        }
        return QuoteExp.getInstance(type);
    }

    Object parseURILiteral() throws IOException, SyntaxException {
        getRawToken();
        if (this.curToken != 34) {
            return declError("expected a URILiteral");
        }
        String str = new String(this.tokenBuffer, 0, this.tokenBufferLength);
        return TextUtils.replaceWhitespace(str, true);
    }

    Expression parseExpr() throws IOException, SyntaxException {
        return parseExprSingle();
    }

    final Expression parseExprSingle() throws IOException, SyntaxException {
        int i = this.curLine;
        int i2 = this.curColumn;
        peekOperand();
        switch (this.curToken) {
            case 241:
                return parseIfExpr();
            case 242:
                return parseTypeSwitch();
            case FOR_DOLLAR_TOKEN /* 243 */:
                return parseFLWRExpression(true);
            case LET_DOLLAR_TOKEN /* 244 */:
                return parseFLWRExpression(false);
            case SOME_DOLLAR_TOKEN /* 245 */:
                return parseQuantifiedExpr(false);
            case EVERY_DOLLAR_TOKEN /* 246 */:
                return parseQuantifiedExpr(true);
            default:
                return parseBinaryExpr(priority(400));
        }
    }

    Expression parseBinaryExpr(int prio) throws IOException, SyntaxException {
        int tokPriority;
        Expression func;
        Expression exp = parseUnaryExpr();
        while (true) {
            int token = peekOperator();
            if (token != 10 && ((token != 404 || peek() != 47) && (tokPriority = priority(token)) >= prio)) {
                char saveReadState = pushNesting('%');
                getRawToken();
                popNesting(saveReadState);
                if (token >= 422 && token <= OP_CAST_AS) {
                    if (token == OP_CAST_AS || token == OP_CASTABLE_AS) {
                        this.parseContext = 67;
                    }
                    Expression type = parseDataType();
                    this.parseContext = 0;
                    Expression[] args = new Expression[2];
                    switch (token) {
                        case 422:
                            args[0] = exp;
                            args[1] = type;
                            func = makeFunctionExp("gnu.xquery.lang.XQParser", "instanceOf");
                            break;
                        case 423:
                            args[0] = type;
                            args[1] = exp;
                            func = makeFunctionExp("gnu.xquery.lang.XQParser", "treatAs");
                            break;
                        case OP_CASTABLE_AS /* 424 */:
                            args[0] = exp;
                            args[1] = type;
                            func = new ReferenceExp(XQResolveNames.castableAsDecl);
                            break;
                        default:
                            args[0] = type;
                            args[1] = exp;
                            func = new ReferenceExp(XQResolveNames.castAsDecl);
                            break;
                    }
                    exp = new ApplyExp(func, args);
                } else if (token == 422) {
                    exp = new ApplyExp(makeFunctionExp("gnu.xquery.lang.XQParser", "instanceOf"), exp, parseDataType());
                } else {
                    Expression exp2 = parseBinaryExpr(tokPriority + 1);
                    if (token == 401) {
                        exp = new IfExp(booleanValue(exp), booleanValue(exp2), XQuery.falseExp);
                    } else if (token == 400) {
                        exp = new IfExp(booleanValue(exp), XQuery.trueExp, booleanValue(exp2));
                    } else {
                        exp = makeBinary(token, exp, exp2);
                    }
                }
            }
        }
        return exp;
    }

    Expression parseUnaryExpr() throws IOException, SyntaxException {
        if (this.curToken == 414 || this.curToken == 413) {
            int op = this.curToken;
            getRawToken();
            Expression exp = parseUnaryExpr();
            Expression func = makeFunctionExp("gnu.xquery.util.ArithOp", op == 413 ? "plus" : "minus", op == 413 ? "+" : "-");
            return new ApplyExp(func, exp);
        }
        Expression exp2 = parseUnionExpr();
        return exp2;
    }

    Expression parseUnionExpr() throws IOException, SyntaxException {
        Expression exp = parseIntersectExceptExpr();
        while (true) {
            int op = peekOperator();
            if (op == 419) {
                getRawToken();
                Expression exp2 = parseIntersectExceptExpr();
                exp = makeBinary(op, exp, exp2);
            } else {
                return exp;
            }
        }
    }

    Expression parseIntersectExceptExpr() throws IOException, SyntaxException {
        Expression exp = parsePathExpr();
        while (true) {
            int op = peekOperator();
            if (op == 420 || op == 421) {
                getRawToken();
                Expression exp2 = parsePathExpr();
                exp = makeBinary(op, exp, exp2);
            } else {
                return exp;
            }
        }
    }

    Expression parsePathExpr() throws IOException, SyntaxException {
        Expression dot;
        Expression step1;
        if (this.curToken == 47 || this.curToken == 68) {
            Declaration dotDecl = this.comp.lexical.lookup((Object) DOT_VARNAME, false);
            if (dotDecl == null) {
                dot = syntaxError("context item is undefined", "XPDY0002");
            } else {
                dot = new ReferenceExp(DOT_VARNAME, dotDecl);
            }
            step1 = new ApplyExp(ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("rootDocument", 1), dot);
            int next = skipSpace(this.nesting != 0);
            unread(next);
            if (next < 0 || next == 41 || next == 125) {
                getRawToken();
                return step1;
            }
        } else {
            step1 = parseStepExpr();
        }
        return parseRelativePathExpr(step1);
    }

    Expression parseNameTest(boolean attribute) throws IOException, SyntaxException {
        String local = null;
        String prefix = null;
        if (this.curToken == 81) {
            int colon = this.tokenBufferLength;
            do {
                colon--;
            } while (this.tokenBuffer[colon] != ':');
            prefix = new String(this.tokenBuffer, 0, colon);
            int colon2 = colon + 1;
            local = new String(this.tokenBuffer, colon2, this.tokenBufferLength - colon2);
        } else if (this.curToken == 415) {
            int next = read();
            String local2 = "";
            if (next != 58) {
                unread(next);
            } else {
                int next2 = read();
                if (next2 < 0) {
                    eofError("unexpected end-of-file after '*:'");
                }
                if (XName.isNameStart((char) next2)) {
                    unread();
                    getRawToken();
                    if (this.curToken != 65) {
                        syntaxError("invalid name test");
                    } else {
                        local2 = new String(this.tokenBuffer, 0, this.tokenBufferLength).intern();
                    }
                } else if (next2 != 42) {
                    syntaxError("missing local-name after '*:'");
                }
            }
            return QuoteExp.getInstance(new Symbol(null, local2));
        } else if (this.curToken == 65) {
            local = new String(this.tokenBuffer, 0, this.tokenBufferLength);
            if (attribute) {
                return new QuoteExp(Namespace.EmptyNamespace.getSymbol(local.intern()));
            }
            prefix = null;
        } else if (this.curToken == 67) {
            prefix = new String(this.tokenBuffer, 0, this.tokenBufferLength);
            if (read() != 42) {
                syntaxError("invalid characters after 'NCName:'");
            }
            local = "";
        }
        if (prefix != null) {
            prefix = prefix.intern();
        }
        Expression[] args = new Expression[3];
        args[0] = new ApplyExp((Expression) new ReferenceExp(XQResolveNames.resolvePrefixDecl), QuoteExp.getInstance(prefix));
        args[1] = new QuoteExp(local == null ? "" : local);
        args[2] = new QuoteExp(prefix);
        ApplyExp make = new ApplyExp(Compilation.typeSymbol.getDeclaredMethod("make", 3), args);
        make.setFlag(4);
        return make;
    }

    Expression parseNodeTest(int axis) throws IOException, SyntaxException {
        Expression dot;
        Expression makeAxisStep;
        String axisName;
        peekOperand();
        Expression[] args = new Expression[1];
        Expression etype = parseMaybeKindTest();
        if (etype != null) {
            args[0] = etype;
        } else if (this.curToken == 65 || this.curToken == 81 || this.curToken == 67 || this.curToken == 415) {
            args[0] = makeNamedNodeType(axis == 2, parseNameTest(axis == 2), null);
        } else if (axis >= 0) {
            return syntaxError("unsupported axis '" + axisNames[axis] + "::'");
        } else {
            return null;
        }
        Declaration dotDecl = this.comp.lexical.lookup((Object) DOT_VARNAME, false);
        if (dotDecl == null) {
            dot = syntaxError("node test when context item is undefined", "XPDY0002");
        } else {
            dot = new ReferenceExp(DOT_VARNAME, dotDecl);
        }
        if (etype == null) {
            getRawToken();
        }
        if (axis == 3 || axis == -1) {
            makeAxisStep = makeChildAxisStep;
        } else if (axis == 4) {
            makeAxisStep = makeDescendantAxisStep;
        } else {
            switch (axis) {
                case 0:
                    axisName = "Ancestor";
                    break;
                case 1:
                    axisName = "AncestorOrSelf";
                    break;
                case 2:
                    axisName = "Attribute";
                    break;
                case 3:
                case 4:
                case 8:
                default:
                    throw new Error();
                case 5:
                    axisName = "DescendantOrSelf";
                    break;
                case 6:
                    axisName = "Following";
                    break;
                case 7:
                    axisName = "FollowingSibling";
                    break;
                case 9:
                    axisName = "Parent";
                    break;
                case 10:
                    axisName = "Preceding";
                    break;
                case 11:
                    axisName = "PrecedingSibling";
                    break;
                case 12:
                    axisName = "Self";
                    break;
            }
            makeAxisStep = QuoteExp.getInstance(new PrimProcedure("gnu.kawa.xml." + axisName + "Axis", "make", 1));
        }
        ApplyExp mkAxis = new ApplyExp(makeAxisStep, args);
        mkAxis.setFlag(4);
        return new ApplyExp((Expression) mkAxis, dot);
    }

    Expression parseRelativePathExpr(Expression exp) throws IOException, SyntaxException {
        Expression beforeSlashSlash = null;
        while (true) {
            if (this.curToken == 47 || this.curToken == 68) {
                boolean descendants = this.curToken == 68;
                LambdaExp lexp = new LambdaExp(3);
                Declaration dotDecl = lexp.addDeclaration(DOT_VARNAME);
                dotDecl.setFlag(262144L);
                dotDecl.setType(NodeType.anyNodeTest);
                dotDecl.noteValue(null);
                lexp.addDeclaration(POSITION_VARNAME, LangPrimType.intType);
                lexp.addDeclaration(LAST_VARNAME, LangPrimType.intType);
                this.comp.push(lexp);
                if (descendants) {
                    this.curToken = 47;
                    Expression dot = new ReferenceExp(DOT_VARNAME, dotDecl);
                    Expression[] args = {dot};
                    TreeScanner op = DescendantOrSelfAxis.anyNode;
                    lexp.body = new ApplyExp(op, args);
                    beforeSlashSlash = exp;
                } else {
                    getRawToken();
                    Expression exp2 = parseStepExpr();
                    if (beforeSlashSlash != null && (exp2 instanceof ApplyExp)) {
                        Expression func = ((ApplyExp) exp2).getFunction();
                        if (func instanceof ApplyExp) {
                            ApplyExp aexp = (ApplyExp) func;
                            if (aexp.getFunction() == makeChildAxisStep) {
                                aexp.setFunction(makeDescendantAxisStep);
                                exp = beforeSlashSlash;
                            }
                        }
                    }
                    lexp.body = exp2;
                    beforeSlashSlash = null;
                }
                this.comp.pop(lexp);
                Expression[] args2 = {exp, lexp};
                exp = new ApplyExp(RelativeStep.relativeStep, args2);
            } else {
                return exp;
            }
        }
    }

    Expression parseStepExpr() throws IOException, SyntaxException {
        Expression exp;
        Expression unqualifiedStep;
        if (this.curToken == 46 || this.curToken == 51) {
            int axis = this.curToken == 46 ? 12 : 9;
            getRawToken();
            Declaration dotDecl = this.comp.lexical.lookup((Object) DOT_VARNAME, false);
            if (dotDecl == null) {
                exp = syntaxError("context item is undefined", "XPDY0002");
            } else {
                exp = new ReferenceExp(DOT_VARNAME, dotDecl);
            }
            if (axis == 9) {
                Expression[] args = {exp};
                exp = new ApplyExp(ParentAxis.make(NodeType.anyNodeTest), args);
            }
            return parseStepQualifiers(exp, axis == 12 ? -1 : axis);
        }
        int axis2 = peekOperand() - 100;
        if (axis2 >= 0 && axis2 < 13) {
            getRawToken();
            unqualifiedStep = parseNodeTest(axis2);
        } else if (this.curToken == 64) {
            getRawToken();
            axis2 = 2;
            unqualifiedStep = parseNodeTest(2);
        } else if (this.curToken == OP_ATTRIBUTE) {
            axis2 = 2;
            unqualifiedStep = parseNodeTest(2);
        } else {
            unqualifiedStep = parseNodeTest(-1);
            if (unqualifiedStep != null) {
                axis2 = 3;
            } else {
                axis2 = -1;
                unqualifiedStep = parsePrimaryExpr();
            }
        }
        return parseStepQualifiers(unqualifiedStep, axis2);
    }

    Expression parseStepQualifiers(Expression exp, int axis) throws IOException, SyntaxException {
        Procedure valuesFilter;
        while (this.curToken == 91) {
            int startLine = getLineNumber() + 1;
            int startColumn = getColumnNumber() + 1;
            int i = this.seenPosition;
            int i2 = this.seenLast;
            getRawToken();
            LambdaExp lexp = new LambdaExp(3);
            maybeSetLine(lexp, startLine, startColumn);
            Declaration dot = lexp.addDeclaration(DOT_VARNAME);
            if (axis >= 0) {
                dot.setType(NodeType.anyNodeTest);
            } else {
                dot.setType(SingletonType.getInstance());
            }
            lexp.addDeclaration(POSITION_VARNAME, Type.intType);
            lexp.addDeclaration(LAST_VARNAME, Type.intType);
            this.comp.push(lexp);
            dot.noteValue(null);
            Expression cond = parseExprSequence(93, false);
            if (this.curToken == -1) {
                eofError("missing ']' - unexpected end-of-file");
            }
            if (axis < 0) {
                valuesFilter = ValuesFilter.exprFilter;
            } else if (axis == 0 || axis == 1 || axis == 9 || axis == 10 || axis == 11) {
                valuesFilter = ValuesFilter.reverseFilter;
            } else {
                valuesFilter = ValuesFilter.forwardFilter;
            }
            maybeSetLine(cond, startLine, startColumn);
            this.comp.pop(lexp);
            lexp.body = cond;
            getRawToken();
            Expression[] args = {exp, lexp};
            exp = new ApplyExp(valuesFilter, args);
        }
        return exp;
    }

    Expression parsePrimaryExpr() throws IOException, SyntaxException {
        Expression exp = parseMaybePrimaryExpr();
        if (exp == null) {
            Expression exp2 = syntaxError("missing expression");
            if (this.curToken != -1) {
                getRawToken();
            }
            return exp2;
        }
        return exp;
    }

    void parseEntityOrCharRef() throws IOException, SyntaxException {
        int base;
        int next = read();
        if (next == 35) {
            int next2 = read();
            if (next2 == 120) {
                base = 16;
                next2 = read();
            } else {
                base = 10;
            }
            int value = 0;
            while (next2 >= 0) {
                int digit = Character.digit((char) next2, base);
                if (digit < 0 || value >= 134217728) {
                    break;
                }
                value = (value * base) + digit;
                next2 = read();
            }
            if (next2 != 59) {
                unread();
                error("invalid character reference");
                return;
            } else if ((value > 0 && value <= 55295) || ((value >= 57344 && value <= 65533) || (value >= 65536 && value <= 1114111))) {
                tokenBufferAppend(value);
                return;
            } else {
                error('e', "invalid character value " + value, "XQST0090");
                return;
            }
        }
        int saveLength = this.tokenBufferLength;
        while (next >= 0) {
            char ch = (char) next;
            if (!XName.isNamePart(ch)) {
                break;
            }
            tokenBufferAppend(ch);
            next = read();
        }
        if (next != 59) {
            unread();
            error("invalid entity reference");
            return;
        }
        String ref = new String(this.tokenBuffer, saveLength, this.tokenBufferLength - saveLength);
        this.tokenBufferLength = saveLength;
        appendNamedEntity(ref);
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x008c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0170  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void parseContent(char r21, java.util.Vector r22) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            Method dump skipped, instructions count: 565
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.parseContent(char, java.util.Vector):void");
    }

    Expression parseEnclosedExpr() throws IOException, SyntaxException {
        String saveErrorIfComment = this.errorIfComment;
        this.errorIfComment = null;
        char saveReadState = pushNesting('{');
        peekNonSpace("unexpected end-of-file after '{'");
        int startLine = getLineNumber() + 1;
        int startColumn = getColumnNumber() + 1;
        getRawToken();
        Expression exp = parseExpr();
        while (this.curToken != 125) {
            if (this.curToken == -1 || this.curToken == 41 || this.curToken == 93) {
                exp = syntaxError("missing '}'");
                break;
            }
            if (this.curToken != 44) {
                exp = syntaxError("missing '}' or ','");
            } else {
                getRawToken();
            }
            exp = makeExprSequence(exp, parseExpr());
        }
        maybeSetLine(exp, startLine, startColumn);
        popNesting(saveReadState);
        this.errorIfComment = saveErrorIfComment;
        return exp;
    }

    public static Expression booleanValue(Expression exp) {
        Expression[] args = {exp};
        Expression string = makeFunctionExp("gnu.xquery.util.BooleanValue", "booleanValue");
        return new ApplyExp(string, args);
    }

    Expression parseXMLConstructor(int next, boolean inElementContent) throws IOException, SyntaxException {
        if (next == 33) {
            int next2 = read();
            if (next2 == 45 && peek() == 45) {
                skip();
                getDelimited("-->");
                boolean bad = false;
                int i = this.tokenBufferLength;
                boolean sawHyphen = true;
                while (true) {
                    i--;
                    if (i >= 0) {
                        boolean curHyphen = this.tokenBuffer[i] == '-';
                        if (sawHyphen && curHyphen) {
                            bad = true;
                            break;
                        }
                        sawHyphen = curHyphen;
                    } else {
                        break;
                    }
                }
                if (bad) {
                    return syntaxError("consecutive or final hyphen in XML comment");
                }
                Expression[] args = {new QuoteExp(new String(this.tokenBuffer, 0, this.tokenBufferLength))};
                return new ApplyExp(makeFunctionExp("gnu.kawa.xml.CommentConstructor", "commentConstructor"), args);
            } else if (next2 == 91 && read() == 67 && read() == 68 && read() == 65 && read() == 84 && read() == 65 && read() == 91) {
                if (!inElementContent) {
                    error('e', "CDATA section must be in element content");
                }
                getDelimited("]]>");
                Expression[] args2 = {new QuoteExp(new String(this.tokenBuffer, 0, this.tokenBufferLength))};
                return new ApplyExp(makeCDATA, args2);
            } else {
                return syntaxError("'<!' must be followed by '--' or '[CDATA['");
            }
        } else if (next == 63) {
            int next3 = peek();
            if (next3 < 0 || !XName.isNameStart((char) next3) || getRawToken() != 65) {
                syntaxError("missing target after '<?'");
            }
            String target = new String(this.tokenBuffer, 0, this.tokenBufferLength);
            int nspaces = 0;
            while (true) {
                int ch = read();
                if (ch < 0) {
                    break;
                } else if (!Character.isWhitespace((char) ch)) {
                    unread();
                    break;
                } else {
                    nspaces++;
                }
            }
            getDelimited("?>");
            if (nspaces == 0 && this.tokenBufferLength > 0) {
                syntaxError("target must be followed by space or '?>'");
            }
            String content = new String(this.tokenBuffer, 0, this.tokenBufferLength);
            Expression[] args3 = {new QuoteExp(target), new QuoteExp(content)};
            return new ApplyExp(makeFunctionExp("gnu.kawa.xml.MakeProcInst", "makeProcInst"), args3);
        } else if (next < 0 || !XName.isNameStart((char) next)) {
            return syntaxError("expected QName after '<'");
        } else {
            unread(next);
            getRawToken();
            char saveReadState = pushNesting('<');
            Expression exp = parseElementConstructor();
            if (!inElementContent) {
                exp = wrapWithBaseUri(exp);
            }
            popNesting(saveReadState);
            return exp;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ApplyExp castQName(Expression value, boolean element) {
        Declaration fdecl = element ? XQResolveNames.xsQNameDecl : XQResolveNames.xsQNameIgnoreDefaultDecl;
        return new ApplyExp((Expression) new ReferenceExp(fdecl), value);
    }

    Expression parseElementConstructor() throws IOException, SyntaxException {
        int ch;
        String startTag = new String(this.tokenBuffer, 0, this.tokenBufferLength);
        Vector vec = new Vector();
        vec.addElement(castQName(new QuoteExp(startTag), true));
        this.errorIfComment = "comment not allowed in element start tag";
        NamespaceBinding nsBindings = null;
        while (true) {
            boolean sawSpace = false;
            ch = read();
            while (ch >= 0 && Character.isWhitespace((char) ch)) {
                ch = read();
                sawSpace = true;
            }
            if (ch >= 0 && ch != 62 && ch != 47) {
                if (!sawSpace) {
                    syntaxError("missing space before attribute");
                }
                unread(ch);
                getRawToken();
                int vecSize = vec.size();
                if (this.curToken != 65 && this.curToken != 81) {
                    break;
                }
                String attrName = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                int startLine = getLineNumber() + 1;
                int startColumn = (getColumnNumber() + 1) - this.tokenBufferLength;
                String definingNamespace = null;
                if (this.curToken == 65) {
                    if (attrName.equals("xmlns")) {
                        definingNamespace = "";
                    }
                } else if (attrName.startsWith("xmlns:")) {
                    definingNamespace = attrName.substring(6).intern();
                }
                Expression makeAttr = definingNamespace != null ? null : MakeAttribute.makeAttributeExp;
                vec.addElement(castQName(new QuoteExp(attrName), false));
                int ch2 = skipSpace();
                if (ch2 != 61) {
                    this.errorIfComment = null;
                    return syntaxError("missing '=' after attribute");
                }
                int ch3 = skipSpace();
                int enclosedExpressionsStart = this.enclosedExpressionsSeen;
                if (ch3 == 123) {
                    warnOldVersion("enclosed attribute value expression should be quoted");
                    vec.addElement(parseEnclosedExpr());
                } else {
                    parseContent((char) ch3, vec);
                }
                int n = vec.size() - vecSize;
                if (definingNamespace != null) {
                    String ns = "";
                    if (n == 1) {
                        ns = "";
                    } else if (this.enclosedExpressionsSeen > enclosedExpressionsStart) {
                        syntaxError("enclosed expression not allowed in namespace declaration");
                    } else {
                        Object x = vec.elementAt(vecSize + 1);
                        if (x instanceof ApplyExp) {
                            ApplyExp ax = (ApplyExp) x;
                            if (ax.getFunction() == makeText) {
                                x = ax.getArg(0);
                            }
                        }
                        ns = ((QuoteExp) x).getValue().toString().intern();
                    }
                    vec.setSize(vecSize);
                    checkAllowedNamespaceDeclaration(definingNamespace, ns, true);
                    if (definingNamespace == "") {
                        definingNamespace = null;
                    }
                    NamespaceBinding nsb = nsBindings;
                    while (true) {
                        if (nsb == null) {
                            break;
                        } else if (nsb.getPrefix() != definingNamespace) {
                            nsb = nsb.getNext();
                        } else {
                            error('e', definingNamespace == null ? "duplicate default namespace declaration" : "duplicate namespace prefix '" + definingNamespace + '\'', "XQST0071");
                        }
                    }
                    if (ns == "") {
                        ns = null;
                    }
                    nsBindings = new NamespaceBinding(definingNamespace, ns, nsBindings);
                } else {
                    Expression[] args = new Expression[n];
                    int i = n;
                    while (true) {
                        i--;
                        if (i < 0) {
                            break;
                        }
                        args[i] = (Expression) vec.elementAt(vecSize + i);
                    }
                    vec.setSize(vecSize);
                    ApplyExp aexp = new ApplyExp(makeAttr, args);
                    maybeSetLine(aexp, startLine, startColumn);
                    vec.addElement(aexp);
                }
            } else {
                break;
            }
        }
        this.errorIfComment = null;
        boolean empty = false;
        if (ch == 47) {
            ch = read();
            if (ch == 62) {
                empty = true;
            } else {
                unread(ch);
            }
        }
        if (!empty) {
            if (ch != 62) {
                return syntaxError("missing '>' after start element");
            }
            parseContent('<', vec);
            int ch4 = peek();
            if (ch4 >= 0) {
                if (!XName.isNameStart((char) ch4)) {
                    return syntaxError("invalid tag syntax after '</'");
                }
                getRawToken();
                String tag = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                if (!tag.equals(startTag)) {
                    return syntaxError("'<" + startTag + ">' closed by '</" + tag + ">'");
                }
                this.errorIfComment = "comment not allowed in element end tag";
                ch4 = skipSpace();
                this.errorIfComment = null;
            }
            if (ch4 != 62) {
                return syntaxError("missing '>' after end element");
            }
        }
        Expression[] args2 = new Expression[vec.size()];
        vec.copyInto(args2);
        MakeElement mkElement = new MakeElement();
        mkElement.copyNamespacesMode = this.copyNamespacesMode;
        mkElement.setNamespaceNodes(nsBindings);
        return new ApplyExp((Expression) new QuoteExp(mkElement), args2);
    }

    Expression wrapWithBaseUri(Expression exp) {
        return getStaticBaseUri() == null ? exp : new ApplyExp(MakeWithBaseUri.makeWithBaseUri, new ApplyExp((Expression) new ReferenceExp(XQResolveNames.staticBaseUriDecl), Expression.noExpressions), exp).setLine(exp);
    }

    Expression parseParenExpr() throws IOException, SyntaxException {
        getRawToken();
        char saveReadState = pushNesting('(');
        Expression exp = parseExprSequence(41, true);
        popNesting(saveReadState);
        if (this.curToken == -1) {
            eofError("missing ')' - unexpected end-of-file");
        }
        return exp;
    }

    Expression parseExprSequence(int rightToken, boolean optional) throws IOException, SyntaxException {
        if (this.curToken == rightToken || this.curToken == -1) {
            if (!optional) {
                syntaxError("missing expression");
            }
            return QuoteExp.voidExp;
        }
        Expression exp = null;
        while (true) {
            Expression exp1 = parseExprSingle();
            exp = exp == null ? exp1 : makeExprSequence(exp, exp1);
            if (this.curToken != rightToken && this.curToken != -1) {
                if (this.nesting != 0 || this.curToken != 10) {
                    if (this.curToken != 44) {
                        Expression exp2 = syntaxError(rightToken == 41 ? "expected ')'" : "confused by syntax error");
                        return exp2;
                    }
                    getRawToken();
                } else {
                    return exp;
                }
            } else {
                return exp;
            }
        }
    }

    Expression parseTypeSwitch() throws IOException, SyntaxException {
        Declaration decl;
        Declaration decl2;
        char save = pushNesting('t');
        Expression selector = parseParenExpr();
        getRawToken();
        Vector vec = new Vector();
        vec.addElement(selector);
        while (match("case")) {
            pushNesting('c');
            getRawToken();
            if (this.curToken == 36) {
                decl2 = parseVariableDeclaration();
                if (decl2 == null) {
                    return syntaxError("missing Variable after '$'");
                }
                getRawToken();
                if (match("as")) {
                    getRawToken();
                } else {
                    error('e', "missing 'as'");
                }
            } else {
                decl2 = new Declaration("(arg)");
            }
            decl2.setTypeExp(parseDataType());
            popNesting('t');
            LambdaExp lexp = new LambdaExp(1);
            lexp.addDeclaration(decl2);
            if (match("return")) {
                getRawToken();
            } else {
                error("missing 'return' after 'case'");
            }
            this.comp.push(lexp);
            pushNesting('r');
            Expression caseExpr = parseExpr();
            lexp.body = caseExpr;
            popNesting('t');
            this.comp.pop(lexp);
            vec.addElement(lexp);
        }
        if (match("default")) {
            LambdaExp lexp2 = new LambdaExp(1);
            getRawToken();
            if (this.curToken == 36) {
                decl = parseVariableDeclaration();
                if (decl == null) {
                    return syntaxError("missing Variable after '$'");
                }
                getRawToken();
            } else {
                decl = new Declaration("(arg)");
            }
            lexp2.addDeclaration(decl);
            if (match("return")) {
                getRawToken();
            } else {
                error("missing 'return' after 'default'");
            }
            this.comp.push(lexp2);
            Expression defaultExpr = parseExpr();
            lexp2.body = defaultExpr;
            this.comp.pop(lexp2);
            vec.addElement(lexp2);
        } else {
            error(this.comp.isPedantic() ? 'e' : 'w', "no 'default' clause in 'typeswitch'", "XPST0003");
        }
        popNesting(save);
        Expression[] args = new Expression[vec.size()];
        vec.copyInto(args);
        return new ApplyExp(makeFunctionExp("gnu.kawa.reflect.TypeSwitch", "typeSwitch"), args);
    }

    /* JADX WARN: Removed duplicated region for block: B:164:0x0090 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    gnu.expr.Expression parseMaybePrimaryExpr() throws java.io.IOException, gnu.text.SyntaxException {
        /*
            Method dump skipped, instructions count: 1580
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.parseMaybePrimaryExpr():gnu.expr.Expression");
    }

    public Expression parseIfExpr() throws IOException, SyntaxException {
        char saveReadState1 = pushNesting('i');
        getRawToken();
        char saveReadState2 = pushNesting('(');
        Expression cond = parseExprSequence(41, false);
        popNesting(saveReadState2);
        if (this.curToken == -1) {
            eofError("missing ')' - unexpected end-of-file");
        }
        getRawToken();
        if (!match("then")) {
            syntaxError("missing 'then'");
        } else {
            getRawToken();
        }
        Expression thenPart = parseExpr();
        if (!match("else")) {
            syntaxError("missing 'else'");
        } else {
            getRawToken();
        }
        popNesting(saveReadState1);
        Expression elsePart = parseExpr();
        return new IfExp(booleanValue(cond), thenPart, elsePart);
    }

    public boolean match(String word) {
        int len;
        char cs;
        char cb;
        if (this.curToken == 65 && this.tokenBufferLength == (len = word.length())) {
            int i = len;
            do {
                i--;
                if (i >= 0) {
                    cs = word.charAt(i);
                    cb = this.tokenBuffer[i];
                } else {
                    return true;
                }
            } while (cs == cb);
            return false;
        }
        return false;
    }

    public Object parseVariable() throws IOException, SyntaxException {
        if (this.curToken == 36) {
            getRawToken();
        } else {
            syntaxError("missing '$' before variable name");
        }
        String str = new String(this.tokenBuffer, 0, this.tokenBufferLength);
        if (this.curToken != 81) {
            if (this.curToken == 65) {
                return Namespace.EmptyNamespace.getSymbol(str.intern());
            }
            return null;
        }
        return str;
    }

    public Declaration parseVariableDeclaration() throws IOException, SyntaxException {
        Object name = parseVariable();
        if (name == null) {
            return null;
        }
        Declaration decl = new Declaration(name);
        maybeSetLine(decl, getLineNumber() + 1, (getColumnNumber() + 1) - this.tokenBufferLength);
        return decl;
    }

    public Expression parseFLWRExpression(boolean isFor) throws IOException, SyntaxException {
        int flworDeclsSave = this.flworDeclsFirst;
        this.flworDeclsFirst = this.flworDeclsCount;
        Expression exp = parseFLWRInner(isFor);
        if (match("order")) {
            char saveNesting = pushNesting(isFor ? 'f' : 'l');
            getRawToken();
            if (match("by")) {
                getRawToken();
            } else {
                error("missing 'by' following 'order'");
            }
            Stack specs = new Stack();
            while (true) {
                boolean descending = false;
                char emptyOrder = this.defaultEmptyOrder;
                LambdaExp lexp = new LambdaExp(this.flworDeclsCount - this.flworDeclsFirst);
                for (int i = this.flworDeclsFirst; i < this.flworDeclsCount; i++) {
                    lexp.addDeclaration(this.flworDecls[i].getSymbol());
                }
                this.comp.push(lexp);
                lexp.body = parseExprSingle();
                this.comp.pop(lexp);
                specs.push(lexp);
                if (match("ascending")) {
                    getRawToken();
                } else if (match("descending")) {
                    getRawToken();
                    descending = true;
                }
                if (match("empty")) {
                    getRawToken();
                    if (match("greatest")) {
                        getRawToken();
                        emptyOrder = 'G';
                    } else if (match("least")) {
                        getRawToken();
                        emptyOrder = 'L';
                    } else {
                        error("'empty' sequence order must be 'greatest' or 'least'");
                    }
                }
                specs.push(new QuoteExp((descending ? "D" : "A") + emptyOrder));
                NamedCollator collation = this.defaultCollator;
                if (match("collation")) {
                    Object uri = parseURILiteral();
                    if (uri instanceof String) {
                        try {
                            String uriString = resolveAgainstBaseUri((String) uri);
                            collation = NamedCollator.make(uriString);
                        } catch (Exception e) {
                            error('e', "unknown collation '" + uri + "'", "XQST0076");
                        }
                    }
                    getRawToken();
                }
                specs.push(new QuoteExp(collation));
                if (this.curToken != 44) {
                    break;
                }
                getRawToken();
            }
            if (!match("return")) {
                return syntaxError("expected 'return' clause");
            }
            getRawToken();
            LambdaExp lexp2 = new LambdaExp(this.flworDeclsCount - this.flworDeclsFirst);
            for (int i2 = this.flworDeclsFirst; i2 < this.flworDeclsCount; i2++) {
                lexp2.addDeclaration(this.flworDecls[i2].getSymbol());
            }
            popNesting(saveNesting);
            this.comp.push(lexp2);
            lexp2.body = parseExprSingle();
            this.comp.pop(lexp2);
            int nspecs = specs.size();
            Expression[] args = new Expression[nspecs + 2];
            args[0] = exp;
            args[1] = lexp2;
            for (int i3 = 0; i3 < nspecs; i3++) {
                args[i3 + 2] = (Expression) specs.elementAt(i3);
            }
            return new ApplyExp(makeFunctionExp("gnu.xquery.util.OrderedMap", "orderedMap"), args);
        }
        this.flworDeclsCount = this.flworDeclsFirst;
        this.flworDeclsFirst = flworDeclsSave;
        return exp;
    }

    public Expression parseFLWRInner(boolean isFor) throws IOException, SyntaxException {
        ScopeExp sc;
        Expression cond;
        Expression body;
        Expression body2;
        char saveNesting = pushNesting(isFor ? 'f' : 'l');
        this.curToken = 36;
        Declaration decl = parseVariableDeclaration();
        if (decl == null) {
            return syntaxError("missing Variable - saw " + tokenString());
        }
        if (this.flworDecls == null) {
            this.flworDecls = new Declaration[8];
        } else if (this.flworDeclsCount >= this.flworDecls.length) {
            Declaration[] tmp = new Declaration[this.flworDeclsCount * 2];
            System.arraycopy(this.flworDecls, 0, tmp, 0, this.flworDeclsCount);
            this.flworDecls = tmp;
        }
        Declaration[] declarationArr = this.flworDecls;
        int i = this.flworDeclsCount;
        this.flworDeclsCount = i + 1;
        declarationArr[i] = decl;
        getRawToken();
        Expression type = parseOptionalTypeDeclaration();
        Expression[] inits = new Expression[1];
        Declaration posDecl = null;
        if (isFor) {
            boolean sawAt = match("at");
            ScopeExp lexp = new LambdaExp(sawAt ? 2 : 1);
            if (sawAt) {
                getRawToken();
                if (this.curToken == 36) {
                    posDecl = parseVariableDeclaration();
                    getRawToken();
                }
                if (posDecl == null) {
                    syntaxError("missing Variable after 'at'");
                }
            }
            sc = lexp;
            if (match("in")) {
                getRawToken();
            } else {
                if (this.curToken == 76) {
                    getRawToken();
                }
                syntaxError("missing 'in' in 'for' clause");
            }
        } else {
            if (this.curToken == 76) {
                getRawToken();
            } else {
                if (match("in")) {
                    getRawToken();
                }
                syntaxError("missing ':=' in 'let' clause");
            }
            ScopeExp let = new LetExp(inits);
            sc = let;
        }
        inits[0] = parseExprSingle();
        if (type != null && !isFor) {
            inits[0] = Compilation.makeCoercion(inits[0], type);
        }
        popNesting(saveNesting);
        this.comp.push(sc);
        sc.addDeclaration(decl);
        if (type != null) {
            decl.setTypeExp(type);
        }
        if (isFor) {
            decl.noteValue(null);
            decl.setFlag(262144L);
        }
        if (posDecl != null) {
            sc.addDeclaration(posDecl);
            posDecl.setType(LangPrimType.intType);
            posDecl.noteValue(null);
            posDecl.setFlag(262144L);
        }
        if (this.curToken == 44) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after ','");
            }
            body2 = parseFLWRInner(isFor);
        } else if (match("for")) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after 'for'");
            }
            body2 = parseFLWRInner(true);
        } else if (match("let")) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after 'let'");
            }
            body2 = parseFLWRInner(false);
        } else {
            char save = pushNesting('w');
            if (this.curToken == 196) {
                getRawToken();
                cond = parseExprSingle();
            } else if (match("where")) {
                cond = parseExprSingle();
            } else {
                cond = null;
            }
            popNesting(save);
            boolean sawStable = match("stable");
            if (sawStable) {
                getRawToken();
            }
            boolean sawReturn = match("return");
            boolean sawOrder = match("order");
            if (!sawReturn && !sawOrder && !match("let") && !match("for")) {
                return syntaxError("missing 'return' clause");
            }
            if (!sawOrder) {
                peekNonSpace("unexpected eof-of-file after 'return'");
            }
            int bodyLine = getLineNumber() + 1;
            int bodyColumn = getColumnNumber() + 1;
            if (sawReturn) {
                getRawToken();
            }
            if (sawOrder) {
                int ndecls = this.flworDeclsCount - this.flworDeclsFirst;
                Expression[] args = new Expression[ndecls];
                for (int i2 = 0; i2 < ndecls; i2++) {
                    args[i2] = new ReferenceExp(this.flworDecls[this.flworDeclsFirst + i2]);
                }
                body = new ApplyExp(new PrimProcedure("gnu.xquery.util.OrderedMap", "makeTuple$V", 1), args);
            } else {
                Expression body3 = parseExprSingle();
                body = body3;
            }
            body2 = cond != null ? new IfExp(booleanValue(cond), body, QuoteExp.voidExp) : body;
            maybeSetLine(body2, bodyLine, bodyColumn);
        }
        this.comp.pop(sc);
        if (isFor) {
            LambdaExp lexp2 = (LambdaExp) sc;
            lexp2.body = body2;
            return new ApplyExp(makeFunctionExp("gnu.kawa.functions.ValuesMap", lexp2.min_args == 1 ? "valuesMap" : "valuesMapWithPos"), sc, inits[0]);
        }
        ((LetExp) sc).setBody(body2);
        return sc;
    }

    public Expression parseQuantifiedExpr(boolean isEvery) throws IOException, SyntaxException {
        Expression body;
        char saveNesting = pushNesting(isEvery ? 'e' : 's');
        this.curToken = 36;
        Declaration decl = parseVariableDeclaration();
        if (decl == null) {
            return syntaxError("missing Variable token:" + this.curToken);
        }
        getRawToken();
        LambdaExp lexp = new LambdaExp(1);
        lexp.addDeclaration(decl);
        decl.noteValue(null);
        decl.setFlag(262144L);
        decl.setTypeExp(parseOptionalTypeDeclaration());
        if (match("in")) {
            getRawToken();
        } else {
            if (this.curToken == 76) {
                getRawToken();
            }
            syntaxError("missing 'in' in QuantifiedExpr");
        }
        Expression[] inits = {parseExprSingle()};
        popNesting(saveNesting);
        this.comp.push(lexp);
        if (this.curToken == 44) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after ','");
            }
            body = parseQuantifiedExpr(isEvery);
        } else {
            boolean sawSatisfies = match("satisfies");
            if (!sawSatisfies && !match("every") && !match("some")) {
                return syntaxError("missing 'satisfies' clause");
            }
            peekNonSpace("unexpected eof-of-file after 'satisfies'");
            int bodyLine = getLineNumber() + 1;
            int bodyColumn = getColumnNumber() + 1;
            if (sawSatisfies) {
                getRawToken();
            }
            body = parseExprSingle();
            maybeSetLine(body, bodyLine, bodyColumn);
        }
        this.comp.pop(lexp);
        lexp.body = body;
        Expression[] args = {lexp, inits[0]};
        return new ApplyExp(makeFunctionExp("gnu.xquery.util.ValuesEvery", isEvery ? "every" : "some"), args);
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:?, code lost:
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public gnu.expr.Expression parseFunctionDefinition(int r14, int r15) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            Method dump skipped, instructions count: 395
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.parseFunctionDefinition(int, int):gnu.expr.Expression");
    }

    public Object readObject() throws IOException, SyntaxException {
        return parse(null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Symbol namespaceResolve(String name, boolean function) {
        String prefix;
        int colon = name.indexOf(58);
        if (colon >= 0) {
            prefix = name.substring(0, colon).intern();
        } else {
            prefix = function ? XQuery.DEFAULT_FUNCTION_PREFIX : XQuery.DEFAULT_ELEMENT_PREFIX;
        }
        String uri = QNameUtils.lookupPrefix(prefix, this.constructorNamespaces, this.prologNamespaces);
        if (uri == null) {
            if (colon < 0) {
                uri = "";
            } else if (!this.comp.isPedantic()) {
                try {
                    Class.forName(prefix);
                    uri = "class:" + prefix;
                } catch (Exception e) {
                    uri = null;
                }
            }
            if (uri == null) {
                getMessages().error('e', "unknown namespace prefix '" + prefix + "'", "XPST0081");
                uri = "(unknown namespace)";
            }
        }
        String local = colon < 0 ? name : name.substring(colon + 1);
        return Symbol.make(uri, local, prefix);
    }

    void parseSeparator() throws IOException, SyntaxException {
        int startLine = this.port.getLineNumber() + 1;
        int startColumn = this.port.getColumnNumber() + 1;
        int next = skipSpace(this.nesting != 0);
        if (next != 59) {
            if (warnOldVersion && next != 10) {
                this.curLine = startLine;
                this.curColumn = startColumn;
                error('w', "missing ';' after declaration");
            }
            if (next >= 0) {
                unread(next);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0051, code lost:
        if (r18 != 47) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public gnu.expr.Expression parse(gnu.expr.Compilation r52) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            Method dump skipped, instructions count: 3054
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.parse(gnu.expr.Compilation):gnu.expr.Expression");
    }

    public void handleOption(Symbol name, String value) {
    }

    public static Expression makeFunctionExp(String className, String name) {
        return makeFunctionExp(className, Compilation.mangleNameIfNeeded(name), name);
    }

    public static Expression makeFunctionExp(String className, String fieldName, String name) {
        return new ReferenceExp(name, Declaration.getDeclarationValueFromStatic(className, fieldName, name));
    }

    String tokenString() {
        switch (this.curToken) {
            case -1:
                return "<EOF>";
            case 34:
                StringBuffer sbuf = new StringBuffer();
                sbuf.append('\"');
                for (int i = 0; i < this.tokenBufferLength; i++) {
                    char ch = this.tokenBuffer[i];
                    if (ch == '\"') {
                        sbuf.append('\"');
                    }
                    sbuf.append(ch);
                }
                sbuf.append('\"');
                return sbuf.toString();
            case 65:
            case 81:
                return new String(this.tokenBuffer, 0, this.tokenBufferLength);
            case 70:
                return new String(this.tokenBuffer, 0, this.tokenBufferLength) + " + '('";
            default:
                if (this.curToken >= 100 && this.curToken - 100 < 13) {
                    return axisNames[this.curToken - 100] + "::-axis(" + this.curToken + ")";
                }
                if (this.curToken >= 32 && this.curToken < 127) {
                    return Integer.toString(this.curToken) + "='" + ((char) this.curToken) + "'";
                }
                return Integer.toString(this.curToken);
        }
    }

    public void error(char severity, String message, String code) {
        SourceMessages messages = getMessages();
        SourceError err = new SourceError(severity, this.port.getName(), this.curLine, this.curColumn, message);
        err.code = code;
        messages.error(err);
    }

    @Override // gnu.text.Lexer
    public void error(char severity, String message) {
        error(severity, message, null);
    }

    public Expression declError(String message) throws IOException, SyntaxException {
        if (this.interactive) {
            return syntaxError(message);
        }
        error(message);
        while (this.curToken != 59 && this.curToken != -1) {
            getRawToken();
        }
        return new ErrorExp(message);
    }

    public Expression syntaxError(String message, String code) throws IOException, SyntaxException {
        int ch;
        error('e', message, code);
        if (this.interactive) {
            this.curToken = 0;
            this.curValue = null;
            this.nesting = 0;
            ((InPort) getPort()).readState = '\n';
            do {
                ch = read();
                if (ch < 0) {
                    break;
                } else if (ch == 13) {
                    break;
                }
            } while (ch != 10);
            unread(ch);
            throw new SyntaxException(getMessages());
        }
        return new ErrorExp(message);
    }

    public Expression syntaxError(String message) throws IOException, SyntaxException {
        return syntaxError(message, "XPST0003");
    }

    @Override // gnu.text.Lexer
    public void eofError(String msg) throws SyntaxException {
        fatal(msg, "XPST0003");
    }

    public void fatal(String msg, String code) throws SyntaxException {
        SourceMessages messages = getMessages();
        SourceError err = new SourceError('f', this.port.getName(), this.curLine, this.curColumn, msg);
        err.code = code;
        messages.error(err);
        throw new SyntaxException(messages);
    }

    void warnOldVersion(String message) {
        if (warnOldVersion || this.comp.isPedantic()) {
            error(this.comp.isPedantic() ? 'e' : 'w', message);
        }
    }

    public void maybeSetLine(Expression exp, int line, int column) {
        String file = getName();
        if (file != null && exp.getFileName() == null && !(exp instanceof QuoteExp)) {
            exp.setFile(file);
            exp.setLine(line, column);
        }
    }

    public void maybeSetLine(Declaration decl, int line, int column) {
        String file = getName();
        if (file != null) {
            decl.setFile(file);
            decl.setLine(line, column);
        }
    }
}
