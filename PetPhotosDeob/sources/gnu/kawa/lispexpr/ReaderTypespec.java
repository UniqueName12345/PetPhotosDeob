package gnu.kawa.lispexpr;

/* loaded from: classes.dex */
public class ReaderTypespec extends ReadTableEntry {
    @Override // gnu.kawa.lispexpr.ReadTableEntry
    public int getKind() {
        return 6;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0057, code lost:
        if (1 != 1) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0060, code lost:
        if (0 != 0) goto L28;
     */
    @Override // gnu.kawa.lispexpr.ReadTableEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object read(gnu.text.Lexer r12, int r13, int r14) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            r11 = this;
            int r7 = r12.tokenBufferLength
            gnu.text.LineBufferedReader r3 = r12.getPort()
            gnu.kawa.lispexpr.ReadTable r5 = gnu.kawa.lispexpr.ReadTable.getCurrent()
            r6 = 0
            r12.tokenBufferAppend(r13)
            r1 = r13
            boolean r8 = r3 instanceof gnu.mapping.InPort
            if (r8 == 0) goto L1e
            r8 = r3
            gnu.mapping.InPort r8 = (gnu.mapping.InPort) r8
            char r6 = r8.readState
            r8 = r3
            gnu.mapping.InPort r8 = (gnu.mapping.InPort) r8
            char r9 = (char) r13
            r8.readState = r9
        L1e:
            r2 = 0
        L1f:
            r4 = r1
            int r8 = r3.pos     // Catch: java.lang.Throwable -> L71
            int r9 = r3.limit     // Catch: java.lang.Throwable -> L71
            if (r8 >= r9) goto L45
            r8 = 10
            if (r4 == r8) goto L45
            char[] r8 = r3.buffer     // Catch: java.lang.Throwable -> L71
            int r9 = r3.pos     // Catch: java.lang.Throwable -> L71
            int r10 = r9 + 1
            r3.pos = r10     // Catch: java.lang.Throwable -> L71
            char r1 = r8[r9]     // Catch: java.lang.Throwable -> L71
        L34:
            r8 = 92
            if (r1 != r8) goto L4f
            boolean r8 = r12 instanceof gnu.kawa.lispexpr.LispReader     // Catch: java.lang.Throwable -> L71
            if (r8 == 0) goto L4a
            r0 = r12
            gnu.kawa.lispexpr.LispReader r0 = (gnu.kawa.lispexpr.LispReader) r0     // Catch: java.lang.Throwable -> L71
            r8 = r0
            int r1 = r8.readEscape()     // Catch: java.lang.Throwable -> L71
            goto L1f
        L45:
            int r1 = r3.read()     // Catch: java.lang.Throwable -> L71
            goto L34
        L4a:
            int r1 = r3.read()     // Catch: java.lang.Throwable -> L71
            goto L1f
        L4f:
            if (r2 != 0) goto L59
            r8 = 91
            if (r1 != r8) goto L59
            r8 = 1
            r2 = 1
            if (r8 == r2) goto L6d
        L59:
            if (r2 == 0) goto L62
            r8 = 93
            if (r1 != r8) goto L62
            r2 = 0
            if (r2 == 0) goto L6d
        L62:
            gnu.kawa.lispexpr.ReadTableEntry r8 = r5.lookup(r1)     // Catch: java.lang.Throwable -> L71
            int r8 = r8.getKind()     // Catch: java.lang.Throwable -> L71
            r9 = 2
            if (r8 != r9) goto L7d
        L6d:
            r12.tokenBufferAppend(r1)     // Catch: java.lang.Throwable -> L71
            goto L1f
        L71:
            r8 = move-exception
            r12.tokenBufferLength = r7
            boolean r9 = r3 instanceof gnu.mapping.InPort
            if (r9 == 0) goto L7c
            gnu.mapping.InPort r3 = (gnu.mapping.InPort) r3
            r3.readState = r6
        L7c:
            throw r8
        L7d:
            r12.unread(r1)     // Catch: java.lang.Throwable -> L71
            java.lang.String r8 = new java.lang.String     // Catch: java.lang.Throwable -> L71
            char[] r9 = r12.tokenBuffer     // Catch: java.lang.Throwable -> L71
            int r10 = r12.tokenBufferLength     // Catch: java.lang.Throwable -> L71
            int r10 = r10 - r7
            r8.<init>(r9, r7, r10)     // Catch: java.lang.Throwable -> L71
            java.lang.String r8 = r8.intern()     // Catch: java.lang.Throwable -> L71
            r12.tokenBufferLength = r7
            boolean r9 = r3 instanceof gnu.mapping.InPort
            if (r9 == 0) goto L98
            gnu.mapping.InPort r3 = (gnu.mapping.InPort) r3
            r3.readState = r6
        L98:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.ReaderTypespec.read(gnu.text.Lexer, int, int):java.lang.Object");
    }
}
