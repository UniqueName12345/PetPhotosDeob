package com.google.appinventor.components.runtime.repackaged.org.json;

/* loaded from: classes2.dex */
public class HTTPTokener extends JSONTokener {
    public HTTPTokener(String string) {
        super(string);
    }

    public String nextToken() throws JSONException {
        char c;
        StringBuffer sb = new StringBuffer();
        do {
            c = next();
        } while (Character.isWhitespace(c));
        if (c != '\"' && c != '\'') {
            while (c != 0 && !Character.isWhitespace(c)) {
                sb.append(c);
                c = next();
            }
            return sb.toString();
        }
        while (true) {
            char c2 = next();
            if (c2 < ' ') {
                throw syntaxError("Unterminated string.");
            }
            if (c2 == c) {
                return sb.toString();
            }
            sb.append(c2);
        }
    }
}
