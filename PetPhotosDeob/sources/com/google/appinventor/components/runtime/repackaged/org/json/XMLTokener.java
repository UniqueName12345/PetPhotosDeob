package com.google.appinventor.components.runtime.repackaged.org.json;

import java.util.HashMap;

/* loaded from: classes2.dex */
public class XMLTokener extends JSONTokener {
    public static final HashMap entity = new HashMap(8);

    static {
        entity.put("amp", XML.AMP);
        entity.put("apos", XML.APOS);
        entity.put("gt", XML.GT);
        entity.put("lt", XML.LT);
        entity.put("quot", XML.QUOT);
    }

    public XMLTokener(String s) {
        super(s);
    }

    public String nextCDATA() throws JSONException {
        StringBuffer sb = new StringBuffer();
        while (true) {
            char c = next();
            if (end()) {
                throw syntaxError("Unclosed CDATA");
            }
            sb.append(c);
            int i = sb.length() - 3;
            if (i >= 0 && sb.charAt(i) == ']' && sb.charAt(i + 1) == ']' && sb.charAt(i + 2) == '>') {
                sb.setLength(i);
                return sb.toString();
            }
        }
    }

    public Object nextContent() throws JSONException {
        char c;
        do {
            c = next();
        } while (Character.isWhitespace(c));
        if (c == 0) {
            return null;
        }
        if (c == '<') {
            return XML.LT;
        }
        StringBuffer sb = new StringBuffer();
        while (c != '<' && c != 0) {
            if (c == '&') {
                sb.append(nextEntity(c));
            } else {
                sb.append(c);
            }
            c = next();
        }
        back();
        return sb.toString().trim();
    }

    public Object nextEntity(char ampersand) throws JSONException {
        char c;
        StringBuffer sb = new StringBuffer();
        while (true) {
            c = next();
            if (!Character.isLetterOrDigit(c) && c != '#') {
                break;
            }
            sb.append(Character.toLowerCase(c));
        }
        if (c != ';') {
            throw syntaxError(new StringBuffer().append("Missing ';' in XML entity: &").append((Object) sb).toString());
        }
        String string = sb.toString();
        Object object = entity.get(string);
        return object != null ? object : new StringBuffer().append(ampersand).append(string).append(";").toString();
    }

    public Object nextMeta() throws JSONException {
        char c;
        char c2;
        do {
            c = next();
        } while (Character.isWhitespace(c));
        switch (c) {
            case 0:
                throw syntaxError("Misshaped meta tag");
            case '!':
                return XML.BANG;
            case '\"':
            case '\'':
                do {
                    c2 = next();
                    if (c2 == 0) {
                        throw syntaxError("Unterminated string");
                    }
                } while (c2 != c);
                return Boolean.TRUE;
            case '/':
                return XML.SLASH;
            case '<':
                return XML.LT;
            case '=':
                return XML.EQ;
            case '>':
                return XML.GT;
            case '?':
                return XML.QUEST;
        }
        while (true) {
            char c3 = next();
            if (Character.isWhitespace(c3)) {
                return Boolean.TRUE;
            }
            switch (c3) {
                case 0:
                case '!':
                case '\"':
                case '\'':
                case '/':
                case '<':
                case '=':
                case '>':
                case '?':
                    back();
                    return Boolean.TRUE;
            }
        }
    }

    public Object nextToken() throws JSONException {
        char c;
        do {
            c = next();
        } while (Character.isWhitespace(c));
        switch (c) {
            case 0:
                throw syntaxError("Misshaped element");
            case '!':
                return XML.BANG;
            case '\"':
            case '\'':
                StringBuffer sb = new StringBuffer();
                while (true) {
                    char c2 = next();
                    if (c2 == 0) {
                        throw syntaxError("Unterminated string");
                    }
                    if (c2 == c) {
                        return sb.toString();
                    }
                    if (c2 == '&') {
                        sb.append(nextEntity(c2));
                    } else {
                        sb.append(c2);
                    }
                }
            case '/':
                return XML.SLASH;
            case '<':
                throw syntaxError("Misplaced '<'");
            case '=':
                return XML.EQ;
            case '>':
                return XML.GT;
            case '?':
                return XML.QUEST;
            default:
                StringBuffer sb2 = new StringBuffer();
                while (true) {
                    sb2.append(c);
                    c = next();
                    if (Character.isWhitespace(c)) {
                        return sb2.toString();
                    }
                    switch (c) {
                        case 0:
                            return sb2.toString();
                        case '!':
                        case '/':
                        case '=':
                        case '>':
                        case '?':
                        case '[':
                        case ']':
                            back();
                            return sb2.toString();
                        case '\"':
                        case '\'':
                        case '<':
                            throw syntaxError("Bad character in a name");
                    }
                }
        }
    }

    public boolean skipPast(String to) throws JSONException {
        int offset = 0;
        int length = to.length();
        char[] circle = new char[length];
        for (int i = 0; i < length; i++) {
            char c = next();
            if (c == 0) {
                return false;
            }
            circle[i] = c;
        }
        while (true) {
            int j = offset;
            boolean b = true;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                } else if (circle[j] != to.charAt(i2)) {
                    b = false;
                    break;
                } else {
                    j++;
                    if (j >= length) {
                        j -= length;
                    }
                    i2++;
                }
            }
            if (b) {
                return true;
            }
            char c2 = next();
            if (c2 == 0) {
                return false;
            }
            circle[offset] = c2;
            offset++;
            if (offset >= length) {
                offset -= length;
            }
        }
    }
}
