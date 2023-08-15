package gnu.xquery.util;

import gnu.bytecode.Access;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XIntegerType;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Path;
import gnu.text.URIPath;
import gnu.xml.TextUtils;
import java.net.URI;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class StringUtils {
    private static String ERROR_VALUE = "<error>";

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String coerceToString(Object arg, String functionName, int iarg, String onEmpty) {
        if (arg instanceof KNode) {
            arg = KNode.atomicValue(arg);
        }
        if ((arg != Values.empty && arg != null) || onEmpty == ERROR_VALUE) {
            if ((arg instanceof UntypedAtomic) || (arg instanceof CharSequence) || (arg instanceof URI) || (arg instanceof Path)) {
                return arg.toString();
            }
            throw new WrongType(functionName, iarg, arg, onEmpty == ERROR_VALUE ? "xs:string" : "xs:string?");
        }
        return onEmpty;
    }

    public static Object lowerCase(Object node) {
        return coerceToString(node, "lower-case", 1, "").toLowerCase();
    }

    public static Object upperCase(Object node) {
        return coerceToString(node, "upper-case", 1, "").toUpperCase();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static double asDouble(Object value) {
        if (!(value instanceof Number)) {
            value = NumberValue.numberValue(value);
        }
        return ((Number) value).doubleValue();
    }

    public static Object substring(Object str, Object start) {
        double d1 = asDouble(start);
        if (Double.isNaN(d1)) {
            return "";
        }
        int i = (int) (d1 - 0.5d);
        if (i < 0) {
            i = 0;
        }
        String s = coerceToString(str, "substring", 1, "");
        int len = s.length();
        int offset = 0;
        while (true) {
            int offset2 = offset;
            i--;
            if (i >= 0) {
                if (offset2 >= len) {
                    return "";
                }
                offset = offset2 + 1;
                char ch = s.charAt(offset2);
                if (ch >= 55296 && ch < 56320 && offset < len) {
                    offset++;
                }
            } else {
                return s.substring(offset2);
            }
        }
    }

    public static Object substring(Object str, Object start, Object length) {
        String s = coerceToString(str, "substring", 1, "");
        int len = s.length();
        double d1 = Math.floor(asDouble(start) - 0.5d);
        double d2 = d1 + Math.floor(asDouble(length) + 0.5d);
        if (d1 <= 0.0d) {
            d1 = 0.0d;
        }
        if (d2 > len) {
            d2 = len;
        }
        if (d2 <= d1) {
            return "";
        }
        int i1 = (int) d1;
        int i2 = ((int) d2) - i1;
        int offset = 0;
        while (true) {
            int offset2 = offset;
            i1--;
            if (i1 < 0) {
                while (true) {
                    i2--;
                    if (i2 >= 0) {
                        if (offset2 >= len) {
                            return "";
                        }
                        int offset3 = offset2 + 1;
                        char ch = s.charAt(offset2);
                        if (ch >= 55296 && ch < 56320 && offset3 < len) {
                            offset3++;
                        }
                        offset2 = offset3;
                    } else {
                        int i22 = offset2;
                        return s.substring(offset2, i22);
                    }
                }
            } else if (offset2 >= len) {
                return "";
            } else {
                offset = offset2 + 1;
                char ch2 = s.charAt(offset2);
                if (ch2 >= 55296 && ch2 < 56320 && offset < len) {
                    offset++;
                }
            }
        }
    }

    public static Object stringLength(Object str) {
        String s = coerceToString(str, "string-length", 1, "");
        int slen = s.length();
        int len = 0;
        int i = 0;
        while (i < slen) {
            int i2 = i + 1;
            char ch = s.charAt(i);
            if (ch >= 55296 && ch < 56320 && i2 < slen) {
                i2++;
            }
            len++;
            i = i2;
        }
        return IntNum.make(len);
    }

    public static Object substringBefore(Object str, Object find) {
        int start;
        String s = coerceToString(str, "substring-before", 1, "");
        String f = coerceToString(find, "substring-before", 2, "");
        int flen = f.length();
        return (flen != 0 && (start = s.indexOf(f)) >= 0) ? s.substring(0, start) : "";
    }

    public static Object substringAfter(Object str, Object find) {
        String s = coerceToString(str, "substring-after", 1, "");
        String f = coerceToString(find, "substring-after", 2, "");
        int flen = f.length();
        if (flen != 0) {
            int start = s.indexOf(f);
            return start >= 0 ? s.substring(start + flen) : "";
        }
        return s;
    }

    public static Object translate(Object str, Object map, Object trans) {
        String sv = coerceToString(str, "translate", 1, "");
        Object map2 = KNode.atomicValue(map);
        if (!(map2 instanceof UntypedAtomic) && !(map2 instanceof String)) {
            throw new WrongType("translate", 2, str, "xs:string");
        }
        String m = map2.toString();
        int mlen = m.length();
        Object trans2 = KNode.atomicValue(trans);
        if (!(trans2 instanceof UntypedAtomic) && !(trans2 instanceof String)) {
            throw new WrongType("translate", 3, str, "xs:string");
        }
        String t = trans2.toString();
        if (mlen != 0) {
            int slen = sv.length();
            StringBuffer s = new StringBuffer(slen);
            int tlen = t.length();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < slen) {
                    i = i2 + 1;
                    char c1 = sv.charAt(i2);
                    char c2 = 0;
                    if (c1 >= 55296 && c1 < 56320 && i < slen) {
                        c2 = sv.charAt(i);
                        i++;
                    }
                    int j = 0;
                    int mi = 0;
                    while (true) {
                        if (mi >= mlen) {
                            break;
                        }
                        int mi2 = mi + 1;
                        char m1 = m.charAt(mi);
                        char m2 = 0;
                        if (m1 >= 55296 && m1 < 56320 && mi2 < mlen) {
                            m2 = m.charAt(mi2);
                            mi2++;
                        }
                        if (m1 == c1 && m2 == c2) {
                            int ti = 0;
                            while (true) {
                                int ti2 = ti;
                                if (ti2 >= tlen) {
                                    break;
                                }
                                ti = ti2 + 1;
                                char t1 = t.charAt(ti2);
                                char t2 = 0;
                                if (t1 >= 55296 && t1 < 56320 && ti < tlen) {
                                    t2 = t.charAt(ti);
                                    ti++;
                                }
                                if (j != 0) {
                                    j--;
                                } else {
                                    c1 = t1;
                                    c2 = t2;
                                    break;
                                }
                            }
                        } else {
                            j++;
                            mi = mi2;
                        }
                    }
                    s.append(c1);
                    if (c2 != 0) {
                        s.append(c2);
                    }
                } else {
                    return s.toString();
                }
            }
        } else {
            return sv;
        }
    }

    public static Object stringPad(Object str, Object padcount) {
        int count = ((Number) NumberValue.numberValue(padcount)).intValue();
        if (count <= 0) {
            if (count == 0) {
                return "";
            }
            throw new IndexOutOfBoundsException("Invalid string-pad count");
        }
        String sv = coerceToString(str, "string-pad", 1, "");
        int slen = sv.length();
        StringBuffer s = new StringBuffer(count * slen);
        for (int i = 0; i < count; i++) {
            s.append(sv);
        }
        return s.toString();
    }

    public static Object contains(Object str, Object contain) {
        String s = coerceToString(str, "contains", 1, "");
        String c = coerceToString(contain, "contains", 2, "");
        return s.indexOf(c) < 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    public static Object startsWith(Object str, Object with) {
        String s = coerceToString(str, "starts-with", 1, "");
        String w = coerceToString(with, "starts-with", 2, "");
        return s.startsWith(w) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Object endsWith(Object str, Object with) {
        String s = coerceToString(str, "ends-with", 1, "");
        String w = coerceToString(with, "ends-with", 2, "");
        return s.endsWith(w) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Object stringJoin(Object strseq, Object join) {
        StringBuffer s = new StringBuffer();
        String glue = coerceToString(join, "string-join", 2, ERROR_VALUE);
        int glen = glue.length();
        int index = 0;
        boolean started = false;
        while (true) {
            int next = Values.nextIndex(strseq, index);
            if (next >= 0) {
                Object obj = Values.nextValue(strseq, index);
                if (obj != Values.empty) {
                    if (started && glen > 0) {
                        s.append(glue);
                    }
                    s.append(TextUtils.stringValue(obj));
                    started = true;
                    index = next;
                }
            } else {
                return s.toString();
            }
        }
    }

    public static String concat$V(Object arg1, Object arg2, Object[] args) {
        String str1 = TextUtils.stringValue(SequenceUtils.coerceToZeroOrOne(arg1, "concat", 1));
        String str2 = TextUtils.stringValue(SequenceUtils.coerceToZeroOrOne(arg2, "concat", 2));
        StringBuilder result = new StringBuilder(str1);
        result.append(str2);
        int count = args.length;
        for (int i = 0; i < count; i++) {
            Object arg = SequenceUtils.coerceToZeroOrOne(args[i], "concat", i + 2);
            result.append(TextUtils.stringValue(arg));
        }
        return result.toString();
    }

    public static Object compare(Object val1, Object val2, NamedCollator coll) {
        if (val1 == Values.empty || val1 == null || val2 == Values.empty || val2 == null) {
            return Values.empty;
        }
        if (coll == null) {
            coll = NamedCollator.codepointCollation;
        }
        int ret = coll.compare(val1.toString(), val2.toString());
        return ret < 0 ? IntNum.minusOne() : ret > 0 ? IntNum.one() : IntNum.zero();
    }

    public static void stringToCodepoints$X(Object arg, CallContext ctx) {
        String str = coerceToString(arg, "string-to-codepoints", 1, "");
        int len = str.length();
        Consumer out = ctx.consumer;
        int i = 0;
        while (i < len) {
            int i2 = i + 1;
            int ch = str.charAt(i);
            if (ch >= 55296 && ch < 56320 && i2 < len) {
                ch = ((ch - 55296) * 1024) + (str.charAt(i2) - 56320) + 65536;
                i2++;
            }
            out.writeInt(ch);
            i = i2;
        }
    }

    private static void appendCodepoint(Object code, StringBuffer sbuf) {
        IntNum I = (IntNum) XIntegerType.integerType.cast(code);
        int i = I.intValue();
        if (i <= 0 || (i > 55295 && (i < 57344 || ((i > 65533 && i < 65536) || i > 1114111)))) {
            throw new IllegalArgumentException("codepoints-to-string: " + i + " is not a valid XML character [FOCH0001]");
        }
        if (i >= 65536) {
            sbuf.append((char) (((i - 65536) >> 10) + 55296));
            i = (i & 1023) + 56320;
        }
        sbuf.append((char) i);
    }

    public static String codepointsToString(Object arg) {
        if (arg == null) {
            return "";
        }
        StringBuffer sbuf = new StringBuffer();
        if (arg instanceof Values) {
            Values vals = (Values) arg;
            int ipos = vals.startPos();
            while (true) {
                ipos = vals.nextPos(ipos);
                if (ipos == 0) {
                    break;
                }
                appendCodepoint(vals.getPosPrevious(ipos), sbuf);
            }
        } else {
            appendCodepoint(arg, sbuf);
        }
        return sbuf.toString();
    }

    public static String encodeForUri(Object arg) {
        String str = coerceToString(arg, "encode-for-uri", 1, "");
        return URIPath.encodeForUri(str, 'U');
    }

    public static String iriToUri(Object arg) {
        String str = coerceToString(arg, "iri-to-uru", 1, "");
        return URIPath.encodeForUri(str, Access.INNERCLASS_CONTEXT);
    }

    public static String escapeHtmlUri(Object arg) {
        String str = coerceToString(arg, "escape-html-uri", 1, "");
        return URIPath.encodeForUri(str, 'H');
    }

    public static String normalizeSpace(Object arg) {
        String str = coerceToString(arg, "normalize-space", 1, "");
        int len = str.length();
        StringBuffer sbuf = null;
        int skipped = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (Character.isWhitespace(ch)) {
                if (sbuf == null && skipped == 0 && i > 0) {
                    sbuf = new StringBuffer(str.substring(0, i));
                }
                skipped++;
            } else {
                if (skipped > 0) {
                    if (sbuf != null) {
                        sbuf.append(' ');
                    } else if (skipped > 1 || i == 1 || str.charAt(i - 1) != ' ') {
                        sbuf = new StringBuffer();
                    }
                    skipped = 0;
                }
                if (sbuf != null) {
                    sbuf.append(ch);
                }
            }
        }
        return sbuf != null ? sbuf.toString() : skipped > 0 ? "" : str;
    }

    public static Pattern makePattern(String pattern, String flags) {
        int fl = 0;
        int i = flags.length();
        while (true) {
            i--;
            if (i >= 0) {
                char ch = flags.charAt(i);
                switch (ch) {
                    case 'i':
                        fl |= 66;
                        break;
                    case 'm':
                        fl |= 8;
                        break;
                    case 's':
                        fl |= 32;
                        break;
                    case 'x':
                        StringBuffer sbuf = new StringBuffer();
                        int plen = pattern.length();
                        int inBracket = 0;
                        int j = 0;
                        while (j < plen) {
                            int j2 = j + 1;
                            char pch = pattern.charAt(j);
                            if (pch == '\\' && j2 < plen) {
                                sbuf.append(pch);
                                pch = pattern.charAt(j2);
                                j2++;
                            } else if (pch == '[') {
                                inBracket++;
                            } else if (pch == ']') {
                                inBracket--;
                            } else if (inBracket == 0 && Character.isWhitespace(pch)) {
                                j = j2;
                            }
                            sbuf.append(pch);
                            j = j2;
                        }
                        pattern = sbuf.toString();
                        break;
                    default:
                        throw new IllegalArgumentException("unknown 'replace' flag");
                }
            } else {
                if (pattern.indexOf("{Is") >= 0) {
                    StringBuffer sbuf2 = new StringBuffer();
                    int plen2 = pattern.length();
                    int j3 = 0;
                    while (j3 < plen2) {
                        int j4 = j3 + 1;
                        char pch2 = pattern.charAt(j3);
                        if (pch2 == '\\' && j4 + 4 < plen2) {
                            sbuf2.append(pch2);
                            int j5 = j4 + 1;
                            char pch3 = pattern.charAt(j4);
                            sbuf2.append(pch3);
                            if ((pch3 == 'p' || pch3 == 'P') && pattern.charAt(j5) == '{' && pattern.charAt(j5 + 1) == 'I' && pattern.charAt(j5 + 2) == 's') {
                                sbuf2.append('{');
                                sbuf2.append(Access.INNERCLASS_CONTEXT);
                                sbuf2.append('n');
                                j4 = j5 + 3;
                            } else {
                                j4 = j5;
                            }
                        } else {
                            sbuf2.append(pch2);
                        }
                        j3 = j4;
                    }
                    pattern = sbuf2.toString();
                }
                return Pattern.compile(pattern, fl);
            }
        }
    }

    public static boolean matches(Object input, String pattern) {
        return matches(input, pattern, "");
    }

    public static boolean matches(Object arg, String pattern, String flags) {
        String str = coerceToString(arg, "matches", 1, "");
        return makePattern(pattern, flags).matcher(str).find();
    }

    public static String replace(Object input, String pattern, String replacement) {
        return replace(input, pattern, replacement, "");
    }

    public static String replace(Object arg, String pattern, String replacement, String flags) {
        String str = coerceToString(arg, "replace", 1, "");
        int rlen = replacement.length();
        int i = 0;
        while (i < rlen) {
            int i2 = i + 1;
            char rch = replacement.charAt(i);
            if (rch == '\\') {
                if (i2 < rch) {
                    int i3 = i2 + 1;
                    char rch2 = replacement.charAt(i2);
                    if (rch2 == '\\' || rch2 == '$') {
                        i2 = i3;
                    }
                }
                throw new IllegalArgumentException("invalid replacement string [FORX0004]");
            }
            i = i2;
        }
        return makePattern(pattern, flags).matcher(str).replaceAll(replacement);
    }

    /* JADX WARN: Incorrect condition in loop: B:7:0x001f */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void tokenize$X(java.lang.Object r10, java.lang.String r11, java.lang.String r12, gnu.mapping.CallContext r13) {
        /*
            java.lang.String r7 = "tokenize"
            r8 = 1
            java.lang.String r9 = ""
            java.lang.String r6 = coerceToString(r10, r7, r8, r9)
            gnu.lists.Consumer r4 = r13.consumer
            java.util.regex.Pattern r7 = makePattern(r11, r12)
            java.util.regex.Matcher r3 = r7.matcher(r6)
            int r1 = r6.length()
            if (r1 != 0) goto L1a
        L19:
            return
        L1a:
            r5 = 0
        L1b:
            boolean r2 = r3.find()
            if (r2 != 0) goto L29
            java.lang.String r7 = r6.substring(r5)
            r4.writeObject(r7)
            goto L19
        L29:
            int r0 = r3.start()
            java.lang.String r7 = r6.substring(r5, r0)
            r4.writeObject(r7)
            int r5 = r3.end()
            if (r5 != r0) goto L1b
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "pattern matches empty string"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.StringUtils.tokenize$X(java.lang.Object, java.lang.String, java.lang.String, gnu.mapping.CallContext):void");
    }

    public static Object codepointEqual(Object arg1, Object arg2) {
        String str1 = coerceToString(arg1, "codepoint-equal", 1, null);
        String str2 = coerceToString(arg2, "codepoint-equal", 2, null);
        if (str1 == null || str2 == null) {
            return Values.empty;
        }
        return str1.equals(str2) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Object normalizeUnicode(Object arg) {
        return normalizeUnicode(arg, "NFC");
    }

    public static Object normalizeUnicode(Object arg, String form) {
        String str = coerceToString(arg, "normalize-unicode", 1, "");
        if ("".equals(form.trim().toUpperCase())) {
            return str;
        }
        throw new UnsupportedOperationException("normalize-unicode: unicode string normalization not available");
    }
}
