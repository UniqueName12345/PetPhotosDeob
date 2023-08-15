package gnu.kawa.functions;

import gnu.mapping.OutPort;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.FieldPosition;

/* compiled from: LispFormat.java */
/* loaded from: classes.dex */
class LispNewlineFormat extends ReportFormat {
    static final String line_separator = System.getProperty("line.separator", "\n");
    int count;
    int kind;

    LispNewlineFormat() {
    }

    public static LispNewlineFormat getInstance(int count, int kind) {
        LispNewlineFormat fmt = new LispNewlineFormat();
        fmt.count = count;
        fmt.kind = kind;
        return fmt;
    }

    @Override // gnu.text.ReportFormat
    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        int count = getParam(this.count, 1, args, start);
        if (this.count == -1610612736) {
            start++;
        }
        while (true) {
            count--;
            if (count >= 0) {
                printNewline(this.kind, dst);
            } else {
                return start;
            }
        }
    }

    public static void printNewline(int kind, Writer dst) throws IOException {
        if ((dst instanceof OutPort) && kind != 76) {
            ((OutPort) dst).writeBreak(kind);
        } else if (dst instanceof PrintWriter) {
            ((PrintWriter) dst).println();
        } else {
            dst.write(line_separator);
        }
    }
}
