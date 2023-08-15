package com.google.appinventor.components.runtime.repackaged.org.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

/* loaded from: classes2.dex */
public class JSONTokener {
    private long character;
    private boolean eof;
    private long index;
    private long line;
    private char previous;
    private Reader reader;
    private boolean usePrevious;

    public JSONTokener(Reader reader) {
        this.reader = reader.markSupported() ? reader : new BufferedReader(reader);
        this.eof = false;
        this.usePrevious = false;
        this.previous = (char) 0;
        this.index = 0L;
        this.character = 1L;
        this.line = 1L;
    }

    public JSONTokener(InputStream inputStream) throws JSONException {
        this(new InputStreamReader(inputStream));
    }

    public JSONTokener(String s) {
        this(new StringReader(s));
    }

    public void back() throws JSONException {
        if (this.usePrevious || this.index <= 0) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        this.index--;
        this.character--;
        this.usePrevious = true;
        this.eof = false;
    }

    public static int dehexchar(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - '7';
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'W';
        }
        return -1;
    }

    public boolean end() {
        return this.eof && !this.usePrevious;
    }

    public boolean more() throws JSONException {
        next();
        if (end()) {
            return false;
        }
        back();
        return true;
    }

    public char next() throws JSONException {
        int c;
        if (this.usePrevious) {
            this.usePrevious = false;
            c = this.previous;
        } else {
            try {
                c = this.reader.read();
                if (c <= 0) {
                    this.eof = true;
                    c = 0;
                }
            } catch (IOException exception) {
                throw new JSONException(exception);
            }
        }
        this.index++;
        if (this.previous == '\r') {
            this.line++;
            this.character = c != 10 ? 1L : 0L;
        } else if (c == 10) {
            this.line = 1 + this.line;
            this.character = 0L;
        } else {
            this.character++;
        }
        this.previous = (char) c;
        return this.previous;
    }

    public char next(char c) throws JSONException {
        char n = next();
        if (n != c) {
            throw syntaxError(new StringBuffer().append("Expected '").append(c).append("' and instead saw '").append(n).append("'").toString());
        }
        return n;
    }

    public String next(int n) throws JSONException {
        if (n == 0) {
            return "";
        }
        char[] chars = new char[n];
        for (int pos = 0; pos < n; pos++) {
            chars[pos] = next();
            if (end()) {
                throw syntaxError("Substring bounds error");
            }
        }
        return new String(chars);
    }

    public char nextClean() throws JSONException {
        char c;
        do {
            c = next();
            if (c == 0) {
                break;
            }
        } while (c <= ' ');
        return c;
    }

    public String nextString(char quote) throws JSONException {
        StringBuffer sb = new StringBuffer();
        while (true) {
            char c = next();
            switch (c) {
                case 0:
                case '\n':
                case '\r':
                    throw syntaxError("Unterminated string");
                case '\\':
                    char c2 = next();
                    switch (c2) {
                        case '\"':
                        case '\'':
                        case '/':
                        case '\\':
                            sb.append(c2);
                            continue;
                        case 'b':
                            sb.append('\b');
                            continue;
                        case 'f':
                            sb.append('\f');
                            continue;
                        case 'n':
                            sb.append('\n');
                            continue;
                        case 'r':
                            sb.append('\r');
                            continue;
                        case 't':
                            sb.append('\t');
                            continue;
                        case 'u':
                            sb.append((char) Integer.parseInt(next(4), 16));
                            continue;
                        default:
                            throw syntaxError("Illegal escape.");
                    }
                default:
                    if (c == quote) {
                        return sb.toString();
                    }
                    sb.append(c);
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0017, code lost:
        back();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String nextTo(char r4) throws com.google.appinventor.components.runtime.repackaged.org.json.JSONException {
        /*
            r3 = this;
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
        L5:
            char r0 = r3.next()
            if (r0 == r4) goto L15
            if (r0 == 0) goto L15
            r2 = 10
            if (r0 == r2) goto L15
            r2 = 13
            if (r0 != r2) goto L23
        L15:
            if (r0 == 0) goto L1a
            r3.back()
        L1a:
            java.lang.String r2 = r1.toString()
            java.lang.String r2 = r2.trim()
            return r2
        L23:
            r1.append(r0)
            goto L5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.repackaged.org.json.JSONTokener.nextTo(char):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001b, code lost:
        back();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String nextTo(java.lang.String r4) throws com.google.appinventor.components.runtime.repackaged.org.json.JSONException {
        /*
            r3 = this;
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
        L5:
            char r0 = r3.next()
            int r2 = r4.indexOf(r0)
            if (r2 >= 0) goto L19
            if (r0 == 0) goto L19
            r2 = 10
            if (r0 == r2) goto L19
            r2 = 13
            if (r0 != r2) goto L27
        L19:
            if (r0 == 0) goto L1e
            r3.back()
        L1e:
            java.lang.String r2 = r1.toString()
            java.lang.String r2 = r2.trim()
            return r2
        L27:
            r1.append(r0)
            goto L5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.repackaged.org.json.JSONTokener.nextTo(java.lang.String):java.lang.String");
    }

    public Object nextValue() throws JSONException {
        char c = nextClean();
        switch (c) {
            case '\"':
            case '\'':
                return nextString(c);
            case '[':
                back();
                return new JSONArray(this);
            case '{':
                back();
                return new JSONObject(this);
            default:
                StringBuffer sb = new StringBuffer();
                while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
                    sb.append(c);
                    c = next();
                }
                back();
                String string = sb.toString().trim();
                if ("".equals(string)) {
                    throw syntaxError("Missing value");
                }
                return JSONObject.stringToValue(string);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0014, code lost:
        r10.reader.reset();
        r10.index = r4;
        r10.character = r2;
        r10.line = r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public char skipTo(char r11) throws com.google.appinventor.components.runtime.repackaged.org.json.JSONException {
        /*
            r10 = this;
            long r4 = r10.index     // Catch: java.io.IOException -> L26
            long r2 = r10.character     // Catch: java.io.IOException -> L26
            long r6 = r10.line     // Catch: java.io.IOException -> L26
            java.io.Reader r8 = r10.reader     // Catch: java.io.IOException -> L26
            r9 = 1000000(0xf4240, float:1.401298E-39)
            r8.mark(r9)     // Catch: java.io.IOException -> L26
        Le:
            char r0 = r10.next()     // Catch: java.io.IOException -> L26
            if (r0 != 0) goto L20
            java.io.Reader r8 = r10.reader     // Catch: java.io.IOException -> L26
            r8.reset()     // Catch: java.io.IOException -> L26
            r10.index = r4     // Catch: java.io.IOException -> L26
            r10.character = r2     // Catch: java.io.IOException -> L26
            r10.line = r6     // Catch: java.io.IOException -> L26
        L1f:
            return r0
        L20:
            if (r0 != r11) goto Le
            r10.back()
            goto L1f
        L26:
            r1 = move-exception
            com.google.appinventor.components.runtime.repackaged.org.json.JSONException r8 = new com.google.appinventor.components.runtime.repackaged.org.json.JSONException
            r8.<init>(r1)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.repackaged.org.json.JSONTokener.skipTo(char):char");
    }

    public JSONException syntaxError(String message) {
        return new JSONException(new StringBuffer().append(message).append(toString()).toString());
    }

    public String toString() {
        return new StringBuffer().append(" at ").append(this.index).append(" [character ").append(this.character).append(" line ").append(this.line).append("]").toString();
    }
}
