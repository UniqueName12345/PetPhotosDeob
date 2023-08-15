package gnu.kawa.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;

/* loaded from: classes.dex */
public class PreProcess {
    static final String JAVA4_FEATURES = "+JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio";
    static final String JAVA5_FEATURES = "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName";
    static final String NO_JAVA4_FEATURES = "-JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android";
    static final String NO_JAVA6_FEATURES = "-JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer";
    static String[] version_features = {"java1", "-JAVA2 -JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java2", "+JAVA2 -JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java4", "-JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java4x", "-JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +JAXP-1.3 +use:javax.xml.transform -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java5", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java6compat5", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName -JAVA6 -JAVA7 +JAVA6COMPAT5 +use:java.text.Normalizer -use:java.dyn -Android", "java6", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName +JAVA6 -JAVA7 -JAVA6COMPAT5 +use:java.text.Normalizer -use:java.dyn -Android", "java7", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName +JAVA6 +JAVA7 -JAVA6COMPAT5 +use:java.text.Normalizer +use:java.dyn -Android", "android", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +JAXP-1.3 -JAXP-QName -use:javax.xml.transform -JAVA6 -JAVA6COMPAT5 +Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer"};
    String filename;
    Hashtable keywords = new Hashtable();
    int lineno;
    byte[] resultBuffer;
    int resultLength;

    void error(String msg) {
        System.err.println(this.filename + ':' + this.lineno + ": " + msg);
        System.exit(-1);
    }

    public void filter(String filename) throws Throwable {
        if (filter(filename, new BufferedInputStream(new FileInputStream(filename)))) {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(this.resultBuffer, 0, this.resultLength);
            out.close();
            System.err.println("Pre-processed " + filename);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x009a, code lost:
        if (r15 >= 0) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x009c, code lost:
        if (r8 > 0) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00a2, code lost:
        if (r6 == 13) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a8, code lost:
        if (r6 == 10) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00aa, code lost:
        if (r12 == r13) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00b0, code lost:
        if (r6 == 32) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00b6, code lost:
        if (r6 == 9) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00bc, code lost:
        if (r6 != 47) goto L193;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00be, code lost:
        r33.mark(100);
        r14 = r33.read();
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00cf, code lost:
        if (r14 != 47) goto L177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00d1, code lost:
        r16 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00d3, code lost:
        r33.reset();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00d6, code lost:
        if (r16 == false) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00d8, code lost:
        r21 = r20 + 1;
        r5[r20] = com.google.appinventor.components.runtime.util.Ev3Constants.Opcode.INIT_BYTES;
        r20 = r21 + 1;
        r5[r21] = com.google.appinventor.components.runtime.util.Ev3Constants.Opcode.INIT_BYTES;
        r21 = r20 + 1;
        r5[r20] = 32;
        r8 = 1;
        r7 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0114, code lost:
        if (r14 != 42) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0116, code lost:
        r14 = r33.read();
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x011e, code lost:
        if (r14 == 32) goto L191;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0124, code lost:
        if (r14 == 9) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x012a, code lost:
        if (r14 == 35) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x012c, code lost:
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x012f, code lost:
        r16 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0132, code lost:
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0135, code lost:
        r16 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0165  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean filter(java.lang.String r32, java.io.BufferedInputStream r33) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 913
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.util.PreProcess.filter(java.lang.String, java.io.BufferedInputStream):boolean");
    }

    void handleArg(String arg) {
        if (arg.charAt(0) == '%') {
            String arg2 = arg.substring(1);
            int i = 0;
            while (true) {
                if (i >= version_features.length) {
                    System.err.println("Unknown version: " + arg2);
                    System.exit(-1);
                }
                if (arg2.equals(version_features[i])) {
                    break;
                }
                i += 2;
            }
            String features = version_features[i + 1];
            System.err.println("(variant " + arg2 + " maps to: " + features + ")");
            StringTokenizer tokenizer = new StringTokenizer(features);
            while (tokenizer.hasMoreTokens()) {
                handleArg(tokenizer.nextToken());
            }
        } else if (arg.charAt(0) == '+') {
            this.keywords.put(arg.substring(1), Boolean.TRUE);
        } else if (arg.charAt(0) == '-') {
            int eq = arg.indexOf(61);
            if (eq > 1) {
                String keyword = arg.substring(arg.charAt(1) == '-' ? 2 : 1, eq);
                String value = arg.substring(eq + 1);
                Boolean b = Boolean.FALSE;
                if (value.equalsIgnoreCase("true")) {
                    b = Boolean.TRUE;
                } else if (!value.equalsIgnoreCase("false")) {
                    System.err.println("invalid value " + value + " for " + keyword);
                    System.exit(-1);
                }
                this.keywords.put(keyword, b);
                return;
            }
            this.keywords.put(arg.substring(1), Boolean.FALSE);
        } else {
            try {
                filter(arg);
            } catch (Throwable ex) {
                System.err.println("caught " + ex);
                ex.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public static void main(String[] args) {
        PreProcess pp = new PreProcess();
        pp.keywords.put("true", Boolean.TRUE);
        pp.keywords.put("false", Boolean.FALSE);
        for (String str : args) {
            pp.handleArg(str);
        }
    }
}
